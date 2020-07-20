package com.macro.mall.tiny.common.api;

/**
 * 通用返回对象
 */
public class CommonResult<T> {
    private long code;
    private String message;
    private T data;
    protected CommonResult() {}
    protected CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回的结果
     * @param data 获得数据
     */
//    静态泛型方法应该使用其他类型区分,这样才能清楚地将静态方法的泛型类型和实例类型的泛型类型区分开
    public static <K> CommonResult<K> success(K data) {
        return new CommonResult<K>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回的结果，重载上面那个success方法
     * @param data 获取的数据
     * @param message 提示信息
     */
    public static <K> CommonResult<K> success(K data, String message) {
        return new CommonResult<K>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <K> CommonResult<K> failed(IErrorCode errorCode) {
        return new CommonResult<K>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <K> CommonResult<K> failed(String message) {
        return new CommonResult<K>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <K> CommonResult<K> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <K> CommonResult<K> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <K> CommonResult<K> validateFailed(String message) {
        return new CommonResult<K>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录时返回的结果
     */
    public static <K> CommonResult<K> unauthorized(K data) {
        return new CommonResult<K>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), null);
    }

    /**
     * 未授权返回结果
     */
    public static <K> CommonResult<K> forbidden(K data) {
        return new CommonResult<K>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), null);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
