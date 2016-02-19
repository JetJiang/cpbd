package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import play.db.jpa.Model;
@Entity(name = "user_base")
public class UserBase extends Model{
	public String plateNumber;
	public String vehilceType;
	public String createTime;
	public static boolean isExist(String plateNumber){
		boolean flag = false;
		List<UserBase> list = new ArrayList<UserBase>();
		list = UserBase.find("plateNumber=?", plateNumber).fetch();
		if(list!=null&&list.size()>0){
			flag = true;
		}
		return flag;
		
	}
	public static Long getIdByPlateNumber(String plateNumber){
		List<UserBase> list = new ArrayList<UserBase>();
		list = UserBase.find("plateNumber=?", plateNumber).fetch();
		return list.get(0).id;
	}
}
