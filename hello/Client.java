package hello;// Java hello.Client.java

import java.nio.file.Files;
import java.nio.file.Paths;

public class Client {
    public static void main(String[] args) {
        java.util.Properties props = System.getProperties();

        int status = 0;
        org.omg.CORBA.ORB orb = null;

        try {
            orb = org.omg.CORBA.ORB.init(args, props);
            status = run(orb);
        } catch (Exception ex) {
            ex.printStackTrace();
            status = 1;
        }

        if (orb != null) {
            try {
                orb.destroy();
            } catch (Exception ex) {
                ex.printStackTrace();
                status = 1;
            }
        }

        System.exit(status);
    }

    static int run(org.omg.CORBA.ORB orb) {
        try {
            // Read IOR string from file
            String ior = new String(Files.readAllBytes(Paths.get("ior.txt")));

            // Convert IOR string to CORBA object reference
            org.omg.CORBA.Object obj = orb.string_to_object(ior);

            // Narrow to Hello interface
            Hello helloRef = HelloHelper.narrow(obj);

            // Call the sayHello method
            String response = helloRef.sayHello();
            System.out.println("Response from hello.Server: " + response);
            return 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 1;
        }
    }
}
