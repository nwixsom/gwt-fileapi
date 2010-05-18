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

import com.google.gwt.xhr.client.XMLHttpRequest;

public class XMLHttpRequest2 extends XMLHttpRequest {

    /**
     * Creates an XMLHttpRequestw object.
     * 
     * @return the created object
     */
    public static native XMLHttpRequest2 create() /*-{
        if ($wnd.XMLHttpRequest) {
          return new XMLHttpRequest();
        } else {
          try {
            return new ActiveXObject('MSXML2.XMLHTTP.3.0');
          } catch (e) {
            return new ActiveXObject("Microsoft.XMLHTTP");
          }
        }
    }-*/;

    protected XMLHttpRequest2() {
        super();
    }

    public final native void sendFile(File file)/*-{
        this.send(file);
    }-*/;

    public final native void setOnProgressHandler(UploadProgressHandler handler)/*-{
        var _this=this;
        if (this.upload){
            this.upload.addEventListener("progress",function(e){
                handler.@com.gwtpro.html5.fileapi.client.UploadProgressHandler::onProgress(Lcom/gwtpro/html5/fileapi/client/XMLHttpRequest2;I)(_this, e.loaded);
            }, false);
        }
    }-*/;
}
