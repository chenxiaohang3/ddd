package club.twzw.bean;

import java.io.Serializable;

public class User implements Serializable {
	private Integer id;
	private String name;
	private String gender;
	private String email;

	@Override
	public String toString() {
		return "user{" + "id=" + id + ", name='" + name + '\'' + ", gender='" + gender + '\'' + ", email='" + email
				+ '\'' + '}';
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
