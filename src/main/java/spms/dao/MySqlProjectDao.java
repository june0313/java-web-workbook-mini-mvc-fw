package spms.dao;

import com.google.common.collect.Lists;
import lombok.Setter;
import spms.annotation.Component;
import spms.vo.Project;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component("projectDao")
@Setter
public class MySqlProjectDao implements ProjectDao {
	private DataSource dataSource;

	@Override
	public List<Project> selectList() throws Exception {
		List<Project> projects = Lists.newArrayList();

		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			connection = dataSource.getConnection();
			String sql = "SELECT PNO, PNAME, STA_DATE, END_DATE, STATE FROM PROJECTS ORDER BY PNO";
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				projects.add(new Project()
					.setNo(resultSet.getInt("PNO"))
					.setTitle(resultSet.getString("PNAME"))
					.setStartDate(resultSet.getDate("STA_DATE"))
					.setEndDate(resultSet.getDate("END_DATE"))
					.setState(resultSet.getInt("STATE")));
			}

			return projects;

		} catch (SQLException e) {
			throw e;
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Override
	public int insert(Project project) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = dataSource.getConnection();
			String sql = "INSERT INTO PROJECTS (PNAME, CONTENT, STA_DATE, END_DATE, TAGS, CRE_DATE, STATE) VALUES (?, ?, ?, ?, ?, NOW(), 0)";
			statement = connection.prepareStatement(sql);
			statement.setString(1, project.getTitle());
			statement.setString(2, project.getContent());
			statement.setDate(3, project.getStartDate());
			statement.setDate(4, project.getEndDate());
			statement.setString(5, project.getTags());
			return statement.executeUpdate();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

}
