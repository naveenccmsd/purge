package com.ccmsd.file;

import java.io.File;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PurgeNow
{
  public static void main(String[] args)
  {
    PurgeNow f = new PurgeNow();
    if (args.length > 3) {
      f.startPurge(args[0], args[2], args[1], Boolean.parseBoolean(args[3]));
    } else {
      System.out.println("check arguments properly");
    }
  }
  
  public void startScan() {}
  
  public void startPurge(String folderPath, String cutoffdate, String process, boolean scanSubFolder)
  {
    if (process.equalsIgnoreCase("purge"))
    {
      System.out.println("Memory at the path" + folderPath + " before Purge process");
      diskSpace(folderPath);
      System.out.println("Purge process started...This process will delete all the file and folder modified before" + cutoffdate);
      doPurge(folderPath, cutoffdate, process, scanSubFolder);
      System.out.println("Purge process Completed");
      System.out.println("Memory at the path " + folderPath + " After Purge process");
      diskSpace(folderPath);
    }
    else if (process.equalsIgnoreCase("scan"))
    {
      System.out.println("Scanning purge process......[The below files will be deleted in purge process]");
      doPurge(folderPath, cutoffdate, process, scanSubFolder);
    }
  }
  
  public void doPurge(String folderPath, String cutoffdate, String process, boolean scanSubFolder)
  {
    File folder = new File(folderPath);
    for (File fileEntry : folder.listFiles()) {
      if (fileEntry.isDirectory())
      {
        if (scanSubFolder)
        {
          doPurge(fileEntry.getAbsolutePath(), cutoffdate, process, scanSubFolder);
          if (fileEntry.list().length <= 0)
          {
            System.out.println("Folder is empty :" + fileEntry.getAbsolutePath());
            deleteFiles(fileEntry.getAbsoluteFile());
          }
        }
      }
      else
      {
        String modDate = FileModifiedDate(fileEntry.getAbsolutePath());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        try
        {
          Date date1 = sdf1.parse(modDate);
          Date date2 = sdf.parse(cutoffdate);
          if (date1.before(date2)) {
            if (process.equalsIgnoreCase("purge")) {
              deleteFiles(fileEntry.getAbsoluteFile());
            } else if (process.equalsIgnoreCase("scan")) {
              System.out.println(fileEntry.getAbsoluteFile() + " Last Modified:" + modDate);
            }
          }
        }
        catch (ParseException e)
        {
          System.out.println("Please provide proper Date");
          System.out.println(e);
        }
      }
    }
  }
  
  void deleteFiles(File file)
  {
    if (file.delete()) {
      System.out.println(file.getName() + " is deleted!");
    } else {
      System.out.println("Failed to delete File :" + file.getName());
    }
  }
  
  String FileModifiedDate(String filepath)
  {
    File file = new File(filepath);
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    String lastModifiedDate = sdf.format(Long.valueOf(file.lastModified()));
    return lastModifiedDate;
  }
  
  void diskSpace(String folderPath)
  {
    File file = new File(folderPath);
    long totalSpace = file.getTotalSpace();
    long usableSpace = file.getUsableSpace();
    long freeSpace = file.getFreeSpace();
    
    System.out.println(" === Partition Detail ===");
    
    System.out.println(" === bytes ===");
    System.out.println("Total size : " + totalSpace + " bytes");
    System.out.println("Space free : " + usableSpace + " bytes");
    System.out.println("Space free : " + freeSpace + " bytes");
    
    System.out.println(" === mega bytes ===");
    System.out.println("Total size : " + totalSpace / 1024L / 1024L + " mb");
    System.out.println("Space free : " + usableSpace / 1024L / 1024L + " mb");
    System.out.println("Space free : " + freeSpace / 1024L / 1024L + " mb");
  }
}
