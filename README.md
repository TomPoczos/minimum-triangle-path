Please note I made a one line change post deadline to make the program to conform to IO requirements, 
i.e. so that instead of passing it a filename that contains the data, one can do

```
cat << EOF | java -jar my.jar
7
6 3
3 8 5
11 2 10 9
EOF
```

If you wish to evaluate the version that has been submitted at/before the deadline, feel free to git checkout the 
penultimate commit (704cb62). Although there is no difference other than how the program consumes initial data.

---

for building the final jar I used the sbt-assembly sbt plugin, you can build the jar using the `assembly` sbt command.

once done, you will find the jar at the usual location ($PROJECT_DIRECTORY/target/scala-2.13)