package com.gwtpro.html5.fileapi.client;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.gwtpro.html5.fileapi.client.events.FileEvent;
import com.gwtpro.html5.fileapi.client.events.HasFileEvents;
import com.gwtpro.html5.fileapi.client.events.FileEvent.FileEventHandler;

public class DropHandler implements HasFileEvents{

    private JsArray<File> files;
    private final HandlerManager handlerManager;

    public DropHandler(Element element) {
        this.handlerManager=new HandlerManager(this);
        bindTo(element);
    }

    public DropHandler(Widget widget) {
        this.handlerManager=new HandlerManager(this);
        bindTo(widget.getElement());
    }

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
            if (event.dataTransfer && event.dataTransfer.files) {
                var files=event.dataTransfer.files;
                if (files && files.length){
                    _this.@com.gwtpro.html5.fileapi.client.DropHandler::files = event.dataTransfer.files;
                    _this.@com.gwtpro.html5.fileapi.client.DropHandler::filesDropped()();
                }
            }
            return false;
        };
    }-*/;

    protected void filesDropped() {
        this.handlerManager.fireEvent(new FileEvent(this.files));
    }

}
