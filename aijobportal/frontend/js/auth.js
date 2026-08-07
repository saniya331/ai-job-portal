// ========================================
// REGISTER
// ========================================

const registerForm = document.getElementById("registerForm");

if (registerForm) {

    registerForm.addEventListener("submit", async function (event) {

        event.preventDefault();

        const fullName =
            document.getElementById("fullName").value.trim();

        const email =
            document.getElementById("email").value.trim();

        const password =
            document.getElementById("password").value;

        const role =
            document.getElementById("role").value;

        const message =
            document.getElementById("message");

        try {

            message.textContent = "Registering...";

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

            console.log("Register Response:", data);

            message.textContent =
                "Registration successful! Redirecting to login...";

            setTimeout(function () {

                window.location.href = "login.html";

            }, 1000);

        } catch (error) {

            console.error("Registration Error:", error);

            message.textContent =
                "Registration failed: " + error.message;
        }
    });
}


// ========================================
// LOGIN
// ========================================

const loginForm = document.getElementById("loginForm");

if (loginForm) {

    loginForm.addEventListener("submit", async function (event) {

        event.preventDefault();

        const email =
            document.getElementById("email").value.trim();

        const password =
            document.getElementById("password").value;

        const message =
            document.getElementById("message");

        try {

            message.textContent = "Logging in...";

            const data = await apiRequest(
                "/api/auth/login",
                "POST",
                {
                    email: email,
                    password: password
                }
            );

            console.log("Login Response:", data);

            // Save JWT token
            localStorage.setItem(
                "token",
                data.token
            );

            // Save email
            localStorage.setItem(
                "email",
                email
            );

            // Save role if backend sends it
            if (data.role) {

                localStorage.setItem(
                    "role",
                    data.role
                );
            }

            message.textContent =
                "Login successful!";

            // Redirect
            setTimeout(function () {

                if (data.role === "RECRUITER") {

                    window.location.href =
                        "recruiter-dashboard.html";

                } else {

                    window.location.href =
                        "student-dashboard.html";
                }

            }, 500);

        } catch (error) {

            console.error("Login Error:", error);

            message.textContent =
                "Login failed: " + error.message;
        }
    });
}