# interview
用于管理面试的信息及面试人员面试流程中的后续跟进，更加方便地管理面试信息。
***
## 目录
[提供的功能](#提供的功能)
[相关系统](#相关系统)

***
## 提供的功能

|功能名称|功能介绍|开发状态|
|---|---|---|
|面试信息管理初版|基本的面试信息的增、删、改、查|已经完成，持续完善中|
|柱状图展示面试信息|以柱状图的方式展示面试信息，可以比较方便地根据面试状态查看面试信息，以便合理地安排面试时间|已完成，持续完善中|
|多Tab页展示面试信息|面试信息字段越来越多，将面试信息按照基本信息、一面信息、二面信息等Tab页展示，可以更加方便在不同阶段操作|未开发|
|记录一个人的多次面试信息|现在系统中因为业务设置原因，一个人只能录入一个面试信息，应该支持记录一个面试人员在不同时期的面试信息|未开发|

***
## 相关系统

|系统代码|系统名称|系统介绍|技术架构|系统链接|
|---|---|---|---|---|
|sso|单点系统|对多个系统提供公共的登录校验、登录会话管理、用户权限校验功能|目前采用的是Struts2+Spring+Mybatis框架，未来计划改造为SpringCloud微服务|[yankj12/sso branch/ssoweb](https://github.com/yankj12/sso)|
|interview|面试管理系统|管理面试信息|目前采用的是Struts2+Spring+Mybatis框架，未来计划改造为动静分离+SpringCloud微服务|[yankj12/interview](https://github.com/yankj12/interview)|

***

## 功能设计

### 多Tab页展示面试信息
- 基本信息可以在一个tab页面
- 一面信息放在一个tab页
- 二面信息可以放在一个tab页

这样创建的时候大部分操作在基本信息tab，一面结束后更新一面状态和二面状态，在两个tab页面操作。每次操作每个tab页面字段都不会太多，比较容易找到对应字段。





