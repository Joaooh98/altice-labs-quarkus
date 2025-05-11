/**
 * Calculadora Labseq
 * Aplicação para calcular valores da sequência Labseq
 */

document.addEventListener('DOMContentLoaded', function() {
    // Elementos DOM
    const calculateBtn = document.getElementById('calculateBtn');
    const indexInput = document.getElementById('indexInput');
    const resultDiv = document.getElementById('result');
    const loadingSpan = document.getElementById('loading');
    const historyList = document.getElementById('historyList');
    
    // Histórico de cálculos
    const history = [];
    
    // Eventos
    calculateBtn.addEventListener('click', handleCalculate);
    indexInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            calculateBtn.click();
        }
    });
    
    // Inicializar a exibição do histórico
    updateHistoryDisplay();
    
    /**
     * Função principal para calcular o valor Labseq
     */
    async function handleCalculate() {
        const n = indexInput.value.trim();
        
        // Validação básica
        if (n === '' || parseInt(n) < 0) {
            showError('Por favor, insira um número inteiro não negativo.');
            return;
        }
        
        // Limpar resultado anterior e mostrar carregamento
        setLoadingState(true);
        
        try {
            // Chamada à API
            const response = await fetch(`/labseq/${n}`);
            
            if (!response.ok) {
                throw new Error(`Erro: ${response.status} - ${await response.text()}`);
            }
            
            // Interpretar o resultado como JSON
            const resultText = await response.text();
            let resultData;
            
            try {
                // Tenta fazer o parse do JSON
                resultData = JSON.parse(resultText);
            } catch (e) {
                // Se não for um JSON válido, usa o texto como resultado
                resultData = {
                    index: parseInt(n),
                    value: resultText,
                    calculationTimeMs: 0
                };
            }
            
            // Exibir resultado
            showResult(resultData);
            
            // Adicionar ao histórico
            addToHistory(resultData);
            
        } catch (error) {
            showError(error.message);
        } finally {
            setLoadingState(false);
        }
    }
    
    /**
     * Configura o estado de carregamento da interface
     * @param {boolean} isLoading - Se a aplicação está carregando 
     */
    function setLoadingState(isLoading) {
        if (isLoading) {
            resultDiv.textContent = '';
            resultDiv.classList.remove('error');
            resultDiv.style.display = 'none';
            loadingSpan.style.display = 'inline-block';
            calculateBtn.disabled = true;
        } else {
            loadingSpan.style.display = 'none';
            calculateBtn.disabled = false;
        }
    }
    
    /**
     * Exibe o resultado do cálculo de forma estruturada
     * @param {Object} resultData - Objeto com os dados do resultado
     */
    function showResult(resultData) {
        resultDiv.innerHTML = '';
        resultDiv.classList.remove('error');
        
        // Criar elementos para apresentar o resultado de forma estruturada
        const container = document.createElement('div');
        container.className = 'result-container';
        
        // Título do resultado
        const title = document.createElement('div');
        title.className = 'result-title';
        title.textContent = `l(${resultData.index}) =`;
        
        // Valor do resultado
        const value = document.createElement('div');
        value.className = 'result-value';
        value.textContent = resultData.value;
        
        // Tempo de cálculo
        const timeContainer = document.createElement('div');
        timeContainer.className = 'result-time-container';
        
        const timeLabel = document.createElement('span');
        timeLabel.className = 'result-time-label';
        timeLabel.textContent = 'Tempo de cálculo:';
        
        const timeValue = document.createElement('span');
        timeValue.className = 'result-time-value';
        timeValue.textContent = `${resultData.calculationTimeMs} ms`;
        
        timeContainer.appendChild(timeLabel);
        timeContainer.appendChild(timeValue);
        
        // Adicionar todos os elementos ao container
        container.appendChild(title);
        container.appendChild(value);
        container.appendChild(timeContainer);
        
        resultDiv.appendChild(container);
        resultDiv.style.display = 'block';
    }
    
    /**
     * Exibe mensagem de erro
     * @param {string} message - Mensagem de erro
     */
    function showError(message) {
        resultDiv.textContent = message;
        resultDiv.classList.add('error');
        resultDiv.style.display = 'block';
    }
    
    /**
     * Adiciona um cálculo ao histórico
     * @param {Object} resultData - Objeto com os dados do resultado
     */
    function addToHistory(resultData) {
        // Adicionar no início do array para mostrar os mais recentes primeiro
        history.unshift({
            data: resultData,
            timestamp: new Date()
        });
        
        // Limitar histórico a 10 itens
        if (history.length > 10) {
            history.pop();
        }
        
        // Atualizar a exibição do histórico
        updateHistoryDisplay();
    }
    
    /**
     * Atualiza a exibição do histórico de cálculos
     */
    function updateHistoryDisplay() {
        historyList.innerHTML = '';
        
        if (history.length === 0) {
            historyList.innerHTML = '<p class="no-history">Nenhum cálculo realizado ainda.</p>';
            return;
        }
        
        history.forEach(item => {
            const historyItem = document.createElement('div');
            historyItem.className = 'history-item';
            
            // Container de resultado
            const resultContainer = document.createElement('div');
            resultContainer.className = 'history-result-container';
            
            // Índice e valor
            const indexValue = document.createElement('div');
            indexValue.className = 'history-index-value';
            
            const indexSpan = document.createElement('span');
            indexSpan.className = 'history-index';
            indexSpan.textContent = `l(${item.data.index}) =`;
            
            const valueSpan = document.createElement('span');
            valueSpan.className = 'history-value';
            valueSpan.textContent = item.data.value;
            
            indexValue.appendChild(indexSpan);
            indexValue.appendChild(valueSpan);
            
            // Tempo de cálculo
            const timeSpan = document.createElement('div');
            timeSpan.className = 'history-calc-time';
            timeSpan.textContent = `${item.data.calculationTimeMs} ms`;
            
            resultContainer.appendChild(indexValue);
            resultContainer.appendChild(timeSpan);
            
            // Timestamp
            const timestampSpan = document.createElement('div');
            timestampSpan.className = 'history-time';
            timestampSpan.textContent = formatTimestamp(item.timestamp);
            
            historyItem.appendChild(resultContainer);
            historyItem.appendChild(timestampSpan);
            
            // Adicionar item ao histórico
            historyList.appendChild(historyItem);
        });
    }
    
    /**
     * Formata timestamp para exibição
     * @param {Date} date - Data a ser formatada
     * @return {string} - String formatada com a data
     */
    function formatTimestamp(date) {
        return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' });
    }
});