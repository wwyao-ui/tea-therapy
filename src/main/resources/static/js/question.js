async function fetchQuestions() {
    const response = await fetch('/questions/select');
    const data = await response.json();
    console.log(data);
    return data;
}

// 定义固定的选项数组
const options = [
    { id: 1, score: 1, text: '没有' },
    { id: 2, score: 2, text: '很少' },
    { id: 3, score: 3, text: '有时' },
    { id: 4, score: 4, text: '经常' },
    { id: 5, score: 5, text: '总是' }
];
const moduleNameMap = {
    1: '1. 根据近一年的体验和感觉回答以下问题（平和质）',
    2: '2. 根据近一年的体验和感觉回答以下问题（气虚质）',
    3: '3. 根据近一年的体验和感觉回答以下问题（阳虚质）',
    4: '4. 根据近一年的体验和感觉回答以下问题（阴虚质）',
    5: '5. 根据近一年的体验和感觉回答以下问题（痰湿质）',
    6: '6. 根据近一年的体验和感觉回答以下问题（湿热质）',
    7: '7. 根据近一年的体验和感觉回答以下问题（血瘀质）',
    8: '8. 根据近一年的体验和感觉回答以下问题（气郁质）',
    9: '9. 根据近一年的体验和感觉回答以下问题（特禀质）'
};
// 动态生成问卷问题和选项
let questions=null;
async function generateSurvey() {
    const surveyForm = document.getElementById('surveyForm');
    questions = await fetchQuestions();

    let currentModuleId = null;
    let currentModuleName = null;

    questions.data.forEach(question => {
        console.log(question.questionnaireid);
        if (currentModuleId !== question.questionnaireid) {
            // 新模块开始
            if (currentModuleName) {
                // 添加模块分割线
                const hr = document.createElement('hr');
                surveyForm.appendChild(hr);
            }
            currentModuleId = question.questionnaireid;
            currentModuleName = moduleNameMap[currentModuleId];

            // 添加模块标题
            const moduleTitle = document.createElement('h4');
            moduleTitle.textContent = currentModuleName;
            surveyForm.appendChild(moduleTitle);
        }
        const questionDiv = document.createElement('div');
        questionDiv.className = 'mb-3';
        questionDiv.id = `question${question.id}Wrapper`;

        const questionLabel = document.createElement('label');
        questionLabel.className = 'form-label';
        questionLabel.textContent = question.content;
        questionLabel.setAttribute('for', `question${question.id}`);
        questionLabel.id = `question${question.id}Label`;
        questionDiv.appendChild(questionLabel);

        const rowDiv = document.createElement('div');
        rowDiv.className = 'row';
        rowDiv.setAttribute('aria-labelledby', `question${question.id}Label`);

        options.forEach(option => {
            const colDiv = document.createElement('div');
            colDiv.className = 'col';

            const formCheckDiv = document.createElement('div');
            formCheckDiv.className = 'form-check';

            const input = document.createElement('input');
            input.className = 'form-check-input';
            input.type = 'radio';
            input.name = `question${question.id}`; // 使用问题的id作为name属性
            input.id = `question${question.id}_option${option.id}`;
            input.dataset.group=`${question.questionnaireid}`;
            input.value = option.score;
            input.required = true;
            formCheckDiv.appendChild(input);

            const label = document.createElement('label');
            label.className = 'form-check-label';
            label.textContent = option.text;
            label.setAttribute('for', `question${question.id}_option${option.id}`);
            formCheckDiv.appendChild(label);

            colDiv.appendChild(formCheckDiv);
            rowDiv.appendChild(colDiv);
        });

        questionDiv.appendChild(rowDiv);
        surveyForm.appendChild(questionDiv);
    });
    const hr = document.createElement('hr');
    surveyForm.appendChild(hr);

}
function collectAndSummarizeScores() {
    sendDataToBackend();
    const scoreSummary = {}; // 创建一个对象来存储每个分组的总分
    let groupId=null;
    // 获取所有单选按钮
    const radios = document.querySelectorAll('input[type="radio"]:checked');
    // 遍历所有选中的单选按钮
    radios.forEach(radio => {
        groupId = radio.dataset.group; // 从data-group属性获取分组ID
        const score = parseInt(radio.value, 10); // 解析分数为整数
        console.log(score);
        // 如果该分组尚未在scoreSummary对象中，则初始化其总分为0
        if (!scoreSummary[groupId]) {
            scoreSummary[groupId] = 0;
        }
        // 累加该分组的总分
        scoreSummary[groupId] += score;
        console.log(scoreSummary);

    });
    for (const key in scoreSummary) {
        if (scoreSummary.hasOwnProperty(key)) {
            let value = scoreSummary[key];
            value=parseInt(value,10);
            sendDataToBackend(key.toString(),value);
        }
    }
}
function sendDataToBackend(questionnaireid,lscore) {
    // 使用URLSearchParams来构建表单编码的请求体
    const params = new URLSearchParams();
    params.append('questionnaireid', questionnaireid);
    params.append('lscore', lscore);

    fetch('/calculation/addlscore', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: params.toString() // 将URLSearchParams转换为字符串作为请求体
    })
        .then(response => {
            console.log('Response status:n', response.status); // 打印响应状态码
            console.log('Response headers:', response.headers); // 打印响应头
            if (!response.ok) {
                throw new Error('Failed to send data to backend');
            }
            return response.json(); // 返回解析后的JSON数据
        })
        .then(data => {
            console.log('Response data:', data); // 打印响应数据，可用于调试
            // 检查后端返回的结果状态
            if (data.status !== 'ok') {
                throw new Error(data.message || 'Unknown error from backend');
            }
            console.log('Data sent successfully.');
        })
        .catch(error => {
            console.error('Error:', error); // 打印错误信息，用于调试
        });
}

generateSurvey();

document.addEventListener('DOMContentLoaded', function() {
    const modal = new bootstrap.Modal(document.getElementById('surveyModal'));
    const form = document.getElementById('surveyForm');
    const submitButton = document.getElementById('submitSurvey');
    const content = document.querySelector('.blocked-content');

    // 显示模态框
    modal.show();

    // 监听表单提交
    submitButton.addEventListener('click', function(e) {
        e.preventDefault();
        if (form.checkValidity()) {
            // 隐藏模态框
            modal.hide();
            // 允许访问页面内容
            content.classList.remove('blocked-content');
        } else {
            form.reportValidity();
        }
    });
});