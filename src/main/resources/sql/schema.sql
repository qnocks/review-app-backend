DROP TABLE IF EXISTS usr CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS authority CASCADE;

DROP TABLE IF EXISTS usr_role CASCADE;
DROP TABLE IF EXISTS role_authority CASCADE;

DROP TABLE IF EXISTS review CASCADE;
DROP TABLE IF EXISTS tag CASCADE;
DROP TABLE IF EXISTS comment CASCADE;
DROP TABLE IF EXISTS review_tag CASCADE;

CREATE TABLE IF NOT EXISTS usr (
    id bigint PRIMARY KEY,
    username varchar(20) NOT NULL,
    email varchar(20) NOT NULL,
    password varchar(256) NOT NULL,
    active bool NOT NULL
);

CREATE TABLE IF NOT EXISTS role (
    id bigint PRIMARY KEY,
    name varchar(20) NOT NULL
);

-- CREATE TABLE IF NOT EXISTS authority (
--     id int PRIMARY KEY,
--     name varchar(30)
-- );

CREATE TABLE IF NOT EXISTS usr_role (
    usr_id bigint REFERENCES usr(id) ON DELETE SET DEFAULT ON UPDATE RESTRICT,
    role_id bigint REFERENCES role(id) ON DELETE SET DEFAULT ON UPDATE RESTRICT,
    CONSTRAINT usr_role_id PRIMARY KEY (usr_id, role_id)
);

-- CREATE TABLE IF NOT EXISTS role_authority (
--     role_id int REFERENCES role(id) ON DELETE SET DEFAULT ON UPDATE RESTRICT,
--     authorities_id int REFERENCES authority(id) ON DELETE SET DEFAULT ON UPDATE RESTRICT,
--     CONSTRAINT role_authorities_id PRIMARY KEY (role_id, authorities_id)
-- );

CREATE TABLE IF NOT EXISTS review (
    id bigint PRIMARY KEY,
    name varchar(100),
    content varchar(20),
    content_name varchar(40),
    txt text,
    score int,
    rating float4,
    likes int,
    images_link varchar(255),
    user_id bigint
);

CREATE TABLE IF NOT EXISTS tag (
    id bigint PRIMARY KEY,
    name varchar(20)
);

-- CREATE TABLE IF NOT EXISTS comment (
--     id   int PRIMARY KEY,
--     name varchar(100),
--     review_id int REFERENCES review(id) ON DELETE CASCADE ON UPDATE RESTRICT
-- );

CREATE TABLE IF NOT EXISTS review_tag (
    review_id bigint,
    tag_id bigint,
    CONSTRAINT review_tag_id PRIMARY KEY (review_id, tag_id)
);





