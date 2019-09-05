package com.prosegur.active.listeners;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * 
 * @author Lucas Gabriel
 *
 */
public class WindowListener implements IWindowListener{

	private AtomicBoolean withListener;
	private EditorListener editorListener ;
	
	public WindowListener() {
		withListener = new AtomicBoolean(false);
		editorListener= new EditorListener();
	}
	
	@Override
	public void windowActivated(IWorkbenchWindow arg0) {
		addListener(arg0);
	}

	@Override
	public void windowClosed(IWorkbenchWindow arg0) {
		removeListener(arg0);
	}

	@Override
	public void windowDeactivated(IWorkbenchWindow arg0) {
		removeListener(arg0);
	}

	@Override
	public void windowOpened(IWorkbenchWindow arg0) {
		addListener(arg0);
		
	}

	private void addListener(IWorkbenchWindow arg0) {
		if(!withListener.get() && Objects.nonNull(arg0.getActivePage())) {
			arg0.getActivePage().addPartListener(editorListener);
			withListener.set(true);
			
		}
	}
	
	private void removeListener(IWorkbenchWindow arg0) {
		if(withListener.get() && Objects.nonNull(arg0.getActivePage())) {
			arg0.getActivePage().removePartListener(editorListener);
			withListener.set(false);
		}
	}

}
