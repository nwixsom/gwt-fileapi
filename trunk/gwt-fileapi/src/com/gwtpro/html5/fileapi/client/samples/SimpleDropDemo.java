package com.gwtpro.html5.fileapi.client.samples;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtpro.html5.fileapi.client.DropHandler;
import com.gwtpro.html5.fileapi.client.File;
import com.gwtpro.html5.fileapi.client.events.FileEvent;
import com.gwtpro.html5.fileapi.client.events.FileEvent.FileEventHandler;

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
