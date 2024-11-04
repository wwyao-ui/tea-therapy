
fetch('questionnaire.html')
    .then(response => response.text())
    .then(html => {
        document.getElementById('questionnaire-container').innerHTML = html;
    })
    .catch(error => console.error('Error loading the navigation template:', error));