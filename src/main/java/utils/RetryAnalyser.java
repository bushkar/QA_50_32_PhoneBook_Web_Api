package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyser implements IRetryAnalyzer {
    private int retryCount = 0;
    private static int maxTryValue = 3;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (retryCount < maxTryValue) {
            retryCount++;
            return true;
        }
        return false;
    }
}
