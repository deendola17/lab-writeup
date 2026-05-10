# VA Week 7 - Network & Vulnerability Analysis

## Question 1: Analyse packet1.pcap and find the flag

After analysing the packet, I found that packet number 37 is different from other packets as it has no reply or request.

Decode the letter contained in the right container and it will show the flag:

**Flag: `SUCTF2023[ai_is_cool]`**

---

## Question 2: Analyse packet2.pcap and find the flag

After analysing all the packet file, I found a pattern that only TCP packets have a word in the right container.

| Packet | Message |
|--------|---------|
| 22 | hello |
| 32 | How are you |
| 70 | Are you a robot? |
| 329 | Did you ever play tic-tac-toe |
| 409 | Yeah, of course. |
| 505 | But you don't play any more? |
| 535 | No. |
| 587 | Why? |
| 651 | Because it's a boring game. It's always a tie. |
| 720 | Exactly. There's no way to win. |
| 774 | The game itself is pointless. |

---

## Question 3: Port Analysis & Attack Path

### 1. What can an attacker do with each port?

| Port | Service | What an attacker can do |
|------|---------|------------------------|
| 21/tcp | FTP (vsftpd 2.3.4) | Upload or download files, brute-force login, exploit known backdoor (if vulnerable), access misconfigured anonymous FTP |
| 22/tcp | SSH (OpenSSH 5.3p1) | Brute-force credentials, attempt weak authentication attacks, gain remote shell access if credentials compromised |
| 80/tcp | HTTP (Apache 2.2.8) | Perform web enumeration, exploit web vulnerabilities, access hidden directories/files |
| 139/tcp | NetBIOS-SSN | Enumerate shares and users, gather system information, identify domain/workgroup structure |
| 445/tcp | SMB (Windows 7 SP1) | Access file shares, perform remote exploitation, execute remote code, lateral movement in network |

### 2. What vulnerabilities are likely present based on the version?

| Port | Service | Version | Likely Vulnerabilities |
|------|---------|---------|------------------------|
| 21/tcp | FTP (vsftpd) | 2.3.4 | Known backdoor vulnerability (CVE-2011-2523), possible remote command execution, weak authentication, anonymous login misconfiguration |
| 22/tcp | SSH (OpenSSH) | 5.3p1 | Weak cryptographic algorithms, possible user enumeration, vulnerable to brute-force |

### 3. Which one is the highest risk and why?

**Port 445 (SMB on Windows 7 SP1)**

- Direct remote code execution possible
- Wormable exploit such as EternalBlue
- Can lead to full system takeover without credentials
- Often used in real-world ransomware attacks

### 4. What attack path can be built from this?

| Step | Action |
|------|--------|
| **Step 1: Recon** | Nmap reveals SMB and outdated OS |
| **Step 2: Exploit SMB (445)** | Use EternalBlue exploit to gain SYSTEM shell |
| **Step 3: Pivot** | Use stolen creds for SSH (22) OR upload payload via FTP (21) OR web shell via HTTP (80) |

### 5. What should be the remediation?

1. **Patch OS** - Upgrade Windows 7 to Windows 10/11
2. **Disable SMBv1** - Block port 445 externally, Enable SMB signing
3. **Update services** - Apache (latest 2.4+), OpenSSH (modern version 8+), vsftpd (remove or upgrade)
4. **Secure access** - Disable anonymous FTP, Use key-based SSH authentication, Strong password policy and account lockout

---

## Question 4: Identify the OS (OS Fingerprinting) - TTL

| Image | TTL Value | Operating System |
|-------|-----------|------------------|
| Image 1 | 64 | Linux or Unix-based |
| Image 2 | 255 | Cisco device (router or switch) |
| Image 3 | 128 | Windows |

---

## Question 5: Analyse the Nessus file (Ghostcat)

### 1. What is the affected port number?

**Port 8009**

| Port | Hosts |
|------|-------|
| 8009 / tcp / ajp13 | 192.168.232.139 |

### 2. What is the affected protocol?

The affected protocol in the Nessus vulnerability report is **AJP13 (Apache JServ Protocol)**, running on TCP port 8009.

### 3. What is the CVSS score of vulnerability found?

| CVSS Version | Score | Rating |
|--------------|-------|--------|
| CVSS v3.0 Base Score | 9.8 | Critical |
| CVSS v3.0 Temporal Score | 8.8 | High |
| CVSS v2.0 Base Score | 7.5 | High |
| CVSS v2.0 Temporal Score | 5.9 | Medium |

**Additional Risk Information:**
- VPR (Vulnerability Priority Rating): 8.9
- EPSS (Exploit Prediction Scoring System): 0.9447
- Risk Factor: High

### 4. Can you find any exploit related to this vulnerability?

**Description:**

A file read/inclusion vulnerability was found in the AJP connector. A remote, unauthenticated attacker could exploit this vulnerability to read web application files from a vulnerable server. In instances where the vulnerable server allows file uploads, an attacker could upload malicious JavaServer Pages (JSP) code within a variety of file types and gain remote code execution (RCE).

**Related vulnerability - CVE-2020-1745 (Red Hat Undertow AJP RCE):**

- A file inclusion vulnerability in Undertow's AJP connector
- Exploitable on default port 8009
- Attackers can read arbitrary web application files
- If file upload functionality exists, attackers can upload malicious JSP code disguised as images or other files, then include and execute it → Remote Code Execution (RCE)
- Root cause: Improper authorization checks in the AJP connector

### 5. Find CVE for this vulnerability.

**CVEs:** `CVE-2020-1938`, `CVE-2020-1745`

**CVE-2020-1938 Detail:**

When using the Apache JServ Protocol (AJP), care must be taken when trusting incoming connections to Apache Tomcat. Tomcat treats AJP connections as having higher trust than, for example, a similar HTTP connection. If such connections are available to an attacker, they can be exploited in ways that may be surprising.

In Apache Tomcat:
- 9.0.0.M1 to 9.0.0.30
- 8.5.0 to 8.5.50
- 7.0.0 to 7.0.99

Tomcat shipped with an AJP Connector enabled by default that listened on all configured IP addresses. It was expected (and recommended in the security guide) that this Connector would be disabled if not required.

This vulnerability report identified a mechanism that allowed:
- Returning arbitrary files from anywhere in the web application
- Processing any file in the web application as a JSP

Further, if the web application allowed file upload and stored those files within the web application (or the attacker was able to control the content of the web application by some other means), then this, along with the ability to process a file as a JSP, made remote code execution possible.

---

## Summary

| Question | Key Findings |
|----------|--------------|
| Q1 | Flag: `SUCTF2023[ai_is_cool]` |
| Q2 | TCP packet conversation pattern identified |
| Q3 | Highest risk: Port 445 SMB (EternalBlue) |
| Q4 | OS fingerprinting via TTL (Linux, Cisco, Windows) |
| Q5 | Ghostcat vulnerability (CVE-2020-1938) on AJP port 8009, CVSS 9.8 |
