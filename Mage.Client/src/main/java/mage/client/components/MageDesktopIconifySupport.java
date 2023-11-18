package mage.client.components;

/**
 * GUI: support windows mimize (iconify) by double clicks
 *
 * @author JayDi85
 */
public interface MageDesktopIconifySupport {

    /**
     * Window's header width after minimized to icon
     */
    default int getDesktopIconWidth() {
        return 250;
    }

}
