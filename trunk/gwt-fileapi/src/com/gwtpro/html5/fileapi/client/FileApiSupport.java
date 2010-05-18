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

public final class FileApiSupport {

    public static native boolean isDragDropSupported()/*-{
        return 'draggable' in document.createElement('span');
    }-*/;
    
    public static native boolean isHttpXmlRequestLevel2()/*-{
        return !!((typeof XMLHttpRequest != "undefined") &&
            (new XMLHttpRequest()).upload);
    }-*/;
    
    public static native boolean isMultipleFileInputSupported()/*-{
       var input=document.createElement('input');
       input.setAttribute('type','file');
       return input.files!=null;
    }-*/;
}
