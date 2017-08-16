package com.huifenqi.jedi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by smq on 2017/6/15.
 */
public final class LogUtil {
    private static final Map<Class<?>, Logger> clazzLoggerMap = new ConcurrentHashMap<>();
    private static final Map<String, Logger> tagLoggerMap = new ConcurrentHashMap<>();

    public static final Logger with(Class<?> clazz) {
        Logger logger = clazzLoggerMap.get(clazz);
        if (null == logger) {
            synchronized (clazzLoggerMap) {
                if (null == logger) {
                    logger = LoggerFactory.getLogger(clazz);
                    clazzLoggerMap.put(clazz, logger);
                }
            }
        }

        return logger;
    }

    public static final Logger with(String tag) {
        Logger logger = tagLoggerMap.get(tag);
        if (null == logger) {
            synchronized (tagLoggerMap) {
                if (null == logger) {
                    logger = LoggerFactory.getLogger(tag);
                    tagLoggerMap.put(tag, logger);
                }
            }
        }

        return logger;
    }

    /**
     * debug级别
     *
     * @param msg
     */
    public static final void d(String msg) {
        LogUtil.with(Object.class).debug(msg);
    }

    public static final void d(Class<?> clazz, String msg) {
        LogUtil.with(clazz).debug(msg);
    }

    public static final void d(Class<?> clazz, String format, Object... args) {
        LogUtil.with(clazz).debug(format, args);
    }

    public static final void d(Class<?> clazz, String msg, Throwable t) {
        LogUtil.with(clazz).debug(msg, t);
    }


    public static final void d(String tag, String msg) {
        LogUtil.with(tag).debug(msg);
    }

    public static final void d(String tag, String format, Object... args) {
        LogUtil.with(tag).debug(format, args);
    }

    public static final void d(String tag, String msg, Throwable t) {
        LogUtil.with(tag).debug(msg, t);
    }


    /**
     * info级别
     *
     * @param msg
     */
    public static final void i(String msg) {
        LogUtil.with(Object.class).info(msg);
    }

    public static final void i(Class<?> clazz, String msg) {
        LogUtil.with(clazz).info(msg);
    }

    public static final void i(Class<?> clazz, String format, Object... args) {
        LogUtil.with(clazz).info(format, args);
    }

    public static final void i(Class<?> clazz, String msg, Throwable t) {
        LogUtil.with(clazz).info(msg, t);
    }

    public static final void i(String tag, String msg) {
        LogUtil.with(tag).info(msg);
    }

    public static final void i(String tag, String format, Object... args) {
        LogUtil.with(tag).info(format, args);
    }

    public static final void i(String tag, String msg, Throwable t) {
        LogUtil.with(tag).info(msg, t);
    }


    /**
     * error级别
     *
     * @param msg
     */
    public static final void e(String msg) {
        LogUtil.with(Object.class).error(msg);
    }

    public static final void e(Class<?> clazz, String msg) {
        LogUtil.with(clazz).error(msg);
    }

    public static final void e(Class<?> clazz, String format, Object... args) {
        LogUtil.with(clazz).error(format, args);
    }

    public static final void e(Class<?> clazz, String msg, Throwable t) {
        LogUtil.with(clazz).error(msg, t);
    }

    public static final void e(String tag, String msg) {
        LogUtil.with(tag).error(msg);
    }

    public static final void e(String tag, String format, Object... args) {
        LogUtil.with(tag).error(format, args);
    }

    public static final void e(String tag, String msg, Throwable t) {
        LogUtil.with(tag).error(msg, t);
    }


}
