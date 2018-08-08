/**
Copyright: SYARS
2018

File Name: attendance_portal.js
************************************************
Change Date		Name		Description
08/08/2018		Deepak S.	Initial Creation

************************************************

*/

/**
* This file contains the java script functions related to 
* Attendance Portal functionalities.
*/

/* To retrieve member related details and save in cache memory*/
function retrieveAllMembers() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById("loader").style.visibility = "hidden";
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
	var url = "http://attendanceportal-env.kehejuewsh.ap-south-1.elasticbeanstalk.com/v1/rest/members";
	
	// retrieve userId, password from cache and set to header
	var idPassword = retrieveUserIdPasswordFromCache();
	if(idPassword != undefined){
		document.getElementById("loader").style.visibility = "visible";
		xhttp.open(method, url, true);
		xhttp.setRequestHeader('Authorization', 'Basic '
				+ window.btoa('Role' + ':' + idPassword[2]));
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
			document.getElementById("loader").style.visibility = "hidden";
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
	var url = "http://attendanceportal-env.kehejuewsh.ap-south-1.elasticbeanstalk.com/v1/rest/attendance/count";
	
	// retrieve userId, password from cache and set to header
	var idPassword = retrieveUserDetailsFromCache();
	if(idPassword != undefined){
		document.getElementById("loader").style.visibility = "visible";
		xhttp.open(method, url, true);
		xhttp.setRequestHeader('Authorization', 'Basic '
				+ window.btoa('Role' + ':' + idPassword[2]));
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
			document.getElementById("loader").style.visibility = "hidden";
			console.log("response:" + this.responseText);
			var message = document.getElementById('response');
			message.innerHTML = '<p>' + this.responseText;
		}
	};
	
	var method = "POST";
	var url = "http://attendanceportal-env.kehejuewsh.ap-south-1.elasticbeanstalk.com/v1/rest/attendance";
	
	// retrieve userId, password from cache and set to header
	var idPassword = retrieveUserDetailsFromCache();
	if(idPassword != undefined){
		document.getElementById("loader").style.visibility = "visible";
		xhttp.open(method, url, true);
		xhttp.setRequestHeader('Authorization', 'Basic '
				+ window.btoa('Role' + ':' + idPassword[2]));
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
			document.getElementById("loader").style.visibility = "hidden";
			console.log("response:" + this.responseText);
			var responseMessage = document.getElementById('response');
			responseMessage.innerHTML = '<p>' + this.responseText;
		}
	};
	
	var method = "POST";
	var url = "http://attendanceportal-env.kehejuewsh.ap-south-1.elasticbeanstalk.com/v1/rest/members";
	
	// retrieve userId, password from cache and set to header
	var idPassword = retrieveUserDetailsFromCache();
	if(idPassword != undefined){
		document.getElementById("loader").style.visibility = "visible";
		xhttp.open(method, url, true);
		xhttp.setRequestHeader('Authorization', 'Basic '
				+ window.btoa('Role' + ':' + idPassword[2]));
		xhttp.setRequestHeader("Content-Type", "application/json");
		
		// call service
		xhttp.send(JSON.stringify(data));
	}
}


/* To register a member as user in the system. This is also used to
 * update the user details.
 */
function registerUser(action) {
	var url = "http://attendanceportal-env.kehejuewsh.ap-south-1.elasticbeanstalk.com/v1/rest/users";
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
			document.getElementById("loader").style.visibility = "hidden";
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

	// retrieve userId, password from cache and set to header
	var idPassword = retrieveUserDetailsFromCache();
	if(idPassword != undefined){
		document.getElementById("loader").style.visibility = "visible";
		xhttp.open(method, url, true);
		xhttp.setRequestHeader('Authorization', 'Basic '
				+ window.btoa('Role' + ':' + idPassword[2]));
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
			document.getElementById("loader").style.visibility = "hidden";
			
			if (this.status == 200) {
				var APIResponse = JSON.parse(this.responseText);
				// Put the object into cache storage
				localStorage.setItem('loggedInMember', JSON
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
	var url = "http://attendanceportal-env.kehejuewsh.ap-south-1.elasticbeanstalk.com/v1/rest/users/";
	url += userId;
	if(userId != null && userId != undefined && password != null && password != undefined){
		document.getElementById("loader").style.visibility = "visible";
		xhttp.open("GET", url, true);
		xhttp.setRequestHeader('Authorization', 'Basic '
				+ window.btoa(userId + ':' + password));
		xhttp.setRequestHeader("Content-Type", "application/json; charset=utf-8");
		xhttp.send();
	}
}


/* To log a member out from system. Saved user details are deleted from cache memory */
function logOut() {
	localStorage.removeItem('loggedInMember');
	// navigate to login page after logout
	window.location.href = '../index.html';
}


/* To parse user details saved in cache memory */
function retrieveUserDetailsFromCache(){
	// retrieve userId, password from cache
	var parsedCacheObject = JSON.parse(localStorage.getItem('loggedInMember'));
	
	var userId;
	var password;
	var role;
	if(parsedCacheObject == null){
		return;
	}
	for ( var key in parsedCacheObject) {
		if (parsedCacheObject.hasOwnProperty(key)) {
			
			var val = parsedCacheObject[key];
			if (key == 'customizedUserId' && val != null) {
				userId = val;
			} else if (key == 'userId' && val != null) {
				userId = val;
			} else if (key == 'password' && val != null) {
				password = val;
			}else if (key == 'userRole' && val != null) {
				role = val;
			}
			
		}
	}
	return [userId,password,role];
}


