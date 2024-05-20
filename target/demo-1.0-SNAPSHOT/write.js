document.addEventListener('DOMContentLoaded', function () {
  const form = document.getElementById('carForm');

  form.addEventListener('submit', function (event) {
      event.preventDefault();

      const formData = new FormData(form);
      const car = {};
      formData.forEach(function (value, key) {
          car[key] = value;
      });

      const xhr = new XMLHttpRequest();
      xhr.open('POST', 'http://localhost:8081/demo_war_exploded/index.html', true);
      xhr.setRequestHeader('Content-Type', 'application/json');

      xhr.onload = function () {
          if (xhr.status === 200) {
              console.log('Данные успешно отправлены');
          } else {
              console.log('Произошла ошибка при обработке запроса:', xhr.statusText);
          }
      };

      xhr.send(JSON.stringify(car));
      this.reset();
  });
});