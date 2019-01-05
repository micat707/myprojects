# 实现背景
目前很多项目组都会进行每日构建，而每日构建的很重要的一个环节就是跑单元测试。
在每日构建发布前，会看其对应的单元测试是否都通过了，如果存在未通过的情况，则会导致每日构建发布失败。
导致单元测试失败的原因除了代码相关修改之外，另一个最主要的就是第三方接口不稳定导致。
第三方接口可以理解为调用其它的服务、数据库访问等操作。
因而，为了保证每日构建的成功率，需要对第三方接口进行mock操作。

传统单元测试mock框架在使用过程中需要构建输入参数及写相应的返回参数。
对于比较复杂的第三方接口的输入及返回，如果通过手工去构建，必将会花费大量的时间，同时也容易出错。

rpmock即通过record（将返回结果写入文件）、 replay（读取文件内容，序列化为对象）的方式进行mock。本mock框架从简化mock构造数据的角度出发，设计了一个可行并且便于使用的mock框架。
不管第三方接口的入参及返回结果有多复杂，都可以很快的构造出理想的返回结果。
基于此框架进行mock单元测试，只需要写很少的代码，便可以对所有的第三方接口进行mock，
大大地简化了开发人员的工作。另外，开发人员也可以很方便修改mock结果中的内容，
以便构造其它不同的第三方接口返回。
# 实现原理
（1）对spring bean 的field进行检查，看是否有需要mock的field（通过ismock判断）。

（2）如果有，则进行mock操作。如果没有，则判断是否(通过judgeNeedContinue判断)需要递归检查该field是否有需要mock的字段。

（3）对需要mock的字段进行mock。

# 使用方式
本mock适用于对使用spring 管理的bean进行mock。
## 实现JsonRpMockHelper的子类
参考BaiduRpMock的实现。其中，需要实现isNeedMock及judgeNeedContinue方法。

其中：

（1）isNeedMock方法如果返回为true ,则说明该字段的调用需要mock。

（2）judgeNeedContinue 如果返回为true，则说明需要递归检查是否还有需要mock的字段。

## 在单元测试中进行mock的使用
参考TestProxyMock的使用方式。概括可为:

（1）对JsonRpMockHelper的子类实例化。比如：BaiduRpMock baiduRpMock = new BaiduRpMock();

（2）利用子类对业务对象进行mock。只需要传最外层的 bean 业务对象即可。

（3）利用mock处理后的bean进行单元测试。先通过RECORD方式调用，得到正常的测试场景，再通过REPLAY方式回放即可。
