//package com.example.eugene.testing;

import android.provider.BaseColumns;

public final class MEMContract {

    private MEMContract() {}

    public static class Attendee implements BaseColumns {
        public static final String TABLE_NAME = "ATTENDEE";
        public static final String CUSTOMER_ID = "CUSTOMER_ID";
        public static final String MEETING_ID = "MEETING_ID";
        public static final String DISCOUNT_PERCENT = "DISCOUNT_PERCENT";
    }

    public static class Customer implements BaseColumns {
        public static final String TABLE_NAME = "CUSTOMER";
        public static final String NAME = "NAME";
        public static final String CONSECUTIVE_PAYMENT = "CONSECUTIVE_PAYMENT";
    }

    public static class Meeting implements BaseColumns {
        public static final String TABLE_NAME = "MEETING";
        public static final String DATE = "DATE";
        public static final String ORGANIZER = "ORGANIZER";
        public static final String RATE = "RATE";
        public static final String HALL_ID = "HALL_ID";
    }

    public static class Payment implements BaseColumns {
        public static final String TABLE_NAME = "PAYMENT";
        public static final String ATTENDEE_ID = "ATTENDEE_ID";
        public static final String DATE = "DATE";
        public static final String AMOUNT = "AMOUNT";
    }

    public static class Hall implements BaseColumns {
        public static final String TABLE_NAME = "HALL";
        public static final String NAME = "NAME";
        public static final String RATE = "RATE";
    }

    public static class Coach implements BaseColumns {
        public static final String TABLE_NAME = "COACH";
        public static final String NAME = "NAME";
        public static final String RATE = "RATE";
    }
}