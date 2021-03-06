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

(function($, rf) {
    rf.ui = rf.ui || {};

    var defaultOptions = {
        mode : "server",
        cssRoot : "ddm",
        cssClasses : {}
    }

    // constructor definition

    rf.ui.MenuItem = function(componentId, options) {
        this.options = {};
        $.extend(this.options, defaultOptions, options || {});
        $super.constructor.call(this, componentId);
        $.extend(this.options.cssClasses, buildCssClasses.call(this, this.options.cssRoot));
        this.attachToDom(componentId);
        this.element = $(rf.getDomElement(componentId));
        rf.Event.bindById(this.id, 'click', this.__clickHandler, this);
        rf.Event.bindById(this.id, 'mouseenter', this.select, this);
        rf.Event.bindById(this.id, 'mouseleave', this.unselect, this);
        this.selected = false;
    };

    var buildCssClasses = function(cssRoot) {
        var cssClasses = {
            itemCss : "rf-" +cssRoot+ "-itm",
            selectItemCss : "rf-" +cssRoot+ "-itm-sel",
            unselectItemCss : "rf-" +cssRoot+ "-itm-unsel",
            labelCss: "rf-" +cssRoot+ "-lbl"
        }
        return cssClasses;
    }

    rf.BaseComponent.extend(rf.ui.MenuItem);

    // define super class link
    var $super = rf.ui.MenuItem.$super;

    $.extend(rf.ui.MenuItem.prototype, (function() {

        return {
            name : "MenuItem",
            select : function() {
                this.element.removeClass(this.options.cssClasses.unselectItemCss);
                this.element.addClass(this.options.cssClasses.selectItemCss);
                this.selected = true;
            },
            unselect : function() {
                this.element.removeClass(this.options.cssClasses.selectItemCss);
                this.element.addClass(this.options.cssClasses.unselectItemCss);
                this.selected = false;
            },
            activate : function() {
                this.invokeEvent('click', rf.getDomElement(this.id));
            },

            isSelected : function() {
                return this.selected;
            },

            __clickHandler : function(e) {
                if ($(e.target).is(":input:not(:button):not(:reset):not(:submit)")) {
                    return;
                }

                var parentMenu = this.__getParentMenu();
                if (parentMenu) {
                    parentMenu.processItem(this.element);
                }
                
                var item = rf.getDomElement(this.id);
                var params = this.options.params;
                var form = this.__getParentForm(item);
                var itemId = {};
                itemId[item.id] = item.id;
                $.extend(itemId, params || {});
                e.form = form;
                e.itemId = itemId;
                this.options.onClickHandler.call(this, e);
            },

            __getParentForm : function(item) {
                return $($(item).parents("form").get(0));
            },

            __getParentMenu : function() {
                var menu = this.element.parents('div.' + this.options.cssClasses.labelCss);
                if (menu && menu.length > 0)
                    return rf.$(menu);
                else
                    return null;
            }
        };
    })());

})(jQuery, RichFaces);