package com.example.a20240804

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() { //앱화면을 정의

    // lateinit var  변수를 초기화하는 데 나중에 할수있도록
    // 설정
    private lateinit var myDBHelper: MyDBHelper
    private lateinit var edtName: EditText
    private lateinit var edtNumber: EditText
    private lateinit var edtResultName: EditText
    private lateinit var edtResultNumber : EditText
    private lateinit var btnInit : Button
    private lateinit var btnInsert : Button
    private lateinit var btnSelect : Button
    private lateinit var sqlDB : SQLiteDatabase //데이터베이스 객체 저장

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //1. 뷰 초기화
        edtName = findViewById(R.id.edtName)
        edtNumber = findViewById(R.id.edtNumber)
        edtResultName = findViewById(R.id.edtResultName)
        edtResultNumber = findViewById(R.id.edtResultNumber)
        btnInit = findViewById(R.id.btnInit)
        btnInsert = findViewById(R.id.btnInsert)
        btnSelect = findViewById(R.id.btnSelect)

        //데이터베이스 헬퍼 초기화
        myDBHelper  = MyDBHelper(this)


        // 초기화버튼에 이벤트 작성하기
        btnInit.setOnClickListener{
        }

        // 입력버튼 이벤트 달기
        btnInsert.setOnClickListener{
            try {
                val name = edtName.text.toString()
                val number = edtNumber.text.toString()
                             .toInt()


                // 추가(삽입,쓰기) 가능한 데이터베이스 객체 열기!
                sqlDB = myDBHelper.writableDatabase
                // sql실행
                sqlDB.execSQL(
                    "insert into groupTBL values( " +
                            "$name,$number);")
                // sql닫기
                sqlDB.close()
                Toast.makeText(
                    applicationContext, "입력됨",
                    Toast.LENGTH_SHORT
                ).show()
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
        // 조회버튼 이벤트 달기
        btnSelect.setOnClickListener {

        }




        // sqlite3
        //   - 데이터베이스에서 가장 간단하게 사용해서
        //     데이터를 저장하는 역할
        //     스마트폰 안에 내용을 저장하는 방식!
    }

    // sqlite를 사용하기 위해서 SQLiteOpenHelper상속
    // inner 내부 클래스 멤버들은 외부 클래스 멤버에 접근
    // 할 수 있다.
    // inner 클래스
    //   class안에 class 들어간 모습!
    // inner 생략하고 그냥 일반 중첩클래스로 그냥 사용해도
    //  무방하다 왜? 내부클래스로 선언되는 db들이 외부클래스에
    //  멤버를 하나도 안 사용한다.

    //  위에 데이터를 확인하고 싶을 경우에 경로
    // view -> tool windows -> device explorer
    //  / -> data 폴더 안에 data -> 패키지명 ->
    //   databases / 데이터베이스.db

    // files도 같이 들어있는 걸 볼 수 있다.


    class MyDBHelper(context: Context):
            SQLiteOpenHelper(context,"groupDB",null,1){
        override fun onCreate(db: SQLiteDatabase) {
            //데이터베이스가 처음 생성될 때 호출
            db.execSQL("create table groupTBL ( " +
                    "gName  char(30) primary key, " +
                    "gNumber integer);")
        }
        // 데이터베이스가 업그레이드 될떄 호출된다.
        override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
            db.execSQL("DROP TABLE IF EXISTS groupTBL")
            onCreate(db)
        }
    }
}

