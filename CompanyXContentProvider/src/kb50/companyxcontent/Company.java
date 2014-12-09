package kb50.companyxcontent;

import java.util.List;


public class Company {

	private int id;
	private String name;
	private String site;
	private String info;
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	private List<Office> offices;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public List<Office> getOffice() {
		return offices;
	}

	public void setOffice(List<Office> offices) {
		this.offices = offices;
	}

}
