INSERT INTO PUBLIC."flyway_schema_history" ("installed_rank", "version", "description", "type", "script", "checksum", "installed_by", "installed_on", "execution_time", "success") VALUES (-1, null, '<< Flyway Schema History table created >>', 'TABLE', '', null, '', '2021-01-22 12:15:25.616113', 0, true);
INSERT INTO PUBLIC."flyway_schema_history" ("installed_rank", "version", "description", "type", "script", "checksum", "installed_by", "installed_on", "execution_time", "success") VALUES (1, '1', 'init tasks table', 'SQL', 'V1__init_tasks_table.sql', -832096088, '', '2021-01-22 12:15:25.646992', 4, true);
INSERT INTO PUBLIC."flyway_schema_history" ("installed_rank", "version", "description", "type", "script", "checksum", "installed_by", "installed_on", "execution_time", "success") VALUES (2, '2', 'insert example todo', 'JDBC', 'db.migration.V2__insert_example_todo', null, '', '2021-01-22 12:15:25.665941', 7, true);
INSERT INTO PUBLIC."flyway_schema_history" ("installed_rank", "version", "description", "type", "script", "checksum", "installed_by", "installed_on", "execution_time", "success") VALUES (3, '3', 'add deadline column.', 'SQL', 'V3__add_deadline_column..sql', 146220455, '', '2021-01-22 12:15:25.681899', 6, true);
INSERT INTO PUBLIC."flyway_schema_history" ("installed_rank", "version", "description", "type", "script", "checksum", "installed_by", "installed_on", "execution_time", "success") VALUES (4, '4', 'add created and updated columns', 'SQL', 'V4__add_created_and_updated_columns.sql', 734086727, '', '2021-01-22 12:15:25.695861', 4, true);
INSERT INTO PUBLIC."flyway_schema_history" ("installed_rank", "version", "description", "type", "script", "checksum", "installed_by", "installed_on", "execution_time", "success") VALUES (5, '5', 'init memos table', 'SQL', 'V5__init_memos_table.sql', -469643717, '', '2021-01-22 12:15:25.711819', 7, true);
INSERT INTO PUBLIC."flyway_schema_history" ("installed_rank", "version", "description", "type", "script", "checksum", "installed_by", "installed_on", "execution_time", "success") VALUES (6, '6', 'init memo groups tables', 'SQL', 'V6__init_memo_groups_tables.sql', 1801849871, '', '2021-01-22 12:15:25.722790', 4, true);