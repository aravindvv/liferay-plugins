/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.socialcoding.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.socialcoding.NoSuchSVNRepositoryException;
import com.liferay.socialcoding.model.SVNRepository;
import com.liferay.socialcoding.model.impl.SVNRepositoryImpl;
import com.liferay.socialcoding.model.impl.SVNRepositoryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the s v n repository service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SVNRepositoryPersistence
 * @see SVNRepositoryUtil
 * @generated
 */
public class SVNRepositoryPersistenceImpl extends BasePersistenceImpl<SVNRepository>
	implements SVNRepositoryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SVNRepositoryUtil} to access the s v n repository persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SVNRepositoryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_URL = new FinderPath(SVNRepositoryModelImpl.ENTITY_CACHE_ENABLED,
			SVNRepositoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUrl",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_URL = new FinderPath(SVNRepositoryModelImpl.ENTITY_CACHE_ENABLED,
			SVNRepositoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUrl",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SVNRepositoryModelImpl.ENTITY_CACHE_ENABLED,
			SVNRepositoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SVNRepositoryModelImpl.ENTITY_CACHE_ENABLED,
			SVNRepositoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the s v n repository in the entity cache if it is enabled.
	 *
	 * @param svnRepository the s v n repository to cache
	 */
	public void cacheResult(SVNRepository svnRepository) {
		EntityCacheUtil.putResult(SVNRepositoryModelImpl.ENTITY_CACHE_ENABLED,
			SVNRepositoryImpl.class, svnRepository.getPrimaryKey(),
			svnRepository);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_URL,
			new Object[] { svnRepository.getUrl() }, svnRepository);
	}

	/**
	 * Caches the s v n repositories in the entity cache if it is enabled.
	 *
	 * @param svnRepositories the s v n repositories to cache
	 */
	public void cacheResult(List<SVNRepository> svnRepositories) {
		for (SVNRepository svnRepository : svnRepositories) {
			if (EntityCacheUtil.getResult(
						SVNRepositoryModelImpl.ENTITY_CACHE_ENABLED,
						SVNRepositoryImpl.class, svnRepository.getPrimaryKey(),
						this) == null) {
				cacheResult(svnRepository);
			}
		}
	}

	/**
	 * Clears the cache for all s v n repositories.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(SVNRepositoryImpl.class.getName());
		EntityCacheUtil.clearCache(SVNRepositoryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the s v n repository.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(SVNRepository svnRepository) {
		EntityCacheUtil.removeResult(SVNRepositoryModelImpl.ENTITY_CACHE_ENABLED,
			SVNRepositoryImpl.class, svnRepository.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_URL,
			new Object[] { svnRepository.getUrl() });
	}

	/**
	 * Creates a new s v n repository with the primary key. Does not add the s v n repository to the database.
	 *
	 * @param svnRepositoryId the primary key for the new s v n repository
	 * @return the new s v n repository
	 */
	public SVNRepository create(long svnRepositoryId) {
		SVNRepository svnRepository = new SVNRepositoryImpl();

		svnRepository.setNew(true);
		svnRepository.setPrimaryKey(svnRepositoryId);

		return svnRepository;
	}

	/**
	 * Removes the s v n repository with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the s v n repository to remove
	 * @return the s v n repository that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a s v n repository with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SVNRepository remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the s v n repository with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param svnRepositoryId the primary key of the s v n repository to remove
	 * @return the s v n repository that was removed
	 * @throws com.liferay.socialcoding.NoSuchSVNRepositoryException if a s v n repository with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SVNRepository remove(long svnRepositoryId)
		throws NoSuchSVNRepositoryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SVNRepository svnRepository = (SVNRepository)session.get(SVNRepositoryImpl.class,
					new Long(svnRepositoryId));

			if (svnRepository == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						svnRepositoryId);
				}

				throw new NoSuchSVNRepositoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					svnRepositoryId);
			}

			return svnRepositoryPersistence.remove(svnRepository);
		}
		catch (NoSuchSVNRepositoryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes the s v n repository from the database. Also notifies the appropriate model listeners.
	 *
	 * @param svnRepository the s v n repository to remove
	 * @return the s v n repository that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public SVNRepository remove(SVNRepository svnRepository)
		throws SystemException {
		return super.remove(svnRepository);
	}

	protected SVNRepository removeImpl(SVNRepository svnRepository)
		throws SystemException {
		svnRepository = toUnwrappedModel(svnRepository);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, svnRepository);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		SVNRepositoryModelImpl svnRepositoryModelImpl = (SVNRepositoryModelImpl)svnRepository;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_URL,
			new Object[] { svnRepositoryModelImpl.getUrl() });

		EntityCacheUtil.removeResult(SVNRepositoryModelImpl.ENTITY_CACHE_ENABLED,
			SVNRepositoryImpl.class, svnRepository.getPrimaryKey());

		return svnRepository;
	}

	public SVNRepository updateImpl(
		com.liferay.socialcoding.model.SVNRepository svnRepository,
		boolean merge) throws SystemException {
		svnRepository = toUnwrappedModel(svnRepository);

		boolean isNew = svnRepository.isNew();

		SVNRepositoryModelImpl svnRepositoryModelImpl = (SVNRepositoryModelImpl)svnRepository;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, svnRepository, merge);

			svnRepository.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SVNRepositoryModelImpl.ENTITY_CACHE_ENABLED,
			SVNRepositoryImpl.class, svnRepository.getPrimaryKey(),
			svnRepository);

		if (!isNew &&
				(!Validator.equals(svnRepository.getUrl(),
					svnRepositoryModelImpl.getOriginalUrl()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_URL,
				new Object[] { svnRepositoryModelImpl.getOriginalUrl() });
		}

		if (isNew ||
				(!Validator.equals(svnRepository.getUrl(),
					svnRepositoryModelImpl.getOriginalUrl()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_URL,
				new Object[] { svnRepository.getUrl() }, svnRepository);
		}

		return svnRepository;
	}

	protected SVNRepository toUnwrappedModel(SVNRepository svnRepository) {
		if (svnRepository instanceof SVNRepositoryImpl) {
			return svnRepository;
		}

		SVNRepositoryImpl svnRepositoryImpl = new SVNRepositoryImpl();

		svnRepositoryImpl.setNew(svnRepository.isNew());
		svnRepositoryImpl.setPrimaryKey(svnRepository.getPrimaryKey());

		svnRepositoryImpl.setSvnRepositoryId(svnRepository.getSvnRepositoryId());
		svnRepositoryImpl.setUrl(svnRepository.getUrl());
		svnRepositoryImpl.setRevisionNumber(svnRepository.getRevisionNumber());

		return svnRepositoryImpl;
	}

	/**
	 * Finds the s v n repository with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the s v n repository to find
	 * @return the s v n repository
	 * @throws com.liferay.portal.NoSuchModelException if a s v n repository with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SVNRepository findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the s v n repository with the primary key or throws a {@link com.liferay.socialcoding.NoSuchSVNRepositoryException} if it could not be found.
	 *
	 * @param svnRepositoryId the primary key of the s v n repository to find
	 * @return the s v n repository
	 * @throws com.liferay.socialcoding.NoSuchSVNRepositoryException if a s v n repository with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SVNRepository findByPrimaryKey(long svnRepositoryId)
		throws NoSuchSVNRepositoryException, SystemException {
		SVNRepository svnRepository = fetchByPrimaryKey(svnRepositoryId);

		if (svnRepository == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + svnRepositoryId);
			}

			throw new NoSuchSVNRepositoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				svnRepositoryId);
		}

		return svnRepository;
	}

	/**
	 * Finds the s v n repository with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the s v n repository to find
	 * @return the s v n repository, or <code>null</code> if a s v n repository with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SVNRepository fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the s v n repository with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param svnRepositoryId the primary key of the s v n repository to find
	 * @return the s v n repository, or <code>null</code> if a s v n repository with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SVNRepository fetchByPrimaryKey(long svnRepositoryId)
		throws SystemException {
		SVNRepository svnRepository = (SVNRepository)EntityCacheUtil.getResult(SVNRepositoryModelImpl.ENTITY_CACHE_ENABLED,
				SVNRepositoryImpl.class, svnRepositoryId, this);

		if (svnRepository == null) {
			Session session = null;

			try {
				session = openSession();

				svnRepository = (SVNRepository)session.get(SVNRepositoryImpl.class,
						new Long(svnRepositoryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (svnRepository != null) {
					cacheResult(svnRepository);
				}

				closeSession(session);
			}
		}

		return svnRepository;
	}

	/**
	 * Finds the s v n repository where url = &#63; or throws a {@link com.liferay.socialcoding.NoSuchSVNRepositoryException} if it could not be found.
	 *
	 * @param url the url to search with
	 * @return the matching s v n repository
	 * @throws com.liferay.socialcoding.NoSuchSVNRepositoryException if a matching s v n repository could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SVNRepository findByUrl(String url)
		throws NoSuchSVNRepositoryException, SystemException {
		SVNRepository svnRepository = fetchByUrl(url);

		if (svnRepository == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("url=");
			msg.append(url);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchSVNRepositoryException(msg.toString());
		}

		return svnRepository;
	}

	/**
	 * Finds the s v n repository where url = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param url the url to search with
	 * @return the matching s v n repository, or <code>null</code> if a matching s v n repository could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SVNRepository fetchByUrl(String url) throws SystemException {
		return fetchByUrl(url, true);
	}

	/**
	 * Finds the s v n repository where url = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param url the url to search with
	 * @return the matching s v n repository, or <code>null</code> if a matching s v n repository could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SVNRepository fetchByUrl(String url, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { url };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_URL,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_SVNREPOSITORY_WHERE);

			if (url == null) {
				query.append(_FINDER_COLUMN_URL_URL_1);
			}
			else {
				if (url.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_URL_URL_3);
				}
				else {
					query.append(_FINDER_COLUMN_URL_URL_2);
				}
			}

			query.append(SVNRepositoryModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (url != null) {
					qPos.add(url);
				}

				List<SVNRepository> list = q.list();

				result = list;

				SVNRepository svnRepository = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_URL,
						finderArgs, list);
				}
				else {
					svnRepository = list.get(0);

					cacheResult(svnRepository);

					if ((svnRepository.getUrl() == null) ||
							!svnRepository.getUrl().equals(url)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_URL,
							finderArgs, svnRepository);
					}
				}

				return svnRepository;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_URL,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (SVNRepository)result;
			}
		}
	}

	/**
	 * Finds all the s v n repositories.
	 *
	 * @return the s v n repositories
	 * @throws SystemException if a system exception occurred
	 */
	public List<SVNRepository> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the s v n repositories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of s v n repositories to return
	 * @param end the upper bound of the range of s v n repositories to return (not inclusive)
	 * @return the range of s v n repositories
	 * @throws SystemException if a system exception occurred
	 */
	public List<SVNRepository> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the s v n repositories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of s v n repositories to return
	 * @param end the upper bound of the range of s v n repositories to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of s v n repositories
	 * @throws SystemException if a system exception occurred
	 */
	public List<SVNRepository> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SVNRepository> list = (List<SVNRepository>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SVNREPOSITORY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SVNREPOSITORY.concat(SVNRepositoryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SVNRepository>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SVNRepository>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs,
						list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes the s v n repository where url = &#63; from the database.
	 *
	 * @param url the url to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUrl(String url)
		throws NoSuchSVNRepositoryException, SystemException {
		SVNRepository svnRepository = findByUrl(url);

		svnRepositoryPersistence.remove(svnRepository);
	}

	/**
	 * Removes all the s v n repositories from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SVNRepository svnRepository : findAll()) {
			svnRepositoryPersistence.remove(svnRepository);
		}
	}

	/**
	 * Counts all the s v n repositories where url = &#63;.
	 *
	 * @param url the url to search with
	 * @return the number of matching s v n repositories
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUrl(String url) throws SystemException {
		Object[] finderArgs = new Object[] { url };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_URL,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SVNREPOSITORY_WHERE);

			if (url == null) {
				query.append(_FINDER_COLUMN_URL_URL_1);
			}
			else {
				if (url.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_URL_URL_3);
				}
				else {
					query.append(_FINDER_COLUMN_URL_URL_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (url != null) {
					qPos.add(url);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_URL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the s v n repositories.
	 *
	 * @return the number of s v n repositories
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SVNREPOSITORY);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the s v n repository persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.socialcoding.model.SVNRepository")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SVNRepository>> listenersList = new ArrayList<ModelListener<SVNRepository>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SVNRepository>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(SVNRepositoryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = JIRAActionPersistence.class)
	protected JIRAActionPersistence jiraActionPersistence;
	@BeanReference(type = JIRAChangeGroupPersistence.class)
	protected JIRAChangeGroupPersistence jiraChangeGroupPersistence;
	@BeanReference(type = JIRAChangeItemPersistence.class)
	protected JIRAChangeItemPersistence jiraChangeItemPersistence;
	@BeanReference(type = JIRAIssuePersistence.class)
	protected JIRAIssuePersistence jiraIssuePersistence;
	@BeanReference(type = SVNRepositoryPersistence.class)
	protected SVNRepositoryPersistence svnRepositoryPersistence;
	@BeanReference(type = SVNRevisionPersistence.class)
	protected SVNRevisionPersistence svnRevisionPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_SVNREPOSITORY = "SELECT svnRepository FROM SVNRepository svnRepository";
	private static final String _SQL_SELECT_SVNREPOSITORY_WHERE = "SELECT svnRepository FROM SVNRepository svnRepository WHERE ";
	private static final String _SQL_COUNT_SVNREPOSITORY = "SELECT COUNT(svnRepository) FROM SVNRepository svnRepository";
	private static final String _SQL_COUNT_SVNREPOSITORY_WHERE = "SELECT COUNT(svnRepository) FROM SVNRepository svnRepository WHERE ";
	private static final String _FINDER_COLUMN_URL_URL_1 = "svnRepository.url IS NULL";
	private static final String _FINDER_COLUMN_URL_URL_2 = "svnRepository.url = ?";
	private static final String _FINDER_COLUMN_URL_URL_3 = "(svnRepository.url IS NULL OR svnRepository.url = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "svnRepository.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SVNRepository exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SVNRepository exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(SVNRepositoryPersistenceImpl.class);
}