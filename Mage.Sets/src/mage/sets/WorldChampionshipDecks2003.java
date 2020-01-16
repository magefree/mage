package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/wc03
 */
public class WorldChampionshipDecks2003 extends ExpansionSet {

    private static final WorldChampionshipDecks2003 instance = new WorldChampionshipDecks2003();

    public static WorldChampionshipDecks2003 getInstance() {
        return instance;
    }

    private WorldChampionshipDecks2003() {
        super("World Championship Decks 2003", "WC03", ExpansionSet.buildDate(2003, 8, 6), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Anger", "pk77", Rarity.UNCOMMON, mage.cards.a.Anger.class));
        cards.add(new SetCardInfo("Anurid Brushhopper", "dz137sb", Rarity.RARE, mage.cards.a.AnuridBrushhopper.class));
        cards.add(new SetCardInfo("Aquamoeba", "dh24", Rarity.COMMON, mage.cards.a.Aquamoeba.class));
        cards.add(new SetCardInfo("Arcanis the Omnipotent", "pk66", Rarity.RARE, mage.cards.a.ArcanisTheOmnipotent.class));
        cards.add(new SetCardInfo("Arrogant Wurm", "dh120", Rarity.UNCOMMON, mage.cards.a.ArrogantWurm.class));
        cards.add(new SetCardInfo("Barren Moor", "pk312", Rarity.COMMON, mage.cards.b.BarrenMoor.class));
        cards.add(new SetCardInfo("Basking Rootwalla", "dh121", Rarity.COMMON, mage.cards.b.BaskingRootwalla.class));
        cards.add(new SetCardInfo("Bloodstained Mire", "pk313", Rarity.RARE, mage.cards.b.BloodstainedMire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodstained Mire", "we313", Rarity.RARE, mage.cards.b.BloodstainedMire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Buried Alive", "pk118", Rarity.UNCOMMON, mage.cards.b.BuriedAlive.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Buried Alive", "pk118sb", Rarity.UNCOMMON, mage.cards.b.BuriedAlive.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Burning Wish", "pk83", Rarity.RARE, mage.cards.b.BurningWish.class));
        cards.add(new SetCardInfo("Cabal Therapy", "pk62", Rarity.UNCOMMON, mage.cards.c.CabalTherapy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cabal Therapy", "pk62sb", Rarity.UNCOMMON, mage.cards.c.CabalTherapy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cabal Therapy", "we62sb", Rarity.UNCOMMON, mage.cards.c.CabalTherapy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Careful Study", "dh70", Rarity.COMMON, mage.cards.c.CarefulStudy.class));
        cards.add(new SetCardInfo("Centaur Garden", "dh316", Rarity.UNCOMMON, mage.cards.c.CentaurGarden.class));
        cards.add(new SetCardInfo("Chainer's Edict", "pk57", Rarity.UNCOMMON, mage.cards.c.ChainersEdict.class));
        cards.add(new SetCardInfo("Circular Logic", "dh33", Rarity.UNCOMMON, mage.cards.c.CircularLogic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Circular Logic", "dz33", Rarity.UNCOMMON, mage.cards.c.CircularLogic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Circular Logic", "dz33sb", Rarity.UNCOMMON, mage.cards.c.CircularLogic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("City of Brass", "dh322", Rarity.RARE, mage.cards.c.CityOfBrass.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("City of Brass", "we322", Rarity.RARE, mage.cards.c.CityOfBrass.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Compulsion", "dz34", Rarity.UNCOMMON, mage.cards.c.Compulsion.class));
        cards.add(new SetCardInfo("Cunning Wish", "dz37", Rarity.RARE, mage.cards.c.CunningWish.class));
        cards.add(new SetCardInfo("Dark Banishing", "we123sb", Rarity.COMMON, mage.cards.d.DarkBanishing.class));
        cards.add(new SetCardInfo("Decompose", "pk128sb", Rarity.UNCOMMON, mage.cards.d.Decompose.class));
        cards.add(new SetCardInfo("Decree of Justice", "dz8", Rarity.RARE, mage.cards.d.DecreeOfJustice.class));
        cards.add(new SetCardInfo("Deep Analysis", "dh36", Rarity.COMMON, mage.cards.d.DeepAnalysis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deep Analysis", "dh36sb", Rarity.COMMON, mage.cards.d.DeepAnalysis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deep Analysis", "dz36", Rarity.COMMON, mage.cards.d.DeepAnalysis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Demolish", "pk183sb", Rarity.UNCOMMON, mage.cards.d.Demolish.class));
        cards.add(new SetCardInfo("Doomed Necromancer", "pk140", Rarity.RARE, mage.cards.d.DoomedNecromancer.class));
        cards.add(new SetCardInfo("Elfhame Palace", "dz324", Rarity.UNCOMMON, mage.cards.e.ElfhamePalace.class));
        cards.add(new SetCardInfo("Entomb", "pk132", Rarity.RARE, mage.cards.e.Entomb.class));
        cards.add(new SetCardInfo("Envelop", "dh39sb", Rarity.COMMON, mage.cards.e.Envelop.class));
        cards.add(new SetCardInfo("Exalted Angel", "dz28sb", Rarity.RARE, mage.cards.e.ExaltedAngel.class));
        cards.add(new SetCardInfo("Flaring Pain", "we89sb", Rarity.COMMON, mage.cards.f.FlaringPain.class));
        cards.add(new SetCardInfo("Flooded Strand", "dz316", Rarity.RARE, mage.cards.f.FloodedStrand.class));
        cards.add(new SetCardInfo("Forest", "dh348", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "dh349", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "dh350", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "dz347", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "dz348", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "dz349", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gempalm Incinerator", "we94", Rarity.UNCOMMON, mage.cards.g.GempalmIncinerator.class));
        cards.add(new SetCardInfo("Goblin Grappler", "we100", Rarity.COMMON, mage.cards.g.GoblinGrappler.class));
        cards.add(new SetCardInfo("Goblin Piledriver", "we205", Rarity.RARE, mage.cards.g.GoblinPiledriver.class));
        cards.add(new SetCardInfo("Goblin Sharpshooter", "we207", Rarity.RARE, mage.cards.g.GoblinSharpshooter.class));
        cards.add(new SetCardInfo("Goblin Sledder", "we209", Rarity.COMMON, mage.cards.g.GoblinSledder.class));
        cards.add(new SetCardInfo("Goblin Taskmaster", "we210", Rarity.COMMON, mage.cards.g.GoblinTaskmaster.class));
        cards.add(new SetCardInfo("Goblin Warchief", "we97", Rarity.UNCOMMON, mage.cards.g.GoblinWarchief.class));
        cards.add(new SetCardInfo("Guiltfeeder", "pk68sb", Rarity.RARE, mage.cards.g.Guiltfeeder.class));
        cards.add(new SetCardInfo("Haunting Echoes", "pk142sb", Rarity.RARE, mage.cards.h.HauntingEchoes.class));
        cards.add(new SetCardInfo("Hunting Pack", "dz121sb", Rarity.UNCOMMON, mage.cards.h.HuntingPack.class));
        cards.add(new SetCardInfo("Innocent Blood", "pk145", Rarity.COMMON, mage.cards.i.InnocentBlood.class));
        cards.add(new SetCardInfo("Island", "dh336", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "dh337", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "dh338", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "dz335", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "dz336", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "dz337", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Krosan Reclamation", "dh122", Rarity.UNCOMMON, mage.cards.k.KrosanReclamation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Krosan Reclamation", "dh122sb", Rarity.UNCOMMON, mage.cards.k.KrosanReclamation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Krosan Reclamation", "dz122sb", Rarity.UNCOMMON, mage.cards.k.KrosanReclamation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Krosan Verge", "dz141", Rarity.UNCOMMON, mage.cards.k.KrosanVerge.class));
        cards.add(new SetCardInfo("Last Rites", "pk146sb", Rarity.COMMON, mage.cards.l.LastRites.class));
        cards.add(new SetCardInfo("Mana Leak", "dh89sb", Rarity.COMMON, mage.cards.m.ManaLeak.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Leak", "dz89", Rarity.COMMON, mage.cards.m.ManaLeak.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mirari", "dz303", Rarity.RARE, mage.cards.m.Mirari.class));
        cards.add(new SetCardInfo("Mirari's Wake", "dz139", Rarity.RARE, mage.cards.m.MirarisWake.class));
        cards.add(new SetCardInfo("Moment's Peace", "dz251", Rarity.COMMON, mage.cards.m.MomentsPeace.class));
        cards.add(new SetCardInfo("Mountain", "pk344", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "pk345", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "pk346", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "we343", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "we344", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "we346", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nantuko Vigilante", "dh132sb", Rarity.COMMON, mage.cards.n.NantukoVigilante.class));
        cards.add(new SetCardInfo("Patriarch's Bidding", "pk161sb", Rarity.RARE, mage.cards.p.PatriarchsBidding.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Patriarch's Bidding", "we161", Rarity.RARE, mage.cards.p.PatriarchsBidding.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phantom Centaur", "dh127sb", Rarity.UNCOMMON, mage.cards.p.PhantomCentaur.class));
        cards.add(new SetCardInfo("Phantom Nishoba", "pk190", Rarity.RARE, mage.cards.p.PhantomNishoba.class));
        cards.add(new SetCardInfo("Plains", "dz331", Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", "dz332", Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", "dz333", Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pyroclasm", "pk210sb", Rarity.UNCOMMON, mage.cards.p.Pyroclasm.class));
        cards.add(new SetCardInfo("Quiet Speculation", "dh49", Rarity.UNCOMMON, mage.cards.q.QuietSpeculation.class));
        cards.add(new SetCardInfo("Ray of Distortion", "dz42sb", Rarity.COMMON, mage.cards.r.RayOfDistortion.class));
        cards.add(new SetCardInfo("Ray of Revelation", "dh20", Rarity.COMMON, mage.cards.r.RayOfRevelation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ray of Revelation", "dh20sb", Rarity.COMMON, mage.cards.r.RayOfRevelation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ray of Revelation", "dz20sb", Rarity.COMMON, mage.cards.r.RayOfRevelation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Recoup", "pk216", Rarity.UNCOMMON, mage.cards.r.Recoup.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Recoup", "pk216sb", Rarity.UNCOMMON, mage.cards.r.Recoup.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Renewed Faith", "dz50", Rarity.COMMON, mage.cards.r.RenewedFaith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Renewed Faith", "dz50sb", Rarity.COMMON, mage.cards.r.RenewedFaith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Roar of the Wurm", "dh266", Rarity.UNCOMMON, mage.cards.r.RoarOfTheWurm.class));
        cards.add(new SetCardInfo("Shadowblood Ridge", "pk326", Rarity.RARE, mage.cards.s.ShadowbloodRidge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shadowblood Ridge", "we326", Rarity.RARE, mage.cards.s.ShadowbloodRidge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sickening Dreams", "pk83sb", Rarity.UNCOMMON, mage.cards.s.SickeningDreams.class));
        cards.add(new SetCardInfo("Siege-Gang Commander", "we103", Rarity.RARE, mage.cards.s.SiegeGangCommander.class));
        cards.add(new SetCardInfo("Skirk Prospector", "we230", Rarity.COMMON, mage.cards.s.SkirkProspector.class));
        cards.add(new SetCardInfo("Skycloud Expanse", "dz327", Rarity.RARE, mage.cards.s.SkycloudExpanse.class));
        cards.add(new SetCardInfo("Smother", "pk170", Rarity.UNCOMMON, mage.cards.s.Smother.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Smother", "we170", Rarity.UNCOMMON, mage.cards.s.Smother.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Smother", "we170sb", Rarity.UNCOMMON, mage.cards.s.Smother.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soul Feast", "pk165sb", Rarity.UNCOMMON, mage.cards.s.SoulFeast.class));
        cards.add(new SetCardInfo("Sparksmith", "we235", Rarity.COMMON, mage.cards.s.Sparksmith.class));
        cards.add(new SetCardInfo("Starstorm", "we328sb", Rarity.RARE, mage.cards.s.Starstorm.class));
        cards.add(new SetCardInfo("Stitch Together", "pk72", Rarity.UNCOMMON, mage.cards.s.StitchTogether.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stitch Together", "pk72sb", Rarity.UNCOMMON, mage.cards.s.StitchTogether.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stupefying Touch", "dh48sb", Rarity.UNCOMMON, mage.cards.s.StupefyingTouch.class));
        cards.add(new SetCardInfo("Sulfuric Vortex", "we106sb", Rarity.RARE, mage.cards.s.SulfuricVortex.class));
        cards.add(new SetCardInfo("Swamp", "pk339", Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", "pk340", Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", "pk341", Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", "we339", Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", "we340", Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", "we342", Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Symbiotic Wurm", "pk289", Rarity.RARE, mage.cards.s.SymbioticWurm.class));
        cards.add(new SetCardInfo("Undead Gladiator", "pk178", Rarity.RARE, mage.cards.u.UndeadGladiator.class));
        cards.add(new SetCardInfo("Unsummon", "dh112", Rarity.COMMON, mage.cards.u.Unsummon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unsummon", "dh112sb", Rarity.COMMON, mage.cards.u.Unsummon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vengeful Dreams", "dz21", Rarity.RARE, mage.cards.v.VengefulDreams.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vengeful Dreams", "dz21sb", Rarity.RARE, mage.cards.v.VengefulDreams.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Visara the Dreadful", "pk179", Rarity.RARE, mage.cards.v.VisaraTheDreadful.class));
        cards.add(new SetCardInfo("Wild Mongrel", "dh283", Rarity.COMMON, mage.cards.w.WildMongrel.class));
        cards.add(new SetCardInfo("Wing Shards", "dz25sb", Rarity.UNCOMMON, mage.cards.w.WingShards.class));
        cards.add(new SetCardInfo("Wonder", "dh54", Rarity.UNCOMMON, mage.cards.w.Wonder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wonder", "dh54sb", Rarity.UNCOMMON, mage.cards.w.Wonder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wrath of God", "dz58", Rarity.RARE, mage.cards.w.WrathOfGod.class));
        cards.add(new SetCardInfo("Zombify", "pk174", Rarity.UNCOMMON, mage.cards.z.Zombify.class));
     }
}
