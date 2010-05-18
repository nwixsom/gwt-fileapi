package com.gwtpro.html5.fileapi.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.Widget;
import com.gwtpro.html5.fileapi.client.file.File;

public class FileInput extends Widget implements HasName, HasChangeHandlers {

	private static class FileInputImpl {
		public native JsArray<File> getFiles(InputElement inputElement) /*-{
			return inputElement.value && inputElement.value!=""?
				[{fileName: inputElement.value, fileSize: -1}]:
				[];
		}-*/;

		public boolean isAllowMultipleFiles(InputElement inputElement) {
			return false;
		}

		public void setAllowMultipleFiles(InputElement inputElement,
				boolean allow) {
		}

		public boolean supportsFileAPI() {
			return false;
		}
	}

	@SuppressWarnings("unused")
	private static class FileInputImplHtml5 extends FileInputImpl {
		@Override
		public native JsArray<File> getFiles(InputElement inputElement) /*-{
			return inputElement.files;
		}-*/;

		@Override
		public boolean isAllowMultipleFiles(InputElement inputElement) {
			return inputElement.hasAttribute("multiple");
		}

		@Override
		public void setAllowMultipleFiles(InputElement inputElement,
				boolean allow) {
			if (allow) {
				inputElement.setAttribute("multiple", "");
			} else {
				inputElement.removeAttribute("multiple");
			}
		}

		@Override
		public boolean supportsFileAPI() {
			return true;
		}
	}

	private final FileInputImpl impl;
	private final InputElement inputElement;

	public FileInput() {
		this.inputElement = Document.get().createFileInputElement();
		setElement(this.inputElement);
		setStyleName("gwt-FileUpload");
		this.impl = GWT.create(FileInputImpl.class);
	}

	@Override
	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return addDomHandler(handler, ChangeEvent.getType());
	}

	public File[] getFiles() {
		JsArray<File> files = this.impl.getFiles(this.inputElement);
		File[] result = new File[files.length()];
		for (int i = 0; i < files.length(); ++i) {
			result[i] = files.get(i);
		}
		return result;
	}

	public String getName() {
		return this.inputElement.getName();
	}

	public boolean isAllowedMultipleFiles() {
		return this.impl.isAllowMultipleFiles(this.inputElement);
	}

	public boolean isDisabled() {
		return this.inputElement.isDisabled();
	}

	public void setAllowMultipleFiles(boolean allow) {
		this.impl.setAllowMultipleFiles(this.inputElement, allow);
	}

	public void setDisabled(boolean disabled) {
		this.inputElement.setDisabled(disabled);
	}

	public void setName(String name) {
		this.inputElement.setName(name);
	}

	public boolean supportsFileAPI() {
		return this.impl.supportsFileAPI();
	}

}
