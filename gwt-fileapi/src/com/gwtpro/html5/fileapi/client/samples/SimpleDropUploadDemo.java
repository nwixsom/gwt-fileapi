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
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtpro.html5.fileapi.client.DropHandler;
import com.gwtpro.html5.fileapi.client.File;
import com.gwtpro.html5.fileapi.client.FileUploader;
import com.gwtpro.html5.fileapi.client.events.FileEvent;
import com.gwtpro.html5.fileapi.client.events.FileUploadDoneEvent;
import com.gwtpro.html5.fileapi.client.events.FileUploadProgressEvent;
import com.gwtpro.html5.fileapi.client.events.FileEvent.FileEventHandler;
import com.gwtpro.html5.fileapi.client.events.FileUploadDoneEvent.FileUploadDoneEventHandler;
import com.gwtpro.html5.fileapi.client.events.FileUploadProgressEvent.FileUploadProgressEventHandler;

public class SimpleDropUploadDemo implements EntryPoint {

    private int currentFile;
    private JsArray<File> files;
    private FileUploader fileUploader;

    @Override
    public void onModuleLoad() {
        RootPanel rootPanel = RootPanel.get();
        DropHandler dropHandler = new DropHandler(rootPanel);
        final Grid table = new Grid();
        RootPanel.get("table").add(table);
        this.fileUploader = new FileUploader();
        this.fileUploader.setUrl(GWT.getModuleBaseURL() + "upload-test");
        this.fileUploader
                .addFileUploadDoneEventHandler(new FileUploadDoneEventHandler() {

                    @Override
                    public void onDone(FileUploadDoneEvent event) {
                        if (event.isSuccessful()) {
                            table.setText(
                                    SimpleDropUploadDemo.this.currentFile + 1,
                                    2, "success: " + event.getResponse());
                        } else if (event.getResponseCode() == 0) {
                            table.setText(
                                    SimpleDropUploadDemo.this.currentFile + 1,
                                    2, "aborted");
                        } else {
                            table.setText(
                                    SimpleDropUploadDemo.this.currentFile + 1,
                                    2, "failed: " + event.getResponseCode()
                                            + " " + event.getResponse());
                        }
                    }
                });
        this.fileUploader.addFileUploadProgressEventHandler(new FileUploadProgressEventHandler() {
            
            @Override
            public void onProgress(FileUploadProgressEvent event) {
                //GWT.log(event.getProgress()+"",null);
                //table.setText(SimpleDropUploadDemo.this.currentFile+1, 2, (event.getProgress()*100/event.getFile().getFileSize())+"%");
                table.setText(SimpleDropUploadDemo.this.currentFile+1, 2, event.getProgress()+"");
            }
        });
        dropHandler.addFileEventHandler(new FileEventHandler() {
            @Override
            public void onFiles(FileEvent event) {
                SimpleDropUploadDemo.this.files = event.getFiles();
                table.clear();
                table.resize(SimpleDropUploadDemo.this.files.length() + 1, 3);
                table.setText(0, 0, "File name");
                table.setText(0, 1, "File size");
                table.setText(0, 2, "Progress");
                for (int i = 0; i < SimpleDropUploadDemo.this.files.length(); ++i) {
                    table.setText(i + 1, 0, SimpleDropUploadDemo.this.files.get(i).getFileName());
                    table.setText(i + 1, 1, SimpleDropUploadDemo.this.files.get(i).getFileSize() + "");
                }
                SimpleDropUploadDemo.this.currentFile = -1;
                uploadNextFile();
            }
        });
    }

    private void uploadNextFile() {
        SimpleDropUploadDemo.this.currentFile++;
        this.fileUploader.sendFile(this.files
                .get(SimpleDropUploadDemo.this.currentFile));
    }
}
