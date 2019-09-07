/*
    根据  订单号 opendid 订单支付状态 查询订单详情
    --0 未支付 1 待出场  2已完成  默认是0
    exec p_getSmgOrderDetailPos '88201909061026010000000007299108','o_gYO5DtBdNax5Eod-5mr0UCS6jo',2
*/
IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE ID = OBJECT_ID(N'[dbo].[p_getSmgOrderDetailPos]') and OBJECTPROPERTY(ID, N'IsProcedure') = 1)
BEGIN
	DROP PROCEDURE [dbo].[p_getSmgOrderDetailPos]

END
GO
CREATE PROC [dbo].[p_getSmgOrderDetailPos]
 @merchantOrderId varchar(80),  --单号
 @openId varchar(80),           --opendId
 @orderStatus int=0             --0 未支付 1 待出场  2已完成
 AS
BEGIN

    SELECT lineId,openId,unionId,storeId,merchantOrderId,payOrderId,orderStatus,orderType,
            cGoodsNo,cGoodsName,amount,discountAmount,basePrice,price,qty,
            weight,isWeight,barcode,unit,createTime,payedTime,checkUpNo,
            checkUpName,iscommit,receivingCode,actualAmount,showTime
    FROM tSMGGoodsInfo
    WHERE openId=@openId AND merchantOrderId=@merchantOrderId AND orderStatus=@orderStatus

END

GO