package com.hlyf.smg.domin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2019-08-07.
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain=true)
public class OfflineStore {

    private String cStoreNo;
    private String cStoreName;
}
