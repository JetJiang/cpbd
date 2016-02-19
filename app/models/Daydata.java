package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Daydata extends Model{
	public String date;
	public double SPJ;
	public double MA3;
	public double MA5;
	public double MA10;
	public double MA21;
	public double ABJBTOP;
	public double ABJBBUY;
	public double ABJBSALE;
	public double ABJBCURRENT;
	public double ABJBBOTTOM;
	public double LTSH;
	public double QSDD;
	
	public static List<Daydata> findDaydata(){
		return Daydata.find("order by id asc").fetch();
	}
	public static List<Daydata> findDaydataByCondition(String start,String end){
		List<Daydata> list = new ArrayList<Daydata>();
		list = Daydata.find("date between ? and ? and LTSH>90", start,end).fetch();
		return list;
	}
	public static List<Daydata> findDaydataByDate(String start,String end){
		List<Daydata> list = new ArrayList<Daydata>();
		list = Daydata.find("date between ? and ?", start,end).fetch();
		return list;
	}
}
