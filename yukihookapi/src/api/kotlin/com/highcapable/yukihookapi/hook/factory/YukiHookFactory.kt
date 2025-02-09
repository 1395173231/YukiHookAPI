/*
 * YukiHookAPI - An efficient Kotlin version of the Xposed Hook API.
 * Copyright (C) 2019-2022 HighCapable
 * https://github.com/fankes/YukiHookAPI
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * This file is Created by fankes on 2022/2/2.
 */
@file:Suppress("unused", "DEPRECATION", "DeprecatedCallableAddReplaceWith")

package com.highcapable.yukihookapi.hook.factory

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Process
import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.xposed.YukiHookModuleStatus
import com.highcapable.yukihookapi.hook.xposed.prefs.YukiHookModulePrefs
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import com.highcapable.yukihookapi.hook.xposed.proxy.YukiHookXposedInitProxy
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

/**
 * 在 [IYukiHookXposedInit] 中调用 [YukiHookAPI.configs]
 * @param initiate 配置方法体
 */
inline fun IYukiHookXposedInit.configs(initiate: YukiHookAPI.Configs.() -> Unit) = YukiHookAPI.configs(initiate)

/**
 * 在 [IYukiHookXposedInit] 中调用 [YukiHookAPI.encase]
 * @param initiate Hook 方法体
 */
fun IYukiHookXposedInit.encase(initiate: PackageParam.() -> Unit) = YukiHookAPI.encase(initiate)

/**
 * 在 [IYukiHookXposedInit] 中装载 [YukiHookAPI]
 * @param hooker Hook 子类数组 - 必填不能为空
 * @throws IllegalStateException 如果 [hooker] 是空的
 */
fun IYukiHookXposedInit.encase(vararg hooker: YukiBaseHooker) = YukiHookAPI.encase(hooker = hooker)

@Deprecated("请将接口转移到 IYukiHookXposedInit")
inline fun YukiHookXposedInitProxy.configs(initiate: YukiHookAPI.Configs.() -> Unit) = YukiHookAPI.configs(initiate)

@Deprecated("请将接口转移到 IYukiHookXposedInit")
fun YukiHookXposedInitProxy.encase(initiate: PackageParam.() -> Unit) = YukiHookAPI.encase(initiate)

@Deprecated("请将接口转移到 IYukiHookXposedInit")
fun YukiHookXposedInitProxy.encase(vararg hooker: YukiBaseHooker) = YukiHookAPI.encase(hooker = hooker)

/**
 * 获取模块的存取对象
 * @return [YukiHookModulePrefs]
 */
val Context.modulePrefs get() = YukiHookModulePrefs(context = this)

/**
 * 获取模块的存取对象
 * @param name 自定义 Sp 存储名称
 * @return [YukiHookModulePrefs]
 */
fun Context.modulePrefs(name: String) = YukiHookModulePrefs(context = this).name(name)

/**
 * 获取当前进程名称
 * @return [String]
 */
val Context.processName
    get() = try {
        BufferedReader(FileReader(File("/proc/${Process.myPid()}/cmdline"))).let { buff ->
            buff.readLine().trim { it <= ' ' }.let {
                buff.close()
                it
            }
        }
    } catch (_: Throwable) {
        packageName ?: ""
    }

/**
 * 判断当前 Hook Framework 是否支持资源钩子(Resources Hook)
 * @return [Boolean] 是否支持
 */
val Any?.isSupportResourcesHook get() = YukiHookModuleStatus.hasResourcesHook()

/**
 * 判断模块是否在 Xposed 或太极、无极中激活
 * @return [Boolean] 是否激活
 */
val Context.isModuleActive get() = YukiHookModuleStatus.isActive() || isTaiChiModuleActive

/**
 * 仅判断模块是否在 Xposed 中激活
 * @return [Boolean] 是否激活
 */
val Any?.isXposedModuleActive get() = YukiHookModuleStatus.isActive()

/**
 * 仅判断模块是否在太极、无极中激活
 *
 * 此处的实现代码来自太极官方文档封装和改进
 *
 * 详情请参考太极开发指南中的 [如何判断模块是否激活了？](https://taichi.cool/zh/doc/for-xposed-dev.html#%E5%A6%82%E4%BD%95%E5%88%A4%E6%96%AD%E6%A8%A1%E5%9D%97%E6%98%AF%E5%90%A6%E6%BF%80%E6%B4%BB%E4%BA%86%EF%BC%9F)
 * @return [Boolean] 是否激活
 */
val Context.isTaiChiModuleActive: Boolean
    get() {
        var isModuleActive = false
        runCatching {
            var result: Bundle? = null
            Uri.parse("content://me.weishu.exposed.CP/").also { uri ->
                runCatching {
                    result = contentResolver.call(uri, "active", null, null)
                }.onFailure {
                    // TaiChi is killed, try invoke
                    runCatching {
                        startActivity(Intent("me.weishu.exp.ACTION_ACTIVE").apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) })
                    }.onFailure { return false }
                }
                if (result == null)
                    result = contentResolver.call(Uri.parse("content://me.weishu.exposed.CP/"), "active", null, null)
                if (result == null) return false
            }
            isModuleActive = result?.getBoolean("active", false) == true
        }
        return isModuleActive
    }