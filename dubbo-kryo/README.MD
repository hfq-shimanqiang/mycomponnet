dubbo-kryo是dubbo的一个扩展插件，主要是为了解决duboox2.8.4中kryo序列化问题

## 起由

 jedi项目内部模块和三方购买模块（会员、账务、清分），使用kryo协议序列化出错
 
## 扩展

1、 使用了最新稳定版本的kryo组件（4.0.0），4.0.0解决了对象依赖同名字段序列化错误

2、 扩展完全遵循dubbo的插件方式


## 如何使用
 
#### 以**Gradle**为例
 
 1、配置仓库
 
 ```$xslt
repositories {
    jcenter() {
        url "http://123.57.37.231:18081/artifactory/jedi-repo/"
    }
}
```
 
 
 2、 引用插件到项目
 
 ```$xslt

compile(group: 'com.huifenqi.jedi', name: 'dubbo-kryo', version: '1.0-SNAPSHOT')
```


 3、配置dubbo的协议为kryo2,可能是这个样子的
 
 ```$xslt

 <dubbo:protocol name="dubbo"
                 port="28080"
                 serialization="kryo2"/>
```
 
 