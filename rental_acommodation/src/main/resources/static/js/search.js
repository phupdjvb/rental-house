// chuyển hướng sang trang tìm kiếm
document.addEventListener("DOMContentLoaded", function () {
    var searchInput = document.getElementById("header-search-input");
    var searchButton = document.getElementById("button-addon2");
    var searchForm = document.getElementById("search-form");

    searchForm.addEventListener("submit", function (event) {
        event.preventDefault();
        var keyword = searchInput.value;// lấy giá trị từ input
        redirectToSearch(keyword);
    });

    searchButton.addEventListener("click", function () {
        var keyword = searchInput.value;
        redirectToSearch(keyword);
    });

    // chuyển hướng tới trang tìm kiếm 
    function redirectToSearch(keyword, page) {
        if (keyword.trim() !== "") {
            if (isNaN(page) || parseInt(page) <= 0) {
                page = 0; // Giá trị mặc định cho trang là 0 (trang đầu tiên)
            }

            var pathRedirect = "";
            const currentPath = window.location.pathname;// dường dẫn hiện tai

            if(currentPath.startsWith("/tenant/")) {
                pathRedirect = "/tenant/tenant-search?keyword=";
            } 

            if(currentPath.startsWith("/landlord/")) {
                pathRedirect = "/landlord/landlord-search?keyword=";
            }

            if(currentPath.startsWith("/admin?")) {
                pathRedirect = "/admin/admin-search/keyword=";
            }
            window.location.href = pathRedirect + encodeURIComponent(keyword) + "&page=" + page;
        }
    }
});

// Hàm thực hiện gán nhãn bài đăng theo tên quận
function assignTag() {
    var selectElement = document.getElementById("area")
    var selectedValue = selectElement.options[selectElement.selectedIndex].value;
    var tagField = document.getElementById("tag");

    switch(selectedValue) {
        case "Quận Thanh Xuân":
            tagField.value = "tx, thanh xuan, thanh, xuan, khoa học tự nhiên, khtn, nhân văn, hus";
            break;
        case "Quận Ba Đình":
            tagField.value = "bd, ba dinh, ba, dinh, lăng bác, hoàng thành";
            break;
        case "Quận Bắc Từ Liêm":
            tagField.value = "btl, tu liem, bac tu liem";
            break;
        case "Quận Cầu Giấy":
            tagField.value = "cau giay, cg, giấy, cầu, trung tâm thành phố, sư phạm, công nghệ, ngoại ngữ, vnu";
            break;
        case "Quận Đống Đa":
            tagField.value = "dong da, thuỷ lợi, ngân hàng, công đoàn";
            break;
        case "Quận Hai Bà Trưng":
            tagField.value = "hbt, hai ba trung, bách khoa, hust, neu, kinh tế quốc dân, đồ ăn ngon";
            break;
        case "Quận Hà Đông":
            tagField.value = "hđ, hà đông";
            break;
        case "Quận Hoàn Kiếm":
            tagField.value = "hk, trung tâm, phố cổ, tháp rùa";
            break;
        case "Quận Nam Từ Liêm":
            tagField.value = "ntl, hiện đại, phát triển, trung tâm, bảo tàng hà nội";
            break;
        case "Quận Tây Hồ":
            tagField.value = "hồ tây, tay ho";
            break;
        default:
            tagField.value = null;
    }
}

function setupListeners() {
    var selectElement = document.getElementById("area");
    // Lắng nghe change của trường select
    selectElement.addEventListener("change", function () {
        assignTag(); // Gán giá trị vào trường tag
    });
}

// Lắng nghe sự kiện DOMContentLoaded
document.addEventListener("DOMContentLoaded", setupListeners);
