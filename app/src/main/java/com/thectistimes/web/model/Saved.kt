package com.thectistimes.web.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thectistimes.web.util.Constants

@Entity(tableName = Constants.TABLENAMESAVED)
class Saved(
    @PrimaryKey
    var id:Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Saved(id=$id)"
    }


    companion object CREATOR : Parcelable.Creator<Saved> {
        override fun createFromParcel(parcel: Parcel): Saved {
            return Saved(parcel)
        }

        override fun newArray(size: Int): Array<Saved?> {
            return arrayOfNulls(size)
        }
    }
}