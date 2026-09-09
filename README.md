DDL FOR DATABASE ON THIS SERVICE:

CREATE TABLE public.task (
	id varchar(36) DEFAULT gen_random_uuid() NOT NULL,
	title varchar(255) NOT NULL,
	description varchar(1000) NULL,
	created_at timestamp NOT NULL,
	updated_at timestamp NULL,
	created_by varchar(100) NOT NULL,
	status varchar(50) DEFAULT 'TODO'::character varying NOT NULL,
	CONSTRAINT task_created_at_not_null NOT NULL created_at,
	CONSTRAINT task_created_by_not_null NOT NULL created_by,
	CONSTRAINT task_id_not_null NOT NULL id,
	CONSTRAINT task_pkey PRIMARY KEY (id),
	CONSTRAINT task_status_not_null NOT NULL status,
	CONSTRAINT task_title_not_null NOT NULL title
);
