package com.gxh.bean;
/**
 * 新闻实体类
 * @author GengXuHan
 * @Description
 * @Date 2018年7月2日 上午8:50:11
 */
public class news {

	private String newsid;
	private String newstitle;    
	private String newscontext ;
	private String    newstype  ;   // 0代表国际新闻 1代表站内新闻
	public String getNewsid() {
		return newsid;
	}
	public void setNewsid(String newsid) {
		this.newsid = newsid;
	}
	public String getNewstitle() {
		return newstitle;
	}
	public void setNewstitle(String newstitle) {
		this.newstitle = newstitle;
	}
	public String getNewscontext() {
		return newscontext;
	}
	public void setNewscontext(String newscontext) {
		this.newscontext = newscontext;
	}
	
	public String getNewstype() {
		return newstype;
	}
	public void setNewstype(String newstype) {
		this.newstype = newstype;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	private String createtime ;
	private String remark    ;  

}
