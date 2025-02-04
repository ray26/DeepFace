package com.iustu.identification.ui.main.config.libmanager;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.iustu.identification.R;
import com.iustu.identification.entity.Library;
import com.iustu.identification.ui.base.BaseFragment;
import com.iustu.identification.ui.main.config.libmanager.mvp.LibManagerPesenter;
import com.iustu.identification.ui.main.config.libmanager.mvp.LibManagerView;
import com.iustu.identification.ui.widget.dialog.WaitProgressDialog;
import com.iustu.identification.util.DataCache;
import com.iustu.identification.util.PageSetHelper;
import com.iustu.identification.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Liu Yuchuan on 2017/11/5.
 */

public class LibraryManageFragment extends BaseFragment implements LibManagerView {
    @BindView(R.id.library_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.page_tv)
    TextView pageTv;

    private WaitProgressDialog waitProgressDialog;
    private LibManagerPesenter presenter;

    private LibraryManagerAdapter mAdapter;
    private PageSetHelper pageSetHelper;

    private HashSet<String> mChooseList = DataCache.getChosenLibConfig();     // 用来记录当前哪些库正在使用中，里面保存的是libName
    private HashSet<String> mChangeList = DataCache.getChangedLib();
    private List<Library> mLibraryList;

    @Override
    protected int postContentView() {
        return R.layout.fragment_library_manage;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, View view) {
        super.initView(savedInstanceState, view);
        mLibraryList = new ArrayList<>();
        initData();
        mAdapter = new LibraryManagerAdapter(mLibraryList);
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, mAdapter.getDisplayCountPerPage(), LinearLayoutManager.HORIZONTAL,false ){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });
        mAdapter.setOnPageItemClickListener((v, index, position)->{
            Library library = mLibraryList.get(index);
            if(library.inUsed == 1){
                library.inUsed = 0;
                mChangeList.add(library.libName);
                mChooseList.remove(library.libName);
            }else {
                library.inUsed = 1;
                mChooseList.add(library.libName);
                mChangeList.remove(library.libName);
            }
            mAdapter.notifyItemChanged(position);
        });
        recyclerView.setAdapter(mAdapter);
        pageSetHelper = new PageSetHelper(recyclerView, pageTv);
    }

    @OnClick(R.id.last_page_iv)
    public void lastPage(){
        pageSetHelper.lastPage();
    }

    @OnClick(R.id.next_page_iv)
    public void nextPage(){
        pageSetHelper.nextPage();
    }

    @Override
    public void setPresenter(LibManagerPesenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initData() {
        presenter.onInitData();
    }

    @Override
    public void bindData(List<com.iustu.identification.entity.Library> data) {
        this.mLibraryList.addAll(data);
        for (Library library : mLibraryList) {
            if (library.inUsed == 1)
                mChooseList.add(library.libName);
        }
        mAdapter.notifyDataChange();
    }

    @Override
    public void onFailed(String e) {
        ToastUtil.show(e);
    }

    @Override
    public void onSuccess() {
    }

    @Override
    public void showWaitDialog(String content) {
        waitProgressDialog = new WaitProgressDialog.Builder()
                .title(content)
                .cancelable(false)
                .build();
        waitProgressDialog.show(mActivity.getSupportFragmentManager(), "Loading");
    }

    @Override
    public void dissmissDialog() {
        waitProgressDialog.dismiss();
        waitProgressDialog = null;
    }
}
