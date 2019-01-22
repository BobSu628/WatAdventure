package game;
import java.io.File;

public class Test {

    public static void main(String[] args)
    {
        // Provide full path for directory(change accordingly)
        String maindirpath = "saves";

        // File object
        File maindir = new File(maindirpath);

        if(maindir.exists() && maindir.isDirectory())
        {
            // array for files and sub-directories
            // of directory pointed by maindir
            File arr[] = maindir.listFiles();

            System.out.println("**********************************************");
            System.out.println("Files from game directory : " + maindir);
            System.out.println("**********************************************");

            // Calling recursive method
           for(int i = 0; i < arr.length; i ++){
               System.out.println(arr[i].getName());
           }
        }
    }
}