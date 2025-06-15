insert into master_role(role_id, role_code, role_name, description, status, created_at)
values (1, 'SUPERADMIN', 'Super Admin', 'Full Access', 'ACTIVE', '2025-06-13 00:00:00'),
       (2, 'ADMIN', 'Admin', 'Admin Role', 'ACTIVE', '2025-06-13 00:00:00'),
       (3, 'USER', 'User', 'Normal User Role', 'ACTIVE', '2025-06-13 00:00:00');

insert into master_menu(menu_id, parent_id, menu_code, menu_name, menu_icon, menu_url, menu_order, status, created_at)
values (1, null, 'DASHBOARD', 'Dashboard', 'dashboard', '/dashboard', 1, 'ACTIVE', '2025-06-13 00:00:00'),
       (2, null, 'DATA_MASTER', 'Data Master', 'database', '/master', 2, 'ACTIVE', '2025-06-13 00:00:00'),
       (3, 2, 'MASTER_UNIT', 'Master Unit', 'building', '/master/unit', 1, 'ACTIVE', '2025-06-13 00:00:00'),
       (4, 2, 'MASTER_ROLE', 'Master Role', 'users', '/master/role', 2, 'ACTIVE', '2025-06-13 00:00:00'),
       (5, null, 'SETUP_MARGIN', 'Setup Margin', 'settings', '/setup/margin', 3, 'ACTIVE', '2025-06-13 00:00:00');

insert into role_menu(role_menu_id, role_id, menu_id, status, created_at)
values (1, 1, 1, 'ACTIVE', '2025-06-13 00:00:00'),
       (2, 1, 2, 'ACTIVE', '2025-06-13 00:00:00'),
       (3, 1, 3, 'ACTIVE', '2025-06-13 00:00:00'),
       (4, 1, 4, 'ACTIVE', '2025-06-13 00:00:00'),
       (5, 1, 5, 'ACTIVE', '2025-06-13 00:00:00'),
       (6, 2, 1, 'ACTIVE', '2025-06-13 00:00:00'),
       (7, 2, 2, 'ACTIVE', '2025-06-13 00:00:00'),
       (8, 2, 3, 'ACTIVE', '2025-06-13 00:00:00'),
       (9, 3, 1, 'ACTIVE', '2025-06-13 00:00:00');

insert into master_unit(unit_id, unit_code, unit_name, description)
values (1, 'UNIT_01', 'Unit 01', 'desc unit 1'),
       (2, 'UNIT_02', 'Unit 02', 'desc unit 2');

insert into master_user(user_id, username, email, password, full_name, phone, unit_id, role_id, status, created_at)
values (1, 'superadmin', 'superadmin@domain.com', '$2a$12$FBpTNa/HnfFYFsh5x7i5T.RX5lwHfvwnWl.mHlnvRi2/jTjNsKx5i', 'Super Admin', '08123456789', 1, 1, 'ACTIVE', '2025-06-13 00:00:00'),
       (2, 'admin01', 'admin01@domain.com', '$2a$12$FBpTNa/HnfFYFsh5x7i5T.RX5lwHfvwnWl.mHlnvRi2/jTjNsKx5i', 'Admin User', '08129876543', 2, 2, 'ACTIVE', '2025-06-13 00:00:00'),
       (3, 'user01', 'user01@domain.com', '$2a$12$FBpTNa/HnfFYFsh5x7i5T.RX5lwHfvwnWl.mHlnvRi2/jTjNsKx5i', 'User Biasa', '08127778888', 2, 3, 'ACTIVE', '2025-06-13 00:00:00');

SELECT setval(pg_get_serial_sequence('master_role', 'role_id'), COALESCE(MAX(role_id), 0) + 1, false) FROM master_role;
SELECT setval(pg_get_serial_sequence('master_menu', 'menu_id'), COALESCE(MAX(menu_id), 0) + 1, false) FROM master_menu;
SELECT setval(pg_get_serial_sequence('role_menu', 'role_menu_id'), COALESCE(MAX(role_menu_id), 0) + 1, false) FROM role_menu;
SELECT setval(pg_get_serial_sequence('master_unit', 'unit_id'), COALESCE(MAX(unit_id), 0) + 1, false) FROM master_unit;
SELECT setval(pg_get_serial_sequence('master_user', 'user_id'), COALESCE(MAX(user_id), 0) + 1, false) FROM master_user;