/**
 * Copyright 2010 ArcBees Inc.
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

package com.gwtplatform.samples.basic.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * @author Philippe Beaudoin
 */
public class ResponseView extends ViewImpl implements ResponsePresenter.MyView {

	// private static String html = "<h1>Remote Procedure Call</h1>\n"
	// + "<table align=\"center\">\n" + "  <tr>\n"
	// + "    <td style=\"font-weight:bold;\">Sending name to server:</td>\n"
	// + "  </tr>\n" + "  <tr>\n"
	// + "    <td id=\"textToServerContainer\"></td>\n" + "  </tr>\n"
	// + "  <tr>\n"
	// + "    <td style=\"font-weight:bold;\">Server replies:</td>\n"
	// + "  </tr>\n" + "  <tr>\n"
	// + "    <td id=\"serverResponseContainer\"></td>\n" + "  </tr>\n"
	// + "  <tr>\n" + "    <td id=\"closeButton\"></td>\n" + "  </tr>\n"
	// + "</table>\n";

	// var mySplitResult = myString.split("~");
	//	 
	//	 
	// for (var i=0; i < mySplitResult.length; i++)
	// {
	// document.write("<span id = \"vjeko" + i +
	// "\" style = \"color:grey; font-size:xx-large;\">" + mySplitResult[i] +
	// "</span>");
	// }

	private static String html = "<h1></h1>\n" + "<table align=\"center\">\n"
			+ "  <tr>\n" + "  <tr>\n"
			+ "    <td style=\"font-weight:bold;\"></td>\n" + "  </tr>\n"
			+ "  <tr>\n" + "    <td id=\"serverResponseContainer\"></td>\n"
			+ "  </tr>\n" + "  <tr>\n" + "    <td id=\"closeButton\"></td>\n"
			+ "  </tr>\n" + "</table>\n";

	HTMLPanel panel = new HTMLPanel(html);

	private final Button closeButton;
	private final HTML serverResponseLabel;

	private final Label textToServerLabel;

	private final int refreshDelay;
	private final int retryDelay;

	private Timer fetchUserTimer;

	private String[] mySplitResult;
	private int currentSymbol;

	@Inject
	public ResponseView() {
		closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		textToServerLabel = new Label();
		serverResponseLabel = new HTML();

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		panel.add(closeButton, "closeButton");
		// panel.add(textToServerLabel, "textToServerContainer");
		panel.add(serverResponseLabel, "serverResponseContainer");

		this.refreshDelay = 5000;
		this.retryDelay = 10000;
		this.fetchUserTimer = new Timer() {
			@Override
			public void run() {
				colorize();
			}
		};

		fetchUserTimer.schedule(500);
	}

	@Override
	public Widget asWidget() {
		return panel;
	}

	@Override
	public Button getCloseButton() {
		return closeButton;
	}

	@Override
	public void setServerResponse(String serverResponse) {
		String spanhtml = "";
		// The Symbol "~" need to be picked from parser but I do not yet know
		// how to give client side access to apache commons
		mySplitResult = serverResponse.split("~");
		for (int i = 0; i < mySplitResult.length; i++) {
			spanhtml = spanhtml
					+ ("<span id = \"vjeko" + i
							+ "\" style = \"color:grey; font-size:xx-large;\">"
							+ mySplitResult[i] + "</span>");
		}

		serverResponseLabel.setHTML(spanhtml);
		// serverResponseLabel.setHTML(StringUtils.replace(serverResponse,
		// Parser.SACRED_STRING, ""));
		// colorize(mySplitResult);
		fetchUserTimer.schedule(500);
		currentSymbol = 0;
		// colorize();
	}

	@Override
	public void colorize() {

		int resultSize = 0;
		if (mySplitResult != null)
			resultSize = mySplitResult.length;

		if (resultSize > 1) {
			this.fetchUserTimer = new Timer() {
				@Override
				public void run() {
					fetchUserTimer.schedule(500);
					// currentSymbol = 0;
					renderColours();
				}
			};

			// renderColours();
		}

	}

	private void renderColours() {
		// fetchUserTimer.schedule(500);
		// if (!document.all) return;
		// for (int y = 0; y < mySplitResult.length; y++) {
		if (currentSymbol > mySplitResult.length)
			currentSymbol = 0;
		for (int i = 0; i < mySplitResult.length; i++) {
			if (currentSymbol == i) {
				DOM.setStyleAttribute(DOM.getElementById("vjeko" + i), "color",
						"blue");
				break;
			} else {
				// document.all["vjeko" + i].style.color = '888888';
				DOM.setStyleAttribute(DOM.getElementById("vjeko" + i), "color",
						"grey");
			}
		}
		// colorTimer = window.setTimeout("colorize()", 500);
		currentSymbol++;
		// }
		// currentSymbol = 0;
	}
	// @Override
	// public void setTextToServer(String textToServer) {
	// textToServerLabel.setText(textToServer);
	// }

}
