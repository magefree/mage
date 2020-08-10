package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/prel
 */
public class ReleaseEvents extends ExpansionSet {

    private static final ReleaseEvents instance = new ReleaseEvents();

    public static ReleaseEvents getInstance() {
        return instance;
    }

    private ReleaseEvents() {
        super("Release Events", "PREL", ExpansionSet.buildDate(2007, 10, 12), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        // Silver-bordered card not implemented
        // cards.add(new SetCardInfo("Ass Whuppin'", 2, Rarity.RARE, mage.cards.a.AssWhuppin.class));
        cards.add(new SetCardInfo("Azorius Guildmage", 9, Rarity.RARE, mage.cards.a.AzoriusGuildmage.class));
        cards.add(new SetCardInfo("Budoka Pupil", "3a", Rarity.UNCOMMON, mage.cards.b.BudokaPupil.class));
        cards.add(new SetCardInfo("Dimir Guildmage", 7, Rarity.RARE, mage.cards.d.DimirGuildmage.class));
        cards.add(new SetCardInfo("Force of Nature", 5, Rarity.RARE, mage.cards.f.ForceOfNature.class));
        cards.add(new SetCardInfo("Ghost-Lit Raider", 4, Rarity.RARE, mage.cards.g.GhostLitRaider.class));
        cards.add(new SetCardInfo("Gruul Guildmage", 8, Rarity.RARE, mage.cards.g.GruulGuildmage.class));
        cards.add(new SetCardInfo("Hedge Troll", 11, Rarity.RARE, mage.cards.h.HedgeTroll.class));
        cards.add(new SetCardInfo("Rukh Egg", 1, Rarity.RARE, mage.cards.r.RukhEgg.class));
        // Russian-only printing
        //cards.add(new SetCardInfo("Shivan Dragon", 6, Rarity.RARE, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Shriekmaw", 13, Rarity.RARE, mage.cards.s.Shriekmaw.class));
        cards.add(new SetCardInfo("Storm Entity", 12, Rarity.RARE, mage.cards.s.StormEntity.class));
        cards.add(new SetCardInfo("Sudden Shock", 10, Rarity.RARE, mage.cards.s.SuddenShock.class));
     }
}
