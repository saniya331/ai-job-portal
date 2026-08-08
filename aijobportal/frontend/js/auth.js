// ================================
// REGISTER
// ================================

const registerForm = document.getElementById("registerForm");

if (registerForm) {

    registerForm.addEventListener("submit", async function (event) {

        event.preventDefault();

        const fullName = document.getElementById("fullName").value;
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        const role = document.getElementById("role").value;

        const message = document.getElementById("message");

        try {

            const data = await apiRequest(
                "/api/auth/register",
                "POST",
                {
                    fullName: fullName,
                    email: email,
                    password: password,
                    role: role
                }
            );

            console.log("Registration Response:", data);

            message.textContent = "Registration successful!";

            setTimeout(function () {

                window.location.href = "login.html";

            }, 1000);

        } catch (error) {

            console.error(error);

            message.textContent =
                "Registration failed: " + error.message;
        }
    });
}


// ================================
// LOGIN
// ================================

const loginForm = document.getElementById("loginForm");

if (loginForm) {

    loginForm.addEventListener("submit", async function (event) {

        event.preventDefault();

        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;

        const message = document.getElementById("message");

        try {

            const data = await apiRequest(
                "/api/auth/login",
                "POST",
                {
                    email: email,
                    password: password
                }
            );

            console.log("Login Response:", data);

            // ================================
            // SAVE LOGIN INFORMATION
            // ================================

            localStorage.setItem("token", data.token);

            localStorage.setItem("email", data.email);

            localStorage.setItem("fullName", data.fullName);

            localStorage.setItem("role", data.role);

            message.textContent = "Login successful!";

            // ================================
            // REDIRECT BASED ON ROLE
            // ================================

            setTimeout(function () {

                if (data.role === "STUDENT") {

                    window.location.href =
                        "student-dashboard.html";

                } else if (data.role === "RECRUITER") {

                    if(data.role==="RECRUITER"){

window.location.href =
"recruiter-dashboard.html";

}

else{


window.location.href =
"student-dashboard.html";


}

                } else {

                    message.textContent =
                        "Unknown user role.";

                }

            }, 1000);

        } catch (error) {

            console.error(error);

            message.textContent =
                "Login failed: " + error.message;
        }
    });
}