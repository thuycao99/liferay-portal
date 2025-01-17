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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import React, {useState} from 'react';

/**
 * Manipulates small amounts of data with a form shown inside a modal.
 */
const SaveTemplateModal = ({
	alert,
	closeModal,
	dialogTitle,
	formDataQuerySelector,
	formSubmitURL,
	initialVisible,
	modalFieldLabel,
	modalFieldName,
	modalFieldPlaceholder,
	namespace,
	onFormSuccess,
}) => {
	const isMounted = useIsMounted();
	const [errorMessage, setErrorMessage] = useState();
	const [loadingResponse, setLoadingResponse] = useState(false);
	const [visible, setVisible] = useState(initialVisible);
	const [inputValue, setInputValue] = useState('');

	const handleFormError = (responseContent) => {
		setErrorMessage(responseContent?.error || '');
	};

	const _handleSubmit = async (event) => {
		event.preventDefault();

		const mainFormData = document.querySelector(formDataQuerySelector);

		const modalFormData = document.getElementById(namespace + 'form');

		const updateData = {};

		updateData[namespace + modalFieldName] =
			modalFormData[namespace + modalFieldName].value;

		Liferay.Util.setFormValues(mainFormData, updateData);

		const formData = new FormData(mainFormData);
		try {
			const response = await fetch(formSubmitURL, {
				body: formData,
				method: 'POST',
			});

			const responseJson = await response.json();

			if (isMounted()) {
				if (responseJson.error) {
					setLoadingResponse(false);

					handleFormError(responseJson);
				}
				else {
					setVisible(false);

					closeModal();

					if (onFormSuccess) {
						onFormSuccess(responseJson);
					}
				}
			}
		}
		catch (error) {
			handleFormError();
		}
		finally {
			setLoadingResponse(true);
		}
	};

	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);

			closeModal();
		},
	});

	return (
		visible && (
			<ClayModal observer={observer} size="md">
				<ClayModal.Header>{dialogTitle}</ClayModal.Header>

				<form id={`${namespace}form`} onSubmit={_handleSubmit}>
					<ClayModal.Body>
						{alert && alert.message && alert.title && (
							<ClayAlert
								displayType={alert.style}
								title={alert.title}
							>
								{alert.message}
							</ClayAlert>
						)}

						<div
							className={`form-group ${
								errorMessage ? 'has-error' : ''
							}`}
						>
							<label
								className="control-label"
								htmlFor={`${namespace}${modalFieldName}`}
							>
								{modalFieldLabel}

								<span className="reference-mark">
									<ClayIcon symbol="asterisk" />
								</span>
							</label>

							<input
								autoFocus
								className="form-control"
								disabled={loadingResponse}
								id={`${namespace}${modalFieldName}`}
								name={`${namespace}${modalFieldName}`}
								onChange={(event) =>
									setInputValue(event.target.value)
								}
								placeholder={modalFieldPlaceholder}
								required
								type="text"
								value={inputValue}
							/>

							{errorMessage && (
								<div className="form-feedback-item">
									<ClayIcon
										className="inline-item inline-item-before"
										symbol="exclamation-full"
									/>

									{errorMessage}
								</div>
							)}
						</div>
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<ClayButton.Group spaced>
								<ClayButton
									disabled={loadingResponse}
									displayType="secondary"
									onClick={onClose}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>

								<ClayButton
									disabled={
										loadingResponse ||
										inputValue.length == 0
									}
									displayType="primary"
									type="submit"
								>
									{loadingResponse && (
										<span className="inline-item inline-item-before">
											<span
												aria-hidden="true"
												className="loading-animation"
											></span>
										</span>
									)}

									{Liferay.Language.get('save')}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</form>
			</ClayModal>
		)
	);
};

export default SaveTemplateModal;
