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
package com.gwtpro.html5.fileapi.client.upload;

import com.google.gwt.http.client.Header;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Timer;
import com.google.gwt.xhr.client.XMLHttpRequest;

public class UploadRequest {

    private static Header[] getHeaders(XMLHttpRequest xmlHttp) {
        String allHeaders = xmlHttp.getAllResponseHeaders();
        String[] unparsedHeaders = allHeaders.split("\n");
        Header[] parsedHeaders = new Header[unparsedHeaders.length];
        for (int i = 0, n = unparsedHeaders.length; i < n; ++i) {
            String unparsedHeader = unparsedHeaders[i];
            if (unparsedHeader.length() == 0) {
                continue;
            }
            int endOfNameIdx = unparsedHeader.indexOf(':');
            if (endOfNameIdx < 0) {
                continue;
            }
            final String name = unparsedHeader.substring(0, endOfNameIdx)
                    .trim();
            final String value = unparsedHeader.substring(endOfNameIdx + 1)
                    .trim();
            Header header = new Header() {

                @Override
                public String getName() {
                    return name;
                }

                @Override
                public String getValue() {
                    return value;
                }

                @Override
                public String toString() {
                    return name + " : " + value;
                }
            };
            parsedHeaders[i] = header;
        }
        return parsedHeaders;
    }

    private final int timeoutMillis;
    private XMLHttpRequest2 xmlHttpRequest;
    private Timer timer;

    public UploadRequest(XMLHttpRequest2 xmlHttpRequest, int timeoutMillis,
            final UploadRequestCallback callback) {
        this.timeoutMillis = timeoutMillis;
        this.xmlHttpRequest = xmlHttpRequest;
        if (timeoutMillis > 0) {
            this.timer = new Timer() {

                @Override
                public void run() {
                    fireOnTimeout(callback);
                }
            };
            this.timer.schedule(timeoutMillis);
        } else {
            this.timer = null;
        }
    }

    /**
     * Cancels a pending request. If the request has already been canceled or if
     * it has timed out no action is taken.
     */
    public void cancel() {
        if (this.xmlHttpRequest != null) {
            XMLHttpRequest xmlHttp = this.xmlHttpRequest;
            this.xmlHttpRequest = null;
            xmlHttp.clearOnReadyStateChange();
            xmlHttp.abort();
            if (this.timer != null) {
                this.timer.cancel();
            }
        }
    }

    /**
     * Returns true if this request is waiting for a response.
     * 
     * @return true if this request is waiting for a response
     */
    public boolean isPending() {
        if (this.xmlHttpRequest == null) {
            return false;
        }
        int readyState = this.xmlHttpRequest.getReadyState();
        switch (readyState) {
        case XMLHttpRequest.OPENED:
        case XMLHttpRequest.HEADERS_RECEIVED:
        case XMLHttpRequest.LOADING:
            return true;
        }
        return false;
    }

    private Response createResponse(final XMLHttpRequest2 xhr) {
        Response response = new Response() {

            @Override
            public String getHeader(String header) {
                return xhr.getResponseHeader(header);
            }

            @Override
            public Header[] getHeaders() {
                return UploadRequest.getHeaders(xhr);
            }

            @Override
            public String getHeadersAsString() {
                return xhr.getAllResponseHeaders();
            }

            @Override
            public int getStatusCode() {
                return xhr.getStatus();
            }

            @Override
            public String getStatusText() {
                return xhr.getStatusText();
            }

            @Override
            public String getText() {
                return xhr.getResponseText();
            }
        };
        return response;
    }

    private void fireOnTimeout(UploadRequestCallback callback) {
        if (this.xmlHttpRequest == null) {
            return;
        }
        cancel();
        callback.onError(this, new UploadRequestTimeoutException(this,
                this.timeoutMillis));
    }

    private native String getBrowserSpecificFailure(XMLHttpRequest xhr) /*-{
        try {
          if (xhr.status === undefined) {
            return "XmlHttpRequest.status == undefined, please see Safari bug " +
                   "http://bugs.webkit.org/show_bug.cgi?id=3810 for more details";
          }
          return null;
        } catch (e) {
          return "Unable to read XmlHttpRequest.status; likely causes are a " +
                 "networking error or bad cross-domain request. Please see " +
                 "https://bugzilla.mozilla.org/show_bug.cgi?id=238559 for more " +
                 "details";
        }
    }-*/;

    void fireOnResponseReceived(UploadRequestCallback callback) {
        if (this.xmlHttpRequest == null) {
            return;
        }
        if (this.timer != null) {
            this.timer.cancel();
        }
        final XMLHttpRequest2 xhr = this.xmlHttpRequest;
        this.xmlHttpRequest = null;
        String errorMsg = getBrowserSpecificFailure(xhr);
        if (errorMsg != null) {
            Throwable exception = new RuntimeException(errorMsg);
            callback.onError(this, exception);
        } else {
            Response response = createResponse(xhr);
            callback.onResponseReceived(this, response);
        }
    }
}
