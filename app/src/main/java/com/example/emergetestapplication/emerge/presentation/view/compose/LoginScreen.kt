package com.example.emergetestapplication.emerge.presentation.view.compose

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emergetestapplication.R
import com.example.emergetestapplication.emerge.common.AppConstants
import com.example.emergetestapplication.emerge.presentation.view.state.AuthState
import com.example.emergetestapplication.ui.theme.EmergeTestApplicationTheme

@Composable
fun LoginScreen(
    authState: AuthState,
    errorEvent: Int,
    onLogin: (String, String) -> Unit,
    onLoginSuccess: () -> Unit,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier =
        Modifier
            .background(color = colorResource(id = R.color.white))
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = R.string.login_screen_title),
            color = Color.Black,
            fontSize = 20.sp,
            maxLines = 1,
        )

        TextField(
            modifier = Modifier.padding(top = 30.dp),
            value = username,
            onValueChange = { username = it },
            label = { Text(stringResource(id = R.string.hint_username)) },
            shape = RoundedCornerShape(4.dp),
            colors =
                TextFieldDefaults.textFieldColors(
                    textColor = colorResource(id = R.color.black),
                    focusedIndicatorColor = colorResource(id = R.color.teal_700),
                    cursorColor = colorResource(id = R.color.teal_700),
                    focusedLabelColor = colorResource(id = R.color.teal_700),
                    unfocusedLabelColor = colorResource(id = R.color.teal_700),
                ),
        )
        TextField(
            modifier = Modifier.padding(top = 25.dp),
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.hint_password)) },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(4.dp),
            colors =
                TextFieldDefaults.textFieldColors(
                    textColor = colorResource(id = R.color.black),
                    focusedIndicatorColor = colorResource(id = R.color.teal_700),
                    cursorColor = colorResource(id = R.color.teal_700),
                    focusedLabelColor = colorResource(id = R.color.teal_700),
                    unfocusedLabelColor = colorResource(id = R.color.teal_700),
                ),
        )
        Button(
            onClick = { onLogin(username, password) },
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors =
                ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.teal_700),
                    contentColor = Color.White,
                ),
            enabled = username.isNotBlank() && password.isNotBlank(),
        ) {
            Text(stringResource(id = R.string.login))
        }
        if (authState.isLoading) {
            CircularProgressIndicator(color = colorResource(id = R.color.teal_700))
        }
        if (authState.isAuthenticated) {
            Toast.makeText(context, AppConstants.TOAST_LOGIN_SUCCESS, Toast.LENGTH_SHORT).show()
            onLoginSuccess()
        }
    }

    LaunchedEffect(errorEvent) {
        authState.errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    EmergeTestApplicationTheme {
        LoginScreen(authState = AuthState(), onLogin = { _, _ -> }, onLoginSuccess = {}, errorEvent = 0)
    }
}
