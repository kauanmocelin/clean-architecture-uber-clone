drop schema if exists uber_clone cascade;

create schema uber_clone;

create table uber_clone.account (
	account_id uuid primary key,
	name text not null,
	email text not null,
	cpf text not null,
	car_plate text null,
	is_passenger boolean not null default false,
	is_driver boolean not null default false
);

create table uber_clone.ride (
    ride_id uuid,
    passenger_id uuid,
    driver_id uuid,
    status text,
    fare numeric,
    distance numeric,
    from_lat numeric,
    from_long numeric,
    to_lat numeric,
    to_long numeric,
    date timestamp,
    last_lat numeric,
    last_long numeric
);

create table uber_clone.position (
    position_id uuid,
    ride_id uuid,
    lat numeric,
    long numeric,
    date timestamp
);