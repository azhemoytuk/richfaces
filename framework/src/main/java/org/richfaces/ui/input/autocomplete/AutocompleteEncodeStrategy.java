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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;

public interface AutocompleteEncodeStrategy {
    void encodeItemsContainerBegin(FacesContext facesContext, UIComponent component) throws IOException;

    void encodeItemsContainerEnd(FacesContext facesContext, UIComponent component) throws IOException;

    void encodeItemBegin(FacesContext facesContext, UIComponent component) throws IOException;

    void encodeItemEnd(FacesContext facesContext, UIComponent component) throws IOException;

    void encodeItem(FacesContext facesContext, UIComponent component) throws IOException;

    void encodeFakeItem(FacesContext facesContext, UIComponent component) throws IOException;

    String getContainerElementId(FacesContext facesContext, UIComponent component);
}
