package org.example;

import java.sql.*;
import java.util.List;
import java.sql.PreparedStatement;
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.example.AccoliteData;

import javax.xml.transform.Result;

public class DataBaseManager {
    private static final JDBC_URL="jdbc::mysql://localhost::3306/accolite";
    private static final String USERNAME="root";
    private static final String PASSWORD="Aayushi@123";
    private static final String TABLE_NAME="AccoliteINTERVIEWData";
    private static final BasicDataSource dataSource=new BasicDataSource();
    static{
        dataSource.setUrl(JDBC_URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

    }
    public static void createTable(){
        try(Connection connection =dataSource.getConnection(); Statement statement=connection.createStatement()){
            String createTableQuery ="Create Table if not Exists "+TABLE_NAME+" ("+
                    "ID int AUTO_INCREMENT PRIMARY KEY,"+
                    "date VARCHAR(255), "+
                    "month VARCHAR(255),"+
                    "team VARCHAR(255),"+
                    "panelName VARCHAR(255),"+
                    "round VARCHAR(255),"+
                    "skill VARCHAR(255),"+
                    "time VARCHAR(255),"+
                    "candidateCurrentLoc VARCHAR(255),"+
                    "candidatePrefferdLoc VARCHAR(255),"+
                    "candidateName VARCHAR(255)"+
                    ")";
            statement.executeUpdate(createTableQuery);
            System.out.println("Table "+TABLE_NAME +" ' created successfully. ");

        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }
    public static void insertData(List<AccoliteData> data){
        data.parallelStream().forEach(record->{
            try(Connection connection=dataSource.getConnection(); PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO "+TABLE_NAME +"( date, month, team, panelNaem, round, skill, time, "+"candidateCurrentLoc, candidtePrefferedLoc, candidateName) VALUES (?,?,?,?,?,?,?,?,?,?)")){
                preparedStatement.setString(1,record.getDate());
                preparedStatement.setString(2,record.getMonth());
                preparedStatement.setString(3,record.getTeam());
                preparedStatement.setString(4,record.getPanelName());
                preparedStatement.setString(5,record.getRound());
                preparedStatement.setString(6,record.getSkill());
                preparedStatement.setString(7,record.getTime());
                preparedStatement.setString(8,record.getCandidate_current_loc());
                preparedStatement.setString(9,record.getCandidate_Pref_loc());
                preparedStatement.setString(10,record.getCandidate_Name());


                preparedStatement.executeUpdate();

            }catch (SQLException e){
                e.printStackTrace();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        });
    }
    public static void teamWithMaxInterviews(){
        try(Connection connection =dataSource.getConnection();
            Statement statement=connection.createStatement()){
            String query="SELECT team, COUNT(*) as interviewCount\n"+
                    "FROM accolite_date\n"+
                    "WHERE month IN('OCT-23','NOV-23')\n"+
                    "GROUP BY team\n"+
                    "ORDER BY interviewCount DESC\n"+
                    "LIMIT 1;";
            ResultSet resultSet= statement.executeQuery(query);
            if(resultSet.next()){
                System.out.println("Team with Maximum Interviews: "+resultSet.getString("team"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

    }
    public static void teamWithMinInterviews() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT team, COUNT(*) as interviewCount\n" +
                    "FROM accolit_data\n" +
                    "WHERE month IN('Oct-23','Nov-23')\n" +
                    "GROUP BY team\n" +
                    "ORDER BY interviewCount ASC\n" +
                    "Limit 1;";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                System.out.println("Team with Minimum Interviews: " + resultSet.getString("team"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void top3Panels(){
        try(Connection connection=dataSource.getConnection();
            Statement statement= connection.createStatement()) {
            String query = "SELECT panelName, COUNT(*) as interviewCount\n" +
                    "FROM accolite_data\n" +
                    "WHERE month IN('Oct-23','Nov-23')\n"+

                    "GROUP BY panelName\n"+
                    "ORDER BY interviewCount DESC\n"+
                    "LIMIT 3;";
            ResultSet resultSet=statement.executeQuery(query);
            while(resultSet.next()){
                System.out.println("Panel: "+resultSet.getString("panelName")+
                        ",Interview Count: "+ resultSet.getInt("interviewCount"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void top3Skills(){
        try(Connection connection=dataSource.getConnection();
            Statement statement=connection.createStatement()) {
            String query = "CREATE OR REPLACE VIEW top_skills_view AS\n" +
                    "SELECT skill,COUNT(*) as interviewCount\n" +
                    "FROM accolite_data\n" +
                    "WHERE month IN('Oct -23, 'Nov-23')\n" +
                    "GROUP BY skill\n" +
                    "ORDER BY interviewCount DESC\n" +
                    "LIMIT 3;\n" +
                    "\n";
            statement.executeUpdate(query);
            String Query2 = "SELECT 8 FROM top_skills_view;";
            ResultSet resultSet = statement.executeQuery(Query2);
            while (resultSet.next()) {
                System.out.println("Skill: " + resultSet.getString("skill") +
                        ", Interview Count: " + resultSet.getInt("interviewCount"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void skillsInPeakTime()
    {
        try(Connection connection=dataSource.getConnection();
            Statement statement=connection.createStatement()){

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void skillsInPeakTime(){
        try(Connection connection=dataSource.getConnection();
            Statement statement=connection.createStatement()){
            String query="SELECT skill, COUNT(*) as interviewCount\n"+
                    "FROM accolite =_data\n"+
                    "WHERE time='Peak Time'\n"+
                    "GROUP BY skill\n"+
                    "ORDER BY interviewCount DESC\n"+
                    "LIMIT 3;";
            ResultSet resultSet=statement.executeQuery(query);
            while(resultSet.next()){
                System.out.println("Skill in Peak Time: "+resultSet.getString("skill")+", Interview Count: "+ resultSet.getInt("interviewCount"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
