package com.example.tuan4_bt2


import android.net.http.HttpException
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("onboarding1") { OnboardingPage(navController, 0) }
        composable("onboarding2") { OnboardingPage(navController, 1) }
        composable("onboarding3") { OnboardingPage(navController, 2) }
        composable("home") { AppNavigator() }
    }
}

// 🚀 2. Splash Screen
@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("onboarding1") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            modifier = Modifier.size(250.dp),
            contentDescription = "UTH logo"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "UTH SmartTasks",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )
    }
}

// 🚀 3. Onboarding Pages
data class OnboardingData(val image: Int, val title: String, val description: String)

val onboardingList = listOf(
    OnboardingData(R.drawable.banner, "Easy Time Management", "With management based on priority and daily tasks..."),
    OnboardingData(R.drawable.banner2, "Increase Work Effectiveness", "Time management and the determination of more..."),
    OnboardingData(R.drawable.banner3, "Reminder Notification", "The advantage of this application is that it also...")
)

@Composable
fun OnboardingPage(navController: NavHostController, index: Int) {
    val isLastPage = index == onboardingList.lastIndex

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp, 66.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                repeat(onboardingList.size) { i ->
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .padding(2.dp)
                            .background(if (i == index) Color.Blue else Color.LightGray, CircleShape)
                    )
                }
            }
            Text(
                text = "skip",
                color = Color.Blue,
                fontSize = 16.sp,
                modifier = Modifier.clickable { navController.navigate("home") }
            )
        }
        Spacer(modifier = Modifier.height(46.dp))
        Image(
            painter = painterResource(id = onboardingList[index].image),
            modifier = Modifier.size(250.dp),
            contentDescription = "Onboarding Image"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = onboardingList[index].title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = onboardingList[index].description,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(140.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (index > 0) {
                Button(
                    onClick = { navController.navigate("onboarding${index}") },
                    colors = ButtonDefaults.buttonColors(Color.LightGray),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.size(width = 0.dp, height = 50.dp)
                ) {
                    Text(text = "←", color = Color.White, fontSize = 22.sp)
                }
            } else {
                Spacer(modifier = Modifier.width(80.dp))
            }

            Button(
                onClick = {
                    if (isLastPage) {
                        navController.navigate("home") {
                            popUpTo("onboarding1") { inclusive = true }
                        }
                    } else {
                        navController.navigate("onboarding${index + 2}")
                    }
                },
                colors = ButtonDefaults.buttonColors(Color.Blue),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier.size(width = 250.dp, height = 50.dp)
            ) {
                Text(
                    text = if (isLastPage) "Get Started" else "Next",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    SplashScreen(rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun PreviewOnboarding1() {
    OnboardingPage(rememberNavController(), 0)
}

@Preview(showBackground = true)
@Composable
fun PreviewOnboarding2() {
    OnboardingPage(rememberNavController(), 1)
}

@Preview(showBackground = true)
@Composable
fun PreviewOnboarding3() {
    OnboardingPage(rememberNavController(), 2)
}



/// index

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "main") {
        composable("main") { MainScreenTask(navController) }
        composable("detail/{taskTitle}/{taskDesc}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("taskTitle") ?: "No Title"
            val description = backStackEntry.arguments?.getString("taskDesc") ?: "No Description"
            val status = backStackEntry.arguments?.getString("taskStatus") ?: "No Status"
            TaskDetailScreen(navController, title, description)
        }
    }
}

@Composable
fun MainScreenTask(navController: NavController) {
    var tasks by remember { mutableStateOf<List<Task>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        isLoading = true
        try {
            val response = withContext(Dispatchers.IO) { RetrofitInstance.api.getTasks() }
            tasks = response.data
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        bottomBar = { CustomBottomBar() }, // Đảm bảo thanh này luôn hiển thị
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(75.dp)
                )
                Spacer(modifier = Modifier.width(8.dp)) // Giãn cách hình ảnh và chữ

                Column(
                    modifier = Modifier.weight(1f) // Chiếm hết không gian còn lại
                ) {
                    Text(
                        text = "SmartTasks",
                        color = Color.Blue,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Start) // Căn trái
                    )
                    Text(
                        text = "A simple and efficient to-do app",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue,
                        modifier = Modifier.align(Alignment.Start) // Căn trái
                    )
                }

                // Biểu tượng chuông
                IconButton(onClick = { /* TODO: Xử lý khi nhấn chuông */ }) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notification",
                        tint = Color.Yellow // Đổi màu để giống ảnh mẫu
                    )
                }
            }

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    EmptyTaskScreen()
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(tasks) { task ->
                        TaskItem(task, onClick = {
                            navController.navigate("detail/${task.title}/${task.description}")
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyTaskScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.img), contentDescription = "No Tasks" , Modifier.size(150.dp))
            Text("No Tasks Yet!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Stay productive—add something to do", fontSize = 16.sp, color = Color.Gray)
        }
    }
}

@Composable
fun TaskItem(task: Task, onClick: () -> Unit) {
    var isChecked by remember { mutableStateOf(false) } // Lưu trạng thái checkbox
    val currentTime by remember { mutableStateOf(getCurrentTime()) } // Lấy thời gian thực

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(Color(0xFFD0E7FF)) // Màu xanh
    ) {
        Column (modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it } // Cập nhật trạng thái
                )
                Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa Checkbox và văn bản

                Column(modifier = Modifier.weight(1f)) { // Column chiếm toàn bộ chiều ngang còn lại
                    Text(
                        text = task.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = task.description,
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Bên trái: Trạng thái task
                Text(
                    text = "Status: ${task.status}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                // Bên phải: Hiển thị thời gian thực
                Text(
                    text = currentTime,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

// Hàm lấy thời gian thực
fun getCurrentTime(): String {
    val sdf = java.text.SimpleDateFormat("HH:mm:ss    dd/MM/yyyy", java.util.Locale.getDefault())
    return sdf.format(java.util.Date())
}


@Composable
fun TaskDetailScreen(navController: NavController, title: String, description: String) {
    var isChecked by remember { mutableStateOf(false) }
    var isCheckedd by remember { mutableStateOf(false) }
    var isCheckeds by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween // Canh giữa và hai bên
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp)) // Bo viền
                    .background(Color.Blue)
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Text(text = "Detail",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold ,
                modifier = Modifier.padding(top = 15.dp) ,
                color = Color.Blue,
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp)) // Bo viền
                    .background(Color.LightGray)
            ) {
                IconButton(onClick = { /* Navigate to delete */ }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, fontSize = 14.sp, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(8.dp))
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(Color(0xFFFDE0DC))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ){
                IconButton(onClick = {/*Cateroly work*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.category), contentDescription = "Category Work",
                        modifier = Modifier.size(20.dp)
                    )

                }
                Column {
                    Text(text = "Category")
                    Text(text = "Work")
                }
                IconButton(onClick = {/*Cateroly work*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.status), contentDescription = "Category Work",
                        modifier = Modifier.size(20.dp)
                    )

                }
                Column {
                    Text(text = "Status")
                    Text(text = "In Progress")
                }
                IconButton(onClick = {/*Cateroly work*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.priority), contentDescription = "Category Work",
                        modifier = Modifier.size(20.dp)
                    )

                }
                Column {
                    Text(text = "Priority")
                    Text(text = "High")
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Subtasks" , fontSize = 20.sp , modifier = Modifier.padding(5.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(Color(0xFFD0E7FF)) // Màu xanh
        )
        {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { isChecked = it } // Cập nhật trạng thái
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa Checkbox và văn bản
                    Text(
                        text = "This task is related to Fitness. It needs to be completed"
                    )
                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(Color(0xFFD0E7FF)) // Màu xanh
        )
        {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isCheckedd,
                        onCheckedChange = { isCheckedd = it } // Cập nhật trạng thái
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa Checkbox và văn bản
                    Text(
                        text = "This task is related to Fitness. It needs to be completed"
                    )
                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(Color(0xFFD0E7FF)) // Màu xanh
        )
        {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isCheckeds,
                        onCheckedChange = { isCheckeds = it } // Cập nhật trạng thái
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa Checkbox và văn bản
                    Text(
                        text = "This task is related to Fitness. It needs to be completed"
                    )
                }
            }
        }
    }

}
@Composable
fun CustomBottomBar() {
    Box(modifier = Modifier.fillMaxWidth()) {
        BottomAppBar(
            containerColor = Color.White,
            tonalElevation = 4.dp,
            modifier = Modifier
                .clip(RoundedCornerShape(40.dp))
                .align(Alignment.BottomCenter) // Căn BottomAppBar dưới cùng
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Navigate to Home */ }) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "Home", tint = Color.Blue)
                }
                IconButton(onClick = { /* Navigate to Calendar */ }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Calendar", tint = Color.Blue)
                }
                Spacer(modifier = Modifier.width(50.dp)) // Khoảng trống cho FAB
                IconButton(onClick = { /* Navigate to Documents */ }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Documents", tint = Color.Blue)
                }
                IconButton(onClick = { /* Navigate to Settings */ }) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings", tint = Color.Gray)
                }
            }
        }

        FloatingActionButton(
            onClick = { /* Xử lý thêm task */ },
            containerColor = Color.Blue,
            contentColor = Color.White,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(6.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = -25.dp) // Đẩy FAB lên trên
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task")
        }
    }
}