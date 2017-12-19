
package com.ibm.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NewClientUI {

    DriverIE driver;
    static BrowserIE browser;
    public NewClientPo po;
    public I_UIData data;
    String xstr;
    static String commit;
    static WebDriverUtil webutils;
    static int photocount = 3;

    static String langpurchased;
    static String languilocals;
    static String langclientbase;
    static String internat;
    static String deployment;
    static String env;
    static String uniqueness;
    // static String clientname;
    static String db;
    static String email;
    static String ack;

    String url_newclient;

    // static ValidationClientData validateflds ;
    static int retry_counter = 0;
    static String baselib;
    static String clientId;
    static String clientId_post;
    static String clientType;
    static String icn;
    static String tsEnablement;
    static String tsClientIdentifier;
    static String prefix;
    static String clientid;
    static Boolean production = false;

    public NewClientUI() {
        super();

        po = new NewClientPo();

        webutils = new WebDriverUtil();
        webutils.setDir("d://workspace//imgs");
    }

    /**
     * Main method that is called to run the New Client creation process.
     * 
     * The code in main calls the runNewClient method (which actually performs
     * the creation) and catches any exceptions.
     * 
     * If an exception is caught any processes are cleaned up and the code
     * exited with a value of -1.
     * 
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("NewClient with exception handling 2");
        int exitValue = 0;

        try {
            // Clean up anything that might have been left from a previous run.
            exitProcesses();

            // Run the code to create the new client.
            runNewClient(args);

            // Cleanup performed in the finally block.
        } catch (NewClientException ex) {
            // If we get an exception we clean up.
            ex.printStackTrace();
            System.out.println("Process failed to create new client. Cleaning up...");
            System.out.println(ex.getMessage());
            exitValue = -1;
        } catch (LoginException ex) {
            // If we get an exception we clean up.
            ex.printStackTrace();
            System.out.println("Got an unexpected exception in Login. Cleaning up...");
            System.out.println(ex.getMessage());
            exitValue = -1;
        } catch (MenuHandlerException ex) {
            ex.printStackTrace();
            System.out.println("Got an unexpected exception accessing menu. Cleaning up...");
            System.out.println(ex.getMessage());
            exitValue = -1;
        } catch (Exception ex) {
            // If we get an exception we clean up.
            System.out.println("Got an unexpected exception in SiteCreation. Cleaning up...");
            ex.printStackTrace();
            exitValue = -1;
        } finally {
            exitProcesses();
        }

        String exitString = "";

        // Prepare a status message to output.
        if (exitValue == 0) {
            exitString = "Success";
        } else {
            exitString = "Failed";
        }

        System.out.println("Exiting NewClient Creation - ".concat(exitString));

        // Return 0 for success and -1 for failure.
        System.exit(exitValue);
    }

    /**
     * Private static method that holds all the code to create the new client.
     * 
     * @param args
     * @throws Exception
     */
    private static void runNewClient(String[] args) throws Exception {
        String clistr = null;

        System.out.println("Version0.7\n\n");
        System.out.println("* **********  API. Updates  **********************");
        System.out.println("* UI Timing");
        System.out.println("* **********  Site Creation  Updates  **********************");
        System.out.println("* Clinet ID");
        System.out.println("* Full Lang Pack");
        System.out.println("* Client Type External / xI");
        System.out.println("* Direct Client Hanem Profision");
        System.out.println("* TS Enablement");
        System.out.println("* TS Client Identifier");
        System.out.println("");
        System.out.println("* Ignoring Protected mode setting");
        System.out.println("");

        clistr = "";
        for (String acli : args) {
            clistr += " \"";
            clistr += acli;
            clistr += "\" ";

            System.out.println("INF : Program Input (" + acli + ")");
        }
        System.out.println("INF : Cli String (" + clistr + ")");

        System.out.println("\n\n\n");

        System.out.println("INF : Assigning to internal data structure\n\n\n");
        int i = 0;
        String syspropsdir = args[i++].split("->")[1];
        deployment = args[i++].split("->")[1].toLowerCase();
        uniqueness = args[i++].split("->")[1].toLowerCase();
        commit = args[i++].split("->")[1]; // Can test both modes of logic but
                                           // not commit to DB
        env = args[i++].split("->")[1]; // QA environent client
                                        // list is different to
                                        // Prduction list.
        String hostname = args[i++].split("->")[1];

        String username = args[i++].split("->")[1];
        String pass = args[i++].split("->")[1];
        clientid = args[i++].split("->")[1];

        // String clientname=args[i++].split("->")[1]+newclientUI.getXstr();
        String clientname = args[i++].split("->")[1];
        System.out.println("CLient " + clientname);
        // String nwpath=args[i++].split("->")[1]+newclientUI.getXstr();
        String nwpath = args[i++].split("->")[1];
        db = args[i++].split("->")[1];

        // String prefix=args[i++].split("->")[1]+newclientUI.getXstr();
        String prefix = args[i++].split("->")[1];

        ack = args[i++].split("->")[1];
        String ackcode = args[i++].split("->")[1];

        email = args[i++].split("->")[1];

        // Lang Support
        internat = args[i++].split("->")[1];
        langpurchased = args[i++].split("->")[1];
        languilocals = args[i++].split("->")[1];
        langclientbase = args[i++].split("->")[1];
        clientType = args[i++].split("->")[1];
        try {
            baselib = args[i++].split("->")[1];
        } catch (Exception e) {
            baselib = "";
        }

        clientId_post = args[i++].split("->")[1].toLowerCase();
        icn = args[i++].split("->")[1].toLowerCase();

        tsEnablement = args[i++].split("->")[1].toLowerCase();
        tsClientIdentifier = args[i++].split("->")[1].toLowerCase();

        System.out.println("INF : Finishing Preprocesssing Steps");
        System.out.println("\n\n");

        System.out.println("\n***** Code -> WorkBench (Client) Command Start *****");
        System.out.println("INF : Command Line Information ");
        System.out.println("INF : Project Root Dir  (" + syspropsdir + ")");
        System.out.println("INF : Deploy Geo  (" + deployment + ")");
        System.out.println("INF : Unique  (" + uniqueness + ")");
        System.out.println("INF : Commit  (" + commit + ")");
        System.out.println("INF : Environment  (" + env + ")");
        System.out.println("INF : Target Host  (" + hostname + ")");
        System.out.println("INF : User  (" + username + ")");
        // System.out.println("INF : Pass  ("+pass+")");
        System.out.println("INF : Client ID  (" + clientid + ")");
        System.out.println("INF : Client Name  (" + clientname + ")");
        System.out.println("INF : NW Path  (" + nwpath + ")");
        System.out.println("INF : DB  (" + db + ")");
        System.out.println("INF : Prefix  (" + prefix + ")");
        System.out.println("INF : Acknowledgement  (" + ack + ")");
        System.out.println("INF : Acknowledgement Code  (" + ackcode + ")");
        System.out.println("INF : Email  (" + email + ")");
        System.out.println("INF : Internamtion Settings   (" + internat + ")");
        System.out.println("INF : Lang Purchased  (" + langpurchased + ")");
        System.out.println("INF : Lang UI  (" + languilocals + ")");
        System.out.println("INF : Lang ClientBase  (" + langclientbase + ")");

        System.out.println("INF : Client Support Type  (" + clientType + ")");
        System.out.println("INF : Base Lib  (" + baselib + ")");
        System.out.println("INF : Post Client ID  (" + clientId_post + ")");
        System.out.println("INF : ICN  (" + icn + ")");
        System.out.println("\n");
        System.out.println("INF : TS Enablement  (" + tsEnablement + ")");
        System.out.println("INF : TS ClientIdentifier  (" + tsClientIdentifier + ")");

        url_services = "https://" + hostname + "/BRAdmin/login/asp/login.asp";
        System.out.println("INF : WB Login  (" + url_services + ")");
        System.out.println("\n");

        String userName = "input[name='txtusername']";
        WebElement we = browser.findElementByCssSelector(userName);
        we.sendKeys(username);

        String clientIdent = "input[name='txtclientix']";
        we = browser.findElementByCssSelector(clientIdent);
        we.clear();
        we.sendKeys("BrassRing.com");

        String password = "input[name='txtpassword']";
        we = browser.findElementByCssSelector(password);
        we.sendKeys(pass);

        String bLogin = "input[name='btnLogin']";
        we = browser.findElementByCssSelector(bLogin);
        we.click();

        System.out.println("********************* Ending Authentication *************** \n\n");
        System.out.println("********************* Start Environment/Client Handler *************** \n\n");

        System.out.println("Required environment is " + env);
        if (env.equalsIgnoreCase("Production (Real!)")) {
            production = true;
        }

        webutils.holdup(3);
        browser.switchTo().frame(browser.findElementByCssSelector("#nav"));
        String envirnoment = "select[name='sltEnv']";
        we = browser.findElementByCssSelector(envirnoment);

        Select select = new Select(we);
        WebElement element = select.getFirstSelectedOption();
        System.out.println("Default environment is " + element.getText());
        boolean envFound = false;
        if (!element.getText().equals(env)) {
            System.out.println("Changing environment from " + element.getText() + " to " + env);
            select = new Select(we);
            List<WebElement> allOptions = select.getOptions();
            System.out.println("size = " + allOptions.size());
            for (WebElement weEnvironment : allOptions) {
                if (weEnvironment.getText().equals(env)) {
                    System.out.println("Setting Environemnt To " + env);
                    weEnvironment.click();
                    envFound = true;
                    break;
                }
            }

            if (!envFound) {
                throw (new NewClientException("Could not find " + env + " as an option"));
            }

            browser.switchTo().defaultContent();
            browser.switchTo().frame(browser.findElementByCssSelector("#main"));

            System.out.println("Lets wait for a min for page to load");
            webutils.holdup(60);
            System.out.println("Ok - min over lets resume");
            // Wait for "Welcome to Workbench" to re-appear
            // WebDriverWait waitForPageTitle = new WebDriverWait(browser, 50);
            // waitForPageTitle.until(ExpectedConditions.visibilityOfElementLocated(By
            // .cssSelector("body > center > table > tbody > tr > td")));
            // webutils.holdup(5);
            // WebDriverWait waitFoNavBar = new WebDriverWait(browser, 30);
            // waitFoNavBar.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#nav")));
            browser.switchTo().frame(browser.findElementByCssSelector("#nav"));

        } else {
            System.out.println("Default environment is required therefore, no action needed");
        }

        System.out.println("********************* End Environment/Client Handler *************** \n\n");

        /*
         * Menu Time
         */
        System.out.println("********************* Start Menu Handler *************** \n\n");

        String admin = "div[id='el2']";
        we = browser.findElementByCssSelector(admin);
        we.click();

        browser.switchTo().defaultContent();
        browser.switchTo().frame(browser.findElementByCssSelector("#main"));

        // String manageClients = "div[id='el86']";
        // we = browser.findElementByCssSelector(manageClients);
        // we.click();

        String manageClients = "Manage clients";
        we = browser.findElement((By.linkText(manageClients)));
        we.click();

        String addNewClient = "#divAction > table > tbody > tr:nth-child(4) > td:nth-child(1) > span";
        we = browser.findElementByCssSelector(addNewClient);
        we.click();

        System.out.println("\n\nINF : Intent -> Switchig Back To Default Content\n\n\n");
        if (browser.swithch2oldWin()) {
            System.out.println("INF : Switching back to main window - REACHED");
        } else {
            // System.out.println("ERR : Couldnt switching back to main window.");
            throw (new NewClientException("Could not switch back to main window"));
            // exitProcesses();
            // System.exit(-1);
        }

        // newclientUI.logOff(mhndlr, newclientUI);
        System.out.println("INF : Exit Client Creation");
        // System.exit(0);
    }

    public void logOff(MenuHandlerUI mhndlr, NewClientUI newclientUI) throws Exception {

        if (mhndlr.po.clickLogoff()) {

            System.out.println("INF : Log off successful , Proceeding to close login/homepage window");

            if (webutils.waitonTitle("Log in to Workbench", 120)) {
                System.out.println("INF : Waiting On Login Page - Clean Logout");
                newclientUI.getDriver().quit();
            } else {
                System.out.println("WRN : Didnt Get  A Clean Logout - No Login Page");
                newclientUI.getDriver().quit();
            }

            // newclientUI.getDriver().quit();
        } else {
            System.out.println("ERR : Log off unsuccessful , leaving  parent  window open for inspection ");
            newclientUI.getDriver().quit();
        }
        // newclientUI.getDriver().quit();

    }

    @Override
    public I_UIAutomation doOnce() throws Exception {

        doDirect();
        doItMyWay();
        return this;

    }

    public void doItMyWay() throws Exception {
        System.out.println("Doing it My Way");
        WebElement we;

        String clientsName = "input[name='txtclientname']";
        we = browser.findElementByCssSelector(clientsName);
        we.clear();
        System.out.println("Client name is " + po.getClientname_di());
        we.sendKeys(po.getClientname_di());

        String networkPath = "input[name='txtnetwork']";
        we = browser.findElementByCssSelector(networkPath);
        we.clear();
        we.sendKeys(po.getClientname_di());

        String DBname = "select[name='sltverity']";
        we = browser.findElementByCssSelector(DBname);
        Select select = new Select(we);
        List<WebElement> allOptions = select.getOptions();
        System.out.println("Size = " + allOptions.size());

        for (WebElement we1 : allOptions) {
            if (we1.getText().startsWith(db.toUpperCase() + "_")) {
                System.out.println("INF : Setting DB Name To " + we1.getText());
                we1.click();
                break;
            }
        }

        String baseLibrary = "select[name='sltlibrary']";
        we = browser.findElementByCssSelector(baseLibrary);
        select = new Select(we);
        allOptions = select.getOptions();
        System.out.println("Size = " + allOptions.size());

        for (WebElement we2 : allOptions) {
            if (we2.getText().equals(baselib)) {
                System.out.println("INF : Setting Library To " + we2.getText());
                we2.click();
                break;
            }
        }

        String pref = "input[name='txtprefix']";
        we = browser.findElementByCssSelector(pref);
        we.clear();
        we.sendKeys(po.getPrefix_di());

        if (ack.equalsIgnoreCase("yes")) {
            String acknowledgementsYes = "input[name='optacknowledgements'][value='Y']";
            we = browser.findElementByCssSelector(acknowledgementsYes);
            we.click();
        } else {
            String acknowledgementsNo = "input[name='optacknowledgements'][value='N']";
            we = browser.findElementByCssSelector(acknowledgementsNo);
            we.click();
        }

        String userEmail = "input[name='txthsemail']";
        we = browser.findElementByCssSelector(userEmail);
        we.clear();
        we.sendKeys(email);

        if (tsEnablement.equalsIgnoreCase("yes")) {
            System.out.println("Enable");
            String tsEnablementYes = "input[name='optenablets'][value='1']";
            we = browser.findElementByCssSelector(tsEnablementYes);
            we.click();
        }
        webutils.holdup(1);
        String tsEnablementText = "input[name='txtidentifier']";
        we = browser.findElementByCssSelector(tsEnablementText);
        we.sendKeys(po.getClientname_di());

        if (internat.equals("INT_ON")) {

            String international = "input[name='int_international'][value='1']";
            we = browser.findElementByCssSelector(international);
            we.click();

            System.out.println("Client Language Selection is " + langpurchased);

            InputStream is = NewClientUI.class.getResourceAsStream("language.xml");

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            XPathFactory xpf = XPathFactory.newInstance();
            XPath xpath = xpf.newXPath();
            XPathExpression xpe = xpath.compile("//languages[@language=\'" + langpurchased + "\']");

            NodeList result = (NodeList) xpe.evaluate(doc, XPathConstants.NODESET);
            Node nNode = result.item(0);
            Element eElement = (Element) nNode;

            if (result.getLength() > 0) {
                System.out.println("Selected language from " + langpurchased + " has been found.");
                System.out.println("Language is: " + eElement.getAttribute("language"));
                System.out.println("Language ID: " + eElement.getAttribute("languageid"));
                System.out.println("locale ID is: " + eElement.getAttribute("localeid"));

                // String language = eElement.getAttribute("language");
                String language = "input[name='int_purchasedlanguages'][languageName="
                        + eElement.getAttribute("language") + "]";
                we = browser.findElementByCssSelector(language);
                we.click();

                String uiLocale = "input[name='int_clientpurchasedlocale'][value=" + eElement.getAttribute("localeid")
                        + "]";
                we = browser.findElementByCssSelector(uiLocale);
                we.click();

                String clientBase = "input[name='int_clientlocale'][value=" + eElement.getAttribute("localeid") + "]";
                we = browser.findElementByCssSelector(clientBase);
                we.click();

            } else {
                webutils.takeSnapshots(photocount, 1, "prefix_lang");
                throw (new NewClientException(
                        "Error: Selected language of " + langpurchased + " has not, been found."));
            }

            if (production) {
                System.out.println("production");
                String clientID = "input[name='txtoptionalclientid']";
                we = browser.findElementByCssSelector(clientID);
                we.clear();
                we.sendKeys(clientId_post);
            }

            String ibmNumber = "input[name='txticn']";
            we = browser.findElementByCssSelector(ibmNumber);
            we.clear();
            we.sendKeys(icn);

            if (clientType.equalsIgnoreCase("Internal Kenexa")) {
                String clientType = "input[name='optsupport'][value='1']";
                we = browser.findElementByCssSelector(clientType);
                we.click();
            } else {
                String clientType = "input[name='optsupport'][value='0']";
                we = browser.findElementByCssSelector(clientType);
                we.click();
            }

            // String cancelButton = "input[name='btncancel']";
            // we = browser.findElementByCssSelector(cancelButton);
            // we.click();

            String saveButton = "input[name='btnsave']";
            we = browser.findElementByCssSelector(saveButton);
            we.click();

            browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            waitClientDetail("Client details set", 300, 60);

            webutils.holdup(20);

            System.out.println("\n\nINF : Intent -> Switchig Back To Default Content\n\n\n");
            if (browser.swithch2oldWin()) {
                System.out.println("INF : Switching back to main window - REACHED");
            } else {
                System.out.println("ERR : Switching back to main window.");
                throw (new NewClientException("Could not switch back to main window"));
                // exitProcesses();
                // System.exit(-1);
            }

            browser.switchTo().frame(browser.findElementByCssSelector("#nav"));

            String logOffButton = "span[title='Log Off']";
            we = browser.findElementByCssSelector(logOffButton);
            we.click();

            Date date2 = new Date();
            System.out.println("Ending " + date2.toString());
        }
    }

    public static void exitProcesses() {

        System.out.println("Closing any running processes");

        List<String> myList = new ArrayList<String>();

        myList.add("iexplore.exe");
        myList.add("IEDriverServer.exe");

        for (int i = 0; i < myList.size(); i++) {

            String command = "cmd /c tasklist /svc | findstr " + myList.get(i);
            try {
                Process p = Runtime.getRuntime().exec(command);
                while (p.getInputStream().read() != -1) {
                }

                if (p.waitFor() == 0) {
                    System.out.println("Process: " + myList.get(i) + " is running, Ending process.");
                    Runtime.getRuntime().exec("taskkill /F /IM " + myList.get(i));
                } else {
                    System.out.println("Process: " + myList.get(i) + " is not running, Proceeding with automation.");
                }

            } catch (IOException | InterruptedException e1) {
                e1.printStackTrace();
            }
        }

    }

    public void persistClient2File(String clientname, String prefix) {
        System.out.println("Are we calling this");
        getPropscfg().setDiFile("c:/workspace/cfg/" + ((NewClient) getData()).getClientname() + ".props");
        getPropscfg().getProps().put("WB_Prefix", prefix);
        getPropscfg().getProps().put("ClientName", ((NewClient) getData()).getClientname());
        getPropscfg().writeProps();
    }

    public void persistClientTs2File(String hostname, String clientId) {

    }

    public String persistReadProp(String clientProp) {

        // WB_NewClientId
        return getPropscfg().readProp(clientProp);

    }

    public void doDirect() throws NewClientException {

        System.out.println("INF :  Direct Provisioning ");
        setXstr(getRandomValue());

        po.setClientname_id("name");
        po.setClientname_val("txtclientname");
        po.setClientname_di(((NewClient) getData()).getClientname());
        // po.ClientNameDatain();

        // NW Path txtnetwork(input)
        po.setNwpath_id("name");
        po.setNwpath_val("txtnetwork");
        po.setNwpath_di(((NewClient) getData()).getClientname()); // Same as
                                                                  // ClientName
                                                                  // po.NWPathDatain();

        po.setPrefix_id("name");
        po.setPrefix_val("txtprefix"); // Special Case
        po.setPrefix_di(((NewClient) getData()).getPrefix());
        // po.setPrefix_di(prefix);
        // po.PrefixDatain();

        persistClient2File(((NewClient) getData()).getClientname(), getXstr());

    }

    public void doUnique() throws NewClientException {

        System.out.println("INF : Randon String  (" + getXstr() + ")");

        // setXstr(getRandomValue());
        System.out.println("INF : Randon String  (" + getXstr() + ")");

        // Client ID txtclientname(input)
        // String getData().clientname=args[i++];
        po.setClientname_id("name");
        po.setClientname_val("txtclientname");
        po.setClientname_di(((NewClient) getData()).getClientname() + getXstr());
        po.ClientNameDatain();

        // NW Path txtnetwork(input)
        // String getData().nwpath=args[i++];
        po.setNwpath_id("name");
        po.setNwpath_val("txtnetwork");
        po.setNwpath_di(((NewClient) getData()).getClientname() + getXstr());
        po.NWPathDatain();

        po.setPrefix_id("name");
        po.setPrefix_val("txtprefix"); // Special Case
        po.setPrefix_di(getXstr());
        po.PrefixDatain();

    }

    public void doBaseLib() throws Exception {

        po.BaseClientLibrary(baselib);

        doAck();

    }

    public void doAck() throws Exception {
        // <TD style="FONT-SIZE: 12pt; FONT-FAMILY: Verdana; FONT-WEIGHT: bold;
        // xbackground: pink" width="100%" align=center>Prefix already
        // exists.</TD>
        po.setAck_id("name");
        po.setAck_val("optacknowledgements"); // Special Case
        po.setAck_di(((NewClient) getData()).getAck());
        po.AckDatain();

        // txtackinsert
        if (((NewClient) getData()).getAck().toLowerCase().contains("yes")) {
            // Worry about code
            po.setAckcode_id("name");
            po.setAckcode_val("txtackinsert"); // Special Case
            po.setAckcode_di(((NewClient) getData()).getAckcode());
            po.AckCodeDatain();
        }

        doRest();
    }

    public void doRest() throws Exception {

        // *Search Engine (DSN/DBname/Server) sltverity(select).click , option
        // value=EXPRESS14_1
        // String getData().email=args[i++];
        po.setDbname_id("value");
        po.setDbname_val(((NewClient) getData()).getDb()); // Special Case
        po.setDbname_di(((NewClient) getData()).getDb());
        po.DBNameDatain();

        // HS Email txthsemail (input)
        // String getData().db=args[i++];
        po.setHsemail_id("name");
        po.setHsemail_val("txthsemail");
        po.setHsemail_di(((NewClient) getData()).getEmail());
        po.HSEmailDatain();

        doLanguages();
    }

    public void doLanguages() throws Exception {
        System.out.println("\n*************************************");
        System.out.println("INF : International Settings....");
        System.out.println("*************************************\n");

        po.setLangPurchased_id("languageName");
        po.setLangPurchased_di("cb");

        po.setLangLocals_id("localeName");
        po.setLangLocals_di("cb");

        po.setLangClientBaseLocals_id("value");
        po.setLangClientBaseLocals_di("cb");

        // Turn On Multip Lang

        // Internation Multip On

        if (internat.contains("yes") || internat.contains("on")) {

            System.out.println("INF: Internation Setting On");

            //
            po.setLangInternat_id("name");
            po.setLangInternat_di("1");
            po.setLangInternat_val("int_international");
            po.LangInternatDataIn();

            System.out.println("\n\nINF: Do we have more than one language to support?");

            List<String> langPurchaseditems = Arrays.asList(langpurchased.split(","));

            if (langPurchaseditems.size() > 0) {
                System.out.println("INF: Yes Multiple Languages To Support\n");

                for (String mulilang : langPurchaseditems) {
                    System.out.println("INF: Next Purchased Lang Up (" + mulilang + ")");
                    doLanguageSelectionPurchased(mulilang);
                }

            } else {
                throw (new NewClientException("Multiple Languages Support Turned On, but No languages to support"));
                // System.out.println("ERR: Multiple Languages Support Turned On, but No languages to support");
                // System.out.println("INF: (" + langpurchased + ")");
                // exitProcesses();
                // System.exit(-1);
            }

        } else {

            System.out.println("INF: Internation Setting Off");
            doLanguageSelectionPurchased(langpurchased);
        }

        doClientExtras();
        // If Production Mode
        // Apply Client ID

    }

    private void doClientExtras() throws Exception {

        // if (env.contains("prod"))
        doClientID();
        doClientType();

        // txtoptionalclientid
    }

    //
    private void doICN() throws NewClientException {

        System.out.println("\n ***** Start : ICN Option ***** ");

        po.setIcn_id("name");
        po.setIcn_val("txticn");
        po.setIcn_di(icn);

        if (icn.contains("n/a") == true) {
            System.out.println("INF : ICN  Set to n/a (" + icn + ")");
        } else {
            po.IcnDataIn();

        }

        System.out.println("\n ***** Finish : ICN Option ***** ");
    }

    private void doTsClientEnablement() throws NewClientException {

        System.out.println("\n ***** Start : doTsClientEnablement Option ***** ");

        po.setTsClientEnablement_id("name");
        po.setTsClientEnablement_val("optenablets");
        po.setTsClientEnablement_di(tsEnablement);

        if (tsEnablement.contains("n/a") == true) {
            System.out.println("INF : tsEnablement Set to n/a (" + tsEnablement + ")");
        } else {
            po.TsClientEnablementDataIn();
        }

        System.out.println("\n ***** Finish : doTsClientEnablement Option ***** ");
    }

    private void doTsClientIdentifier() throws NewClientException {

        System.out.println("\n ***** Start : doTsClientIdentifier Option ***** ");

        po.setTsClientIdentifier_id("name");
        po.setTsClientIdentifier_val("txtidentifier");
        po.setTsClientIdentifier_di(tsClientIdentifier);

        if (tsClientIdentifier.contains("n/a") == true) {
            System.out.println("INF : tsClientIdentifier Set to n/a (" + tsClientIdentifier + ")");
        } else {
            po.TsClientIdentifierDataIn();
        }

        System.out.println("\n ***** Finish : doTsClientIdentifier Option ***** ");

    }

    // class="formBodyDetail"
    private void doNewClientId() throws NewClientException {

        System.out.println("\n ***** Start : New Client Id  ***** ");

        po.setNewicn_id("class");
        po.setNewicn_val("formBodyDetail");
        po.setNewicn_di("ClientID:");

        po.NewClientIdDataIn();

        System.out.println("\n ***** Finish : New Client Id ***** ");

        doSaveIdFile(po);

    }

    private void doSaveIdFile(NewClientPo po) {

        String test = po.getNewicn_payload();
        System.out.println("INF : Client ID before it is stripped and stored in Text File " + test);

        Pattern p = Pattern.compile("\\((.*?)\\)");
        Matcher m = p.matcher(test);
        while (m.find()) {
            clientId_post += m.group();
        }

        clientId_post = clientId_post.replaceAll("[^\\d]", "");
        System.out.println("INF : Client ID stripped and stored in Text File " + clientId_post);
        System.out.println("What about this one");
        getPropscfg().setDiFile("c:/workspace/cfg/" + ((NewClient) getData()).getClientname() + ".props");
        getPropscfg().getProps().put("WB_NewClientId", clientId_post);
        getPropscfg().writeProps();

    }

    private void doClientType() throws Exception {

        System.out.println("\n ***** Start :Updating Client Type Option ***** ");
        po.setClienttype_id("name");
        po.setClienttype_val("optsupport");

        if (clientType.contentEquals("external")) {
            po.setClienttype_di("1");
        } else if (clientType.contentEquals("internal kenexa")) {
            po.setClienttype_di("2");
        } else {
            throw (new NewClientException("Client Support Type Not Recognised (" + clientType + ")"));
            // System.out.println("ERR :Client Support Type Not Recognised (" +
            // clientType + ")");
            // exitProcesses();
            // System.exit(-1);
        }

        po.ClientTypeDataIn();

        System.out.println("***** Finish :Updating Client Type Option ***** ");

    }

    private void doClientID() throws NewClientException {

        System.out.println("\n ***** Start :Updating Client ID  ***** ");
        po.setClientid_id("name");
        po.setClientid_val("txtoptionalclientid");
        po.setClientid_di(clientId_post);

        // If Given Use else read from file
        if (env.contains("prod") == true) {
            System.out.println("INF : Reading ID from File ...");
            clientId_post = persistReadProp("WB_NewClientId");
            po.setClientid_di(clientId_post);

            // Spit out
            po.ClientIdDataIn();

        } else {
            // Direct
            System.out.println("INF : Reading ID from CLI ...");
            po.ClientIdDataIn();
        }

        System.out.println("***** Finish :Updating Client ID  ***** ");

    }

    public void doLanguageSelectionPurchased(String purchasedLanguage) throws Exception {
        try {

            System.out.println("Client Selection value passed is " + purchasedLanguage);

            InputStream is = NewClientUI.class.getResourceAsStream("language.xml");

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            XPathFactory xpf = XPathFactory.newInstance();
            XPath xpath = xpf.newXPath();
            XPathExpression xpe = xpath.compile("//languages[@language=\'" + purchasedLanguage + "\']");

            NodeList result = (NodeList) xpe.evaluate(doc, XPathConstants.NODESET);
            Node nNode = result.item(0);
            Element eElement = (Element) nNode;

            if (result.getLength() > 0) {
                System.out.println("Selected language from " + purchasedLanguage + " has been found.");
                System.out.println("Language is: " + eElement.getAttribute("language"));
                System.out.println("Language ID: " + eElement.getAttribute("languageid"));
                System.out.println("locale ID is: " + eElement.getAttribute("localeid"));

                // Set the Purchased Languages
                po.clickLanguageSelector("int_purchasedlanguages", eElement.getAttribute("languageid"));

                // Set the UI Locale
                po.clickLanguageSelector("int_clientpurchasedlocale", eElement.getAttribute("localeid"));

                // Set the Client Base Locale
                po.clickLanguageSelector("int_clientlocale", eElement.getAttribute("localeid"));

                // Remove after Jim's approval ????
                // po.setLangPurchased_val(eElement.getAttribute("language"));
                // po.LangPurchasedDatain(eElement.getAttribute("languageid"));
                // po.setLangLocals_val(eElement.getAttribute("locale"));
                // po.LangLocalsDatain(eElement.getAttribute("localeid"));
                // po.setLangClientBaseLocals_val(eElement.getAttribute("localeid"));
                // po.LangClientBaseDatain(eElement.getAttribute("localeid"));

            } else {
                webutils.takeSnapshots(photocount, 1, "prefix_lang");
                throw (new NewClientException(
                        "Error: Selected language of " + purchasedLanguage + " has not, been found."));
            }

        } catch (Exception e) {
            e.printStackTrace();
            webutils.takeSnapshots(photocount, 1, "prefix_lang");
            throw (new NewClientException("Error: Calling language selection"));
        }
    }

    public void saveForm() throws NewClientException {

        po.Save();

    }

    public void commit() throws NewClientException {

        if (commit.toLowerCase().contentEquals("yes")) {
            saveForm();
            try {
                waitClientDetail("Client details set", 300, 60);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (commit.toLowerCase().contentEquals("no")) {
            System.out.println("INF : test run, so cancel button");
            po.Cancel();
        } else {
            System.out.println("ERR : Unrecognised Commit Option (Yes/No) only valid your supplied (" + commit + ")");
        }

        // if (env.contains("staging"))
        // doNewClientId();

    }

    public void waitClientDetail(String msg, long durLong, long durShort) throws Exception {

        if (webutils.waitonTitle(msg, durLong)) {
            System.out.println("INF : Waiting for Client Details Set Succc page - reached");
        } else {
            System.out.println("ERR : Didnt Get Client Details Set Succc page");
            webutils.takeSnapshots(photocount, 1, "prefix_noclientdetails");
            throw (new NewClientException("Didnt Get Client Details Set Succc page"));
            // exitProcesses();
            // System.exit(-1);
        }
    }

    @Override
    public I_UIAutomation setUrl(String url) {
        // TODO Auto-generated method stub
        getDriver().setUrl(url);
        return this;
    }

    @Override
    public I_UIAutomation navTo() {
        // TODO Auto-generated method stub
        getDriver().navto();
        return null;
    }

    @Override
    public I_UIAutomation doAgain() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DriverIE getDriver() {
        // TODO Auto-generated method stub
        return driver;
    }

    @Override
    public I_UIAutomation setDriver(DriverIE driver) {
        // TODO Auto-generated method stub
        this.driver = driver;
        return this;
    }

    public I_UIAutomation getData() {
        return (I_UIAutomation) data;
    }

    @Override
    public I_UIAutomation setData(I_UIData data) {
        // TODO Auto-generated method stub
        this.data = data;
        return this;
    }

    public NewClientPo getPo() {
        return po;
    }

    public void setPo(NewClientPo po) {
        this.po = po;
    }

    public String getXstr() {
        return xstr;
    }

    public void setXstr(String xstr) {
        this.xstr = xstr;
    }

}
