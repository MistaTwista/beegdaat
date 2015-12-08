#BeegDaat

##Первый
pom.xml - файл настроек проекта, дословно объектная модель проекта. Что где лежит, куда класть готовое, какие доп модули потребуются, где тесты, где исходники

* -Xfatal-warnings - останавливает компиляцию если есть warning
* -Xlint - набор требований к форматированию кода
* -deprecation - показывает информацию по использованным, но помеченным на удаление функциям
* -explaintypes - указывает на ошибки с типами переменных

##Второй
Загружаются в Nexus: контрольные суммы, xml файлы, jar, pom
Минимальное время автоматического апдейта синхронизации списка доступных версий: 1 час
Настройки авторизации Nexus: ~/.m2/settings.xml

##Третий
1. Real time для всех разный, где то 10мс, где то 2 часа, строгость нарушения от потребностей системы
2. Для получения задержки между сообщениями: `Date.getTime()` (producer -> kafka -> consumer)
3. Spark в 20 раз больше Kafka, потому и компилится дольше (examples, scala, mqtt)
4. `sys.addShutdownHook{ println("Run after sigterm") }` для вызова после SIGTERM
5. Показываем адрес Zookeeper
```
import org.apache.log4j.Logger
val Log = Logger.getLogger(AppName.this.getClass().getSimpleName())
Log.info("DEBUG info:" + zkQuorum)
```
*Shame bonus:*

[StreamingContext.checkpoint()](https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.streaming.StreamingContext) используется для фиксации каждой партии данных в HDFS-совместимом каталоге и если consumer перестаёт работать мы можем запустить его в актуальном состоянии

