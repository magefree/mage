package org.mage.plugins.card.dl.sources;

import java.util.Date;

/**
 * Scryfall API: bulk file links to download
 * <p>
 * API docs: <a href="https://scryfall.com/docs/api/bulk-data">here</a>
 *
 * @author JayDi85
 */
public class ScryfallApiBulkData {

    public String object;
    public String type;
    public Date updated_at;
    public long size;
    public String download_uri;
}
