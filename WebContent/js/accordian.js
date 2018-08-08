/**
Copyright: Agile City District-6
2017

File Name: accordian.js
************************************************
Change Date		Name		Description
04/11/2017		Deepak S	Initial Creation

************************************************

*/

/**
* This file contains the java script functions related to 
* accordian and its panel.
*/

/* To run accordion: expand-collapse functionality */
function createAccordian(){
	var acc = document.getElementsByClassName("accordion");
	var i;
	for (i = 0; i < acc.length; i++) {
	  acc[i].onclick = function() {
		this.classList.toggle("active");
		var panel = this.nextElementSibling;
		if (panel.style.maxHeight){
		  panel.style.maxHeight = null;
		} else {
		  panel.style.maxHeight = panel.scrollHeight + "px";
		} 
	  }
	}
}
	