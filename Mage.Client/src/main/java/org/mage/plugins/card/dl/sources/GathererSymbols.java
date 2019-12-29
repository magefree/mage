/**
 * GathererSymbols.java
 *
 * Created on 25.08.2010
 */
package org.mage.plugins.card.dl.sources;

import com.google.common.collect.AbstractIterator;
import java.io.File;
import static java.lang.String.format;
import java.util.Iterator;
import mage.client.constants.Constants;
import org.mage.plugins.card.dl.DownloadJob;
import static org.mage.plugins.card.dl.DownloadJob.fromURL;
import static org.mage.plugins.card.dl.DownloadJob.toFile;
import static org.mage.plugins.card.utils.CardImageUtils.getImagesDir;

/**
 * The class GathererSymbols.
 *
 * @version V0.0 25.08.2010
 * @author Clemens Koza
 */
public class GathererSymbols implements Iterable<DownloadJob> {
    //TODO chaos and planeswalker symbol
    //chaos: https://gatherer.wizards.com/Images/Symbols/chaos.gif

    private static File outDir;

    private static final String urlFmt = "https://gatherer.wizards.com/handlers/image.ashx?size=%1$s&name=%2$s&type=symbol";

    private static final String[] sizes = {"small", "medium", "large"};

    private static final String[] symbols = {"W", "U", "B", "R", "G",
        "W/U", "U/B", "B/R", "R/G", "G/W", "W/B", "U/R", "B/G", "R/W", "G/U",
        "2/W", "2/U", "2/B", "2/R", "2/G",
        "WP", "UP", "BP", "RP", "GP",
        "X", "S", "T", "Q", "C", "E"};
    private static final int minNumeric = 0, maxNumeric = 16;

    public GathererSymbols() {
        outDir = new File(getImagesDir() + Constants.RESOURCE_PATH_SYMBOLS);

        if (!outDir.exists()) {
            outDir.mkdirs();
        }
    }

    @Override
    public Iterator<DownloadJob> iterator() {
        return new AbstractIterator<DownloadJob>() {
            private int sizeIndex, symIndex, numeric = minNumeric;
            private File dir = new File(outDir, sizes[sizeIndex]);

            @Override
            protected DownloadJob computeNext() {
                while (true) {
                    String sym;
                    if (symIndex < symbols.length) {
                        sym = symbols[symIndex++];
                    } else if (numeric <= maxNumeric) {
                        sym = String.valueOf(numeric++);
                    } else {
                        sizeIndex++;
                        if (sizeIndex == sizes.length) {
                            return endOfData();
                        }

                        symIndex = 0;
                        numeric = 0;
                        dir = new File(outDir, sizes[sizeIndex]);
                        continue;
                    }
                    String symbol = sym.replaceAll("/", "");
                    File dst = new File(dir, symbol + ".gif");

                    /**
                     * Handle a bug on Gatherer where a few symbols are missing
                     * at the large size. Fall back to using the medium symbol
                     * for those cases.
                     */
                    int modSizeIndex = sizeIndex;
                    if (sizeIndex == 2) {
                        switch (sym) {
                            case "WP":
                            case "UP":
                            case "BP":
                            case "RP":
                            case "GP":
                            case "E":
                            case "C":
                                modSizeIndex = 1;
                                break;

                            default:
                            // Nothing to do, symbol is available in the large size
                        }
                    }

                    switch (symbol) {
                        case "T":
                            symbol = "tap";
                            break;
                        case "Q":
                            symbol = "untap";
                            break;
                        case "S":
                            symbol = "snow";
                            break;
                    }

                    String url = format(urlFmt, sizes[modSizeIndex], symbol);

                    return new DownloadJob(sym, fromURL(url), toFile(dst));
                }
            }
        };
    }
}
