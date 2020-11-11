package com.oystersource.utils

import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object StringUtil {

    private val dateFormater = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        }
    }

    private val dateFormater2 = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("yyyy-MM-dd")
        }
    }


    val PATTERN_URL =
        Pattern.compile("(http://|https://|www\\.)[^\\u4e00-\\u9fa5,^\\s,^\\[,^\\]]+|[^\\u4e00-\\u9fa5,^\\s,^\\[,^\\]]+(.com|.cn|.org|.info|.net|.gov|.edu)[^\\u4e00-\\u9fa5,^\\s,^\\[,^\\]]*")

    /**
     * @param time 毫秒
     * @return
     */

    fun convertTime(time: Long): String {

        //秒
        val s = (time / 1000).toInt()
        //分
        val m = s / 60
        //小时
        val h = m / 60
        //天
        val d = h / 24
        //月
        val M = d / 30
        //年
        val y = M / 12

        if (y > 0) {
            return y.toString() + "年"
        }
        if (M > 0) {
            return M.toString() + "个月"
        }
        if (d > 0) {
            return d.toString() + "天"
        }
        if (h > 0) {
            return h.toString() + "小时"
        }
        if (m > 0) {
            return m.toString() + "分"
        }
        return if (s > 0) {
            s.toString() + "秒"
        } else time.toString() + ""
    }

    /**
     * 判断是否为空串（会对字符串进行trim()）
     *
     * @param str 需要判断的 字符串
     */
    fun isBlank(str: String?): Boolean {
        return str == null || "" == str.trim { it <= ' ' }
    }

    /**
     * 判断是否为空串（会对字符串进行trim()）
     *
     * @param str 需要判断的 字符串
     * @return true不为空；false为""或者null
     */
    fun isNotBlank(str: String?): Boolean {
        return str != null && "" != str.trim { it <= ' ' }
    }

    /**
     * 判断多个字符串是否都不为空
     * @param strings
     * @return
     */
    fun areAllNotBlank(vararg strings: String): Boolean {
        if (strings == null) {
            return false
        }
        for (s in strings) {
            if (isBlank(s)) {
                return false
            }
        }
        return true
    }

    /**
     * 判断是否为空串（不会对字符串进行trim()）
     *
     * @param str 需要判断的 字符串
     * @return true为""或者null
     */
    fun isEmpty(str: String?): Boolean {
        return str == null || "" == str
    }

    /**
     * 判断是否为空串（不会对字符串进行trim()）
     *
     * @param str 需要判断的 字符串
     * @return true不为空；false为""或者null
     */
    fun isNotEmpty(str: String?): Boolean {
        return str != null && "" != str
    }

    fun startWith(str: String?, prefix: String?): Boolean {
        return !(str == null || prefix == null) && str.startsWith(prefix)
    }

    fun isHttp(url: String): Boolean {
        return startWith(url, "http")
    }

    /**
     * 获取文件/路径的后缀名
     */
    fun getExt(str: String): String? {
        return if (isBlank(str)) {
            null
        } else str.substring(str.lastIndexOf("."), str.length - 1)
    }


    /**
     * 判断一个String是否为小数
     *
     * @param str 要判断的String串
     * @return 是小数返回true，否则返回false;
     */
    fun isDecimal(str: String): Boolean {
        if (isBlank(str)) {
            return false
        }
        try {
            val p = Pattern.compile("[0-9]+\\.{1}[0-9]+")
            val m = p.matcher(str)
            if (m.matches()) {
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     */
    fun getStringWithoutEnter(str: String?): String {
        var dest = ""
        if (str != null) {
            val p = Pattern.compile("\\s*|\t|\r|\n")
            val m = p.matcher(str)
            dest = m.replaceAll("")
        }
        return dest
    }

    /**
     * 将数字保留两位小数
     */
    fun parseNumber(number: Double): String {
        val df = DecimalFormat("#0.00")
        return df.format(number)
    }

    /**
     * 将数组转换成字符串，并将在中间插入分隔符
     *
     * @param arr       数组
     * @param separator 分隔符
     * @return
     */
    @JvmOverloads
    fun join(arr: Array<Any>?, separator: String = ","): String {
        if (arr != null && isNotBlank(separator)) {
            val sb = StringBuilder()
            for (obj in arr) {
                if (obj != null) {
                    sb.append(separator)
                    sb.append(obj)
                }
            }
            if (sb.length > 0) {
                return sb.substring(separator.length)
            }
        }
        return ""
    }


    fun ToDBC(input: String): String {
        val c = input.toCharArray()
        for (i in c.indices) {
            if (c[i].toInt() == 12288) {
                c[i] = 32.toChar()
                continue
            }
            if (c[i].toInt() > 65280 && c[i].toInt() < 65375)
                c[i] = (c[i].toInt() - 65248).toChar()
        }
        return String(c)
    }

    /**
     * 1.str1 == null || str2 == null，retrun false<br></br>
     * 2.str1 == str2，return true<br></br>
     * else.return str1.equals(str2)
     */
    fun equals(str1: String?, str2: String?): Boolean {
        if (str1 == null || str2 == null) {
            return false
        }
        return if (str1 === str2) {
            true
        } else str1 == str2
    }

    /**
     * 统计字符串长度，可以统计中文 中文为2个字符，英文为1个字符
     */
    fun countUTF8StringLength(str: String): Int {
        var m = 0
        val arr = str.toCharArray()
        for (i in arr.indices) {
            val c = arr[i]
            if (c.toInt() >= 0x0391 && c.toInt() <= 0xFFE5)
            // 中文字符
            {
                m = m + 2
            } else if (c.toInt() >= 0x0000 && c.toInt() <= 0x00FF)
            // 英文字符
            {
                m = m + 1
            }
        }
        return m
    }

    /**
     * 判断str是否为空，为空则返回def；否则返回str
     */
    @JvmOverloads
    fun getString(str: String, def: String? = null): String? {
        var def = def
        if (isNotBlank(str)) {
            return str
        }
        if (isBlank(def)) {
            def = ""
        }
        return def
    }

    /**
     * 截取" 字节"的方法,注意 不是字符截取
     */
    fun getSubString(str: String, pstart: Int, pend: Int): String? {
        var resu: String? = ""
        var beg = 0
        var end = 0
        var count1 = 0
        val temp = CharArray(str.length)
        str.toCharArray(temp, 0, 0, str.length)
        val bol = BooleanArray(str.length)
        for (i in temp.indices) {
            bol[i] = false
            if (temp[i].toInt() > 255) {// 说明是中文
                count1++
                bol[i] = true
            }
        }
        if (pstart > str.length + count1) {
            resu = null
        }
        if (pstart > pend) {
            resu = null
        }
        if (pstart < 1) {
            beg = 0
        } else {
            beg = pstart - 1
        }
        if (pend > str.length + count1) {
            end = str.length + count1
        } else {
            end = pend// 在substring的末尾一样
        }
        // 下面开始求应该返回的字符串
        if (resu != null) {
            if (beg == end) {
                var count = 0
                if (beg == 0) {
                    if (bol[0] == true)
                        resu = null
                    else
                        resu = String(temp, 0, 1)
                } else {
                    var len = beg// zheli
                    for (y in 0 until len) {// 表示他前面是否有中文,不管自己
                        if (bol[y] == true)
                            count++
                        len--// 想明白为什么len--
                    }
                    // for循环运行完毕后，len的值就代表在正常字符串中，目标beg的上一字符的索引值
                    if (count == 0) {// 说明前面没有中文
                        if (temp[beg].toInt() > 255)
                        // 说明自己是中文
                            resu = null// 返回空
                        else
                            resu = String(temp, beg, 1)
                    } else {// 前面有中文，那么一个中文应与2个字符相对
                        if (temp[len + 1].toInt() > 255)
                        // 说明自己是中文
                            resu = null// 返回空
                        else
                            resu = String(temp, len + 1, 1)
                    }
                }
            } else {// 下面是正常情况下的比较
                var temSt = beg
                var temEd = end - 1// 这里减掉一
                for (i in 0 until temSt) {
                    if (bol[i] == true)
                        temSt--
                } // 循环完毕后temSt表示前字符的正常索引
                for (j in 0 until temEd) {
                    if (bol[j] == true)
                        temEd--
                } // 循环完毕后temEd-1表示最后字符的正常索引
                if (bol[temSt] == true)
                // 说明是字符，说明索引本身是汉字的后半部分，那么应该是不能取的
                {
                    var cont = 0
                    for (i in 0..temSt) {
                        cont++
                        if (bol[i] == true)
                            cont++
                    }
                    if (pstart == cont)
                    // 是偶数不应包含,如果pstart<cont则要包含
                        temSt++// 从下一位开始
                }
                if (bol[temEd] == true) {// 因为temEd表示substring
                    // 的最面参数，此处是一个汉字，下面要确定是否应该含这个汉字
                    var cont = 0
                    for (i in 0..temEd) {
                        cont++
                        if (bol[i] == true)
                            cont++
                    }
                    if (pend < cont)
                    // 是汉字的前半部分不应包含
                        temEd--// 所以只取到前一个
                }
                if (temSt == temEd) {
                    resu = String(temp, temSt, 1)
                } else if (temSt > temEd) {
                    resu = null
                } else {
                    resu = str.substring(temSt, temEd + 1)
                }
            }
        }
        return resu// 返回结果
    }

    /**
     * 去除特殊字符，首行缩进 ："\\s*|\t|\r|\n" 有问题
     *
     * @param str
     * @return
     */
    fun replaceBlank(str: String?): String {
        var dest = ""
        if (str != null) {
            val p = Pattern.compile("\\s*|\t|\r|\n")
            val m = p.matcher(str)
            dest = m.replaceAll("")
        }
        return dest
    }

    /**
     * 将两个字符串拼成一个字符串（会将null换成""）
     */
    fun append(str1: String?, str2: String?): String {
        return (str1 ?: "") + (str2 ?: "")
    }

    /**
     * @param str
     * @param maxLength 要显示的字的个数
     * @return
     */
    fun ellipsizeEnd(str: String, maxLength: Int): String? {
        if (isBlank(str)) {
            return str
        }
        return if (str.length <= maxLength) {
            str
        } else {
            str.substring(0, maxLength) + "..."
        }
    }

    /**
     * 讲大于等于0的double转成带有两位小数的string
     */
    fun doubleToString(d: Double): String {
        var s = "0.00"
        if (d >= 0) {
            val df = DecimalFormat("######0.00")
            s = df.format(d)
        }
        return s
    }

    /**
     * 在url后面拼接参数，假如url中带有?则拼"&params"，否则拼"?params"
     */
    fun appendParams(url: String, params: String): String {
        var url = url
        if (url.contains("?")) {
            url += "&"
        } else {
            url += "?"
        }
        url += params
        return url
    }

    fun isUrl(input: String?): Boolean {
        if (input == null) {
            return false
        }
        val mAtMatcher = PATTERN_URL.matcher(input)
        while (mAtMatcher.find()) {
            return true
        }
        return false
    }

    fun decode(str: String): String {
        var str = str
        try {
            str = str.replace("%(?![0-9a-fA-F]{2})".toRegex(), "%25")
            str = str.replace("\\+".toRegex(), "%2B")
            return URLDecoder.decode(str, "utf-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return ""
    }

    fun encode(str: String): String {
        try {
            if (isNotBlank(str)) {
                return URLEncoder.encode(str, "utf-8")
            }
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * 使json字符串规范化，去掉引号、反斜杠、\r、\n
     */
    fun jsonCanonicalize(json: String): String {
        var json = json
        if (isBlank(json)) {
            return "{}"
        }
        json = json.replace("\\", "\\\\")   // 替换反斜杠
            .replace("\r", "\\\r")      // 替换\r
            .replace("\n", "\\\n")     // 替换\n
        // 替换value中的双引号，正则表达式最后的}前面的\\是转义符，不转义的话在Java环境可以通过，但Android环境会报错
        val pattern = Pattern.compile("(?<=:\\s?\").*?(?=\"\\s?,|\"\\s?})")
        val matcher = pattern.matcher(json)
        while (matcher.find()) {
            val target = matcher.group()
            json = json.replace(target, target.replace("\"", "\\\""))
        }
        return json
    }


    /**
     * 提取中括号中内容，忽略中括号中的括号
     * @param msg
     * @return
     */
    fun extractMessage(msg: String): List<String> {

        val list = ArrayList<String>()
        var start = 0
        var startFlag = 0
        var endFlag = 0
        for (i in 0 until msg.length) {
            if (msg[i] == '(') {
                startFlag++
                if (startFlag == endFlag + 1) {
                    start = i
                }
            } else if (msg[i] == ')') {
                endFlag++
                if (endFlag == startFlag) {
                    list.add(msg.substring(start + 1, i))
                }
            }
        }
        return list
    }
}
