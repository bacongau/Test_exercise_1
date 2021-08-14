package com.example.test1.model.response;

public class LoginResponse {
    private int code;
    private String msg;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data {
        private String id;
        private String accessToken;
        private String refreshToken;
        private int expireIn;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public int getExpireIn() {
            return expireIn;
        }

        public void setExpireIn(int expireIn) {
            this.expireIn = expireIn;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", accessToken='" + accessToken + '\'' +
                    ", refreshToken='" + refreshToken + '\'' +
                    ", expireIn=" + expireIn +
                    '}';
        }
    }
}
