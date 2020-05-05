package com.schedule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ProcessMain {

	public static void main(String[] args) throws IOException {

		// input parameters
		String teamMembersStr = "5";
		String floor = "8";
		String meetingStartTimeStr = "10:30";
		String meetingEndTimeStr="11:30";

		
		int meetingStartTime = Integer.parseInt(meetingStartTimeStr.replace(":", ""));
		int meetingEndTime= Integer.parseInt(meetingEndTimeStr.replace(":", ""));
		
		int teamMembers = Integer.parseInt(teamMembersStr);
		
		Map<String, List<AvailableTimeVO>> mapFinalAvailableTime = new HashMap<String, List<AvailableTimeVO>>();
		
		DataGeneration dataGeneration = new DataGeneration();
		dataGeneration.dataGeneration();
		
		Map<String, List<AvailableTimeVO>> mapAvailableTime = dataGeneration.getMapAvailableTime();
		Map<String, RoomSizeVO> mapRoomSize = dataGeneration.getMapRoomSize();
		
				
		Set<Entry<String, List<AvailableTimeVO>>> setEntries= mapAvailableTime.entrySet();
		System.out.println("Expected time :"+meetingStartTime+" "+meetingEndTime);
	
		for(Entry<String,List<AvailableTimeVO>> entry:setEntries) {
			String roomNumber=entry.getKey();
			List<AvailableTimeVO> listTimes=entry.getValue();
			for(AvailableTimeVO timeVO:listTimes) {
			 Integer availabeStartTime=	Integer.parseInt(timeVO.getStartDate().replace(":", ""));
			 Integer availabeEndTime=Integer.parseInt(timeVO.getEndDate().replace(":", ""));
			 RoomSizeVO roomSizeVO= mapRoomSize.get(roomNumber);
			 
			 if(availabeStartTime<=meetingStartTime   && availabeEndTime>=meetingEndTime && roomSizeVO.getRoomSize()>=teamMembers) {
				 //System.out.println("exact match time slot room number:"+timeVO.getRoomNumber());	
				 mapFinalAvailableTime.put(roomNumber, listTimes);
				 System.out.println("Room number for available match time slot and team size :"+timeVO.getRoomNumber()+ " "+availabeStartTime+" "+availabeEndTime+ " "+roomSizeVO.getRoomSize());
			 
			 }
		
			 
			}
		}
		
		
		
		
		Set<Entry<String, List<AvailableTimeVO>>> setFinalEntries= mapFinalAvailableTime.entrySet();
	
		List<Double> FinalRoomList=new ArrayList<Double>();
		for(Entry<String,List<AvailableTimeVO>> entry:setFinalEntries) {
			String roomNumber=entry.getKey();
			List<AvailableTimeVO> listTimes=entry.getValue();
			for(AvailableTimeVO timeVO:listTimes) {
			 Integer availabeStartTime=	Integer.parseInt(timeVO.getStartDate().replace(":", ""));
			 Integer availabeEndTime=Integer.parseInt(timeVO.getEndDate().replace(":", ""));
			 RoomSizeVO roomSizeVO= mapRoomSize.get(roomNumber);
			 
			 if(availabeStartTime==meetingStartTime   && availabeEndTime==meetingEndTime && roomSizeVO.getRoomSize()==teamMembers) {
				 //System.out.println("exact match time slot room number:"+timeVO.getRoomNumber());	
				 FinalRoomList.add(Double.parseDouble(roomNumber));
				 System.out.println("room number for exact match time slot and team size :"+timeVO.getRoomNumber()+ " "+availabeStartTime+" "+availabeEndTime+ " "+roomSizeVO.getRoomSize());
			     break;
			 } if(availabeStartTime==meetingStartTime   && availabeEndTime==meetingEndTime) {
				 //System.out.println("exact match time slot room number:"+timeVO.getRoomNumber());	
				 FinalRoomList.add(Double.parseDouble(roomNumber));
				 System.out.println("Room number for exact match time slot and room size greatar thean team size :"+timeVO.getRoomNumber()+ " "+availabeStartTime+" "+availabeEndTime+ " "+roomSizeVO.getRoomSize());   
			 }	 
			}
		}
		
		if(FinalRoomList.isEmpty()) {
			System.out.println("No Meetings Rooms Available..");
		}else {
		
			double[] closestRoomArray = FinalRoomList.stream()
					.mapToDouble(Double::doubleValue)
					.toArray();
		
			Double floorNumber=Double.parseDouble(floor);
			
			double finalBesMeetingtRoom=getClosestRoom(closestRoomArray,floorNumber);
			
			 System.out.println("Final Best Meeting Room: "+finalBesMeetingtRoom);
			 
			
		}
	
		
	}

	
	public void getFinalRoomList() {
		
	}
	
	
	 public static double getClosestRoom(double[] closestRoomArray, double floorNumber) {
	     
	        double closestRoom = floorNumber;
	        Arrays.sort(closestRoomArray);
	 
	        for (int index = 0; index < closestRoomArray.length; index++) {
	           if(closestRoomArray[index]>floorNumber) {
	        	   closestRoom=closestRoomArray[index];
	        	   break;
	           }
	        }
	        return closestRoom;
	    }
	 
	
}
