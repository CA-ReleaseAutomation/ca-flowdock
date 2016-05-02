/*******************************************************************************************************
*   Copyright (c) 2014 CA. All rights reserved.
*
*   This software and all information contained therein is confidential and proprietary and shall
*   not be duplicated, used, disclosed or disseminated in any way except as authorized by the
*   applicable license agreement, without the express written permission of CA. All authorized
*   reproductions must be marked with this language.
*
*   EXCEPT AS SET FORTH IN THE APPLICABLE LICENSE AGREEMENT, TO THE EXTENT PERMITTED BY APPLICABLE
*   LAW, CA PROVIDES THIS SOFTWARE WITHOUT WARRANTY OF ANY KIND, INCLUDING WITHOUT LIMITATION, ANY
*   IMPLIED WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. IN NO EVENT WILL CA
*   BE LIABLE TO THE END USER OR ANY THIRD PARTY FOR ANY LOSS OR DAMAGE, DIRECT OR INDIRECT, FROM
*   THE USE OF THIS SOFTWARE, INCLUDING WITHOUT LIMITATION, LOST PROFITS, BUSINESS INTERRUPTION,
*   GOODWILL, OR LOST DATA, EVEN IF CA IS EXPRESSLY ADVISED OF SUCH LOSS OR DAMAGE.
*
********************************************************************************************************/

package com.mycompany.cararallyflowdock;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.Map;
import java.util.HashMap;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.IllegalArgumentException;

import com.nolio.platform.shared.api.ActionDescriptor;
import com.nolio.platform.shared.api.ActionResult;
import com.nolio.platform.shared.api.ParameterDescriptor;
import com.nolio.platform.shared.api.Password;
import com.nolio.platform.shared.datamodel.Action;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.jayway.jsonpath.JsonPath;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import com.ca.nolio.rdk.dto.Operations;
import com.ca.nolio.rdk.dto.VarType;
import com.ca.nolio.rdk.dto.FilterType;
import com.ca.nolio.rdk.dto.InputParam;
import com.ca.nolio.rdk.dto.OutputParam;
import com.ca.nolio.rdk.dto.Authentication;
import com.ca.nolio.rdk.dto.HttpMethod;
import com.ca.nolio.rdk.dto.QOP;
import com.ca.nolio.rdk.dto.CredentialsInfo;
import com.ca.nolio.rdk.dto.exception.ActionException;
import com.ca.nolio.rdk.model.helper.*;
import com.ca.nolio.rdk.model.RestClientManager;
import com.ca.nolio.rdk.dto.HttpRDKResponse;
import com.ca.nolio.rdk.dto.RDKHeader;

import com.ca.nolio.rdk.template.helper.ErrorHelper;
import com.ca.nolio.rdk.template.helper.ReplacementsHelper;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import javax.xml.xpath.XPathConstants;

import net.minidev.json.*;


/**
 *
 * @author Joe Offenberg
 */
@ActionDescriptor(
        name = "Flowdock Flow Message",
        description = "Sends a message to a flow in Flowdock.",
        category="Messaging." )
    public class FlowdockFlowMessage extends Action {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(FlowdockFlowMessage.class);
    private static final String SCAPE_CHAR = "\\";
    private static final String CHAR_SET = "UTF-8";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String ENCODING = "Accept-Encoding";
    private static String WEBSERVICE_OUTPUT = "rdkWebserviceOutput.txt";
    private List<InputParam> inputParameters = null;    
    private List<OutputParam> outputParameters = null;  


                    
    @ParameterDescriptor(
        name = "Username",
        description = "The Username to authenticate",
        out = false,
        in = true,
        nullable = false, // parameter is required
        order = 10,
        defaultValueAsString = ""
        )


            private String username1;
        @ParameterDescriptor(
        name = "Password",
        description = "The Password to use",
        out = false,
        in = true,
        nullable = false, // parameter is required
        order = 20,
        defaultValueAsString = ""
        )

                 private Password password2;
                @ParameterDescriptor(
        name = "Organization",
        description = "Flowdock organization name.",
        out = false,
        in = true,
        nullable = false, // parameter is required
        order = 30,
        defaultValueAsString = ""
        )

                 private String organization3;
                @ParameterDescriptor(
        name = "Flow Name",
        description = "The parameterized name of the target flow.",
        out = false,
        in = true,
        nullable = false, // parameter is required
        order = 40,
        defaultValueAsString = ""
        )

                 private String flowname4;
                @ParameterDescriptor(
        name = "Content",
        description = "Content Description",
        out = false,
        in = true,
        nullable = false, // parameter is required
        order = 50,
        defaultValueAsString = ""
        )

                 private String content5;
            
/*    @ParameterDescriptor(
        name = "Authentication", 
        description = "Authenthication type.", 
        out = false, 
        in = true, 
        nullable = true,
        defaultValueAsString = "Authentication.BASIC",
        order = 60
    )*/
    private Authentication authType = Authentication.BASIC;


    @ParameterDescriptor(
            name = "Response Body",
                    description = "This output parameter holds the standard response of the request.",
                out = true, 
            in = false     
        )
        
    private String responsebody1;

    @ParameterDescriptor(
            name = "Response Headers",
                    description = "The Response Headers from the request.",
                out = true, 
            in = false     
        )
        
    private String responseheaders2;

    @ParameterDescriptor(
            name = "Response Code",
                    description = "The Response Code.",
                out = true, 
            in = false     
        )
        
    private Integer responsecode3;

    @ParameterDescriptor(
            name = "thread_id",
                    description = "thread_id of the new message created.",
                out = true, 
            in = false     
        )
        
    private String thread_id4;


    // Getters and Setters
            public String getusername1() {
        return username1;
    } 
    
    public void setusername1(String username1) {
        this.username1 = username1;
    }
                public Password getpassword2() {
        return password2;
    } 
    
    public void setpassword2(Password password2) {
        this.password2 = password2;
    }
                public String getorganization3() {
        return organization3;
    } 
    
    public void setorganization3(String organization3) {
        this.organization3 = organization3;
    }
                public String getflowname4() {
        return flowname4;
    } 
    
    public void setflowname4(String flowname4) {
        this.flowname4 = flowname4;
    }
                public String getcontent5() {
        return content5;
    } 
    
    public void setcontent5(String content5) {
        this.content5 = content5;
    }
        // Getters and Setters
        public String getresponsebody1() {
        return responsebody1;
    }
    
    public void setresponsebody1(String responsebody1) {
        this.responsebody1 = responsebody1;
    }
            public String getresponseheaders2() {
        return responseheaders2;
    }
    
    public void setresponseheaders2(String responseheaders2) {
        this.responseheaders2 = responseheaders2;
    }
            public Integer getresponsecode3() {
        return responsecode3;
    }
    
    public void setresponsecode3(Integer responsecode3) {
        this.responsecode3 = responsecode3;
    }
            public String getthread_id4() {
        return thread_id4;
    }
    
    public void setthread_id4(String thread_id4) {
        this.thread_id4 = thread_id4;
    }
    
    @Override
    public ActionResult execute() {
        HttpRDKResponse response = new HttpRDKResponse("NO RESPONSE", null, -1);
        try {
            log.debug("A call was received to execute a Restful Action: " + "Flowdock Flow Message");

            String rawRequestValue = "";

            response = new RestClientManager().restCall(HttpMethod.POST, authType, getInput(),
                "https://${Username}:${Password}@api.flowdock.com/flows/${Organization}/${Flow Name}/messages", 30, "", "",
                rawRequestValue, getRequestParameters(), getHeaders());

            log.debug("REST call response: " + response);

                                        } catch (Exception e) {
            response = new HttpRDKResponse("There was an error trying to execute a REST Call Action: '"
                + "Flowdock Flow Message" + "' for Action Pack: "   + "", null, -1);
            log.error(response.getContent(), e);
            return new ActionResult(false, response.getContent() + e.getMessage());
        }

        populateOutputParameters(response);
        return getActionResult();
    }   

    private void populateOutputParameters(HttpRDKResponse output) {
        if (StringUtils.isBlank(output.getContent())) {
            log.debug("Output is empty");
        }
        
                   
                    try {
                Pattern pattern = Pattern.compile("[\\s\\S]*");
                System.out.println("Response Body");
                                    Matcher matcher = pattern.matcher(output.getContent());
                                String stringMatch = null;
                if (matcher.find()) {
                    stringMatch = matcher.group();
                }
        
              responsebody1 = String.valueOf(stringMatch);
            } catch (PatternSyntaxException e) {
                log.error("Caught exception during populating output parameter: " + "Response Body" + ". Bad pattern", e);
            } catch (Exception e) {
                log.error("Caught exception during populating output parameter: " + "Response Body", e);
            }
                               
                    try {
                Pattern pattern = Pattern.compile("[\\s\\S]*");
                System.out.println("Response Headers");
                                    RDKHeader[] headers = output.getHeaders();;
                    JSONArray jsonArray = new JSONArray();
                    for(RDKHeader header : headers){
                        JSONObject obj = new JSONObject();
                        obj.put("value", header.getValue());
                        obj.put("header", header.getHeader());
                        jsonArray.add(obj);
                    }
                    
                    Matcher matcher = pattern.matcher(jsonArray.toJSONString());
                                String stringMatch = null;
                if (matcher.find()) {
                    stringMatch = matcher.group();
                }
        
              responseheaders2 = String.valueOf(stringMatch);
            } catch (PatternSyntaxException e) {
                log.error("Caught exception during populating output parameter: " + "Response Headers" + ". Bad pattern", e);
            } catch (Exception e) {
                log.error("Caught exception during populating output parameter: " + "Response Headers", e);
            }
                               
                    log.debug("Response Code: " + output.getResponseCode());
            responsecode3 = output.getResponseCode();
                               
                    try {
                                                            Object objectMatch = JsonPath.read(output.getContent(), "$.thread_id");
                                                    
                if (objectMatch != null) {
                                            String stringMatch = objectMatch.toString();
                                                                                            thread_id4 = String.valueOf(stringMatch);
                                                            }
                
               
            } catch (Exception e) {
                log.error("Caught exception during JsonPath matching", e);
            }
                }
    
    private ActionResult getActionResult() {
            ActionResult actionResult = null;
        try { 
            actionResult = new ActionResult(true, ReplacementsHelper.replaceOutputParameters( ReplacementsHelper.replaceInputParameters( "Execution succeeded ${Response Body}", getInput() ), getOutput() ) );
        } catch (Exception e) {
            log.error("There was an error on the replacement of parameters", e);
        }
        

        return actionResult;
    }

    private void createOutputLocationFile(String outputLocation, String content) throws ActionException {
        File outputDirs = new File(outputLocation);
        if (!outputDirs.exists()) {
            try {
                outputDirs.mkdirs();
            } catch (SecurityException se) {
                log.error("Caught security exception while creating output location: " + outputLocation);
                throw new ActionException("Security exception while creating output location: " + se.getMessage(), se);
            }
        }
        
        String fileName = outputLocation + File.separator + WEBSERVICE_OUTPUT;
        try {
            FileWriter fw = new FileWriter(fileName);
            fw.write(content);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            log.error("Caught IO exception during writing to file");
            throw new ActionException("IO exception during writing to file: " + e.getMessage(), e);
        }
    }   
    
    private List<InputParam> getInput() {
                    inputParameters = new ArrayList<InputParam>();
            InputParam p = null;
                    p = new InputParam();
            p.setName("Username");
                                    p.setVariableName("username");
            p.setPrefix("");
            p.setType(VarType.String);
                                                                     p.setValue(username1);
                                             
            inputParameters.add(p);     
                    p = new InputParam();
            p.setName("Password");
                                    p.setVariableName("password");
            p.setPrefix("");
            p.setType(VarType.Password);
                                                                                     if(password2 != null) {
                    p.setValue(password2.toString());
                }
                                             
            inputParameters.add(p);     
                    p = new InputParam();
            p.setName("Organization");
                                    p.setVariableName("organization");
            p.setPrefix("");
            p.setType(VarType.String);
                                                                     p.setValue(organization3);
                                             
            inputParameters.add(p);     
                    p = new InputParam();
            p.setName("Flow Name");
                                    p.setVariableName("flowname");
            p.setPrefix("");
            p.setType(VarType.String);
                                                                     p.setValue(flowname4);
                                             
            inputParameters.add(p);     
                    p = new InputParam();
            p.setName("Content");
                                    p.setVariableName("content");
            p.setPrefix("");
            p.setType(VarType.String);
                                                                     p.setValue(content5);
                                             
            inputParameters.add(p);     
                
        
        
        return inputParameters;
    }   

    private List<OutputParam> getOutput() {
            if (outputParameters == null) {
            outputParameters = new ArrayList<OutputParam>();
            OutputParam p = null;
                    p = new OutputParam();
            p.setName("Response Body");
                                    p.setVariableName("responsebody");
            p.setType(VarType.String);
            
                            p.setFilterType(FilterType.REGEX);
                        
                                                                     p.setFilterValue( responsebody1 );
                                     
            outputParameters.add(p);                    
                    p = new OutputParam();
            p.setName("Response Headers");
                                    p.setVariableName("responseheaders");
            p.setType(VarType.String);
            
                            p.setFilterType(FilterType.REGEX);
                        
                                                                     p.setFilterValue( responseheaders2 );
                                     
            outputParameters.add(p);                    
                    p = new OutputParam();
            p.setName("Response Code");
                                    p.setVariableName("responsecode");
            p.setType(VarType.Integer);
            
                            p.setFilterType(FilterType.REGEX);
                        
                                                                                     if(responsecode3 != null) {
                    p.setFilterValue( responsecode3.toString() );
                }
                                     
            outputParameters.add(p);                    
                    p = new OutputParam();
            p.setName("thread_id");
                                    p.setVariableName("thread_id");
            p.setType(VarType.String);
            
                            p.setFilterType(FilterType.JSON);
                        
                                                                     p.setFilterValue( thread_id4 );
                                     
            outputParameters.add(p);                    
                }
            return outputParameters;
    }

    public HashMap getRequestParameters() {
        HashMap reqParams = null;
                reqParams = new HashMap<String, String>();
        String paramKey = null;
        String paramValue = null;
                    paramKey = ReplacementsHelper.replaceInputParameters("event", getInput());
                        paramValue = ReplacementsHelper.replaceInputParameters("message", getInput());
            reqParams.put(paramKey, paramValue);
                    paramKey = ReplacementsHelper.replaceInputParameters("content", getInput());
                        paramValue = ReplacementsHelper.replaceInputParameters("${Content}", getInput());
            reqParams.put(paramKey, paramValue);
                
        return reqParams;
    }

    public HashMap getHeaders() {
        HashMap headers = null;
        
        return headers;
    }
}