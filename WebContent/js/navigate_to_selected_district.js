/**
Copyright: Agile City District-6
2017

File Name: navigate_to_selected_district.js
************************************************
Change Date		Name		Description
10/11/2017		Deepak S	Initial Creation

************************************************

*/

/**
* This file contains the java script functions which decides
* where the page will navigate on click of a submenu of a district.
* It also saves the district number in the local storage which is
* used in the navigating page.
*/
function navigateToSelectedDistrict( district_nr, document_type){
	
	//alert('inside navigateToSelectedDistrict district nr:'+ district_nr + ', document_type:' +document_type);
	//clear existing local storage for district
	localStorage.removeItem("district");
	
	//form the URL
	var location = document_type + ".html";  
	//alert('navigating page:'+ location);
	
	//set district to local storage for accessing in routing page
	localStorage.setItem("district",district_nr);
	
	//route to specified URL
	window.location.href = location;
}

/**
* This file contains the java script functions which decides
* where the page will navigate on click of a submenu of a district.
* It also saves the district number in the local storage which is
* used in the navigating page.
*/
function navigateToClickedDistrict( district_nr){
	
	//alert('inside navigateToSelectedDistrict district nr:'+ district_nr);
	//clear existing local storage for district
	localStorage.removeItem("district");
	
	//form the URL
	var location = "team_home_with_dropdown.html";  
	//alert('navigating page:'+ location);
	
	//set district to local storage for accessing in routing page
	localStorage.setItem("district",district_nr);
	
	//route to specified URL
	window.location.href = location;
}


/**
* This file contains the java script functions which decides
* where the page will navigate on click of a submenu of a district.
* It also saves the district number in the local storage which is
* used in the navigating page.
*/
function navigateToSelectedDocument(document_type){
	
	//alert('inside navigateToSelectedDistrict document_type:'+ document_type);
	//clear existing local storage for district
	//localStorage.removeItem("district");
	
	//form the URL
	var location = document_type + "_with_dropdown.html";  
	//alert('navigating page:'+ location);
	
	//set district to local storage for accessing in routing page
	//localStorage.setItem("district",district_nr);
	
	//route to specified URL
	window.location.href = location;
}