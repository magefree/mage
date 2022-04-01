package mage.server.util;

import mage.server.util.config.GamePlugin;
import mage.server.util.config.Plugin;
import mage.server.util.config.Server;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

public final class Comparators {
    private Comparators() {

    }

    public static final Comparator<Server.Home> homeComparator = (h1, h2) -> {
        if (h1 == h2) {
            return 0;
        }
        if (h1 == null || h1.getClass() != h2.getClass()) {
            return -1;
        }
        return Objects.equals(h1.getInternal(), h2.getInternal()) &&
                Objects.equals(h1.getExternal(), h2.getExternal()) &&
                Objects.equals(h1.getPort(), h2.getPort()) &&
                Objects.equals(h1.getExternalport(), h2.getExternalport()) &&
                Objects.equals(h1.getSecondaryport(), h2.getSecondaryport()) ? 0 : -1;

    };

    public static final Comparator<Server> serverComparator = (s1, s2) -> {
        if (s1 == s2) return 0;
        if (s1 == null || s1.getClass() != s2.getClass()) return -1;
        return collectionComparator(homeComparator).compare(s1.getHome(), s2.getHome()) == 0 &&
                Objects.equals(s1.isMultihome(), s2.isMultihome()) &&
                Objects.equals(s1.getServerAddress(), s2.getServerAddress()) &&
                Objects.equals(s1.getServerName(), s2.getServerName()) &&
                Objects.equals(s1.getPort(), s2.getPort()) &&
                Objects.equals(s1.getMaxGameThreads(), s2.getMaxGameThreads()) &&
                Objects.equals(s1.getMaxSecondsIdle(), s2.getMaxSecondsIdle()) &&
                Objects.equals(s1.getSecondaryBindPort(), s2.getSecondaryBindPort()) &&
                Objects.equals(s1.getBacklogSize(), s2.getBacklogSize()) &&
                Objects.equals(s1.getNumAcceptThreads(), s2.getNumAcceptThreads()) &&
                Objects.equals(s1.getMaxPoolSize(), s2.getMaxPoolSize()) &&
                Objects.equals(s1.getLeasePeriod(), s2.getLeasePeriod()) &&
                Objects.equals(s1.getSocketWriteTimeout(), s2.getSocketWriteTimeout()) &&
                Objects.equals(s1.getMinUserNameLength(), s2.getMinUserNameLength()) &&
                Objects.equals(s1.getMaxUserNameLength(), s2.getMaxUserNameLength()) &&
                Objects.equals(s1.getInvalidUserNamePattern(), s2.getInvalidUserNamePattern()) &&
                Objects.equals(s1.getMinPasswordLength(), s2.getMinPasswordLength()) &&
                Objects.equals(s1.getMaxPasswordLength(), s2.getMaxPasswordLength()) &&
                Objects.equals(s1.getMaxAiOpponents(), s2.getMaxAiOpponents()) &&
                Objects.equals(s1.isSaveGameActivated(), s2.isSaveGameActivated()) &&
                Objects.equals(s1.isAuthenticationActivated(), s2.isAuthenticationActivated()) &&
                Objects.equals(s1.getGoogleAccount(), s2.getGoogleAccount()) &&
                Objects.equals(s1.getMailgunApiKey(), s2.getMailgunApiKey()) &&
                Objects.equals(s1.getMailgunDomain(), s2.getMailgunDomain()) &&
                Objects.equals(s1.getMailSmtpHost(), s2.getMailSmtpHost()) &&
                Objects.equals(s1.getMailSmtpPort(), s2.getMailSmtpPort()) &&
                Objects.equals(s1.getMailUser(), s2.getMailUser()) &&
                Objects.equals(s1.getMailPassword(), s2.getMailPassword()) &&
                Objects.equals(s1.getMailFromAddress(), s2.getMailFromAddress()) ? 0 : -1;
    };

    public static final Comparator<Plugin> pluginComparator = (p1, p2) -> {
        if (p1 == p2) {
            return 0;
        }
        if (p1 == null || p1.getClass() != p2.getClass()) {
            return -1;
        }
        return Objects.equals(p1.getName(), p2.getName()) &&
                Objects.equals(p1.getJar(), p2.getJar()) &&
                Objects.equals(p1.getClassName(), p2.getClassName()) ? 0 : -1;
    };

    public static final Comparator<GamePlugin> gamePluginComparator = (p1, p2) -> {
        if (p1 == p2) {
            return 0;
        }
        if (p1 == null || p1.getClass() != p2.getClass()) {
            return -1;
        }
        return Objects.equals(p1.getName(), p2.getName()) &&
                Objects.equals(p1.getJar(), p2.getJar()) &&
                Objects.equals(p1.getClassName(), p2.getClassName()) &&
                Objects.equals(p1.getTypeName(), p2.getTypeName()) ? 0 : -1;
    };

    public static <T> Comparator<Collection<T>> collectionComparator(Comparator<T> tComparator) {
        return (c1, c2) -> {
            if (c1 == c2) {
                return 0;
            }
            if (c1 == null || c2 == null) {
                return -1;
            }
            if (c1.size() != c2.size()) {
                return c1.size() - c2.size();
            }
            final Iterator<T> c1Iterator = c1.iterator();
            final Iterator<T> c2Iterator = c2.iterator();
            while (c1Iterator.hasNext()) {
                final T c1T = c1Iterator.next();
                final T c2T = c2Iterator.next();
                final int result = tComparator.compare(c1T, c2T);
                if (result != 0) {
                    return result;
                }
            }
            return 0;
        };
    }
}
