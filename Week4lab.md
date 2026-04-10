# Vulnerabilities Analysis
**Author:** Muhammad Afiq Izzuddin Bin Saifullizan

This repository contains forensic analysis of various files using steganography and file identification tools on Kali Linux.

---

## 1. Ocean.jpg

* **Tool Used:** `exiftool`
<img src="https://github.com/user-attachments/assets/1310d216-7990-46e7-bed9-5ea8aa0cb790" width="600" alt="Exiftool metadata output">
<img width="671" height="115" alt="image" src="https://github.com/user-attachments/assets/86bd7255-5ea7-4052-90b3-bf920b38d4bb" />

* **Analysis:** Checked the metadata for hidden strings.
* **Findings:** The `Comment` field contained the hidden flag.
* **Flag:** `THIS IS THE HIDDEN FLAG`

---

## 2. Dog.jpg
*   **Tool Used:** `binwalk`
*   **Analysis:** Scanned the image for embedded files and extracted them.
*   **Discovery:**
    *   **JPEG Data:** `0x0`
    *   **Zip Archive:** `0x15890` (contains `hidden_text.txt`)
*   **Extraction Process:**
    ```bash
    binwalk -e dog.jpg
    cd _dog.jpg.extracted
    cat hidden_text.txt
    ```
    <img width="1146" height="551" alt="image" src="https://github.com/user-attachments/assets/0e7a2f50-8118-470f-8c90-3daaf82d3ef1" />

*   **Flag:** `THIS IS A HIDDEN FLAG`

---

## 3. Solitaire.exe
*   **Tool Used:** `file`
<img width="617" height="62" alt="image" src="https://github.com/user-attachments/assets/e35f4c66-76ff-469d-b00e-c66525ddd57a" />

*   **Analysis:** Verified the file signature against the extension.
*   **Findings:** Despite the `.exe` extension, the file is actually **PNG image data** (640 x 449, 8-bit/color RGBA).
*   **Vulnerability:** Extension spoofing/Masquerading.

---

## 4. Rubiks.jpg
*   **Tools Used:** `file`, `binwalk`
<img width="582" height="67" alt="image" src="https://github.com/user-attachments/assets/a1087c43-d4ac-457f-b321-165e14805d39" />
<img width="1627" height="475" alt="image" src="https://github.com/user-attachments/assets/559d07c2-348b-4c7e-9802-28bbbcaeeaf8" />

*   **Analysis:**
    1.  **File Identification:** Identified as a PNG image disguised as a JPEG.
    2.  **Binwalk Scan:** Found Zlib compressed data at `0x29`.
*   **Checksum Analysis:**
    *   **MD5 Checksum:** `e96542f3574d8af2b1c93b0918fb1d9b`
    *   **Translated Keyword:** `SHADOW`

---

## 5. Computer.jpg
*   **Tool Used:** Hex Editor / Strings
<img width="858" height="238" alt="image" src="https://github.com/user-attachments/assets/44b7296d-3e0a-4fc9-94b9-af8c75675155" />

*   **Observation:**
    ```text
    00000000 FF D8 FF E0 00 10 4A 46 49 46 ...
    00000010 00 48 00 00 FF E2 02 1C 49 43 ...
    ```
*   **Technical Note:** The JPEG **APP2 (FF E2)** segment length does not match the actual ICC profile data size, indicating potential data manipulation or corruption within the profile segment.
