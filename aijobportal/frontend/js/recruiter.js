// =====================================
// AUTH CHECK
// =====================================


const token =
localStorage.getItem("token");


if(!token){

window.location.href =
"login.html";

}




// =====================================
// OPEN POST JOB PAGE
// =====================================


function postJobPage(){

window.location.href =
"post-job.html";

}




// =====================================
// POST JOB
// =====================================


async function postJob(){



const job = {


title:
document.getElementById("title").value,


company:
document.getElementById("company").value,


location:
document.getElementById("location").value,


description:
document.getElementById("description").value,


requiredSkills:
document.getElementById("requiredSkills").value,


salary:
document.getElementById("salary").value


};




try{


const response =
await fetch(
"http://localhost:8080/api/jobs",
{


method:"POST",


headers:{


"Content-Type":
"application/json",


"Authorization":
"Bearer " + token


},


body:
JSON.stringify(job)


}

);



const data =
await response.json();



if(!response.ok){


throw new Error(
data.message ||
"Job posting failed"
);


}



document.getElementById("message")
.innerHTML =
"Job Posted Successfully ✅";



console.log(data);



}



catch(error){


console.error(error);


document.getElementById("message")
.innerHTML =
error.message;


}



}




// =====================================
// LOGOUT
// =====================================


const logoutBtn =
document.getElementById("logoutBtn");


if(logoutBtn){


logoutBtn.onclick=function(){


localStorage.clear();


window.location.href =
"login.html";


};


}