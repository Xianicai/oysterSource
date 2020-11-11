package com.oystersource.common

class Urls {
    companion object {
        /**
         * H5测试环境域名
         */
        const val WEB_TEST_BASE_URL = "https://app-test.au32.cn/"

        /**
         * H5正式环境域名
         */
        const val WEB_FORMAL_BASE_URL = "https://app.au32.cn/"

        /**
         * APP测试环境域名
         */
        const val APP_TEST_BASE_URL = "http://152.136.155.163:8088/"

        /**
         * APP正式环境域名
         */
        const val APP_FORMAL_BASE_URL = "http://152.136.155.163:8088/"


        //**************************     App接口Url     ******************************//
        /**
         * 用户注册
         */
        const val POST_USER_REGISTRY = "api/registry"

        /**
         * 账号登录
         */
        const val ACCOUNT_LOGIN = "api/login"
        /**
         * 用户详情
         */
        const val GET_USER_DETAIL = "api/farmer/getFarmerById"

        /**
         * 生蚝
         */
        const val POST_ADD_ADDOYSTER = "api/oyster/addOyster"

        /**
         * 上传图片
         */
        const val POST_UPLOAD_FILE = "api/file/upload"

        /**
         * 下苗
         */
        const val POST_ADD_OPERATE = "api/operate/addOperate"
        /**
         * 增加确权人
         */
        const val POST_ADD_FARMER= "api/farmer/addFarmer"
        /**
         * 获取溯源信息
         */
        const val GET_SOURCE_INFO= "api/farmer/getAllState"

    }
}

