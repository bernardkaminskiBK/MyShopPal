package com.android10_kotlin.myshoppal.firestore

import android.app.Activity
import android.net.Uri
import android.util.Log
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.activities.LoginActivity
import com.android10_kotlin.myshoppal.activities.RegisterActivity
import com.android10_kotlin.myshoppal.activities.UserProfileActivity
import com.android10_kotlin.myshoppal.models.User
import com.android10_kotlin.myshoppal.utils.Constants
import com.android10_kotlin.myshoppal.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.HashMap

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User) {
        mFireStore.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    activity.getString(R.string.error_message),
                    e
                )
            }
    }

    private fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }

    fun getUserDetails(activity: Activity) {
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.i(activity.javaClass.simpleName, document.toString())

                val user = document.toObject(User::class.java)!!
                Utils.saveUserNameSharedPreferences(activity, user)

                when (activity) {
                    is LoginActivity -> {
                        activity.userLoggedInSuccess(user)
                    }
                }
            }
            .addOnFailureListener { e ->
                when (activity) {
                    is LoginActivity -> {
                        activity.hideProgressDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting user details.",
                    e
                )
            }
    }

    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>) {
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .update(userHashMap)
            .addOnSuccessListener {
                when (activity) {
                    is UserProfileActivity -> {
                        activity.userProfileUpdateSuccess()
                    }
                }
            }.addOnFailureListener { e ->
                when (activity) {
                    is UserProfileActivity -> {
                        activity.hideProgressDialog()
                    }
                }
                Log.e(
                    activity.javaClass.simpleName,
                    activity.getString(R.string.error_updating_message),
                    e
                )
            }
    }

    fun uploadImageToCloudStorage(activity: Activity, imageFileUri: Uri?) {
        val imageExtension = Utils.getImageExtension(activity, imageFileUri)
        val sRef: StorageReference =
            FirebaseStorage.getInstance()
                .reference.child(Constants.USER_PROFILE_IMAGE + System.currentTimeMillis() + "." + imageExtension)

        sRef.putFile(imageFileUri!!).addOnSuccessListener { taskSnapshot ->
            Log.e(
                "Firebase Image URL",
                taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
            )

            taskSnapshot.metadata!!.reference!!.downloadUrl
                .addOnSuccessListener { uri ->
                    Log.e("Downloadable image url", uri.toString())
                    when (activity) {
                        is UserProfileActivity -> {
                            activity.imageUploadSuccess(uri.toString())
                        }
                    }
                }
        }.addOnFailureListener {
            when (activity) {
                is UserProfileActivity -> {
                    activity.hideProgressDialog()
                }
            }
            Log.e(activity.javaClass.simpleName, it.message, it)
        }

    }
}
