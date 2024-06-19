package com.example.emergetestapplication.emerge.presentation.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.emergetestapplication.R
import com.example.emergetestapplication.emerge.data.model.movies.Movie
import com.example.emergetestapplication.emerge.presentation.view.state.MoviesState
import com.example.emergetestapplication.ui.theme.EmergeTestApplicationTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchMoviesScreen(
    moviesState: MoviesState,
    searchMovies: (String) -> Unit,
    onMovieSelected: (Movie) -> Unit,
    clearMoviesSearch: () -> Unit,
) {
    var query by remember { mutableStateOf(TextFieldValue("")) }
    var searchJob by remember { mutableStateOf<Job?>(null) }
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        onDispose {
            query = TextFieldValue("")
            clearMoviesSearch()
        }
    }

    Column(
        modifier =
            Modifier
                .background(color = colorResource(id = R.color.white))
                .fillMaxSize()
                .padding(16.dp),
    ) {
        OutlinedTextField(
            colors =
                TextFieldDefaults.outlinedTextFieldColors(
                    textColor = colorResource(id = R.color.black),
                    backgroundColor = colorResource(id = R.color.white),
                    focusedBorderColor = colorResource(id = R.color.teal_700),
                    unfocusedBorderColor = colorResource(id = R.color.teal_700),
                    cursorColor = colorResource(id = R.color.teal_700),
                    focusedLabelColor = colorResource(id = R.color.teal_700),
                    unfocusedLabelColor = colorResource(id = R.color.teal_700),
                ),
            shape = RoundedCornerShape(16.dp),
            value = query,
            onValueChange = {
                query = it
                searchJob?.cancel()
                searchJob =
                    coroutineScope.launch {
                        delay(500)
                        if (query.text.isNotEmpty()) {
                            searchMovies(query.text)
                        }
                    }
            },
            label = { Text("Search A Movie to Add to Your List") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            moviesState.isLoading -> {
                CircularProgressIndicator(
                    modifier =
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(24.dp)
                            .align(Alignment.CenterHorizontally),
                    color = colorResource(id = R.color.teal_700),
                )
            }

            moviesState.errorMessage != null -> {
                Text(text = "Error: ${moviesState.errorMessage}")
            }

            else -> {
                LazyColumn {
                    items(moviesState.movies) { movie ->
                        SearchResultItem(movie = movie, onClick = { onMovieSelected(movie) })
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchMoviesScreenPreview() {
    EmergeTestApplicationTheme {
        SearchMoviesScreen(
            moviesState = MoviesState(),
            searchMovies = {},
            onMovieSelected = {},
            clearMoviesSearch = {},
        )
    }
}
