package com.hlyf.smg.domin;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.util.Date;

/**
 * Created by Administrator on 2019-08-08.
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class SMGStoreLocation {

    private Long lineId;

    private String openId;

    private String unionId;

    private String province;

    private String city;

    private String location;

    private String storeId;

    private String storeName;

    private String longitude;//维度

    private String latitude;//经度

    private Integer limitNumber;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Double distance;

    private String area;

    private String posName;

    private String posId;

    public SMGStoreLocation(String openId, String unionId, String province, String city, String location,
                            String storeId, String storeName, String longitude, String latitude, Integer limitNumber,String area) {
        this.openId = openId;
        this.unionId = unionId;
        this.province = province;
        this.city = city;
        this.location = location;
        this.storeId = storeId;
        this.storeName = storeName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.limitNumber = limitNumber;
        this.area = area;
    }

    public SMGStoreLocation(String openId, String unionId, String province, String city, String location,
                            String storeId, String storeName, String longitude, String latitude, Integer limitNumber) {
        this.openId = openId;
        this.unionId = unionId;
        this.province = province;
        this.city = city;
        this.location = location;
        this.storeId = storeId;
        this.storeName = storeName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.limitNumber = limitNumber;
    }
}
