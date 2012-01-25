package org.dyndns.tooman;

import java.io.*;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class SpiritDBAdapter  extends SQLiteOpenHelper{

	/** The Android's default system path of your application database.
	 * Сюда будет копироваться база с карты */
    private static String DB_PATH = "/data/data/org.no-ip.tooman/databases/";
    private static String DB_NAME = "alcotable.db";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public SpiritDBAdapter(Context context) {
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    /** Создаём пустую базу и записываем туда данные из существующей на карте */
    public void createDataBase() throws IOException{
    	boolean dbExist = checkDataBase();
    	if(dbExist){
    		// база уже есть - ничего не делаем
    	}else{
    		//By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
        	try {
    			copyDataBase();
    		} 
        	catch (IOException e) {
        		throw new Error("Error copying database");
        	}
    	}
    }

    /** Проверяем наличие базы, чтобы не переписывать её каждый раз при открытии приложения
     * @return true если есть, false если нет */
    private boolean checkDataBase(){
    	SQLiteDatabase checkDB = null;
     	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
     	}catch(SQLiteException e){
     		// базы на карте нет
     	}
     	if(checkDB != null){
     		checkDB.close();
     	}
     	return checkDB != null ? true : false;
    }
    
    /** 
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    }

    public void openDataBase() throws SQLException{
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }
    
    @Override
	public synchronized void close() {
    	if(myDataBase != null)
    		myDataBase.close();
     	    super.close();
 	}
    
	@Override
	public void onCreate(SQLiteDatabase db) {}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}