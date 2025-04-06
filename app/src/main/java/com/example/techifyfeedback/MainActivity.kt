package com.example.techifyfeedback

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.techifyfeedback.ui.theme.TechifyFeedbackTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TechifyFeedbackTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = { TopBar() },
                    bottomBar = { BottomNavigationBar(navController) }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "dashboard",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable("dashboard") { DashboardScreen(navController) }
                        composable("assignments") { AssignmentsScreen() }
                        composable("students") { StudentsScreen() }
                        composable("analytics") { AnalyticsScreen() }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavHostController) {
    val backgroundColor = Color(0xFFF5F5F5)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {

            Text(
                "Dashboard",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE)
            )
            Text(
                "Welcome back, Students!",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Metrics Section
            DashboardMetricsSection()

            // Assignments Section
            RecentAssignmentsSection()

            // Insights Section
            InsightsCard(modifier = Modifier.padding(top = 16.dp))
        }
    }



@Composable
fun DashboardMetricsSection() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
            MetricsCard("Pending Assignments", "12", R.drawable.assignment, Color(0xFFFFA726))
            MetricsCard("Total Students", "87", R.drawable.user, Color(0xFF42A5F5))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
            MetricsCard("Feedback Given", "156", R.drawable.analytics, Color(0xFF66BB6A))
            MetricsCard("Avg. Time Saved", "4.2h", R.drawable.stopwatch, Color(0xFFAB47BC))
        }
    }
}


@Composable
fun RecentAssignmentsSection() {
    Text(
        "Recent Assignments",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF6200EE),
        modifier = Modifier.padding(top = 16.dp)
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(top = 8.dp)
    ) {
        items(getSampleAssignments()) { assignment ->
            AssignmentCard(
                title = assignment.title,
                grade = assignment.grade,
                submissions = assignment.submissions,
                progress = assignment.progress
            )
        }
    }
}
data class Assignment(
    val title: String,
    val grade: String,
    val submissions: String,
    val progress: Float
)

fun getSampleAssignments(): List<Assignment> {
    return listOf(
        Assignment("Math Homework", "A+", "30/40", 0.75f),
        Assignment("Physics Lab Report", "B", "25/30", 0.85f),
        Assignment("History Essay", "A", "18/20", 0.90f)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    val primaryColor = Color(0xFF6200EE)

    CenterAlignedTopAppBar(
        title = { Text("Techify Feedback", color = primaryColor, fontWeight = FontWeight.Bold) },
        actions = {
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = primaryColor)
            }
            Box(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFBB86FC))
                    .clickable { /* TODO */ },
                contentAlignment = Alignment.Center
            ) {
                Text("JD", color = primaryColor, fontWeight = FontWeight.Bold)
            }
        }
    )
}
data class BottomNavItem(
    val title: String,
    val icon: Painter,
    val route: String
)


@Composable
fun AssignmentsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Assignments Screen", fontSize = 20.sp)
    }
}

@Composable
fun StudentsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Students Screen", fontSize = 20.sp)
    }
}

@Composable
fun AnalyticsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Analytics Screen", fontSize = 20.sp)
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("Dashboard", painterResource(R.drawable.dashboard), "dashboard"),
        BottomNavItem("Assignments", painterResource(R.drawable.assignment), "assignments"),
        BottomNavItem("Students", painterResource(R.drawable.user), "students"),
        BottomNavItem("Analytics", painterResource(R.drawable.analytics), "analytics")
    )

    NavigationBar(
        containerColor = Color.White
    ) {
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStack?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = item.icon,
                        contentDescription = item.title,
                        modifier = Modifier.size(20.dp) // Set small icon size
                    )
                },
                label = {
                    Text(text = item.title, fontSize = 12.sp) // Small label
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


@Composable
fun MetricsCard(title: String, value: String, icon: Int, color: Color) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(120.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(painter = painterResource(icon), contentDescription = title, tint = Color.White, modifier = Modifier.size(24.dp))
            Column {
                Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text(title, fontSize = 14.sp, color = Color.White.copy(alpha = 0.9f))
            }
        }
    }
}

@Composable
fun AssignmentCard(title: String, grade: String, submissions: String, progress: Float) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF6200EE))
                Text(grade, color = Color.Gray, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                color = Color(0xFF6200EE),
                trackColor = Color(0xFFBB86FC)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Submissions: $submissions", fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Composable
fun InsightsCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Insights Coming Soon!", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6200EE))
        }
    }
}
