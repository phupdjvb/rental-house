function togglePasswordVisibility(inputId) {
    var inputField = document.getElementById(inputId);
    var icon = inputField.nextElementSibling.querySelector("i");
    if (inputField.type === "password") {
        inputField.type = "text";
        icon.classList.remove("fa-eye");
        icon.classList.add("fa-eye-slash");
    } else {
        inputField.type = "password";
        icon.classList.remove("fa-eye-slash");
        icon.classList.add("fa-eye");
    }
}