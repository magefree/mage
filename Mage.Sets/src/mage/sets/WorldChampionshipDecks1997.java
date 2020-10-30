package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/wc97
 */
public class WorldChampionshipDecks1997 extends ExpansionSet {

    private static final WorldChampionshipDecks1997 instance = new WorldChampionshipDecks1997();

    public static WorldChampionshipDecks1997 getInstance() {
        return instance;
    }

    private WorldChampionshipDecks1997() {
        super("World Championship Decks 1997", "WC97", ExpansionSet.buildDate(1997, 8, 13), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Abduction", "pm30", Rarity.UNCOMMON, mage.cards.a.Abduction.class));
        cards.add(new SetCardInfo("Abeyance", "jk1", Rarity.RARE, mage.cards.a.Abeyance.class));
        cards.add(new SetCardInfo("Black Knight", "js143", Rarity.UNCOMMON, mage.cards.b.BlackKnight.class));
        cards.add(new SetCardInfo("Bounty of the Hunt", "sg85sb", Rarity.UNCOMMON, mage.cards.b.BountyOfTheHunt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bounty of the Hunt", "sg85", Rarity.UNCOMMON, mage.cards.b.BountyOfTheHunt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Choking Sands", "js113", Rarity.COMMON, mage.cards.c.ChokingSands.class));
        cards.add(new SetCardInfo("Circle of Protection: Black", "jk17sb", Rarity.COMMON, mage.cards.c.CircleOfProtectionBlack.class));
        cards.add(new SetCardInfo("Circle of Protection: Red", "jk20sb", Rarity.COMMON, mage.cards.c.CircleOfProtectionRed.class));
        cards.add(new SetCardInfo("City of Brass", "pm413", Rarity.RARE, mage.cards.c.CityOfBrass.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("City of Brass", "js413", Rarity.RARE, mage.cards.c.CityOfBrass.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("City of Brass", "jk413", Rarity.RARE, mage.cards.c.CityOfBrass.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("City of Solitude", "sg102sb", Rarity.RARE, mage.cards.c.CityOfSolitude.class));
        cards.add(new SetCardInfo("Cloud Elemental", "pm29", Rarity.COMMON, mage.cards.c.CloudElemental.class));
        cards.add(new SetCardInfo("Contagion", "js45", Rarity.UNCOMMON, mage.cards.c.Contagion.class));
        cards.add(new SetCardInfo("Counterspell", "pm77", Rarity.COMMON, mage.cards.c.Counterspell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Counterspell", "jk77", Rarity.COMMON, mage.cards.c.Counterspell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crumble", "sg287sb", Rarity.UNCOMMON, mage.cards.c.Crumble.class));
        cards.add(new SetCardInfo("Disenchant", "js26sb", Rarity.COMMON, mage.cards.d.Disenchant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Disenchant", "jk26sb", Rarity.COMMON, mage.cards.d.Disenchant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Disenchant", "jk26", Rarity.COMMON, mage.cards.d.Disenchant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Disintegrate", "pm219", Rarity.COMMON, mage.cards.d.Disintegrate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Disintegrate", "jk219", Rarity.COMMON, mage.cards.d.Disintegrate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Disrupt", "pm37a", Rarity.COMMON, mage.cards.d.Disrupt.class));
        cards.add(new SetCardInfo("Dissipate", "pm61sb", Rarity.UNCOMMON, mage.cards.d.Dissipate.class));
        cards.add(new SetCardInfo("Dystopia", "js47sb", Rarity.RARE, mage.cards.d.Dystopia.class));
        cards.add(new SetCardInfo("Earthquake", "js223", Rarity.RARE, mage.cards.e.Earthquake.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Earthquake", "jk223sb", Rarity.RARE, mage.cards.e.Earthquake.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ebony Charm", "js120sb", Rarity.COMMON, mage.cards.e.EbonyCharm.class));
        cards.add(new SetCardInfo("Emerald Charm", "sg106sb", Rarity.COMMON, mage.cards.e.EmeraldCharm.class));
        cards.add(new SetCardInfo("Exile", "js3sb", Rarity.RARE, mage.cards.e.Exile.class));
        cards.add(new SetCardInfo("Fallen Askari", "js59", Rarity.COMMON, mage.cards.f.FallenAskari.class));
        cards.add(new SetCardInfo("Force of Will", "pm28", Rarity.UNCOMMON, mage.cards.f.ForceOfWill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Force of Will", "jk28", Rarity.UNCOMMON, mage.cards.f.ForceOfWill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "sg449", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "sg448", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "sg447", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "sg446", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forsaken Wastes", "js125sb", Rarity.RARE, mage.cards.f.ForsakenWastes.class));
        cards.add(new SetCardInfo("Frenetic Efreet", "pm264", Rarity.RARE, mage.cards.f.FreneticEfreet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frenetic Efreet", "jk264", Rarity.RARE, mage.cards.f.FreneticEfreet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fyndhorn Elves", "sg244", Rarity.COMMON, mage.cards.f.FyndhornElves.class));
        cards.add(new SetCardInfo("Gemstone Mine", "js164", Rarity.UNCOMMON, mage.cards.g.GemstoneMine.class));
        cards.add(new SetCardInfo("Ghazban Ogre", "sg298", Rarity.COMMON, mage.cards.g.GhazbanOgre.class));
        cards.add(new SetCardInfo("Giant Growth", "sg299", Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Hammer of Bogardan", "jk181", Rarity.RARE, mage.cards.h.HammerOfBogardan.class));
        cards.add(new SetCardInfo("Harvest Wurm", "sg130", Rarity.COMMON, mage.cards.h.HarvestWurm.class));
        cards.add(new SetCardInfo("Heart of Yavimaya", "sg138", Rarity.RARE, mage.cards.h.HeartOfYavimaya.class));
        cards.add(new SetCardInfo("Honorable Passage", "js7sb", Rarity.UNCOMMON, mage.cards.h.HonorablePassage.class));
        cards.add(new SetCardInfo("Hydroblast", "pm94sb", Rarity.UNCOMMON, mage.cards.h.Hydroblast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hydroblast", "js94sb", Rarity.UNCOMMON, mage.cards.h.Hydroblast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Impulse", "jk34", Rarity.COMMON, mage.cards.i.Impulse.class));
        cards.add(new SetCardInfo("Incinerate", "pm242", Rarity.COMMON, mage.cards.i.Incinerate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Incinerate", "js242", Rarity.COMMON, mage.cards.i.Incinerate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Incinerate", "jk242", Rarity.COMMON, mage.cards.i.Incinerate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "pm437", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "jk437", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "pm436", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "jk436", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "pm435", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "jk435", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "pm434", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "jk434", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jolrael's Centaur", "sg222", Rarity.COMMON, mage.cards.j.JolraelsCentaur.class));
        cards.add(new SetCardInfo("Kjeldoran Outpost", "jk139sb", Rarity.RARE, mage.cards.k.KjeldoranOutpost.class));
        cards.add(new SetCardInfo("Knight of Stromgald", "js171", Rarity.UNCOMMON, mage.cards.k.KnightOfStromgald.class));
        cards.add(new SetCardInfo("Knight of the Mists", "pm36sb", Rarity.COMMON, mage.cards.k.KnightOfTheMists.class));
        cards.add(new SetCardInfo("Lhurgoyf", "sg309", Rarity.RARE, mage.cards.l.Lhurgoyf.class));
        cards.add(new SetCardInfo("Man-o'-War", "pm37b", Rarity.COMMON, mage.cards.m.ManOWar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Man-o'-War", "js37", Rarity.COMMON, mage.cards.m.ManOWar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mind Stone", "pm153", Rarity.COMMON, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Mountain", "pm445", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "jk445", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "pm444", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "jk444", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "pm443", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "jk443", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "pm442", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "jk442", Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Necratog", "js76", Rarity.UNCOMMON, mage.cards.n.Necratog.class));
        cards.add(new SetCardInfo("Nekrataal", "js66", Rarity.UNCOMMON, mage.cards.n.Nekrataal.class));
        cards.add(new SetCardInfo("Nevinyrral's Disk", "pm391sb", Rarity.RARE, mage.cards.n.NevinyrralsDisk.class));
        cards.add(new SetCardInfo("Ophidian", "pm45", Rarity.COMMON, mage.cards.o.Ophidian.class));
        cards.add(new SetCardInfo("Phyrexian Furnace", "pm155sb", Rarity.UNCOMMON, mage.cards.p.PhyrexianFurnace.class));
        cards.add(new SetCardInfo("Pillage", "pm76sb", Rarity.UNCOMMON, mage.cards.p.Pillage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pillage", "jk76", Rarity.UNCOMMON, mage.cards.p.Pillage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", "jk433", Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", "jk432", Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", "jk431", Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", "jk430", Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Political Trickery", "jk81sb", Rarity.RARE, mage.cards.p.PoliticalTrickery.class));
        cards.add(new SetCardInfo("Pyroblast", "pm262sb", Rarity.UNCOMMON, mage.cards.p.Pyroblast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pyroblast", "js262sb", Rarity.UNCOMMON, mage.cards.p.Pyroblast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pyroblast", "jk262sb", Rarity.UNCOMMON, mage.cards.p.Pyroblast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pyrokinesis", "pm78sb", Rarity.UNCOMMON, mage.cards.p.Pyrokinesis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pyrokinesis", "pm78", Rarity.UNCOMMON, mage.cards.p.Pyrokinesis.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quirion Ranger", "sg117", Rarity.COMMON, mage.cards.q.QuirionRanger.class));
        cards.add(new SetCardInfo("River Boa", "sg118sb", Rarity.COMMON, mage.cards.r.RiverBoa.class));
        cards.add(new SetCardInfo("Rogue Elephant", "sg139", Rarity.COMMON, mage.cards.r.RogueElephant.class));
        cards.add(new SetCardInfo("Serrated Arrows", "pm110sb", Rarity.COMMON, mage.cards.s.SerratedArrows.class));
        cards.add(new SetCardInfo("Shadow Guildmage", "js140", Rarity.COMMON, mage.cards.s.ShadowGuildmage.class));
        cards.add(new SetCardInfo("Spectral Bears", "sg98", Rarity.UNCOMMON, mage.cards.s.SpectralBears.class));
        cards.add(new SetCardInfo("Sulfurous Springs", "js424", Rarity.RARE, mage.cards.s.SulfurousSprings.class));
        cards.add(new SetCardInfo("Suq'Ata Lancer", "pm96", Rarity.COMMON, mage.cards.s.SuqAtaLancer.class));
        cards.add(new SetCardInfo("Swamp", "js441", Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", "js440", Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", "js439", Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", "js438", Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swords to Plowshares", "jk54", Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Thawing Glaciers", "jk144", Rarity.RARE, mage.cards.t.ThawingGlaciers.class));
        cards.add(new SetCardInfo("Uktabi Orangutan", "sg123sb", Rarity.UNCOMMON, mage.cards.u.UktabiOrangutan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Uktabi Orangutan", "sg123", Rarity.UNCOMMON, mage.cards.u.UktabiOrangutan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Uktabi Orangutan", "js123", Rarity.UNCOMMON, mage.cards.u.UktabiOrangutan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Underground River", "js426", Rarity.RARE, mage.cards.u.UndergroundRiver.class));
        cards.add(new SetCardInfo("Undiscovered Paradise", "pm167", Rarity.RARE, mage.cards.u.UndiscoveredParadise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Undiscovered Paradise", "js167", Rarity.RARE, mage.cards.u.UndiscoveredParadise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Undiscovered Paradise", "jk167", Rarity.RARE, mage.cards.u.UndiscoveredParadise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Whirling Dervish", "sg341sb", Rarity.UNCOMMON, mage.cards.w.WhirlingDervish.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Whirling Dervish", "sg341", Rarity.UNCOMMON, mage.cards.w.WhirlingDervish.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wildfire Emissary", "pm203", Rarity.UNCOMMON, mage.cards.w.WildfireEmissary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wildfire Emissary", "jk203", Rarity.UNCOMMON, mage.cards.w.WildfireEmissary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Winter Orb", "sg408", Rarity.RARE, mage.cards.w.WinterOrb.class));
     }
}
