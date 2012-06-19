/*
* Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.remote;

import java.net.*;
import java.util.Enumeration;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Connection {

    private String host;
    private int port;
    private String username;
    private String password;
    private ProxyType proxyType;
    private String proxyHost;
    private int proxyPort;
    private String proxyUsername;
    private String proxyPassword;

    private int avatarId;

    private static final String serialization = "?serializationtype=jboss";
    private static final String transport = "bisocket";

    @Override
    public int hashCode() {
        return (transport + host + Integer.toString(port) + proxyType.toString()).hashCode();    
    }

    @Override
    public boolean equals(Object object) {
        if (! (object instanceof Connection)) {
            return false;    
        }
        Connection otherConnection = (Connection) object;
        return hashCode() == otherConnection.hashCode();
    }

    @Override
    public String toString() {
        return host + ":" + Integer.toString(port) + "/" + serialization;
    }

    public String getURI() {
        if (host.equals("localhost")) {
            try {
                InetAddress inet = getLocalAddress();
                if (inet != null) {
                    return transport + "://" + inet.getHostAddress() + ":" + port + "/" + serialization;
                }
            } catch (SocketException ex) {
                // just use localhost if can't find local ip
            }
        }
        return transport + "://" + host + ":" + port + "/" + serialization;
    }

    public ProxyType getProxyType() {
        return proxyType;
    }

    public void setProxyType(ProxyType proxyType) {
        this.proxyType = proxyType;
    }

    public enum ProxyType {
        SOCKS("Socks"), HTTP("HTTP"), NONE("None");

        private String text;

        ProxyType(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }

        public static ProxyType valueByText(String value) {
            for (ProxyType type : values()) {
                if (type.text.equals(value)) return type;
            }
            return NONE;
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public static InetAddress getLocalAddress() throws SocketException {
        for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements(); ) {
            NetworkInterface iface = interfaces.nextElement( );
            if (iface.isLoopback())
                continue;
            for (InterfaceAddress addr: iface.getInterfaceAddresses()) {
                if (addr != null) {
                    InetAddress iaddr = addr.getAddress();
                    if (iaddr != null && iaddr instanceof Inet4Address) {
                        return iaddr;
                    }
                }
            }
        }
        return null;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

}
