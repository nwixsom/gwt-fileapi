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
package com.gwtpro.html5.fileapi.client;

import com.google.gwt.http.client.Request;

@SuppressWarnings("serial")
public class UploadRequestTimeoutException extends Throwable {

    private static String formatMessage(int timeoutMillis) {
        return "A request timeout has expired after "
                + Integer.toString(timeoutMillis) + " ms";
    }

    /**
     * Time, in milliseconds, of the timeout.
     */
    private final int timeoutMillis;
    /**
     * Request object which experienced the timed out.
     */
    private final UploadRequest request;

    /**
     * Constructs a timeout exception for the given {@link Request}.
     * 
     * @param request
     *            the request which timed out
     * @param timeoutMillis
     *            the number of milliseconds which expired
     */
    public UploadRequestTimeoutException(UploadRequest request, int timeoutMillis) {
        super(formatMessage(timeoutMillis));
        this.request = request;
        this.timeoutMillis = timeoutMillis;
    }

    /**
     * Returns the {@link UploadRequest} instance which timed out.
     * 
     * @return the {@link UploadRequest} instance which timed out
     */
    public UploadRequest getRequest() {
        return this.request;
    }

    /**
     * Returns the request timeout value in milliseconds.
     * 
     * @return the request timeout value in milliseconds
     */
    public int getTimeoutMillis() {
        return this.timeoutMillis;
    }
}
