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
String redirect = ParamUtil.getString(request, "redirect");

JournalEditDDMStructuresDisplayContext journalEditDDMStructuresDisplayContext = new JournalEditDDMStructuresDisplayContext(request);

DDMStructure ddmStructure = journalEditDDMStructuresDisplayContext.getDDMStructure();

long groupId = BeanParamUtil.getLong(ddmStructure, request, "groupId", scopeGroupId);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(PortalUtil.escapeRedirect(redirect));

renderResponse.setTitle((ddmStructure != null) ? LanguageUtil.format(request, "edit-x", ddmStructure.getName(locale), false) : LanguageUtil.get(request, "new-structure"));
%>

<portlet:actionURL name="/journal/add_ddm_structure" var="addDDMStructureURL">
	<portlet:param name="mvcPath" value="/edit_ddm_structure.jsp" />
</portlet:actionURL>

<portlet:actionURL name="/journal/update_ddm_structure" var="updateDDMStructureURL">
	<portlet:param name="mvcPath" value="/edit_ddm_structure.jsp" />
</portlet:actionURL>

<aui:form action="<%= (ddmStructure == null) ? addDDMStructureURL : updateDDMStructureURL %>" cssClass="edit-article-form" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveDDMStructure();" %>'>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="ddmStructureId" type="hidden" value="<%= journalEditDDMStructuresDisplayContext.getDDMStructureId() %>" />
	<aui:input name="definition" type="hidden" />

	<aui:model-context bean="<%= ddmStructure %>" model="<%= DDMStructure.class %>" />

	<nav class="component-tbar subnav-tbar-light tbar tbar-article">
		<div class="container-fluid container-fluid-max-xl">
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">
					<aui:input cssClass="form-control-inline" label="" name="name" placeholder='<%= LanguageUtil.format(request, "untitled-x", "structure") %>' wrapperCssClass="article-content-title mb-0" />
				</li>
				<li class="tbar-item">
					<div class="journal-article-button-row tbar-section text-right">
						<a class="btn btn-outline-borderless btn-outline-secondary btn-sm mr-3" href="<%= HtmlUtil.escape(redirect) %>">
							<liferay-ui:message key="cancel" />
						</a>

						<aui:button cssClass="btn-sm mr-3" type="submit" value="save" />
					</div>
				</li>
			</ul>
		</div>
	</nav>

	<div class="container-fluid container-fluid-max-xl container-view">
		<div class="sheet">
			<liferay-ui:error exception="<%= DDMFormLayoutValidationException.class %>" message="please-enter-a-valid-form-layout" />

			<liferay-ui:error exception="<%= DDMFormLayoutValidationException.MustNotDuplicateFieldName.class %>">

				<%
				DDMFormLayoutValidationException.MustNotDuplicateFieldName mndfn = (DDMFormLayoutValidationException.MustNotDuplicateFieldName)errorException;
				%>

				<liferay-ui:message arguments="<%= HtmlUtil.escape(StringUtil.merge(mndfn.getDuplicatedFieldNames(), StringPool.COMMA_AND_SPACE)) %>" key="the-definition-field-name-x-was-defined-more-than-once" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= DDMFormValidationException.class %>" message="please-enter-a-valid-form-definition" />

			<liferay-ui:error exception="<%= DDMFormValidationException.MustNotDuplicateFieldName.class %>">

				<%
				DDMFormValidationException.MustNotDuplicateFieldName mndfn = (DDMFormValidationException.MustNotDuplicateFieldName)errorException;
				%>

				<liferay-ui:message arguments="<%= HtmlUtil.escape(mndfn.getFieldName()) %>" key="the-definition-field-name-x-was-defined-more-than-once" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= DDMFormValidationException.MustSetFieldsForForm.class %>" message="please-add-at-least-one-field" />

			<liferay-ui:error exception="<%= DDMFormValidationException.MustSetOptionsForField.class %>">

				<%
				DDMFormValidationException.MustSetOptionsForField msoff = (DDMFormValidationException.MustSetOptionsForField)errorException;
				%>

				<liferay-ui:message arguments="<%= HtmlUtil.escape(msoff.getFieldName()) %>" key="at-least-one-option-should-be-set-for-field-x" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= DDMFormValidationException.MustSetValidCharactersForFieldName.class %>">

				<%
				DDMFormValidationException.MustSetValidCharactersForFieldName msvcffn = (DDMFormValidationException.MustSetValidCharactersForFieldName)errorException;
				%>

				<liferay-ui:message arguments="<%= HtmlUtil.escape(msvcffn.getFieldName()) %>" key="invalid-characters-were-defined-for-field-name-x" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= LocaleException.class %>">

				<%
				LocaleException le = (LocaleException)errorException;
				%>

				<c:if test="<%= le.getType() == LocaleException.TYPE_CONTENT %>">
					<liferay-ui:message arguments="<%= new String[] {StringUtil.merge(le.getSourceAvailableLocales(), StringPool.COMMA_AND_SPACE), StringUtil.merge(le.getTargetAvailableLocales(), StringPool.COMMA_AND_SPACE)} %>" key="the-default-language-x-does-not-match-the-portal's-available-languages-x" />
				</c:if>
			</liferay-ui:error>

			<liferay-ui:error exception="<%= StructureDefinitionException.class %>" message="please-enter-a-valid-definition" />
			<liferay-ui:error exception="<%= StructureDuplicateElementException.class %>" message="please-enter-unique-structure-field-names-(including-field-names-inherited-from-the-parent-structure)" />
			<liferay-ui:error exception="<%= StructureNameException.class %>" message="please-enter-a-valid-name" />

			<c:if test="<%= (ddmStructure != null) && (DDMStorageLinkLocalServiceUtil.getStructureStorageLinksCount(journalEditDDMStructuresDisplayContext.getDDMStructureId()) > 0) %>">
				<div class="alert alert-warning">
					<liferay-ui:message key="there-are-content-references-to-this-structure.-you-may-lose-data-if-a-field-name-is-renamed-or-removed" />
				</div>
			</c:if>

			<c:if test="<%= (journalEditDDMStructuresDisplayContext.getDDMStructureId() > 0) && (DDMTemplateLocalServiceUtil.getTemplatesCount(null, PortalUtil.getClassNameId(DDMStructure.class), journalEditDDMStructuresDisplayContext.getDDMStructureId()) > 0) %>">
				<div class="alert alert-info">
					<liferay-ui:message key="there-are-template-references-to-this-structure.-please-update-them-if-a-field-name-is-renamed-or-removed" />
				</div>
			</c:if>

			<c:if test="<%= (ddmStructure != null) && (groupId != scopeGroupId) %>">
				<div class="alert alert-warning">
					<liferay-ui:message key="this-structure-does-not-belong-to-this-site.-you-may-affect-other-sites-if-you-edit-this-structure" />
				</div>
			</c:if>

			<liferay-frontend:form-navigator
				formModelBean="<%= ddmStructure %>"
				id="<%= JournalWebConstants.FORM_NAVIGATOR_ID_JOURNAL_DDM_STRUCTURE %>"
				showButtons="<%= false %>"
			/>

			<%@ include file="/form_builder.jspf" %>
		</div>
	</div>
</aui:form>

<aui:script>
	function <portlet:namespace />openParentDDMStructureSelector() {
		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					modal: true
				},
				eventName: '<portlet:namespace />selectDDMStructure',
				id: '<portlet:namespace />selectDDMStructure',
				title: '<%= UnicodeLanguageUtil.get(request, "select-structure") %>',
				uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/select_ddm_structure.jsp" /><portlet:param name="classPK" value="<%= String.valueOf(journalEditDDMStructuresDisplayContext.getDDMStructureId()) %>" /></portlet:renderURL>'
			},
			function(event) {
				var form = document.<portlet:namespace />fm;

				Liferay.Util.setFormValues(
					form,
					{
						parentDDMStructureId: event.ddmstructureid,
						parentDDMStructureName: Liferay.Util.unescape(event.name)
					}
				);

				var removeParentDDMStructureButton = Liferay.Util.getFormElement(form, 'removeParentDDMStructureButton');

				if (removeParentDDMStructureButton) {
					Liferay.Util.toggleDisabled(removeParentDDMStructureButton, false);
				}
			}
		);
	}

	function <portlet:namespace />removeParentDDMStructure() {
		var form = document.<portlet:namespace />fm;

		Liferay.Util.setFormValues(
			form,
			{
				parentDDMStructureId: '',
				parentDDMStructureName: ''
			}
		);

		var removeParentDDMStructureButton = Liferay.Util.getFormElement(form, 'removeParentDDMStructureButton');

		if (removeParentDDMStructureButton) {
			Liferay.Util.toggleDisabled(removeParentDDMStructureButton, true);
		}
	}

	function <portlet:namespace />saveDDMStructure() {
		Liferay.Util.postForm(
			document.<portlet:namespace />fm,
			{
				data: {
					definition: <portlet:namespace />formBuilder.getContentValue()
				}
			}
		);
	}
</aui:script>