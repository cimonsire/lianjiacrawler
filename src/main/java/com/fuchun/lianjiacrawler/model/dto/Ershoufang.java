package com.fuchun.lianjiacrawler.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Ershoufang {

    private float price;

    private Room room;

    private Type type;

    private String communityName;

    private Location location;

    private String lianjiaId;

    private Base base;

    private Transaction transaction;

    @Data
    public static class Room {

        private String mainInfo;

        private String subInfo;
    }

    @Data
    public static class Type {

        private String mainInfo;

        private String subInfo;
    }

    @Data
    public static class Location {

        private String district;

        private String road;

        private String supplement;
    }

    @Data
    public static class Base {

        private String houseType;

        private String floor;

        private float grossArea;

        private float teachingArea;

        private String structure;

        private String buildingType;

        private String orientation;

        private String buildingStructure;

        private String decoration;

        private String ladderHouseholdProportion;

        private String elevator;

        private float propertyRight;
    }

    @Data
    public static class Transaction {

        private Date saleDate;

        private String tradingOwnership;

        private Date lastTransactionDate;

        private String houseUsage;

        private String serviceLife;

        private String propertyOwn;

        private String mortgage;
    }
}
