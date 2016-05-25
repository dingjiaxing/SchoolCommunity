package cn.edu.shu.scommunity.view;

public interface RefreshListener {
	 /** 
    * 下拉刷新 
    */  
   void onDownPullRefresh();  
 
   /** 
    * 上拉加载更多 
    */  
   void onLoadingMore();  
}

