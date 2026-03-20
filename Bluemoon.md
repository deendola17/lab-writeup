# 🔵 Bluemoon CTF Write-up (Easy)

## 📌 Overview

This write-up documents the full exploitation process of the **Bluemoon (Easy)** machine, covering reconnaissance, enumeration, exploitation, and post-exploitation.

---

## 🎯 Target Information

* **Target IP:** `192.168.56.101`
* **Difficulty:** Easy
* **Goal:** Obtain proof of compromise (flag)

---

## 🔍 1. Reconnaissance

Performed an initial scan using Nmap:

```bash
nmap -sV 192.168.56.101
```

### 🧾 Open Ports

| Port | Service | Description            |
| ---- | ------- | ---------------------- |
| 21   | FTP     | File Transfer Protocol |
| 22   | SSH     | Secure Shell           |
| 80   | HTTP    | Web Server             |

---

## 🌐 2. Enumeration

### 🔎 Web Enumeration

Used Gobuster to discover hidden directories:

```bash
gobuster dir -u http://192.168.56.101 -w /usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt -t 50
```

### 📁 Findings

* `/hidden_text` 🔥

---

## 🧩 3. Hidden Content Analysis

Accessing `/hidden_text` revealed a **QR code**.

### 🔐 Extracted Credentials

* **Username:** `userftp`
* **Password:** `ftpp@ssword`

---

## 📂 4. FTP Access

Connected to FTP:

```bash
ftp 192.168.56.101
```

### 📥 Result

* Retrieved files containing a **password list**
* This indicates potential **credential reuse vulnerability**

---

## 💥 5. Exploitation

Used Hydra to brute-force SSH using the password list:

```bash
hydra -l robin -P passwords.txt ssh://192.168.56.101
```

### ✅ Successful Credentials

* **Username:** `robin`
* **Password:** `[REDACTED]`

---

## 🖥️ 6. Initial Access

Logged in via SSH:

```bash
ssh robin@192.168.56.101
```

### 📁 Discovered Files

```bash
ls
```

* `project/`
* `user1.txt`

### 🚩 Flag Found

```bash
cat user1.txt
```

✔️ Successfully obtained **Proof of Compromise**

---

## 🔐 7. Privilege Escalation

Checked sudo permissions:

```bash
sudo -l
```

### 📌 Result

```bash
(robin) NOPASSWD: /home/robin/project/feedback.sh (as user jerry)
```

### 🧠 Analysis

* Script can be executed as another user (`jerry`)
* Potential for **privilege escalation via script abuse or PATH hijacking**

---

## 🎯 Conclusion

* Successfully exploited **credential reuse**
* Gained SSH access using brute force
* Retrieved user-level flag
* Identified a privilege escalation vector

---

## 🧠 Lessons Learned

* 🔍 Always perform thorough directory enumeration
* 📂 FTP services may expose sensitive data
* 🔐 Password reuse is a critical vulnerability
* ⚡ Automation tools like Hydra significantly improve efficiency
* 🚀 Always check `sudo -l` for escalation paths

---

## 📸 (Optional) Screenshots

> Add your screenshots here:

* Nmap scan
* Gobuster results
* FTP access
* Hydra success
* SSH login
* Flag capture

---

## ⚠️ Disclaimer

This write-up is for **educational purposes only**. All activities were performed in a controlled lab environment.

---
