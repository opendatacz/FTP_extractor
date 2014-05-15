package cz.opendata.linked.ftp.extractor;

import org.junit.Test;
import org.junit.Assert;

import cz.cuni.mff.xrg.odcs.dataunit.file.handlers.DirectoryHandler;
import cz.cuni.mff.xrg.odcs.commons.configuration.ConfigException;
import cz.cuni.mff.xrg.odcs.dataunit.file.FileDataUnit;
import cz.cuni.mff.xrg.odcs.dpu.test.TestEnvironment;
import java.io.IOException;


public class FTP_extractorTest {

	@Test
	public void testDownload() {

		try {

			FTP_extractor extractor = new FTP_extractor();
			//FTP_extractorConfig config = new FTP_extractorConfig("ted.europa.eu",21,"eu-tenders","eu-tenders-123","/TED-XML",false,false);
			FTP_extractorConfig config = new FTP_extractorConfig("kastan.pomyka.cz",21,"kastan.pomyka.cz","kastanjestrom","temp","",true,false);

			extractor.configureDirectly(config);

			TestEnvironment env = TestEnvironment.create();
			FileDataUnit output = env.createFileOutput("downloadedFiles");

			try {
				// run the execution
				env.run(extractor);
				System.out.println("Resulting directory: " + output.getRootDir().size());

				Assert.assertTrue(output.getRootDir().size() > 0);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// release resources
				env.release();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConfigException e) {
			e.printStackTrace();
		}


	}


}
