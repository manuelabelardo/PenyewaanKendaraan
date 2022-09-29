package com.test.penyewaankendaraan.room

@Entity
data class Note {
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val note: String
}