package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pm19
 */
public class CoreSet2019Promos extends ExpansionSet {

    private static final CoreSet2019Promos instance = new CoreSet2019Promos();

    public static CoreSet2019Promos getInstance() {
        return instance;
    }

    private CoreSet2019Promos() {
        super("Core Set 2019 Promos", "PM19", ExpansionSet.buildDate(2018, 7, 13), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ajani's Last Stand", "4s", Rarity.RARE, mage.cards.a.AjanisLastStand.class));
        cards.add(new SetCardInfo("Ajani, Adversary of Tyrants", "3s", Rarity.MYTHIC, mage.cards.a.AjaniAdversaryOfTyrants.class));
        cards.add(new SetCardInfo("Alpine Moon", "128s", Rarity.RARE, mage.cards.a.AlpineMoon.class));
        cards.add(new SetCardInfo("Amulet of Safekeeping", "226s", Rarity.RARE, mage.cards.a.AmuletOfSafekeeping.class));
        cards.add(new SetCardInfo("Apex of Power", "129s", Rarity.MYTHIC, mage.cards.a.ApexOfPower.class));
        cards.add(new SetCardInfo("Arcades, the Strategist", "212s", Rarity.MYTHIC, mage.cards.a.ArcadesTheStrategist.class));
        cards.add(new SetCardInfo("Banefire", "130p", Rarity.RARE, mage.cards.b.Banefire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Banefire", "130s", Rarity.RARE, mage.cards.b.Banefire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bone Dragon", "88s", Rarity.MYTHIC, mage.cards.b.BoneDragon.class));
        cards.add(new SetCardInfo("Chaos Wand", "228s", Rarity.RARE, mage.cards.c.ChaosWand.class));
        cards.add(new SetCardInfo("Chromium, the Mutable", "214s", Rarity.MYTHIC, mage.cards.c.ChromiumTheMutable.class));
        cards.add(new SetCardInfo("Cleansing Nova", "9p", Rarity.RARE, mage.cards.c.CleansingNova.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cleansing Nova", "9s", Rarity.RARE, mage.cards.c.CleansingNova.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crucible of Worlds", "229s", Rarity.MYTHIC, mage.cards.c.CrucibleOfWorlds.class));
        cards.add(new SetCardInfo("Dark-Dweller Oracle", "134s", Rarity.RARE, mage.cards.d.DarkDwellerOracle.class));
        cards.add(new SetCardInfo("Death Baron", 90, Rarity.RARE, mage.cards.d.DeathBaron.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Death Baron", "90p", Rarity.RARE, mage.cards.d.DeathBaron.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Death Baron", "90s", Rarity.RARE, mage.cards.d.DeathBaron.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Demanding Dragon", "135p", Rarity.RARE, mage.cards.d.DemandingDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Demanding Dragon", "135s", Rarity.RARE, mage.cards.d.DemandingDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Demon of Catastrophes", 91, Rarity.RARE, mage.cards.d.DemonOfCatastrophes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Demon of Catastrophes", "91s", Rarity.RARE, mage.cards.d.DemonOfCatastrophes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Desecrated Tomb", 230, Rarity.RARE, mage.cards.d.DesecratedTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Desecrated Tomb", "230s", Rarity.RARE, mage.cards.d.DesecratedTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Detection Tower", "249s", Rarity.RARE, mage.cards.d.DetectionTower.class));
        cards.add(new SetCardInfo("Dismissive Pyromancer", "136s", Rarity.RARE, mage.cards.d.DismissivePyromancer.class));
        cards.add(new SetCardInfo("Djinn of Wishes", "52s", Rarity.RARE, mage.cards.d.DjinnOfWishes.class));
        cards.add(new SetCardInfo("Dragon's Hoard", "232s", Rarity.RARE, mage.cards.d.DragonsHoard.class));
        cards.add(new SetCardInfo("Elvish Clancaller", "179p", Rarity.RARE, mage.cards.e.ElvishClancaller.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elvish Clancaller", "179s", Rarity.RARE, mage.cards.e.ElvishClancaller.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elvish Rejuvenator", 180, Rarity.COMMON, mage.cards.e.ElvishRejuvenator.class));
        cards.add(new SetCardInfo("Fraying Omnipotence", "97s", Rarity.RARE, mage.cards.f.FrayingOmnipotence.class));
        cards.add(new SetCardInfo("Gigantosaurus", "185p", Rarity.RARE, mage.cards.g.Gigantosaurus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gigantosaurus", "185s", Rarity.RARE, mage.cards.g.Gigantosaurus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goblin Trashmaster", "144p", Rarity.RARE, mage.cards.g.GoblinTrashmaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goblin Trashmaster", "144s", Rarity.RARE, mage.cards.g.GoblinTrashmaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goreclaw, Terror of Qal Sisma", "186p", Rarity.RARE, mage.cards.g.GoreclawTerrorOfQalSisma.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goreclaw, Terror of Qal Sisma", "186s", Rarity.RARE, mage.cards.g.GoreclawTerrorOfQalSisma.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Graveyard Marshal", "99s", Rarity.RARE, mage.cards.g.GraveyardMarshal.class));
        cards.add(new SetCardInfo("Guttersnipe", 145, Rarity.UNCOMMON, mage.cards.g.Guttersnipe.class));
        cards.add(new SetCardInfo("Hungering Hydra", "189s", Rarity.RARE, mage.cards.h.HungeringHydra.class));
        cards.add(new SetCardInfo("Infernal Reckoning", "102s", Rarity.RARE, mage.cards.i.InfernalReckoning.class));
        cards.add(new SetCardInfo("Isareth the Awakener", "104s", Rarity.RARE, mage.cards.i.IsarethTheAwakener.class));
        cards.add(new SetCardInfo("Isolate", "17s", Rarity.RARE, mage.cards.i.Isolate.class));
        cards.add(new SetCardInfo("Lathliss, Dragon Queen", "149s", Rarity.RARE, mage.cards.l.LathlissDragonQueen.class));
        cards.add(new SetCardInfo("Lena, Selfless Champion", "21s", Rarity.RARE, mage.cards.l.LenaSelflessChampion.class));
        cards.add(new SetCardInfo("Leonin Warleader", "23p", Rarity.RARE, mage.cards.l.LeoninWarleader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Leonin Warleader", "23s", Rarity.RARE, mage.cards.l.LeoninWarleader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana's Contract", "107s", Rarity.RARE, mage.cards.l.LilianasContract.class));
        cards.add(new SetCardInfo("Liliana, Untouched by Death", "106s", Rarity.MYTHIC, mage.cards.l.LilianaUntouchedByDeath.class));
        cards.add(new SetCardInfo("Magistrate's Scepter", "238s", Rarity.RARE, mage.cards.m.MagistratesScepter.class));
        cards.add(new SetCardInfo("Mentor of the Meek", "27s", Rarity.RARE, mage.cards.m.MentorOfTheMeek.class));
        cards.add(new SetCardInfo("Metamorphic Alteration", "60s", Rarity.RARE, mage.cards.m.MetamorphicAlteration.class));
        cards.add(new SetCardInfo("Militia Bugler", 29, Rarity.UNCOMMON, mage.cards.m.MilitiaBugler.class));
        cards.add(new SetCardInfo("Mistcaller", "62s", Rarity.RARE, mage.cards.m.Mistcaller.class));
        cards.add(new SetCardInfo("Murder", 110, Rarity.UNCOMMON, mage.cards.m.Murder.class));
        cards.add(new SetCardInfo("Mystic Archaeologist", "63s", Rarity.RARE, mage.cards.m.MysticArchaeologist.class));
        cards.add(new SetCardInfo("Nicol Bolas, the Arisen", "218s", Rarity.MYTHIC, mage.cards.n.NicolBolasTheArisen.class));
        cards.add(new SetCardInfo("Nicol Bolas, the Ravager", "218s", Rarity.MYTHIC, mage.cards.n.NicolBolasTheRavager.class));
        cards.add(new SetCardInfo("Omniscience", "65s", Rarity.MYTHIC, mage.cards.o.Omniscience.class));
        cards.add(new SetCardInfo("One with the Machine", "66s", Rarity.RARE, mage.cards.o.OneWithTheMachine.class));
        cards.add(new SetCardInfo("Open the Graves", "112s", Rarity.RARE, mage.cards.o.OpenTheGraves.class));
        cards.add(new SetCardInfo("Palladia-Mors, the Ruiner", "219s", Rarity.MYTHIC, mage.cards.p.PalladiaMorsTheRuiner.class));
        cards.add(new SetCardInfo("Patient Rebuilding", "67s", Rarity.RARE, mage.cards.p.PatientRebuilding.class));
        cards.add(new SetCardInfo("Pelakka Wurm", "192s", Rarity.RARE, mage.cards.p.PelakkaWurm.class));
        cards.add(new SetCardInfo("Phylactery Lich", "113s", Rarity.RARE, mage.cards.p.PhylacteryLich.class));
        cards.add(new SetCardInfo("Prodigious Growth", "194s", Rarity.RARE, mage.cards.p.ProdigiousGrowth.class));
        cards.add(new SetCardInfo("Reliquary Tower", 254, Rarity.UNCOMMON, mage.cards.r.ReliquaryTower.class));
        cards.add(new SetCardInfo("Remorseful Cleric", "33p", Rarity.RARE, mage.cards.r.RemorsefulCleric.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Remorseful Cleric", "33s", Rarity.RARE, mage.cards.r.RemorsefulCleric.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Resplendent Angel", "34p", Rarity.MYTHIC, mage.cards.r.ResplendentAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Resplendent Angel", "34s", Rarity.MYTHIC, mage.cards.r.ResplendentAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Runic Armasaur", "200s", Rarity.RARE, mage.cards.r.RunicArmasaur.class));
        cards.add(new SetCardInfo("Sai, Master Thopterist", "69p", Rarity.RARE, mage.cards.s.SaiMasterThopterist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sai, Master Thopterist", "69s", Rarity.RARE, mage.cards.s.SaiMasterThopterist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sarkhan's Unsealing", "155s", Rarity.RARE, mage.cards.s.SarkhansUnsealing.class));
        cards.add(new SetCardInfo("Sarkhan, Fireblood", "154s", Rarity.MYTHIC, mage.cards.s.SarkhanFireblood.class));
        cards.add(new SetCardInfo("Scapeshift", "201s", Rarity.MYTHIC, mage.cards.s.Scapeshift.class));
        cards.add(new SetCardInfo("Sigiled Sword of Valeron", "244s", Rarity.RARE, mage.cards.s.SigiledSwordOfValeron.class));
        cards.add(new SetCardInfo("Spit Flame", "160s", Rarity.RARE, mage.cards.s.SpitFlame.class));
        cards.add(new SetCardInfo("Suncleanser", "39s", Rarity.RARE, mage.cards.s.Suncleanser.class));
        cards.add(new SetCardInfo("Supreme Phantom", "76p", Rarity.RARE, mage.cards.s.SupremePhantom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Supreme Phantom", "76s", Rarity.RARE, mage.cards.s.SupremePhantom.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tezzeret, Artifice Master", "79s", Rarity.MYTHIC, mage.cards.t.TezzeretArtificeMaster.class));
        cards.add(new SetCardInfo("Thorn Lieutenant", "203p", Rarity.RARE, mage.cards.t.ThornLieutenant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thorn Lieutenant", "203s", Rarity.RARE, mage.cards.t.ThornLieutenant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Transmogrifying Wand", "247s", Rarity.RARE, mage.cards.t.TransmogrifyingWand.class));
        cards.add(new SetCardInfo("Vaevictis Asmadi, the Dire", "225s", Rarity.MYTHIC, mage.cards.v.VaevictisAsmadiTheDire.class));
        cards.add(new SetCardInfo("Valiant Knight", "42s", Rarity.RARE, mage.cards.v.ValiantKnight.class));
        cards.add(new SetCardInfo("Vivien Reid", "208p", Rarity.MYTHIC, mage.cards.v.VivienReid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vivien Reid", "208s", Rarity.MYTHIC, mage.cards.v.VivienReid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vivien's Invocation", "209s", Rarity.RARE, mage.cards.v.ViviensInvocation.class));
        cards.add(new SetCardInfo("Windreader Sphinx", "84s", Rarity.RARE, mage.cards.w.WindreaderSphinx.class));
     }
}
