# meilisearch-demo

# Setup

- run `docker-compose up meilisearch` to spin up Meilisearch instance
- master key for Meilisearch is `masterKey` for development purposes
- calling Meilisearch instance through REST calls use done using `Authorization: Bearer masterKey` header
- you possibly need to change `spring.sql.init.mode` property to seed the schema once

# What this does

- Allows user to create an entity through `POST /api/project` endpoint
- The creation of this entity gets intercepted and results into a job being enqueued
- The job gets the persisted entity (projected as a DTO) and persists this into Meilisearch under the index name
  of `MyFancyDto.class.getSimpleName()`
- The `GET /api/project` endpoint returns the stored dtos in Meilisearch

# General idea

- Have some kind of background syncing to other datastores (which are faster for certain purposes) while not disturbing
  the write performance.
- Concrete implementations of this idea could be persisting an audit trail or using the persisted data for a dashboard
  that does not need 'realtime' info

# TODO / future improvements

- Extract specific entity fetching out of job. Would rather like to see a handler per entity, which get activated in the
  job.
- Add specific searching (filtering? sorting?) capabilities to `GET` endpoint
- Store metadata about the Meilisearch sync onto the entity or on the DTO in Meilisearch 