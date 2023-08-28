// validation.js
function showInvalidDateTimeMessage() {
    document.getElementById("invalid-date-time").style.display = "block";
    setTimeout(function() {
        document.getElementById("invalid-date-time").style.display = "none";
    }, 5000); // 5 seconds
}

document.getElementById("booking-form").addEventListener("submit", function(event) {
    const bookingDate = new Date(document.getElementById("bookingDate").value);
    const bookingTime = new Date("1970-01-01T" + document.getElementById("bookingTime").value);
    const currentDateTime = new Date(); // Lấy thời gian hiện tại

    // Kiểm tra nếu ngày hoặc giờ hẹn không hợp lệ
    if (isNaN(bookingDate) || isNaN(bookingTime) || bookingDate < currentDateTime || (bookingDate.getTime() === currentDateTime.getTime() && bookingTime <= currentDateTime)) {
        event.preventDefault(); // Ngăn submit form
        showInvalidDateTimeMessage(); // Hiển thị thông báo
    }
});
