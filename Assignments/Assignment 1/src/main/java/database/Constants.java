package database;

import java.util.*;
import static database.Constants.Rights.*;
import static database.Constants.Roles.*;

public class Constants {
    public static class Schemas {
        public static final String TEST = "test_bank";
        public static final String PRODUCTION = "bank";

        public static final String[] SCHEMAS = new String[]{TEST,PRODUCTION};
    }
    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";

        public static final String[] ROLES = new String[]{ADMINISTRATOR,EMPLOYEE};
    }

    public static class Tables {
        public static final String CLIENT = "client";
        public static final String ACCOUNT_TYPE = "account_type";
        public static final String ACCOUNT = "account";
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";
        public static final String ACTIVITY = "activity";
        public static final String UTILITIES = "utilities";

        public static final String[] TABLES = new String[]{CLIENT,ACCOUNT_TYPE,ACCOUNT,USER,ROLE,RIGHT,ROLE_RIGHT,USER_ROLE,ACTIVITY,UTILITIES};
    }

    public static class Rights {
        public static final String ADD_CLIENT = "add_client";
        public static final String UPDATE_CLIENT = "update_client";
        public static final String VIEW_CLIENT = "view_client";

        public static final String CREATE_ACCOUNT = "create_account";
        public static final String UPDATE_ACCOUNT = "update_account";
        public static final String DELETE_ACCOUNT = "delete_account";
        public static final String VIEW_ACCOUNT = "view_account";

        public static final String TRANSFER_MONEY = "transfer_money";
        public static final String PROCESS_UTILITIES = "process_utilities";

        public static final String CREATE_EMPLOYEE = "create_employee";
        public static final String READ_EMPLOYEE = "read_employee";
        public static final String UPDATE_EMPLOYEE = "update_employee";
        public static final String DELETE_EMPLOYEE = "delete_employee";

        public static final String GENERATE_REPORT = "generate_report";

        public static final String[] RIGHTS = new String[]{ADD_CLIENT,UPDATE_CLIENT,VIEW_CLIENT,
                                                                CREATE_ACCOUNT,UPDATE_ACCOUNT,DELETE_ACCOUNT,VIEW_ACCOUNT,
                                                                TRANSFER_MONEY,PROCESS_UTILITIES,
                                                                CREATE_EMPLOYEE,READ_EMPLOYEE,UPDATE_EMPLOYEE,DELETE_EMPLOYEE,
                                                                GENERATE_REPORT};
    }
    public static class AccountTypes {
        public static final String DEBIT = "debit";
        public static final String DEPOSIT = "deposit";
        public static final String CREDIT = "credit";

        public static final String[] ACCOUNTTYPES = {DEBIT,DEPOSIT,CREDIT};
    }
    public static Map<String, List<String>> getRolesRights(){
        Map<String, List<String>> rolesRights = new HashMap<>();
        for(String role : ROLES){
            rolesRights.put(role,new ArrayList<>());
        }
        rolesRights.get(ADMINISTRATOR).addAll(Arrays.asList(RIGHTS));

        rolesRights.get(EMPLOYEE).addAll(Arrays.asList(ADD_CLIENT,UPDATE_CLIENT,VIEW_CLIENT,CREATE_ACCOUNT,UPDATE_ACCOUNT,DELETE_ACCOUNT,VIEW_ACCOUNT,TRANSFER_MONEY,PROCESS_UTILITIES));
        return rolesRights;
    }
}
