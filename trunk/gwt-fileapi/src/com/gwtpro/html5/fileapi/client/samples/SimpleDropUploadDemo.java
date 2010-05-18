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
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtpro.html5.fileapi.client.DropHandler;
import com.gwtpro.html5.fileapi.client.File;
import com.gwtpro.html5.fileapi.client.UploadRequest;
import com.gwtpro.html5.fileapi.client.UploadRequestBuilder;
import com.gwtpro.html5.fileapi.client.UploadRequestCallback;
import com.gwtpro.html5.fileapi.client.events.FileEvent;
import com.gwtpro.html5.fileapi.client.events.FileEvent.FileEventHandler;

public class SimpleDropUploadDemo implements EntryPoint {

    private int currentFile;
    private JsArray<File> files;
    private UploadRequestBuilder fileUploader;
    private Grid table;

    @Override
    public void onModuleLoad() {
        RootPanel rootPanel = RootPanel.get();
        DropHandler dropHandler = new DropHandler(rootPanel);
        this.table = new Grid();
        RootPanel.get("table").add(this.table);
        String url = GWT.getModuleBaseURL() + "upload-test";
        this.fileUploader = new UploadRequestBuilder(url);
        this.fileUploader.setCallback(new UploadRequestCallback() {

            @Override
            public void onError(UploadRequest request, Throwable exception) {
                SimpleDropUploadDemo.this.table.setText(SimpleDropUploadDemo.this.currentFile + 1, 2,
                        "failed: " + exception.getMessage());
                uploadNextFile(SimpleDropUploadDemo.this.currentFile + 1);
            }

            @Override
            public void onResponseReceived(UploadRequest request,
                    Response response) {
                SimpleDropUploadDemo.this.table.setText(SimpleDropUploadDemo.this.currentFile + 1, 2,
                        "success: " + response.getText());
                uploadNextFile(SimpleDropUploadDemo.this.currentFile + 1);
            }

            @Override
            public void onUploadProgress(UploadRequest request,
                    int bytesUploaded) {
                SimpleDropUploadDemo.this.table.setText(SimpleDropUploadDemo.this.currentFile + 1, 2,
                        bytesUploaded + "");
            }

        });
        dropHandler.addFileEventHandler(new FileEventHandler() {

            @Override
            public void onFiles(FileEvent event) {
                SimpleDropUploadDemo.this.files = event.getFiles();
                SimpleDropUploadDemo.this.table.clear();
                SimpleDropUploadDemo.this.table.resize(SimpleDropUploadDemo.this.files.length() + 1, 3);
                SimpleDropUploadDemo.this.table.setText(0, 0, "File name");
                SimpleDropUploadDemo.this.table.setText(0, 1, "File size");
                SimpleDropUploadDemo.this.table.setText(0, 2, "Progress");
                for (int i = 0; i < SimpleDropUploadDemo.this.files.length(); ++i) {
                    SimpleDropUploadDemo.this.table.setText(i + 1, 0, SimpleDropUploadDemo.this.files
                            .get(i).getFileName());
                    SimpleDropUploadDemo.this.table.setText(i + 1, 1, SimpleDropUploadDemo.this.files
                            .get(i).getFileSize()
                            + "");
                    SimpleDropUploadDemo.this.table.setText(i + 1, 2, "");
                }
                uploadNextFile(0);
            }
        });
    }

    private void uploadNextFile(int index) {
        SimpleDropUploadDemo.this.currentFile = index;
        if (index < this.files.length()) {
            try {
                this.fileUploader.sendFile(this.files
                        .get(SimpleDropUploadDemo.this.currentFile));
            } catch (RequestException e) {
                this.table.setText(index + 1, 2, "failed: " + e.getMessage());
                uploadNextFile(index + 1);
            }
        }
    }
}
