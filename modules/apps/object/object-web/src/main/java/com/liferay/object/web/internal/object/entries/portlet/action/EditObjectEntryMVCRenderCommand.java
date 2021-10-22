/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.object.web.internal.object.entries.portlet.action;

import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.security.permission.resource.ObjectEntryModelResourcePermissionTracker;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectLayoutLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.web.internal.object.entries.display.context.ObjectEntryDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Marco Leo
 */
public class EditObjectEntryMVCRenderCommand implements MVCRenderCommand {

	public EditObjectEntryMVCRenderCommand(
		DDMFormRenderer ddmFormRenderer, ItemSelector itemSelector,
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryModelResourcePermissionTracker
			objectEntryModelResourcePermissionTracker,
		ObjectEntryService objectEntryService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectLayoutLocalService objectLayoutLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		Portal portal) {

		_ddmFormRenderer = ddmFormRenderer;
		_itemSelector = itemSelector;
		_listTypeEntryLocalService = listTypeEntryLocalService;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryModelResourcePermissionTracker =
			objectEntryModelResourcePermissionTracker;
		_objectEntryService = objectEntryService;
		_objectFieldLocalService = objectFieldLocalService;
		_objectLayoutLocalService = objectLayoutLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
		_portal = portal;
	}

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			new ObjectEntryDisplayContext(
				_ddmFormRenderer, _portal.getHttpServletRequest(renderRequest),
				_itemSelector, _listTypeEntryLocalService,
				_objectDefinitionLocalService,
				_objectEntryModelResourcePermissionTracker, _objectEntryService,
				_objectFieldLocalService, _objectLayoutLocalService,
				_objectRelationshipLocalService));

		return "/object_entries/edit_object_entry.jsp";
	}

	private final DDMFormRenderer _ddmFormRenderer;
	private final ItemSelector _itemSelector;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryModelResourcePermissionTracker
		_objectEntryModelResourcePermissionTracker;
	private final ObjectEntryService _objectEntryService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectLayoutLocalService _objectLayoutLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final Portal _portal;

}