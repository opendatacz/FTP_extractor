package cz.opendata.linked.ftp.extractor;

import cz.cuni.mff.xrg.odcs.commons.module.config.DPUConfigObjectBase;

/**
 *
 * Put your DPU's configuration here.
 *
 */
public class FTP_extractorConfig extends DPUConfigObjectBase {

    public String server;
    public Integer port;
    public String user;
	public String pass;
	public String initialDir;
	public String fileType;
	public boolean onlyGain;
	public boolean useOnlyCache;

	public FTP_extractorConfig() {
		this.server= "";
		this.port = 21;
		this.user = "";
		this.pass = "";
		this.initialDir = "";
		this.onlyGain = true;
		this.useOnlyCache = false;
		this.fileType = "";
	}

	public FTP_extractorConfig(String server, Integer port, String user, String pass, String initialDir, String fileType, boolean onlyGain, boolean useOnlyCache) {
		this.server= server;
		this.port = port;
		this.user = user;
		this.pass = pass;
		this.initialDir = initialDir;
		this.onlyGain = onlyGain;
		this.useOnlyCache = useOnlyCache;
		this.fileType = fileType;
	}

    @Override
    public boolean isValid() {

	    return true;
        //return !dateFrom.isEmpty() && !dateTo.isEmpty() && !context.isEmpty();
    }

}



















