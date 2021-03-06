/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.richfaces.ui.input.autocomplete;

import org.richfaces.ui.common.HtmlConstants;
import org.richfaces.util.HtmlUtil;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import java.io.IOException;

public class AutocompleteTableLayoutStrategy extends AbstractAutocompleteLayoutStrategy implements AutocompleteEncodeStrategy {
    public void encodeFakeItem(FacesContext facesContext, UIComponent component) throws IOException {
        ResponseWriter responseWriter = facesContext.getResponseWriter();
        responseWriter.startElement(HtmlConstants.TR_ELEMENT, component);
        responseWriter.startElement(HtmlConstants.TD_ELEM, component);
        responseWriter.writeAttribute(HtmlConstants.STYLE_ATTRIBUTE, "display:none", null);
        responseWriter.endElement(HtmlConstants.TD_ELEM);
        responseWriter.endElement(HtmlConstants.TR_ELEMENT);
    }

    public void encodeItemsContainerBegin(FacesContext facesContext, UIComponent component) throws IOException {
        ResponseWriter responseWriter = facesContext.getResponseWriter();
        responseWriter.startElement(HtmlConstants.TABLE_ELEMENT, component);
        responseWriter.writeAttribute(HtmlConstants.ID_ATTRIBUTE, getContainerElementId(facesContext, component), null);
        responseWriter.writeAttribute(HtmlConstants.CLASS_ATTRIBUTE, "rf-au-tbl", null);
        responseWriter.startElement(HtmlConstants.TBODY_ELEMENT, component);
    }

    public void encodeItemsContainerEnd(FacesContext facesContext, UIComponent component) throws IOException {
        ResponseWriter responseWriter = facesContext.getResponseWriter();
        responseWriter.endElement(HtmlConstants.TBODY_ELEMENT);
        responseWriter.endElement(HtmlConstants.TABLE_ELEMENT);
    }

    public void encodeItemBegin(FacesContext facesContext, UIComponent component) throws IOException {
        ResponseWriter writer = facesContext.getResponseWriter();
        writer.startElement(HtmlConstants.TR_ELEMENT, component);
        writer.writeAttribute(HtmlConstants.CLASS_ATTRIBUTE, "rf-au-itm", null);
        writer.startElement(HtmlConstants.TD_ELEM, component);
    }

    private void encodeItemChildBegin(FacesContext facesContext, UIComponent component) throws IOException {
        ResponseWriter writer = facesContext.getResponseWriter();
        writer.startElement(HtmlConstants.TD_ELEM, component);
    }

    private void encodeItemChildEnd(FacesContext facesContext, UIComponent component) throws IOException {
        ResponseWriter writer = facesContext.getResponseWriter();
        writer.endElement(HtmlConstants.TD_ELEM);
    }

    public void encodeItemEnd(FacesContext facesContext, UIComponent component) throws IOException {
        ResponseWriter writer = facesContext.getResponseWriter();
        writer.endElement(HtmlConstants.TD_ELEM);
        writer.endElement(HtmlConstants.TR_ELEMENT);
    }

    public void encodeItem(FacesContext facesContext, UIComponent component) throws IOException {
        ResponseWriter writer = facesContext.getResponseWriter();
        writer.startElement(HtmlConstants.TR_ELEMENT, component);
        writer.writeAttribute(HtmlConstants.CLASS_ATTRIBUTE, "rf-au-itm", null);
        for (UIComponent child : component.getChildren()) {
            if (child instanceof UIColumn) {
                encodeItemChildBegin(facesContext, component);
                String styleClass = "rf-au-fnt rf-au-inp";
                styleClass = HtmlUtil.concatClasses(styleClass, child.getAttributes().get("styleClass"));
                writer.writeAttribute(HtmlConstants.CLASS_ATTRIBUTE, styleClass, null);
                child.encodeAll(facesContext);
                encodeItemChildEnd(facesContext, component);
            }
        }
        writer.endElement(HtmlConstants.TR_ELEMENT);
    }
}
