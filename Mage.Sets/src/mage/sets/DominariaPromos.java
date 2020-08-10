package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pdom
 */
public class DominariaPromos extends ExpansionSet {

    private static final DominariaPromos instance = new DominariaPromos();

    public static DominariaPromos getInstance() {
        return instance;
    }

    private DominariaPromos() {
        super("Dominaria Promos", "PDOM", ExpansionSet.buildDate(2018, 4, 27), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Adeliz, the Cinder Wind", "190s", Rarity.UNCOMMON, mage.cards.a.AdelizTheCinderWind.class));
        cards.add(new SetCardInfo("Arvad the Cursed", "191s", Rarity.UNCOMMON, mage.cards.a.ArvadTheCursed.class));
        cards.add(new SetCardInfo("Aryel, Knight of Windgrace", "192s", Rarity.RARE, mage.cards.a.AryelKnightOfWindgrace.class));
        cards.add(new SetCardInfo("Baird, Steward of Argive", "4s", Rarity.UNCOMMON, mage.cards.b.BairdStewardOfArgive.class));
        cards.add(new SetCardInfo("Benalish Marshal", "6p", Rarity.RARE, mage.cards.b.BenalishMarshal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Benalish Marshal", "6s", Rarity.RARE, mage.cards.b.BenalishMarshal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blackblade Reforged", "211p", Rarity.RARE, mage.cards.b.BlackbladeReforged.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blackblade Reforged", "211s", Rarity.RARE, mage.cards.b.BlackbladeReforged.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cabal Stronghold", "238p", Rarity.RARE, mage.cards.c.CabalStronghold.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cabal Stronghold", "238s", Rarity.RARE, mage.cards.c.CabalStronghold.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cast Down", 81, Rarity.UNCOMMON, mage.cards.c.CastDown.class));
        cards.add(new SetCardInfo("Clifftop Retreat", "239p", Rarity.RARE, mage.cards.c.ClifftopRetreat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Clifftop Retreat", "239s", Rarity.RARE, mage.cards.c.ClifftopRetreat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Danitha Capashen, Paragon", "12s", Rarity.UNCOMMON, mage.cards.d.DanithaCapashenParagon.class));
        cards.add(new SetCardInfo("Darigaaz Reincarnated", "193s", Rarity.MYTHIC, mage.cards.d.DarigaazReincarnated.class));
        cards.add(new SetCardInfo("Daring Archaeologist", "13s", Rarity.RARE, mage.cards.d.DaringArchaeologist.class));
        cards.add(new SetCardInfo("Demonlord Belzenlok", "86s", Rarity.MYTHIC, mage.cards.d.DemonlordBelzenlok.class));
        cards.add(new SetCardInfo("Dread Shade", "88s", Rarity.RARE, mage.cards.d.DreadShade.class));
        cards.add(new SetCardInfo("Evra, Halcyon Witness", "16s", Rarity.RARE, mage.cards.e.EvraHalcyonWitness.class));
        cards.add(new SetCardInfo("Fall of the Thran", "18s", Rarity.RARE, mage.cards.f.FallOfTheThran.class));
        cards.add(new SetCardInfo("Forebear's Blade", "214p", Rarity.RARE, mage.cards.f.ForebearsBlade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forebear's Blade", "214s", Rarity.RARE, mage.cards.f.ForebearsBlade.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garna, the Bloodflame", "194s", Rarity.UNCOMMON, mage.cards.g.GarnaTheBloodflame.class));
        cards.add(new SetCardInfo("Gilded Lotus", "215p", Rarity.RARE, mage.cards.g.GildedLotus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gilded Lotus", "215s", Rarity.RARE, mage.cards.g.GildedLotus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goblin Chainwhirler", "129p", Rarity.RARE, mage.cards.g.GoblinChainwhirler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goblin Chainwhirler", "129s", Rarity.RARE, mage.cards.g.GoblinChainwhirler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grand Warlord Radha", "195s", Rarity.RARE, mage.cards.g.GrandWarlordRadha.class));
        cards.add(new SetCardInfo("Grunn, the Lonely King", "165s", Rarity.UNCOMMON, mage.cards.g.GrunnTheLonelyKing.class));
        cards.add(new SetCardInfo("Hallar, the Firefletcher", "196s", Rarity.UNCOMMON, mage.cards.h.HallarTheFirefletcher.class));
        cards.add(new SetCardInfo("Haphazard Bombardment", "131s", Rarity.RARE, mage.cards.h.HaphazardBombardment.class));
        cards.add(new SetCardInfo("Helm of the Host", "217p", Rarity.RARE, mage.cards.h.HelmOfTheHost.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Helm of the Host", "217s", Rarity.RARE, mage.cards.h.HelmOfTheHost.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hinterland Harbor", "240p", Rarity.RARE, mage.cards.h.HinterlandHarbor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hinterland Harbor", "240s", Rarity.RARE, mage.cards.h.HinterlandHarbor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("History of Benalia", "21p", Rarity.MYTHIC, mage.cards.h.HistoryOfBenalia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("History of Benalia", "21s", Rarity.MYTHIC, mage.cards.h.HistoryOfBenalia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Isolated Chapel", "241p", Rarity.RARE, mage.cards.i.IsolatedChapel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Isolated Chapel", "241s", Rarity.RARE, mage.cards.i.IsolatedChapel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jaya Ballard", "132s", Rarity.MYTHIC, mage.cards.j.JayaBallard.class));
        cards.add(new SetCardInfo("Jaya's Immolating Inferno", "133s", Rarity.RARE, mage.cards.j.JayasImmolatingInferno.class));
        cards.add(new SetCardInfo("Jhoira, Weatherlight Captain", "197s", Rarity.MYTHIC, mage.cards.j.JhoiraWeatherlightCaptain.class));
        cards.add(new SetCardInfo("Jodah, Archmage Eternal", "198s", Rarity.RARE, mage.cards.j.JodahArchmageEternal.class));
        cards.add(new SetCardInfo("Josu Vess, Lich Knight", "95s", Rarity.RARE, mage.cards.j.JosuVessLichKnight.class));
        cards.add(new SetCardInfo("Kamahl's Druidic Vow", "166s", Rarity.RARE, mage.cards.k.KamahlsDruidicVow.class));
        cards.add(new SetCardInfo("Karn's Temporal Sundering", "55s", Rarity.RARE, mage.cards.k.KarnsTemporalSundering.class));
        cards.add(new SetCardInfo("Karn, Scion of Urza", "1p", Rarity.MYTHIC, mage.cards.k.KarnScionOfUrza.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karn, Scion of Urza", "1s", Rarity.MYTHIC, mage.cards.k.KarnScionOfUrza.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kazarov, Sengir Pureblood", "96s", Rarity.RARE, mage.cards.k.KazarovSengirPureblood.class));
        cards.add(new SetCardInfo("Kwende, Pride of Femeref", "25s", Rarity.UNCOMMON, mage.cards.k.KwendePrideOfFemeref.class));
        cards.add(new SetCardInfo("Lich's Mastery", "98s", Rarity.RARE, mage.cards.l.LichsMastery.class));
        cards.add(new SetCardInfo("Llanowar Elves", 168, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Lyra Dawnbringer", "26p", Rarity.MYTHIC, mage.cards.l.LyraDawnbringer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lyra Dawnbringer", "26s", Rarity.MYTHIC, mage.cards.l.LyraDawnbringer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Marwyn, the Nurturer", "172p", Rarity.RARE, mage.cards.m.MarwynTheNurturer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Marwyn, the Nurturer", "172s", Rarity.RARE, mage.cards.m.MarwynTheNurturer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra's Self-Replicator", "223s", Rarity.RARE, mage.cards.m.MishrasSelfReplicator.class));
        cards.add(new SetCardInfo("Mox Amber", "224s", Rarity.MYTHIC, mage.cards.m.MoxAmber.class));
        cards.add(new SetCardInfo("Muldrotha, the Gravetide", "199s", Rarity.MYTHIC, mage.cards.m.MuldrothaTheGravetide.class));
        cards.add(new SetCardInfo("Multani, Yavimaya's Avatar", "174s", Rarity.MYTHIC, mage.cards.m.MultaniYavimayasAvatar.class));
        cards.add(new SetCardInfo("Naban, Dean of Iteration", "58s", Rarity.RARE, mage.cards.n.NabanDeanOfIteration.class));
        cards.add(new SetCardInfo("Naru Meha, Master Wizard", "59s", Rarity.MYTHIC, mage.cards.n.NaruMehaMasterWizard.class));
        cards.add(new SetCardInfo("Oath of Teferi", "200s", Rarity.RARE, mage.cards.o.OathOfTeferi.class));
        cards.add(new SetCardInfo("Opt", 60, Rarity.COMMON, mage.cards.o.Opt.class));
        cards.add(new SetCardInfo("Phyrexian Scriptures", "100s", Rarity.MYTHIC, mage.cards.p.PhyrexianScriptures.class));
        cards.add(new SetCardInfo("Precognition Field", "61s", Rarity.RARE, mage.cards.p.PrecognitionField.class));
        cards.add(new SetCardInfo("Primevals' Glorious Rebirth", "201s", Rarity.RARE, mage.cards.p.PrimevalsGloriousRebirth.class));
        cards.add(new SetCardInfo("Raff Capashen, Ship's Mage", "202s", Rarity.UNCOMMON, mage.cards.r.RaffCapashenShipsMage.class));
        cards.add(new SetCardInfo("Rite of Belzenlok", "102s", Rarity.RARE, mage.cards.r.RiteOfBelzenlok.class));
        cards.add(new SetCardInfo("Rona, Disciple of Gix", "203s", Rarity.UNCOMMON, mage.cards.r.RonaDiscipleOfGix.class));
        // Japanese-only printing
        //cards.add(new SetCardInfo("Serra Angel", "33c", Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Shalai, Voice of Plenty", "35p", Rarity.RARE, mage.cards.s.ShalaiVoiceOfPlenty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shalai, Voice of Plenty", "35s", Rarity.RARE, mage.cards.s.ShalaiVoiceOfPlenty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shanna, Sisay's Legacy", 204, Rarity.UNCOMMON, mage.cards.s.ShannaSisaysLegacy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shanna, Sisay's Legacy", "204s", Rarity.UNCOMMON, mage.cards.s.ShannaSisaysLegacy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Siege-Gang Commander", "143p", Rarity.RARE, mage.cards.s.SiegeGangCommander.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Siege-Gang Commander", "143s", Rarity.RARE, mage.cards.s.SiegeGangCommander.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Slimefoot, the Stowaway", "205s", Rarity.UNCOMMON, mage.cards.s.SlimefootTheStowaway.class));
        cards.add(new SetCardInfo("Slinn Voda, the Rising Deep", "66s", Rarity.UNCOMMON, mage.cards.s.SlinnVodaTheRisingDeep.class));
        cards.add(new SetCardInfo("Squee, the Immortal", "146p", Rarity.RARE, mage.cards.s.SqueeTheImmortal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Squee, the Immortal", "146s", Rarity.RARE, mage.cards.s.SqueeTheImmortal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steel Leaf Champion", 182, Rarity.RARE, mage.cards.s.SteelLeafChampion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steel Leaf Champion", "182p", Rarity.RARE, mage.cards.s.SteelLeafChampion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steel Leaf Champion", "182s", Rarity.RARE, mage.cards.s.SteelLeafChampion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sulfur Falls", "247p", Rarity.RARE, mage.cards.s.SulfurFalls.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sulfur Falls", "247s", Rarity.RARE, mage.cards.s.SulfurFalls.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sylvan Awakening", "183s", Rarity.RARE, mage.cards.s.SylvanAwakening.class));
        cards.add(new SetCardInfo("Tatyova, Benthic Druid", "206s", Rarity.UNCOMMON, mage.cards.t.TatyovaBenthicDruid.class));
        cards.add(new SetCardInfo("Teferi, Hero of Dominaria", "207s", Rarity.MYTHIC, mage.cards.t.TeferiHeroOfDominaria.class));
        cards.add(new SetCardInfo("Tempest Djinn", "68p", Rarity.RARE, mage.cards.t.TempestDjinn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tempest Djinn", "68s", Rarity.RARE, mage.cards.t.TempestDjinn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Territorial Allosaurus", "184s", Rarity.RARE, mage.cards.t.TerritorialAllosaurus.class));
        cards.add(new SetCardInfo("Teshar, Ancestor's Apostle", "36s", Rarity.RARE, mage.cards.t.TesharAncestorsApostle.class));
        cards.add(new SetCardInfo("Tetsuko Umezawa, Fugitive", "69s", Rarity.UNCOMMON, mage.cards.t.TetsukoUmezawaFugitive.class));
        cards.add(new SetCardInfo("The Antiquities War", "42p", Rarity.RARE, mage.cards.t.TheAntiquitiesWar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Antiquities War", "42s", Rarity.RARE, mage.cards.t.TheAntiquitiesWar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The First Eruption", "122s", Rarity.RARE, mage.cards.t.TheFirstEruption.class));
        cards.add(new SetCardInfo("The Mending of Dominaria", "173s", Rarity.RARE, mage.cards.t.TheMendingOfDominaria.class));
        cards.add(new SetCardInfo("The Mirari Conjecture", "57s", Rarity.RARE, mage.cards.t.TheMirariConjecture.class));
        cards.add(new SetCardInfo("Thran Temporal Gateway", "233s", Rarity.RARE, mage.cards.t.ThranTemporalGateway.class));
        cards.add(new SetCardInfo("Tiana, Ship's Caretaker", "208s", Rarity.UNCOMMON, mage.cards.t.TianaShipsCaretaker.class));
        cards.add(new SetCardInfo("Torgaar, Famine Incarnate", "108s", Rarity.RARE, mage.cards.t.TorgaarFamineIncarnate.class));
        cards.add(new SetCardInfo("Traxos, Scourge of Kroog", "234s", Rarity.RARE, mage.cards.t.TraxosScourgeOfKroog.class));
        cards.add(new SetCardInfo("Two-Headed Giant", "147s", Rarity.RARE, mage.cards.t.TwoHeadedGiant.class));
        cards.add(new SetCardInfo("Urgoros, the Empty One", "109s", Rarity.UNCOMMON, mage.cards.u.UrgorosTheEmptyOne.class));
        cards.add(new SetCardInfo("Urza's Ruinous Blast", "39s", Rarity.RARE, mage.cards.u.UrzasRuinousBlast.class));
        cards.add(new SetCardInfo("Valduk, Keeper of the Flame", "148s", Rarity.UNCOMMON, mage.cards.v.ValdukKeeperOfTheFlame.class));
        cards.add(new SetCardInfo("Verdant Force", "187s", Rarity.RARE, mage.cards.v.VerdantForce.class));
        cards.add(new SetCardInfo("Verix Bladewing", "149s", Rarity.MYTHIC, mage.cards.v.VerixBladewing.class));
        cards.add(new SetCardInfo("Weatherlight", "237s", Rarity.MYTHIC, mage.cards.w.Weatherlight.class));
        cards.add(new SetCardInfo("Whisper, Blood Liturgist", "111s", Rarity.UNCOMMON, mage.cards.w.WhisperBloodLiturgist.class));
        cards.add(new SetCardInfo("Woodland Cemetery", "248p", Rarity.RARE, mage.cards.w.WoodlandCemetery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Woodland Cemetery", "248s", Rarity.RARE, mage.cards.w.WoodlandCemetery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yargle, Glutton of Urborg", "113s", Rarity.UNCOMMON, mage.cards.y.YargleGluttonOfUrborg.class));
        cards.add(new SetCardInfo("Yawgmoth's Vile Offering", "114s", Rarity.RARE, mage.cards.y.YawgmothsVileOffering.class));
        cards.add(new SetCardInfo("Zahid, Djinn of the Lamp", 76, Rarity.RARE, mage.cards.z.ZahidDjinnOfTheLamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zahid, Djinn of the Lamp", "76s", Rarity.RARE, mage.cards.z.ZahidDjinnOfTheLamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zhalfirin Void", 249, Rarity.UNCOMMON, mage.cards.z.ZhalfirinVoid.class));
     }
}
