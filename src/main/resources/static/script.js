var btn = document.getElementById("forum");
btn.addEventListener("click", function1);
function function1() {
    alert("Please sign in before use forum");

    //mouseover
    

}

var btn1 = document.getElementById("adminpanel");
btn1.addEventListener("click", function2);
function function2() {
    alert("You cannot log in unless you are an admin");

}



/*<!--Admin panel reject confirmation-->
function Function() {
  if (confirm('Are you sure to Reject this file? This will be cannot undo')) {
    // Save it!
    console.log('Thing was saved to the database.');
  } else {
    // Do nothing!
    console.log('Thing was not saved to the database.');
  }
}*/

function Function2() {
 var result= confirm("Are you Sure? This file will be visible to the public");
 if(result== false){
     event.preventDefault();
 }
}

<!--File approve confirmation-->
function approveChecker() {
 var result= confirm("Are you Sure? This file will be visible to the public");
 if(result== false){
     event.preventDefault();
 }
}

<!--File reject confirmation-->
function rejectChecker() {
 var result= confirm("Are you Sure? This file will be remove from this list");
 if(result== false){
     event.preventDefault();
 }
}



 <!-- Password hide and unhide-->
 function hideUnhideFunction(){
 var x = document.getElementById("myInput");
 var y = document.getElementById("hide1");
 var z = document.getElementById("hide2");

         if(x.type== 'password'){
         x.type="text"
         y.style.display="block";
         z.style.display="none";

         }else{
             x.type="password"
            y.style.display="none";
            z.style.display="block";


         }

 }

/*<!--Forum comment submission-->
var comment = document.getElementById("submitComment");
comment.addEventListener("click", function1);
function functionPostComment() {
    alert("Please sign in before use forum");*/

   /*$('#myModal').appendTo("body")*/









