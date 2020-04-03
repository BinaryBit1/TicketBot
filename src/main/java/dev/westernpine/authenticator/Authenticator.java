package dev.westernpine.authenticator;

import dev.westernpine.supports.SupportType;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

public class Authenticator {
    
    //permission overrides
    
    public static boolean hasPermission(Guild guild, Permission permission, User user) {
        Member member = guild.getMember(user);
        if(member.isOwner()) return true;
        for (Role role : member.getRoles()) {
            if (role.getPermissions().contains(permission) || role.getPermissions().contains(Permission.ADMINISTRATOR)) {
                return true;
            }
        }
        
        return false;
    }
    
    public static boolean hasRole(Guild guild, String roleName, User user) {
        Member member = guild.getMember(user);
        for (Role role : member.getRoles()) {
            if (role.getName().equals(roleName)) {
                return true;
            }
        }
        
        return false;
    }
    
    public static boolean isSupport(Guild guild, User user) {
        return guild.getMember(user).getRoles().contains(SupportType.getSupportRole(guild)) || Authenticator.hasPermission(guild, Permission.ADMINISTRATOR, user);
    }

}
