package com.itcast.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WordCountMaper extends Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {

        // 接收传入进来的一行文本，把数据类型转换为String类型
        String line = value.toString();
        // 将这行内容按照分隔符切割
        String[] words = line.split(" ");
        // 遍历数组，每出现一个单词就标记一个数组1例如：<单词，1>
        for (String word : words) {
            // 使用context， 把Map阶段处理的数据发送给Reduce阶段作为输入数据
            context.write(new Text(word), new IntWritable(1));
        }
    }


}
