/**
 * 
 */
package com.gwtpro.html5.fileapi.client;

import com.google.gwt.core.client.JavaScriptObject;

public class File extends JavaScriptObject {

    protected File() {
    }

    public final native String getAsBinary() /*-{
		return this.getAsBinary();
	}-*/;

    public final native String getAsDataURL() /*-{
		return this.getAsDataURL();
	}-*/;

    public final native String getAsText() /*-{
		return this.getAsText();
	}-*/;

    public final native String getFileName() /*-{
		return this.fileName;
	}-*/;

    public final native int getFileSize() /*-{
		return this.fileSize;
	}-*/;

    public final native String getType() /*-{
       return this.type;
   }-*/;
}