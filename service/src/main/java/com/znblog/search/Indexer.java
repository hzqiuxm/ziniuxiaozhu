package com.znblog.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by hzqiuxm on 2016/3/19 0019.
 * 生成索引
 */
public class Indexer {
    private IndexWriter writer;
    public static boolean isCreate = true;
    final static String indexDir = "D://lucene//test1";
    final static String dataDir = "D://lucene//test2";

    public static void main(String[] args) throws Exception{
        for(int i=0; i<args.length; i++){
            if("-update".equals(args[i])){
                isCreate = false;
                break;
            }
        }

        long startTime  = System.currentTimeMillis();
        Indexer indexer = new Indexer(indexDir);
        int numIndexed;
        try {
            numIndexed = indexer.index(dataDir,new TextFileFiler());
        } finally {
            indexer.close();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("搜索文件总共用了："+ (endTime-startTime) + "毫秒");

}

    public Indexer(String indexDir) throws Exception{

        //存放索引的地方
        //Directory directory = new RAMDirectory(); 放在内存中
        Directory dir = FSDirectory.open(Paths.get(indexDir));
        Analyzer analyzer  = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        if(isCreate){
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        }else {
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        }
        if(!Files.isWritable(Paths.get(indexDir))){
            System.out.println("存放索引文件目录不可写");
        }else{
            System.out.println("indexDir 目录可写!");
        }
        writer = new IndexWriter(dir,iwc);

    }

    public void close() throws Exception{
        writer.close();
    }

    public int index(String dataDir, FileFilter filter) throws Exception{
        File[] files = new File(dataDir).listFiles();
        for(File f : files){
            if(!f.isDirectory()&&!f.isHidden()&&f.exists()&&f.canRead()&&(filter == null||filter.accept(f))){
                indexFile(f);
            }
        }
        return writer.numDocs();
    }

    private static class TextFileFiler implements FileFilter{

        @Override
        public boolean accept(File pathname) {
            return pathname.getName().toLowerCase().endsWith(".txt");
        }
    }

    protected Document getDocument(File file) throws Exception{
        Document doc = new Document();
       /* //老版本写法
        doc.add(new Field("文档",new FileReader(file)));
        doc.add(new Field("文件名", file.getName(),Field.Store.YES,Field.Index.NOT_ANALYZED));
        doc.add(new Field("路径", file.getCanonicalPath(),Field.Store.YES,Field.Index.NOT_ANALYZED));*/

        //新版本写法
        //NIO的写法需要传入docDir必须是文件
//        final Path docDir = Paths.get("D://lucene//test2//新建文本文档.txt");
//        InputStream stream = Files.newInputStream(docDir, StandardOpenOption.CREATE);
        Field pathField = new StringField("文档", file.toString(), Field.Store.YES);
        Field fileName = new StringField("文件名",file.getName(),Field.Store.YES);
        Field filePath = new StringField("路径",file.getCanonicalPath(),Field.Store.YES);
        Field modfied = new LongField("modified",Files.getLastModifiedTime(Paths.get(dataDir)).toMillis(),Field.Store.YES);

        doc.add(pathField);
        doc.add(fileName);
        doc.add(filePath);
        doc.add(modfied);
        //对应NIO写法
//        doc.add(new TextField("内容",new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));
        doc.add(new TextField("contents",new FileReader(file)));
        return doc;
    }

    private void indexFile(File file) throws Exception{
        System.out.println("Indexing "+ file.getCanonicalPath());
        Document doc = getDocument(file);
        writer.addDocument(doc);
    }

}
