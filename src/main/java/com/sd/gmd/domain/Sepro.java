package com.sd.gmd.domain;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class Sepro {

    private int pid;

    private int uid;

    private String pname;

    private BigDecimal price;

    private int total;

    private int sellnum;

    private int residue;

    private String describe;
}
