package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class CommanderLegends extends ExpansionSet {

    private static final CommanderLegends instance = new CommanderLegends();

    public static CommanderLegends getInstance() {
        return instance;
    }

    private CommanderLegends() {
        super("Commander Legends", "CMR", ExpansionSet.buildDate(2020, 11, 1), SetType.SUPPLEMENTAL);
        this.blockName = "Commander Legends";
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 361;

        cards.add(new SetCardInfo("Alena, Kessig Trapper", 160, Rarity.UNCOMMON, mage.cards.a.AlenaKessigTrapper.class));
        cards.add(new SetCardInfo("Command Tower", 350, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Commander's Sphere", 306, Rarity.COMMON, mage.cards.c.CommandersSphere.class));
        cards.add(new SetCardInfo("Halana, Kessig Ranger", 231, Rarity.UNCOMMON, mage.cards.h.HalanaKessigRanger.class));
        cards.add(new SetCardInfo("Keeper of the Accord", 27, Rarity.RARE, mage.cards.k.KeeperOfTheAccord.class));
        cards.add(new SetCardInfo("Mana Confluence", 721, Rarity.MYTHIC, mage.cards.m.ManaConfluence.class));
        cards.add(new SetCardInfo("Prossh, Skyraider of Kher", 530, Rarity.MYTHIC, mage.cards.p.ProsshSkyraiderOfKher.class));
        cards.add(new SetCardInfo("Rejuvenating Springs", 354, Rarity.RARE, mage.cards.r.RejuvenatingSprings.class));
        cards.add(new SetCardInfo("Sengir, the Dark Baron", 149, Rarity.RARE, mage.cards.s.SengirTheDarkBaron.class));
        cards.add(new SetCardInfo("Spectator Seating", 356, Rarity.RARE, mage.cards.s.SpectatorSeating.class));
        cards.add(new SetCardInfo("Training Center", 358, Rarity.RARE, mage.cards.t.TrainingCenter.class));
        cards.add(new SetCardInfo("Undergrowth Stadium", 359, Rarity.RARE, mage.cards.u.UndergrowthStadium.class));
        cards.add(new SetCardInfo("Vault of Champions", 360, Rarity.RARE, mage.cards.v.VaultOfChampions.class));
    }
}
