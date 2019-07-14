package org.mage.plugins.card.dl.sources;

import mage.cards.Sets;
import org.mage.plugins.card.dl.DownloadServiceInfo;
import org.mage.plugins.card.images.CardDownloadData;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author spjspj
 */
public enum CopyPasteImageSource implements CardImageSource {

    instance;

    private Set<String> supportedSets = new LinkedHashSet<String>();
    private Set<String> missingCards = new LinkedHashSet<String>();
    HashMap<String, String> singleLinks = null;
    boolean loadedFromDialog = false;
    boolean viewMissingCards = true;
    HashMap<String, Integer> singleLinksDone = null;
    private static int maxTimes = 2;

    @Override
    public String getSourceName() {
        return "";
    }

    @Override
    public float getAverageSize() {
        return 260.7f;
    }

    @Override
    public String getNextHttpImageUrl() {
        if (singleLinks == null) {
            setupLinks();
        }

        for (String key : singleLinksDone.keySet()) {
            if (singleLinksDone.get(key) < maxTimes) {
                singleLinksDone.put(key, maxTimes);
                return key;
            }
        }
        if (maxTimes < 2) {
            maxTimes++;
        }
        for (String key : singleLinksDone.keySet()) {
            if (singleLinksDone.get(key) < maxTimes) {
                singleLinksDone.put(key, maxTimes);
                return key;
            }
        }
        return null;
    }

    @Override
    public String getFileForHttpImage(String httpImageUrl) {
        String copy = httpImageUrl;
        if (copy != null) {
            return singleLinks.get(copy);
        }
        return null;
    }

    @Override
    public boolean prepareDownloadList(DownloadServiceInfo downloadServiceInfo, List<CardDownloadData> downloadList) {
        return true;
    }

    @Override
    public CardImageUrls generateCardUrl(CardDownloadData card) throws Exception {
        if (singleLinks == null) {
            setupLinks();
        }
        String url = singleLinks.get(card.getSet() + "/" + card.getName());
        if (url != null && url.length() > 0) {
            return new CardImageUrls(url);
        }
        url = singleLinks.get(card.getSet() + "/" + card.getName() + "." + card.getCollectorId());
        if (url != null && url.length() > 0) {
            return new CardImageUrls(url);
        }
        return null;
    }

    int ls_size_mc = 0;
    int ls_size_ss = 0;
    int ls_size_sl = 0;
    int num_nos = 0;

    private boolean isDifferent() {
        boolean isdiff = false;
        if (ls_size_mc != missingCards.size()) {
            ls_size_mc = missingCards.size();
            isdiff = true;
        }
        if (ls_size_ss != supportedSets.size()) {
            ls_size_ss = supportedSets.size();
            isdiff = true;
        }
        if (ls_size_sl != singleLinks.size()) {
            ls_size_sl = singleLinks.size();
            isdiff = true;
        }
        num_nos++;
        if (num_nos > 2) {
            num_nos = 0;
            isdiff = true;
        }
        return isdiff;
    }

    private void setupLinks() {
        if (singleLinks != null && loadedFromDialog) {
            if (!viewMissingCards) {
                if (isDifferent() && JOptionPane.showConfirmDialog(null,
                        "View your missing cards and reset the list of card images to download again?",
                        "View missing cards (found " + missingCards.size() + ") / Reset URLs to download ", JOptionPane.YES_NO_OPTION)
                        == JOptionPane.YES_OPTION) {
                    viewMissingCards = true;
                    singleLinks.clear();
                    loadedFromDialog = false;
                    supportedSets.clear();
                } else {
                    return;
                }
            }
            if (!(viewMissingCards && missingCards.size() > 0)) {
                return;
            }
        }
        singleLinks = new HashMap<>();
        loadedFromDialog = false;

        final CopyPasteImageSourceDialog dialog = new CopyPasteImageSourceDialog();
        dialog.pack();
        int count = 0;
        if (viewMissingCards && !missingCards.isEmpty() && singleLinks.isEmpty()) {
            viewMissingCards = false;
            String displayMissingCardsStr = "Up to the first 20 cards are:\n";
            String missingCardsStr = "";
            if (this.missingCards != null) {
                for (String card : this.missingCards) {
                    if (count < 20) {
                        displayMissingCardsStr = displayMissingCardsStr + card + "\n";
                    }
                    missingCardsStr = missingCardsStr + card + "\n";

                    count++;
                }
            }
            StringSelection stringSelection = new StringSelection(missingCardsStr);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

            if (isDifferent() && JOptionPane.showConfirmDialog(null,
                    displayMissingCardsStr + "\n\nReset the list again?\n(NB: The full list has been copied to the clipboard)",
                    "Your missing cards (found " + missingCards.size() + "): ", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION) {
                viewMissingCards = true;
                singleLinks.clear();
                loadedFromDialog = false;
                supportedSets.clear();
            } else {
                return;
            }
        }
        dialog.setVisible(true);
        String[] lines = dialog.getPastedData().split(System.getProperty("line.separator"));

        for (String line : lines) {
            // Break into >>  "\1", "\2"
            Pattern regex = Pattern.compile("\\s*\"(.*?)/(.*?)\"\\s*,\\s*\"(.*?)\"");
            Matcher regexMatcher = regex.matcher(line);
            while (regexMatcher.find()) {
                String setCode = regexMatcher.group(1);
                String cardName = regexMatcher.group(2);
                String imageURL = regexMatcher.group(3);
                supportedSets.add(setCode);
                singleLinks.put(setCode + "/" + cardName, imageURL);
                isDifferent();
            }
        }

        loadedFromDialog = true;
        if (lines.length == 0) {
            loadedFromDialog = false;
            viewMissingCards = true;
        }
    }

    @Override
    public CardImageUrls generateTokenUrl(CardDownloadData card) throws IOException {
        try {
            return generateCardUrl(card);
        } catch (Exception ex) {
        }
        return null;
    }

    @Override
    public int getTotalImages() {
        if (singleLinks == null) {
            setupLinks();
        }
        if (singleLinks != null) {
            return singleLinks.size();
        }
        return -1;
    }

    @Override
    public boolean isTokenSource() {
        return false;
    }

    @Override
    public ArrayList<String> getSupportedSets() {
        setupLinks();
        ArrayList<String> supportedSetsCopy = new ArrayList<>();
        if (supportedSets.isEmpty()) {
            for (String setCode : Sets.getInstance().keySet()) {
                supportedSets.add(setCode);
            }
        }

        supportedSetsCopy.addAll(supportedSets);
        return supportedSetsCopy;
    }

    @Override
    public void doPause(String httpImageUrl) {
    }

    @Override
    public boolean isCardImageProvided(String setCode, String cardName) {
        missingCards.add(setCode + "/" + cardName);

        if (singleLinks != null) {
            return singleLinks.containsKey(setCode + "/" + cardName) || singleLinks.containsKey(setCode + "/" + cardName + "-a");
        }
        return false;
    }

    @Override
    public boolean isTokenImageProvided(String setCode, String cardName, Integer tokenNumber) {
        return false;
    }

    @Override
    public boolean isSetSupportedComplete(String setCode) {
        return false;
    }
}
