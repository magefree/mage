package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class FinalFantasyCommander extends ExpansionSet {

    private static final FinalFantasyCommander instance = new FinalFantasyCommander();

    public static FinalFantasyCommander getInstance() {
        return instance;
    }

    private FinalFantasyCommander() {
        super("Final Fantasy Commander", "FIC", ExpansionSet.buildDate(2025, 6, 13), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Celes, Rune Knight", 1, Rarity.MYTHIC, mage.cards.c.CelesRuneKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Celes, Rune Knight", 167, Rarity.MYTHIC, mage.cards.c.CelesRuneKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Celes, Rune Knight", 201, Rarity.MYTHIC, mage.cards.c.CelesRuneKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Celes, Rune Knight", 209, Rarity.MYTHIC, mage.cards.c.CelesRuneKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Celes, Rune Knight", 220, Rarity.MYTHIC, mage.cards.c.CelesRuneKnight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud, Ex-SOLDIER", 168, Rarity.MYTHIC, mage.cards.c.CloudExSOLDIER.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud, Ex-SOLDIER", 2, Rarity.MYTHIC, mage.cards.c.CloudExSOLDIER.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud, Ex-SOLDIER", 202, Rarity.MYTHIC, mage.cards.c.CloudExSOLDIER.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud, Ex-SOLDIER", 210, Rarity.MYTHIC, mage.cards.c.CloudExSOLDIER.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud, Ex-SOLDIER", 221, Rarity.MYTHIC, mage.cards.c.CloudExSOLDIER.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Herald's Horn", 228, Rarity.RARE, mage.cards.h.HeraldsHorn.class));
        cards.add(new SetCardInfo("Hildibrand Manderville", 173, Rarity.RARE, mage.cards.h.HildibrandManderville.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hildibrand Manderville", 83, Rarity.RARE, mage.cards.h.HildibrandManderville.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Secret Rendezvous", 217, Rarity.UNCOMMON, mage.cards.s.SecretRendezvous.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Secret Rendezvous", 218, Rarity.UNCOMMON, mage.cards.s.SecretRendezvous.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Secret Rendezvous", 219, Rarity.UNCOMMON, mage.cards.s.SecretRendezvous.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Secret Rendezvous", 253, Rarity.UNCOMMON, mage.cards.s.SecretRendezvous.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terra, Herald of Hope", 186, Rarity.MYTHIC, mage.cards.t.TerraHeraldOfHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terra, Herald of Hope", 204, Rarity.MYTHIC, mage.cards.t.TerraHeraldOfHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terra, Herald of Hope", 212, Rarity.MYTHIC, mage.cards.t.TerraHeraldOfHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terra, Herald of Hope", 223, Rarity.MYTHIC, mage.cards.t.TerraHeraldOfHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terra, Herald of Hope", 4, Rarity.MYTHIC, mage.cards.t.TerraHeraldOfHope.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tifa, Martial Artist", 188, Rarity.MYTHIC, mage.cards.t.TifaMartialArtist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tifa, Martial Artist", 206, Rarity.MYTHIC, mage.cards.t.TifaMartialArtist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tifa, Martial Artist", 214, Rarity.MYTHIC, mage.cards.t.TifaMartialArtist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tifa, Martial Artist", 225, Rarity.MYTHIC, mage.cards.t.TifaMartialArtist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tifa, Martial Artist", 6, Rarity.MYTHIC, mage.cards.t.TifaMartialArtist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Y'shtola, Night's Blessed", 191, Rarity.MYTHIC, mage.cards.y.YshtolaNightsBlessed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Y'shtola, Night's Blessed", 207, Rarity.MYTHIC, mage.cards.y.YshtolaNightsBlessed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Y'shtola, Night's Blessed", 215, Rarity.MYTHIC, mage.cards.y.YshtolaNightsBlessed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Y'shtola, Night's Blessed", 226, Rarity.MYTHIC, mage.cards.y.YshtolaNightsBlessed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Y'shtola, Night's Blessed", 7, Rarity.MYTHIC, mage.cards.y.YshtolaNightsBlessed.class, NON_FULL_USE_VARIOUS));
    }
}
