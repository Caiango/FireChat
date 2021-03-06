package com.example.firechat.util

import com.example.firechat.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.NullPointerException

object FirestoreUtil {
    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document(
            "users/${
                FirebaseAuth.getInstance().uid
                    ?: throw NullPointerException("UID is null.")
            }"
        )

    fun initCurrentUserIfFirstTime(onComplete: () -> Unit) {
        currentUserDocRef.get().addOnSuccessListener { documentSnaphot ->
            if (!documentSnaphot.exists()) {
                val newUser =
                    User(FirebaseAuth.getInstance().currentUser?.displayName ?: "", "", null)
                currentUserDocRef.set(newUser).addOnSuccessListener { onComplete() }
            } else {
                onComplete()
            }
        }
    }

    fun updateCurrentUser(name: String = "", bio: String = "", profPicPath: String? = null) {
        val userFieldMap = mutableMapOf<String, Any>()
        if (name.isNotBlank()) userFieldMap["name"] = name
        if (bio.isNotBlank()) userFieldMap["bio"] = bio
        if (profPicPath != null) userFieldMap["profPicPath"] = profPicPath
        currentUserDocRef.update(userFieldMap)
    }

    fun getCurrentUser(onComplete: (User) -> Unit) {
        currentUserDocRef.get().addOnSuccessListener {
            onComplete(it.toObject(User::class.java)!!)
        }
    }
}