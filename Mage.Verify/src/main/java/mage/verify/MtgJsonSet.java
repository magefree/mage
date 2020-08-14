package mage.verify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import static mage.verify.MtgJsonService.MTGJSON_IGNORE_NEW_PROPERTIES;

@JsonIgnoreProperties(ignoreUnknown = MTGJSON_IGNORE_NEW_PROPERTIES)
class MtgJsonSet {
    public List<MtgJsonCard> cards;
    public String code;
    public String name;
    public String releaseDate;
    public int totalSetSize;
}
