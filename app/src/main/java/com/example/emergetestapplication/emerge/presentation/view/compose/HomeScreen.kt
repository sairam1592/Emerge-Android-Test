package com.example.emergetestapplication.emerge.presentation.view.compose

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.emergetestapplication.R
import com.example.emergetestapplication.emerge.presentation.view.state.AuthState
import com.example.emergetestapplication.emerge.presentation.view.state.MoviesState
import com.example.emergetestapplication.ui.theme.EmergeTestApplicationTheme

@Composable
fun HomeScreen(
    authState: AuthState,
    moviesState: MoviesState,
    onLogout: () -> Unit,
    onLogoutSuccess: () -> Unit,
    onCreateListClick: () -> Unit,
    onSearchUsersClick: () -> Unit,
) {
    val context = LocalContext.current

    Box(
        modifier =
            Modifier
                .fillMaxSize()
    ) {
        Column(
            modifier =
            Modifier
                .background(color = colorResource(id = R.color.white))
                .fillMaxSize()
                .padding(16.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Welcome!", style = MaterialTheme.typography.h6)

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Button(
                    modifier =
                        Modifier
                            .wrapContentSize(),
                    shape = RoundedCornerShape(16.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.teal_700),
                            contentColor = Color.White,
                        ),
                    onClick = { onCreateListClick() },
                ) {
                    Text(text = "Create Your List")
                }

                Button(
                    modifier =
                        Modifier
                            .padding(start = 16.dp)
                            .wrapContentSize(),
                    shape = RoundedCornerShape(16.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.teal_700),
                            contentColor = Color.White,
                        ),
                    onClick = { onSearchUsersClick() },
                ) {
                    Text(text = "Search Users")
                }
            }
        }

        Button(
            modifier =
                Modifier
                    .padding(20.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(16.dp),
            colors =
                ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.teal_700),
                    contentColor = Color.White,
                ),
            onClick = {
                onLogout()
                Toast.makeText(context, "Logout Successful", Toast.LENGTH_SHORT).show()
            },
        ) {
            Text("Logout", color = Color.White)
        }

        if (!authState.isAuthenticated) {
            onLogoutSuccess()
        }

        if (authState.errorMessage != null) {
            Toast.makeText(context, authState.errorMessage, Toast.LENGTH_SHORT).show()
            Text(text = authState.errorMessage, color = Color.Red)
        }

        Column(
            modifier =
                Modifier
                    .padding(16.dp)
                    .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.create_list_empty_img),
                contentDescription = "Create List Empty Image",
                modifier =
                    Modifier
                        .size(130.dp),
            )

            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = "Start creating your list now!",
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    EmergeTestApplicationTheme {
        HomeScreen(
            authState = AuthState(),
            moviesState = MoviesState(),
            onLogout = {},
            onLogoutSuccess = {},
            onCreateListClick = {},
            onSearchUsersClick = {},
        )
    }
}