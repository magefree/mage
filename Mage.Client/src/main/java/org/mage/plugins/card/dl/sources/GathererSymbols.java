package org.mage.plugins.card.dl.sources;

import com.google.common.collect.AbstractIterator;
import mage.client.constants.Constants;
import org.mage.plugins.card.dl.DownloadJob;

import java.io.File;
import java.util.Iterator;

import static java.lang.String.format;
import static org.mage.plugins.card.dl.DownloadJob.fromURL;
import static org.mage.plugins.card.dl.DownloadJob.toFile;
import static org.mage.plugins.card.utils.CardImageUtils.getImagesDir;

/**
 * The class GathererSymbols.
 *
 * @author Clemens Koza
 * @version V0.0 25.08.2010
 */
public class GathererSymbols implements Iterable<DownloadJob> {

    //TODO chaos and planeswalker symbol
    //chaos: https://gatherer.wizards.com/Images/Symbols/chaos.gif

    private static File outDir;

    private static final String urlFmt = "https://gatherer.wizards.com/handlers/image.ashx?size=%1$s&name=%2$s&type=symbol";

    private static final String[] sizes = {"small", "medium", "large"};

    private static final String[] symbols = {"W", "U", "B", "R", "G",
            "W/U", "U/B", "B/R", "R/G", "G/W", "W/B", "U/R", "B/G", "R/W", "G/U",
            "W/U/P", "U/B/P", "B/R/P", "R/G/P", "G/W/P", "W/B/P", "U/R/P", "B/G/P", "R/W/P", "G/U/P",
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
                        // take next char symbol
                        sym = symbols[symIndex++];
                    } else if (numeric <= maxNumeric) {
                        // take next numeric symbol
                        sym = String.valueOf(numeric++);
                    } else {
                        // switch to next size
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

                    // workaround for miss icons on Gatherer (no cards with it, so no icons)
                    // TODO: comment and try download without workaround, keep fix for symbols with "Resource not found" error
                    switch (symbol) {
                        case "WUP":
                        case "BRP":
                        case "BGP":
                        case "WBP":
                        case "UBP":
                        case "URP":
                            // skip icon download
                            continue;
                    }

                    // workaround for miss large size icons on Gatherer (replace it by medium size)
                    // TODO: comment and try download without workaround, keep fix for symbols with "Resource not found" error
                    int modSizeIndex = sizeIndex;
                    if (sizeIndex == 2) {
                        switch (symbol) {
                            case "GUP":
                            case "GWP":
                            case "RGP":
                            case "RWP":
                                // need replace to medium size
                                modSizeIndex = 1;
                                break;
                            default:
                                // normal size
                                break;
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
