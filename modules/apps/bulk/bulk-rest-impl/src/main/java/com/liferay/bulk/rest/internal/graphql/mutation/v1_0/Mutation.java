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

package com.liferay.bulk.rest.internal.graphql.mutation.v1_0;

import com.liferay.bulk.rest.dto.v1_0.DocumentBulkSelection;
import com.liferay.bulk.rest.dto.v1_0.Keyword;
import com.liferay.bulk.rest.dto.v1_0.KeywordBulkSelection;
import com.liferay.bulk.rest.dto.v1_0.MessageSelection;
import com.liferay.bulk.rest.dto.v1_0.TaxonomyCategoryBulkSelection;
import com.liferay.bulk.rest.dto.v1_0.TaxonomyVocabulary;
import com.liferay.bulk.rest.resource.v1_0.KeywordResource;
import com.liferay.bulk.rest.resource.v1_0.MessageSelectionResource;
import com.liferay.bulk.rest.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.bulk.rest.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.pagination.Page;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Collection;

import javax.annotation.Generated;

import javax.ws.rs.core.Response;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setKeywordResourceComponentServiceObjects(
		ComponentServiceObjects<KeywordResource>
			keywordResourceComponentServiceObjects) {

		_keywordResourceComponentServiceObjects =
			keywordResourceComponentServiceObjects;
	}

	public static void setMessageSelectionResourceComponentServiceObjects(
		ComponentServiceObjects<MessageSelectionResource>
			messageSelectionResourceComponentServiceObjects) {

		_messageSelectionResourceComponentServiceObjects =
			messageSelectionResourceComponentServiceObjects;
	}

	public static void setTaxonomyCategoryResourceComponentServiceObjects(
		ComponentServiceObjects<TaxonomyCategoryResource>
			taxonomyCategoryResourceComponentServiceObjects) {

		_taxonomyCategoryResourceComponentServiceObjects =
			taxonomyCategoryResourceComponentServiceObjects;
	}

	public static void setTaxonomyVocabularyResourceComponentServiceObjects(
		ComponentServiceObjects<TaxonomyVocabularyResource>
			taxonomyVocabularyResourceComponentServiceObjects) {

		_taxonomyVocabularyResourceComponentServiceObjects =
			taxonomyVocabularyResourceComponentServiceObjects;
	}

	@GraphQLInvokeDetached
	public Response patchKeywordBatch(
			@GraphQLName("KeywordBulkSelection") KeywordBulkSelection
				keywordBulkSelection)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> keywordResource.patchKeywordBatch(
				keywordBulkSelection));
	}

	@GraphQLInvokeDetached
	public Response putKeywordBatch(
			@GraphQLName("KeywordBulkSelection") KeywordBulkSelection
				keywordBulkSelection)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> keywordResource.putKeywordBatch(
				keywordBulkSelection));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Keyword> postKeywordsCommonPage(
			@GraphQLName("DocumentBulkSelection") DocumentBulkSelection
				documentBulkSelection)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> {
				Page paginationPage = keywordResource.postKeywordsCommonPage(
					documentBulkSelection);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageSelection postBulkSelection(
			@GraphQLName("DocumentBulkSelection") DocumentBulkSelection
				documentBulkSelection)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageSelectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageSelectionResource ->
				messageSelectionResource.postBulkSelection(
					documentBulkSelection));
	}

	@GraphQLInvokeDetached
	public Response patchTaxonomyCategoryBatch(
			@GraphQLName("TaxonomyCategoryBulkSelection")
				TaxonomyCategoryBulkSelection taxonomyCategoryBulkSelection)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyCategoryResource ->
				taxonomyCategoryResource.patchTaxonomyCategoryBatch(
					taxonomyCategoryBulkSelection));
	}

	@GraphQLInvokeDetached
	public Response putTaxonomyCategoryBatch(
			@GraphQLName("TaxonomyCategoryBulkSelection")
				TaxonomyCategoryBulkSelection taxonomyCategoryBulkSelection)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyCategoryResource ->
				taxonomyCategoryResource.putTaxonomyCategoryBatch(
					taxonomyCategoryBulkSelection));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<TaxonomyVocabulary>
			postContentSpaceTaxonomyVocabulariesCommonPage(
				@GraphQLName("content-space-id") Long contentSpaceId,
				@GraphQLName("DocumentBulkSelection") DocumentBulkSelection
					documentBulkSelection)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyVocabularyResource -> {
				Page paginationPage =
					taxonomyVocabularyResource.
						postContentSpaceTaxonomyVocabulariesCommonPage(
							contentSpaceId, documentBulkSelection);

				return paginationPage.getItems();
			});
	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(KeywordResource keywordResource)
		throws Exception {

		keywordResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			MessageSelectionResource messageSelectionResource)
		throws Exception {

		messageSelectionResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			TaxonomyCategoryResource taxonomyCategoryResource)
		throws Exception {

		taxonomyCategoryResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			TaxonomyVocabularyResource taxonomyVocabularyResource)
		throws Exception {

		taxonomyVocabularyResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private static ComponentServiceObjects<KeywordResource>
		_keywordResourceComponentServiceObjects;
	private static ComponentServiceObjects<MessageSelectionResource>
		_messageSelectionResourceComponentServiceObjects;
	private static ComponentServiceObjects<TaxonomyCategoryResource>
		_taxonomyCategoryResourceComponentServiceObjects;
	private static ComponentServiceObjects<TaxonomyVocabularyResource>
		_taxonomyVocabularyResourceComponentServiceObjects;

}