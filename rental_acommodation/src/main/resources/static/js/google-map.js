function initMap() {
    // Khởi tạo bản đồ
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 15 // Độ phóng bản đồ
    });

    // Sử dụng Geocoder để lấy thông tin địa chỉ từ văn bản địa chỉ
    var geocoder = new google.maps.Geocoder();
    var address = /*[[ ${displayPostDto.address} ]]*/ '';

    geocoder.geocode({ 'address': address }, function (results, status) {
        if (status === 'OK') {
            // Lấy vị trí từ kết quả Geocoder
            var location = results[0].geometry.location;

            // Đặt tọa độ cho bản đồ và thêm đánh dấu
            map.setCenter(location);
            var marker = new google.maps.Marker({
                map: map,
                position: location,
                title: address
            });
        } else {
            console.log('Không thể mã hóa địa chỉ: ' + status);
        }
    });
}