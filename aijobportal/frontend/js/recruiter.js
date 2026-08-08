// =====================================
// RECRUITER AUTH CHECK
// =====================================

const token = localStorage.getItem("token");
const role = localStorage.getItem("role");

if (!token) {
    window.location.href = "login.html";
}

if (role !== "RECRUITER") {

    alert("Access denied. Recruiter account required.");

    window.location.href = "student-dashboard.html";
}


// =====================================
// NAVIGATION
// =====================================

function postJobPage() {

    window.location.href = "post-job.html";

}


function viewMyJobs() {

    window.location.href = "my-posted-jobs.html";

}


function goRecruiterDashboard() {

    window.location.href = "recruiter-dashboard.html";

}


// =====================================
// POST JOB
// =====================================

async function postJob() {

    const title =
        document.getElementById("title").value.trim();

    const company =
        document.getElementById("company").value.trim();

    const location =
        document.getElementById("location").value.trim();

    const description =
        document.getElementById("description").value.trim();

    const requiredSkills =
        document.getElementById("requiredSkills").value.trim();

    const salary =
        document.getElementById("salary").value;


    // Check fields

    if (
        !title ||
        !company ||
        !location ||
        !description ||
        !requiredSkills ||
        !salary
    ) {

        document.getElementById("message").textContent =
            "Please fill all fields.";

        return;

    }


    // Create job object

    const job = {

        title: title,

        company: company,

        location: location,

        description: description,

        requiredSkills: requiredSkills,

        salary: Number(salary)

    };


    try {

        const response = await fetch(
            "http://localhost:8080/api/jobs",
            {

                method: "POST",

                headers: {

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


        console.log(
            "POST JOB RESPONSE:",
            data
        );


        if (!response.ok) {

            throw new Error(
                data.message ||
                "Job posting failed"
            );

        }


        document.getElementById("message").textContent =
            "Job Posted Successfully ✅";


        console.log(
            "Created Job:",
            data
        );


        // Clear form

        document.getElementById("title").value = "";

        document.getElementById("company").value = "";

        document.getElementById("location").value = "";

        document.getElementById("description").value = "";

        document.getElementById("requiredSkills").value = "";

        document.getElementById("salary").value = "";

    }


    catch (error) {

        console.error(
            "POST JOB ERROR:",
            error
        );


        document.getElementById("message").textContent =
            error.message ||
            "Unable to post job.";

    }

}


// =====================================
// LOAD MY POSTED JOBS
// =====================================

async function loadMyJobs() {

    const container =
        document.getElementById("myJobsContainer");

    const message =
        document.getElementById("message");


    // Only run on My Posted Jobs page

    if (!container) {

        return;

    }


    // Get recruiter email

    const email =
        localStorage.getItem("email");


    if (!email) {

        message.textContent =
            "Recruiter email not found.";

        return;

    }


    try {

        message.textContent =
            "Loading your jobs...";


        console.log(
            "Loading jobs for:",
            email
        );


        const response = await fetch(
            "http://localhost:8080/api/jobs/employer/"
            + encodeURIComponent(email),
            {

                method: "GET",

                // Prevent browser cache

                cache: "no-store",

                headers: {

                    "Authorization":
                        "Bearer " + token,

                    "Cache-Control":
                        "no-cache"

                }

            }
        );


        console.log(
            "GET MY JOBS STATUS:",
            response.status
        );


        if (!response.ok) {

            throw new Error(
                "Unable to load your jobs."
            );

        }


        const jobs =
            await response.json();


        console.log(
            "LATEST JOBS FROM SERVER:",
            jobs
        );


        message.textContent = "";


        displayMyJobs(jobs);

    }


    catch (error) {

        console.error(
            "LOAD JOBS ERROR:",
            error
        );


        message.textContent =
            error.message;

    }

}


// =====================================
// DISPLAY MY JOBS
// =====================================

function displayMyJobs(jobs) {

    const container =
        document.getElementById("myJobsContainer");


    if (!container) {

        return;

    }


    // Clear old cards

    container.innerHTML = "";


    // No jobs

    if (!jobs || jobs.length === 0) {

        container.innerHTML = `

            <div class="no-jobs">

                <h2>
                    No jobs posted yet
                </h2>

                <p>
                    Click "Post Job" to create
                    your first job.
                </p>

            </div>

        `;

        return;

    }


    // Display each job

    jobs.forEach(function(job) {

        const card =
            document.createElement("div");


        card.className =
            "job-card";


        card.innerHTML = `

            <h2>
                ${job.title || "Job Title"}
            </h2>

            <p>
                <strong>Company:</strong>
                ${job.company || "Not specified"}
            </p>

            <p>
                <strong>Location:</strong>
                ${job.location || "Not specified"}
            </p>

            <p>
                <strong>Description:</strong>
                ${job.description || "No description"}
            </p>

            <p>
                <strong>Skills:</strong>
                ${job.requiredSkills || "Not specified"}
            </p>

            <p>
                <strong>Salary:</strong>
                ₹${job.salary || 0}
            </p>

            <div class="job-actions">

                <button
                    onclick="editJob(${job.id})">

                    Edit

                </button>


                <button
                    onclick="deleteJob(${job.id})">

                    Delete

                </button>

            </div>

        `;


        container.appendChild(card);

    });

}


// =====================================
// EDIT JOB
// =====================================

async function editJob(id) {

    console.log(
        "EDIT CLICKED - ID:",
        id
    );


    // Ask for new title

    const title =
        prompt("Enter new job title:");


    if (!title || !title.trim()) {

        return;

    }


    // Ask for company

    const company =
        prompt("Enter company name:");


    if (!company || !company.trim()) {

        return;

    }


    // Ask for location

    const location =
        prompt("Enter location:");


    if (!location || !location.trim()) {

        return;

    }


    // Ask for description

    const description =
        prompt("Enter job description:");


    if (!description || !description.trim()) {

        return;

    }


    // Ask for skills

    const requiredSkills =
        prompt("Enter required skills:");


    if (!requiredSkills || !requiredSkills.trim()) {

        return;

    }


    // Ask for salary

    const salary =
        prompt("Enter salary:");


    if (!salary) {

        return;

    }


    // Create updated job object

    const job = {

        title:
            title.trim(),

        company:
            company.trim(),

        location:
            location.trim(),

        description:
            description.trim(),

        requiredSkills:
            requiredSkills.trim(),

        salary:
            Number(salary)

    };


    console.log(
        "SENDING UPDATE:"
    );

    console.log(
        "Job ID:",
        id
    );

    console.log(
        "Job Data:",
        job
    );


    try {

        // PUT request

        const response =
            await fetch(
                "http://localhost:8080/api/jobs/"
                + id,
                {

                    method: "PUT",

                    headers: {

                        "Content-Type":
                            "application/json",

                        "Authorization":
                            "Bearer " + token

                    },

                    body:
                        JSON.stringify(job)

                }
            );


        console.log(
            "PUT STATUS:",
            response.status
        );


        // Read response

        const data =
            await response.json();


        console.log(
            "UPDATED JOB FROM SERVER:",
            data
        );


        // Check response

        if (!response.ok) {

            throw new Error(
                data.message ||
                "Unable to update job"
            );

        }


        alert(
            "Job updated successfully ✅"
        );


        // IMPORTANT:
        // Load fresh data from database

        await loadMyJobs();

    }


    catch (error) {

        console.error(
            "EDIT ERROR:",
            error
        );


        alert(
            "Unable to update job: "
            + error.message
        );

    }

}


// =====================================
// DELETE JOB
// =====================================

async function deleteJob(id) {

    console.log(
        "DELETE CLICKED - ID:",
        id
    );


    const confirmDelete =
        confirm(
            "Are you sure you want to delete this job?"
        );


    if (!confirmDelete) {

        return;

    }


    try {

        const response =
            await fetch(
                "http://localhost:8080/api/jobs/"
                + id,
                {

                    method: "DELETE",

                    headers: {

                        "Authorization":
                            "Bearer " + token

                    }

                }
            );


        console.log(
            "DELETE STATUS:",
            response.status
        );


        if (!response.ok) {

            throw new Error(
                "Unable to delete job"
            );

        }


        alert(
            "Job deleted successfully ✅"
        );


        // Reload fresh jobs

        await loadMyJobs();

    }


    catch (error) {

        console.error(
            "DELETE ERROR:",
            error
        );


        alert(
            error.message
        );

    }

}


// =====================================
// LOGOUT
// =====================================

const logoutBtn =
    document.getElementById("logoutBtn");


if (logoutBtn) {

    logoutBtn.onclick =
        function() {

            localStorage.clear();

            window.location.href =
                "login.html";

        };

}


// =====================================
// PAGE LOAD
// =====================================

document.addEventListener(
    "DOMContentLoaded",
    function() {

        loadMyJobs();

    }
);