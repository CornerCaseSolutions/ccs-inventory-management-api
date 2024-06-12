create table if not exists clothing (
                                                   id uuid primary key default gen_random_uuid(),
                                                   description varchar,
                                                   condition varchar,
                                                   stockStatus varchar,
                                                   stock integer,
                                                   created timestamp,
                                                   updated timestamp
);
