package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pktk
 */
public class KhansOfTarkirPromos extends ExpansionSet {

    private static final KhansOfTarkirPromos instance = new KhansOfTarkirPromos();

    public static KhansOfTarkirPromos getInstance() {
        return instance;
    }

    private KhansOfTarkirPromos() {
        super("Khans of Tarkir Promos", "PKTK", ExpansionSet.buildDate(2014, 9, 27), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Abzan Ascendancy", "160s", Rarity.RARE, mage.cards.a.AbzanAscendancy.class));
        cards.add(new SetCardInfo("Anafenza, the Foremost", "163s", Rarity.MYTHIC, mage.cards.a.AnafenzaTheForemost.class));
        cards.add(new SetCardInfo("Ankle Shanker", 164, Rarity.RARE, mage.cards.a.AnkleShanker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ankle Shanker", "164s", Rarity.RARE, mage.cards.a.AnkleShanker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Avalanche Tusker", 166, Rarity.RARE, mage.cards.a.AvalancheTusker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Avalanche Tusker", "166s", Rarity.RARE, mage.cards.a.AvalancheTusker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodsoaked Champion", "66s", Rarity.RARE, mage.cards.b.BloodsoakedChampion.class));
        cards.add(new SetCardInfo("Butcher of the Horde", "168s", Rarity.RARE, mage.cards.b.ButcherOfTheHorde.class));
        cards.add(new SetCardInfo("Crackling Doom", "171s", Rarity.RARE, mage.cards.c.CracklingDoom.class));
        cards.add(new SetCardInfo("Crater's Claws", "106s", Rarity.RARE, mage.cards.c.CratersClaws.class));
        cards.add(new SetCardInfo("Deflecting Palm", "173s", Rarity.RARE, mage.cards.d.DeflectingPalm.class));
        cards.add(new SetCardInfo("Dig Through Time", "36s", Rarity.RARE, mage.cards.d.DigThroughTime.class));
        cards.add(new SetCardInfo("Dragon Throne of Tarkir", 219, Rarity.RARE, mage.cards.d.DragonThroneOfTarkir.class));
        cards.add(new SetCardInfo("Dragon-Style Twins", "108s", Rarity.RARE, mage.cards.d.DragonStyleTwins.class));
        cards.add(new SetCardInfo("Duneblast", "174s", Rarity.RARE, mage.cards.d.Duneblast.class));
        cards.add(new SetCardInfo("Flying Crane Technique", "176s", Rarity.RARE, mage.cards.f.FlyingCraneTechnique.class));
        cards.add(new SetCardInfo("Grim Haruspex", "73s", Rarity.RARE, mage.cards.g.GrimHaruspex.class));
        cards.add(new SetCardInfo("Hardened Scales", "133s", Rarity.RARE, mage.cards.h.HardenedScales.class));
        cards.add(new SetCardInfo("Heir of the Wilds", 134, Rarity.UNCOMMON, mage.cards.h.HeirOfTheWilds.class));
        cards.add(new SetCardInfo("Herald of Anafenza", "12s", Rarity.RARE, mage.cards.h.HeraldOfAnafenza.class));
        cards.add(new SetCardInfo("High Sentinels of Arashin", "13s", Rarity.RARE, mage.cards.h.HighSentinelsOfArashin.class));
        cards.add(new SetCardInfo("Icy Blast", "42s", Rarity.RARE, mage.cards.i.IcyBlast.class));
        cards.add(new SetCardInfo("Ivorytusk Fortress", 179, Rarity.RARE, mage.cards.i.IvorytuskFortress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ivorytusk Fortress", "179s", Rarity.RARE, mage.cards.i.IvorytuskFortress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jeering Instigator", "113s", Rarity.RARE, mage.cards.j.JeeringInstigator.class));
        cards.add(new SetCardInfo("Jeskai Ascendancy", "180s", Rarity.RARE, mage.cards.j.JeskaiAscendancy.class));
        cards.add(new SetCardInfo("Kheru Lich Lord", "182s", Rarity.RARE, mage.cards.k.KheruLichLord.class));
        cards.add(new SetCardInfo("Mardu Ascendancy", "185s", Rarity.RARE, mage.cards.m.MarduAscendancy.class));
        cards.add(new SetCardInfo("Master of Pearls", "18s", Rarity.RARE, mage.cards.m.MasterOfPearls.class));
        cards.add(new SetCardInfo("Narset, Enlightened Master", "190s", Rarity.MYTHIC, mage.cards.n.NarsetEnlightenedMaster.class));
        cards.add(new SetCardInfo("Necropolis Fiend", "82s", Rarity.RARE, mage.cards.n.NecropolisFiend.class));
        cards.add(new SetCardInfo("Rakshasa Vizier", 193, Rarity.RARE, mage.cards.r.RakshasaVizier.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakshasa Vizier", "193s", Rarity.RARE, mage.cards.r.RakshasaVizier.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rattleclaw Mystic", 144, Rarity.RARE, mage.cards.r.RattleclawMystic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rattleclaw Mystic", "144s", Rarity.RARE, mage.cards.r.RattleclawMystic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sage of the Inward Eye", 195, Rarity.RARE, mage.cards.s.SageOfTheInwardEye.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sage of the Inward Eye", "195s", Rarity.RARE, mage.cards.s.SageOfTheInwardEye.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sidisi, Brood Tyrant", "199s", Rarity.MYTHIC, mage.cards.s.SidisiBroodTyrant.class));
        cards.add(new SetCardInfo("Siege Rhino", "200s", Rarity.RARE, mage.cards.s.SiegeRhino.class));
        cards.add(new SetCardInfo("Sultai Ascendancy", "203s", Rarity.RARE, mage.cards.s.SultaiAscendancy.class));
        cards.add(new SetCardInfo("Sultai Charm", 204, Rarity.UNCOMMON, mage.cards.s.SultaiCharm.class));
        cards.add(new SetCardInfo("Surrak Dragonclaw", "206s", Rarity.MYTHIC, mage.cards.s.SurrakDragonclaw.class));
        cards.add(new SetCardInfo("Temur Ascendancy", "207s", Rarity.RARE, mage.cards.t.TemurAscendancy.class));
        cards.add(new SetCardInfo("Thousand Winds", "58s", Rarity.RARE, mage.cards.t.ThousandWinds.class));
        cards.add(new SetCardInfo("Trail of Mystery", "154s", Rarity.RARE, mage.cards.t.TrailOfMystery.class));
        cards.add(new SetCardInfo("Trap Essence", "209s", Rarity.RARE, mage.cards.t.TrapEssence.class));
        cards.add(new SetCardInfo("Utter End", 210, Rarity.RARE, mage.cards.u.UtterEnd.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Utter End", "210s", Rarity.RARE, mage.cards.u.UtterEnd.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Villainous Wealth", "211s", Rarity.RARE, mage.cards.v.VillainousWealth.class));
        cards.add(new SetCardInfo("Zurgo Helmsmasher", "214s", Rarity.MYTHIC, mage.cards.z.ZurgoHelmsmasher.class));
     }
}
