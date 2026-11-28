package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class AlchemyEdgeOfEternities extends ExpansionSet {

    private static final AlchemyEdgeOfEternities instance = new AlchemyEdgeOfEternities();

    public static AlchemyEdgeOfEternities getInstance() {
        return instance;
    }

    private AlchemyEdgeOfEternities() {
        super("Alchemy: Edge of Eternities", "YEOE", ExpansionSet.buildDate(2025, 8, 19), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Clifftop Retreat", 31, Rarity.RARE, mage.cards.c.ClifftopRetreat.class));
        cards.add(new SetCardInfo("Dragonskull Summit", 32, Rarity.RARE, mage.cards.d.DragonskullSummit.class));
        cards.add(new SetCardInfo("Drowned Catacomb", 33, Rarity.RARE, mage.cards.d.DrownedCatacomb.class));
        cards.add(new SetCardInfo("Flametongue Kavu", 41, Rarity.UNCOMMON, mage.cards.f.FlametongueKavu.class));
        cards.add(new SetCardInfo("Glacial Fortress", 34, Rarity.RARE, mage.cards.g.GlacialFortress.class));
        cards.add(new SetCardInfo("Hinterland Harbor", 35, Rarity.RARE, mage.cards.h.HinterlandHarbor.class));
        cards.add(new SetCardInfo("Isolated Chapel", 36, Rarity.RARE, mage.cards.i.IsolatedChapel.class));
        cards.add(new SetCardInfo("Rootbound Crag", 37, Rarity.RARE, mage.cards.r.RootboundCrag.class));
        cards.add(new SetCardInfo("Sulfur Falls", 38, Rarity.RARE, mage.cards.s.SulfurFalls.class));
        cards.add(new SetCardInfo("Sunpetal Grove", 39, Rarity.RARE, mage.cards.s.SunpetalGrove.class));
        cards.add(new SetCardInfo("Val, Marooned Surveyor", 26, Rarity.UNCOMMON, mage.cards.v.ValMaroonedSurveyor.class));
        cards.add(new SetCardInfo("Woodland Cemetery", 40, Rarity.RARE, mage.cards.w.WoodlandCemetery.class));

    }
}
