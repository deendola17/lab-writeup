# VA Lab Week 11 - Afiq Izzuddin

## 1. Get Ready to Learn

Answer the questions below.

**I am ready to analyze phishing emails!**

*No answer needed*

---

## 2. Cancel Your Order

### Phishing Techniques Used

- **Spoofed email address:** Mimicking a trusted service to gain immediate credibility
- **URL shortening:** Using redirection services to hide the true destination of a link
- **Branded HTML:** Impersonating legitimate corporate imagery to create a sense of authenticity

---

## 3. Track Your Package

### Phishing Techniques Used

- **Spoofed email address:** Mimicking a trusted distribution center to gain immediate credibility
- **Pixel tracking:** Embedding invisible images to notify the sender when the email is opened
- **Link manipulation:** Masking a malicious destination with a fraudulent tracking number

---

## 4. Download Document Here

### Phishing Techniques Used

- **Artificial urgency:** Creating a narrow window for action to create a sense of urgency
- **Brand impersonation:** Layering trusted brands, like Microsoft and Adobe, to build a false sense of security
- **Link redirection:** Using a chain of URLs to hide the final malicious destination from basic email filters
- **Credential harvesting:** Deploying a fake login portal to capture and exfiltrate usernames and passwords

---

## 5. Your Account Is on Hold

### Phishing Techniques Used

- **Spoofed email address:** The sender's display name is set to "Netlix billing" to appear legitimate
- **Sense of urgency:** Using a suspended account notification to pressure the victim into acting quickly
- **Brand impersonation:** Utilizing HTML templates and logos to mimic Netflix billing
- **Poor grammar and typos:** Noticeable misspellings of "Netflix"
- **Attachments:** Using a file attachment rather than a direct link to hide the malicious URL

---

## 6. Your Recent Purchase

### Phishing Techniques Used

- **Spoofed email address:** The sender's display name is set to "Apple Support"
- **Recipient is BCCed:** The victim is not directly sent the email
- **Urgency:** Relies on the use of "Action Required" and a fake purchase notification
- **Poor grammar and typos:** Noticeable spelling errors within the email header
- **Attachments:** The email contains a `.dot` file (Microsoft Word Template), which is an unusual format for a receipt

---

## 7. Scheduled Shipment

### Phishing Techniques Used

- **Spoofed email address:** The sender's display name is set to "DHL Express"
- **Brand impersonation:** Utilizing HTML templates and logos to mimic DHL
- **Attachments:** An Excel document that triggers executable code upon opening

---

## 8. Conclusion

> Great work afiq.izzuddin1718! Room completed! Your skills are skyrocketing!

| Completed tasks | Points earned | Streak |
|----------------|---------------|--------|
| 8               | 56            | 2      |

---

## Introduction

Spam and Phishing remain the most common social engineering threats facing modern organizations. While spam is often low-risk, phishing can trick users into disclosing sensitive information or unknowingly deploying malware. A single unsuspecting user who clicks a malicious link or opens an attachment can give an attacker an initial foothold in the network and cause huge damage to the company.

As a defender, your role involves analyzing email components to determine if they are malicious or benign and gathering information to help harden security measures against future attacks. In this task, you will inspect raw email files and learn how to identify the true origin and intent of a suspicious message.

---

## The Email Address

### Anatomy of an Email Address

An email is composed of the following elements:

1. **Username:** User mailbox that identifies the specific recipient's mailbox on the email server
2. **@ symbol:** Separates the username from the domain and tells the system where to route the email
3. **Domain name:** Specifies the mail server responsible for receiving the message

**Analogy:** Think of an email address as a home mailing address:
- The domain is like the street or apartment building
- The username is the specific person or mailbox within that location

**Question:** Identify the domain used in the following email address: `hatsalesman@tryhatme.com`

**Answer:** `tryhatme.com`

---

## Email Delivery

When you send an email, several protocols work together behind the scenes:

| Protocol | Function |
|----------|----------|
| **SMTP** (Simple Mail Transfer Protocol) | Sends emails |
| **POP3** (Post Office Protocol) | Downloads emails to a device |
| **IMAP** (Internet Message Access Protocol) | Syncs emails across devices |

When receiving emails, your email service will use either POP3 or IMAP, depending on how your mailbox is configured.

---

## Email Headers

An email consists of two main parts:

- **Email header:** Contains metadata about the message, such as sender and the servers involved in delivery
- **Email body:** Contains the actual message content, which may be plain text or HTML

---

## Email Body

> **Use CyberChef to decode and then download the PDF to get the flag**

---

## Types of Phishing

| Type | Description |
|------|-------------|
| **Spam** | Unsolicited bulk emails sent to a large number of recipients. A more malicious form of spam is often called *malspam*. |
| **Phishing** | Emails that impersonate a trusted entity to trick recipients into revealing sensitive information. |
| **Spear Phishing** | A targeted form of phishing aimed at a specific individual or organization, often using personalized information. |
| **Whaling** | A type of spear phishing that specifically targets high-level executives (CEO, CFO) to obtain sensitive data or financial access. |
| **Smishing** | Phishing attacks conducted via SMS or text messages, targeting users on mobile devices. |
| **Vishing** | Phishing attacks carried out through voice calls, where attackers use social engineering over the phone. |

---

## Conclusion

### Further Learning

This room is the first of a series of rooms that make up the Phishing Analysis module of the SOC Level 1 Path. You are encouraged to continue your learning with the walkthrough and challenge rooms below:

- Phishing Emails in Action
- Phishing Analysis Tools
- Phishing Prevention
- The Greenholt Phish
- Snapped Phishing Line

**Question:** What attack, signified by the acronym BEC, uses a compromised email to trick employees into fraud?

**Answer:** `Business Email Compromise`

---

*End of lab content*
