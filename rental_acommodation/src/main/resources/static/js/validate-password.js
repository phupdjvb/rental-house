function validatePassword() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    var pattern = /^(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/;

    const tittleError = document.getElementById("title-error");

    if (password.length < 8 || !pattern.test(password)) {
        tittleError.textContent = "Mật khẩu phải có ít nhất 8 kí tự, bao gồm ít nhất một chữ số và một kí tự đặc biệt (!@#$%^&*)";
    }

    if (password !== confirmPassword) {
        tittleError.textContent = "Mật khẩu và xác nhận mật khẩu không khớp."
    }
}

function setupListeners() {
    var passwordInput = document.getElementById("password");
    var confirmPasswordInput = document.getElementById("confirmPassword");

    passwordInput.addEventListener("input", function () {
        validatePassword();
    });

    confirmPasswordInput.addEventListener("input", function () {
        validatePassword();
    });
}

// chạy mã js sau khi trang đã hoàn thành tải
document.addEventListener("DOMContentLoaded", function () {
    setupListeners(); 
});
