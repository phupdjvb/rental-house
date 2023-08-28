document.addEventListener("DOMContentLoaded", function() {
    var form = document.getElementById("registrationForm");
    console.log("Form element:", form);

    form.addEventListener("submit", function(event) {
        var valid = true;

        var emailField = document.getElementById("email");
        var emailError = document.getElementById("emailError");
        var passwordField = document.getElementById("password");
        var confirmPasswordField = document.getElementById("confirmPassword");
        var passwordMatchError = document.getElementById("passwordMatchError");
        var passwordComplexityError = document.getElementById("passwordComplexityError");
        var phoneField = document.getElementById("phone");
        var phoneError = document.getElementById("phoneError");

        // Clear previous error messages
        emailError.textContent = "";
        passwordMatchError.textContent = "";
        passwordComplexityError.textContent = "";
        phoneError.textContent = "";

        // Email validation
        var emailValue = emailField.value;
        var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
        if (!emailPattern.test(emailValue)) {
            emailError.textContent = "Vui lòng nhập địa chỉ email hợp lệ.";
            valid = false;
        }

        // Password matching validation
        var passwordValue = passwordField.value;
        var confirmPasswordValue = confirmPasswordField.value;
        if (passwordValue !== confirmPasswordValue) {
            passwordMatchError.textContent = "Mật khẩu không khớp.";
            valid = false;
        }

        // Password complexity validation
        var passwordPattern = /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,}$/;
        if (!passwordPattern.test(passwordValue)) {
            passwordComplexityError.textContent = "Mật khẩu phải chứa ít nhất một số và một kí tự đặc biệt, và có ít nhất 8 ký tự.";
            valid = false;
        }

        // Phone number validation
        var phoneValue = phoneField.value;
        var phonePattern = /^[0-9]+$/;
        if (!phonePattern.test(phoneValue)) {
            phoneError.textContent = "Vui lòng chỉ nhập các ký tự số.";
            valid = false;
        }

        if (!valid) {
            event.preventDefault(); // Prevent form submission if validation fails
        }
    });
});
