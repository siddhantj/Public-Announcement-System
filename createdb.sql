create table students
(
std_id varchar(10) not null primary key,
std_position SDO_GEOMETRY
);
insert into user_sdo_geom_metadata values ('students','std_position',sdo_dim_array(sdo_dim_element('X',0,1000,0.005),sdo_dim_element('Y',0,1000,0.005)),NULL);
create index student_index on students(std_position) indextype is MDSYS.SPATIAL_INDEX;

create table buildings
(
b_id varchar(10) not null primary key,
b_name varchar(30),
b_vert int,
b_conf SDO_GEOMETRY
);
insert into user_sdo_geom_metadata values ('buildings','b_conf',sdo_dim_array(sdo_dim_element('X',0,1000,0.005),sdo_dim_element('Y',0,1000,0.005)),NULL);
create index building_index on buildings(b_conf) indextype is MDSYS.SPATIAL_INDEX;

create table asystems
(
  as_id varchar(20) not null primary key,
  as_position SDO_GEOMETRY,
  as_radius int
);
insert into user_sdo_geom_metadata values ('asystems','as_position',sdo_dim_array(sdo_dim_element('X',0,1000,0.005),sdo_dim_element('Y',0,1000,0.005)),NULL);
create index asystems_index on asystems(as_position) indextype is MDSYS.SPATIAL_INDEX;

create table pointQueryTable
(
pt_id int not null primary key,
coordinates SDO_GEOMETRY
);

create table drawPolygonTable
(
poly_id int not null primary key,
coordinates SDO_GEOMETRY
);

insert into user_sdo_geom_metadata values ('drawPolygonTable','coordinates',sdo_dim_array(sdo_dim_element('X',0,1000,0.005),sdo_dim_element('Y',0,1000,0.005)),NULL);
create index drawPolygonTable_index on drawPolygonTable(coordinates) indextype is MDSYS.SPATIAL_INDEX;


