# VA Week 8 - Network Enumeration & Scanning

## Challenge 2 - Fast Nmap Scan

**Normal scan** = Checking every door in a huge skyscraper (65,535 doors)

**Fast scan (-F)** = Only checking the 100 most used entrances (lobby, loading dock, main office doors)

---

## Challenge 11 - SMB NSE Enumeration

- Port 445 is open (file sharing available)
- It's a VMware virtual machine (from MAC address starting `00:0C:29`)
- Response was super fast (0.00047 seconds) - very close to you

### Key Findings:

1. There's a Windows computer (`DESKTOP-V7H90P5`)
2. It's running VMware
3. File sharing is ON
4. Security is decent (no anonymous access)
5. It's on a simple WORKGROUP network

---

## Challenge 14 - SNMP NSE

**SNMP (Simple Network Management Protocol)** = A protocol that lets network admins monitor devices (routers, printers, servers, etc.)

### What the results mean:

| Port | Service | Description |
|------|---------|-------------|
| 135 open | Windows RPC service | Internal Windows communication |
| 139 open | Legacy Windows file sharing | Older SMB protocol |
| 445 open | Modern Windows file sharing (SMB) | Current file sharing protocol |

**OS detected:** Windows

**MAC address:** VMware virtual machine (`00:0C:29` prefix)

---

## Challenge 17 - OS Detection

### What it does:

Detects the target's operating system (Windows, Linux, macOS, etc.) by analyzing how it responds to network packets.

### When to use:

After discovering live hosts, use `-O` to identify what OS you're targeting before selecting specific attack tools.

---

## Challenge 7 - SMTP VRFY / EXPN

The server responded with `252`, confirming the user `root` exists.

- This confirms **VRFY is ENABLED** on this mail server
- Server is running **Postfix on Ubuntu** (Metasploitable)

---

## Challenge 3 - DNS Records

### What this command does:

Queries a DNS server to find the IP address(es) associated with a domain name (e.g., google.com).

**Example command:**
```bash
nslookup google.com
# or
dig google.com
