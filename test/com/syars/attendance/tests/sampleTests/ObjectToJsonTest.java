package com.syars.attendance.tests.sampleTests;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syars.attendance.vo.MemberVO;
import com.syars.attendance.vo.ResponseVO;

public class ObjectToJsonTest {

	public static void main(String[] args) {

		Set<String> attendanceSet = new HashSet<String>();
		attendanceSet.add("11/8/18");
		attendanceSet.add("15/8/18");

		List<ResponseVO> responseList = new ArrayList<ResponseVO>();
		String[][] members = { { "MSEC_1", "Deepak", }, { "MSEC_2", "Rustam" }, { "MSEC_3", "Bipin" } };
		for (String[] member : members) {
			MemberVO vo = new MemberVO();
			ResponseVO responseVo = new ResponseVO();
			vo.setMemberID(member[0]);
			vo.setFullName(member[1]);
			vo.setInitiatedFlag(false);
			responseVo.setMemberVo(vo);
			responseVo.setAttendanceSet(attendanceSet);
			
			responseList.add(responseVo);
		}
		
		Gson gson = new Gson();
		Type type1 = new TypeToken<List<ResponseVO>>() {
		}.getType();
		String json1 = gson.toJson(responseList, type1).toString();
		System.out.println(">>>converted response:"+json1);
	}

}
