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

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.gwtpro.html5.fileapi.client.events.FileEvent;
import com.gwtpro.html5.fileapi.client.events.HasFileEvents;
import com.gwtpro.html5.fileapi.client.events.FileEvent.FileEventHandler;

/**
 * Wires a widget or HTML element for dropping files from the operating system
 * 
 * @author Abed Tony BenBrahim
 * 
 */
public class DropHandler implements HasFileEvents {

    private JsArray<File> files;
    private final HandlerManager handlerManager;

    /**
     * constructs a drop handler for an HTML element
     * 
     * @param element
     *            the element which will be wired to detect file drops
     */
    public DropHandler(Element element) {
        this.handlerManager = new HandlerManager(this);
        bindTo(element);
    }

    /**
     * constructs a drop handle for a GWT widget
     * 
     * @param widget
     *            the widget which will be wired to detect file drops
     */
    public DropHandler(Widget widget) {
        this.handlerManager = new HandlerManager(this);
        bindTo(widget.getElement());
    }

    /**
     * register a handler for file drop events
     * 
     * @param handler
     *            the handler for file drop events
     */
    @Override
    public HandlerRegistration addFileEventHandler(FileEventHandler handler) {
        return this.handlerManager.addHandler(FileEvent.TYPE, handler);
    }

    private native void bindTo(Element element)/*-{
        var _this=this;
        element.ondragenter=function(){
            return false;
        };
        element.ondragover=function(event){
            return false;
        };
        element.ondrop=function(event){
            if (event && event.dataTransfer && event.dataTransfer.files && event.dataTransfer.files.length) {
                _this.@com.gwtpro.html5.fileapi.client.DropHandler::files = event.dataTransfer.files;
                _this.@com.gwtpro.html5.fileapi.client.DropHandler::filesDropped()();
            }
            return false;
        };
    }-*/;

    protected void filesDropped() {
        this.handlerManager.fireEvent(new FileEvent(this.files));
    }
}
