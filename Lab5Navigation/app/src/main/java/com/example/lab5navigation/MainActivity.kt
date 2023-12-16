package com.example.lab5navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab5navigation.ScreenRoute.First.route
import com.example.lab5navigation.ui.theme.Lab5NavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab5NavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
    ComposeAllNavigation()
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab5NavigationTheme {
        Greeting("Android")
    }
}


sealed class ScreenRoute (val route: String) {
    object First: ScreenRoute ("first_screen")
    object Second: ScreenRoute ("second_screen")
}



//==============================================================
@Composable
fun ComposeAllNavigation() {
    val navController = rememberNavController()

    Column(modifier = Modifier
        .fillMaxSize()
        .border(
            width = 1.dp,
            color = Color.Black,
            shape = RoundedCornerShape(20.dp)
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NavHost(
            navController = navController,
            startDestination = ScreenRoute.First.route
        ) {
            composable(ScreenRoute.First.route) {
                MyPage1(navController)
            }
            composable(ScreenRoute.Second.route) {
                MyPage2(navController)
            }
        }
    }
}

@Composable
fun IdNameAgeContent(
    id: String,
    onIDChange: (String) -> Unit,
    name: String,
    onNameChange: (String) -> Unit,
    age: String,
    onAgeChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 5.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .width(400.dp)
                .padding(bottom = 16.dp),
            value = id,
            onValueChange = onIDChange,
            label = { Text(text = "Student ID") }
        )

        OutlinedTextField(
            modifier = Modifier
                .width(400.dp)
                .padding(bottom = 16.dp),
            value = name,
            onValueChange = onNameChange,
            label = { Text(text = "Name") }
        )

        OutlinedTextField(
            modifier = Modifier
                .width(400.dp)
                .padding(bottom = 16.dp),
            value = age,
            onValueChange = onAgeChange,
            label = { Text(text = "Age") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )

        )
    }
}

@Composable
fun CheckboxGroup(
    list: List<String>,
    onSelectionChange: (List<String>) -> Unit
) {
    val selectedItems = remember { mutableStateListOf<String>() }

    Row {
        list.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedItems.contains(item),
                    onCheckedChange = {
                        if (it) {
                            selectedItems.add(item)
                        } else {
                            selectedItems.remove(item)
                        }
                        onSelectionChange(selectedItems.toList())
                    }
                )
                Text(text = item, fontSize = 15.sp)
            }
        }
    }
}

@Composable
fun MyPage1(navHostController: NavHostController) {
    var id by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("") }

    val  context = LocalContext.current

    var selectedItems by rememberSaveable { mutableStateOf(mutableListOf<String>()) }

    val hobbyList = listOf("reading", "drawing", "cocking")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .background(color = Color.LightGray, shape = RoundedCornerShape(20.dp))
                .padding(16.dp),
            text = "Page 1",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
        )

        Text(
            modifier = Modifier.padding(5.dp),
            text = "Enter student information",
            fontSize = 20.sp
        )

        IdNameAgeContent(
            id = id,
            onIDChange = { id = it },
            name = name,
            onNameChange = { name = it },
            age = age,
            onAgeChange = { age = it }
        )

        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp),
            text = "Choose your hobby:"
        )

        CheckboxGroup(
            list = hobbyList,
            onSelectionChange = { newSelectedItems ->
                selectedItems.clear()
                selectedItems.addAll(newSelectedItems)
                Log.d("CheckboxGroup", "Selected Items: $selectedItems")
            }
        )

        Spacer(Modifier.height(height = 8.dp))

        Button(
            onClick = {
                val studentData = Student(id, name, age.toInt(), selectedItems)
                navHostController.currentBackStackEntry?.savedStateHandle?.set("data", studentData)
                navHostController.navigate(ScreenRoute.Second.route)
            }
        ) {
            Text(text = "Send Information")
        }

        Button(
            onClick = {
                val packageName = "com.google.android.youtube"
                startActivitySafe(context, packageName)
            }
        ) {
            Text(text = "Open Youtube")
        }



        Button(
            onClick = {
                val packageName = "com.zhiliaoapp.musically"
                startActivitySafe(context, packageName = packageName)
            }
        ) {
            Text(text = "Open TikTok")
        }
    }
}


fun startActivitySafe(context: Context?, packageName: String) {
    if (context == null || packageName.isBlank()) {
        Log.e("startActivitySafe", "Context or package name is null or blank!")
        return
    }

    try {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.`package` = packageName
        context.startActivity(intent)
    } catch (e: Exception) {
        // Handle any exceptions
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
        ContextCompat.startActivity(context, intent, null)
        Log.e("startActivitySafe", "Error starting activity", e)
    }
}

@Composable
fun MyPage2(navHostController: NavHostController) {
    val data = navHostController.previousBackStackEntry?.savedStateHandle?.get<Student>("data")
        ?: Student("", "", 0, listOf())

    var lastIndex = data.hobby.size - 1
    var hobbySelect = ""

    data.hobby.forEachIndexed { index, item ->
        hobbySelect += if (index == lastIndex) item else "$item, "
    }

    IconButton(
        modifier = Modifier.size(100.dp),
        onClick = { navHostController.navigateUp() }
    ) {
        Icon(Icons.Default.ArrowBack, contentDescription = "", tint = Color.Magenta)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            text = "Page 2",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )

        Text(
            modifier = Modifier.padding(16.dp),
            text = "Student ID: ${data.id}\n\nStudent Name: ${data.name}\n\nHobby: $hobbySelect\n"+
                    "\n\nStudent Age: ${data.age}\n",
            fontSize = 20.sp
        )

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                navHostController.navigate(route = "first") {
                    this.launchSingleTop = true
                    popUpTo(route = "first") {
                        this.inclusive = false
                    }
                }
            }
        ) {
            Text(text = "Go to Page1")
        }
    }
}



