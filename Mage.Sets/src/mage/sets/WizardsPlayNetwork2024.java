package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pw24
 */
public class WizardsPlayNetwork2024 extends ExpansionSet {

    private static final WizardsPlayNetwork2024 instance = new WizardsPlayNetwork2024();

    public static WizardsPlayNetwork2024 getInstance() {
        return instance;
    }

    private WizardsPlayNetwork2024() {
        super("Wizards Play Network 2024", "PW24", ExpansionSet.buildDate(2024, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Chaos Warp", 7, Rarity.RARE, mage.cards.c.ChaosWarp.class));
        cards.add(new SetCardInfo("Commander's Sphere", 8, Rarity.RARE, mage.cards.c.CommandersSphere.class));
        cards.add(new SetCardInfo("Costly Plunder", 14, Rarity.RARE, mage.cards.c.CostlyPlunder.class, RETRO_ART));
        cards.add(new SetCardInfo("Crippling Fear", 17, Rarity.RARE, mage.cards.c.CripplingFear.class, RETRO_ART));
        cards.add(new SetCardInfo("Darksteel Colossus", 19, Rarity.MYTHIC, mage.cards.d.DarksteelColossus.class));
        cards.add(new SetCardInfo("Diabolic Tutor", 13, Rarity.RARE, mage.cards.d.DiabolicTutor.class, RETRO_ART));
        cards.add(new SetCardInfo("Gaea's Liege", 5, Rarity.RARE, mage.cards.g.GaeasLiege.class));
        cards.add(new SetCardInfo("Goblin King", 4, Rarity.RARE, mage.cards.g.GoblinKing.class));
        cards.add(new SetCardInfo("Heirloom Blade", 16, Rarity.RARE, mage.cards.h.HeirloomBlade.class, RETRO_ART));
        cards.add(new SetCardInfo("Lord of Atlantis", 2, Rarity.RARE, mage.cards.l.LordOfAtlantis.class));
        cards.add(new SetCardInfo("Night's Whisper", 18, Rarity.RARE, mage.cards.n.NightsWhisper.class, RETRO_ART));
        cards.add(new SetCardInfo("Oltec Matterweaver", 12, Rarity.MYTHIC, mage.cards.o.OltecMatterweaver.class));
        cards.add(new SetCardInfo("Ravenous Squirrel", 15, Rarity.RARE, mage.cards.r.RavenousSquirrel.class, RETRO_ART));
        cards.add(new SetCardInfo("Rogue's Passage", 10, Rarity.UNCOMMON, mage.cards.r.RoguesPassage.class, RETRO_ART));
        cards.add(new SetCardInfo("Serra Angel", 1, Rarity.RARE, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Transmutation Font", 11, Rarity.MYTHIC, mage.cards.t.TransmutationFont.class));
        cards.add(new SetCardInfo("Underworld Connections", 9, Rarity.RARE, mage.cards.u.UnderworldConnections.class, RETRO_ART));
        cards.add(new SetCardInfo("Zombie Master", 3, Rarity.RARE, mage.cards.z.ZombieMaster.class));
    }
}
