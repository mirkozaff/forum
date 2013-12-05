package utility_package;

import java.io.Closeable;
import java.io.IOException;


public class Functions {
    
    public static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
