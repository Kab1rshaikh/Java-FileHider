package views;

import dao.DataDAO;
import model.Data;
import service.UserService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String email;
    UserView(String email){
        this.email= email;
    }

    public void home(){
        do{
            System.out.println("Welcome"+this.email);
            System.out.println("Press 1 to show hidden files");
            System.out.println("Press 2 to hide a new file");
            System.out.println("Press 3 to unhide a file");
            System.out.println("Press 0 to exit");
            Scanner sc = new Scanner(System.in);
            int choice=Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1:
                    try {
                        List<Data> files = DataDAO.getAllFiles(this.email);
                        System.out.println("ID- File name");
                        for(Data file : files)
                        {
                            System.out.println(file.getId()+" - "+file.getFileName());
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:
                    System.out.println("Enter the file path");
                    String path = sc.nextLine();
                    File f = new File(path);
                    Data file = new Data(0, f.getName(), path,this.email);
                    try {
                        DataDAO.hideFile(file);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 3:
                    List<Data> files = null;
                    try {
                        files = DataDAO.getAllFiles(this.email);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("ID- File name");
                    for(Data file1 : files)
                    {
                        System.out.println(file1.getId()+" - "+file1.getFileName());
                    }
                    System.out.println("Enter the id of file to unhide");
                    int id = Integer.parseInt(sc.nextLine());
                    boolean isValidID = false;
                    for( Data file2 : files){
                        if(file2.getId()==id){
                            isValidID =true;
                            break;
                        }

                    }
                    if(isValidID){
                        try {
                            DataDAO.unhide(id);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else{
                        System.out.println("Wrong ID");
                    }
                    break;
                case 4:
                    System.exit(0);






            }
        }while (true);
    }

}
