package com.egco428.a23264;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class CommentDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,MySQLiteHelper.COLUMN_USERNAME,MySQLiteHelper.COLUMN_PASSWORD,MySQLiteHelper.COLUMN_LONGITUDE,MySQLiteHelper.COLUMN_LATITUDE};

    public CommentDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public String findpass(String username){

        Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_COMMENT
                + " WHERE " + MySQLiteHelper.COLUMN_USERNAME + "=" + "'"+username+"'", null);
        cursor.moveToFirst();

        if(cursor.getCount() <= 0){
            cursor.close();
            return "-";
        }
        else {
            Comment findpassword = cursorToComment(cursor);
            String password = findpassword.getPassword();
            cursor.close();
            return password;
        }

    }
    public void close(){
        dbHelper.close();
    }
    public Comment createComment(String user,String pass,String longi,String lati){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_USERNAME,user);
        values.put(MySQLiteHelper.COLUMN_PASSWORD,pass);
        values.put(MySQLiteHelper.COLUMN_LONGITUDE,longi);
        values.put(MySQLiteHelper.COLUMN_LATITUDE,lati);
        long insertID = database.insert(MySQLiteHelper.TABLE_COMMENT,null,values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENT,allColumns,MySQLiteHelper.COLUMN_ID+" = "+insertID,null,null,null,null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }
    public void deleteComment(Comment comment){
        long id = comment.getId();
        System.out.println("Comment deleted with id:" +id);
        database.delete(MySQLiteHelper.TABLE_COMMENT,MySQLiteHelper.COLUMN_ID+" = "+id,null);
    }
    public List<Comment> getAllComments(){
        List<Comment> comments = new ArrayList<>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENT,allColumns,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }
    public Comment cursorToComment(Cursor cursor){
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setUsername(cursor.getString(1));
        comment.setPassword(cursor.getString(2));
        comment.setLongitude(cursor.getString(3));
        comment.setLatitude(cursor.getString(4));

        return comment;
    }
}
