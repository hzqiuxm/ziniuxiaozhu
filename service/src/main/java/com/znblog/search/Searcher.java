package com.znblog.search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths;

/**
 * Created by hzqiuxm on 2016/3/21 0021.
 */
public class Searcher {
    public static void main(String[] args) throws Exception {

        final String indexDir = "D://lucene//test1";
        //要在文档中搜索的字符串
        String s = "luc";
        search(indexDir,s);
    }

    public static void search(String indexDir,String s) throws Exception{
//        Directory directory = FSDirectory.open(Paths.get(indexDir));
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexDir)));
        IndexSearcher searcher = new IndexSearcher(reader);
        //指定在哪些文件索引中查询
        QueryParser parser = new QueryParser("contents", new StandardAnalyzer());
        Query query = parser.parse(s);
        long startTime = System.currentTimeMillis();

        TopDocs hits = searcher.search(query,10);
        long endTime = System.currentTimeMillis();
        System.out.println("发现" + hits.totalHits + "\n" + "总共用时" +(endTime- startTime) + "ms");

        //找到的话输出文档所在路径
        for(ScoreDoc scoreDoc : hits.scoreDocs){
            Document doc = searcher.doc(scoreDoc.doc);
            System.out.println(doc.get("路径"));
        }
        reader.close();
    }
}
