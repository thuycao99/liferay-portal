<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
JournalEditDDMStructuresDisplayContext journalEditDDMStructuresDisplayContext = new JournalEditDDMStructuresDisplayContext(request);

DDMStructure ddmStructure = journalEditDDMStructuresDisplayContext.getDDMStructure();
%>

<aui:model-context bean="<%= ddmStructure %>" model="<%= DDMStructure.class %>" />

<aui:input name="storageType" type="hidden" value="<%= journalEditDDMStructuresDisplayContext.getStorageType() %>" />

<c:if test="<%= !journalEditDDMStructuresDisplayContext.autogenerateDDMStructureKey() %>">
	<aui:input disabled="<%= ddmStructure != null %>" name="ddmStructureKey" />
</c:if>

<aui:input name="description" />

<aui:input name="parentDDMStructureId" type="hidden" value="<%= journalEditDDMStructuresDisplayContext.getParentDDMStructureId() %>" />

<label class="control-label" for="<portlet:namespace />parentDDMStructureName">
	<liferay-ui:message key="parent-structure" />
</label>

<aui:input disabled="<%= true %>" label="" name="parentDDMStructureName" type="text" value="<%= journalEditDDMStructuresDisplayContext.getParentDDMStructureName() %>" />

<div class="btn-group">
	<div class="btn-group-item">
		<aui:button onClick='<%= renderResponse.getNamespace() + "openParentDDMStructureSelector();" %>' value="select" />
	</div>

	<div class="btn-group-item">
		<aui:button disabled="<%= Validator.isNull(journalEditDDMStructuresDisplayContext.getParentDDMStructureName()) %>" name="removeParentDDMStructureButton" onClick='<%= renderResponse.getNamespace() + "removeParentDDMStructure();" %>' value="remove" />
	</div>
</div>

<c:if test="<%= ddmStructure != null %>">
	<portlet:resourceURL id="/journal/get_ddm_structure" var="getDDMStructureURL">
		<portlet:param name="ddmStructureId" value="<%= String.valueOf(journalEditDDMStructuresDisplayContext.getDDMStructureId()) %>" />
	</portlet:resourceURL>

	<aui:input name="url" type="resource" value="<%= getDDMStructureURL %>" />

	<%
	Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
	%>

	<aui:input name="webDavURL" type="resource" value="<%= ddmStructure.getWebDavURL(themeDisplay, WebDAVUtil.getStorageToken(portlet)) %>" />
</c:if>