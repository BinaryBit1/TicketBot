package proj.bot.ticket.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import proj.bot.ticket.TicketBot;
import proj.bot.ticket.supports.SupportType;

public class ServerTable {

    private String guildId;

    private Connection connection = null;

    public ServerTable(String guildId) {
        this.guildId = guildId;
        open();
        try {
            if (!tableExists(guildId)) {
                String statement = "CREATE TABLE IF NOT EXISTS `" + guildId
                        + "` (`type` varchar(255) NOT NULL, `value` varchar(255) NOT NULL, PRIMARY KEY (type)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
                PreparedStatement sql = connection.prepareStatement(statement);
                sql.executeUpdate();
                sql.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        close();
    }

    public boolean tableExists(String tableName) {
        try (ResultSet rs = connection.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    private synchronized Optional<Boolean> open() {// returns - if connection is
                                                   // renewed.
        boolean wasClosed = true;
        if (connection != null) {
            try {
                if (!connection.isClosed())
                    wasClosed = false;
            } catch (Exception e) {
                wasClosed = true;
            }
        }
        if (wasClosed) {
            HashMap<String, String> variables = TicketBot.getInstance().getConfig().getVariables();
            String ipAddon = "?useSSL=false";
            String ip = "jdbc:mysql://" + variables.get("SQL_IP") + ":" + variables.get("SQL_PORT") + "/"
                    + variables.get("SQL_DATABASE");
            String username = variables.get("SQL_USERNAME");
            String password = variables.get("SQL_PASSWORD");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(ip, username, password);
                return Optional.of(true);
            } catch (Exception e) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection(ip + ipAddon, username, password);
                    return Optional.of(true);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    System.out.println("Failed to connect to server.");
                    return Optional.empty();
                }
            }
        }
        return Optional.of(false);
    }

    private synchronized void close() {
        try {
            if ((connection != null) && (!connection.isClosed())) {
                connection.close();
            }
            return;
        } catch (Exception exception) {
        } finally {
            try {
                connection.close();
            } catch (Exception exception) {
            }
        }
    }

    public boolean getSupportType(SupportType type) {
        boolean exists = false;
        Optional<Boolean> newCon = open();
        if (newCon.isPresent()) {
            boolean isNew = newCon.get();
            try {
                PreparedStatement sql = connection.prepareStatement("SELECT * FROM `" + guildId + "` WHERE type='support';");
                ResultSet rs = sql.executeQuery();
                while (rs.next()) {
                    String value = rs.getString("value");
                    if (type.toString().equalsIgnoreCase(value)) {
                        exists = true;
                        break;
                    }
                }
                rs.close();
                sql.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (isNew)
                    close();
            }
        }
        return exists;
    }

    public void setSupportType(SupportType type, boolean enabled) {
        Optional<Boolean> newCon = open();
        if (newCon.isPresent()) {
            boolean isNew = newCon.get();
            try {
                PreparedStatement sql = connection.prepareStatement("DELETE FROM `" + guildId + "` WHERE type='support', value='" + type.toString() + "';");
                sql.executeUpdate();
                sql.close();
                if(enabled) {
                    sql = connection.prepareStatement("INSERT INTO `" + guildId + "`  (type,value) VALUES ('support','" + type.toString() + "');");
                    sql.executeUpdate();
                    sql.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (isNew)
                    close();
            }
        }
    }

    public List<SupportType> getEnabledSupports() {
        List<SupportType> list = new ArrayList<>();
        Optional<Boolean> newCon = open();
        if (newCon.isPresent()) {
            boolean isNew = newCon.get();
            try {
                PreparedStatement sql = connection.prepareStatement("SELECT * FROM `" + guildId + "` WHERE type='support';");
                ResultSet rs = sql.executeQuery();
                while(rs.next()) {
                    list.add(SupportType.fromString(rs.getString("value")));
                }
                rs.close();
                sql.executeUpdate();
                sql.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (isNew)
                    close();
            }
        }
        return list;
    }

    public void updateBlacklist(String userid, boolean added) {
        Optional<Boolean> newCon = open();
        if (newCon.isPresent()) {
            boolean isNew = newCon.get();
            try {
                PreparedStatement sql = connection.prepareStatement("DELETE FROM `" + guildId + "` WHERE type='blacklist', value='" + userid + "';");
                sql.executeUpdate();
                sql.close();
                if(added) {
                    sql = connection.prepareStatement("INSERT INTO `" + guildId + "`  (type,value) VALUES ('blacklist','" + userid + "');");
                    sql.executeUpdate();
                    sql.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (isNew)
                    close();
            }
        }
    }
    
    public boolean blacklistContains(String userid) {
        boolean isBlacklisted = false;
        Optional<Boolean> newCon = open();
        if (newCon.isPresent()) {
            boolean isNew = newCon.get();
            try {
                PreparedStatement sql = connection.prepareStatement("SELECT * FROM `" + guildId + "` WHERE type='blacklist';");
                ResultSet rs = sql.executeQuery();
                while(rs.next()) {
                    if(rs.getString("value").equals(userid)) {
                        isBlacklisted = true;
                        break;
                    }
                }
                rs.close();
                sql.executeUpdate();
                sql.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (isNew)
                    close();
            }
        }
        return isBlacklisted;
    }
    
    public List<String> getBlacklist() {
        List<String> list = new ArrayList<>();
        Optional<Boolean> newCon = open();
        if (newCon.isPresent()) {
            boolean isNew = newCon.get();
            try {
                PreparedStatement sql = connection.prepareStatement("SELECT * FROM `" + guildId + "` WHERE type='blacklist';");
                ResultSet rs = sql.executeQuery();
                while(rs.next()) {
                    list.add(rs.getString("value"));
                }
                rs.close();
                sql.executeUpdate();
                sql.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (isNew)
                    close();
            }
        }
        return list;
    }
}
