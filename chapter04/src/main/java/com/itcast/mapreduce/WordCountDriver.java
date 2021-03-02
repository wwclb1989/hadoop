package com.itcast.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCountDriver {

    public static void main(String[] args) throws Exception {
        // 通过Job来封装本次MR的相关信息
        Configuration conf = new Configuration();
        // 配置MR运行模式，使用local表示本地模式，可以省略
        conf.set("mapreduce.framework.name", "local");
        Job wcjob = Job.getInstance(conf);
        // 指定MR Job jar包运行主类
        wcjob.setJarByClass(WordCountDriver.class);
        // 指定本次MR所有的Mapper Reducer类
        wcjob.setMapperClass(WordCountMaper.class);
        wcjob.setReducerClass(WordCountReducer.class);
        // 设置业务逻辑Mapper类的输出key和value的数据类型
        wcjob.setMapOutputKeyClass(Text.class);
        wcjob.setMapOutputValueClass(IntWritable.class);
        // 设置业务逻辑Reducer类的输出key和value的数据类型
        wcjob.setOutputKeyClass(Text.class);
        wcjob.setOutputValueClass(IntWritable.class);
        // 使用本地模式指定要处理的数据所在的位置
        FileInputFormat.setInputPaths(wcjob, "E:/mr/input");
        // 使用本地模式指定处理完成之后的结果所保存的位置
        FileOutputFormat.setOutputPath(wcjob, new Path("E:/mr/output"));
        // 提交程序并且临近打印程序执行情况
        boolean res = wcjob.waitForCompletion(true);
        System.exit(res ? 0 : 1);

    }
}
