## MethodFinder [class]

```kotlin
class MethodFinder(override val hookInstance: YukiMemberHookCreater.MemberHookCreater?, override val classSet: Class<*>) : BaseFinder()
```

**变更记录**

`v1.0` `添加`

`v1.0.2` `修改`

合并到 `BaseFinder`

**功能描述**

> `Method` 查找类。

### name [field]

```kotlin
var name: String
```

**变更记录**

`v1.0` `添加`

`v1.0.70` `修改`

允许不填写名称

**功能描述**

> 设置 `Method` 名称。

!> 若不填写名称则必须存在一个其它条件。

### paramCount [field]

```kotlin
var paramCount: Int
```

**变更记录**

`v1.0.67` `新增`

**功能描述**

> 设置 `Method` 参数个数。

你可以不使用 `param` 指定参数类型而是仅使用此变量指定参数个数。

若参数个数小于零则忽略并使用 `param`。

### returnType [field]

```kotlin
var returnType: Any?
```

**变更记录**

`v1.0` `添加`

**功能描述**

> 设置 `Method` 返回值，可不填写返回值，默认模糊查找并取第一个匹配的 `Method`。

### modifiers [method]

```kotlin
inline fun modifiers(initiate: ModifierRules.() -> Unit): IndexTypeCondition
```

**变更记录**

`v1.0.67` `新增`

`v1.0.80` `修改`

将方法体进行 inline

**功能描述**

> 设置 `Method` 标识符筛选条件。

可不设置筛选条件，默认模糊查找并取第一个匹配的 `Method`。

!> 存在多个 `IndexTypeCondition` 时除了 `order` 只会生效最后一个。

### emptyParam [method]

```kotlin
fun emptyParam(): IndexTypeCondition
```

**变更记录**

`v1.0.75` `新增`

**功能描述**

> 设置 `Method` 空参数、无参数。

### param [method]

```kotlin
fun param(vararg paramType: Any): IndexTypeCondition
```

**变更记录**

`v1.0` `添加`

**功能描述**

> 设置 `Method` 参数。

如果同时使用了 `paramCount` 则 `paramTypes` 的数量必须与 `paramCount` 完全匹配。

!> 无参 `Method` 请使用 `emptyParam` 设置查询条件。

!> 有参 `Method` 必须使用此方法设定参数或使用 `paramCount` 指定个数。

!> 存在多个 `IndexTypeCondition` 时除了 `order` 只会生效最后一个。

### order [method]

```kotlin
fun order(): IndexTypeCondition
```

**变更记录**

`v1.0.70` `新增`

**功能描述**

> 顺序筛选字节码的下标。

### name [method]

```kotlin
fun name(value: String): IndexTypeCondition
```

**变更记录**

`v1.0.70` `新增`

**功能描述**

> 设置 `Method` 名称。

!> 若不填写名称则必须存在一个其它条件，默认模糊查找并取第一个匹配的 `Method`。

!> 存在多个 `IndexTypeCondition` 时除了 `order` 只会生效最后一个。

### paramCount [method]

```kotlin
fun paramCount(num: Int): IndexTypeCondition
```

**变更记录**

`v1.0.70` `新增`

**功能描述**

> 设置 `Method` 参数个数。

你可以不使用 `param` 指定参数类型而是仅使用此方法指定参数个数。

若参数个数小于零则忽略并使用 `param`。

!> 存在多个 `IndexTypeCondition` 时除了 `order` 只会生效最后一个。

### returnType [method]

```kotlin
fun returnType(value: Any): IndexTypeCondition
```

**变更记录**

`v1.0.70` `新增`

**功能描述**

> 设置 `Method` 返回值。

可不填写返回值，默认模糊查找并取第一个匹配的 `Method`。

!> 存在多个 `IndexTypeCondition` 时除了 `order` 只会生效最后一个。

### superClass [method]

```kotlin
fun superClass(isOnlySuperClass: Boolean)
```

**变更记录**

`v1.0.80` `新增`

**功能描述**

> 设置在 `classSet` 的所有父类中查找当前 `Method`。

!> 若当前 `classSet` 的父类较多可能会耗时，API 会自动循环到父类继承是 `Any` 前的最后一个类。

### RemedyPlan [class]

```kotlin
inner class RemedyPlan
```

**变更记录**

`v1.0` `添加`

**功能描述**

> `Method` 重查找实现类，可累计失败次数直到查找成功。

#### method [method]

```kotlin
inline fun method(initiate: MethodFinder.() -> Unit): Result
```

**变更记录**

`v1.0` `添加`

`v1.0.80` `修改`

将方法体进行 inline

**功能描述**

> 创建需要重新查找的 `Method`。

你可以添加多个备选方法，直到成功为止，若最后依然失败，将停止查找并输出错误日志。

#### Result [class]

```kotlin
inner class Result
```

**变更记录**

`v1.0.1` `新增`

**功能描述**

> `RemedyPlan` 结果实现类。

##### onFind [method]

```kotlin
fun onFind(initiate: Method.() -> Unit)
```

**变更记录**

`v1.0.1` `新增`

**功能描述**

> 当在 `RemedyPlan` 中找到结果时。

**功能示例**

你可以方便地对重查找的 `Method` 实现 `onFind` 方法。

> 示例如下

```kotlin
method {
    // Your code here.
}.onFind {
    // Your code here.
}
```

### Result [class]

```kotlin
inner class Result(internal val isNoSuch: Boolean, private val e: Throwable?)
```

**变更记录**

`v1.0` `添加`

**功能描述**

> `Method` 查找结果实现类。

#### result [method]

```kotlin
inline fun result(initiate: Result.() -> Unit): Result
```

**变更记录**

`v1.0` `添加`

`v1.0.80` `修改`

将方法体进行 inline

**功能描述**

> 创建监听结果事件方法体。

**功能示例**

你可以使用 `lambda` 形式创建 `Result` 类。

> 示例如下

```kotlin
method {
    // Your code here.
}.result {
    get(instance).call()
    remedys {}
    onNoSuchMethod {}
}
```

#### get [method]

```kotlin
fun get(instance: Any?): Instance
```

**变更记录**

`v1.0.2` `新增`

**功能描述**

> 获得 `Method` 实例处理类。

!> 若你设置了 `remedys` 请使用 `wait` 回调结果方法。

**功能示例**

你可以通过获得方法所在实例来执行方法。

> 示例如下

```kotlin
method {
    // Your code here.
}.get(instance).call()
```

若当前为静态方法，你可以不设置实例。

> 示例如下

```kotlin
method {
    // Your code here.
}.get().call()
```

#### give [method]

```kotlin
fun give(): Method?
```

**变更记录**

`v1.0.67` `新增`

**功能描述**

> 得到方法本身。

#### wait [method]

```kotlin
fun wait(instance: Any?, initiate: Instance.() -> Unit)
```

**变更记录**

`v1.0.2` `新增`

**功能描述**

> 获得 `Method` 实例处理类，配合 `RemedyPlan` 使用。

!> 若你设置了 `remedys` 必须使用此方法才能获得结果。

!> 若你没有设置 `remedys` 此方法将不会被回调。

#### remedys [method]

```kotlin
inline fun remedys(initiate: RemedyPlan.() -> Unit): Result
```

**变更记录**

`v1.0` `添加`

`v1.0.80` `修改`

将方法体进行 inline

**功能描述**

> 创建方法重查找功能。

**功能示例**

当你遇到一种方法可能存在不同形式的存在时，可以使用 `RemedyPlan` 重新查找它，而没有必要使用 `onNoSuchMethod` 捕获异常二次查找方法。

若第一次查找失败了，你还可以在这里继续添加此方法体直到成功为止。

> 示例如下

```kotlin
method {
    // Your code here.
}.remedys {
    method {
        // Your code here.
    }
    method {
        // Your code here.
    }
}
```

#### onNoSuchMethod [method]

```kotlin
inline fun onNoSuchMethod(initiate: (Throwable) -> Unit): Result
```

**变更记录**

`v1.0` `添加`

`v1.0.80` `修改`

将方法体进行 inline

**功能描述**

> 监听找不到方法时。

只会返回第一次的错误信息，不会返回 `RemedyPlan` 的错误信息。

#### ignoredError [method]

```kotlin
fun ignoredError(): Result
```

**变更记录**

`v1.0.3` `新增`

**功能描述**

> 忽略任何错误发出的警告。

若 `isNotIgnoredHookingFailure` 为 `false` 则自动忽略。

#### Instance [class]

```kotlin
inner class Instance(private val instance: Any?)
```

**变更记录**

`v1.0.2` `新增`

**功能描述**

> `Method` 实例处理类。

##### call [method]

```kotlin
fun call(vararg param: Any?): Any?
```

**变更记录**

`v1.0.2` `新增`

**功能描述**

> 执行方法，不指定返回值类型。

##### invoke [method]

```kotlin
fun <T> invoke(vararg param: Any?): T?
```

**变更记录**

`v1.0.2` `新增`

**功能描述**

> 执行方法，指定 `T` 返回值类型。

##### byte [method]

```kotlin
fun byte(vararg param: Any?): Byte?
```

**变更记录**

`v1.0.68` `新增`

**功能描述**

> 执行方法，指定 Byte 返回值类型。

##### int [method]

```kotlin
fun int(vararg param: Any?): Int
```

**变更记录**

`v1.0.65` `新增`

`v1.0.68` `修改`

修改 ~~`callInt`~~ 为 `int`

**功能描述**

> 执行方法，指定 Int 返回值类型。

##### long [method]

```kotlin
fun long(vararg param: Any?): Long
```

**变更记录**

`v1.0.65` `新增`

`v1.0.68` `修改`

修改 ~~`callLong`~~ 为 `long`

**功能描述**

> 执行方法，指定 Long 返回值类型。

##### short [method]

```kotlin
fun short(vararg param: Any?): Short
```

**变更记录**

`v1.0.65` `新增`

`v1.0.68` `修改`

修改 ~~`callShort`~~ 为 `short`

**功能描述**

> 执行方法，指定 Short 返回值类型。

##### double [method]

```kotlin
fun double(vararg param: Any?): Double
```

**变更记录**

`v1.0.65` `新增`

`v1.0.68` `修改`

修改 ~~`callDouble`~~ 为 `double`

**功能描述**

> 执行方法，指定 Double 返回值类型。

##### float [method]

```kotlin
fun float(vararg param: Any?): Float
```

**变更记录**

`v1.0.65` `新增`

`v1.0.68` `修改`

修改 ~~`callFloat`~~ 为 `float`

**功能描述**

> 执行方法，指定 Float 返回值类型。

##### string [method]

```kotlin
fun string(vararg param: Any?): String
```

**变更记录**

`v1.0.65` `新增`

`v1.0.68` `修改`

修改 ~~`callString`~~ 为 `string`

**功能描述**

> 执行方法，指定 String 返回值类型。

##### char [method]

```kotlin
fun char(vararg param: Any?): Char
```

**变更记录**

`v1.0.68` `新增`

**功能描述**

> 执行方法，指定 Char 返回值类型。

##### boolean [method]

```kotlin
fun boolean(vararg param: Any?): Boolean
```

**变更记录**

`v1.0.65` `新增`

`v1.0.68` `修改`

修改 ~~`callBoolean`~~ 为 `boolean`

**功能描述**

> 执行方法，指定 Boolean 返回值类型。

#### array [method]

```kotlin
inline fun <reified T> array(vararg param: Any?): Array<T>
```

**变更记录**

`v1.0.68` `新增`

**功能描述**

> 执行方法，指定 Array 返回值类型。

#### list [method]

```kotlin
inline fun <reified T> list(vararg param: Any?): List<T>
```

**变更记录**

`v1.0.68` `新增`

**功能描述**

> 执行方法，指定 List 返回值类型。