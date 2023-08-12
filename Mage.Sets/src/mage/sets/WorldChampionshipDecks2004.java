package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/wc04
 */
public class WorldChampionshipDecks2004 extends ExpansionSet {

    private static final WorldChampionshipDecks2004 instance = new WorldChampionshipDecks2004();

    public static WorldChampionshipDecks2004 getInstance() {
        return instance;
    }

    private WorldChampionshipDecks2004() {
        super("World Championship Decks 2004", "WC04", ExpansionSet.buildDate(2004, 9, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Akroma's Vengeance", "gn2", Rarity.RARE, mage.cards.a.AkromasVengeance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Akroma's Vengeance", "jn2", Rarity.RARE, mage.cards.a.AkromasVengeance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ancient Den", "mb278", Rarity.COMMON, mage.cards.a.AncientDen.class));
        cards.add(new SetCardInfo("Annul", "ap29sb", Rarity.COMMON, mage.cards.a.Annul.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Annul", "gn29", Rarity.COMMON, mage.cards.a.Annul.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arcbound Ravager", "ap100", Rarity.RARE, mage.cards.a.ArcboundRavager.class));
        cards.add(new SetCardInfo("Arcbound Worker", "ap104a", Rarity.COMMON, mage.cards.a.ArcboundWorker.class));
        cards.add(new SetCardInfo("Astral Slide", "jn4", Rarity.UNCOMMON, mage.cards.a.AstralSlide.class));
        cards.add(new SetCardInfo("Blinkmoth Nexus", "ap163", Rarity.RARE, mage.cards.b.BlinkmothNexus.class));
        cards.add(new SetCardInfo("Chrome Mox", "ap152", Rarity.RARE, mage.cards.c.ChromeMox.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chrome Mox", "mb152", Rarity.RARE, mage.cards.c.ChromeMox.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Circle of Protection: Red", "jn13sb", Rarity.UNCOMMON, mage.cards.c.CircleOfProtectionRed.class));
        cards.add(new SetCardInfo("Cloudpost", "gn280", Rarity.COMMON, mage.cards.c.Cloudpost.class));
        cards.add(new SetCardInfo("Condescend", "gn27", Rarity.COMMON, mage.cards.c.Condescend.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Condescend", "mb27", Rarity.COMMON, mage.cards.c.Condescend.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cranial Plating", "ap113", Rarity.COMMON, mage.cards.c.CranialPlating.class));
        cards.add(new SetCardInfo("Darksteel Citadel", "mb164", Rarity.COMMON, mage.cards.d.DarksteelCitadel.class));
        cards.add(new SetCardInfo("Decree of Justice", "gn8", Rarity.RARE, mage.cards.d.DecreeOfJustice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Decree of Justice", "jn8", Rarity.RARE, mage.cards.d.DecreeOfJustice.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Disciple of the Vault", "ap62a", Rarity.COMMON, mage.cards.d.DiscipleOfTheVault.class));
        cards.add(new SetCardInfo("Eternal Dragon", "gn12a", Rarity.RARE, mage.cards.e.EternalDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eternal Dragon", "jn12", Rarity.RARE, mage.cards.e.EternalDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eternal Witness", "jn86", Rarity.UNCOMMON, mage.cards.e.EternalWitness.class));
        cards.add(new SetCardInfo("Exalted Angel", "gn28", Rarity.RARE, mage.cards.e.ExaltedAngel.class));
        cards.add(new SetCardInfo("Fabricate", "mb35", Rarity.UNCOMMON, mage.cards.f.Fabricate.class));
        cards.add(new SetCardInfo("Fireball", "mb60", Rarity.UNCOMMON, mage.cards.f.Fireball.class));
        cards.add(new SetCardInfo("Flooded Strand", "gn316", Rarity.RARE, mage.cards.f.FloodedStrand.class));
        cards.add(new SetCardInfo("Forest", "jn347", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "jn348", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "jn350", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frogmite", "ap172", Rarity.COMMON, mage.cards.f.Frogmite.class));
        cards.add(new SetCardInfo("Furnace Dragon", "ap62sb", Rarity.RARE, mage.cards.f.FurnaceDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Furnace Dragon", "mb62sb", Rarity.RARE, mage.cards.f.FurnaceDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glimmervoid", "ap281", Rarity.RARE, mage.cards.g.Glimmervoid.class));
        cards.add(new SetCardInfo("Goblin Charbelcher", "mb176", Rarity.RARE, mage.cards.g.GoblinCharbelcher.class));
        cards.add(new SetCardInfo("Great Furnace", "ap282", Rarity.COMMON, mage.cards.g.GreatFurnace.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Great Furnace", "mb282", Rarity.COMMON, mage.cards.g.GreatFurnace.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "gn335", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "gn336", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "gn337", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Krark-Clan Ironworks", "mb134", Rarity.RARE, mage.cards.k.KrarkClanIronworks.class));
        cards.add(new SetCardInfo("Mana Leak", "gn89", Rarity.COMMON, mage.cards.m.ManaLeak.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Leak", "mb89sb", Rarity.COMMON, mage.cards.m.ManaLeak.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Myr Incubator", "mb212", Rarity.RARE, mage.cards.m.MyrIncubator.class));
        cards.add(new SetCardInfo("Ornithopter", "ap224", Rarity.UNCOMMON, mage.cards.o.Ornithopter.class));
        cards.add(new SetCardInfo("Oxidize", "jn79sb", Rarity.UNCOMMON, mage.cards.o.Oxidize.class));
        cards.add(new SetCardInfo("Pacifism", "gn33sb", Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Pentad Prism", "mb143", Rarity.COMMON, mage.cards.p.PentadPrism.class));
        cards.add(new SetCardInfo("Plains", "gn331", Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", "gn332", Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", "gn333", Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", "jn332", Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", "jn333", Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", "jn334", Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plow Under", "jn272", Rarity.RARE, mage.cards.p.PlowUnder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plow Under", "jn272sb", Rarity.RARE, mage.cards.p.PlowUnder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Purge", "gn12sbb", Rarity.UNCOMMON, mage.cards.p.Purge.class));
        cards.add(new SetCardInfo("Pyroclasm", "mb210sb", Rarity.UNCOMMON, mage.cards.p.Pyroclasm.class));
        cards.add(new SetCardInfo("Rampant Growth", "jn274", Rarity.COMMON, mage.cards.r.RampantGrowth.class));
        cards.add(new SetCardInfo("Relic Barrier", "gn147sb", Rarity.UNCOMMON, mage.cards.r.RelicBarrier.class));
        cards.add(new SetCardInfo("Renewed Faith", "jn50", Rarity.COMMON, mage.cards.r.RenewedFaith.class));
        cards.add(new SetCardInfo("Rewind", "gn96", Rarity.UNCOMMON, mage.cards.r.Rewind.class));
        cards.add(new SetCardInfo("Rude Awakening", "jn92sb", Rarity.RARE, mage.cards.r.RudeAwakening.class));
        cards.add(new SetCardInfo("Scrabbling Claws", "gn237sb", Rarity.UNCOMMON, mage.cards.s.ScrabblingClaws.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scrabbling Claws", "jn237sb", Rarity.UNCOMMON, mage.cards.s.ScrabblingClaws.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Seat of the Synod", "ap283", Rarity.COMMON, mage.cards.s.SeatOfTheSynod.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Seat of the Synod", "mb283", Rarity.COMMON, mage.cards.s.SeatOfTheSynod.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Secluded Steppe", "jn324", Rarity.COMMON, mage.cards.s.SecludedSteppe.class));
        cards.add(new SetCardInfo("Seething Song", "ap104sb", Rarity.COMMON, mage.cards.s.SeethingSong.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Seething Song", "mb104sb", Rarity.COMMON, mage.cards.s.SeethingSong.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Serum Visions", "ap36sb", Rarity.COMMON, mage.cards.s.SerumVisions.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Serum Visions", "mb36", Rarity.COMMON, mage.cards.s.SerumVisions.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shrapnel Blast", "ap106", Rarity.UNCOMMON, mage.cards.s.ShrapnelBlast.class));
        cards.add(new SetCardInfo("Somber Hoverguard", "ap51", Rarity.COMMON, mage.cards.s.SomberHoverguard.class));
        cards.add(new SetCardInfo("Stifle", "gn52sb", Rarity.RARE, mage.cards.s.Stifle.class));
        cards.add(new SetCardInfo("Talisman of Dominance", "mb253", Rarity.UNCOMMON, mage.cards.t.TalismanOfDominance.class));
        cards.add(new SetCardInfo("Talisman of Progress", "mb256", Rarity.UNCOMMON, mage.cards.t.TalismanOfProgress.class));
        cards.add(new SetCardInfo("Temple of the False God", "gn143", Rarity.UNCOMMON, mage.cards.t.TempleOfTheFalseGod.class));
        cards.add(new SetCardInfo("Thirst for Knowledge", "gn53", Rarity.UNCOMMON, mage.cards.t.ThirstForKnowledge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thirst for Knowledge", "mb53", Rarity.UNCOMMON, mage.cards.t.ThirstForKnowledge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thoughtcast", "ap54", Rarity.COMMON, mage.cards.t.Thoughtcast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thoughtcast", "mb54", Rarity.COMMON, mage.cards.t.Thoughtcast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tranquil Thicket", "jn326", Rarity.COMMON, mage.cards.t.TranquilThicket.class));
        cards.add(new SetCardInfo("Tree of Tales", "mb285", Rarity.COMMON, mage.cards.t.TreeOfTales.class));
        cards.add(new SetCardInfo("Vault of Whispers", "ap286", Rarity.COMMON, mage.cards.v.VaultOfWhispers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vault of Whispers", "mb286", Rarity.COMMON, mage.cards.v.VaultOfWhispers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Viridian Shaman", "jn139", Rarity.UNCOMMON, mage.cards.v.ViridianShaman.class));
        cards.add(new SetCardInfo("Wayfarer's Bauble", "gn165", Rarity.COMMON, mage.cards.w.WayfarersBauble.class));
        cards.add(new SetCardInfo("Welding Jar", "ap274", Rarity.COMMON, mage.cards.w.WeldingJar.class));
        cards.add(new SetCardInfo("Windswept Heath", "jn328", Rarity.RARE, mage.cards.w.WindsweptHeath.class));
        cards.add(new SetCardInfo("Wing Shards", "jn25", Rarity.UNCOMMON, mage.cards.w.WingShards.class));
        cards.add(new SetCardInfo("Wrath of God", "gn58", Rarity.RARE, mage.cards.w.WrathOfGod.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wrath of God", "jn58", Rarity.RARE, mage.cards.w.WrathOfGod.class, NON_FULL_USE_VARIOUS));
    }
}
