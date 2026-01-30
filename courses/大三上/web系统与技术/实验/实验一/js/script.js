/**
 * 个人主页 JavaScript 交互脚本
 * 实验一：HTML、CSS和JavaScript
 */

// 页面加载完成后执行
document.addEventListener('DOMContentLoaded', function() {
    initCurrentTime();
    initSkillBars();
});

// 显示当前时间
function initCurrentTime() {
    var timeElement = document.getElementById('currentTime');
    if (!timeElement) return;
    
    function updateTime() {
        var now = new Date();
        timeElement.textContent = now.toLocaleString('zh-CN');
    }
    updateTime();
    setInterval(updateTime, 1000);
}

// 技能进度条动画
function initSkillBars() {
    var skillBars = document.querySelectorAll('.skill-progress');
    skillBars.forEach(function(bar) {
        var progress = bar.getAttribute('data-progress');
        if (progress) {
            bar.style.width = progress + '%';
        }
    });
}

// 表单验证
function validateForm(formId) {
    var form = document.getElementById(formId);
    if (!form) return true;
    
    var inputs = form.querySelectorAll('input[required]');
    var valid = true;
    
    inputs.forEach(function(input) {
        if (input.value.trim() === '') {
            input.style.borderColor = '#e74c3c';
            valid = false;
        } else {
            input.style.borderColor = '#2ecc71';
        }
    });
    
    return valid;
}

// 显示消息提示
function showMessage(message, type) {
    alert(message);
}
