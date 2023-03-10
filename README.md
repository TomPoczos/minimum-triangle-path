while the code satisfies all requirements, i.e. solves the problem and is capable of processing the provided 
2000 lines long file in under a minute on my hardware (as evidenced in unit testing, including the performance aspect),

despite my best effort I only managed to get the executable work when a filename is passed to it, i.e. 

```
cat << EOF | java -jar my.jar
7
6 3
3 8 5
11 2 10 9
EOF
```

wouldn't appear in the args array, which is weird because 

`java -jar my.jar 7`

worked. Probably some weird bash thing no one ever ran into, at least according to google search results.

---

for building the final jar I used the sbt-assembly sbt plugin, you can build the jar using the `assembly` sbt command.

once done, you will find the jar at the usual location (/home/tom/Documents/suprnation/minimum-triangle-path/target/scala-2.13)

you can run it by passing a path to your data file to it, in my case it looks like the below, though of course the file's path will
vary

`java -jar minimum-triangle-path-assembly-0.1.0-SNAPSHOT.jar /home/tom/Documents/suprnation/data_small.txt`
