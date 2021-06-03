package com.autoscout.springboot;

import com.autoscout.springboot.csv.header.HeaderType;
import com.autoscout.springboot.csv.header.ListHeader;
import com.autoscout.springboot.exception.IncorrectFormatException;
import com.autoscout.springboot.service.ReportingService;
import com.autoscout.springboot.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Controller
public class WebController {

    @Value("${spring.application.name}")
    private String appName;

    private UploadService uploadService;

    private ReportingService reportingService;

    @Autowired
    public WebController(UploadService uploadService, ReportingService reportingService) {

        this.uploadService = uploadService;
        this.reportingService = reportingService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }

    @GetMapping("/listing-upload")
    public String listingUpload() {
        return "fileupload-listing";
    }

    @GetMapping("/contact-upload")
    public String contactUpload() {
        return "fileupload-contact";
    }


    @PostMapping("/validate-and-upload-listing")
    public String uploadAndProcessListingFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes)
    throws IOException, IncorrectFormatException{

        return uploadAndProcessFile(file, attributes, HeaderType.LISTING);

    }

    @PostMapping("/validate-and-upload-contact")
    public String uploadAndProcessContactFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes)
            throws IOException, IncorrectFormatException{

        return uploadAndProcessFile(file, attributes, HeaderType.CONTACT);

    }

    @GetMapping("/average-report")
    public String generateAverageReport(Model model){

        final Map<String, String> averageReport = reportingService.getAverageListingPricePerSellerType();
        model.addAttribute("averageReport", averageReport);

        return "average-report";
    }

    @GetMapping("/percentage-distribution")
    public String generatePercentageDistribution(Model model){

        final Map<String, String> percentageDistribution = reportingService.getPercentageDistribution();
        model.addAttribute("percentageDistribution", percentageDistribution);

        return "percentage-distribution";
    }

    @GetMapping("/frequently-contacted")
    public String generateTop30MostFrequentlyContactedListings(Model model){

        model.addAttribute("averagePrice", reportingService.getTop30MostFrequentlyContactedListings());

        return "frequently-contacted";
    }

    @GetMapping("/monthly-report")
    public String getMonthlyReport(Model model){

        model.addAttribute("monthlyReport", reportingService.getMonthlyReport());

        return "monthly-report";
    }

    private String uploadAndProcessFile(MultipartFile multipartFile, RedirectAttributes redirectAttributes,
                                      HeaderType headerType) throws IOException, IncorrectFormatException {

        // check if file is empty
        if (multipartFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "File is empty. Please select a valid file to upload.");
            return "redirect:/";
        }

        final String fileName = multipartFile.getOriginalFilename();
        uploadService.validateAndUploadFile(multipartFile.getInputStream(), headerType);
        // return success response
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');

        return "redirect:/";

    }

}
