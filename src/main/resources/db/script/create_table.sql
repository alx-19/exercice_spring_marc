create table public.addresses
(
    id serial
        primary key,
    city varchar(255) not null,
    number bigint not null,
    street varchar(255) not null,
    worksite_id integer
);

alter table public.addresses owner to "user";

create table public.managers
(
    id serial
        primary key,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    mobile bigint not null,
    phone bigint
);

alter table public.managers owner to "user";

create table public.technicians
(
    id serial
        primary key,
    age bigint,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    address_id integer not null
        constraint fknj7cjtejw0acb0qvlqafgyqgn
            references public.addresses,
    manager_id integer not null
        constraint fkpuaftfc61523jghsjytt6y6a4
            references public.managers,
    vehicle_id integer
);

alter table public.technicians owner to "user";

create table public.vehicles
(
    id serial
        primary key,
    number_plate varchar(7) not null
        constraint uk_q3j60y2qghw51mfwq64i43fut
            unique,
    model varchar(255) not null,
    year_of_construction bigint not null,
    technician_id integer
        constraint fkqrnbrkq585eq4faylh5qo183i
            references public.technicians
);

alter table public.vehicles owner to "user";

alter table public.technicians
    add constraint fki7al8pqnkdx7gwk78h01aey5
        foreign key (vehicle_id) references public.vehicles;

create table public.worksites
(
    id serial
        primary key,
    name varchar(255) not null,
    price numeric not null,
    address_id integer
        constraint fkk54a4eh4kns9qbfnlob7j5wte
            references public.addresses
);

alter table public.worksites owner to "user";

alter table public.addresses
    add constraint fk9u9y49v2taaily1sqfu9f6oxg
        foreign key (worksite_id) references public.worksites;

create table public.technician_worksite
(
    technician_id integer not null
        constraint fksibvkta5syh59vj971y2mutii
            references public.technicians,
    worksite_id integer not null
        constraint fkmy010m35kwyx41lr9ecbyqbjb
            references public.worksites
);

alter table public.technician_worksite owner to "user";

create table public.users
(
    id serial
        primary key,
    grants varchar(255),
    mail varchar(255),
    password varchar(255),
    username varchar(30)
);

alter table public.users owner to "user";

