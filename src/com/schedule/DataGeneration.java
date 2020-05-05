package com.schedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataGeneration {

	private Map<String, List<AvailableTimeVO>> mapAvailableTime = new HashMap<String, List<AvailableTimeVO>>();
	private Map<String, RoomSizeVO> mapRoomSize = new HashMap<String, RoomSizeVO>();

	private Map<String, List<String>> mapFloorRoom = new HashMap<String, List<String>>();
	
	public static void main(String args[]) throws IOException {
		new DataGeneration().dataGeneration();
	}
	

	public void dataGeneration() throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("rooms.txt")));
		String line = "";

		while (null != (line = br.readLine())) {
			System.out.println(line);
			String[] arr = line.split(",");
			
			String roomNumber = arr[0];
			String singleDigitRooNumber=arr[0].split(":")[0];
			
			if(null ==mapFloorRoom.get(singleDigitRooNumber)) {
				List<String> roomList=new ArrayList<String>();
				roomList.add(roomNumber);
				mapFloorRoom.put(singleDigitRooNumber, roomList);
			}else {
				List<String> roomList =mapFloorRoom.get(singleDigitRooNumber);
				roomList.add(roomNumber);
			}
			
			
			RoomSizeVO  roomSizeVO=new RoomSizeVO();
			roomSizeVO.setRoomNumber(arr[0]);
			roomSizeVO.setRoomSize(Integer.parseInt(arr[1]));
			mapRoomSize.put(roomNumber,roomSizeVO);
			
			List<AvailableTimeVO> listAvailableDuration = new ArrayList<AvailableTimeVO>();

			for (int i = 2; i < arr.length;) {
				System.out.println(arr[i] + arr[i + 1]);
				AvailableTimeVO availableTimeVO = new AvailableTimeVO();
				availableTimeVO.setStartDate((arr[i]));
				availableTimeVO.setEndDate((arr[i + 1]));
				availableTimeVO.setRoomNumber(roomNumber);
				listAvailableDuration.add(availableTimeVO);
				i = i + 2;
			}
			mapAvailableTime.put(roomNumber, listAvailableDuration);

		}
		if (null != br) {
			br.close();
		}

	}
	
	

	public Map<String, List<AvailableTimeVO>> getMapAvailableTime() {
		return mapAvailableTime;
	}

	public void setMapAvailableTime(Map<String, List<AvailableTimeVO>> mapAvailableTime) {
		this.mapAvailableTime = mapAvailableTime;
	}

	public Map<String, RoomSizeVO> getMapRoomSize() {
		return mapRoomSize;
	}

	public void setMapRoomSize(Map<String, RoomSizeVO> mapRoomSize) {
		this.mapRoomSize = mapRoomSize;
	}
}
