package com.example.test1.model.response;

public class ProfileResponse {
    private int code;
    private String msg;
    private DataProfile data;

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

    public DataProfile getData() {
        return data;
    }

    public void setData(DataProfile data) {
        this.data = data;
    }

    public class DataProfile{
        private String id;
        private String firstName;
        private String lastName;
        private String gender;
        private String email;
        private String phone;
        private String address;
        private long birthday;
        private String avatarUrl;
        private String coverUrl;
        private String description;
        private String identityCard;
        private String identityCardFrontImageUrl;
        private String identityCardBackImageUrl;
        private boolean isLookingForHouse;
        private String region;
        private boolean isProfileVerify;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public long getBirthday() {
            return birthday;
        }

        public void setBirthday(long birthday) {
            this.birthday = birthday;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getIdentityCard() {
            return identityCard;
        }

        public void setIdentityCard(String identityCard) {
            this.identityCard = identityCard;
        }
    }
}
