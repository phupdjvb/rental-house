
document.addEventListener("DOMContentLoaded", function () {
    // Lắng nghe sự kiện click vào nút đặt lịch
    const bookAppointmentBtn = document.getElementById("booking-button");

    if (bookAppointmentBtn) {
        bookAppointmentBtn.addEventListener("click", function () {
            // Hiển thị mục đặt lịch hẹn (có thể là một modal hoặc phần tử khác)
            const bookingForm = document.getElementById("booking-form");
            if (bookingForm) {
                bookingForm.style.display = "block"; // Hiển thị mục đặt lịch
            }
        });
    }
});
