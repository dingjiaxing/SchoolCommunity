package android_hddl_framework.database;

import org.litepal.crud.DataSupport;


public class anitoys extends DataSupport {
    private int area_id;
    private String area_name;
    private int org_level;
    private int parent_id;


    public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	public int getArea_id() {
		return area_id;
	}

	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}

	public int getOrg_level() {
		return org_level;
	}

	public void setOrg_level(int org_level) {
		this.org_level = org_level;
	}

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

}
