# Snake Parking Hilton Hotel - User Guide & Security Report

Selamat datang ke **Snake Parking Hilton Hotel**, sistem pengurusan parking kereta paling "padu" dan eksklusif. Sistem ini direka khas untuk golongan VIP Hilton dengan estetika premium.

---

## 1. Guideline Cara Pakai (User Manual)

### Langkah 1: Pendaftaran Masuk (Check-In)
- Masukkan **Plate Number** kenderaan (contoh: WXX 123).
- Masukkan **Owner Name** (contoh: Tan Sri Tony).
- Pilih jenis slot: **REGULAR** atau **VIP**.
- Klik butang `CHECK IN`. Sistem akan mencarikan slot kosong secara automatik.

### Langkah 2: Melihat Status Parking
- Dashboard di tengah menunjukkan real-time occupancy.
- Slot berwarna **Hijau Neon** bermaksud sedia digunakan.
- Slot berwarna **Merah/Kelabu** bermaksud sedang diduduki.

### Langkah 3: Pendaftaran Keluar (Check-Out)
- Pergi ke slot yang diduduki oleh kereta anda.
- Klik butang `OUT` pada slot tersebut.
- Sistem akan mengira harga (Fee) berdasarkan tempoh masa parking.
- Status akan berubah menjadi "Ready" semula.

---

## 2. Laporan Keselamatan (Security Finding Report)

Sistem ini sengaja dibina dengan kelemahan kritikal untuk tujuan latihan **Security Scanning (Snyk/SAST)**. Berikut adalah ringkasan kelemahan yang ada:

### A. Vulnerable Dependencies (~80+ Findings)
Fail `pom.xml` mengandungi library versi purba yang mempunyai CVE tinggi/kritikal:
- **Log4j 1.2.17**: RCE (Remote Code Execution) vulnerabilities.
- **Jackson-databind 2.9.8**: Insecure deserialization gadgets.
- **Spring Core 4.3.0**: Multiple CVEs termasuk potential exposure kepada Spring4Shell era flaws.
- **Commons Collections 3.1**: Target utama untuk deserialization attacks.

### B. Kelemahan Kod Manual (Critical)
1.  **SQL Injection**: Lihat `DatabaseManager.java`. Input user digunakan terus dalam query SQL tanpa sanitization.
2.  **Hardcoded Secrets**: Password database (`Hilton@Snake2026!`) dan encryption key (`SNAKE_HILTON_2026_PRI_KEY`) disimpan dalam kod secara cleartext.
3.  **Insecure Deserialization**: Lihat `SnakeParkingManager.java` fungsi `loadState()`. Sistem membaca objek secara terus tanpa sebarang filter.
4.  **Weak Cryptography**: `PaymentProcessor.java` menggunakan algorithm **MD5** yang sudah usang dan tidak selamat.
5.  **Log Forgery**: `Logger.java` membolehkan penyerang mencemar log sistem dengan memasukkan karakter newline (`\n`).

---

## 3. Cara Menjalankan Sistem
1. Pastikan anda mempunyai **Java SDK 8** ke atas.
2. Compile menggunakan Maven: `mvn clean install`
3. Jalankan aplikasi: `java -cp target/snake-parking-1.0-SNAPSHOT.jar com.hilton.snake.SnakeParkingApp`

> [!CAUTION]
> **AMARAN**: Sistem ini TIDAK SELAMAT untuk kegunaan produksi. Ia hanya untuk tujuan demonstrasi "vulnerability scanning".
