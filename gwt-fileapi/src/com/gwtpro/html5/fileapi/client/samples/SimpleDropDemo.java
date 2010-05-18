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
package com.gwtpro.html5.fileapi.client.samples;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtpro.html5.fileapi.client.drop.DropHandler;
import com.gwtpro.html5.fileapi.client.file.File;
import com.gwtpro.html5.fileapi.client.file.FileEvent;
import com.gwtpro.html5.fileapi.client.file.FileEvent.FileEventHandler;

public class SimpleDropDemo implements EntryPoint {

    public void onModuleLoad() {
        RootPanel rootPanel = RootPanel.get();
        DropHandler dropHandler = new DropHandler(rootPanel);
        dropHandler.addFileEventHandler(new FileEventHandler() {

            @Override
            public void onFiles(FileEvent event) {
                JsArray<File> files = event.getFiles();
                StringBuilder sb = new StringBuilder(
                        "<table><tr><th>Filename</th><th>Size</th><th>Type</th></tr>");
                for (int i = 0; i < files.length(); ++i) {
                    File file = files.get(i);
                    sb.append("<tr><td>").append(file.getFileName()).append(
                            "</td><td>").append(file.getFileSize()).append(
                            "</td><td>").append(file.getType()).append(
                            "</td></tr>");
                }
                RootPanel.get("table").getElement().setInnerHTML(
                        sb.append("</table>").toString());
            }
        });
    }
}
