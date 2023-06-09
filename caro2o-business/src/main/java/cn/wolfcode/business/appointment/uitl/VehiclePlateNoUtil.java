package cn.wolfcode.business.appointment.uitl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VehiclePlateNoUtil {
    /**
     * 获取车牌类型信息
     *
     * @param plateNo
     * @return 返回null，表示车牌不匹配
     */
    public static VehiclePlateNoEnum getVehiclePlateNo(String plateNo) {
        for (VehiclePlateNoEnum vehiclePlateNoEnum : VehiclePlateNoEnum.values()) {
            Matcher matcher = vehiclePlateNoEnum.pattern.matcher(plateNo);
            if (matcher.find()) {
                return vehiclePlateNoEnum;
            }
        }
        return null;
    }


    public enum VehiclePlateNoEnum {
        /**
         * 匹配民用车牌和使馆车牌
         * 1、第一位为汉子省份缩写
         * 2、第二位为大写字母城市编码
         * 3、后面是5位仅含字母和数字的组合
         */
        CIVIL_LICENSE_PLATE_AND_EMBASSY_LICENSE_PLATE(1, "民用车牌和使馆车牌", Pattern.compile("^[京津冀晋蒙辽吉黑沪苏浙皖闽赣鲁豫鄂湘粤桂琼川贵云渝藏陕甘青宁新]{1}[A-Z]{1}[0-9a-zA-Z]{5}$")),
        /**
         * 匹配特种车牌(挂,警,学,领,港,澳)
         */
        SPECIAL_LICENSE_PLATES(2, "特种车牌", Pattern.compile("^[京津冀晋蒙辽吉黑沪苏浙皖闽赣鲁豫鄂湘粤桂琼川贵云渝藏陕甘青宁新]{1}[A-Z]{1}[0-9a-zA-Z]{4}[挂警学领港澳]{1}$")),
        /**
         * 匹配
         */
        ARMED_POLICE_CAR(3, "武警车牌", Pattern.compile("^WJ[京津冀晋蒙辽吉黑沪苏浙皖闽赣鲁豫鄂湘粤桂琼川贵云渝藏陕甘青宁新]?[0-9a-zA-Z]{5}$")),
        /**
         * 匹配军牌
         */
        MILITARY_CAR(4, "军用车牌", Pattern.compile("^[A-Z]{2}[0-9]{5}$")),
        /**
         * 小型新能源车
         * 1、匹配新能源车辆6位车牌
         */
        SMALL_NEW_ENERGY_VEHICLE(5, "小型新能源车牌", Pattern.compile("^[京津冀晋蒙辽吉黑沪苏浙皖闽赣鲁豫鄂湘粤桂琼川贵云渝藏陕甘青宁新]{1}[A-Z]{1}[DF]{1}[0-9a-zA-Z]{5}$")),
        /**
         * 大型新能源车
         */
        LARGE_NEW_ENERGY_VEHICLE(6, "大型新能源车牌", Pattern.compile("^[京津冀晋蒙辽吉黑沪苏浙皖闽赣鲁豫鄂湘粤桂琼川贵云渝藏陕甘青宁新]{1}[A-Z]{1}[0-9a-zA-Z]{5}[DF]{1}$"));
        private int code;

        private String description;

        private Pattern pattern;

        VehiclePlateNoEnum(int code, String description, Pattern pattern) {
            this.code = code;
            this.description = description;
            this.pattern = pattern;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }
}