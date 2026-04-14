CREATE TABLE IF NOT EXISTS projects (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    name VARCHAR(128) NOT NULL,
    description VARCHAR(510),
    status VARCHAR(128) NOT NULL,
    type VARCHAR(12) NOT NULL,
    value NUMERIC(15,2),
    cost NUMERIC(15,2),
    deadline DATE,
    updated_at TIMESTAMPTZ NOT NULL
)