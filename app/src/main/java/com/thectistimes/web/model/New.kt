package com.thectistimes.web.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thectistimes.web.util.Constants

@Entity(tableName = Constants.TABLENAME)
class New (
    @PrimaryKey
    var id:Int,
    @ColumnInfo(name = "title")
    var title:String,
    @ColumnInfo(name = "content")
    var content: String,
    @ColumnInfo(name = "author")
    var author: String,
    @ColumnInfo(name= "date")
    var date: String,
    @ColumnInfo(name= "imageUrl")
    var imageUrl: String) : Parcelable
{
    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString().toString(), parcel.readString().toString(), parcel.readString().toString(), parcel.readString().toString(), parcel.readString().toString())
    constructor(title: String, content: String, author:String, date: String, imageUrl: String): this(0,title = title,content = content, author = author, date = date, imageUrl = imageUrl)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(author)
        parcel.writeString(date)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "New(id=$id, title='$title')"
    }

    companion object CREATOR : Parcelable.Creator<New> {
        override fun createFromParcel(parcel: Parcel): New {
            return New(parcel)
        }

        override fun newArray(size: Int): Array<New?> {
            return arrayOfNulls(size)
        }
    }
}