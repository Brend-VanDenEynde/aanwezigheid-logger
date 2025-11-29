import './style.css'

const API_BASE = '/api';

let token = localStorage.getItem('token') || null;
let username = localStorage.getItem('username') || null;
let currentProject = null;

function render() {
  if (token) {
    renderDashboard();
  } else {
    renderLogin();
  }
}

function renderLogin() {
  document.querySelector('#app').innerHTML = `
    <h1>Aanwezigheid Logger</h1>
    
    <div class="section">
      <h2>Login</h2>
      <div class="form-group">
        <label for="username">Gebruikersnaam</label>
        <input type="text" id="username" />
      </div>
      <div class="form-group">
        <label for="password">Wachtwoord</label>
        <input type="password" id="password" />
      </div>
      <button id="login-btn">Inloggen</button>
      <div id="login-message" class="message hidden"></div>
    </div>
  `;

  document.getElementById('login-btn').addEventListener('click', handleLogin);
  document.getElementById('password').addEventListener('keypress', (e) => {
    if (e.key === 'Enter') handleLogin();
  });
}

async function handleLogin() {
  const usernameInput = document.getElementById('username').value;
  const password = document.getElementById('password').value;
  const messageDiv = document.getElementById('login-message');

  if (!usernameInput || !password) {
    showMessage(messageDiv, 'Vul alle velden in', 'error');
    return;
  }

  try {
    const response = await fetch(`${API_BASE}/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username: usernameInput, password })
    });

    if (response.ok) {
      const data = await response.json();
      token = data.token;
      username = data.username;
      localStorage.setItem('token', token);
      localStorage.setItem('username', username);
      render();
    } else {
      showMessage(messageDiv, 'Ongeldige inloggegevens', 'error');
    }
  } catch (error) {
    showMessage(messageDiv, 'Fout bij verbinden met server', 'error');
  }
}

function renderDashboard() {
  document.querySelector('#app').innerHTML = `
    <div class="header">
      <h1>Aanwezigheid Logger</h1>
      <div class="user-info">
        Ingelogd als: ${username}
        <button id="logout-btn">Uitloggen</button>
      </div>
    </div>

    <div class="section">
      <h2>Logging Sessie</h2>
      <button id="new-logging-btn">Nieuwe Logging Aanmaken</button>
    </div>

    <div class="section">
      <h2>Aanwezigheden Bekijken</h2>
      <button id="load-all-btn">Alle Aanwezigheden Laden</button>
      <div class="form-group" style="margin-top: 15px;">
        <label for="filter-date">Of filter op datum</label>
        <input type="date" id="filter-date" />
      </div>
      <button id="load-date-btn">Laden op Datum</button>
      <div id="attendance-table"></div>
    </div>

    <div class="section">
      <h2>CSV Export</h2>
      <button id="export-all-btn">Exporteer Alle Data</button>
      <div class="form-group" style="margin-top: 15px;">
        <label for="export-date">Of exporteer specifieke datum</label>
        <input type="date" id="export-date" />
      </div>
      <button id="export-date-btn">Exporteer Datum</button>
      <div id="export-message" class="message hidden"></div>
    </div>

    <div class="section">
      <h2>Gebruiker Registreren</h2>
      <div class="form-group">
        <label for="new-username">Nieuwe gebruikersnaam</label>
        <input type="text" id="new-username" />
      </div>
      <div class="form-group">
        <label for="new-password">Wachtwoord (min 6 karakters)</label>
        <input type="password" id="new-password" />
      </div>
      <button id="register-user-btn">Gebruiker Aanmaken</button>
      <div id="user-register-message" class="message hidden"></div>
    </div>
  `;

  document.getElementById('logout-btn').addEventListener('click', handleLogout);
  document.getElementById('new-logging-btn').addEventListener('click', showProjectPrompt);
  document.getElementById('load-all-btn').addEventListener('click', loadAllAttendance);
  document.getElementById('load-date-btn').addEventListener('click', loadAttendanceByDate);
  document.getElementById('export-all-btn').addEventListener('click', exportAllCSV);
  document.getElementById('export-date-btn').addEventListener('click', exportDateCSV);
  document.getElementById('register-user-btn').addEventListener('click', handleRegisterUser);
}

function showProjectPrompt() {
  document.querySelector('#app').innerHTML = `
    <div class="header">
      <h1>Aanwezigheid Logger</h1>
      <div class="user-info">
        Ingelogd als: ${username}
        <button id="back-btn">Terug</button>
      </div>
    </div>

    <div class="section">
      <h2>Nieuwe Logging Sessie</h2>
      <div class="form-group">
        <label for="project-name">Naam van de les of project</label>
        <input type="text" id="project-name" placeholder="bijv. Java Programming" />
      </div>
      <button id="start-logging-btn">Start Logging</button>
      <div id="project-message" class="message hidden"></div>
    </div>
  `;

  document.getElementById('back-btn').addEventListener('click', renderDashboard);
  document.getElementById('start-logging-btn').addEventListener('click', startLoggingSession);
  document.getElementById('project-name').addEventListener('keypress', (e) => {
    if (e.key === 'Enter') startLoggingSession();
  });
}

function startLoggingSession() {
  const projectName = document.getElementById('project-name').value.trim();
  const messageDiv = document.getElementById('project-message');

  if (!projectName) {
    showMessage(messageDiv, 'Voer een project/les naam in', 'error');
    return;
  }

  currentProject = projectName;
  renderLoggingView();
}

function renderLoggingView() {
  document.querySelector('#app').innerHTML = `
    <div class="scanner-view">
      <div class="scanner-header">
        <span>${currentProject}</span>
        <button id="stop-logging-btn">Stop</button>
      </div>
      <div class="scanner-content">
        <div id="scanner-status" class="scanner-status">Klaar om te scannen</div>
        <input type="text" id="stamnr" class="scanner-input" autofocus />
      </div>
    </div>
  `;

  document.getElementById('stop-logging-btn').addEventListener('click', stopLoggingSession);
  document.getElementById('stamnr').addEventListener('keypress', (e) => {
    if (e.key === 'Enter') handleRegisterAttendance();
  });
  
  // Keep focus on input
  document.getElementById('stamnr').addEventListener('blur', () => {
    setTimeout(() => {
      const input = document.getElementById('stamnr');
      if (input) input.focus();
    }, 100);
  });
}

function stopLoggingSession() {
  currentProject = null;
  renderDashboard();
}

function handleLogout() {
  token = null;
  username = null;
  localStorage.removeItem('token');
  localStorage.removeItem('username');
  render();
}

async function handleRegisterAttendance() {
  const stamnrInput = document.getElementById('stamnr');
  const stamnr = stamnrInput.value.trim();
  const statusDiv = document.getElementById('scanner-status');

  if (!stamnr) {
    return;
  }

  stamnrInput.value = '';

  try {
    console.log('Sending:', { stamnr, lesOfProject: currentProject });
    
    const response = await fetch(`${API_BASE}/aanwezigheid`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify({ stamnr, lesOfProject: currentProject })
    });

    console.log('Response status:', response.status);
    
    const responseText = await response.text();
    console.log('Response body:', responseText);
    
    let data;
    try {
      data = JSON.parse(responseText);
    } catch (e) {
      data = null;
    }

    if (response.ok && data) {
      // Show student name for 3 seconds
      statusDiv.textContent = `${data.studentNaam || data.naam || 'Student'} ${data.studentVoornaam || data.voornaam || ''}`;
      statusDiv.className = 'scanner-status success';
      
      setTimeout(() => {
        statusDiv.textContent = 'Klaar om te scannen';
        statusDiv.className = 'scanner-status';
      }, 3000);
    } else if (response.status === 404) {
      statusDiv.textContent = 'Student niet gevonden';
      statusDiv.className = 'scanner-status error';
      
      setTimeout(() => {
        statusDiv.textContent = 'Klaar om te scannen';
        statusDiv.className = 'scanner-status';
      }, 3000);
    } else if (response.status === 401) {
      handleLogout();
    } else {
      statusDiv.textContent = 'Fout';
      statusDiv.className = 'scanner-status error';
      
      setTimeout(() => {
        statusDiv.textContent = 'Klaar om te scannen';
        statusDiv.className = 'scanner-status';
      }, 3000);
    }
  } catch (error) {
    statusDiv.textContent = 'Verbindingsfout';
    statusDiv.className = 'scanner-status error';
    
    setTimeout(() => {
      statusDiv.textContent = 'Klaar om te scannen';
      statusDiv.className = 'scanner-status';
    }, 3000);
  }
  
  stamnrInput.focus();
}

function addToSessionLog(data) {
  const container = document.getElementById('session-log');
  const existing = container.innerHTML;
  const newEntry = `<div class="log-entry">Student ID: ${data.studentId} - ${data.timestamp}</div>`;
  container.innerHTML = newEntry + existing;
}

async function loadAllAttendance() {
  try {
    const response = await fetch(`${API_BASE}/aanwezigheid`, {
      headers: { 'Authorization': `Bearer ${token}` }
    });

    if (response.ok) {
      const data = await response.json();
      renderAttendanceTable(data);
    } else if (response.status === 401) {
      handleLogout();
    }
  } catch (error) {
    console.error('Fout bij laden:', error);
  }
}

async function loadAttendanceByDate() {
  const date = document.getElementById('filter-date').value;
  if (!date) {
    alert('Selecteer een datum');
    return;
  }

  try {
    const response = await fetch(`${API_BASE}/aanwezigheid/dag/${date}`, {
      headers: { 'Authorization': `Bearer ${token}` }
    });

    if (response.ok) {
      const data = await response.json();
      renderAttendanceTable(data);
    } else if (response.status === 401) {
      handleLogout();
    }
  } catch (error) {
    console.error('Fout bij laden:', error);
  }
}

function renderAttendanceTable(data) {
  const container = document.getElementById('attendance-table');
  
  if (data.length === 0) {
    container.innerHTML = '<p>Geen aanwezigheden gevonden</p>';
    return;
  }

  let html = `
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Student ID</th>
          <th>Timestamp</th>
          <th>Les/Project</th>
          <th>Opmerking</th>
        </tr>
      </thead>
      <tbody>
  `;

  data.forEach(item => {
    html += `
      <tr>
        <td>${item.id}</td>
        <td>${item.studentId}</td>
        <td>${item.timestamp}</td>
        <td>${item.lesOfProject || '-'}</td>
        <td>${item.opmerking || '-'}</td>
      </tr>
    `;
  });

  html += '</tbody></table>';
  container.innerHTML = html;
}

async function exportAllCSV() {
  const messageDiv = document.getElementById('export-message');
  try {
    const response = await fetch(`${API_BASE}/aanwezigheid/export`, {
      headers: { 'Authorization': `Bearer ${token}` }
    });

    if (response.ok) {
      const blob = await response.blob();
      downloadBlob(blob, 'aanwezigheid_alle.csv');
      showMessage(messageDiv, 'CSV gedownload', 'success');
    } else if (response.status === 401) {
      handleLogout();
    }
  } catch (error) {
    showMessage(messageDiv, 'Fout bij exporteren', 'error');
  }
}

async function exportDateCSV() {
  const date = document.getElementById('export-date').value;
  const messageDiv = document.getElementById('export-message');
  
  if (!date) {
    showMessage(messageDiv, 'Selecteer een datum', 'error');
    return;
  }

  try {
    const response = await fetch(`${API_BASE}/aanwezigheid/export/dag/${date}`, {
      headers: { 'Authorization': `Bearer ${token}` }
    });

    if (response.ok) {
      const blob = await response.blob();
      downloadBlob(blob, `aanwezigheid_${date}.csv`);
      showMessage(messageDiv, 'CSV gedownload', 'success');
    } else if (response.status === 401) {
      handleLogout();
    }
  } catch (error) {
    showMessage(messageDiv, 'Fout bij exporteren', 'error');
  }
}

function downloadBlob(blob, filename) {
  const url = window.URL.createObjectURL(blob);
  const a = document.createElement('a');
  a.href = url;
  a.download = filename;
  document.body.appendChild(a);
  a.click();
  window.URL.revokeObjectURL(url);
  a.remove();
}

async function handleRegisterUser() {
  const newUsername = document.getElementById('new-username').value;
  const newPassword = document.getElementById('new-password').value;
  const messageDiv = document.getElementById('user-register-message');

  if (!newUsername || !newPassword) {
    showMessage(messageDiv, 'Vul alle velden in', 'error');
    return;
  }

  if (newPassword.length < 6) {
    showMessage(messageDiv, 'Wachtwoord moet minimaal 6 karakters zijn', 'error');
    return;
  }

  try {
    const response = await fetch(`${API_BASE}/auth/register`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify({ username: newUsername, password: newPassword })
    });

    if (response.ok) {
      const text = await response.text();
      showMessage(messageDiv, text, 'success');
      document.getElementById('new-username').value = '';
      document.getElementById('new-password').value = '';
    } else if (response.status === 401) {
      showMessage(messageDiv, 'Niet geautoriseerd', 'error');
    } else {
      showMessage(messageDiv, 'Gebruikersnaam bestaat al of validatie gefaald', 'error');
    }
  } catch (error) {
    showMessage(messageDiv, 'Fout bij verbinden met server', 'error');
  }
}

function showMessage(element, message, type) {
  element.textContent = message;
  element.className = `message ${type}`;
  element.classList.remove('hidden');
}

render();
