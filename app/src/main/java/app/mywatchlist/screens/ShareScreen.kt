package app.mywatchlist.screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import app.mywatchlist.R
import app.mywatchlist.utils.hasContactPermission
import app.mywatchlist.utils.hasSMSPermission
import app.mywatchlist.utils.requestContactPermission
import app.mywatchlist.utils.requestSMSPermission
import kotlinx.coroutines.launch

@Composable
fun ShareScreen (
    navController: NavController,
    watchableTitle: String?
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 10.dp, 20.dp, 70.dp),
    ) {
        Column() {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledTonalIconButton(
                    onClick = { navController.popBackStack() },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.onBackground
                    )
                ) {
                    Icon(
                        Icons.Outlined.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
                Text(
                    text = stringResource(R.string.share),
                    color = MaterialTheme.colors.onBackground,
                    fontSize = MaterialTheme.typography.h3.fontSize,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier.padding(0.dp, 20.dp, 0.dp, 0.dp)
            ) {
                Text(
                    text = watchableTitle ?: "WATCHABLE_TITLE",
                    color = MaterialTheme.colors.onBackground,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold,
                )
            }
            Row(
                modifier = Modifier
                    .padding(0.dp, 90.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                contactPicker(watchableTitle)
            }
        }
    }
}


@SuppressLint("Range")
@Composable
fun contactPicker(
    watchableTitle: String?,
) {

    var contactName by remember { mutableStateOf("") }
    var contactNumber by remember {  mutableStateOf("")}
    val message = stringResource(R.string.share_sms, contactName, watchableTitle.toString())
    val activity = LocalContext.current as Activity

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickContact(),
        onResult = {
            val contentResolver: ContentResolver = context.contentResolver
            var name = ""
            var number = ""
            val cursor: Cursor? = contentResolver.query(it!!, null, null, null, null)
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    Log.d("Name", name)
                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val phones: Cursor? = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null
                    )
                    if (phones != null) {
                        while (phones.moveToNext()) {
                            number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            Log.d("Number", number)
                        }
                        phones.close()
                    }
                }
            }
            contactName = name
            contactNumber = number
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(
            text = stringResource(R.string.share_with),
            color = MaterialTheme.colors.onBackground,
            fontSize = MaterialTheme.typography.h5.fontSize,
            fontWeight = FontWeight.Bold
        )

        Button(
            modifier = Modifier.padding(0.dp, 20.dp),
            onClick = {
                if (hasContactPermission(context)) {
                    launcher.launch()
                } else {
                    requestContactPermission(context, activity)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.background
            )
        )
        {
            Icon(Icons.Default.Person, stringResource(R.string.select_Contact))
            Text(
                text = stringResource(R.string.select_Contact),
            )
        }

        Text(
            text = contactName,
            color = MaterialTheme.colors.onBackground,
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = contactNumber,
            color = MaterialTheme.colors.onBackground,
        )

        if (!contactName.isNullOrEmpty()){
            Button(
                modifier = Modifier.padding(0.dp, 20.dp),
                onClick = {
                    if (hasSMSPermission(context)) {
                        try {
                            val smsManager: SmsManager = SmsManager.getDefault()
                            smsManager.sendTextMessage(contactNumber, null, message, null, null)
                            scope.launch {
                                snackbarHostState.showSnackbar(message = "Message sent")
                            }
                        } catch (e: Exception) {
                            scope.launch {
                                snackbarHostState.showSnackbar(message = "Couldn't send message")
                            }
                        }
                    } else {
                        requestSMSPermission(context, activity)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.background
                )
            )
            {
                Icon(Icons.Default.Send, stringResource(R.string.share))
                Text(
                    text = stringResource(R.string.share),
                )
            }
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { SnackbarData -> Snackbar(
                    snackbarData = SnackbarData,
                    containerColor = MaterialTheme.colors.onPrimary,
                    contentColor = MaterialTheme.colors.onBackground
                ) }
            )
        }
    }
}