package com.sd.gmd.domain;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class Items {

    private Integer iid;

    private Integer pid;

    private Integer uid;

    private Integer count;

    private String pname;

    private BigDecimal price;


}
