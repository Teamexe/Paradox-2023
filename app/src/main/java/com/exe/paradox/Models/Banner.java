package com.exe.paradox.Models;

public class Banner {
        public Banner() {
        }

        private String redirectLink, _id, imageUrl, text;


        public String getRedirectUrl() {
            return redirectLink;
        }

        public void setRedirectUrl(String redirectUrl) {
            this.redirectLink = redirectUrl;
        }


        public String getId() {
            return _id;
        }

        public void setId(String id) {
            this._id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public Banner(String id, String imageUrl, String redirectUrl, String text) {
            this._id = id;
            this.imageUrl = imageUrl;
            this.redirectLink = redirectUrl;
            this.text = text;
        }
}
