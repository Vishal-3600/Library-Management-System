package com.library.dao;

import com.library.model.Member;
import com.library.util.DBConnection;

import java.sql.*;

public class MemberDAO {

    public void addMember(Member member) {
        String query = "INSERT INTO members(name, email, phone) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, member.getName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getPhone());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Member added successfully.");
            }

        } catch (Exception e) {
            System.out.println("Error adding member: " + e.getMessage());
        }
    }

    public void viewAllMembers() {
        String query = "SELECT * FROM members";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.println("\nID | Name | Email | Phone");
            System.out.println("--------------------------------");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("member_id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getString("email") + " | " +
                        rs.getString("phone")
                );
            }

        } catch (Exception e) {
            System.out.println("Error viewing members: " + e.getMessage());
        }
    }
}
