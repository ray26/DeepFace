package com.iustu.identification.ui.main.config.libmanager.mvp;

import android.database.Cursor;

import com.iustu.identification.entity.Library;
import com.iustu.identification.util.RxUtil;
import com.iustu.identification.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LibManagerPesenter {
    private LibManagerView mView;
    private Disposable disposable;

    public void setView(LibManagerView view) {
        this.mView = view;
    }

    public void onInitData() {
        mView.showWaitDialog("正在获取数据...");
        Observable observable = RxUtil.getQuaryObservalbe(false, RxUtil.DB_LIBRARY, RxUtil.LIBRARY_COLUMNS, null, null, null, null, null, null);
        observable.subscribe(new Observer<Cursor>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Cursor cursor) {
                if (cursor.getCount() == 0)
                    return;
                List<Library> data = new ArrayList<>();

                while (cursor.moveToNext()) {
                    Library library = new Library();
                    library.libName = cursor.getString(cursor.getColumnIndex("libName"));
                    library.count = cursor.getInt(cursor.getColumnIndex("count"));
                    library.inUsed = cursor.getInt(cursor.getColumnIndex("inUsed"));
                    library.description = cursor.getString(cursor.getColumnIndex("description"));
                    data.add(library);
                }
                mView.bindData(data);
                cursor.close();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                disposable.dispose();
                mView.onFailed(e.getMessage());
                mView.dissmissDialog();
            }

            @Override
            public void onComplete() {
                disposable.dispose();
                mView.dissmissDialog();
                disposable = null;
            }
        });
    }

    public void onUpdateData(HashSet<String> mChooseList) {
        Observable observable = RxUtil.updataLibraries();
        observable.subscribe(new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                ToastUtil.show("数据库同步失败:" + e.getMessage());
            }

            @Override
            public void onComplete() {
                disposable.dispose();
                disposable = null;
                mView.onSuccess();
            }
        });
    }
}
