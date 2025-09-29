package hello;// Java hello.Server.java

public class Server {
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

    static int run(org.omg.CORBA.ORB orb)
            throws org.omg.CORBA.UserException {
        try {
            org.omg.PortableServer.POA rootPOA =
                    org.omg.PortableServer.POAHelper.narrow(
                            orb.resolve_initial_references("RootPOA"));

            org.omg.PortableServer.POAManager manager =
                    rootPOA.the_POAManager();

            // Activate the POA manager
            manager.activate();

            // Create servant and get object reference
            HelloImpl helloImpl = new HelloImpl(); // your servant class
            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(helloImpl);

            // Convert object reference to IOR string
            String ior = orb.object_to_string(ref);

            // Write IOR to file
            java.nio.file.Files.write(
                    java.nio.file.Paths.get("ior.txt"),
                    ior.getBytes()
            );

            System.out.println("hello.Server ready. IOR saved to ior.txt");

            // Run the ORB event loop
            orb.run();

            return 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 1;
        }
    }
}
