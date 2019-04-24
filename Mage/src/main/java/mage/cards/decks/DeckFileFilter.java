package mage.cards.decks;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.Locale;

/**
 * @author JayDi85
 */
public class DeckFileFilter extends FileFilter {

    private final String ext;
    private final String description;

    public DeckFileFilter(String ext, String description) {
        this.ext = ext.toLowerCase(Locale.ENGLISH);
        this.description = description;
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String fileExt = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            fileExt = s.substring(i + 1).toLowerCase(Locale.ENGLISH);
        }
        return (fileExt != null) && fileExt.equals(this.ext);
    }

    @Override
    public String getDescription() {
        return description;
    }
}