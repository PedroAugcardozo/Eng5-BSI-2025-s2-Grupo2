document.addEventListener("DOMContentLoaded", function() {

    const loginForm = document.getElementById("login-form");
    const cpfInput = document.getElementById("cpf");
    const passwordInput = document.getElementById("password");
    const cpfFeedback = document.getElementById("cpf-feedback");
    const passwordFeedback = document.getElementById("password-feedback");
    const formAlert = document.getElementById("form-alert");

    loginForm.addEventListener("submit", function(event) {
        event.preventDefault();

        resetValidation();

        const cpf = cpfInput.value.replace(/\D/g, '');
        const password = passwordInput.value;

        let isValid = true;

        if (!validateCPF(cpf)) {
            cpfInput.classList.add("is-invalid");
            cpfFeedback.textContent = "Por favor, insira um CPF válido (11 dígitos).";
            isValid = false;
        }

        if (!validatePassword(password)) {
            passwordInput.classList.add("is-invalid");
            passwordFeedback.textContent = "A senha deve ter min. 8 caracteres, uma maiúscula e um caractere especial.";
            isValid = false;
        }

        if (isValid) {
            console.log("Formulário válido. Enviando para a API...");
            
            const apiUrl = 'http://localhost:8080/entrar'; 

            fetch(apiUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    login: cpf,
                    senha: password 
                }),
            })
            .then(response => {
                if (response.ok) {
                    return response.json(); 
                } else {
                    return response.json().then(err => {
                        window.alert('CPF ou senha incorretos.');
                        throw new Error(err.message || 'CPF ou senha incorretos.');
                    });
                }
            })
            .then(data => {
                console.log('Sucesso:', data);
                showAlert('Login realizado com sucesso!', 'success');
            })
            .catch(error => {
                console.error('Erro na requisição:', error.message);
                showAlert(error.message, 'danger');
            });
        }
    });

    function validateCPF(cpf) {
        const cpfLimpo = cpf.replace(/\D/g, '');
        return cpfLimpo.length === 11;
    }

    function validatePassword(password) {
        const minLength = 8;
        const hasUpperCase = /[A-Z]/.test(password);
        const hasSpecialChar = /[^A-Za-z0-9]/.test(password); 
        return password.length >= minLength && hasUpperCase && hasSpecialChar;
    }

    function resetValidation() {
        cpfInput.classList.remove("is-invalid");
        passwordInput.classList.remove("is-invalid");
        formAlert.classList.add("d-none"); 
    }

    function showAlert(message, type = 'danger') {
        formAlert.textContent = message;
        formAlert.className = `alert alert-${type}`; 
        formAlert.classList.remove("d-none"); 
    }
});