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
package com.gwtpro.html5.fileapi.client.file;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event used for notification of the availability of files
 * 
 * @author Abed Tony BenBrahim
 * 
 */
public class FileEvent extends GwtEvent<FileEvent.FileEventHandler> {

    public interface FileEventHandler extends EventHandler {

        void onFiles(FileEvent event);
    }

    public static final Type<FileEventHandler> TYPE = new GwtEvent.Type<FileEventHandler>();
    private final JsArray<File> files;

    /**
     * construct a file event with an array of associated files
     * @param files the files associated with this event
     */
    public FileEvent(JsArray<File> files) {
        this.files = files;
    }

    @Override
    public GwtEvent.Type<FileEventHandler> getAssociatedType() {
        return TYPE;
    }

    /**
     * gets the files associated with this event
     * 
     * @return an array of files associated with this event
     */
    public JsArray<File> getFiles() {
        return this.files;
    }

    @Override
    protected void dispatch(FileEventHandler handler) {
        handler.onFiles(this);
    }
}
