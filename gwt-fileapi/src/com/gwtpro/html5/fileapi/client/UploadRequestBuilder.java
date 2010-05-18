/*
 * Copyright 2008 Google Inc.
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

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.RequestPermissionException;
import com.google.gwt.xhr.client.ReadyStateChangeHandler;
import com.google.gwt.xhr.client.XMLHttpRequest;

public class UploadRequestBuilder {

    public static native boolean isSupported()/*-{
        return !!(new XMLHttpRequest().upload);
    }-*/;

    /**
     * Map of header name to value that will be added to the JavaScript
     * XmlHttpRequest object before sending a request.
     */
    private Map<String, String> headers;
    /**
     * Password to use when opening a JavaScript XmlHttpRequest object.
     */
    private String password;
    /**
     * Timeout in milliseconds before the request timeouts and fails.
     */
    private int timeoutMillis;
    /**
     * URL to use when opening a JavaScript XmlHttpRequest object.
     */
    private final String url;
    /**
     * User to use when opening a JavaScript XmlHttpRequest object.
     */
    private String user;
    /**
     * The callback to call when the request completes or on progress events.
     */
    private UploadRequestCallback callback;

    /**
     * Creates a builder using the parameters values for configuration.
     * 
     * @param url
     *            URL that has already has already been URL encoded. Please see
     *            {@link com.google.gwt.http.client.URL#encode(String)} and
     *            {@link com.google.gwt.http.client.URL#encodeComponent(String)}
     *            for how to do this.
     * @throws IllegalArgumentException
     *             if the URL is empty or null
     */
    public UploadRequestBuilder(String url) {
        if (url == null || "".equals(url.trim())) {
            throw new IllegalArgumentException("url must be specified");
        }
        this.url = url;
    }

    /**
     * Returns the callback previously set by
     * {@link #setCallback(UploadRequestCallback)}, or <code>null</code> if no
     * callback was set.
     */
    public UploadRequestCallback getCallback() {
        return this.callback;
    }

    /**
     * Returns the value of a header previous set by
     * {@link #setHeader(String, String)}, or <code>null</code> if no such
     * header was set.
     * 
     * @param header
     *            the name of the header
     */
    public String getHeader(String header) {
        if (this.headers == null) {
            return null;
        }
        return this.headers.get(header);
    }

    /**
     * Returns the password previously set by {@link #setPassword(String)}, or
     * <code>null</code> if no password was set.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns the timeoutMillis previously set by
     * {@link #setTimeoutMillis(int)}, or <code>0</code> if no timeoutMillis was
     * set.
     */
    public int getTimeoutMillis() {
        return this.timeoutMillis;
    }

    /**
     * Returns the HTTP URL specified in the constructor.
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Returns the user previously set by {@link #setUser(String)}, or
     * <code>null</code> if no user was set.
     */
    public String getUser() {
        return this.user;
    }

    /**
     * Sends a POST request based to upload a file, on the current builder
     * configuration.
     * 
     * @return a {@link UploadRequest} object that can be used to track the
     *         request
     * @throws RequestException
     *             if the call fails to initiate
     * @throws NullPointerException
     *             if a request callback has not been set
     */
    public UploadRequest sendFile(File file) throws RequestException {
        if (this.callback == null) {
            throw new NullPointerException("callback has not been set");
        }
        XMLHttpRequest2 xmlHttpRequest = XMLHttpRequest2.create();
        try {
            if (this.user != null && this.password != null) {
                xmlHttpRequest.open("POST", this.url, this.user, this.password);
            } else if (this.user != null) {
                xmlHttpRequest.open("POST", this.url, this.user);
            } else {
                xmlHttpRequest.open("POST", this.url);
            }
        } catch (JavaScriptException e) {
            RequestPermissionException requestPermissionException = new RequestPermissionException(
                    this.url);
            requestPermissionException.initCause(new RequestException(e
                    .getMessage()));
            throw requestPermissionException;
        }
        if (this.headers != null) {
            for (Map.Entry<String, String> header : this.headers.entrySet()) {
                try {
                    xmlHttpRequest.setRequestHeader(header.getKey(), header
                            .getValue());
                } catch (JavaScriptException e) {
                    throw new RequestException(e.getMessage());
                }
            }
        }
        final UploadRequest request = new UploadRequest(xmlHttpRequest,
                this.timeoutMillis, this.callback);
        xmlHttpRequest.setOnReadyStateChange(new ReadyStateChangeHandler() {

            public void onReadyStateChange(XMLHttpRequest xhr) {
                if (xhr.getReadyState() == XMLHttpRequest.DONE) {
                    xhr.clearOnReadyStateChange();
                    request
                            .fireOnResponseReceived(UploadRequestBuilder.this.callback);
                }
            }
        });
        xmlHttpRequest.setOnProgressHandler(new UploadProgressHandler() {

            @Override
            public void onProgress(XMLHttpRequest2 xmlHttpRequest,
                    int bytesUploaded) {
                UploadRequestBuilder.this.callback.onUploadProgress(request,
                        bytesUploaded);
            }
        });
        try {
            xmlHttpRequest.sendFile(file);
        } catch (JavaScriptException e) {
            throw new RequestException(e.getMessage());
        }
        return request;
    }

    /**
     * Sets the response handler for this request. This method <b>must</b> be
     * called before calling {@link #sendFile()}.
     * 
     * @param callback
     *            the response handler to be notified when the request fails or
     *            completes
     * 
     * @throws NullPointerException
     *             if <code>callback</code> is <code>null</code>
     */
    public void setCallback(UploadRequestCallback callback) {
        if (callback == null) {
            throw new NullPointerException("callback must be set");
        }
        this.callback = callback;
    }

    /**
     * Sets a request header with the given name and value. If a header with the
     * specified name has already been set then the new value overwrites the
     * current value.
     * 
     * @param header
     *            the name of the header
     * @param value
     *            the value of the header
     * 
     * @throws IllegalArgumentException
     *             if header or value are the empty string or null
     */
    public void setHeader(String header, String value) {
        if (header == null || "".equals(header.trim())) {
            throw new IllegalArgumentException("header must be set");
        }
        if (this.headers == null) {
            this.headers = new HashMap<String, String>();
        }
        this.headers.put(header, value);
    }

    /**
     * Sets the password to use in the request URL. This is ignored if there is
     * no user specified.
     * 
     * @param password
     *            password to use in the request URL
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the number of milliseconds to wait for a request to complete. Should
     * the request timeout, the
     * {@link com.google.gwt.http.client.UploadRequestCallback#onError(Request, Throwable)}
     * method will be called on the callback instance given to the
     * {@link com.google.gwt.http.client.RequestBuilder#sendRequest(String, RequestCallback)}
     * method. The callback method will receive an instance of the
     * {@link com.google.gwt.http.client.RequestTimeoutException} class as its
     * {@link java.lang.Throwable} argument.
     * 
     * @param timeoutMillis
     *            number of milliseconds to wait before canceling the request, a
     *            value of zero disables timeouts
     * 
     * @throws IllegalArgumentException
     *             if the timeout value is negative
     */
    public void setTimeoutMillis(int timeoutMillis) {
        if (timeoutMillis < 0) {
            throw new IllegalArgumentException("Timeouts cannot be negative");
        }
        this.timeoutMillis = timeoutMillis;
    }

    /**
     * Sets the user name that will be used in the request URL.
     * 
     * @param user
     *            user name to use
     */
    public void setUser(String user) {
        this.user = user;
    }
}
