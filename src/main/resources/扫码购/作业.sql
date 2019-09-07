--作业  执行
DELETE tSMGGoodsInfo
WHERE orderStatus=0 AND datediff(hh,showTime,GETDATE())>6
--作业  执行
UPDATE tSMGGoodsInfo
SET orderStatus=2
WHERE orderStatus=1 AND datediff(hh,showTime,GETDATE())>6