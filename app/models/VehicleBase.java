package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import play.db.jpa.Model;
@Entity(name = "vehicle_base")
public class VehicleBase extends Model{
	public String plateNumber;
	public String vehilceType;
	public static boolean isExist(String plateNumber){
		boolean flag = false;
		List<VehicleBase> list = new ArrayList<VehicleBase>();
		list = VehicleBase.find("plateNumber=?", plateNumber).fetch();
		if(list!=null&&list.size()>0){
			flag = true;
		}
		return flag;
		
	}
	public static Long getIdByPlateNumber(String plateNumber){
		List<VehicleBase> list = new ArrayList<VehicleBase>();
		list = VehicleBase.find("plateNumber=?", plateNumber).fetch();
		return list.get(0).id;
	}
}
