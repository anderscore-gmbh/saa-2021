SET DATABASE SQL SYNTAX PGS TRUE;

CREATE TABLE scheduler
(
  id         serial              NOT NULL,
  name       varchar(255)        NOT NULL,
  cron       varchar(255)        NOT NULL,
  created_at date                NOT NULL,
  updated_at date                NOT NULL,
  PRIMARY KEY (id)
);

comment on table scheduler
  is 'Scheduler that runs to create tickets in wekan';

create index scheduler_name_index
  on scheduler (name);
