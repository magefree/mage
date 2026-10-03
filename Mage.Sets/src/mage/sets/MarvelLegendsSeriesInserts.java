package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/lmar
 */
public class MarvelLegendsSeriesInserts extends ExpansionSet {

    private static final MarvelLegendsSeriesInserts instance = new MarvelLegendsSeriesInserts();

    public static MarvelLegendsSeriesInserts getInstance() {
        return instance;
    }

    private MarvelLegendsSeriesInserts() {
        super("Marvel Legends Series Inserts", "LMAR", ExpansionSet.buildDate(2025, 9, 30), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Anti-Venom, Horrifying Healer", 1, Rarity.MYTHIC, mage.cards.a.AntiVenomHorrifyingHealer.class));
        cards.add(new SetCardInfo("Captain America, Team Leader", 6, Rarity.MYTHIC, mage.cards.c.CaptainAmericaTeamLeader.class));
        cards.add(new SetCardInfo("Doctor Doom, King of Latveria", 7, Rarity.MYTHIC, mage.cards.d.DoctorDoomKingOfLatveria.class));
        cards.add(new SetCardInfo("Huntmaster of the Fells", 3, Rarity.RARE, mage.cards.h.HuntmasterOfTheFells.class));
        cards.add(new SetCardInfo("Invisible Woman", 5, Rarity.MYTHIC, mage.cards.i.InvisibleWoman.class));
        cards.add(new SetCardInfo("Iron Spider, Stark Upgrade", 4, Rarity.RARE, mage.cards.i.IronSpiderStarkUpgrade.class));
        cards.add(new SetCardInfo("Spectacular Spider-Man", 2, Rarity.RARE, mage.cards.s.SpectacularSpiderMan.class));
        cards.add(new SetCardInfo("T'Challa, the Black Panther", 8, Rarity.MYTHIC, mage.cards.t.TChallaTheBlackPanther.class));
    }
}
