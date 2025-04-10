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
- BouncyCastle / Java Crypto APIs
- Maven (or Gradle, optional)

---

## 🛠️ Getting Started

### 1. Clone the Repository
```bash
