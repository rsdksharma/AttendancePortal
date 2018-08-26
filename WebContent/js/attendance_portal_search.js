var keyUpCount = 0;
function searchMember(){
	$(document).ready(function(){
		$("#searchInput").on("keyup", function() {
			keyUpCount = $(this).val().length;
			if(keyUpCount > 0){
				document.getElementById("registeredMembersTableDiv").style.display = "block";
				var value = $(this).val().toLowerCase();
				$("#searchableBody tr").filter(function() {
					$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
				});
			}
			else{
				document.getElementById("registeredMembersTableDiv").style.display = "none";
			}
		});
	});
}