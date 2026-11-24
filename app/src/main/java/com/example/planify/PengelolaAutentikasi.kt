package com.example.planify

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

/**
 * Kelas PengelolaAutentikasi bertanggung jawab untuk mengelola proses autentikasi pengguna
 * dengan Firebase Authentication (seperti pendaftaran akun dan pembaruan profil).
 */
class PengelolaAutentikasi {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Mendaftarkan pengguna baru ke Firebase Authentication menggunakan alamat email dan kata sandi.
     * Setelah berhasil, memperbarui profil pengguna dengan nama lengkap.
     */
    suspend fun daftarkanPengguna(
        namaLengkap: String,
        alamatEmail: String,
        kataSandi: String
    ): Result<Unit> {
        return try {
            // Membuat akun baru di Firebase Auth
            val hasilAutentikasi = auth.createUserWithEmailAndPassword(alamatEmail, kataSandi).await()
            val pengguna = hasilAutentikasi.user

            // Jika berhasil, perbarui nama tampilan (Display Name) pengguna
            if (pengguna != null) {
                val pembaruanProfil = UserProfileChangeRequest.Builder()
                    .setDisplayName(namaLengkap)
                    .build()
                pengguna.updateProfile(pembaruanProfil).await()
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
