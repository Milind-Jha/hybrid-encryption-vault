# 🔐 Hybrid Encryption Vault

A Java-based hybrid encryption system that securely encrypts and decrypts large data by combining the performance of AES with the security of RSA. This project is ideal for secure data transmission, vault-like file protection, and enterprise-grade data security.

## 🚀 Features

- 🔑 **Asymmetric RSA** encryption of AES keys
- 🔒 **Symmetric AES** encryption for large payloads
- 📁 Support for text and file encryption/decryption
- 🧪 Easily testable and extensible utility classes
- 🛡️ Designed with cryptographic best practices

## 🧠 How It Works

1. Generate a **random AES key** and IV.
2. Encrypt the data with AES.
3. Encrypt the AES key using **RSA public key**.
4. Store/transmit the AES-encrypted data + RSA-encrypted key.

This is the industry-standard approach used in HTTPS, secure messaging, and encrypted file systems.

## 🧰 Tech Stack

- Java 17+
- Java Crypto APIs/Spring Boot 2
- Maven (or Gradle, optional)

## 🛡️ Commercial Licensing

This project is available under a dual-license model.

- **Free for educational and research use** under [CC BY-NC 4.0](https://creativecommons.org/licenses/by-nc/4.0/)
- **Commercial use requires a license**. Contact [jhamilind61@gmail.com](jhamilind61@gmail.com) to request a commercial license and pricing.

Please refer to [`LICENSE-COMMERCIAL.txt`](./LICENSE-COMMERCIAL.txt) for commercial terms.


---

## 🛠️ Getting Started

### 1. Clone the Repository
```bash
