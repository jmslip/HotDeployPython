package com.prosegur.active;

import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.prosegur.active.listeners.WindowListener;

/**
 * 
 * Classe para pegar os eventos dos editores do eclipse 
 * para conseguir recuperar todos os arquivos que foram abertos
 * 
 * @author Lucas Gabriel
 *
 */
public class StartupHotReload implements IStartup{

	@Override
	public void earlyStartup() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.addWindowListener(new WindowListener());
	}
}
