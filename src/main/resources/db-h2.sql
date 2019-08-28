drop table if exists account
;
drop table if exists account_history
;
drop table if exists transfer_history
;

create table account (
    id long auto_increment not null,
    account_number varchar(12) not null,

    constraint pk_account primary key (id)
)
;
create table account_history (
    id long auto_increment not null,
    account_id long not null,
    value numeric (10,2) not null,

    constraint pk_account_history primary key (id),
    constraint fk_account_account_history foreign key (account_id) references (id)
)
;
create table transfer_history (
    id long auto_increment not null,
    debt_account_id long not null,
    credit_account_id long not null,
    datetime timestamp not null,

    constraint pk_transfer_history primary key (id),
    constraint fk_transfer_history_debt_account foreign key (debt_account_id) references (id),
    constraint fk_transfer_history_credit_account foreign key (credit_account_id) references (id)
)
;
insert into account (account_number)
values (124578)
;
insert into account (account_number)
values (3216547)
;
insert into account_history (account_id, value)
values ((select id from account where account_number = 124578), 100.57)
;
insert into account_history (account_id, value)
values ((select id from account where account_number = 3216547), 352.89)
;