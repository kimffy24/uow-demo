# uow-demo

简单的演示 uow 和 uowgen-maven-plugin的使用

## 前置

### 1. 生成sql并导入数据库

使用以下命令执行1次codegen

```
cd uow-demo
mvn clean com.github.kimffy24:uowgen-maven-plugin:0.1.0.0:uow-gen -DoutputSpec=pro/jiefzz/demo/uowdemo
```

其中，`outputSpec=pro/jiefzz/demo/uowdemo` 可以按需指定为别的目录；

库的sql文件在 `src/main/java/pro/jiefzz/demo/uowdemo/uow-gen-all.sql` 里，自行导入数据库即可；

## codegen过程简述

uowgen插件会先执行一次编译，然后通过加载部分class并分析元数据，输出我们需要的UoW java类（并且他们能都带上了注解，不用再单独配置）

依靠outputSpec参数指定输出的根目录，codegen会在此目录下创建uowgenroot文件夹，并把生成的java类输出到此。

相关的提示信息会也会输出到uowgenroot/package-info.java中

> 因为codegen过程已在compile之后，并且他又生成了新的java类，codegen执行和后面的编译打包需要分开执行

## 运行

相关的测试脚本放到了 文件夹`src/test/java` 的 `pro.jiefzz.demo.uow.demo1.springtest.serviceTest` 包下;

