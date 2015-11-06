package com.renfei.blog.user.model;

import com.google.common.base.Objects;
import lombok.*;

import java.util.Date;

import static com.renfei.blog.common.utils.Arguments.notEmpty;


/**
 * Created with IntelliJ IDEA
 * Author: songrenfei
 * Date: 15/2/4
 * Time: 下午5:29
 */
@ToString
@EqualsAndHashCode
public class User {

    private static final long serialVersionUID = -7156141639881746299L;

    @Setter
    private Long id;

    @Getter
    @Setter
    private String nickname;    // 昵称

    @Getter @Setter
    private String mobile;      // 手机


    @Getter @Setter
    private String email;       // 邮箱

    @Setter
    private Integer type;       // 类型

    @Getter @Setter
    private String passwd;      // 32位加密密码

    @Getter @Setter
    private Integer status;     // 状态

    @Getter @Setter
    private Date createdAt;     // 创建时间

    @Getter @Setter
    private Date updatedAt;     // 更新时间



    public static enum TYPE {
        ADMIN(0, "管理员"),
        BUYER(1, "买家"),
        SELLER(2, "卖家"),
        SITE_OWNER(3, "站点拥有者"),
        AGENT(4, "代理商"),
        ALL(-99, "全部");             // 用于权限校验，不是真实TYPE

        private final int value;

        private final String display;

        private TYPE(int number, String display) {
            this.value = number;
            this.display = display;
        }

        public static TYPE fromName(String name) {
            for (TYPE type : TYPE.values()) {
                if (type.name().equalsIgnoreCase(name)) {
                    return type;
                }
            }
            return null;
        }

        public static TYPE fromNumber(Integer number) {
            if (number == null) {
                return null;
            }
            for (TYPE type : TYPE.values()) {
                if (type.value == number) {
                    return type;
                }
            }
            return null;
        }

        public int toNumber() {
            return value;
        }

        public String toName() {
            return display;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    /**
     * 状态枚举
     */
    public static enum Status {
        FROZEN(-2, "已冻结"),
        LOCKED(-1, "已锁定"),
        NOT_ACTIVATE(0, "未激活"),
        NORMAL(1, "正常");

        private final int value;

        private final String desc;

        private Status(int number, String desc) {
            this.value = number;
            this.desc = desc;
        }

        /**
         * 根据数值返回状态枚举
         * @param value 数值
         * @return 状态枚举
         */
        public static Status from(int value) {
            for (Status status : Status.values()) {
                if (Objects.equal(status.value, value)) {
                    return status;
                }
            }
            return null;
        }

        public int value() {
            return value;
        }

        @Override
        public String toString() {
            return desc;
        }
    }

    public String getName() {
        if (notEmpty(nickname)) {
            return nickname;
        }
        if (notEmpty(mobile)) {
            return mobile;
        }
        return email;
    }

    /**
     * 判断用户是否是某个状态
     * @param user 用户
     * @param s 状态
     * @return 是返回true, 反之false
     */
    public static boolean isStatus(User user, Status s){
        return user.getStatus() == s.value();
    }


}
