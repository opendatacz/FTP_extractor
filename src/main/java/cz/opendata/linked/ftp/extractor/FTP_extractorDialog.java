package cz.opendata.linked.ftp.extractor;

import cz.cuni.mff.xrg.odcs.commons.configuration.*;
import cz.cuni.mff.xrg.odcs.commons.module.dialog.BaseConfigDialog;
import com.vaadin.ui.*;

/**
 * DPU's configuration dialog. User can use this dialog to configure DPU configuration.
 *
 */
public class FTP_extractorDialog extends BaseConfigDialog<FTP_extractorConfig> {

    private GridLayout mainLayout;

    private TextField input_server;
    private TextField input_port;
	private TextField input_user;
	private PasswordField input_pass;
	private TextField input_initialDir;
	private TextField input_fileType;
    private CheckBox input_onlyGain;
	private CheckBox input_useOnlyCache;

    public FTP_extractorDialog() {
        super(FTP_extractorConfig.class);
        buildMainLayout();
        setCompositionRoot(mainLayout);
    }


	@Override
	public void setConfiguration(FTP_extractorConfig conf) throws ConfigException {

		input_server.setValue(conf.server);
        input_port.setValue(conf.port.toString());
		input_user.setValue(conf.user.trim());
		input_pass.setValue(conf.pass.trim());
		input_initialDir.setValue(conf.initialDir.trim());
        input_onlyGain.setValue(conf.onlyGain);
		input_useOnlyCache.setValue(conf.useOnlyCache);
		input_fileType.setValue(conf.fileType);

	}

	@Override
	public FTP_extractorConfig getConfiguration() throws ConfigException {


        FTP_extractorConfig conf = new FTP_extractorConfig();

        conf.server = input_server.getValue().trim();
        conf.port = Integer.parseInt(input_port.getValue().trim());
        conf.user = input_user.getValue().trim();
		conf.pass = input_pass.getValue().trim();
		conf.initialDir = input_initialDir.getValue().trim();
		conf.onlyGain = input_onlyGain.getValue();
		conf.useOnlyCache = input_useOnlyCache.getValue();
		conf.fileType = input_fileType.getValue();

        return conf;
	}

    /**
     * Builds main layout
     *
     * @return mainLayout GridLayout with all components of configuration
     *         dialog.
     */
    private GridLayout buildMainLayout() {


        // common part: create layout
        mainLayout = new GridLayout(1,4);
        mainLayout.setImmediate(false);
        mainLayout.setWidth("100%");
        mainLayout.setHeight("100%");
        mainLayout.setMargin(false);
        //mainLayout.setSpacing(true);

        // top-level component properties
        setWidth("100%");
        setHeight("100%");

        // text field for FTP server address
        input_server = new TextField();
        input_server.setNullRepresentation("");
        input_server.setCaption("FTP server address");
        input_server.setImmediate(true);
        input_server.setWidth("100%");
        input_server.setHeight("-1px");
        mainLayout.addComponent(input_server);

        // text field for server port number
        input_port = new TextField();
        input_port.setNullRepresentation("");
        input_port.setCaption("Server port number");
        input_port.setImmediate(true);
        input_port.setWidth("100%");
        input_port.setHeight("-1px");
        mainLayout.addComponent(input_port);

	    // text field for user
	    input_user = new TextField();
	    input_user.setNullRepresentation("User");
	    input_user.setCaption("");
	    input_user.setImmediate(true);
	    input_user.setWidth("100%");
	    input_user.setHeight("-1px");
	    mainLayout.addComponent(input_user);

	    // text field for password
	    input_pass = new PasswordField();
	    input_pass.setNullRepresentation("Password");
	    input_pass.setCaption("");
	    input_pass.setImmediate(true);
	    input_pass.setWidth("100%");
	    input_pass.setHeight("-1px");
	    input_pass.setDescription("If server doesn´t require password, leave it blank");
	    mainLayout.addComponent(input_pass);

	    // text field for initial directory path
	    input_initialDir = new TextField();
	    input_initialDir.setNullRepresentation("");
	    input_initialDir.setCaption("Initial directory");
	    input_initialDir.setImmediate(true);
	    input_initialDir.setWidth("100%");
	    input_initialDir.setHeight("-1px");
	    input_initialDir.setDescription("From this directory FTP extractor will recursively download all files.");
	    mainLayout.addComponent(input_initialDir);

	    // text field for initial directory path
	    input_fileType = new TextField();
	    input_fileType.setNullRepresentation("");
	    input_fileType.setCaption("File types");
	    input_fileType.setImmediate(true);
	    input_fileType.setWidth("100%");
	    input_fileType.setHeight("-1px");
	    input_fileType.setDescription("Process only files with defined extensions (xml, tar.gz, ...). Extension names must be comma separated. In case you don´t care about extensions, leave it blank");
	    input_fileType.setInputPrompt("xml,txt");
	    mainLayout.addComponent(input_fileType);

        // checkbox for onlyGain
        input_onlyGain = new CheckBox();
        input_onlyGain.setCaption("Process gain only");
        input_onlyGain.setWidth("100%");
	    input_onlyGain.setDescription("Process all new files, which aren´t already cached.");
        mainLayout.addComponent(input_onlyGain);

	    // checkbox for use only cached files
	    input_useOnlyCache = new CheckBox();
	    input_useOnlyCache.setCaption("Process only cached files");
	    input_useOnlyCache.setWidth("100%");
	    input_useOnlyCache.setDescription("New files won´t be downloaded.");
	    mainLayout.addComponent(input_useOnlyCache);

        return mainLayout;
    }

	
}
