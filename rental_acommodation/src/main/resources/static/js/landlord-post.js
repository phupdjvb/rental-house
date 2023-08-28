// Validate cho trường title bài đăng, tối thiểu là 50 kí tự
document.addEventListener('DOMContentLoaded', function() {
    const titleInput = document.getElementById('title');
    const titleError = document.getElementById('title-error');

    const addressInput = document.getElementById('address')
    const addressError = document.getElementById('address-error')

    titleInput.addEventListener('input', function() {
        const titleValue = titleInput.value;
        if (titleValue.length < 30) {
            titleError.textContent = 'Tiêu đề phải có ít nhất 30 kí tự';
        } else {
            titleError.textContent = '';
        }
    });

    addressInput.addEventListener('input', function() {
        const addressValue = address.value;
        if(addressValue.length < 20) {
            addressError.textContent = "Địa chỉ phải có ít nhất 20 kí tự";
        }
    });

    document.querySelector('form').addEventListener('submit', function(event) {
        const titleValue = titleInput.value.trim();
        if (titleValue.length < 50) {
            event.preventDefault();
            titleError.textContent = 'Tiêu đề phải có ít nhất 50 kí tự';
        }

        if(addressValue.length < 20) {
            event.preventDefault();
            addressError.textContent = 'Địa chỉ phải có ít nhất 20 kí tự';
        }
    });
});
