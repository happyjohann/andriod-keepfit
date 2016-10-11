package com.miraclehinn.dietdiary;

import java.sql.Date;
import java.sql.Time;

public class Meal {
	private int _id;
	private Date date;
	private Time time;
	private String type;
	private String name;
	private int calory;
	private byte[] photo;

	public Meal() {
		super();
	}
	
	public Meal(Date date, Time time, String type, String name,
			int calory) {
		super();
		this.date = date;
		this.time = time;
		this.type = type;
		this.name = name;
		this.calory = calory;
	}

	public Meal(Date date, Time time, String type, String name,
			int calory, byte[] photo) {
		super();
		this.date = date;
		this.time = time;
		this.type = type;
		this.name = name;
		this.calory = calory;
		this.photo = photo;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCalory() {
		return calory;
	}

	public void setCalory(int calory) {
		this.calory = calory;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

}
