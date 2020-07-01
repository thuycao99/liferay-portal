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

import ClayButton from '@clayui/button';
import moment from 'moment';
import React, {useContext} from 'react';

import Color from '../color/Color.es';
import {SidebarContext} from '../sidebar/SidebarContext.es';

export default ({data, field, summary, totalEntries, type}) => {
	const {portletNamespace, toggleSidebar} = useContext(SidebarContext);

	const formatDate = (field) => {
		moment.locale(themeDisplay.getLanguageId().split('_', 1)[0]);
		const m = moment(field);
		field = m.format('L');

		return field;
	};

	return (
		<div className="field-list">
			<ul className="entries-list">
				{Array.isArray(data) &&
					data.map((field, index) => (
						<li key={index}>
							{type == 'color' ? (
								<Color hexColor={field} />
							) : type == 'date' ? formatDate(field) : (
								field
							)}
						</li>
					))}

				{data.length == 5 && totalEntries > 5 ? (
					<li id={`${portletNamespace}-see-more`} key={'see-more'}>
						<ClayButton
							displayType="link"
							onClick={() =>
								toggleSidebar(
									field,
									summary,
									totalEntries,
									type
								)
							}
						>
							{Liferay.Language.get('see-all-entries')}
						</ClayButton>
					</li>
				) : null}
			</ul>
		</div>
	);
};
