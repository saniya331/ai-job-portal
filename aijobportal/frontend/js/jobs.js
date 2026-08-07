// =====================================
// JOBS PAGE
// =====================================


// Check login

const token = localStorage.getItem("token");

if (!token) {

    window.location.href = "login.html";

}


// Load jobs when page opens

document.addEventListener("DOMContentLoaded", function () {

    loadJobs();

});


// =====================================
// LOAD ALL JOBS
// =====================================

async function loadJobs() {

    const container =
        document.getElementById("jobsContainer");

    const message =
        document.getElementById("message");


    try {

        message.textContent = "Loading jobs...";


        const response = await fetch(
            "http://localhost:8080/api/jobs",
            {
                method: "GET",

                headers: {

                    "Authorization":
                        "Bearer " + token

                }

            }
        );


        if (!response.ok) {

            throw new Error(
                "Failed to load jobs"
            );

        }


        const jobs =
            await response.json();


        console.log(
            "Jobs from backend:",
            jobs
        );


        message.textContent = "";


        displayJobs(jobs);


    }
    catch(error) {

        console.error(
            "Error:",
            error
        );


        message.textContent =
            "Unable to load jobs.";

    }

}


// =====================================
// DISPLAY JOB CARDS
// =====================================

function displayJobs(jobs) {


    const container =
        document.getElementById("jobsContainer");


    container.innerHTML = "";


    if(jobs.length === 0) {

        container.innerHTML = `

        <div class="no-jobs">

            <h2>No jobs available</h2>

            <p>Please check later.</p>

        </div>

        `;

        return;

    }



    jobs.forEach(function(job){


        const card =
            document.createElement("div");


        card.className =
            "job-card";



        card.innerHTML = `


        <h2>
            ${job.title}
        </h2>


        <p>
            <strong>Company:</strong>
            ${job.company}
        </p>


        <p>
            <strong>Location:</strong>
            ${job.location}
        </p>


        <p>
            <strong>Description:</strong>
            ${job.description}
        </p>


        <p>
            <strong>Skills:</strong>
            ${job.requiredSkills}
        </p>


        <p>
            <strong>Salary:</strong>
            ₹${job.salary}
        </p>


        <button onclick="applyForJob(${job.id})">

            Apply Now

        </button>


        `;



        container.appendChild(card);


    });


}



// =====================================
// SEARCH JOBS
// =====================================

function searchJobs() {

    const searchInput =
        document.getElementById("searchInput");


    const searchText =
        searchInput.value
        .toLowerCase()
        .trim();


    const jobCards =
        document.querySelectorAll(".job-card");


    jobCards.forEach(function(card) {


        const jobText =
            card.innerText.toLowerCase();


        if(jobText.includes(searchText)) {


            card.style.display = "block";


        } else {


            card.style.display = "none";


        }


    });

}

// =====================================
// APPLY JOB
// =====================================

async function applyForJob(jobId){


    const email =
        localStorage.getItem("email");



    if(!email){


        alert(
            "Login required"
        );


        window.location.href =
            "login.html";


        return;

    }



    const application = {


        jobId: jobId,


        studentEmail: email


    };



    try{


        const response =
            await fetch(

            "http://localhost:8080/api/applications",

            {

                method:"POST",


                headers:{


                    "Content-Type":
                    "application/json",


                    "Authorization":
                    "Bearer " + token


                },


                body:
                JSON.stringify(application)

            }


        );



        const data =
            await response.json();



        console.log(
            "Application Response:",
            data
        );



        if(!response.ok){


            alert(
                data.message ||
                data ||
                "Already applied"
            );


            return;


        }



        alert(
            "Application submitted successfully!"
        );


    }

    catch(error){


        console.error(error);


        alert(
            "Application failed"
        );


    }


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
document.getElementById("logoutBtn");


if(logoutBtn){


logoutBtn.addEventListener(
"click",
function(){


    localStorage.removeItem("token");

    localStorage.removeItem("email");

    localStorage.removeItem("fullName");

    localStorage.removeItem("role");


    window.location.href =
        "login.html";


});


}