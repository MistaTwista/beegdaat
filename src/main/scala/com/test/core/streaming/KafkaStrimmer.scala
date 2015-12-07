/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// scalastyle:off println
package com.test.core.streaming

import java.util.HashMap

import org.apache.kafka.clients.producer.{ProducerConfig, KafkaProducer, ProducerRecord}

import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf

/**
 * Consumes messages from one or more topics in Kafka and does wordcount.
 * Usage: KafkaStrimmer <zkQuorum> <group> <topics> <numThreads>
 *   <zkQuorum> is a list of one or more zookeeper servers that make quorum
 *   <group> is the name of kafka consumer group
 *   <topics> is a list of one or more kafka topics to consume from
 *   <numThreads> is the number of threads the kafka consumer should use
 *
 * Example:
 *    `$ bin/run-example \
 *      org.apache.spark.examples.streaming.KafkaStrimmer zoo01,zoo02,zoo03 \
 *      my-consumer-group topic1,topic2 1`
 */
object KafkaStrimmer {
  def main(args: Array[String]) {
    if (args.length < 4) {
      System.err.println("Usage: KafkaStrimmer <zkQuorum> <group> <topics> <numThreads>")
      System.exit(1)
    }

    // params
    val Array(zkQuorum, group, topics, numThreads) = args
    // Spark app configuration
    val sparkConf = new SparkConf().setAppName("KafkaStrimmer")
    // creating entry point for Spark Streaming
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    // shame on me... magic
    ssc.checkpoint("checkpoint")

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap)

    lines.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
// scalastyle:on println
