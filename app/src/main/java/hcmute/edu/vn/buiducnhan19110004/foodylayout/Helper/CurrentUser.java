package hcmute.edu.vn.buiducnhan19110004.foodylayout.Helper;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.UserDomain;

public class CurrentUser {
    private static int user_id;
    private static String full_name;
    private static String email;
    private static String password;
    private static String phone;
    private static boolean isLogin;

    public static void SetCurrentUser(UserDomain user) {
        setUser_id(user.getId());
        setFull_name(user.getFull_name());
        setEmail(user.getEmail());
        setPassword(user.getPassword());
        setPhone(user.getPhone());
        setIsLogin(true);
    }
    public static void MakeLogOut(){
        setIsLogin(false);
    }
    public static int getUser_id() {
        return user_id;
    }

    public static void setUser_id(int user_id) {
        CurrentUser.user_id = user_id;
    }

    public static String getFull_name() {
        return full_name;
    }

    public static void setFull_name(String full_name) {
        CurrentUser.full_name = full_name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        CurrentUser.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        CurrentUser.password = password;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        CurrentUser.phone = phone;
    }

    public static boolean isIsLogin() {
        return isLogin;
    }

    private static void setIsLogin(boolean isLogin) {
        CurrentUser.isLogin = isLogin;
    }

}
