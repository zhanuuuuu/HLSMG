/*
     购物车  订单表
 */
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[tSMGGoodsInfo]')
                      AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
          DROP TABLE [dbo].[tSMGGoodsInfo]
           CREATE TABLE tSMGGoodsInfo(
                  --下面是商品的基础字段
                  lineId   BIGINT IDENTITY(1,1),  --行号
                  openId VARCHAR(64),
                  unionId VARCHAR(64),
                  storeId VARCHAR(64),
                  merchantOrderId VARCHAR(64),   --我们的订单号
                  payOrderId VARCHAR(64),        --小程序的支付单号（线下支付,微信支付,电子卡支付）
                  orderStatus int DEFAULT 0,     --订单状态  0 待支付订单 1 已经支付待出厂订单 2 已完成订单（经收银员检查订单无疑问）
                  orderType   int DEFAULT 0,     --结算方式 0 微信支付 1移动POS支付  2 前台pos支付
                  cGoodsNo VARCHAR(30),
                  cGoodsName VARCHAR(100),
                  amount MONEY,
                  discountAmount MONEY,
                  basePrice MONEY DEFAULT 0,
                  price MONEY DEFAULT 0,
                  qty INT DEFAULT 1,
                  weight MONEY DEFAULT 0,
                  isWeight bit DEFAULT 0,      --是否称重商品 0 非称重  1 称重
                  barcode VARCHAR(30),
                  unit VARCHAR(20),
                  createTime DATETIME DEFAULT (GETDATE()),  --订单创建的时间
                  payedTime DATETIME   DEFAULT (GETDATE()), --支付时间
                  checkUpNo VARCHAR(64) DEFAULT  '',       --记录核销人员编号
                  checkUpName VARCHAR(64) DEFAULT  '',      --记录核销人员名称
                  iscommit   int DEFAULT 0,    --未提交 0 提交 1 （给线下增加提取字段）
                  receivingCode VARCHAR(30) DEFAULT  '',
                  actualAmount MONEY DEFAULT 0,   --实际付款金额
                  showTime DATETIME DEFAULT (GETDATE()),  --订单上面显示的时间
                  primary key(lineId,merchantOrderId)   --联合主键
              )
/*
     超市门店位置表（主要保存门店编号 门店名称 门店地理位置,小程序每单限购数量（））
 */
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[tSMGStoreLocation]')
                      AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
          DROP TABLE [dbo].[tSMGStoreLocation]
           CREATE TABLE tSMGStoreLocation(
                  --下面是商品的基础字段
                  lineId   BIGINT IDENTITY(1,1),  --行号
                  openId VARCHAR(64),
                  unionId VARCHAR(64),
                  province    VARCHAR(64),
                  city    VARCHAR(64),
                  area    VARCHAR(64),
                  location    VARCHAR(100),          --具体位置
                  storeId VARCHAR(64),
                  storeName VARCHAR(64),
                  longitude VARCHAR(64),         --经度
                  latitude VARCHAR(64),          --维度
                  limitNumber INT DEFAULT 1000,          --每单限购多少必须去收银台结账
                  createTime DATETIME DEFAULT (GETDATE()),
                  updateTime DATETIME DEFAULT (GETDATE()),  --修改时间
                  tel VARCHAR(20)
                  primary key(lineId,storeId)
              )

/*
     线上钱包表
 */
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[tSMGMoneyOnline]')
                      AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
          DROP TABLE [dbo].[tSMGMoneyOnline]
           CREATE TABLE tSMGMoneyOnline(
                  --下面是商品的基础字段
                  lineId   BIGINT IDENTITY(1,1),  --行号
                  openId VARCHAR(64),
                  unionId VARCHAR(64),
                  storeId VARCHAR(64),
                  amount MONEY,                   --当前金额
                  malt   VARCHAR(64),             --随机生成的盐
                  moneySign VARCHAR(100)          --金额对比加密的字符串是否一致
                  createTime DATETIME DEFAULT (GETDATE())  --
                  primary key(lineId)
              )
/*
    线上钱包消费充值记录表
 */
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[tSMGMoneySaleLog]')
                      AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
          DROP TABLE [dbo].[tSMGMoneySaleLog]
           CREATE TABLE tSMGMoneySaleLog(
                  lineId   BIGINT IDENTITY(1,1),  --行号
                  openId VARCHAR(64),
                  unionId VARCHAR(64),
                  storeId VARCHAR(64),
                  merchantOrderId VARCHAR(64),   --我们的订单号(消费)  如果是充值(充值的单号)
                  payOrderId VARCHAR(64),         --哆啦宝支付单据
                  amount MONEY,                    --消费（或）的金额
                  saleStatus INT  DEFAULT 0,      --默认0 消费 1 充值
                  payType VARCHAR(32),             --消费或支付方式
                  saleTime DATETIME DEFAULT (GETDATE())   --消费（或充值）的时间
                  primary key(lineId,merchantOrderId)
              )

/*
     支付配置表
*/
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[tSMGPayConfig]')
            AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
DROP TABLE [dbo].[tSMGPayConfig]
 CREATE TABLE tSMGPayConfig(
        lineId   BIGINT IDENTITY(1,1),  --行号
        accesskey VARCHAR(100),
        secretkey VARCHAR(100),
        agentnum VARCHAR(64),
        customernum VARCHAR(64),
        sn VARCHAR(64),                  --哆啦宝的机器号
        machinenum VARCHAR(64),
        shopnum VARCHAR(64),
        storeId VARCHAR(64),
        createTime DATETIME DEFAULT (GETDATE())
        primary key(storeId,sn)
)

--配置表
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[tSMGPosConfiguration]')
            AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
DROP TABLE [dbo].[tSMGPosConfiguration]
 CREATE TABLE tSMGPosConfiguration(
        storeId VARCHAR(64),
        --下面是商品的基础字段
        posName VARCHAR(64), --库名
        posid  VARCHAR(20), --对应我们的 posid  前台收银编号
        beizhu  VARCHAR(64)
        primary key(storeId)
)
--用户表
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[tSMGUsers]')
            AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
DROP TABLE [dbo].[tSMGUsers]
 CREATE TABLE tSMGUsers(
        lineId   BIGINT IDENTITY(1,1),  --行号
        openId VARCHAR(64),
        unionId VARCHAR(64),
        userTel VARCHAR(20),                --电话号码  只有授权电话的才可以购物
        administration INT  DEFAULT 0,     --默认0 是否有权操作门店位置登记
        formId  VARCHAR(100),               --推送用的
        createTime DATETIME DEFAULT (GETDATE())
        primary key(openId)
)

--问题反馈表  每人每天只能反映一个问题
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[tSMGProblems]')
            AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
DROP TABLE [dbo].[tSMGProblems]
 CREATE TABLE tSMGProblems(
        lineId   BIGINT IDENTITY(1,1),  --行号
        openId VARCHAR(64),
        unionId VARCHAR(64),
        userTel VARCHAR(20),            --电话号码  只有授权电话的才可以购物
        problemType VARCHAR(64),        --问题类别
        description VARCHAR(200),       --问题描述
        imageUrls   VARCHAR(150),
        createTime DATETIME DEFAULT (GETDATE())
        primary key(lineId,openId)
)

--常见问题
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[tSMGCommonProblems]')
            AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
DROP TABLE [dbo].[tSMGCommonProblems]
 CREATE TABLE tSMGCommonProblems(
        lineId   BIGINT IDENTITY(1,1),   --行号
        problemTitle VARCHAR(64),       --问题Title
        description VARCHAR(600),       --问题阐述 用,隔开
        createTime DATETIME DEFAULT (GETDATE())
        primary key(lineId)
)
--formId 表
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[tSMGFormIds]')
            AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
DROP TABLE [dbo].[tSMGFormIds]
 CREATE TABLE tSMGFormIds(
        lineId   BIGINT IDENTITY(1,1),  --行号
        openId VARCHAR(64),
        formId VARCHAR(20),
        createTime DATETIME DEFAULT (GETDATE())
        primary key(lineId,openId)
)
