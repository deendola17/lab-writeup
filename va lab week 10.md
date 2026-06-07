# VA Week 10 Lab: Vulnerability Assessment on Metasploitable 2

## LAB 1: Vulnerability Scanning

### 1. Define Scan Scope (IP, Host, Purpose)

#### 1. IP Address
- **Specific IP:** 192.168.56.135
- **Network Range:** Single host only
- **Exclusions:** All other IPs on the network (including the Kali host, router, gateway, and any other VMs) are explicitly excluded from the scan

#### 2. Host
- **Operating System:** Ubuntu 8.04
- **Hostname:** metasploitable
- **Network Interface:** NAT Adapter or production networks
- **Credentials:** None provided

#### 3. Purpose
**Primary Goal:** Identify known vulnerabilities in intentionally outdated software

**Specific Focus Areas:**
- Open network ports and running services
- Default or weak credentials on services
- Backdoor services
- Missing security patches
- Misconfigurations exposing sensitive information

---

### 2. Run Scan

![Nessus Scan Results](scan_results.png)

---

### 3. Top 5 Vulnerabilities, CVE IDs, CVSS Score & Severity

| Rank | Vulnerability Name | CVE ID(s) | CVSS Score | Severity |
|------|--------------------|-----------|------------|----------|
| 1 | UnrealIRCd Backdoor Detection | CVE-2010-2075 | 10.0 | CRITICAL |
| 2 | Debian OpenSSH/OpenSSL Package Random Number Generator Weakness | CVE-2008-0166 | 7.5 (CVSS v3) / 10.0 (CVSS v2) | CRITICAL |
| 3 | Bash Remote Code Execution (Shellshock) | CVE-2014-6271 | 9.8 | CRITICAL |
| 4 | SSL Version 2 and 3 Protocol Detection | CVE-2014-3566 (POODLE) | 9.8 | CRITICAL |
| 5 | VNC Server 'password' Password | CVE-2025-46352 | 10.0* | CRITICAL |

> **Note:** CVE-2025-46352 is actually for a fire panel system. The VNC issue is properly classified as CWE-798 (Hard-coded Credentials).

---

### 4. Export Scan Results (PDF)

Unable to export in Nessus Essentials (free version limitation).

![Nessus Export Limitation](export_limitation.png)

---

## LAB 2: CVE, CVSS & CWE Correlation

### 1. UnrealIRCd Backdoor Detection (CVE-2010-2075)

#### CVSS Score & Vector
- **CVSS v2 Score:** 10.0 (Critical)
- **Attack Vector:** Network
- **Privileges Required:** None
- **User Interaction:** None

#### CWE Mapping
**CWE-912:** Hidden Functionality (Backdoor) — The UnrealIRCd source code was maliciously modified on certain mirror sites

#### Exploitable in Metasploitable 2?
**YES**

The UnrealIRCd 3.2.8.1 backdoor is one of the most well-known vulnerabilities on Metasploitable 2. Metasploit contains a dedicated module for this exact backdoor. The backdoor triggers when a malicious payload is sent to the IRC server, granting the attacker remote code execution with root privileges. No authentication is required, making it trivial to exploit.

---

### 2. Debian OpenSSH/OpenSSL RNG Weakness (CVE-2008-0166)

#### CVSS Score & Vector
- **CVSS v3.1 Score:** 7.5 (High)
- **CVSS v2 Score:** 10.0 (Critical)
- **Attack Vector:** Network
- **Attack Complexity:** Low
- **Privileges Required:** None
- **User Interaction:** None

#### CVSS v3.1 Vector String
`CVSS:3.1/AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:N/A:N`

#### CWE Mapping
**CWE-338:** Use of Cryptographically Weak Pseudo-Random Number Generator (PRNG)

#### Exploitable in Metasploitable 2?
**YES**

Metasploitable 2 runs Ubuntu 8.04, which falls within the affected timeframe (September 2006 - May 2008). The OpenSSL PRNG was crippled to only use the process ID as entropy, resulting in only 32,767 possible keys. An attacker can pre-compute all possible keys for a given key type and size, then brute-force the actual key. Exploitation requires either:
- Obtaining the public key first, then brute-forcing the private key offline
- Performing a man-in-the-middle attack to capture encrypted traffic

---

### 3. Bash Remote Code Execution — Shellshock (CVE-2014-6271)

#### CVSS Score & Vector
- **CVSS v3 Score:** 9.8 (Critical)
- **Attack Vector:** Network
- **Attack Complexity:** Low
- **Privileges Required:** None
- **User Interaction:** None

#### CWE Mapping
**CWE-94:** Improper Control of Generation of Code ('Code Injection')

#### Exploitable in Metasploitable 2?
**YES**

The Shellshock vulnerability allows remote attackers to execute arbitrary code by crafting environment variables that contain trailing strings after function definitions. On Metasploitable 2, the vulnerability can be exploited through:
- Apache CGI scripts — Sending malicious HTTP headers
- SSH ForceCommand — If configured
- DHCP clients — When processing malicious DHCP responses

---

### 4. SSL Version 2 and 3 Protocol Detection — POODLE (CVE-2014-3566)

#### CVSS Score & Vector
- **CVSS v3.1 Score:** 4.3 (Medium)
- **CVSS v2 Score:** 4.3 (Medium)
- **Attack Vector:** Network
- **Attack Complexity:** High
- **Privileges Required:** None
- **User Interaction:** Required

#### CVSS v3.1 Vector String
`CVSS:3.1/AV:N/AC:H/PR:N/UI:R/S:C/C:L/I:N/A:N`

#### CWE Mapping
**CWE-327:** Use of a Broken or Risky Cryptographic Algorithm

#### Exploitable in Metasploitable 2?
**YES (with limitations)**

The POODLE attack allows a remote attacker to obtain sensitive information by exploiting the SSLv3 protocol's design error. Exploitation requires:
1. Man-in-the-middle position
2. Ability to downgrade connection from TLS to SSLv3
3. Multiple connections to progressively decrypt data

---

### 5. VNC Server 'password' Password

> **⚠️ Important Note:** CVE-2025-46352 is INCORRECT for this vulnerability. It applies to a fire panel system, not Metasploitable 2.

The actual issue is **hard-coded default credentials** — the VNC server is configured with the password `"password"`.

#### CVSS Score & Vector
- **CVSS v2 Score:** 10.0 (Critical)
- **CVSS v3 Score:** Approximately 9.8 (Critical)
- **Attack Vector:** Network
- **Attack Complexity:** Low
- **Privileges Required:** None
- **User Interaction:** None

#### CWE Mapping
**CWE-798:** Use of Hard-coded Credentials

#### Exploitable in Metasploitable 2?
**YES**

Any attacker who discovers the VNC service on port 5900 can connect using any VNC client, enter the password `"password"`, and gain full graphical remote access to the system with root privileges.

---

## LAB 3: Manual Validation

### 1. UnrealIRCd Backdoor Detection (CVE-2010-2075)

![UnrealIRCd Validation](unrealircd_validation.png)

**Verdict:** ✅ True Positive

**Justification:** The backdoor was inserted into the official UnrealIRCd 3.2.8.1 source code on mirrors between November 2009 and June 12, 2010. The backdoor triggers when a command starting with the string "AB" is sent to the server, which then executes arbitrary commands via `system()`.

---

### 2. Debian OpenSSH/OpenSSL RNG Weakness (CVE-2008-0166)

![SSH RNG Validation](ssh_rng_validation.png)

**Verdict:** ✅ True Positive

**Justification:** The vulnerability stems from a Debian OpenSSL patch that removed two lines of code from `_md_rand.c`, which seeded the PRNG using the current process ID only — reducing entropy to 32,767 possible keys.

---

### 3. Bash Remote Code Execution - Shellshock (CVE-2014-6271)

![Shellshock Validation](shellshock_validation.png)

**The output clearly shows:**
1. The malicious User-Agent header injected a command
2. The `id` command executed on the target system
3. The output `uid=33(www-data)` appeared before the normal CGI script output

**Verdict:** ✅ True Positive

---

### 4. SSL Version 2 and 3 Protocol Detection + POODLE (CVE-2014-3566)

![POODLE Validation](poodle_validation.png)

**Verdict:** ✅ True Positive

**Justification:**
- The SMTP service on port 25 explicitly supports SSLv3
- Nmap's `ssl-enum-ciphers` script identified the CBC-mode cipher vulnerability (CVE-2014-3566)
- SSLv3 is vulnerable to the POODLE attack, allowing MITM attackers to decrypt communications
- Additional weaknesses include anonymous key exchange, export-grade ciphers, and broken RC4 cipher

---

### 5. VNC Server 'password' Password (Default Credentials)

![VNC Validation](vnc_validation.png)

**Verdict:** ✅ True Positive

**Justification:** The VNC server on Metasploitable 2 is configured with the hard-coded password `"password"`.
- Deliberate training vulnerability
- No CVE assigned (configuration choice, not software flaw)
- No authentication restrictions
- Anyone who discovers the VNC service can gain graphical root access

---

## LAB 4: Risk Ranking & Remediation

### 1. Rank Vulnerabilities Based on Exploitability, Impact, Exposure

| Priority | Vulnerability | CVSS | Exploitability | Impact | Exposure | Why This Rank |
|----------|---------------|------|----------------|--------|----------|----------------|
| 1 | VNC Default Password | 10.0 | Very High | Critical (Root) | Internal Network | No exploit needed; default password gives immediate root access |
| 2 | UnrealIRCd Backdoor | 10.0 | Very High | Critical (Root) | Internal Network | Metasploit module exists; one command gives root shell |
| 3 | Shellshock | 9.8 | High | High (www-data) | Internal Network | Requires CGI script; many vectors available |
| 4 | Debian SSH RNG Weakness | 7.5/10.0 | Medium | High (Key compromise) | Internal Network | Requires brute-force or pre-computed keys |
| 5 | SSLv3 + POODLE | 4.3 | Low | Medium (Data decryption) | Internal Network | Requires MITM position; user interaction needed |

---

### 2. Remediation Priority List

| Priority | Vulnerability | CVE ID | CVSS | Remediation Action |
|----------|---------------|--------|------|--------------------|
| 1 (Highest) | VNC Server 'password' Password | CWE-798 | 10.0 | Change VNC password or disable VNC service immediately |
| 2 | UnrealIRCd Backdoor Detection | CVE-2010-2075 | 10.0 | Replace with clean UnrealIRCd version or disable IRC service |
| 3 | Bash Remote Code Execution (Shellshock) | CVE-2014-6271 | 9.8 | Upgrade Bash package or disable Apache mod_cgi |
| 4 | Debian OpenSSH RNG Weakness | CVE-2008-0166 | 7.5/10.0 | Regenerate all SSH host keys and user keys |
| 5 (Lowest) | SSL Version 2 and 3 Protocol Detection (POODLE) | CVE-2014-3566 | 4.3 | Disable SSLv2 and SSLv3 on SMTP service (port 25) |

---

### 3. Why a Medium CVSS May Be More Dangerous Than a High

| CVSS 7.5 (High) - SSH Weakness | CVSS 4.3 (Medium) - POODLE |
|--------------------------------|----------------------------|
| Requires brute-force offline | Requires MITM position |
| Requires key pre-computation | Attacker must be on the network |
| May not lead to immediate root | Data decryption only |
| 2-10 hours to exploit | Active network position required |

**Why a Medium can be more dangerous than a High in YOUR environment:**

| CVSS 7.5 (High) - SSH Weakness | CVSS 4.3 (Medium) - VNC Default Password |
|--------------------------------|-------------------------------------------|
| Requires brute-force offline | **No skill required** |
| Requires key pre-computation | **Any attacker can do it** |
| May not lead to immediate root | **Immediate root graphical access** |
| 2-10 hours to exploit | **10 seconds to exploit** |

**Key Takeaways:**
- Default credentials are the #1 attack vector in real breaches
- Exploitability is trivial (CVSS doesn't fully capture this)
- The impact is immediate and complete
- CVSS score is a starting point, not the final answer

---

## Summary

All 5 vulnerabilities were successfully validated as **True Positives** on Metasploitable 2. The most critical remediation priority is the VNC default password, followed by the UnrealIRCd backdoor, Shellshock, SSH RNG weakness, and finally the POODLE SSLv3 vulnerability.

---
*Lab completed on Kali Linux against Metasploitable 2 target (192.168.56.135)*
