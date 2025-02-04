package com.iustu.identification.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iustu.identification.R;
import com.iustu.identification.util.IconFontUtil;

/**
 * Created by Liu Yuchuan on 2017/11/4.
 */

public class TitleBar extends FrameLayout {
    private boolean backEnable;
    private boolean sureEnable;
    private boolean searchEnable;

    private TitleBarListener titleBarListener;

    private TextView titleTv;
    private ImageView backIv;
    private ImageView sureIv;
    private TextView searchTv;

    public static final int ID_BACK = 0;
    public static final int ID_SURE = 1;
    public static final int ID_SEARCH = 2;

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_title_bar, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        String title = ta.getString(R.styleable.TitleBar_title);
        backEnable = ta.getBoolean(R.styleable.TitleBar_backEnable, false);
        sureEnable = ta.getBoolean(R.styleable.TitleBar_sureEnable, false);
        searchEnable = ta.getBoolean(R.styleable.TitleBar_searchEnable, false);
        ta.recycle();
        titleTv = findViewById(R.id.title_bar_title_tv);
        backIv = findViewById(R.id.title_bar_back_iv);
        titleTv.setText(title);
        if(backEnable){
            backIv.setVisibility(VISIBLE);
        }else {
            backIv.setVisibility(GONE);
        }
        sureIv = findViewById(R.id.title_bar_sure_iv);
        if(sureEnable){
            sureIv.setVisibility(VISIBLE);
        }else {
            sureIv.setVisibility(GONE);
        }
        searchTv = findViewById(R.id.title_bar_search);
        searchTv.setText("搜索");
        if(searchEnable){
            searchTv.setVisibility(VISIBLE);
        }else {
            searchTv.setVisibility(GONE);
        }
        backIv.setOnClickListener(v -> {
            if(backEnable && titleBarListener != null){
                titleBarListener.onTitleButtonClick(ID_BACK);
            }
        });
        sureIv.setOnClickListener(v -> {
            if(sureEnable && titleBarListener != null){
                titleBarListener.onTitleButtonClick(ID_SURE);
            }
        });
        searchTv.setOnClickListener(v -> {
            if (searchEnable && titleBarListener != null) {
                titleBarListener.onTitleButtonClick(ID_SEARCH);
            }
        });
    }

    public interface TitleBarListener{
        void onTitleButtonClick(int id);
    }

    public void setTitleBarListener(TitleBarListener titleBarListener) {
        this.titleBarListener = titleBarListener;
    }

    public void setTitle(String title){
        titleTv.setText(title);
    }

    public void setBackEnable(boolean enable){
        backEnable = enable;
        if(backEnable){
            backIv.setVisibility(VISIBLE);
        }else {
            backIv.setVisibility(GONE);
        }
    }

    public void setSureEnable(boolean enable) {
        this.sureEnable = enable;
        if(sureEnable){
            sureIv.setVisibility(VISIBLE);
        }else {
            sureIv.setVisibility(GONE);
        }
    }

    public void setSearchEnable(boolean searchEnable) {
        this.searchEnable = searchEnable;
        if (searchEnable) {
            searchTv.setVisibility(VISIBLE);
        } else {
            searchTv.setVisibility(GONE);
        }
    }
}
