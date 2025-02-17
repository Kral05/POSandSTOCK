package util;

public class RegexUtil {
    // 帳號：4-12 個字母或數字
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9]{4,12}$";

    // 密碼：至少 8 碼，包含大小寫字母與數字
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    // 手機號碼：台灣手機號碼，09 開頭，後面 8 碼數字
    public static final String MOBILE_REGEX = "^09\\d{8}$";

    // 地址：允許中文、英文、數字、空格，並且必須包含「區」和「號」
    public static final String ADDRESS_REGEX = "^[\\u4e00-\\u9fa5a-zA-Z0-9\\s]{1,5}區[\\u4e00-\\u9fa5a-zA-Z0-9\\s]*號$";

    // 檢查輸入是否符合正規表示法
    public static boolean isValid(String input, String regex) {
        return input != null && input.matches(regex);
    }
}
