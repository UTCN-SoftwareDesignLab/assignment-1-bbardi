package launcher;

import database.Bootstrapper;

import java.sql.SQLException;

public class Main {
    private static boolean BOOTSTRAP = false;

    public static void main(String[] args) {
        bootstrap();

        ComponentFactory componentFactory = ComponentFactory.getInstance(false);
        componentFactory.getLoginView().setVisible(true);
    }

    private static void bootstrap(){
        if(BOOTSTRAP)
        {
            try {
                new Bootstrapper().execute();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
