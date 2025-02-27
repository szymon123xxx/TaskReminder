package com.example.taskreminder.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { isBottomSheetVisible = true }) {
                Icon(
                    Icons.Filled.Add,
                    "Floating action button."
                )
            }
        }
    ) {
        if (isBottomSheetVisible) {
            BottomSheet(dismissSheet = { isBottomSheetVisible = false })
        }

        Box(
            modifier = modifier
                .padding(it)
                .padding(horizontal = 16.dp)
                .padding(top = 50.dp)
        ) {
            Column {
                DropDownMenu()
                EventContainer()
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheet(
    modifier: Modifier = Modifier,
    dismissSheet: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxHeight(),
        onDismissRequest = dismissSheet,
        sheetState = sheetState,
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            ),
            value = "",
            maxLines = 5,
            onValueChange = { },
            placeholder = {
                Text(
                    modifier = Modifier,
                    text = "Add title",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            },
        )

        PickerTime()
        PickerDate()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PickerTime() {
    //the state is picked and so on should be extracted outside
    val currentTime = Calendar.getInstance()
    var isTimePickerVisible by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime[Calendar.HOUR_OF_DAY],
        initialMinute = currentTime[Calendar.MINUTE],
        is24Hour = true,
    )

    TextButton(
        shape = RoundedCornerShape(16.dp),
        onClick = { isTimePickerVisible = true }
    ) {
        Text("${timePickerState.hour}:${timePickerState.minute}")
    }

    if (isTimePickerVisible) {
        Dialog(
            onDismissRequest = { isTimePickerVisible = false }
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Blue)
                    .padding(16.dp)
            ) {
                TimePicker(state = timePickerState)
                Button(onClick = { isTimePickerVisible = false }) {
                    Text("Dismiss picker")
                }
                Button(onClick = { }) {
                    Text("Confirm selection")
                }
            }
        }
    }
}

fun convertMillisToLocalDate(millis: Long): ZonedDateTime {
    // Interpret the milliseconds as the start of the day in UTC, then convert to Los Angeles time
    val utcDateAtStartOfDay = Instant
        .ofEpochMilli(millis)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()
    println("UTC Date at Start of Day: $utcDateAtStartOfDay") // Debugging UTC date

    // Convert to the same instant in Local time zone
    val localDate = utcDateAtStartOfDay.atStartOfDay(ZoneId.systemDefault())
    println("Local Date: $localDate") // Debugging local date

    return localDate

}

fun dateToString(date: ZonedDateTime): String {
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy", Locale.getDefault())
    return dateFormatter.format(date)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PickerDate() {
    var isDatePickerVisible by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    TextButton(
        shape = RoundedCornerShape(16.dp),
        onClick = { isDatePickerVisible = true }
    ) {
        Text(
            "${
                datePickerState.selectedDateMillis?.let {
                    convertMillisToLocalDate(it)
                }?.let {
                    dateToString(it)
                } ?: "nie ma daty "
            }"
        )
    }
    if (isDatePickerVisible) {
        DatePickerDialog(
            onDismissRequest = { isDatePickerVisible = false },
            confirmButton = {},
            dismissButton = {},
        ) {
            DatePicker(datePickerState)
        }
    }
}

@Composable
private fun EventContainer(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropDownMenu(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Button(
            shape = MenuDefaults.shape,
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(containerColor = MenuDefaults.containerColor),
        ) {
            Text(
                text = "Past",
                color = MenuDefaults.itemColors().textColor
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                modifier = Modifier,
                text = { Text("Upcoming") },
                onClick = { /* Handle edit! */ },
                leadingIcon = { Icon(Icons.Outlined.Edit, contentDescription = null) }
            )
        }
    }
}