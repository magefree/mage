package mage.verify;

import java.util.List;

class JsonCard {
    public String layout;
    public String name;
    public List<String> names; // flip cards
    public String manaCost;
    public int cmc;
    public List<String> colors;
    public List<String> colorIdentity;
    public String type;
    public List<String> supertypes;
    public List<String> types;
    public List<String> subtypes;
    public String text;
    public String power;
    public String toughness;
    public String loyalty;
    public String imageName;
    public boolean starter; // only available in boxed sets and not in boosters
    public int hand; // vanguard
    public int life; // vanguard

    // only available in AllSets.json
    public String artist;
    public String flavor;
    public String id;
    public int multiverseid;
    public String rarity;
    public boolean reserved;
    public int[] variations;
    public String number;
    public String mciNumber;
    public String releaseDate; // promos
    public String border;
    public String watermark;
    public boolean timeshifted;
}
