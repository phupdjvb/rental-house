function cancelBooking() {
    // Lấy giá trị của CSRF Token từ thẻ meta
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    var datingIdCancel = $("#datingIdCancel").val();

    // Tạo AJAX request
    $.ajax({
        type: "POST",
        url: "/landlord/landlord-cancel-booking",
        data: { datingIdCancel: datingIdCancel},
        // Thiết lập header cho yêu cầu AJAX
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function (response) {
            // Xử lý khi booking được huỷ thành công
            alert(response);
            var row = $("#row-" + datingId);
            if (row) {
                row.hide();
            }
        },
        error: function (xhr, status, error) {
            console.log("XHR status: " + xhr.status);
            console.log("Error: " + error);
            alert("Có lỗi xảy ra khi huỷ lịch");
        }
    });
}

function confirmBooking() {
    // Lấy giá trị của CSRF Token từ thẻ meta
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    var datingIdConfirm = $("#datingIdConfirm").val();

    // Tạo AJAX request
    $.ajax({
        type: "POST",
        url: "/landlord/landlord-confirm-booking",
        data: { datingIdConfirm: datingIdConfirm},
        // Thiết lập header cho yêu cầu AJAX
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function (response) {
            // Xử lý khi booking được huỷ thành công
            alert(response);
            var row = $("#row-" + datingId);
            if (row) {
                row.hide();
            }
        },
        error: function (xhr, status, error) {
            console.log("XHR status: " + xhr.status);
            console.log("Error: " + error);
            alert("Có lỗi xảy ra khi huỷ lịch");
        }
    });
}