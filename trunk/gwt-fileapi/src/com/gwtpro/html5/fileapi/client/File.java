/*
 * Copyright 2010 Abed Tony BenBrahim
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gwtpro.html5.fileapi.client;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * representation of an operating system file
 * 
 * @author Abed Tony BenBrahim
 * 
 */
public class File extends JavaScriptObject {

    protected File() {
    }

    /**
     * gets the content of the file as binary
     * 
     * @return a binary string with the content
     */
    public final native String getAsBinary() /*-{
        return this.getAsBinary();
    }-*/;

    /**
     * gets the content of the file as a data URL
     * 
     * @return a string with the data formatted as a data URL
     */
    public final native String getAsDataURL() /*-{
        return this.getAsDataURL();
    }-*/;

    /**
     * gets the content of the file as a text
     * 
     * @return a string containing the content of the file as text
     */
    public final native String getAsText() /*-{
        return this.getAsText();
    }-*/;

    /**
     * gets the filename
     * 
     * @return the filename
     */
    public final native String getFileName() /*-{
        return this.fileName;
    }-*/;

    /**
     * gets the file size in bytes
     * 
     * @return the file size in bytes
     */
    public final native int getFileSize() /*-{
        return this.fileSize;
    }-*/;

    /**
     * gets the MIME type of the file, may be null if the browser cannot detect
     * the type
     * 
     * @return the MIME type of the file
     */
    public final native String getType() /*-{
       return this.type;
   }-*/;
}
