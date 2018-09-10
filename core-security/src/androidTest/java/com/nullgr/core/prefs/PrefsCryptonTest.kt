package com.nullgr.core.prefs

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.nullgr.core.security.prefs.crypto.Api18PrefsCryptonImpl
import com.nullgr.core.security.prefs.crypto.Api23PrefsCryptonImpl
import com.nullgr.core.security.prefs.crypto.LegacyPrefsCryptonImpl
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.security.GeneralSecurityException

/**
 * Created by Grishko Nikita on 01.02.18.
 */
@RunWith(AndroidJUnit4::class)
class PrefsCryptonTest {

    companion object {
        private const val ALIAS = "PrefsCryptonTest_Key_Alias"
        private const val ALIAS_RSA = "PrefsCryptonTest_Key_Alias_RSA"
        private const val TEXT_TO_BE_ENCRYPTED = "This is text to be encrypted."
        private const val LOG_TAG = "---- PrefsLogTag"
    }

    @Test
    fun encrypt_LegacyPrefsCryptonImpl_Success() {
        val cryptonImpl = LegacyPrefsCryptonImpl(InstrumentationRegistry.getTargetContext(), ALIAS)
        val encrypted = cryptonImpl.encrypt(TEXT_TO_BE_ENCRYPTED)
        Log.d(LOG_TAG, "Legacy: $encrypted")
        Assert.assertNotEquals(TEXT_TO_BE_ENCRYPTED, encrypted)
    }

    @Test
    fun decrypt_afterEncryption_LegacyPrefsCryptonImpl_Success() {
        val cryptonImpl = LegacyPrefsCryptonImpl(InstrumentationRegistry.getTargetContext(), ALIAS)
        val encrypted = cryptonImpl.encrypt(TEXT_TO_BE_ENCRYPTED)
        val decrypted = cryptonImpl.decrypt(encrypted)
        Assert.assertEquals(TEXT_TO_BE_ENCRYPTED, decrypted)
    }

    @Test(expected = GeneralSecurityException::class)
    fun decrypt_withOtherAlias_afterEncryption_LegacyPrefsCryptonImpl_Fails() {
        val cryptonImpl1 = LegacyPrefsCryptonImpl(InstrumentationRegistry.getTargetContext(), ALIAS)
        val encrypted = cryptonImpl1.encrypt(TEXT_TO_BE_ENCRYPTED)

        val cryptonImpl2 = LegacyPrefsCryptonImpl(InstrumentationRegistry.getTargetContext(), ALIAS + "SOMETHING")
        cryptonImpl2.decrypt(encrypted)
    }

    @Test
    fun encrypt_Api23PrefsCryptonImpl_Success() {
        val cryptonImpl = Api23PrefsCryptonImpl(InstrumentationRegistry.getTargetContext(), ALIAS)
        val encrypted = cryptonImpl.encrypt(TEXT_TO_BE_ENCRYPTED)
        Log.d(LOG_TAG, "Api23: $encrypted")
        Assert.assertNotEquals(TEXT_TO_BE_ENCRYPTED, encrypted)
    }

    @Test
    fun decrypt_afterEncryption_Api23PrefsCryptonImpl_Success() {
        val cryptonImpl = Api23PrefsCryptonImpl(InstrumentationRegistry.getTargetContext(), ALIAS)
        val encrypted = cryptonImpl.encrypt(TEXT_TO_BE_ENCRYPTED)
        val decrypted = cryptonImpl.decrypt(encrypted)
        Assert.assertEquals(TEXT_TO_BE_ENCRYPTED, decrypted)
    }

    @Test(expected = GeneralSecurityException::class)
    fun decrypt_withOtherAlias_afterEncryption_Api23PrefsCryptonImpl_Fails() {
        val cryptonImpl1 = Api23PrefsCryptonImpl(InstrumentationRegistry.getTargetContext(), ALIAS)
        val encrypted = cryptonImpl1.encrypt(TEXT_TO_BE_ENCRYPTED)

        val cryptonImpl2 = Api23PrefsCryptonImpl(InstrumentationRegistry.getTargetContext(), ALIAS + "SOMETHING")
        cryptonImpl2.decrypt(encrypted)
    }

    @Test
    fun encrypt_Api18PrefsCryptonImpl_Success() {
        val cryptonImpl = Api18PrefsCryptonImpl(InstrumentationRegistry.getTargetContext(), ALIAS_RSA)
        val encrypted = cryptonImpl.encrypt(TEXT_TO_BE_ENCRYPTED)
        Log.d(LOG_TAG, "Api18: $encrypted")
        Assert.assertNotEquals(TEXT_TO_BE_ENCRYPTED, encrypted)
    }

    @Test
    fun decrypt_afterEncryption_Api18PrefsCryptonImpl_Success() {
        val cryptonImpl = Api18PrefsCryptonImpl(InstrumentationRegistry.getTargetContext(), ALIAS_RSA)
        val encrypted = cryptonImpl.encrypt(TEXT_TO_BE_ENCRYPTED)
        val decrypted = cryptonImpl.decrypt(encrypted)
        Assert.assertEquals(TEXT_TO_BE_ENCRYPTED, decrypted)
    }

    @Test(expected = GeneralSecurityException::class)
    fun decrypt_withOtherAlias_afterEncryption_Api18PrefsCryptonImpl_Fails() {
        val cryptonImpl1 = Api18PrefsCryptonImpl(InstrumentationRegistry.getTargetContext(), ALIAS_RSA)
        val encrypted = cryptonImpl1.encrypt(TEXT_TO_BE_ENCRYPTED)

        val cryptonImpl2 = Api18PrefsCryptonImpl(InstrumentationRegistry.getTargetContext(), ALIAS_RSA + "SOMETHING")
        cryptonImpl2.decrypt(encrypted)
    }
}