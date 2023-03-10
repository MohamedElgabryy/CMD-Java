
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;




 class Terminal {

    Parser parser = new Parser();
    File directorypath = new File(System.getProperty("user.dir"));

    void echo(String[] args) {

        System.out.println(args[0]);
    }

    void pwd() {

        System.out.println(directorypath);

    }

    void Is() {
        String[] filesName = directorypath.list();
        Arrays.sort(filesName);
        for (String file : filesName) {
            System.out.println(file);
        }
    }

    void Isr() {

        String[] filesName = directorypath.list();
        Arrays.sort(filesName, Collections.reverseOrder());
        for (String file : filesName) {
            System.out.println(file);
        }

    }
     String handlingFullShortPath(Path relativePath){
        String path;
        if(relativePath.isAbsolute()){
         
            path=relativePath.toAbsolutePath().toString();
            return path;
            }else{
           
                path=directorypath+"\\"+relativePath.toString();
                return path;
            }
      
    }
    void cd(String []args){
        if(args.length==0){
            String homePath=System.getProperty("user.home");
            directorypath=new File(homePath);
        }else if(args.length==1 && args[0].equals("..")){
            directorypath=directorypath.getParentFile();

        }else {
            Path relativePath = Paths.get(args[0]);
            File newfile;
           newfile = new File (handlingFullShortPath(relativePath));
            if(newfile.exists()){
                directorypath=newfile;
            }else{
                System.out.println("This Path is invaild");
            }

        }}


    public void rm(String[] args) {
        String current = System.getProperty("user.dir");
        String current_path = current + "//" + args[0];
        File file = new File(current_path);
        if (file.exists())
            file.delete();
        else {
            System.out.println("file isn't exist");
        }
    }

    public String cat(String[] args) throws IOException {
        if (args.length == 1) {
            File file = new File(args[0]);
            if(!file.exists())
            {
                System.out.println("file not exist");
            }
            else {
                Scanner read = new Scanner(file);
                while (read.hasNextLine()) {
                    System.out.println(read.nextLine());
                }
                read.close();
            }
        }
        else if (args.length == 2) {
            File one = new File(args[0]);
            File two = new File(args[1]);
            if (!one.exists()||!two.exists())
            {
                System.out.println("files are not exists");
            }
            else {
                Scanner read = new Scanner(one);
                while (read.hasNextLine()) {
                    System.out.println(read.nextLine());
                }
                read.close();
                Scanner re = new Scanner(two);
                while (re.hasNextLine()) {
                    System.out.println(re.nextLine());
                }
                re.close();
            }

        }
        return null;
    }
    public void mkdir(String[] args) {
       File newDirectory;
        for (String arg : args) {
        if(arg.contains("/")){
            Path path= Paths.get(arg);
            String pat=handlingFullShortPath(path);
          newDirectory= new File(pat);
         
        
        }else{
          newDirectory = new File(directorypath.getAbsolutePath());

        }    
        if (newDirectory.exists()){
         System.out.println("Directory created");
            System.out.println(newDirectory);
        
        }else{
            System.out.println("Error in vaild");}
        }
       
    }

   public void rmdir(String[] args){
      if(args.length==0){
          System.out.println("Cant find any Directory");
      }
      else if(args[0].equals("*")){
    
    File direcPath = new File(directorypath.getAbsolutePath());
    FileFilter fiFilter = new FileFilter(){
         public boolean accept(File dir) {          
            if (dir.isDirectory()&& dir.list().length==0) {
               return true;
            } else {
               return false;
            }
         }
      };//filefilter only accpets empty directories
      File[] list = direcPath.listFiles(fiFilter);
     
      for(File fileName : list) {
         fileName.delete();
      }  
   }
       else{
        Path path=Paths.get(args[0]);
        String strPath=handlingFullShortPath(path);
          System.out.println(strPath);
        File newDirectory=new File(strPath);
        if(newDirectory.isDirectory()){
        if(newDirectory.list().length==0){
          newDirectory.delete();
            System.out.println("Directory Deleted");
        }else{
            System.out.println("Directory is not empty");
        }
        
       }else{
            System.out.println("invalid directory");
        }}
      
   }   
   
public void touch(String [] args) throws IOException  {
    
    Path pathToFile=Paths.get(args[0]);
    
    String path = handlingFullShortPath(pathToFile);

   BufferedWriter newFile= new BufferedWriter(new FileWriter(new File(path)));

   newFile.close();

}	
public void cp(String[] args) throws Exception{
            if(args.length==1){
                System.out.println("Error: invalid parameters are enter");
            
            }else{
        FileInputStream in = new FileInputStream(args[0]);
        FileOutputStream out = new FileOutputStream(args[1]);
  
        try {
  
            int n;
            while ((n = in.read()) != -1) {
                out.write(n);
            }
        }
        finally {
            if (in != null) {
  
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
        System.out.println("File Copied");
    }
        }					       
       public void cpr(String[] args) { 
           
            if(args.length==1){
                System.out.println("Error: invalid parameters are enter");
            
            }else{
		File Source = new File(args[0]);
		File Destination = new File(args[1]);
		
		if( !(Source.isAbsolute()) )
			Source = new File(directorypath + "\\" + args[0] );
	
		if( !(Destination.isAbsolute()) )
			Destination = new File(directorypath + "\\" + args[1]);	

		   if(Source.exists())
		   {
	   try {
					Files.copy(Source.toPath(),Destination.toPath());
		} catch (IOException e) {
					System.out.println("Error in the DirectoryPath");
}	 
			   
		   }
						
	       } 
       }
    public void chooseCommandAction() throws IOException, Exception {
        String commandAction = parser.getCommandName();
        if (commandAction.equals("cat")) {
            cat(parser.getArgs());
        } else if (commandAction.equals("rm")) {
            rm(parser.getArgs());
        } else if (commandAction.equals("echo")) {
            echo(parser.getArgs());
        } else if (commandAction.equals("pwd")) {
            pwd();
        } else if (commandAction.equals("cd")) {
            cd(parser.getArgs());
        } else if (commandAction.equals("Isr")) {
            Isr();
        } else if (commandAction.equals("Is")) {
            Is();
        }
        else if (commandAction.equals("exit")) {
            System.exit(0);
        }else if(commandAction.equals("cp")){
            cp(parser.getArgs());
        }else if (commandAction.equals("cp-r")){
            cpr(parser.getArgs());
        }else if(commandAction.equals("touch")){
            touch(parser.getArgs());
        }else if(commandAction.equals("mkdir")){
            mkdir(parser.getArgs());
        
        }else if(commandAction.equals("rmdir")){
         rmdir(parser.getArgs());
        
        }
        else
        {
            System.out.println("Invalid command");
        }

    }
public static void main(String[] args) throws Exception  {
        Terminal terminal = new Terminal();
        Scanner input = new Scanner(System.in);
        String in ;
        System.out.println("enter the command name ");
        in = input.nextLine();

        for(int i = 0; in != "exit"; i++) {

            terminal.parser.parse(in);
            terminal.chooseCommandAction();
            in = input.nextLine();

        }


    }

}



class Parser {
    String commandName;
    String[] args;

    public boolean parse(String input) {
        String in[] = input.split(" ");
        int counter = 0;
        commandName = in[0];

        if (in.length > 1 && in[1] == "-r") {
            counter = 1;
            commandName +=  " " + in[1];
        }

        if(counter == 0) {
            args = new String [in.length - 1];
            for (int i = 1; i < in.length; i++) {
                args[i - 1]= in[i];
            }
        }
        else if(counter == 1) {
            args = new String [in.length - 2];
            for (int i = 2; i < in.length; i++) {
                args[i-2]= in[i];
            }
        }
        return true;
    }
    public String getCommandName(){
        return commandName;
    }
    public String[] getArgs(){
        return args;
    }
}