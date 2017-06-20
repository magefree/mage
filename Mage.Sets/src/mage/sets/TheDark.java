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

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author North
 */
public class TheDark extends ExpansionSet {

    private static final TheDark instance = new TheDark();

    public static TheDark getInstance() {
        return instance;
    }

    private TheDark() {
        super("The Dark", "DRK", ExpansionSet.buildDate(1994, 7, 1), SetType.EXPANSION);
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Amnesia", 19, Rarity.UNCOMMON, mage.cards.a.Amnesia.class));
        cards.add(new SetCardInfo("Angry Mob", 74, Rarity.UNCOMMON, mage.cards.a.AngryMob.class));
        cards.add(new SetCardInfo("Apprentice Wizard", 20, Rarity.RARE, mage.cards.a.ApprenticeWizard.class));
        cards.add(new SetCardInfo("Ashes to Ashes", 1, Rarity.COMMON, mage.cards.a.AshesToAshes.class));
        cards.add(new SetCardInfo("Ball Lightning", 56, Rarity.RARE, mage.cards.b.BallLightning.class));
        cards.add(new SetCardInfo("Barl's Cage", 93, Rarity.RARE, mage.cards.b.BarlsCage.class));
        cards.add(new SetCardInfo("Blood Moon", 57, Rarity.RARE, mage.cards.b.BloodMoon.class));
        cards.add(new SetCardInfo("Bog Imp", 3, Rarity.COMMON, mage.cards.b.BogImp.class));
        cards.add(new SetCardInfo("Bog Rats", 4, Rarity.COMMON, mage.cards.b.BogRats.class));
        cards.add(new SetCardInfo("Bone Flute", 94, Rarity.UNCOMMON, mage.cards.b.BoneFlute.class));
        cards.add(new SetCardInfo("Book of Rass", 95, Rarity.UNCOMMON, mage.cards.b.BookOfRass.class));
        cards.add(new SetCardInfo("Brainwash", 76, Rarity.COMMON, mage.cards.b.Brainwash.class));
        cards.add(new SetCardInfo("Brothers of Fire", 58, Rarity.UNCOMMON, mage.cards.b.BrothersOfFire.class));
        cards.add(new SetCardInfo("Carnivorous Plant", 38, Rarity.COMMON, mage.cards.c.CarnivorousPlant.class));
        cards.add(new SetCardInfo("Cave People", 59, Rarity.UNCOMMON, mage.cards.c.CavePeople.class));
        cards.add(new SetCardInfo("City of Shadows", 113, Rarity.RARE, mage.cards.c.CityOfShadows.class));
        cards.add(new SetCardInfo("Coal Golem", 96, Rarity.UNCOMMON, mage.cards.c.CoalGolem.class));
        cards.add(new SetCardInfo("Dance of Many", 21, Rarity.RARE, mage.cards.d.DanceOfMany.class));
        cards.add(new SetCardInfo("Dark Heart of the Wood", 117, Rarity.COMMON, mage.cards.d.DarkHeartOfTheWood.class));
        cards.add(new SetCardInfo("Diabolic Machine", 98, Rarity.UNCOMMON, mage.cards.d.DiabolicMachine.class));
        cards.add(new SetCardInfo("Drowned", 23, Rarity.COMMON, mage.cards.d.Drowned.class));
        cards.add(new SetCardInfo("Dust to Dust", 78, Rarity.COMMON, mage.cards.d.DustToDust.class));
        cards.add(new SetCardInfo("Eater of the Dead", 6, Rarity.UNCOMMON, mage.cards.e.EaterOfTheDead.class));
        cards.add(new SetCardInfo("Electric Eel", 24, Rarity.UNCOMMON, mage.cards.e.ElectricEel.class));
        cards.add(new SetCardInfo("Elves of Deep Shadow", 39, Rarity.UNCOMMON, mage.cards.e.ElvesOfDeepShadow.class));
        cards.add(new SetCardInfo("Exorcist", 79, Rarity.RARE, mage.cards.e.Exorcist.class));
        cards.add(new SetCardInfo("Fellwar Stone", 99, Rarity.UNCOMMON, mage.cards.f.FellwarStone.class));
        cards.add(new SetCardInfo("Festival", 81, Rarity.COMMON, mage.cards.f.Festival.class));
        cards.add(new SetCardInfo("Fire Drake", 61, Rarity.UNCOMMON, mage.cards.f.FireDrake.class));
        cards.add(new SetCardInfo("Fissure", 62, Rarity.COMMON, mage.cards.f.Fissure.class));
        cards.add(new SetCardInfo("Flood", 26, Rarity.UNCOMMON, mage.cards.f.Flood.class));
        cards.add(new SetCardInfo("Fountain of Youth", 100, Rarity.UNCOMMON, mage.cards.f.FountainOfYouth.class));
        cards.add(new SetCardInfo("Gaea's Touch", 40, Rarity.COMMON, mage.cards.g.GaeasTouch.class));
        cards.add(new SetCardInfo("Ghost Ship", 27, Rarity.COMMON, mage.cards.g.GhostShip.class));
        cards.add(new SetCardInfo("Goblin Caves", 63, Rarity.COMMON, mage.cards.g.GoblinCaves.class));
        cards.add(new SetCardInfo("Goblin Digging Team", 64, Rarity.COMMON, mage.cards.g.GoblinDiggingTeam.class));
        cards.add(new SetCardInfo("Goblin Hero", 65, Rarity.COMMON, mage.cards.g.GoblinHero.class));
        cards.add(new SetCardInfo("Goblins of the Flarg", 69, Rarity.COMMON, mage.cards.g.GoblinsOfTheFlarg.class));
        cards.add(new SetCardInfo("Goblin Wizard", 68, Rarity.RARE, mage.cards.g.GoblinWizard.class));
        cards.add(new SetCardInfo("Grave Robbers", 8, Rarity.RARE, mage.cards.g.GraveRobbers.class));
        cards.add(new SetCardInfo("Hidden Path", 41, Rarity.RARE, mage.cards.h.HiddenPath.class));
        cards.add(new SetCardInfo("Holy Light", 83, Rarity.COMMON, mage.cards.h.HolyLight.class));
        cards.add(new SetCardInfo("Inferno", 70, Rarity.RARE, mage.cards.i.Inferno.class));
        cards.add(new SetCardInfo("Land Leeches", 42, Rarity.COMMON, mage.cards.l.LandLeeches.class));
        cards.add(new SetCardInfo("Living Armor", 101, Rarity.UNCOMMON, mage.cards.l.LivingArmor.class));
        cards.add(new SetCardInfo("Mana Clash", 71, Rarity.RARE, mage.cards.m.ManaClash.class));
        cards.add(new SetCardInfo("Mana Vortex", 30, Rarity.RARE, mage.cards.m.ManaVortex.class));
        cards.add(new SetCardInfo("Marsh Gas", 10, Rarity.COMMON, mage.cards.m.MarshGas.class));
        cards.add(new SetCardInfo("Marsh Goblins", 118, Rarity.COMMON, mage.cards.m.MarshGoblins.class));
        cards.add(new SetCardInfo("Marsh Viper", 44, Rarity.COMMON, mage.cards.m.MarshViper.class));
        cards.add(new SetCardInfo("Maze of Ith", 114, Rarity.UNCOMMON, mage.cards.m.MazeOfIth.class));
        cards.add(new SetCardInfo("Merfolk Assassin", 31, Rarity.UNCOMMON, mage.cards.m.MerfolkAssassin.class));
        cards.add(new SetCardInfo("Morale", 87, Rarity.COMMON, mage.cards.m.Morale.class));
        cards.add(new SetCardInfo("Murk Dwellers", 11, Rarity.COMMON, mage.cards.m.MurkDwellers.class));
        cards.add(new SetCardInfo("Niall Silvain", 45, Rarity.RARE, mage.cards.n.NiallSilvain.class));
        cards.add(new SetCardInfo("Orc General", 72, Rarity.UNCOMMON, mage.cards.o.OrcGeneral.class));
        cards.add(new SetCardInfo("People of the Woods", 46, Rarity.UNCOMMON, mage.cards.p.PeopleOfTheWoods.class));
        cards.add(new SetCardInfo("Preacher", 89, Rarity.RARE, mage.cards.p.Preacher.class));
        cards.add(new SetCardInfo("Rag Man", 13, Rarity.RARE, mage.cards.r.RagMan.class));
        cards.add(new SetCardInfo("Riptide", 34, Rarity.COMMON, mage.cards.r.Riptide.class));
        cards.add(new SetCardInfo("Safe Haven", 115, Rarity.RARE, mage.cards.s.SafeHaven.class));
        cards.add(new SetCardInfo("Savaen Elves", 47, Rarity.COMMON, mage.cards.s.SavaenElves.class));
        cards.add(new SetCardInfo("Scarwood Goblins", 119, Rarity.COMMON, mage.cards.s.ScarwoodGoblins.class));
        cards.add(new SetCardInfo("Scarwood Hag", 49, Rarity.UNCOMMON, mage.cards.s.ScarwoodHag.class));
        cards.add(new SetCardInfo("Scavenger Folk", 50, Rarity.COMMON, mage.cards.s.ScavengerFolk.class));
        cards.add(new SetCardInfo("Sisters of the Flame", 73, Rarity.UNCOMMON, mage.cards.s.SistersOfTheFlame.class));
        cards.add(new SetCardInfo("Skull of Orm", 106, Rarity.UNCOMMON, mage.cards.s.SkullOfOrm.class));
        cards.add(new SetCardInfo("Squire", 90, Rarity.COMMON, mage.cards.s.Squire.class));
        cards.add(new SetCardInfo("Standing Stones", 107, Rarity.UNCOMMON, mage.cards.s.StandingStones.class));
        cards.add(new SetCardInfo("Stone Calendar", 108, Rarity.RARE, mage.cards.s.StoneCalendar.class));
        cards.add(new SetCardInfo("Sunken City", 35, Rarity.COMMON, mage.cards.s.SunkenCity.class));
        cards.add(new SetCardInfo("Tivadar's Crusade", 91, Rarity.UNCOMMON, mage.cards.t.TivadarsCrusade.class));
        cards.add(new SetCardInfo("Tormod's Crypt", 109, Rarity.UNCOMMON, mage.cards.t.TormodsCrypt.class));
        cards.add(new SetCardInfo("Tracker", 52, Rarity.RARE, mage.cards.t.Tracker.class));
        cards.add(new SetCardInfo("Uncle Istvan", 16, Rarity.UNCOMMON, mage.cards.u.UncleIstvan.class));
        cards.add(new SetCardInfo("Venom", 53, Rarity.COMMON, mage.cards.v.Venom.class));
        cards.add(new SetCardInfo("Water Wurm", 37, Rarity.COMMON, mage.cards.w.WaterWurm.class));
        cards.add(new SetCardInfo("Witch Hunter", 92, Rarity.RARE, mage.cards.w.WitchHunter.class));
        cards.add(new SetCardInfo("Word of Binding", 17, Rarity.COMMON, mage.cards.w.WordOfBinding.class));
        cards.add(new SetCardInfo("Wormwood Treefolk", 55, Rarity.RARE, mage.cards.w.WormwoodTreefolk.class));
    }
}
