package process;

import java.sql.Connection;
import java.sql.Statement;

public class Setup {

    protected Connection connect = null;
    protected Statement statement = null;
    protected String dataBaseName = "roadpech_mobile";
    protected String username = "root";
    protected String password = "";

    public Setup() {
	super();
    }

}