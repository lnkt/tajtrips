package com.tajway.tajwaycabs.Activities;


/**
 * Created by Rahul Hooda on 14/7/17.
 */


public enum AppEnvironment {

    SANDBOX {
        @Override
        public String merchant_Key() {
            return "3uj3A3Eg";
        }

        @Override
        public String merchant_ID() {
            return "7290629";
        }

        @Override
        public String furl() {
            return "http://avaskm.jhojhu.com/tajtripcars/api/fail-url";
        }

        @Override
        public String surl() {
            return "http://avaskm.jhojhu.com/tajtripcars/api/success-url";
        }

        @Override
        public String salt() {
            return "wRJvwdM2gq";
        }

        @Override
        public boolean debug() {
            return true;
        }
    },
    PRODUCTION {
        @Override
        public String merchant_Key() {
            return "3uj3A3Eg";
        }
        @Override
        public String merchant_ID() {
            return "7290629";
        }
        @Override
        public String furl() {
            return "http://avaskm.jhojhu.com/tajtripcars/api/fail-url";
        }

        @Override
        public String surl() {
            return "http://avaskm.jhojhu.com/tajtripcars/api/success-url";
        }

        @Override
        public String salt() {
            return "wRJvwdM2gq";
        }

        @Override
        public boolean debug() {
            return false;
        }
    };

    public abstract String merchant_Key();

    public abstract String merchant_ID();

    public abstract String furl();

    public abstract String surl();

    public abstract String salt();

    public abstract boolean debug();


}

