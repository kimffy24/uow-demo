# uow-demo

简单的演示 uow 和 uowgen-maven-plugin的使用

## 前置

### 1. 生成sql并导入数据库

使用以下命令执行1次codegen

```
cd uow-demo
mvn clean com.github.kimffy24:uowgen-maven-plugin:0.0.2.2:uow-gen -DoutputSpec=pro/jiefzz/demo/uowdemo
```

其中，`outputSpec=pro/jiefzz/demo/uowdemo` 可以按需指定为别的目录；

库的sql文件在 `src/main/java/pro/jiefzz/demo/uowdemo/uow-gen-all.sql` 里，自行导入数据库即可；

### 2. spring入口处加入注解 @MapperScan("pro.jiefzz.demo.uowdemo.uowgenroot")

包路径就是上一步中outputSpec指定的目录下一级别的uowgenroot文件夹。

## codegen过程简述

依靠outputSpec参数指定输出的根目录，codegen会在此目录下创建uowgenroot文件夹，并把内容输出到此。

相关的提示信息会也会输出到uowgenroot/package-info.java中

需要注意的是，
src/main/java的目录下回生成一个uow-gen-rbind-info.json的文件，
里面是UoW的mapper类和UoW聚合类绑定的信息，
暂时无法放到别的地方，也不支持通过参数改名。

## 运行

相关的测试脚本放到了 文件夹`src/test/java` 的 `pro.jiefzz.demo.uow.demo1.springtest.serviceTest` 包下;

