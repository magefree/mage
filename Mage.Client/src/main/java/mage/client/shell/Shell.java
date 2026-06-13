package mage.client.shell;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Entry point for the opt-in "modern UI shell".
 * <p>
 * Design goals (see {@code SHELL.md} at the repo root):
 * <ul>
 *     <li><b>Additive.</b> Practically all shell code lives in this new {@code mage.client.shell}
 *     package and in additive resources, so upstream merges of {@code master} never conflict here.</li>
 *     <li><b>Single seam.</b> The shell is wired into the existing client at exactly one place
 *     ({@code GuiDisplayUtil.refreshThemeSettings()}), behind {@link #isEnabled()}.</li>
 *     <li><b>Opt-in, default off.</b> Stock XMage behaviour is byte-for-byte unchanged unless the
 *     {@code xmage.shell} system property or {@code XMAGE_SHELL} environment variable is truthy.</li>
 * </ul>
 * Because the shell is dormant by default, dropping these files onto any future version of the
 * client is safe even if the surrounding code has drifted.
 *
 * @author modern-shell
 */
public final class Shell {

    /** System property that enables the shell, e.g. {@code -Dxmage.shell=1}. */
    public static final String PROPERTY = "xmage.shell";

    /** Environment variable that enables the shell, e.g. {@code XMAGE_SHELL=1}. */
    public static final String ENV = "XMAGE_SHELL";

    /** System property selecting the variant ({@code dark} or {@code light}); defaults to dark. */
    public static final String THEME_PROPERTY = "xmage.shell.theme";

    /** Environment variable selecting the variant ({@code dark} or {@code light}). */
    public static final String THEME_ENV = "XMAGE_SHELL_THEME";

    /**
     * Package that holds the shell's FlatLaf customisation files
     * ({@code FlatLaf.properties}, etc.). Registered as a custom defaults source so all theming
     * lives in additive resources rather than hard-coded Java.
     */
    private static final String DEFAULTS_PACKAGE = "mage.client.shell";

    private static volatile Boolean enabledCache;

    private Shell() {
    }

    /**
     * @return true when the modern shell should replace the stock Nimbus look-and-feel.
     * Resolved once and cached for the lifetime of the process.
     */
    public static boolean isEnabled() {
        Boolean cached = enabledCache;
        if (cached == null) {
            cached = resolveEnabled();
            enabledCache = cached;
        }
        return cached;
    }

    private static boolean resolveEnabled() {
        String prop = System.getProperty(PROPERTY);
        if (prop != null) {
            return isTruthy(prop);
        }
        return isTruthy(System.getenv(ENV));
    }

    private static boolean isTruthy(String value) {
        if (value == null) {
            return false;
        }
        switch (value.trim().toLowerCase()) {
            case "1":
            case "true":
            case "on":
            case "yes":
            case "enabled":
                return true;
            default:
                return false;
        }
    }

    /**
     * Install the modern look-and-feel. Called from the client's single LAF-install seam when
     * {@link #isEnabled()} is true.
     * <p>
     * All concrete styling (accent colour, corner radii, scrollbar shape, spacing) is expressed in
     * the additive {@code FlatLaf.properties} resource in this package, so it can be tuned without
     * touching Java code.
     *
     * @throws UnsupportedLookAndFeelException if the platform rejects the look-and-feel (matches the
     *                                         checked exception already handled at the call site).
     */
    public static void installLookAndFeel() throws UnsupportedLookAndFeelException {
        // Must be registered before the LAF is created so the .properties overrides are picked up.
        // FlatLaf loads FlatLaf.properties (shared) plus FlatDarkLaf/FlatLightLaf.properties (variant).
        FlatLaf.registerCustomDefaultsSource(DEFAULTS_PACKAGE);
        UIManager.setLookAndFeel(isLightVariant() ? new FlatLightLaf() : new FlatDarkLaf());
    }

    /**
     * @return true when the light ("Arcane Parchment") variant is requested; dark is the default.
     */
    public static boolean isLightVariant() {
        String value = System.getProperty(THEME_PROPERTY);
        if (value == null) {
            value = System.getenv(THEME_ENV);
        }
        return value != null && "light".equalsIgnoreCase(value.trim());
    }
}
