package Processes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

public class DBManager extends Thread
{
    BufferedReader rd;

    public DBManager() throws FileNotFoundException
    {

    }
    @Override
    public void run()
    {

    }

    public BufferedReader getReader()
    {
        return rd;
    }
}
