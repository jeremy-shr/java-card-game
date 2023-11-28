//import all the required component for testing 
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class TestRunner {
    // the main method here calls TestAll that defines the test suite 
    public static void main(String[] args) {
    Result result = JUnitCore.runClasses(TestAll.class);

    // if the test are not validated, return the value 
    //and expected value and false at the end
    for (Failure failure : result.getFailures()) {
    System.out.println(failure.toString());
    }
    // if test vaidated, returns true
    System.out.println(result.wasSuccessful());
    
    }
}
