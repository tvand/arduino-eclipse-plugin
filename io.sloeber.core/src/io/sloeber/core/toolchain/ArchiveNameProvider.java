package io.sloeber.core.toolchain;

import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.core.ITool;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import io.sloeber.core.common.Common;
import io.sloeber.core.common.Const;
import io.sloeber.core.tools.Helpers;

public class ArchiveNameProvider implements IManagedOutputNameProviderJaba {

	@Override
	public IPath[] getOutputNames(ITool tool, IPath[] primaryInputNames) {
		Common.log(new Status(IStatus.ERROR, Const.CORE_PLUGIN_ID,
				"ArchiveNameProvider: The call should go to the overloaded function not here.")); //$NON-NLS-1$
		return null;
	}

	@Override
	public IPath[] getOutputNames(IProject project, IConfiguration cConf, ITool tool, IPath[] primaryInputNames) {
		IPath[] outputNames = new IPath[primaryInputNames.length];
		boolean bUseArchiver = Common
				.getBuildEnvironmentVariable(project, cConf.getName(), Const.ENV_KEY_USE_ARCHIVER, Const.TRUE)
				.equalsIgnoreCase(Const.TRUE);
		if (!bUseArchiver) {
			// we don't use archiving so we ignore all files
			return null;
		}
		for (int curPath = 0; curPath < primaryInputNames.length; curPath++) {
			IPath infile = primaryInputNames[curPath];
			if (infile.toString().startsWith(Const.ARDUINO_CODE_FOLDER_PATH)
					&& (!"cxx".equals(infile.getFileExtension()))) { //$NON-NLS-1$
				outputNames[curPath] = Helpers.GetOutputName(infile).addFileExtension("o"); //$NON-NLS-1$
			} else {
				return null;
			}
		}
		return outputNames;
	}

}
