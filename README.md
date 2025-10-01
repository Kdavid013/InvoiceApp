# InvoiceApp

Egyszerű számlakezelő rendszer Spring Boot segítségével.  
A projekt célja a felhasználók, szerepkörök és számlák kezelésének bemutatása jogosultság alapú hozzáférés mellett.  

---

## 🚀 Funkciók
- **Regisztráció és bejelentkezés**
  - Egyedi felhasználónév ellenőrzés
  - Minimum 8 karakteres jelszó
  - 3 hibás bejelentkezési próbálkozás után Captcha kötelező
- **Szerepkörök**
  - `ADMIN`: minden funkcióhoz hozzáfér
  - `ACCOUNTANT`: számlák létrehozása, listázása, megtekintése
  - `USER`: számlák listázása és megtekintése
- **Adminisztráció**
  - Felhasználók listázása
  - Felhasználók törlése
  - Szerepkörök állítása (több szerepkör is adható)
- **Számlák kezelése**
  - Listázás
  - Létrehozás
  - Megtekintés (read-only)

---

## 🛠️ Technológiák
- **Backend:** Spring Boot (Java)
- **Adatbázis:** H2 (in-memory)
- **Frontend:** Thymeleaf
- **Biztonság:** Spring Security

---

## 🗄️ Alap felhasználók
Az alkalmazás induláskor feltölt három felhasználót (data.sql alapján):

| Név        | Felhasználónév | Jelszó     | Szerepkör     |
|------------|----------------|------------|---------------|
| Admin      | Admin          | 123456789  | ADMIN         |
| User       | User           | 123456789  | USER          |
| Accountant | Accountant     | 123456789  | ACCOUNTANT    |

---

## ▶️ Futtatás
```bash
mvn spring-boot:run
```
Ezután az alkalmazás itt lesz elérhető:
http://localhost:8080

---

## 🧪 Tesztelés

UserServiceTest tartalmaz unit és integrációs teszteket.
RolesServiceTest teszteli a szerepek lekérését
InvoiceServiceTest lefedi a fő funkciókat (létrehozás, törlés, listázás).
REST API végpontok Postman-ben is kipróbálhatók.

---

## 📬 Postman Collection

A postman/InvoiceApp.postman_collection.json fájl importálható Postman-be az Import gombbal.
Ez tartalmazza a legfontosabb endpointokat (regisztráció, login, számla CRUD, admin funkciók).

[My Collection.postman_collection.json](https://github.com/user-attachments/files/22645855/My.Collection.postman_collection.json)
