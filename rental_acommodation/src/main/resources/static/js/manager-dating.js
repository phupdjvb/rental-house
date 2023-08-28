function cancelBooking() {
    // Lấy giá trị của CSRF Token từ thẻ meta
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    var datingId = $("#datingId").val();
    var bookingDate = $("#bookingDate").val();
    var bookingTime = $("#bookingTime").val();
    var tenantId = $("#tenantId").val();
    var landlordId = $("#landlordId").val();

    // Tạo AJAX request
    $.ajax({
        type: "POST",
        url: "/tenant/tenant-queue",
        data: { datingId: datingId,
                bookingTime: bookingTime,
                bookingDate: bookingDate,
                tenantId: tenantId,
                landlordId: landlordId},
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