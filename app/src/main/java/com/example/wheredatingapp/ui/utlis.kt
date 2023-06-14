package com.example.wheredatingapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RowSeparator(separator : Int){
    Row(modifier = Modifier.padding(vertical = separator.dp)) {}
}

@Composable
fun ColumnSeparator(separator: Int){
    Column(modifier = Modifier.padding(horizontal = separator.dp)) {}
}