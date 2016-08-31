package com.dinkevin.xui.view;

import com.dinkevin.xui.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/** 
 * 自定义 ListView　下拉刷新,上拉加载更多 
 * @author SunnyCoffee 
 * @create 2013-10-24 
 * @version 1.0 
 */  
  
@SuppressLint({ "InflateParams", "ClickableViewAccessibility" })
public class PullRefreshListView extends ListView implements OnScrollListener {  
  
    // 区分当前操作是刷新还是加载  
    public static final int REFRESH = 0;  
    public static final int LOAD = 1;  
  
    // 区分PULL和RELEASE的距离的大小  
    private static final int SPACE = 20;  
  
    // 定义header的四种状态和当前状态  
    private static final int NONE = 0;  
    private static final int PULL = 1;  
    private static final int RELEASE = 2;  
    private static final int REFRESHING = 3;  
    private int state;  
  
    private LayoutInflater inflater;  
    private View view_header;  
    private View view_footer;  
    
    private ProgressBar prb_refresh,prb_load;  
    private TextView txt_refreshTip,txt_loadTip;
    private ImageView img_refresh;
  
    private int startY;  
  
    private int firstVisibleItem;  
    private int scrollState;  
    private int headerContentInitialHeight;  
    private int headerContentHeight;  
  
    // 只有在 ListView 第一个item显示的时候（ ListView 滑到了顶部）才进行下拉刷新， 否则此时的下拉只是滑动 ListView  
    private boolean isRecorded;  
    private boolean isLoading;				// 判断是否正在加载  
    private boolean loadEnable = true;		// 开启或者关闭加载更多功能  
    private boolean isLoadFull;  
    private int pageSize = 10;  
  
    private OnRefreshListener onRefreshListener;  
    private OnLoadListener onLoadListener;  
  
    public PullRefreshListView(Context context) {  
        super(context);  
        initView(context);  
    }  
  
    public PullRefreshListView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        initView(context);  
    }  
  
    public PullRefreshListView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
        initView(context);  
    }  
  
    // 下拉刷新监听  
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {  
        this.onRefreshListener = onRefreshListener;  
    }  
  
    // 加载更多监听  
    public void setOnLoadListener(OnLoadListener onLoadListener) {  
        this.loadEnable = true;  
        this.onLoadListener = onLoadListener;  
    }  
  
    public boolean isLoadEnable() {  
        return loadEnable;  
    }  
  
    /**
     * 这里的开启或者关闭加载更多，并不支持动态调整  
     * @param loadEnable
     */
    public void setLoadEnable(boolean loadEnable) {  
        this.loadEnable = loadEnable; 
        if(loadEnable)
        	this.addFooterView(view_footer);
        else
        	this.removeFooterView(view_footer);  
    }  
  
    /**
     * 获取单页加载数据大小
     * @return
     */
    public int getPageSize() {  
        return pageSize;  
    }  
  
    /**
     * 设置单页数据大小
     * @param pageSize
     */
    public void setPageSize(int pageSize) {  
        this.pageSize = pageSize;  
    }  
  
    // 初始化组件  
    private void initView(Context context) {  
  
        inflater = LayoutInflater.from(context);  
        view_footer = inflater.inflate(R.layout.xui_pull_refresh_footer, null);  
        txt_loadTip = (TextView) view_footer.findViewById(R.id.txt_load_tip);  
        prb_load = (ProgressBar) view_footer.findViewById(R.id.prb_load);  
  
        view_header = inflater.inflate(R.layout.xui_pull_refresh_header, null);  
        txt_refreshTip = (TextView) view_header.findViewById(R.id.txt_refresh_tip);  
        prb_refresh = (ProgressBar) view_header.findViewById(R.id.prb_refresh);  
        prb_refresh.setVisibility(View.GONE);
        img_refresh = (ImageView) view_header.findViewById(R.id.img_refresh);
        img_refresh.setVisibility(View.VISIBLE);
  
        // 为 ListView 添加头部和尾部，并进行初始化  
        headerContentInitialHeight = view_header.getPaddingTop();  
        measureView(view_header);  
        headerContentHeight = view_header.getMeasuredHeight();  
        topPadding(-headerContentHeight);  
        
        // 添加头与脚
        this.addHeaderView(view_header);  
        this.addFooterView(view_footer);
        this.setOnScrollListener(this);  
    }  
  
    public void onRefresh() {  
        if (onRefreshListener != null) {  
            onRefreshListener.onRefresh();  
        }  
    }  
  
    public void onLoad() {  
        if (onLoadListener != null) {  
            onLoadListener.onLoad();  
        }  
    }  
  
    /**
     * 刷新结束，通知 ListView 头状态更新
     */
    public void onRefreshComplete() {  
        state = NONE;  
        refreshHeaderViewByState();  
    }
  
    /**
     * 用于加载更多结束后的回调  
     */
    public void onLoadComplete() {  
        isLoading = false;  
    }  
  
    @Override  
    public void onScroll(AbsListView view, int firstVisibleItem,  
            int visibleItemCount, int totalItemCount) {  
        this.firstVisibleItem = firstVisibleItem;  
    }  
  
    @Override  
    public void onScrollStateChanged(AbsListView view, int scrollState) {  
        this.scrollState = scrollState;  
        ifNeedLoad(view, scrollState);  
    }  
  
    // 根据 ListView 滑动的状态判断是否需要加载更多  
    private void ifNeedLoad(AbsListView view, int scrollState) {  
        if (!loadEnable) {  
            return;  
        }  
        try {  
            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE  
                    && !isLoading  
                    && view.getLastVisiblePosition() == view  
                            .getPositionForView(view_footer) && !isLoadFull) {  
                onLoad();  
                isLoading = true;  
            }  
        } catch (Exception e) {  
        }  
    }  
  
    /** 
     * 监听触摸事件，解读手势 
     */  
    @Override  
    public boolean onTouchEvent(MotionEvent ev) {  
        switch (ev.getAction()) {  
        case MotionEvent.ACTION_DOWN:  
            if (firstVisibleItem == 0) {  
                isRecorded = true;  
                startY = (int) ev.getY();  
            }  
            break;  
        case MotionEvent.ACTION_CANCEL:  
        case MotionEvent.ACTION_UP:  
            if (state == PULL) {  
                state = NONE;  
                refreshHeaderViewByState();  
            } else if (state == RELEASE) {  
                state = REFRESHING;  
                refreshHeaderViewByState();  
                onRefresh();  
            }  
            isRecorded = false;  
            break;  
        case MotionEvent.ACTION_MOVE:  
            whenMove(ev);  
            break;  
        }  
        return super.onTouchEvent(ev);  
    }  
  
    // 解读手势，刷新header状态  
    private void whenMove(MotionEvent ev) {  
        if (!isRecorded) {  
            return;  
        }  
        int tmpY = (int) ev.getY();  
        int space = tmpY - startY;  
        int topPadding = space - headerContentHeight;  
        switch (state) {  
        case NONE:  
            if (space > 0) {  
                state = PULL;  
                refreshHeaderViewByState();  
            }  
            break;  
        case PULL:  
            topPadding(topPadding);  
            if (scrollState == SCROLL_STATE_TOUCH_SCROLL  
                    && space > headerContentHeight + SPACE) {  
                state = RELEASE;  
                refreshHeaderViewByState();  
            }  
            break;  
        case RELEASE:  
            topPadding(topPadding);  
            if (space > 0 && space < headerContentHeight + SPACE) {  
                state = PULL;  
                refreshHeaderViewByState();  
            } else if (space <= 0) {  
                state = NONE;  
                refreshHeaderViewByState();  
            }  
            break;  
        }  
  
    }  
  
    // 调整header的大小。其实调整的只是距离顶部的高度。  
    private void topPadding(int topPadding) {  
        view_header.setPadding(view_header.getPaddingLeft(), topPadding,  
                view_header.getPaddingRight(), view_header.getPaddingBottom());  
        view_header.invalidate();  
    }  
  
    /** 
     * 这个方法是根据结果的大小来决定footer显示的。 
     * <p> 
     * 这里假定每次请求的条数为10。如果请求到了10条。则认为还有数据。如过结果不足10条，则认为数据已经全部加载，这时footer显示已经全部加载 
     * </p> 
     * @param resultSize 
     */  
    public void setResultSize(int resultSize) {  
        if (resultSize >= 0 && resultSize < pageSize) 
        {  
            isLoadFull = true;  
            txt_loadTip.setText(R.string.xui_no_more);
            prb_load.setVisibility(View.GONE);
        }
        else if (resultSize == pageSize) {  
            isLoadFull = false;  
            txt_loadTip.setText(R.string.xui_load_more); 
            if(prb_load != null) prb_load.setVisibility(View.VISIBLE);
        }  
    }  
  
    // 根据当前状态，调整header  
    private void refreshHeaderViewByState() {  
    	
        switch (state) {  
        case NONE:  
            topPadding(-headerContentHeight);
            txt_refreshTip.setText(R.string.xui_release_to_refresh);
            prb_refresh.setVisibility(View.GONE);
            img_refresh.setVisibility(View.VISIBLE);
            break;  
            
        case PULL:  
        	txt_refreshTip.setText(R.string.xui_release_to_refresh);
        	prb_refresh.setVisibility(View.GONE);
        	img_refresh.setVisibility(View.VISIBLE);
            break;  
            
        case RELEASE:  
        	txt_refreshTip.setText(R.string.xui_release_to_refresh);
        	prb_refresh.setVisibility(View.GONE);
        	img_refresh.setVisibility(View.VISIBLE);
            break;  
            
        case REFRESHING:  
            topPadding(headerContentInitialHeight);  
            prb_refresh.setVisibility(View.VISIBLE);
            img_refresh.setVisibility(View.GONE);
            txt_refreshTip.setText(R.string.xui_refreshing);
            break;  
        }  
    }  
  
    // 用来计算header大小的。比较隐晦。  
    private void measureView(View child) {  
        ViewGroup.LayoutParams p = child.getLayoutParams();  
        if (p == null) {  
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  
                    ViewGroup.LayoutParams.WRAP_CONTENT);  
        }  
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);  
        int lpHeight = p.height;  
        int childHeightSpec;  
        if (lpHeight > 0) {  
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,  
                    MeasureSpec.EXACTLY);  
        } else {  
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,  
                    MeasureSpec.UNSPECIFIED);  
        }  
        child.measure(childWidthSpec, childHeightSpec);  
    }  
  
    /* 
     * 定义下拉刷新接口 
     */  
    public interface OnRefreshListener {  
        public void onRefresh();  
    } 
  
    /* 
     * 定义加载更多接口 
     */  
    public interface OnLoadListener {  
        public void onLoad();  
    }
} 