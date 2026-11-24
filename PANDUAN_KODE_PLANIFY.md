# Panduan Kode Proyek Planify

Selamat datang di dokumentasi resmi proyek **Planify**! Panduan ini dirancang khusus dalam Bahasa Indonesia untuk membantu mahasiswa dan pemula dalam memahami struktur, logika, serta komponen pengembangan aplikasi Android menggunakan **Kotlin**, **Jetpack Compose**, dan **XML Layout**.

---

## 1. Struktur Folder Project

Proyek Planify mengikuti arsitektur standar Android dengan struktur package utama `com.example.planify`:

```text
app/src/main/
├── AndroidManifest.xml
├── java/com/example/planify/
│   ├── AktivitasUtama.kt          (Activity utama berbasis XML Layout)
│   ├── LayarBeranda.kt            (Header Dashboard - Compose)
│   ├── LayarMasuk.kt              (Halaman Login - Compose)
│   ├── LayarDaftar.kt             (Halaman Register - Compose)
│   ├── LayarCatatan.kt            (Halaman Catatan - Compose)
│   ├── LayarKegiatan.kt           (Halaman Daftar Kegiatan - Compose)
│   ├── LayarKalender.kt           (Halaman Kalender - Compose)
│   ├── LayarProfil.kt             (Halaman Profil - Compose)
│   ├── LayarPembuka.kt            (Splash Screen - Compose)
│   └── PengelolaAutentikasi.kt    (Repository Firebase Auth)
└── res/
    ├── drawable/                  (Background, shape, dan ikon vector)
    ├── layout/                    (File XML layout UI)
    ├── mipmap/                    (Launcher icon aplikasi)
    └── values/                    (Warna, tema, dan teks strings)
```

---

## 2. Fungsi Setiap File Kotlin

- **`AktivitasUtama.kt`**: Activity utama yang dijalankan saat aplikasi dibuka. Menampilkan antarmuka Dashboard berbasis XML (`aktivitas_beranda.xml`) lengkap dengan interaksi tombol dan navigasi bawah.
- **`LayarBeranda.kt`**: Komponen Compose untuk bagian kepala/header beranda.
- **`LayarMasuk.kt`**: Komponen Compose untuk antarmuka halaman Login.
- **`LayarDaftar.kt`**: Komponen Compose untuk antarmuka halaman Pendaftaran akun baru beserta validasi input.
- **`LayarCatatan.kt`**: Halaman placeholder untuk fitur catatan.
- **`LayarKegiatan.kt`**: Halaman placeholder untuk daftar kegiatan.
- **`LayarKalender.kt`**: Halaman placeholder untuk kalender.
- **`LayarProfil.kt`**: Halaman placeholder untuk profil pengguna.
- **`LayarPembuka.kt`**: Halaman *Splash Screen* dengan animasi loading dan ilustrasi kalender berbasis Canvas.
- **`PengelolaAutentikasi.kt`**: Kelas penghubung (*Repository*) untuk berinteraksi langsung dengan Firebase Authentication.

---

## 3. Fungsi Setiap File XML

- **`aktivitas_beranda.xml`**: Layout utama berbasis XML yang mendesain tampilan Dashboard (Header, Sapaan, Ringkasan Hari Ini, Kegiatan Mendatang, FAB, dan Navigasi Bawah).
- **`latar_kartu_navy.xml`**: Drawable bentuk persegi dengan sudut melengkung berwarna navy (`#263F6A`) untuk kartu ringkasan.
- **`latar_kegiatan_kosong.xml`**: Drawable latar belakang area *empty state* kegiatan.
- **`latar_navigasi_bawah.xml`**: Drawable latar belakang menu navigasi bawah dengan sudut atas melengkung.
- **`latar_menu_terpilih.xml`**: Indikator latar belakang biru muda untuk menu navigasi yang aktif.
- **`latar_ikon_profil.xml`**: Lingkaran avatar profil dengan garis tepi.
- **File Vektor (`ic_*.xml`)**: Berbagai ikon pendukung seperti ikon beranda, kalender, catatan, checklist, profil, dan tombol tambah (`+`).

---

## 4. Fungsi Setiap Class

- **`AktivitasUtama`**: Kelas `AppCompatActivity` yang menjadi titik masuk visual berbasis XML Layout.
- **`PengelolaAutentikasi`**: Kelas helper/repository untuk membungkus operasi autentikasi Firebase.

---

## 5. Fungsi Setiap Function Penting

- **`onCreate()`**: Fungsi bawaan Android Activity yang dipanggil pertama kali saat aktivitas dibuat.
- **`daftarkanPengguna()`**: Fungsi suspend untuk membuat akun Firebase Auth (`createUserWithEmailAndPassword`) dan memperbarui profil nama lengkap (`updateProfile`).
- **`LayarDaftar()`**: Fungsi Composable utama yang merender form pendaftaran dan menangani logika klik tombol daftar.

---

## 6. Cara Kerja Navigasi Aplikasi

Aplikasi Planify menggunakan **AktivitasUtama** sebagai penampil utama layout XML `aktivitas_beranda.xml`. Pada layout tersebut, terdapat komponen **Navigasi Bawah** (*Bottom Navigation*) yang terdiri dari 5 menu interaktif (Beranda, Jadwal, Catatan, Kegiatan, Kalender) yang dihubungkan dengan *event click listener* di `AktivitasUtama.kt`.

---

## 7. Cara Kerja Login dan Register

- **Pendaftaran (`LayarDaftar.kt`)**: Pengguna mengisi nama lengkap, email, password, dan konfirmasi password. Setelah divalidasi, `PengelolaAutentikasi` mengirim data ke Firebase. Jika sukses, akun tercipta di server Firebase Console.
- **Masuk (`LayarMasuk.kt`)**: Pengguna memasukkan kredensial email dan password untuk masuk ke dalam aplikasi.

---

## 8. Cara Kerja Firebase Authentication

Firebase Auth menangani enkripsi password dan manajemen sesi pengguna secara otomatis di cloud. Di proyek ini, `google-services.json` menghubungkan aplikasi Android dengan project Firebase Planify di server Google secara aman.

---

## 9. Hubungan Antara XML dan Kotlin

Dalam pendekatan XML Layout (seperti pada Dashboard):
1. Desain UI dibuat di file XML (`aktivitas_beranda.xml`).
2. Di file Kotlin (`AktivitasUtama.kt`), komponen XML diambil menggunakan fungsi `findViewById<T>(R.id.namaID)`.
3. Logika interaksi (seperti `setOnClickListener`) dipasangkan pada komponen tersebut melalui Kotlin.

---

## 10. Penjelasan Dasar Jetpack Compose

Jetpack Compose adalah toolkit modern Android untuk membangun UI secara deklaratif (tanpa XML):
- **`@Composable`**: Anotasi penanda fungsi pembuat komponen UI.
- **`remember` & `mutableStateOf`**: Menyimpan dan memantau perubahan state data (seperti input teks atau status loading).
- **`Modifier`**: Objek untuk mengatur tata letak, ukuran, padding, dan warna komponen.
