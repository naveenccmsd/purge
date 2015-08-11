# purge


#To find the files which is older than a date [Excluding sub directries]
java -classpath csxPurge.jar com.ccmsd.file.PurgeNow "C:\Users\np122\Downloads" "scan" "11/15/2015" "false"

#To find the files and folder which is older than a date [Including sub directries]
java -classpath csxPurge.jar com.ccmsd.file.PurgeNow "C:\Users\np122\Downloads" "scan" "11/15/2015" "true"

#To Delete the files which is older than a date [Excluding sub directries]
java -classpath csxPurge.jar com.ccmsd.file.PurgeNow "\\wpcsmmp02\Data\spool\logs" "purge" "03/15/2015" "false"

#To Delete the files and folder which is older than a date [Including sub directries]
java -classpath csxPurge.jar com.ccmsd.file.PurgeNow "C:\Users\np122\Downloads" "purge" "11/15/2015" "true"
