package com.prosegur.active;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

public class SaveHotReload extends AbstractHandler implements IHandler{

	@Override
	public Object execute(ExecutionEvent execEvent) throws ExecutionException {
		new Thread(()->{
			try {
				FilesSaved.getInstance().reload();
			} catch (IOException e) {
				Logger.getLogger(SaveHotReload.class.getName()).log(Level.SEVERE, e.getMessage());
			}
		}).start();
		return null;
	}	
}
