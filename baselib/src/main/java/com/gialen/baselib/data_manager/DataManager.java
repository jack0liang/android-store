package com.gialen.baselib.data_manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.gialen.baselib.bean.db_bean.UserInfo;
import com.gialen.baselib.greendao.gen.DaoMaster;
import com.gialen.baselib.greendao.gen.DaoSession;
import com.gialen.baselib.greendao.gen.UserInfoDao;

import java.util.List;


/**
 * 数据库操作统一管理类
 */
public  class DataManager {
    private final static String TAG = "DataManager";
    private final static String dbName = "gialen_db";
    private static DataManager mInstance;
    private MySQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    public DaoSession getmDaoSession() {
        return mDaoSession;
    }

    public DataManager(Context context) {
        openHelper = new MySQLiteOpenHelper(context, dbName, null);
        db = openHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }


    /**
     * 初始化
     *
     * @param context
     */
    public static void init(Context context) {
        if (mInstance == null) {
            synchronized (DataManager.class) {
                if (mInstance == null) {
                    mInstance = new DataManager(context);
                }
            }
        }
    }

    /**
     * 获取单例引用
     */
    public static DataManager getInstance() {
        if (mInstance == null) {
            try {
                throw new Exception("please init dbmanager before use");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mInstance;
    }

    public void insertUserInfo(UserInfo userInfo) {
        UserInfoDao userInfoDao = mDaoSession.getUserInfoDao();
        userInfoDao.deleteAll();
        userInfoDao.insertOrReplace(userInfo);
    }

    public void clearUserInfo() {
        mDaoSession.getUserInfoDao().deleteAll();
    }

    public UserInfo queryUserInfo(){
       return mDaoSession.getUserInfoDao().queryBuilder().build().unique();
    }

}