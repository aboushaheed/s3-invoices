package com.billing.poc.interfaces.http;

import com.amazonaws.AmazonClientException;
import com.billing.poc.domaine.invoice.InvoiceService;
import com.billing.poc.infra.aws.s3.DownloadService;
import com.billing.poc.infra.aws.s3.Pair;
import com.billing.poc.infra.aws.s3.UploadService;
import com.billing.poc.interfaces.http.exception.SearchInvalidFormatDataException;
import com.billing.poc.interfaces.http.model.response.InvoiceDTO;
import com.billing.poc.interfaces.http.model.request.CreateInvoiceRequest;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("/invoices")
@Api("invoice endpoint")
public class InvoiceController {

    private UploadService uploadService;
    private InvoiceService invoiceService;
    private DownloadService downloadService;

    private final static boolean ADD_CONTENT_ATTACHMENT = false;
    @Autowired
    public InvoiceController(UploadService uploadService,
                             InvoiceService invoiceService, DownloadService downloadService) {
        this.uploadService = uploadService;
        this.invoiceService = invoiceService;
        this.downloadService = downloadService;
    }

    @ApiOperation(value="Add a file to S3", nickname="addFile", notes="This service upload the file")
    @RequestMapping(path="/files/{fileName}",  consumes ="multipart/form-data", method= RequestMethod.POST, produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "name of the file to upload", required = true, dataType = "string", paramType = "path")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request Accepted"),
            @ApiResponse(code = 400, message = "upload did not perform"),
    })
    public void uploadFile(
            @RequestPart(required = true) MultipartFile file, @PathVariable String fileName) {
        uploadService.upload(file, fileName);
    }


    @ApiOperation(value="Store metadata of file", nickname="storeData", notes="This service store metadata")
    @RequestMapping(path="/",  consumes ="application/json", method= RequestMethod.POST, produces="application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request Accepted"),
            @ApiResponse(code = 400, message = "upload did not perform"),
    })
    public InvoiceDTO storeData(
            @RequestBody @Valid CreateInvoiceRequest createInvoiceRequest) {
        return invoiceService.saveData(createInvoiceRequest);
    }

    @ApiOperation(value="download a file stored into S3", nickname="downloadFile", notes="This service retrieve a document stored into S3")
    @RequestMapping(path="/files/{fileName}", produces="*", method= RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "The file name", required = true, dataType = "string", paramType = "path")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request Accepted"),
            @ApiResponse(code = 400, message = "values in args are incorrect or error occured during processing"),
            @ApiResponse(code = 404, message = "No document matches request"),
    })
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        try {
            Pair<byte[], Map<String, Object>> fileDownloaded = downloadService.downloadFile(fileName);

            final HttpHeaders responseHeaders = getDownloadHttpHeader(fileName);
            for(Map.Entry<String, Object> entry : fileDownloaded.getSecond().entrySet()) {
                if(!responseHeaders.containsKey(entry.getKey()))
                    responseHeaders.add(entry.getKey(), entry.getValue().toString());
            }
            return new ResponseEntity<byte[]>(fileDownloaded.getFirst(), responseHeaders, HttpStatus.OK);
        } catch (AmazonClientException | InterruptedException | IOException e) {
            throw new SearchInvalidFormatDataException("error during download from s3", e);
        }

    }


    private HttpHeaders getDownloadHttpHeader(String fileName) throws IOException {
        final HttpHeaders responseHeaders = new HttpHeaders();
        if(ADD_CONTENT_ATTACHMENT)
            responseHeaders.add("content-disposition", "attachment; filename=" + fileName);
        responseHeaders.add("content-type", Files.probeContentType(Paths.get(fileName)));
        return responseHeaders;
    }
}
