package com.example.emergetestapplication.emerge.presentation.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emergetestapplication.R
import com.example.emergetestapplication.ui.theme.EmergeTestApplicationTheme

@Composable
fun StartUpScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToSignUp: () -> Unit,
) {
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
            text = "Hi, Welcome to Your Movies App",
            color = Color.Black,
            fontSize = 18.sp,
            maxLines = 1
        )

        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = "- Curate multiple Favourite List and Add Top 5 Movies to it.\n" +
                    "- Search your favourite movie and add it to your list.\n" +
                "- Delete the list and start over if you wish.\n" +
                "- Seach and view other's favourite list too.\n\n" +
                "** Don't have an account? Signup now to get started.\n" +
                "** Already have an account? Login now to access your list.",
            color = Color.DarkGray,
            fontSize = 14.sp,
            lineHeight = 25.sp,
        )

        Button(
            onClick = onNavigateToLogin,
            modifier =
            Modifier
                .padding(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 10.dp)
                    .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors =
                ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.teal_700),
                    contentColor = Color.White,
                ),
        ) {
            Text("LOGIN")
        }
        Button(
            onClick = onNavigateToSignUp,
            modifier =
            Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors =
                ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.teal_700),
                    contentColor = Color.White,
            )
        ) {
            Text("SIGNUP")
        }
    }
}

@Preview
@Composable
private fun StartupScreenPreview() {
    EmergeTestApplicationTheme {
        StartUpScreen(onNavigateToLogin = { }, onNavigateToSignUp = { })
    }
}
