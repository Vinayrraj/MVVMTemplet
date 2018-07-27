package com.vinay.library.data.user;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Records")
public class Record {

    @PrimaryKey
    @ColumnInfo(name = "Id")
    @SerializedName(value = "Id", alternate = "idPedido")
    private int id;

    @ColumnInfo(name = "data")
    @SerializedName("data")
    private String data;


    @Override
    public int hashCode() {
        return Objects.hashCode(1);
    }

    @Override
    public String toString() {
        return "Task with title ";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
