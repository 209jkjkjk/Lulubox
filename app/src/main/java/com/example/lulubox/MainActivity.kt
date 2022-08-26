package com.example.lulubox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hexStringEditText = findViewById<EditText>(R.id.hexString)
        val charsetStringEditText = findViewById<EditText>(R.id.charsetString)
        val resultStringTextView = findViewById<TextView>(R.id.resultString)
        val converBtn = findViewById<Button>(R.id.convertbtn)
        val clearBtn = findViewById<Button>(R.id.clearBtn)

        converBtn.setOnClickListener {
            val hexString = hexStringEditText.text.toString()
            val charsetString = charsetStringEditText.text.toString()
            val result = hexStringtoString(hexString, charsetString)
            resultStringTextView.text = result
        }

        clearBtn.setOnClickListener {
            hexStringEditText.setText("")
        }
    }

    fun hexStringtoString(_hexString: String, objCharset: String): String {
        // 测试字符集是否存在
        val cset = charset(objCharset)

        // 拷贝参数字符串
        var hexString = _hexString
        // 格式化字符串
        hexString = hexString.replace("\\s".toRegex(),"")

        // 建立byte数组
        val temp = ByteArray(hexString.length /2)
        var tempIndex = 0 // 优化运算
        for (index in 0..hexString.length-1 step 2) {
            var high = chartoHex(hexString[index]) shl 4
            var low = chartoHex(hexString[index + 1])
            temp[tempIndex++] = (high or low).toByte()
        }

        return String(temp, cset)
    }

    fun chartoHex(char: Char): Int {
        val temp = "0123456789ABCDEF".indexOf(char)
        return if (temp < 0) "abcdef".indexOf(char) + 10 else temp
    }
}