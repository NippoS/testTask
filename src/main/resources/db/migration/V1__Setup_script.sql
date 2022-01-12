create table if not exists users
(
    id                 bigint           auto_increment primary key,
    first_name         varchar(50),
    last_name          varchar(50),
    username           varchar(255),
    created            datetime         not null,
    updated            datetime         not null,
    password           varchar(64),
    user_status        varchar(15)      not null
);

create table if not exists roles
(
    id      bigint         auto_increment primary key,
    name    varchar(32)    not null
);

create table if not exists questions
(
    id                 bigint           auto_increment primary key,
    text               varchar(10000)   not null,
    count_answer       bigint           not null,
    time_updated       datetime         not null,
    question_type      varchar(15)      not null,
    status             varchar(15)      not null
);

create table if not exists surveys
(
    id                 bigint           auto_increment primary key,
    name               varchar(300)     not null,
    date_start         date             not null,
    date_end           date             not null,
    time_start         time             not null,
    time_end           time             not null,
    time_updated       datetime         not null,
    survey_status      varchar(15)      not null,
    status             varchar(15)      not null
);

create table if not exists answers
(
    id              bigint         auto_increment primary key,
    answer          varchar(100)   not null,
    user_id         bigint         not null,
    question_id     bigint         not null
);

create table if not exists users_roles
(
    user_id    bigint    not null,
    role_id    bigint    not null,

    constraint users_roles_user_id__fk
        foreign key (user_id)
            references users (id)
            on delete cascade,

    constraint users_roles_role_id__fk
        foreign key (role_id)
            references roles (id)
            on delete cascade,

    constraint user_role UNIQUE (user_id, role_id)
);

create table if not exists surveys_questions
(
    survey_id     bigint    not null,
    question_id   bigint    not null,

    constraint surveys_questions_survey_id__fk
        foreign key (survey_id)
            references surveys (id)
            on delete cascade,

    constraint surveys_questions_question_id__fk
        foreign key (question_id)
            references questions (id)
            on delete cascade,

    constraint survey_question UNIQUE (survey_id, question_id)
);

create table if not exists users_surveys
(
    user_id     bigint    not null,
    survey_id     bigint    not null,

    constraint users_surveys_user_id__fk
        foreign key (user_id)
            references users (id)
            on delete cascade,

    constraint users_surveys_survey_id__fk
        foreign key (survey_id)
            references surveys (id)
            on delete cascade,

    constraint user_survey UNIQUE (user_id, survey_id)
);