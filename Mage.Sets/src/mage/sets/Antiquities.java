package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author North
 */
public final class Antiquities extends ExpansionSet {

    private static final Antiquities instance = new Antiquities();

    public static Antiquities getInstance() {
        return instance;
    }

    private Antiquities() {
        super("Antiquities", "ATQ", ExpansionSet.buildDate(1994, 2, 1), SetType.EXPANSION);
        this.hasBasicLands = false;
        this.hasBoosters = true; // note: paper boosters had only 8 cards
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;

        cards.add(new SetCardInfo("Amulet of Kroog", 36, Rarity.COMMON, mage.cards.a.AmuletOfKroog.class));
        cards.add(new SetCardInfo("Argivian Archaeologist", 1, Rarity.RARE, mage.cards.a.ArgivianArchaeologist.class));
        cards.add(new SetCardInfo("Argivian Blacksmith", 2, Rarity.COMMON, mage.cards.a.ArgivianBlacksmith.class));
        cards.add(new SetCardInfo("Argothian Pixies", 29, Rarity.COMMON, mage.cards.a.ArgothianPixies.class));
        cards.add(new SetCardInfo("Argothian Treefolk", 30, Rarity.COMMON, mage.cards.a.ArgothianTreefolk.class));
        cards.add(new SetCardInfo("Armageddon Clock", 37, Rarity.UNCOMMON, mage.cards.a.ArmageddonClock.class));
        cards.add(new SetCardInfo("Artifact Blast", 22, Rarity.COMMON, mage.cards.a.ArtifactBlast.class));
        cards.add(new SetCardInfo("Artifact Possession", 15, Rarity.COMMON, mage.cards.a.ArtifactPossession.class));
        cards.add(new SetCardInfo("Artifact Ward", 3, Rarity.COMMON, mage.cards.a.ArtifactWard.class));
        cards.add(new SetCardInfo("Ashnod's Altar", 38, Rarity.UNCOMMON, mage.cards.a.AshnodsAltar.class));
        cards.add(new SetCardInfo("Ashnod's Battle Gear", 39, Rarity.UNCOMMON, mage.cards.a.AshnodsBattleGear.class));
        cards.add(new SetCardInfo("Ashnod's Transmogrant", 40, Rarity.UNCOMMON, mage.cards.a.AshnodsTransmogrant.class));
        cards.add(new SetCardInfo("Atog", 23, Rarity.COMMON, mage.cards.a.Atog.class));
        cards.add(new SetCardInfo("Battering Ram", 41, Rarity.COMMON, mage.cards.b.BatteringRam.class));
        cards.add(new SetCardInfo("Candelabra of Tawnos", 43, Rarity.RARE, mage.cards.c.CandelabraOfTawnos.class));
        cards.add(new SetCardInfo("Circle of Protection: Artifacts", 4, Rarity.UNCOMMON, mage.cards.c.CircleOfProtectionArtifacts.class));
        cards.add(new SetCardInfo("Citanul Druid", 31, Rarity.UNCOMMON, mage.cards.c.CitanulDruid.class));
        cards.add(new SetCardInfo("Clay Statue", 44, Rarity.COMMON, mage.cards.c.ClayStatue.class));
        cards.add(new SetCardInfo("Clockwork Avian", 45, Rarity.RARE, mage.cards.c.ClockworkAvian.class));
        cards.add(new SetCardInfo("Colossus of Sardia", 46, Rarity.RARE, mage.cards.c.ColossusOfSardia.class));
        cards.add(new SetCardInfo("Coral Helm", 47, Rarity.RARE, mage.cards.c.CoralHelm.class));
        cards.add(new SetCardInfo("Crumble", 32, Rarity.COMMON, mage.cards.c.Crumble.class));
        cards.add(new SetCardInfo("Cursed Rack", 48, Rarity.UNCOMMON, mage.cards.c.CursedRack.class));
        cards.add(new SetCardInfo("Damping Field", 5, Rarity.UNCOMMON, mage.cards.d.DampingField.class));
        cards.add(new SetCardInfo("Detonate", 24, Rarity.UNCOMMON, mage.cards.d.Detonate.class));
        cards.add(new SetCardInfo("Drafna's Restoration", 8, Rarity.COMMON, mage.cards.d.DrafnasRestoration.class));
        cards.add(new SetCardInfo("Dragon Engine", 49, Rarity.COMMON, mage.cards.d.DragonEngine.class));
        cards.add(new SetCardInfo("Dwarven Weaponsmith", 25, Rarity.UNCOMMON, mage.cards.d.DwarvenWeaponsmith.class));
        cards.add(new SetCardInfo("Energy Flux", 9, Rarity.UNCOMMON, mage.cards.e.EnergyFlux.class));
        cards.add(new SetCardInfo("Feldon's Cane", 50, Rarity.UNCOMMON, mage.cards.f.FeldonsCane.class));
        cards.add(new SetCardInfo("Gaea's Avenger", 33, Rarity.RARE, mage.cards.g.GaeasAvenger.class));
        cards.add(new SetCardInfo("Gate to Phyrexia", 16, Rarity.UNCOMMON, mage.cards.g.GateToPhyrexia.class));
        cards.add(new SetCardInfo("Goblin Artisans", 26, Rarity.UNCOMMON, mage.cards.g.GoblinArtisans.class));
        cards.add(new SetCardInfo("Golgothian Sylex", 51, Rarity.RARE, mage.cards.g.GolgothianSylex.class));
        cards.add(new SetCardInfo("Grapeshot Catapult", 52, Rarity.COMMON, mage.cards.g.GrapeshotCatapult.class));
        cards.add(new SetCardInfo("Haunting Wind", 17, Rarity.UNCOMMON, mage.cards.h.HauntingWind.class));
        cards.add(new SetCardInfo("Hurkyl's Recall", 10, Rarity.RARE, mage.cards.h.HurkylsRecall.class));
        cards.add(new SetCardInfo("Ivory Tower", 53, Rarity.UNCOMMON, mage.cards.i.IvoryTower.class));
        cards.add(new SetCardInfo("Jalum Tome", 54, Rarity.UNCOMMON, mage.cards.j.JalumTome.class));
        cards.add(new SetCardInfo("Martyrs of Korlis", 6, Rarity.UNCOMMON, mage.cards.m.MartyrsOfKorlis.class));
        cards.add(new SetCardInfo("Mightstone", 55, Rarity.UNCOMMON, mage.cards.m.Mightstone.class));
        cards.add(new SetCardInfo("Millstone", 56, Rarity.UNCOMMON, mage.cards.m.Millstone.class));
        cards.add(new SetCardInfo("Mishra's Factory", "80a", Rarity.UNCOMMON, mage.cards.m.MishrasFactory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra's Factory", "80b", Rarity.RARE, mage.cards.m.MishrasFactory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra's Factory", "80c", Rarity.RARE, mage.cards.m.MishrasFactory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra's Factory", "80d", Rarity.RARE, mage.cards.m.MishrasFactory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra's War Machine", 57, Rarity.RARE, mage.cards.m.MishrasWarMachine.class));
        cards.add(new SetCardInfo("Mishra's Workshop", 81, Rarity.RARE, mage.cards.m.MishrasWorkshop.class));
        cards.add(new SetCardInfo("Obelisk of Undoing", 58, Rarity.RARE, mage.cards.o.ObeliskOfUndoing.class));
        cards.add(new SetCardInfo("Onulet", 59, Rarity.UNCOMMON, mage.cards.o.Onulet.class));
        cards.add(new SetCardInfo("Orcish Mechanics", 27, Rarity.COMMON, mage.cards.o.OrcishMechanics.class));
        cards.add(new SetCardInfo("Ornithopter", 60, Rarity.COMMON, mage.cards.o.Ornithopter.class));
        cards.add(new SetCardInfo("Phyrexian Gremlins", 18, Rarity.COMMON, mage.cards.p.PhyrexianGremlins.class));
        cards.add(new SetCardInfo("Power Artifact", 11, Rarity.UNCOMMON, mage.cards.p.PowerArtifact.class));
        cards.add(new SetCardInfo("Powerleech", 34, Rarity.UNCOMMON, mage.cards.p.Powerleech.class));
        cards.add(new SetCardInfo("Priest of Yawgmoth", 19, Rarity.COMMON, mage.cards.p.PriestOfYawgmoth.class));
        cards.add(new SetCardInfo("Primal Clay", 61, Rarity.UNCOMMON, mage.cards.p.PrimalClay.class));
        cards.add(new SetCardInfo("Rakalite", 62, Rarity.UNCOMMON, mage.cards.r.Rakalite.class));
        cards.add(new SetCardInfo("Reconstruction", 12, Rarity.COMMON, mage.cards.r.Reconstruction.class));
        cards.add(new SetCardInfo("Reverse Polarity", 7, Rarity.COMMON, mage.cards.r.ReversePolarity.class));
        cards.add(new SetCardInfo("Rocket Launcher", 63, Rarity.UNCOMMON, mage.cards.r.RocketLauncher.class));
        cards.add(new SetCardInfo("Sage of Lat-Nam", 13, Rarity.COMMON, mage.cards.s.SageOfLatNam.class));
        cards.add(new SetCardInfo("Shapeshifter", 64, Rarity.RARE, mage.cards.s.Shapeshifter.class));
        cards.add(new SetCardInfo("Shatterstorm", 28, Rarity.RARE, mage.cards.s.Shatterstorm.class));
        cards.add(new SetCardInfo("Staff of Zegon", 65, Rarity.COMMON, mage.cards.s.StaffOfZegon.class));
        cards.add(new SetCardInfo("Strip Mine", "82a", Rarity.UNCOMMON, mage.cards.s.StripMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Strip Mine", "82b", Rarity.RARE, mage.cards.s.StripMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Strip Mine", "82c", Rarity.RARE, mage.cards.s.StripMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Strip Mine", "82d", Rarity.RARE, mage.cards.s.StripMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Su-Chi", 66, Rarity.UNCOMMON, mage.cards.s.SuChi.class));
        cards.add(new SetCardInfo("Tablet of Epityr", 67, Rarity.COMMON, mage.cards.t.TabletOfEpityr.class));
        cards.add(new SetCardInfo("Tawnos's Coffin", 68, Rarity.RARE, mage.cards.t.TawnossCoffin.class));
        cards.add(new SetCardInfo("Tawnos's Wand", 69, Rarity.UNCOMMON, mage.cards.t.TawnossWand.class));
        cards.add(new SetCardInfo("Tawnos's Weaponry", "70+", Rarity.UNCOMMON, mage.cards.t.TawnossWeaponry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tawnos's Weaponry", 70, Rarity.UNCOMMON, mage.cards.t.TawnossWeaponry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tetravus", 71, Rarity.RARE, mage.cards.t.Tetravus.class));
        cards.add(new SetCardInfo("The Rack", 72, Rarity.UNCOMMON, mage.cards.t.TheRack.class));
        cards.add(new SetCardInfo("Titania's Song", 35, Rarity.UNCOMMON, mage.cards.t.TitaniasSong.class));
        cards.add(new SetCardInfo("Transmute Artifact", 14, Rarity.UNCOMMON, mage.cards.t.TransmuteArtifact.class));
        cards.add(new SetCardInfo("Triskelion", 73, Rarity.RARE, mage.cards.t.Triskelion.class));
        cards.add(new SetCardInfo("Urza's Avenger", 74, Rarity.RARE, mage.cards.u.UrzasAvenger.class));
        cards.add(new SetCardInfo("Urza's Chalice", 75, Rarity.COMMON, mage.cards.u.UrzasChalice.class));
        cards.add(new SetCardInfo("Urza's Mine", "83a", Rarity.UNCOMMON, mage.cards.u.UrzasMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Mine", "83b", Rarity.UNCOMMON, mage.cards.u.UrzasMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Mine", "83c", Rarity.COMMON, mage.cards.u.UrzasMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Mine", "83d", Rarity.COMMON, mage.cards.u.UrzasMine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Miter", 76, Rarity.RARE, mage.cards.u.UrzasMiter.class));
        cards.add(new SetCardInfo("Urza's Power Plant", "84a", Rarity.COMMON, mage.cards.u.UrzasPowerPlant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Power Plant", "84b", Rarity.UNCOMMON, mage.cards.u.UrzasPowerPlant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Power Plant", "84c", Rarity.COMMON, mage.cards.u.UrzasPowerPlant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Power Plant", "84d", Rarity.UNCOMMON, mage.cards.u.UrzasPowerPlant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Tower", "85a", Rarity.COMMON, mage.cards.u.UrzasTower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Tower", "85b", Rarity.UNCOMMON, mage.cards.u.UrzasTower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Tower", "85c", Rarity.UNCOMMON, mage.cards.u.UrzasTower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Tower", "85d", Rarity.UNCOMMON, mage.cards.u.UrzasTower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wall of Spears", 77, Rarity.UNCOMMON, mage.cards.w.WallOfSpears.class));
        cards.add(new SetCardInfo("Weakstone", 78, Rarity.UNCOMMON, mage.cards.w.Weakstone.class));
        cards.add(new SetCardInfo("Xenic Poltergeist", 20, Rarity.UNCOMMON, mage.cards.x.XenicPoltergeist.class));
        cards.add(new SetCardInfo("Yawgmoth Demon", 21, Rarity.RARE, mage.cards.y.YawgmothDemon.class));
        cards.add(new SetCardInfo("Yotian Soldier", 79, Rarity.COMMON, mage.cards.y.YotianSoldier.class));
    }
}
