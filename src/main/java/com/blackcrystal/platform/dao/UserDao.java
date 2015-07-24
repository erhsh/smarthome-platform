package com.blackcrystal.platform.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.blackcrystal.platform.pojo.User;

@Repository
public class UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int save(final User user) {
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into user(username, shadow, nick, email, phone) ");
		sb.append(" values(?, ?, ?, ?, ?)");

		return jdbcTemplate.update(sb.toString(),
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, user.getUsername());
						ps.setString(2, user.getShadow());
						ps.setString(3, user.getNick());
						ps.setString(4, user.getEmail());
						ps.setString(5, user.getPhone());
					}
				});
	}

	public List<User> getUsers() {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from user");
		return jdbcTemplate.query(sb.toString(), new UserRowMapper());
	}

	public User getUserById(String id) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from user where id=?");
		return jdbcTemplate.queryForObject(sb.toString(), new Object[] { id },
				new UserRowMapper());
	}

	public User getUserByName(String username) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from user where username=?");
		return jdbcTemplate.queryForObject(sb.toString(),
				new Object[] { username }, new UserRowMapper());
	}

	public boolean isNameExist(String username) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from user where username=?");
		int count = jdbcTemplate.queryForObject(sb.toString(),
				new Object[] { username }, Integer.class);
		return count > 0;
	}

	class UserRowMapper implements RowMapper<User> {

		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();

			user.setId(String.valueOf(rs.getInt("id")));
			user.setShadow(rs.getString("shadow"));
			return user;
		}

	}

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"beans.xml");
		ctx.refresh();
		UserDao userDao = ctx.getBean(UserDao.class);
		boolean flag = userDao.isNameExist("zhangsan123");
		System.out.println(flag);
		ctx.close();
	}
}
