CREATE TABLE contact
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    CONSTRAINT pk_contact PRIMARY KEY (id)
);

CREATE TABLE project
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    title      VARCHAR(255) NOT NULL,
    contact_id BIGINT       NOT NULL,
    CONSTRAINT pk_project PRIMARY KEY (id)
);

ALTER TABLE project
    ADD CONSTRAINT FK_PROJECT_ON_CONTACT FOREIGN KEY (contact_id) REFERENCES contact (id);

