package org.framework.common.util.num;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public final class DataUtils {

    private DataUtils() {
    }

    private static final BigDecimal ONE = new BigDecimal("1");
    private static Map<Class, String> supportTypeMap = new HashMap();

    static {
        supportTypeMap.put(Integer.class, "");
        supportTypeMap.put(Long.class, "");
        supportTypeMap.put(Double.class, "");
        supportTypeMap.put(Byte.class, "");
        supportTypeMap.put(Character.class, "");
        supportTypeMap.put(Short.class, "");
        supportTypeMap.put(Float.class, "");
        supportTypeMap.put(Boolean.class, "");
        supportTypeMap.put(Integer.TYPE, "");
        supportTypeMap.put(Long.TYPE, "");
        supportTypeMap.put(Double.TYPE, "");
        supportTypeMap.put(byte[].class, "");
        supportTypeMap.put(Character.TYPE, "");
        supportTypeMap.put(Short.TYPE, "");
        supportTypeMap.put(Float.TYPE, "");
        supportTypeMap.put(Boolean.TYPE, "");

        supportTypeMap.put(Date.class, "");
        supportTypeMap.put(BigDecimal.class, "");
        supportTypeMap.put(String.class, "");
    }

    public static void addSupportType(Class clazz) {
        supportTypeMap.put(clazz, "");
    }

    public static String zeroToEmpty(int i) {
        return i == 0 ? "" : String.valueOf(i);
    }

    public static String zeroToEmpty(double d) {
        return d == 0.0D ? "" : String.valueOf(d);
    }

    public static String nullToEmpty(String str) {
        return str == null ? "" : str;
    }

    public static String emptyToNull(String str) {
        if (str == null) {
            return null;
        }
        if (str.trim().length() == 0) {
            return null;
        }
        return str;
    }

    public static String dbNullToEmpty(String str) {
        if ((str == null) || (str.equalsIgnoreCase("null"))) {
            return "";
        }
        return str;
    }

    public static String nullToZero(String str) {
        if ((str == null) || (str.trim().length() == 0)) {
            return "0";
        }
        return str;
    }

    public static String getBooleanDescribe(String str) {
        if (str == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if ((str.equalsIgnoreCase("y")) || (str.equalsIgnoreCase("yes")) ||
                (str.equalsIgnoreCase("true")) || (str.equalsIgnoreCase("t")) ||
                (str.equalsIgnoreCase("是")) || (str.equalsIgnoreCase("1"))) {
            return "是";
        }
        if ((str.equalsIgnoreCase("n")) || (str.equalsIgnoreCase("no")) ||
                (str.equalsIgnoreCase("false")) || (str.equalsIgnoreCase("f")) ||
                (str.equalsIgnoreCase("否")) || (str.equalsIgnoreCase("0"))) {
            return "否";
        }
        if (str.trim().equals("")) {
            return "";
        }
        throw new IllegalArgumentException(
                "argument not in ('y','n','yes','no','true','false','t','f','是','否','1','0','')");
    }

    public static boolean getBoolean(String str) {
        if (str == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if ((str.equalsIgnoreCase("y")) || (str.equalsIgnoreCase("yes")) ||
                (str.equalsIgnoreCase("true")) || (str.equalsIgnoreCase("t")) ||
                (str.equalsIgnoreCase("是")) || (str.equalsIgnoreCase("1"))) {
            return true;
        }
        if ((str.equalsIgnoreCase("n")) || (str.equalsIgnoreCase("no")) ||
                (str.equalsIgnoreCase("false")) || (str.equalsIgnoreCase("f")) ||
                (str.equalsIgnoreCase("否")) || (str.equalsIgnoreCase("0"))) {
            return false;
        }
        if (str.trim().equals("")) {
            return false;
        }
        throw new IllegalArgumentException(
                "argument not in ('y','n','yes','no','true','false','t','f','是','否','1','0','')");
    }

    public static String getBooleanDescribe(boolean bln) {
        if (bln) {
            return getBooleanDescribe("true");
        }
        return getBooleanDescribe("false");
    }

    public static int compareByValue(String str1, String str2) {
        BigDecimal big1 = new BigDecimal(str1);
        BigDecimal big2 = new BigDecimal(str2);
        return big1.compareTo(big2);
    }

    public static double round(double value, int scale) {
        BigDecimal b = new BigDecimal(Double.toString(value));
        return b.divide(ONE, scale, 4).doubleValue();
    }


    public static Double getDouble(Object object) {
        Double _double = null;
        if (object != null) {
            _double = new Double(object.toString());
        }
        return _double;
    }

    public static String getString(Object object) {
        String string = null;
        if (object != null) {
            string = new String(object.toString());
        }
        return string;
    }

    public static String getPlainNumber(Integer integer) {
        if (integer == null) {
            return "";
        }
        DecimalFormat df = new DecimalFormat("###0");
        String plainNumber = df.format(integer);
        return plainNumber;
    }

    public static String getPlainNumber(Long _long) {
        if (_long == null) {
            return "";
        }
        DecimalFormat df = new DecimalFormat("###0");
        String plainNumber = df.format(_long);
        return plainNumber;
    }

    public static String getPlainNumber(Double _double) {
        if (_double == null) {
            return "";
        }
        DecimalFormat df = new DecimalFormat("###0.00");
        String plainNumber = df.format(_double);
        return plainNumber;
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}

