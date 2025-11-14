package api.utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {

    private static final Logger logger = LogManager.getLogger(ExtentReportManager.class);
    
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;
    String repName;

    @Override
    public void onStart(ITestContext testContext) {
        logger.info("========== Initializing Extent Report ==========");
        
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";
        
        // Create reports directory if it doesn't exist
        String reportPath = System.getProperty("user.dir") + "/reports";
        File reportsDir = new File(reportPath);
        if (!reportsDir.exists()) {
            boolean created = reportsDir.mkdirs();
            logger.info("Reports directory created: {}", created);
        }
        
        // Full report file path
        String reportFile = reportPath + "/" + repName;
        logger.info("Report will be generated at: {}", reportFile);
        
        // Initialize Spark Reporter
        sparkReporter = new ExtentSparkReporter(reportFile);
        
        // Report configuration
        sparkReporter.config().setDocumentTitle("PetStore API Automation Report");
        sparkReporter.config().setReportName("Pet Store Users API Test Results");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setTimeStampFormat("dd-MM-yyyy HH:mm:ss");
        
        // Initialize Extent Reports
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        
        // System Information
        extent.setSystemInfo("Application", "Pet Store Users API");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tester", "Irfan");
        
        logger.info("Extent Report initialized successfully");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS, "✓ Test Case PASSED: " + result.getName());
        
        logger.info("✓ Test PASSED: {}", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, "✗ Test Case FAILED: " + result.getName());
        test.log(Status.FAIL, "Failure Reason: " + result.getThrowable().getMessage());
        test.fail(result.getThrowable());
        
        logger.error("✗ Test FAILED: {}", result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, "⚠ Test Case SKIPPED: " + result.getName());
        test.skip(result.getThrowable());
        
        logger.warn("⚠ Test SKIPPED: {}", result.getName());
    }

    @Override
    public void onFinish(ITestContext testContext) {
        logger.info("========== Flushing Extent Report ==========");
        
        if (extent != null) {
            extent.flush();
            logger.info("✓ Extent Report generated successfully!");
            logger.info("Report Location: {}/reports/{}", System.getProperty("user.dir"), repName);
        }
    }
}
