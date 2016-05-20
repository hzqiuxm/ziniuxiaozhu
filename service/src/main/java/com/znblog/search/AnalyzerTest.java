package com.znblog.search;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by hzqiuxm on 2016/3/28 0028.
 * 标准分词器对中文和英文分词测试
 * 中文分词器对中文和英文分词测试
 */
public class AnalyzerTest {

    public static void main(String[] args) {

        String str = "Even the smallest person can change the course of the future.";
        String str1 = "即使是小人物，也可以改变未来";
        AnalyzerTest.yingwenAnalyzer(str);
        AnalyzerTest.yingwenAnalyzer(str1);
    }

    public static void yingwenAnalyzer(String str){
        StringReader reader = new StringReader(str);
        StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
        TokenStream tokenStream = standardAnalyzer.tokenStream("field",reader);
        try {
            CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while(tokenStream.incrementToken()){
                System.out.println(charTermAttribute.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
