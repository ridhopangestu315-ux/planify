# Laporan Optimasi Proyek Planify

Dokumentasi ini merangkum seluruh hasil audit, pembersihan, dan penataan ulang struktur proyek Android **Planify** agar bersih dari duplikasi, terorganisasi dengan baik, menggunakan penamaan berbahasa Indonesia secara konsisten, serta siap untuk dikembangkan lebih lanjut.

---

## 1. Struktur Project Sebelum Optimasi

Sebelum optimasi, proyek memiliki banyak file duplikat dengan penamaan campuran (Bahasa Inggris dan Bahasa Indonesia), sisa eksperimen refactoring, serta layout XML ganda (`activity_main.xml`, `activity_dashboard.xml`, `MainActivity.kt`, `AktivitasUtama.kt`, dll.).

---

## 2. Struktur Project Setelah Optimasi

```text
app/src/main/
├── AndroidManifest.xml
├── java/com/example/planify/
│   ├── AktivitasUtama.kt          (Activity utama berbasis XML Layout)
│   ├── LayarBeranda.kt            (Komponen kepala beranda - Compose)
│   ├── LayarMasuk.kt              (Halaman Login - Compose)
│   ├── LayarDaftar.kt             (Halaman Pendaftaran - Compose)
│   ├── LayarCatatan.kt            (Halaman Catatan - Compose)
│   ├── LayarKegiatan.kt           (Halaman Kegiatan - Compose)
│   ├── LayarKalender.kt           (Halaman Kalender - Compose)
│   ├── LayarProfil.kt             (Halaman Profil - Compose)
│   ├── LayarPembuka.kt            (Splash Screen - Compose)
│   └── PengelolaAutentikasi.kt    (Repository Firebase Auth)
└── res/
    ├── drawable/                  (latar_*.xml & ikon vektor ic_*.xml)
    ├── layout/                    (aktivitas_beranda.xml)
    ├── mipmap/                    (Launcher icons)
    ├── values/                    (Colors, strings, themes)
    └── xml/                       (Backup dan data extraction rules)
```

---

## 3. Daftar File yang Dihapus beserta Alasan

Berikut adalah file-file duplikat (versi Bahasa Inggris lama) yang berhasil dihapus dari proyek:
1. `MainActivity.kt` — Digantikan oleh `AktivitasUtama.kt`.
2. `DashboardScreen.kt` — Digantikan oleh `LayarBeranda.kt`.
3. `LoginScreen.kt` — Digantikan oleh `LayarMasuk.kt`.
4. `RegisterScreen.kt` — Digantikan oleh `LayarDaftar.kt`.
5. `NotesScreen.kt` — Digantikan oleh `LayarCatatan.kt`.
6. `ActivityScreen.kt` — Digantikan oleh `LayarKegiatan.kt`.
7. `CalendarScreen.kt` — Digantikan oleh `LayarKalender.kt`.
8. `ProfileScreen.kt` — Digantikan oleh `LayarProfil.kt`.
9. `SplashScreen.kt` — Digantikan oleh `LayarPembuka.kt`.
10. `AuthRepository.kt` — Digantikan oleh `PengelolaAutentikasi.kt`.
11. `activity_dashboard.xml` — Digantikan oleh `aktivitas_beranda.xml`.
12. `activity_main.xml` — Layout bawaan kosong yang tidak digunakan lagi.
13. `bg_navy_card.xml` — Digantikan oleh `latar_kartu_navy.xml`.
14. `bg_empty_state.xml` — Digantikan oleh `latar_kegiatan_kosong.xml`.
15. `bg_bottom_nav.xml` — Digantikan oleh `latar_navigasi_bawah.xml`.
16. `bg_nav_selected.xml` — Digantikan oleh `latar_menu_terpilih.xml`.
17. `bg_profile_circle.xml` — Digantikan oleh `latar_ikon_profil.xml`.

---

## 4. Daftar File yang Digabung

Tidak ada penggabungan logika yang merusak fungsi; seluruh fungsionalitas dari file lama telah dipindahkan sepenuhnya ke file baru dengan penamaan berbahasa Indonesia secara bersih dan terstruktur.

---

## 5. Daftar File yang Dipertahankan beserta Fungsinya

- **`AktivitasUtama.kt`**: Activity utama yang memuat `aktivitas_beranda.xml` dan mengatur interaksi tombol FAB serta navigasi bawah.
- **`LayarBeranda.kt`**: Komponen Compose untuk kepala beranda aplikasi.
- **`LayarMasuk.kt`**: Tampilan halaman masuk (Login) dengan Jetpack Compose.
- **`LayarDaftar.kt`**: Tampilan halaman pendaftaran (Register) dengan integrasi Firebase Auth dan validasi input.
- **`LayarCatatan.kt`**: Halaman placeholder catatan.
- **`LayarKegiatan.kt`**: Halaman placeholder daftar kegiatan.
- **`LayarKalender.kt`**: Halaman placeholder kalender.
- **`LayarProfil.kt`**: Halaman placeholder profil.
- **`LayarPembuka.kt`**: Halaman *Splash Screen* dengan animasi progress bar dan ilustrasi kalender Canvas.
- **`PengelolaAutentikasi.kt`**: Repository untuk autentikasi Firebase.
- **`aktivitas_beranda.xml`**: Layout XML Dashboard utama.
- **File Drawable (`latar_*.xml` & `ic_*.xml`)**: Aset visual dan ikon.

---

## 6. Daftar Kode yang Dioptimasi

- Menghilangkan duplikasi penamaan variabel bahasa Inggris (`fullName`, `email`, `password`, `isLoading`, dll.) menjadi Bahasa Indonesia (`namaLengkap`, `alamatEmail`, `kataSandi`, `sedangMemuat`, dll.).
- Memperbarui seluruh referensi ID pada XML (`fabAdd` $\rightarrow$ `tombolTambah`, `navHome` $\rightarrow$ `menuBeranda`, dll.).
- Menambahkan dokumentasi KDoc / komentar penjelasan dalam Bahasa Indonesia pada setiap file utama.

---

## 7. Daftar Duplikasi yang Berhasil Dihilangkan

- 10 pasang file Kotlin duplikat (English vs Indonesia).
- 2 file layout XML duplikat.
- 5 file drawable XML duplikat.

---

## 8. Fitur yang Berhasil Dipertahankan

- **Firebase Authentication:** Pendaftaran akun pengguna dengan email, password, dan pembaruan profil nama lengkap.
- **Dashboard UI (XML):** Tampilan beranda lengkap dengan kartu ringkasan harian, kegiatan mendatang, FAB, dan Navigasi Bawah.
- **Splash Screen & Pratinjau Compose:** Animasi pembuka dan seluruh pratinjau Composable berfungsi normal.

---

## 9. Hasil Pengujian Firebase

- Integrasi `google-services.json` dan `google-services` Gradle plugin terpasang dengan benar.
- Fungsi registrasi `createUserWithEmailAndPassword` terhubung tanpa error ke backend Firebase Auth.

---

## 10. Hasil Gradle Build

- Perintah build `app:assembleDebug` dijalankan dan sukses dengan status **`BUILD SUCCESSFUL`**.
