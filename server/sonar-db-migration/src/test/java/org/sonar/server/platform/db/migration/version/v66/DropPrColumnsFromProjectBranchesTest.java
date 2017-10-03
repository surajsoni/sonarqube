/*
 * SonarQube
 * Copyright (C) 2009-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.server.platform.db.migration.version.v66;

import java.sql.SQLException;
import org.junit.Rule;
import org.junit.Test;
import org.sonar.db.CoreDbTester;

import static org.sonar.server.platform.db.migration.version.v66.DropPrColumnsFromProjectBranches.PROJECT_BRANCHES_TABLE_NAME;

public class DropPrColumnsFromProjectBranchesTest {
  @Rule
  public CoreDbTester db = CoreDbTester.createForSchema(DropPrColumnsFromProjectBranchesTest.class, "initial.sql");

  private DropPrColumnsFromProjectBranches underTest = new DropPrColumnsFromProjectBranches(db.database());

  @Test
  public void drop_columns() throws SQLException {
    underTest.execute();
    db.assertColumnDoesNotExist(PROJECT_BRANCHES_TABLE_NAME, "kee_type");
    db.assertColumnDoesNotExist(PROJECT_BRANCHES_TABLE_NAME, "pull_request_title");
    db.assertUniqueIndex(PROJECT_BRANCHES_TABLE_NAME, "project_branches_kee", "project_uuid", "kee");
  }
}