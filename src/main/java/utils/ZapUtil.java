package utils;

import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ZapUtil {

    // Update these to match your ZAP configuration
    private static final String ZAP_HOST = "localhost";
    private static final int ZAP_PORT = 8080;       // Check in ZAP: Tools -> Options -> Network
    private static final String ZAP_API_KEY = "Update_API_Key"; // Tools -> Options -> API

    private static ClientApi api;

    // Singleton ClientApi instance
    private static ClientApi getClient() {
        if (api == null) {
            api = new ClientApi(ZAP_HOST, ZAP_PORT, ZAP_API_KEY);
        }
        return api;
    }

    /**
     * Run an Active Scan for the given target URL.
     * Example target: "https://practicetestautomation.com"
     */
    public static void runActiveScan(String targetUrl) throws Exception {
        ClientApi client = getClient();

        System.out.println("Starting ZAP Active Scan for: " + targetUrl);

        // Use the 6-argument scan method (all String params)
        ApiResponse resp = client.ascan.scan(
                targetUrl,
                "true",   // recurse: scan all URLs under this host
                null,     // inScopeOnly
                null,     // scanPolicyName
                null,     // method
                null      // postData
        );

        String scanId = ((ApiResponseElement) resp).getValue();
        System.out.println("ZAP Active Scan started. ID: " + scanId);

        int progress = 0;
        while (progress < 100) {
            Thread.sleep(5000);
            ApiResponse statusResp = client.ascan.status(scanId);
            progress = Integer.parseInt(((ApiResponseElement) statusResp).getValue());
            System.out.println("ZAP scan progress: " + progress + "%");
        }

        System.out.println("ZAP Active Scan completed for: " + targetUrl);
    }

    /**
     * Get the ZAP HTML report as bytes.
     */
    public static byte[] getHtmlReport() throws Exception {
        ClientApi client = getClient();
        return client.core.htmlreport();
    }

    /**
     * Convenience method: run scan and save HTML report to target/zap-report.html
     */
    public static Path runScanAndSaveReport(String targetUrl) throws Exception {
        runActiveScan(targetUrl);

        byte[] reportBytes = getHtmlReport();
        Path reportPath = Paths.get("target", "zap-report.html");

        try {
            Files.createDirectories(reportPath.getParent());
            Files.write(reportPath, reportBytes);
            System.out.println("ZAP report saved at: " + reportPath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to write ZAP report file: " + e.getMessage());
            throw e;
        }

        return reportPath;
    }
}
