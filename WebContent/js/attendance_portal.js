/**
Copyright: SYARS
2018

File Name: attendance_portal.js
************************************************
Change Date		Name		Description
08/08/2018		Deepak S.	Initial Creation

************************************************

*/

var BASE_URI = "http://attendanceportal-env.3rj94hn5hz.ap-south-1.elasticbeanstalk.com";
//var BASE_URI = "http://localhost:8080/AttendancePortal-1.0.0-SNAPSHOT";
var ATTENDANCE_URI = BASE_URI+"/v1/rest/attendance";
var USERS_URI = BASE_URI+"/v1/rest/users";
var MEMBERS_URI = BASE_URI+"/v1/rest/members";

/**
* This file contains the java script functions related to 
* Attendance Portal functionalities.
*/

function retrieveAllAttendanceForToday(){
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			var presentMembers = document.getElementById('presentMembers');
			if(this.status == 200){
				var APIResponse = xhttp.responseText;
				APIResponse = JSON.parse(APIResponse);
				var table_data = '<table id="presenceTable" class="table table-bordered table-striped"><thead><tr><th>Name</th><th>Member Id</th></tr></thead><tbody id="todaysMembersTable">';
				for(var key in APIResponse){
					var val_responseVo = APIResponse[key];
					for(var key_responseVo in val_responseVo){
						if(key_responseVo == "memberVo"){
							var memberVo = val_responseVo[key_responseVo];
							for(var key_member in memberVo){
								if(key_member == "fullName"){
									table_data+= '<tr><td>'+memberVo[key_member]+'</td>'
								}
								else if(key_member == "memberID"){
									table_data+= '<td>'+memberVo[key_member]+'</td></tr>'
								}
							}
						}
					}
				}
				table_data+= '</tbody></table>';
				presentMembers.innerHTML = table_data;
				// show utility buttons
				showUtilityButtons('presenceTable', 'Bfrtip');
			}
			else{
				presentMembers.innerHTML = this.responseText;
			}
			hideLoader();
		}
	};
	
	var method = "GET";
	var url = ATTENDANCE_URI+"/members";
	
	callService(xhttp, method, url);

}

function retrieveRegisteredMembers(isLogin) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			if(this.status == 200){
				var APIResponse = xhttp.responseText;
				APIResponse = JSON.parse(APIResponse);
				// Put the object into storage
				localStorage.setItem('allRegisteredMembers', JSON.stringify(APIResponse));
				// navigate to home page after login
				if(isLogin){
					window.location.href = './html/loggedin.html';
				}
			}
			hideLoader();
		}
	};
	var method = "GET";
	var url = MEMBERS_URI;
	
	callService(xhttp, method, url);
}

/* To retrieve member related details and save in cache memory*/
function retrieveAllMembersFromCache() {
	showLoader();
	var registeredMembers = document.getElementById('registeredMembersDiv');
	// Retrieve the object from storage
	var parsedCacheObject = JSON.parse(localStorage.getItem('allRegisteredMembers'));
	var table_data = '<input id="searchInput" type="text" placeholder="Search Member.." onkeyup="searchMember()"><br><br>';
	table_data+= '<div id="registeredMembersTableDiv" style="display:none">';
	table_data+= '<table id="registeredMembersTable" class="table table-bordered table-striped table-hover">';
	table_data+='<thead><tr><th>Name</th><th>Member Id</th></tr></thead>'
	table_data+= '<tbody id="searchableBody">';
	for ( var key in parsedCacheObject) {
		if (parsedCacheObject.hasOwnProperty(key)) {
			var val = parsedCacheObject[key];
			for ( var key2 in val) {
				if (val.hasOwnProperty(key2)){
					if(key2 == 'fullName' && val[key2] != null){
						table_data += "<tr><td>"+ val[key2] + "</td>";
					}
					if(key2 == 'memberID' && val[key2] != null){
						table_data += "<td onclick=\"insertAttendance('"+val[key2]+"')\"><span class='tooltirp'>"+ val[key2] + "<i class='tooltiptext'>Click to Insert Attendance</i></span></td></tr>";
					}
				}
			}
		}
	}
	table_data += '</tbody></table></div>';
	registeredMembers.innerHTML = table_data;
	
	// show utility buttons
	showUtilityButtons('registeredMembersTable', 't');
	hideLoader();
}

/* To retrieve the number of attendance for today's date */
function displayCount() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		var count = document.getElementById('count');
		if (this.readyState == 4) {
			
			if(this.status == 200){
				console.log("count:" + this.responseText);
				count.innerHTML = '<p>Count for today is:' + this.responseText;
			}
			else{
				count.innerHTML = '<p>' + xhttp.responseText;
			}
			hideLoader();
		}
	};
	var method = "GET";
	var url = ATTENDANCE_URI+"/count";

	callService(xhttp, method, url);
}

/* To insert attendance for specific member in DB */
function insertAttendance(memberId) {
	// get memberId from input
	//var memberId = document.getElementById('memberId').value;
	//create JSON data from inputs
	var data = {};
	data.memberId = memberId;

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			console.log("response:" + this.responseText);
			var message = document.getElementById('response');
			message.innerHTML = '<p>' + this.responseText;
			hideLoader();
		}
	};
	
	var method = "POST";
	var url = ATTENDANCE_URI;
	callService(xhttp, method, url, data);
}


/* To register a member in system */
function registerMember() {
	
	var data = {};
	data.fullName = document.getElementById('fullName').value;
	data.mobileNumber = document.getElementById('mobileNumber').value;
	data.emailId = document.getElementById('emailId').value;
	data.branch = document.getElementById('branch').value;
	data.fathersName = document.getElementById('fathersName').value;
	data.mothersName = document.getElementById('mothersName').value;
	
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			// Typical action to be performed when the document is ready:
			console.log("response:" + this.responseText);
			var responseMessage = document.getElementById('response');
			responseMessage.innerHTML = '<p>' + this.responseText;
			hideLoader();
		}
	};
	
	var method = "POST";
	var url = MEMBERS_URI;
	callService(xhttp, method, url, data);
}


/* To register a member as user in the system. This is also used to
 * update the user details.
 */
function registerUser(action) {
	var url = USERS_URI;
	var method;
	if (action == 'register') {
		method = "POST";
	}
	if (action == 'update') {
		method = "PUT";
	}

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			// Typical action to be performed when the document is ready:
			console.log("response:" + this.responseText);
			var responseMessage = document.getElementById('response');
			responseMessage.innerHTML = '<p>' + this.responseText;
			hideLoader();
		}
	};
	
	// create JASON data from input supplied
	var data = {};
	data.memberId = document.getElementById('memberId').value;
	data.userRole = document.getElementById('userRole').value;
	data.password = document.getElementById('password').value;
	data.customizedUserId = document.getElementById('customizedUserId').value;

	callService(xhttp, method, url, data);

}


/* To provide login functionality for a user. Upon successful login
 * User details are saved in cache memory to avoid DB query again and again
 */
function login() {
	var userId = document.getElementById('userId').value;
	var password = document.getElementById('password').value;

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			// Typical action to be performed when the document is ready:
			
			if (this.status == 200) {
				var APIResponse = JSON.parse(this.responseText);
				// Put the object into cache storage
				localStorage.setItem('loggedInUser', JSON
						.stringify(APIResponse));

				retrieveRegisteredMembers(true);
			}
			else {
				var responseMessage = document.getElementById('loginMessage');
				responseMessage.innerHTML = '<p>' + this.responseText;
			}
			//hideLoader();
		}
	};
	var method = "GET";
	var url = USERS_URI;
	url += "/"+userId;
	if(userId != "" && userId != undefined && password != "" && password != undefined){
		showLoader();
		xhttp.open(method, url, true);
		xhttp.setRequestHeader('Authorization', 'Basic '
				+ window.btoa(userId + ':' + password));
		xhttp.setRequestHeader("Content-Type", "application/json");
		xhttp.send();
	}
	else{
		var responseMessage = document.getElementById('loginMessage');
		responseMessage.innerHTML = '<p> User Id/Password Empty';
		return;
	}
}


/* To log a member out from system. Saved user details are deleted from cache memory */
function logOut() {
	localStorage.clear();
	// navigate to login page after logout
	window.location.href = '../index.html';
}


/* To parse user details saved in cache memory */
function retrieveUserDetailsFromCache(){
	// retrieve usser role from cache
	var parsedCacheObject = JSON.parse(localStorage.getItem('loggedInUser'));
	
	if(parsedCacheObject == null){
		return;
	}
	for ( var key in parsedCacheObject) {
		if (parsedCacheObject.hasOwnProperty(key)) {
			
			var val = parsedCacheObject[key];
			if (key == 'userRole' && val != null) {
				return val;
			}
			
		}
	}
	return;
}


function callService(xhttp, method, url, data){
	// retrieve userRole from cache and set to header
	var userRole = retrieveUserDetailsFromCache();
	if(userRole != undefined && userRole != null){
		showLoader();
		xhttp.open(method, url, true);
		xhttp.setRequestHeader('Authorization', 'Basic '
				+ window.btoa('Role' + ':' + userRole));
		xhttp.setRequestHeader("Content-Type", "application/json");
		
		if(data != null | data != undefined){
			xhttp.send(JSON.stringify(data));
		}
		else{
			xhttp.send();
		}
	}
}

function showLoader(){
	document.getElementById("loader").style.display = "block";
}

function hideLoader(){
	document.getElementById("loader").style.display = "none";
}

function showUtilityButtons(tableId, domOptions){
	var id = "#"+tableId;
	$(id).dataTable({
		paging: false,
        bFilter: false,
        ordering: false,
        searching: true,
	    dom: domOptions,
		processing: true,
		 buttons: [
			
			{
				extend: 'copyHtml5',
				title: 'Members '
			},
			{
				extend: 'excelHtml5',
				title: 'Members '
			},
			
			{
				extend: 'pdfHtml5',
				title: 'Members ',
			},
			{
				extend: 'print',
				text: 'Print',
				title: 'Members ',
			}
		]
	});
}