package cz.opendata.linked.ftp.extractor;

import cz.cuni.mff.xrg.odcs.commons.data.DataUnitException;
import cz.cuni.mff.xrg.odcs.commons.dpu.DPU;
import cz.cuni.mff.xrg.odcs.commons.dpu.DPUContext;
import cz.cuni.mff.xrg.odcs.commons.dpu.DPUException;
import cz.cuni.mff.xrg.odcs.commons.dpu.annotation.*;
import cz.cuni.mff.xrg.odcs.commons.module.dpu.ConfigurableBase;
import cz.cuni.mff.xrg.odcs.commons.web.AbstractConfigDialog;
import cz.cuni.mff.xrg.odcs.commons.web.ConfigDialogProvider;
import cz.cuni.mff.xrg.odcs.dataunit.file.FileDataUnit;
import cz.cuni.mff.xrg.odcs.dataunit.file.handlers.DirectoryHandler;
import cz.cuni.mff.xrg.odcs.dataunit.file.options.OptionsAdd;

import cz.opendata.linked.ftp.utils.cache.Cache;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@AsExtractor
public class FTP_extractor extends ConfigurableBase<FTP_extractorConfig>
        implements DPU, ConfigDialogProvider<FTP_extractorConfig> {

	private final Logger logger = LoggerFactory.getLogger(FTP_extractor.class);

	@OutputDataUnit(name="downloadedFiles",description="It contains downloaded files via FTP.")
    public FileDataUnit filesOutput;

	private DirectoryHandler outputRoot;
	private FTPClient ftpClient;
	private Cache cache;

    public FTP_extractor() {
        super(FTP_extractorConfig.class);
    }

    @Override
    public AbstractConfigDialog<FTP_extractorConfig> getConfigurationDialog() {
        return new FTP_extractorDialog();
    }

    @Override
    public void execute(DPUContext context) throws DPUException {

		this.cache = Cache.getInstance();
		this.cache.setLogger(this.logger);
		this.cache.setBasePath(context.getUserDirectory().toString());

		this.outputRoot = filesOutput.getRootDir();

		this.ftpClient = new FTPClient();

	    try {
		    this.logger.debug("Estabilishing FTP connection to {}", this.config.server);
			this.ftpClient.connect(this.config.server, this.config.port);
			this.ftpClient.login(this.config.user, this.config.pass);
			this.ftpClient.enterLocalPassiveMode();
			this.ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

		    this.logger.debug("FTP connection to {} estabilished.", this.config.server);

		    // recursive process all files in initial directory
		    String initialDir = this.config.initialDir;
		    this.processDirectory(initialDir.endsWith("/") ? initialDir.substring(0, initialDir.lastIndexOf("/")) : initialDir);

		} catch (IOException e) {
			throw new DPUException("Could not estabilish FTP connection.", e);
		} finally {
			try {
				if (
					ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
					this.logger.debug("FTP connection to {} successfully closed.", this.config.server);
			    }
			} catch (IOException e) {
				this.logger.error("Could not close FTP connection." + e.getMessage());
			}
	    }

    }

	/**
	 *
	 * @param path
	 */
	private void processDirectory(String path) {

		boolean addFile;

		try {
			FTPFile[] files = ftpClient.listFiles(path);
			for (FTPFile file : files) {

				String filePath = path + File.separator + file.getName();

				this.logger.debug("Proceed file: {}", filePath);

				// local file path is used as target for file output
				String localFilePath = this.cache.getCacheDir() + File.separator + filePath;

				if (file.getName().equals(".") || file.getName().equals("..")) {
					// skip parent directory and directory itself
					continue;
				}

				if(file.isDirectory()) {
					this.processDirectory(filePath);
				} else {

					String[] fileTypes = this.config.fileType.toLowerCase().replace(" ","").split(",");

					// if configuration requires specific extension type, than compare them
					String fileType = file.getName().substring(file.getName().indexOf(".") + 1, file.getName().length()).toLowerCase();
					if(!this.config.fileType.equals("") && !Arrays.asList(fileTypes).contains(fileType)) {
						// skip the file process
						continue;
					}

					addFile = false;

					if(!this.cache.isCached(filePath) && this.config.useOnlyCache == false) {

						this.logger.debug("Retrieving file {} via FTP.", filePath);
						new File(localFilePath).getParentFile().mkdirs();
						OutputStream output;
						output = new FileOutputStream(localFilePath);

						this.ftpClient.retrieveFile(filePath, output);
						output.close();

						addFile = true;

					} else if(this.cache.isCached(filePath) && this.config.onlyGain == false) {
						this.logger.debug("Retrieving file {} from cache", filePath);
						addFile = true;
					}

					if(addFile) {
						this.outputRoot.addExistingFile(new File(localFilePath), new OptionsAdd(false,true));
					}
				}
			}
		} catch(IOException e) {
			this.logger.error("Error while processing file. " + e.getMessage());
		} catch(DataUnitException e) {
			this.logger.error("Error while sending existing file to DPU output. " + e.getMessage());
		}

	}

}
