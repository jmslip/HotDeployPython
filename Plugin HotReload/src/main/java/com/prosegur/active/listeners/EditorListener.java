package com.prosegur.active.listeners;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

import com.prosegur.active.FilesSaved;

public class EditorListener implements IPartListener2 {

	private void getDocumentToListener(IWorkbenchPartReference partRef) {
		IWorkbenchPart part = partRef.getPart(false);
		if (part instanceof IEditorPart) {
			IEditorPart editorPart = (IEditorPart) part;
			IEditorInput input = editorPart.getEditorInput();
			textEditor(editorPart, input);
			htmlEditor(editorPart, input);
		}
	}

	private void htmlEditor(IEditorPart editorPart, IEditorInput input) {
		if (input instanceof FileEditorInput && editorPart instanceof EditorPart) {
			IFile file = ((FileEditorInput) input).getFile();
			EditorPart e = (EditorPart) editorPart;
			if (file.getFileExtension().equalsIgnoreCase("xhtml")) {
				e.addPropertyListener((arg0, arg1) -> FilesSaved.getInstance().add(file.getRawLocation().toOSString()));
			}
		}
	}

	private void textEditor(IEditorPart editorPart, IEditorInput input) {
		if (input instanceof FileEditorInput && editorPart instanceof ITextEditor) {
			ITextEditor textEditor = (ITextEditor) editorPart;
			IDocument document = textEditor.getDocumentProvider().getDocument(input);
			IFile file = ((FileEditorInput) input).getFile();
			if (file.getFileExtension().equalsIgnoreCase("xhtml")) {
				document.addDocumentListener(new IDocumentListener() {

					@Override
					public void documentChanged(DocumentEvent arg0) {
						FilesSaved.getInstance().add(file.getRawLocation().toOSString());
					}

					@Override
					public void documentAboutToBeChanged(DocumentEvent arg0) {
						// Nothing to do
					}

				});
			}
		}
	}

	@Override
	public void partActivated(IWorkbenchPartReference arg0) {
		getDocumentToListener(arg0);
	}

	@Override
	public void partBroughtToTop(IWorkbenchPartReference arg0) {
		// Nothing to do
	}

	@Override
	public void partClosed(IWorkbenchPartReference arg0) {
		// Nothing to do
	}

	@Override
	public void partDeactivated(IWorkbenchPartReference arg0) {
		// Nothing to do
	}

	@Override
	public void partHidden(IWorkbenchPartReference arg0) {
		// Nothing to do
	}

	@Override
	public void partInputChanged(IWorkbenchPartReference arg0) {
		getDocumentToListener(arg0);
	}

	@Override
	public void partOpened(IWorkbenchPartReference arg0) {
		getDocumentToListener(arg0);
	}

	@Override
	public void partVisible(IWorkbenchPartReference arg0) {
		// Nothing to do
	}

}
