/**
Copyright: Agile City District-6
2017

File Name: load_data_from_csv.js
************************************************
Change Date		Name		Description
20/10/2017		Swati Y		Initial Creation
10/11/2017		Deepak S	Created functions -loadStorySummaryFromCSV: to read and display the story summary
											  -displayHeading: To show the heading on top of page
											  -createDemoURL: to create the demo URL of respective district & iteration
											  -locateDocumentURL: to locate document url for members and stories
03/12/2017		Deepak S	Created function  -loadGraphDataFromCSV: to crate graph for a member
											 
************************************************

*/

/* 
 * This method reads the data from a CSV file and creates a table
 * to be displayed in the html page. It also creates the template of
 * the file name or title to be copied, downloaded or printed.
 */
 
function loadCSVData(type, district_nr){
	var documenturl = locateDocumentURL(type);
	
	$(document).ready(function(){
		$.ajax({
			url:documenturl,
			dataType:"text",
			success:function(data)
			{
				var stored_data = data.split(/\r?\n|\r/);
				var table = document.getElementById("table1");
				var table_data = '';
				var row = '';
				var districtNrString = 'District ' + district_nr;
				for(var count = 0; count<stored_data.length; count++)
				{
					if(!$.isEmptyObject(stored_data[count]))
					{
					//split data using "|"
					  var cell_data = stored_data[count].split("|");
					  
					  //create table heading for member table
					  if(type == 'Members' & count == 0){
					   row+= '<tr><th>'+cell_data[0]+'</th> <th>'+cell_data[2]+'</th> <th>'+cell_data[3]+'</th><th>'+cell_data[4]+'</th><th>'+cell_data[5]+'</th>';
					   row+= '<th>'+cell_data[6]+'</th><th>'+cell_data[8]+'</th></tr>';
					  }
					  //create table heading for stories table
					  if(type == 'Stories' & count == 0){
					   row+= '<tr><th>'+cell_data[1]+'</th> <th>'+cell_data[2]+'</th> <th>'+cell_data[3]+'</th> <th>'+cell_data[4]+'</th> <th>'+cell_data[5]+'</th></tr>';
					  }
					  //start the table body
					  if(count==1){
						table_data += '<tbody>';
						row = '';
					  }
					  //filter details of member and append to row
					  if(type == 'Members' & cell_data[1] == district_nr){
					   row+= '<tr><td>'+cell_data[0]+'</td> <td contenteditable="true">'+cell_data[2]+'</td> <td contenteditable="true">'+cell_data[3]+'</td><td contenteditable="true">'+cell_data[4]+'</td>'
					   row+= '<td contenteditable="true">'+cell_data[5]+'</td><td contenteditable="true">'+cell_data[6]+'</td><td contenteditable="true">'+cell_data[8]+'</td></tr>';
					  }
					  //filter details of stories and append to row
					  if(type == 'Stories' & cell_data[0] == districtNrString){
					   row+= '<tr><td>'+cell_data[1]+'</td> <td>'+cell_data[2]+'</td> <td contenteditable="true">'+cell_data[3]+'</td> <td contenteditable="true">'+cell_data[4]+'</td> <td contenteditable="true">'+cell_data[5]+'</td> </tr>'
					   //row+= '<td contenteditable="true">'+cell_data[5]+'</td></tr>';
					  }
  
					 //end of table head
					 if(count==0){
						table_data += '<thead>' + row + '</thead>';
					 }
					 //end of table body
					 if(count==(stored_data.length-1)){
						table_data += row +'</tbody>';			
					 }
				  }
				}
			    //alert("count:"+count +" and table data:"+table_data)
				//inser the table to DOM
				table.innerHTML = table_data;
				
				//put export buttons
				$("#table1").dataTable({
					dom: 'Bfrtip',
					processing: true,
					 buttons: [
						
						{
							extend: 'copyHtml5',
							title: 'District ' + district_nr + ' ' + type
						},
						{
							extend: 'excelHtml5',
							title: 'District ' + district_nr + type,
							filename: function(){
								var d = new Date();
								var n = d.getDate()+"-"+(d.getMonth()+1)+"-"+d.getFullYear();
								return 'District_' + district_nr + '_' + type + '_' + n;
							}
						},
						{
							extend: 'csvHtml5',
							title: 'District ' + district_nr + ' ' + type,
							exportOptions: {
								modifier: {
									selected: true
								}
							},
							filename: function(){
								var d = new Date();
								var n = d.getDate()+"-"+(d.getMonth()+1)+"-"+d.getFullYear();
								return 'District_' + district_nr + '_' + type + '_' + n;
							}
						},
						{
							extend: 'pdfHtml5',
							title: 'District ' + district_nr + ' ' +  type 
						},
						{
							extend: 'print',
							title: 'District ' + district_nr + ' ' + type,
							text: 'Print'
						}
					]
				});

			}
		});

	});

}

/* return respective document url for members & stories*/
function locateDocumentURL(type){
	var URL = '';
	if(type == 'Members'){
		URL = '../CSV data/team_members.csv';
	}
	else if(type == 'Stories'){
		URL = '../CSV data/stories1.csv';
	}
	else if(type == 'story_summary'){
		URL = '../CSV data/story_summary.csv';
	}
	
	return URL;
}
 

/* To create the hading for respective district number. ex- District 6 */
function displayHeading(district_nr, type){

	//where to display the heading
	var htmlHeading = document.getElementById('heading');
	var docDropdownHeading = document.getElementById('docDropdown');
	
	//heading for team members page
	if(type == 'members'){
		var heading = 'District ' + district_nr + ' Team Members';
	}
	//heading for stories page
	else if(type == 'stories'){
		var heading = 'District ' + district_nr + ' Stories';
	}
	//heading for demo page
	else if(type == 'demo'){
		var heading = 'District ' + district_nr + ' Demo';
	}

	//heading for team home page
	else if(type == 'dashboard'){
		var heading = 'District ' + district_nr + ' Team Dashboard';
	}

	//heading for document dropdown
	else if(type == 'docDropdown'){
		var dropdownHeading = 'District ' + district_nr + ' Documents';
		docDropdownHeading.innerHTML = dropdownHeading;
	}
	
	//append the html data for respective heading DOM
	htmlHeading.innerHTML = heading;	
}

/* To create the URL of the demo document for respective district and iteration */
function createDemoURL(district_nr, iteration_nr){
	var URL = '../demo documents/Demo_District_'+ district_nr +'_Iteration_' + iteration_nr + '.pdf';
	return URL;
}

/*  This function loads the story summaary from the CSV file, 
	filters it based on district number and iteration number 
	and displays it in respective iteration.
*/
function loadStorySummaryFromCSV(district_nr, iteration_nr){
	//location of the story summary CSV file
	var documenturl = locateDocumentURL('story_summary');
	
	//location of the demo document
	var demo_url = createDemoURL(district_nr, iteration_nr);
	$(document).ready(function(){
		$.ajax({
			url:documenturl,
			dataType:"text",
			success:function(data)
			{
				//id in the demo html page where the data is to be dispalyed
				var id = "load_iteration_"+iteration_nr;
				var iteration = document.getElementById(id);
				var html_data = '';
				html_data += '<p><a target="_blank" href="'+ demo_url + '" style="color:#337ab7;text-decoration:underline;">Click to View Demo Presentation</a></p> <h4>Iteration Stories : -</h4>'
				var story_data = data.split(/\r?\n|\r/);
				
				//start the count from 1 to exclude the heading row
				for(var count = 1; count<story_data.length; count++){
					if(!$.isEmptyObject(story_data[count])){
						var cell_data = story_data[count].split("|");
						// filter the data based on district nr and iteration nr and then append to html_data
						if(cell_data[0] == district_nr & cell_data[1] == iteration_nr){
							html_data += cell_data[2] + '. ' + cell_data[3] +'<br>';
						}						
					}				
				}
				//open the demo document in a new tab when the link is clicked
				iteration.innerHTML = html_data;
			}
		});	
	});	
}

/* 
 * This method reads the member data from a CSV file and creates a bar-graph
 * to be displayed in the html page.
 */
function loadGraphDataFromCSV(district_nr){
	
	var districtNrString = 'District ' + district_nr;
	var documenturl = locateDocumentURL('Members');
	var barChartData = null;
	var createdLabels =[];
	var createdDatasets = [];
	//data for inner object
	var createdDataKms = [];
	var createdDataHrs = [];
	var createdDataFresco = [];
	$(document).ready(function(){
		$.ajax({
			url:documenturl,
			dataType:"text",
			success:function(data)
			{
				var member_data = data.split(/\r?\n|\r/);
				for(var count = 0; count<member_data.length; count++){
				  if(!$.isEmptyObject(member_data[count])){
					var cell_data = member_data[count].split("|");
					if(cell_data[1] === district_nr){
						//push the names as labels of graph
						createdLabels.push(cell_data[2].split(' ').pop());
						//push kms, hrs and points value to dataset
						createdDataFresco.push(cell_data[5]);
						createdDataKms.push(cell_data[6]);
						createdDataHrs.push(cell_data[8]);
					}									
				  }				
				}
			}
		});	
	});	

	//push the data found for kms, hrs and points
	createdDatasets.push({ 
							label: 'Fresco Play Points',
							backgroundColor: window.chartColors.yellow,
							data: createdDataFresco
							}, {
							label: 'Fit4Life kms',
							backgroundColor: window.chartColors.red,
							data: createdDataKms
							}, {
							label: 'Fit4Life hrs',
							backgroundColor: window.chartColors.grey,
							data: createdDataHrs
							}
						);
						
	//assign createDatasets and createdLabels to barChartData
	barChartData = {
            labels: createdLabels,
            datasets: createdDatasets
        };
		
		window.onload = function() {
            var ctx = document.getElementById("myChart").getContext("2d");
            window.myBar = new Chart(ctx, {
                type: 'bar',
                data: barChartData,
                options: {
                    title:{
                        display:true,
                        text:"Learning & Fitness Activities"
                    },
                    tooltips: {
                        mode: 'index',
                        intersect: false
                    },
                    responsive: true,
					axisX:{
					title: "Names----->"
					},
					axisY:{
					title: "Kms/Hrs/Points----->"
					},
                    scales: {
                        xAxes: [{
                            stacked: true,
							title:'Name'
                        }],
                        yAxes: [{
                            stacked: true
                        }]
                    }
                }
            });
        };
	
	return barChartData;
}

/*
function createPrimaryLebels(district_nr){
	var documenturl = locateDocumentURL('Members');
	var labels =[]; //var labels = ['January', "February", "March", "April", "May", "June", "July"];
	var districtNrString = 'District ' + district_nr;
	$(document).ready(function(){
		$.ajax({
			url:documenturl,
			dataType:"text",
			success:function(data)
			{
				var member_data = data.split(/\r?\n|\r/);
				for(var count = 1; count<member_data.length; count++){
				  if(!$.isEmptyObject(member_data[count])){
					var cell_data = member_data[count].split("|");
					if(cell_data[1] === districtNrString){
						//alert('count_membr found:'+ memberFound + ',cell data:' + cell_data[2] + ', labels created:' + labels);
						labels.push(cell_data[2].split(' ').pop());
					}									
				  }				
				}
			}
		});	
	});	
	
	return labels;
}

function createDatasets(){
	
	var documenturl = locateDocumentURL('Members');
	var dataset =[];
	var districtNrString = 'District ' + district_nr;
	$(document).ready(function(){
		$.ajax({
			url:documenturl,
			dataType:"text",
			success:function(data)
			{
				var member_data = data.split(/\r?\n|\r/);
				//alert('here1');
				for(var count = 0; count<member_data.length; count++){
				  if(!$.isEmptyObject(member_data[count])){
					var cell_data = member_data[count].split("|");
					
					if(count==0){
						alert('here1');
						dataset.push({ 
							label: 'Fit4Life kms',
							backgroundColor: window.chartColors.red,
							data: [12, 19, 3, 5, 2, 3,10]
							}, {
							label: 'Fit4Life hrs',
							backgroundColor: window.chartColors.blue,
							data: [1, 2, 3, 5, 7, 4]
							}, {
							label: 'Fresco Play Points',
							backgroundColor: window.chartColors.green,
							data: [18, 19, 15, 17, 18, 13]
							}
						);
					}
					if(cell_data[1] === districtNrString){
						dataset.push({ 
							label: 'Fit4Life kms',
							backgroundColor: window.chartColors.red,
							data: fetchDataOfDatasetFromCSV('kms')
							}, {
							label: 'Fit4Life hrs',
							backgroundColor: window.chartColors.blue,
							data: fetchDataOfDatasetFromCSV('hrs')
							}, {
							label: 'Fresco Play Points',
							backgroundColor: window.chartColors.green,
							data: fetchDataOfDatasetFromCSV('Fresco Play Points')
							}
						);
					}									
				  }				
				}
			}
		});	
	});			
	return dataset;
}

function fetchDataOfDatasetFromCSV(dataType){
	
	var data=[];
	if(dataType == 'kms'){
		fetchDataByPoition('position number');
	}
	
	else if(dataType == 'hrs'){
		fetchDataByPoition('position number');
	}
	
	else if(dataType == 'Fresco Play Points'){
		fetchDataByPoition('position number');
	}
	
	return data;
}


function fetchDataByPoition(position){
	
}

*/