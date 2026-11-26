package org.mage.plugins.card.images;

import mage.cards.ExpansionSet;
import mage.cards.Sets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Whole-set image groups backed by the audited printing inventory in download-format-sets.properties.
 * Partial Arena releases and mixed sets stay whole; individual-card bans do not remove images.
 * Refresh historical coverage with Utils/update_image_download_sets.py.
 */
final class ImageDownloadSets {

    private static final Properties AUDITED_SETS = loadSets();
    private static final Date IXALAN_RELEASE = new GregorianCalendar(2017, Calendar.SEPTEMBER, 29).getTime();

    private ImageDownloadSets() {
    }

    static List<String> historic() {
        return Sets.getInstance().values().stream()
                .filter(ImageDownloadSets::isHistoric)
                .map(ExpansionSet::getCode)
                .sorted()
                .collect(Collectors.toList());
    }

    static List<String> vintage() {
        return Sets.getInstance().values().stream()
                .filter(ImageDownloadSets::isVintage)
                .map(ExpansionSet::getCode)
                .sorted()
                .collect(Collectors.toList());
    }

    static boolean isHistoric(ExpansionSet set) {
        String formats = AUDITED_SETS.getProperty(set.getCode());
        if (formats != null) {
            return formats.contains("H");
        }
        // Keep newly added regular/Arena releases usable until the next data audit.
        return set.getSetType().isHistoricLegal() && !set.getReleaseDate().before(IXALAN_RELEASE);
    }

    static boolean isVintage(ExpansionSet set) {
        String formats = AUDITED_SETS.getProperty(set.getCode());
        return formats != null ? formats.contains("V") : set.getSetType().isEternalLegal();
    }

    private static Properties loadSets() {
        Properties sets = new Properties();
        try (InputStream stream = ImageDownloadSets.class.getResourceAsStream("download-format-sets.properties")) {
            if (stream == null) {
                throw new IllegalStateException("Missing image-download format set data");
            }
            sets.load(new InputStreamReader(stream, StandardCharsets.UTF_8));
        } catch (IOException error) {
            throw new IllegalStateException("Cannot load image-download format set data", error);
        }
        return sets;
    }
}
