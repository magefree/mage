/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets;

import java.util.GregorianCalendar;

import mage.cards.ExpansionSet;
import mage.constants.SetType;
import mage.constants.Rarity;
import java.util.List;
import mage.ObjectColor;
import mage.cards.CardGraphicInfo;
import mage.cards.FrameStyle;

/**
 *
 * @author North
 */
public class Antiquities extends ExpansionSet {

    private static final Antiquities fINSTANCE = new Antiquities();

    public static Antiquities getInstance() {
        return fINSTANCE;
    }

    private Antiquities() {
        super("Antiquities", "ATQ", "mage.sets.antiquities", new GregorianCalendar(1994, 2, 1).getTime(), SetType.EXPANSION);
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Amulet of Kroog", 1, Rarity.COMMON, mage.cards.a.AmuletOfKroog.class));
        cards.add(new SetCardInfo("Argivian Archaeologist", 94, Rarity.RARE, mage.cards.a.ArgivianArchaeologist.class));
        cards.add(new SetCardInfo("Argivian Blacksmith", 95, Rarity.COMMON, mage.cards.a.ArgivianBlacksmith.class));
        cards.add(new SetCardInfo("Argothian Pixies", 59, Rarity.COMMON, mage.cards.a.ArgothianPixies.class));
        cards.add(new SetCardInfo("Argothian Treefolk", 60, Rarity.COMMON, mage.cards.a.ArgothianTreefolk.class));
        cards.add(new SetCardInfo("Armageddon Clock", 2, Rarity.UNCOMMON, mage.cards.a.ArmageddonClock.class));
        cards.add(new SetCardInfo("Artifact Blast", 87, Rarity.COMMON, mage.cards.a.ArtifactBlast.class));
        cards.add(new SetCardInfo("Artifact Possession", 45, Rarity.COMMON, mage.cards.a.ArtifactPossession.class));
        cards.add(new SetCardInfo("Artifact Ward", 96, Rarity.COMMON, mage.cards.a.ArtifactWard.class));
        cards.add(new SetCardInfo("Ashnod's Altar", 3, Rarity.UNCOMMON, mage.cards.a.AshnodsAltar.class));
        cards.add(new SetCardInfo("Ashnod's Battle Gear", 4, Rarity.UNCOMMON, mage.cards.a.AshnodsBattleGear.class));
        cards.add(new SetCardInfo("Ashnod's Transmogrant", 5, Rarity.UNCOMMON, mage.cards.a.AshnodsTransmogrant.class));
        cards.add(new SetCardInfo("Atog", 88, Rarity.COMMON, mage.cards.a.Atog.class));
        cards.add(new SetCardInfo("Candelabra of Tawnos", 8, Rarity.RARE, mage.cards.c.CandelabraOfTawnos.class));
        cards.add(new SetCardInfo("Circle of Protection: Artifacts", 97, Rarity.COMMON, mage.cards.c.CircleOfProtectionArtifacts.class));
        cards.add(new SetCardInfo("Citanul Druid", 61, Rarity.UNCOMMON, mage.cards.c.CitanulDruid.class));
        cards.add(new SetCardInfo("Clay Statue", 9, Rarity.COMMON, mage.cards.c.ClayStatue.class));
        cards.add(new SetCardInfo("Clockwork Avian", 10, Rarity.RARE, mage.cards.c.ClockworkAvian.class));
        cards.add(new SetCardInfo("Colossus of Sardia", 11, Rarity.RARE, mage.cards.c.ColossusOfSardia.class));
        cards.add(new SetCardInfo("Coral Helm", 12, Rarity.RARE, mage.cards.c.CoralHelm.class));
        cards.add(new SetCardInfo("Crumble", 62, Rarity.COMMON, mage.cards.c.Crumble.class));
        cards.add(new SetCardInfo("Cursed Rack", 13, Rarity.UNCOMMON, mage.cards.c.CursedRack.class));
        cards.add(new SetCardInfo("Damping Field", 98, Rarity.UNCOMMON, mage.cards.d.DampingField.class));
        cards.add(new SetCardInfo("Detonate", 89, Rarity.UNCOMMON, mage.cards.d.Detonate.class));
        cards.add(new SetCardInfo("Drafna's Restoration", 52, Rarity.COMMON, mage.cards.d.DrafnasRestoration.class));
        cards.add(new SetCardInfo("Dragon Engine", 14, Rarity.COMMON, mage.cards.d.DragonEngine.class));
        cards.add(new SetCardInfo("Dwarven Weaponsmith", 90, Rarity.UNCOMMON, mage.cards.d.DwarvenWeaponsmith.class));
        cards.add(new SetCardInfo("Energy Flux", 53, Rarity.UNCOMMON, mage.cards.e.EnergyFlux.class));
        cards.add(new SetCardInfo("Feldon's Cane", 15, Rarity.UNCOMMON, mage.cards.f.FeldonsCane.class));
        cards.add(new SetCardInfo("Gaea's Avenger", 63, Rarity.RARE, mage.cards.g.GaeasAvenger.class));
        cards.add(new SetCardInfo("Gate to Phyrexia", 46, Rarity.UNCOMMON, mage.cards.g.GateToPhyrexia.class));
        cards.add(new SetCardInfo("Goblin Artisans", 91, Rarity.UNCOMMON, mage.cards.g.GoblinArtisans.class));
        cards.add(new SetCardInfo("Golgothian Sylex", 16, Rarity.RARE, mage.cards.g.GolgothianSylex.class));
        cards.add(new SetCardInfo("Grapeshot Catapult", 17, Rarity.COMMON, mage.cards.g.GrapeshotCatapult.class));
        cards.add(new SetCardInfo("Haunting Wind", 47, Rarity.UNCOMMON, mage.cards.h.HauntingWind.class));
        cards.add(new SetCardInfo("Hurkyl's Recall", 54, Rarity.RARE, mage.cards.h.HurkylsRecall.class));
        cards.add(new SetCardInfo("Ivory Tower", 18, Rarity.UNCOMMON, mage.cards.i.IvoryTower.class));
        cards.add(new SetCardInfo("Jalum Tome", 19, Rarity.UNCOMMON, mage.cards.j.JalumTome.class));
        cards.add(new SetCardInfo("Martyrs of Korlis", 99, Rarity.UNCOMMON, mage.cards.m.MartyrsOfKorlis.class));
        cards.add(new SetCardInfo("Mightstone", 20, Rarity.UNCOMMON, mage.cards.m.Mightstone.class));
        cards.add(new SetCardInfo("Millstone", 21, Rarity.UNCOMMON, mage.cards.m.Millstone.class));
        cards.add(new SetCardInfo("Mishra's Factory", 66, Rarity.UNCOMMON, mage.cards.m.MishrasFactory.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mishra's Factory", 67, Rarity.UNCOMMON, mage.cards.m.MishrasFactory.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mishra's Factory", 68, Rarity.UNCOMMON, mage.cards.m.MishrasFactory.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mishra's Factory", 69, Rarity.UNCOMMON, mage.cards.m.MishrasFactory.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mishra's Workshop", 70, Rarity.RARE, mage.cards.m.MishrasWorkshop.class));
        cards.add(new SetCardInfo("Obelisk of Undoing", 23, Rarity.RARE, mage.cards.o.ObeliskOfUndoing.class));
        cards.add(new SetCardInfo("Onulet", 24, Rarity.UNCOMMON, mage.cards.o.Onulet.class));
        cards.add(new SetCardInfo("Orcish Mechanics", 92, Rarity.COMMON, mage.cards.o.OrcishMechanics.class));
        cards.add(new SetCardInfo("Ornithopter", 25, Rarity.COMMON, mage.cards.o.Ornithopter.class));
        cards.add(new SetCardInfo("Phyrexian Gremlins", 48, Rarity.COMMON, mage.cards.p.PhyrexianGremlins.class));
        cards.add(new SetCardInfo("Power Artifact", 55, Rarity.UNCOMMON, mage.cards.p.PowerArtifact.class));
        cards.add(new SetCardInfo("Powerleech", 64, Rarity.UNCOMMON, mage.cards.p.Powerleech.class));
        cards.add(new SetCardInfo("Priest of Yawgmoth", 49, Rarity.COMMON, mage.cards.p.PriestOfYawgmoth.class));
        cards.add(new SetCardInfo("Primal Clay", 26, Rarity.UNCOMMON, mage.cards.p.PrimalClay.class));
        cards.add(new SetCardInfo("Rakalite", 27, Rarity.UNCOMMON, mage.cards.r.Rakalite.class));
        cards.add(new SetCardInfo("Reconstruction", 56, Rarity.COMMON, mage.cards.r.Reconstruction.class));
        cards.add(new SetCardInfo("Rocket Launcher", 28, Rarity.UNCOMMON, mage.cards.r.RocketLauncher.class));
        cards.add(new SetCardInfo("Sage of Lat-Nam", 57, Rarity.COMMON, mage.cards.s.SageOfLatNam.class));
        cards.add(new SetCardInfo("Shapeshifter", 29, Rarity.RARE, mage.cards.s.Shapeshifter.class));
        cards.add(new SetCardInfo("Shatterstorm", 93, Rarity.RARE, mage.cards.s.Shatterstorm.class));
        cards.add(new SetCardInfo("Staff of Zegon", 30, Rarity.COMMON, mage.cards.s.StaffOfZegon.class));
        cards.add(new SetCardInfo("Strip Mine", 71, Rarity.UNCOMMON, mage.cards.s.StripMine.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Strip Mine", 72, Rarity.UNCOMMON, mage.cards.s.StripMine.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Strip Mine", 73, Rarity.UNCOMMON, mage.cards.s.StripMine.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Strip Mine", 74, Rarity.UNCOMMON, mage.cards.s.StripMine.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Su-Chi", 31, Rarity.UNCOMMON, mage.cards.s.SuChi.class));
        cards.add(new SetCardInfo("Tablet of Epityr", 32, Rarity.COMMON, mage.cards.t.TabletOfEpityr.class));
        cards.add(new SetCardInfo("Tawnos's Coffin", 33, Rarity.RARE, mage.cards.t.TawnossCoffin.class));
        cards.add(new SetCardInfo("Tawnos's Wand", 34, Rarity.UNCOMMON, mage.cards.t.TawnossWand.class));
        cards.add(new SetCardInfo("Tawnos's Weaponry", 35, Rarity.UNCOMMON, mage.cards.t.TawnossWeaponry.class));
        cards.add(new SetCardInfo("Tetravus", 36, Rarity.RARE, mage.cards.t.Tetravus.class));
        cards.add(new SetCardInfo("The Rack", 37, Rarity.UNCOMMON, mage.cards.t.TheRack.class));
        cards.add(new SetCardInfo("Titania's Song", 65, Rarity.UNCOMMON, mage.cards.t.TitaniasSong.class));
        cards.add(new SetCardInfo("Transmute Artifact", 58, Rarity.UNCOMMON, mage.cards.t.TransmuteArtifact.class));
        cards.add(new SetCardInfo("Triskelion", 38, Rarity.RARE, mage.cards.t.Triskelion.class));
        cards.add(new SetCardInfo("Urza's Chalice", 40, Rarity.COMMON, mage.cards.u.UrzasChalice.class));
        cards.add(new SetCardInfo("Urza's Mine", 75, Rarity.UNCOMMON, mage.cards.u.UrzasMine.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Urza's Mine", 76, Rarity.UNCOMMON, mage.cards.u.UrzasMine.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Urza's Mine", 77, Rarity.UNCOMMON, mage.cards.u.UrzasMine.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Urza's Mine", 78, Rarity.UNCOMMON, mage.cards.u.UrzasMine.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Urza's Miter", 41, Rarity.RARE, mage.cards.u.UrzasMiter.class));
        cards.add(new SetCardInfo("Urza's Power Plant", 79, Rarity.UNCOMMON, mage.cards.u.UrzasPowerPlant.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Urza's Power Plant", 80, Rarity.UNCOMMON, mage.cards.u.UrzasPowerPlant.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Urza's Power Plant", 81, Rarity.UNCOMMON, mage.cards.u.UrzasPowerPlant.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Urza's Power Plant", 82, Rarity.UNCOMMON, mage.cards.u.UrzasPowerPlant.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Urza's Tower", 83, Rarity.UNCOMMON, mage.cards.u.UrzasTower.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Urza's Tower", 84, Rarity.UNCOMMON, mage.cards.u.UrzasTower.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Urza's Tower", 85, Rarity.UNCOMMON, mage.cards.u.UrzasTower.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Urza's Tower", 86, Rarity.UNCOMMON, mage.cards.u.UrzasTower.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Wall of Spears", 42, Rarity.UNCOMMON, mage.cards.w.WallOfSpears.class));
        cards.add(new SetCardInfo("Weakstone", 43, Rarity.UNCOMMON, mage.cards.w.Weakstone.class));
        cards.add(new SetCardInfo("Xenic Poltergeist", 50, Rarity.UNCOMMON, mage.cards.x.XenicPoltergeist.class));
        cards.add(new SetCardInfo("Yawgmoth Demon", 51, Rarity.RARE, mage.cards.y.YawgmothDemon.class));
        cards.add(new SetCardInfo("Yotian Soldier", 44, Rarity.COMMON, mage.cards.y.YotianSoldier.class));
    }
}
