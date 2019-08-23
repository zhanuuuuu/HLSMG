/**

 */
IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE ID = OBJECT_ID(N'[dbo].[p_getSmgOrders]') and OBJECTPROPERTY(ID, N'IsProcedure') = 1)
BEGIN
	DROP PROCEDURE [dbo].[p_getSmgOrders]
END
GO
CREATE procedure [dbo].[p_getSmgOrders]
@openId varchar(64),   --小程序 opendId
@pageNum INT,          --页码
@Number  INT,          --每页显示条数
@iFlag  INT            --状态 0 未支付 1 支付了待出厂 2 已完成 3~ (3以上代表全部) 全部

AS
BEGIN

		DECLARE @AllNumber INT
		DECLARE @Sql VARCHAR(8000)

		IF @iFlag=0   --未支付
		BEGIN
		 SELECT * FROM tSMGGoodsInfo
		END
		ELSE

		IF @iFlag=1  --待出厂
		BEGIN
		 SELECT * FROM tSMGGoodsInfo
		END
		ELSE

		IF @iFlag=2  --已完成
		BEGIN
		 SELECT * FROM tSMGGoodsInfo
		END
		ELSE
		             --显示全部
		BEGIN
		 SELECT * FROM tSMGGoodsInfo
		END
END

GO

/*
    把数据插入到临时表中
    exec p_smgdatatoTemp '88201908211007540000000003918878','o_gYO5JnA-34K65x_kcxi25j4gMc','','88','posstation101.dbo.pos_SaleSheetDetailTemp'
*/
IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE ID = OBJECT_ID(N'[dbo].[p_smgdatatoTemp]') and OBJECTPROPERTY(ID, N'IsProcedure') = 1)
BEGIN
	DROP PROCEDURE [dbo].[p_smgdatatoTemp]

END
GO
CREATE PROC [dbo].[p_smgdatatoTemp]
 @merchantOrderId varchar(80),
 @openId varchar(80),
 @cVipNo varchar(80),
 @cPosID VARCHAR(80),
 @tableName VARCHAR(80)
 AS
BEGIN

  DECLARE @SQL VARCHAR(8000)
  SET @SQL = ' DELETE '+@tableName+ ' WHERE cSaleSheetno_time='''+@merchantOrderId+'''
				INSERT INTO '+@tableName+'
					(bWeight,cStoreNo,cPosID,cSaleSheetno_time,iSeed,cGoodsNo,cGoodsName,cBarcode,cOperatorno,cOperatorName,bAuditing,
					 fPrice,fVipPrice,fQuantity,fLastSettle,fLastSettle0,dSaleDate,
					 cSaleTime,dFinanceDate,cVipNo,fPrice_exe,bSettle,bVipPrice,fVipRate,fNormalSettle )

        SELECT isWeight,storeId,'''+@cPosID+''',merchantOrderId,lineId,cGoodsNo,cGoodsName,barcode,'''+@cPosID+''','''+@cPosID+''',bAuditing=0,
						basePrice AS  fPrice,basePrice AS  fVipPrice,fQuantity=(CASE  WHEN isWeight=0 THEN qty ELSE weight  END),
					 amount AS fLastSettle,amount AS fLastSettle0,
					 convert(varchar(10),getdate(),23), cSaleTime=convert(varchar(10),getdate(),108),convert(varchar(10),getdate(),23),'''+@cVipNo+''',
					 basePrice AS  fPrice_exe,bSettle=0,bVipPrice=0,100,amount AS  fNormalSettle  FROM tSMGGoodsInfo
       WHERE openId='''+@openId+''' AND orderStatus=0 AND merchantOrderId='''+@merchantOrderId+''' '

  PRINT(@SQL)
  EXEC(@SQL)
END

GO

--动态调用过程获取数据的过程
--EXEC p_ProcessPosSheetSmg_Z '0002','02','2019043012011993595','13628672210',100,0,'posstation101.dbo.p_ProcessPosSheetSMG'
IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE ID = OBJECT_ID(N'[dbo].[p_ProcessPosSheetSmg_Z]') and OBJECTPROPERTY(ID, N'IsProcedure') = 1)
BEGIN
	DROP PROCEDURE [dbo].[p_ProcessPosSheetSmg_Z]

END
GO
CREATE procedure [dbo].[p_ProcessPosSheetSmg_Z]
@cStoreNo varchar(32),
@cPosID varchar(32),
@cSaleSheetNo varchar(32),
@cVipNo varchar(32),
@fVipRate varchar(32),
@bDiscount varchar(32),
@callName VARCHAR(80)
AS
BEGIN
  DECLARE @SQLWHERE VARCHAR(800)

  SET  @SQLWHERE = 'EXEC '+@callName+' '''+@cStoreNo+''','''
                          +@cPosID+''','''+@cSaleSheetNo+''','''
                          +@cVipNo+''','''+@fVipRate+''','+@bDiscount

  PRINT(@SQLWHERE)

  EXEC (@SQLWHERE)
END

GO