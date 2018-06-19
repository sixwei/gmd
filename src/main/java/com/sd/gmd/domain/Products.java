package com.sd.gmd.domain;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class Products {

    private Integer pid;

    private String bname;

    private Integer bid;

    private String pname;

    private BigDecimal price;

    private String pdescribe;

    private Integer total;

    private Integer sellnumber;

    private Integer residue;

    private Integer uid;
}
