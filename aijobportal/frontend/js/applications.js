// =====================================
// MY APPLICATIONS
// =====================================


const token =
localStorage.getItem("token");


const email =
localStorage.getItem("email");



if(!token || !email){

    window.location.href =
    "login.html";

}



// Load applications

document.addEventListener(
"DOMContentLoaded",
function(){

    loadApplications();

});




// =====================================
// LOAD APPLICATIONS
// =====================================


async function loadApplications(){


const container =
document.getElementById(
"applicationsContainer"
);


const message =
document.getElementById(
"message"
);



try{


message.textContent =
"Loading applications...";



const response =
await fetch(

`http://localhost:8080/api/applications/student/${email}`,

{


method:"GET",


headers:{


"Authorization":
"Bearer " + token


}


}

);



if(!response.ok){

throw new Error(
"Failed to load applications"
);

}



const applications =
await response.json();



console.log(
applications
);



message.textContent="";



displayApplications(
applications
);



}

catch(error){


console.error(error);


message.textContent =
"Unable to load applications.";


}


}




// =====================================
// DISPLAY APPLICATIONS
// =====================================


function displayApplications(applications){


const container =
document.getElementById(
"applicationsContainer"
);



container.innerHTML="";



if(applications.length===0){


container.innerHTML=`


<div class="no-jobs">


<h2>
No Applications Found
</h2>


<p>
Apply for jobs to see them here.
</p>


</div>


`;


return;


}



applications.forEach(function(app){



const card =
document.createElement(
"div"
);



card.className =
"job-card";



card.innerHTML = `


<h2>
Application ID: ${app.id}
</h2>



<p>

<strong>
Job ID:
</strong>

${app.jobId}

</p>



<p>

<strong>
Status:
</strong>

${app.status}

</p>



<p>

<strong>
Applied Date:
</strong>

${app.appliedAt}

</p>



`;



container.appendChild(card);



});



}




// =====================================
// DASHBOARD
// =====================================


function goDashboard(){


window.location.href =
"student-dashboard.html";


}



// =====================================
// LOGOUT
// =====================================


const logoutBtn =
document.getElementById(
"logoutBtn"
);



if(logoutBtn){


logoutBtn.onclick=function(){


localStorage.clear();


window.location.href =
"login.html";


};


}