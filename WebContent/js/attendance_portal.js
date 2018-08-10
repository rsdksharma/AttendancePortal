/**
Copyright: SYARS
2018

File Name: attendance_portal.js
************************************************
Change Date		Name		Description
08/08/2018		Deepak S.	Initial Creation

************************************************

*/

var BASE_URI = "http://localhost:8080/AttendancePortal-1.0.1-SNAPSHOT";
var ATTENDANCE_URI = BASE_URI+"/v1/rest/attendance";
var USERS_URI = BASE_URI+"/v1/rest/users";
var MEMBERS_URI = BASE_URI+"/v1/rest/members";

/**
* This file contains the java script functions related to 
* Attendance Portal functionalities.
*/

/* To retrieve member related details and save in cache memory*/
function retrieveAllMembers() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			hideLoader();
			var APIResponse = xhttp.responseText;
			APIResponse = JSON.parse(APIResponse);
			// Put the object into storage
			localStorage.setItem('testObject', JSON.stringify(APIResponse));
			// Retrieve the object from storage
			var parsedCacheObject = JSON.parse(localStorage
					.getItem('testObject'));
			console.log('parsedCacheObject: ', parsedCacheObject);

			var members = document.getElementById('retrieveAllMembers');
			var innerHTMLDOM = '<p>Retrieved members from cache:<br><br>';
			for ( var key in parsedCacheObject) {
				if (parsedCacheObject.hasOwnProperty(key)) {
					var val = parsedCacheObject[key];
					console.log('key_1:' + key + ',val_1:' + val);
					for ( var key2 in val) {
						if (val.hasOwnProperty(key2)
								&& (key2 == 'fullName' || key2 == 'memberID')
								&& val[key2] != null) {
							innerHTMLDOM += val[key2] + '<br>';
							console.log("innerHTML:" + innerHTMLDOM);
						}
					}
					innerHTMLDOM += '  <br>';
				}
			}
			members.innerHTML = innerHTMLDOM;
		}
	};
	
	var method = "GET";
	var url = MEMBERS_URI;
	
	// retrieve userRole from cache and set to header
	var userRole = retrieveUserDetailsFromCache();
	if(userRole != undefined && userRole != null){
		showLoader();
		xhttp.open(method, url, true);
		xhttp.setRequestHeader('Authorization', 'Basic '
				+ window.btoa('Role' + ':' + userRole));
		xhttp.setRequestHeader("Content-Type", "application/json");
		
		xhttp.send();
	}

}


/* To retrieve the number of attendance for today's date */
function displayCount() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		var count = document.getElementById('count');
		if (this.readyState == 4) {
			hideLoader();
			if(this.status == 200){
				console.log("count:" + this.responseText);
				count.innerHTML = '<p>Count for today is:' + this.responseText;
			}
			else{
				count.innerHTML = '<p>' + this.responseText;
			}
		}
	};
	var method = "GET";
	var url = ATTENDANCE_URI+"/count";

	// retrieve userRole from cache and set to header
	var userRole = retrieveUserDetailsFromCache();
	if(userRole != undefined && userRole != null){
		showLoader();
		xhttp.open(method, url, true);
		xhttp.setRequestHeader('Authorization', 'Basic '
				+ window.btoa('Role' + ':' + userRole));
		xhttp.setRequestHeader("Content-Type", "application/json");
		
		xhttp.send();
	}
}

/* To insert attendance for specific member in DB */
function insertAttendance() {
	
	// get memberId from input
	var memberId = document.getElementById('memberid').value;
	//create JSON data from inputs
	var data = {};
	data.memberId = memberId;

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			hideLoader();
			console.log("response:" + this.responseText);
			var message = document.getElementById('response');
			message.innerHTML = '<p>' + this.responseText;
		}
	};
	
	var method = "POST";
	var url = ATTENDANCE_URI;
	
	// retrieve userRole from cache and set to header
	var userRole = retrieveUserDetailsFromCache();
	if(userRole != undefined && userRole != null){
		showLoader();
		xhttp.open(method, url, true);
		xhttp.setRequestHeader('Authorization', 'Basic '
				+ window.btoa('Role' + ':' + userRole));
		xhttp.setRequestHeader("Content-Type", "application/json");
		
		// call service
		xhttp.send(JSON.stringify(data));
	}
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
			hideLoader();
			console.log("response:" + this.responseText);
			var responseMessage = document.getElementById('response');
			responseMessage.innerHTML = '<p>' + this.responseText;
		}
	};
	
	var method = "POST";
	var url = MEMBERS_URI;
	
	// retrieve userRole from cache and set to header
	var userRole = retrieveUserDetailsFromCache();
	if(userRole != undefined && userRole != null){
		showLoader();
		xhttp.open(method, url, true);
		xhttp.setRequestHeader('Authorization', 'Basic '
				+ window.btoa('Role' + ':' + userRole));
		xhttp.setRequestHeader("Content-Type", "application/json");
		
		// call service
		xhttp.send(JSON.stringify(data));
	}
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
			hideLoader();
			// Typical action to be performed when the document is ready:
			console.log("response:" + this.responseText);
			var responseMessage = document.getElementById('response');
			responseMessage.innerHTML = '<p>' + this.responseText;
		}
	};
	
	// create JASON data from input supplied
	var data = {};
	data.memberId = document.getElementById('memberId').value;
	data.userRole = document.getElementById('userRole').value;
	data.password = document.getElementById('password').value;
	data.customizedUserId = document.getElementById('customizedUserId').value;

	// retrieve userRole from cache and set to header
	var userRole = retrieveUserDetailsFromCache();
	if(userRole != undefined && userRole != null){
		showLoader();
		xhttp.open(method, url, true);
		xhttp.setRequestHeader('Authorization', 'Basic '
				+ window.btoa('Role' + ':' + userRole));
		xhttp.setRequestHeader("Content-Type", "application/json");
		
		// call service
		xhttp.send(JSON.stringify(data));
	}

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
			hideLoader();
			
			if (this.status == 200) {
				var APIResponse = JSON.parse(this.responseText);
				// Put the object into cache storage
				localStorage.setItem('loggedInUser', JSON
						.stringify(APIResponse));

				// navigate to home page after login
				window.location.href = './html/loggedin.html';
			}
			else {
				var responseMessage = document.getElementById('loginMessage');
				responseMessage.innerHTML = '<p>' + this.responseText;
			}
		}
	};
	var url = USERS_URI;
	url += "/"+userId;
	if(userId != "" && userId != undefined && password != "" && password != undefined){
		showLoader();
		xhttp.open("GET", url, true);
		xhttp.setRequestHeader('Authorization', 'Basic '
				+ window.btoa(userId + ':' + password));
		xhttp.setRequestHeader("Content-Type", "application/json; charset=utf-8");
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
	localStorage.removeItem('loggedInUser');
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


function showLoader(){
	document.getElementById("loader").className="loader";
	document.getElementById("loader").style.visibility = "visible";
	document.getElementById("bodycontents").style.visibility = "hidden";
}

function hideLoader(){
	document.getElementById("loader").className="";
	document.getElementById("bodycontents").style.visibility = "visible";
}


