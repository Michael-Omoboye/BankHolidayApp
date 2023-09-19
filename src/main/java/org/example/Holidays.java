package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Holidays {
    public Regions regions;
    public HolidaysInfo holidaysInfo;

    public class HolidaysInfo {
        private String title;
        private String date;
        private String notes;
        private Boolean bunting;

        public String getDate() {
            return date;
        }

        public String getTitle() {
            return this.title;
        }


        public String getNotes() {
            return notes;
        }


        public Boolean getBunting() {
            return bunting;
        }

    }

    public class Regions {

        private String divisions;
        private List<String> holidaysInfo;

        @JsonProperty("england-and-wales")
        public Regions englandAndWales;
    }
}



