/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.sets;

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author LevelX2
 */

public class KnightsVsDragons extends ExpansionSet {
    private static final KnightsVsDragons instance = new KnightsVsDragons();

    public static KnightsVsDragons getInstance() {
        return instance;
    }

    private KnightsVsDragons() {
        super("Duel Decks: Knights vs. Dragons", "DDG", ExpansionSet.buildDate(2011, 4, 1), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Alaborn Cavalier", 18, Rarity.UNCOMMON, mage.cards.a.AlabornCavalier.class));
        cards.add(new SetCardInfo("Armillary Sphere", 62, Rarity.COMMON, mage.cards.a.ArmillarySphere.class));
        cards.add(new SetCardInfo("Benalish Lancer", 12, Rarity.COMMON, mage.cards.b.BenalishLancer.class));
        cards.add(new SetCardInfo("Bloodmark Mentor", 50, Rarity.UNCOMMON, mage.cards.b.BloodmarkMentor.class));
        cards.add(new SetCardInfo("Bogardan Hellkite", 47, Rarity.MYTHIC, mage.cards.b.BogardanHellkite.class));
        cards.add(new SetCardInfo("Bogardan Rager", 57, Rarity.COMMON, mage.cards.b.BogardanRager.class));
        cards.add(new SetCardInfo("Breath of Darigaaz", 64, Rarity.UNCOMMON, mage.cards.b.BreathOfDarigaaz.class));
        cards.add(new SetCardInfo("Captive Flame", 68, Rarity.UNCOMMON, mage.cards.c.CaptiveFlame.class));
        cards.add(new SetCardInfo("Caravan Escort", 2, Rarity.COMMON, mage.cards.c.CaravanEscort.class));
        cards.add(new SetCardInfo("Cinder Wall", 48, Rarity.COMMON, mage.cards.c.CinderWall.class));
        cards.add(new SetCardInfo("Claws of Valakut", 72, Rarity.COMMON, mage.cards.c.ClawsOfValakut.class));
        cards.add(new SetCardInfo("Cone of Flame", 75, Rarity.UNCOMMON, mage.cards.c.ConeOfFlame.class));
        cards.add(new SetCardInfo("Dragon Fodder", 65, Rarity.COMMON, mage.cards.d.DragonFodder.class));
        cards.add(new SetCardInfo("Dragon's Claw", 63, Rarity.UNCOMMON, mage.cards.d.DragonsClaw.class));
        cards.add(new SetCardInfo("Dragonspeaker Shaman", 53, Rarity.UNCOMMON, mage.cards.d.DragonspeakerShaman.class));
        cards.add(new SetCardInfo("Dragon Whelp", 54, Rarity.UNCOMMON, mage.cards.d.DragonWhelp.class));
        cards.add(new SetCardInfo("Edge of Autumn", 25, Rarity.COMMON, mage.cards.e.EdgeOfAutumn.class));
        cards.add(new SetCardInfo("Fiery Fall", 76, Rarity.COMMON, mage.cards.f.FieryFall.class));
        cards.add(new SetCardInfo("Fire-Belly Changeling", 51, Rarity.COMMON, mage.cards.f.FireBellyChangeling.class));
        cards.add(new SetCardInfo("Forest", 43, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 44, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 45, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 46, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Ghostfire", 69, Rarity.COMMON, mage.cards.g.Ghostfire.class));
        cards.add(new SetCardInfo("Grasslands", 35, Rarity.UNCOMMON, mage.cards.g.Grasslands.class));
        cards.add(new SetCardInfo("Griffin Guide", 33, Rarity.UNCOMMON, mage.cards.g.GriffinGuide.class));
        cards.add(new SetCardInfo("Harm's Way", 23, Rarity.UNCOMMON, mage.cards.h.HarmsWay.class));
        cards.add(new SetCardInfo("Henge Guardian", 55, Rarity.UNCOMMON, mage.cards.h.HengeGuardian.class));
        cards.add(new SetCardInfo("Heroes' Reunion", 29, Rarity.UNCOMMON, mage.cards.h.HeroesReunion.class));
        cards.add(new SetCardInfo("Jaws of Stone", 77, Rarity.UNCOMMON, mage.cards.j.JawsOfStone.class));
        cards.add(new SetCardInfo("Juniper Order Ranger", 21, Rarity.UNCOMMON, mage.cards.j.JuniperOrderRanger.class));
        cards.add(new SetCardInfo("Kabira Vindicator", 16, Rarity.UNCOMMON, mage.cards.k.KabiraVindicator.class));
        cards.add(new SetCardInfo("Kilnmouth Dragon", 59, Rarity.RARE, mage.cards.k.KilnmouthDragon.class));
        cards.add(new SetCardInfo("Kinsbaile Cavalier", 17, Rarity.RARE, mage.cards.k.KinsbaileCavalier.class));
        cards.add(new SetCardInfo("Knight Exemplar", 14, Rarity.RARE, mage.cards.k.KnightExemplar.class));
        cards.add(new SetCardInfo("Knight of Cliffhaven", 4, Rarity.COMMON, mage.cards.k.KnightOfCliffhaven.class));
        cards.add(new SetCardInfo("Knight of Meadowgrain", 5, Rarity.UNCOMMON, mage.cards.k.KnightOfMeadowgrain.class));
        cards.add(new SetCardInfo("Knight of the Reliquary", 1, Rarity.MYTHIC, mage.cards.k.KnightOfTheReliquary.class));
        cards.add(new SetCardInfo("Knight of the White Orchid", 6, Rarity.RARE, mage.cards.k.KnightOfTheWhiteOrchid.class));
        cards.add(new SetCardInfo("Knotvine Paladin", 10, Rarity.RARE, mage.cards.k.KnotvinePaladin.class));
        cards.add(new SetCardInfo("Leonin Skyhunter", 7, Rarity.UNCOMMON, mage.cards.l.LeoninSkyhunter.class));
        cards.add(new SetCardInfo("Lionheart Maverick", 3, Rarity.COMMON, mage.cards.l.LionheartMaverick.class));
        cards.add(new SetCardInfo("Loxodon Warhammer", 31, Rarity.RARE, mage.cards.l.LoxodonWarhammer.class));
        cards.add(new SetCardInfo("Mighty Leap", 26, Rarity.COMMON, mage.cards.m.MightyLeap.class));
        cards.add(new SetCardInfo("Mordant Dragon", 58, Rarity.RARE, mage.cards.m.MordantDragon.class));
        cards.add(new SetCardInfo("Mountain", 78, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 79, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 80, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 81, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mudbutton Torchrunner", 52, Rarity.COMMON, mage.cards.m.MudbuttonTorchrunner.class));
        cards.add(new SetCardInfo("Oblivion Ring", 34, Rarity.COMMON, mage.cards.o.OblivionRing.class));
        cards.add(new SetCardInfo("Paladin of Prahv", 22, Rarity.UNCOMMON, mage.cards.p.PaladinOfPrahv.class));
        cards.add(new SetCardInfo("Plains", 39, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 40, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 41, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 42, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plover Knights", 20, Rarity.COMMON, mage.cards.p.PloverKnights.class));
        cards.add(new SetCardInfo("Punishing Fire", 66, Rarity.UNCOMMON, mage.cards.p.PunishingFire.class));
        cards.add(new SetCardInfo("Reciprocate", 24, Rarity.UNCOMMON, mage.cards.r.Reciprocate.class));
        cards.add(new SetCardInfo("Reprisal", 27, Rarity.UNCOMMON, mage.cards.r.Reprisal.class));
        cards.add(new SetCardInfo("Seething Song", 70, Rarity.COMMON, mage.cards.s.SeethingSong.class));
        cards.add(new SetCardInfo("Seismic Strike", 71, Rarity.COMMON, mage.cards.s.SeismicStrike.class));
        cards.add(new SetCardInfo("Sejiri Steppe", 36, Rarity.COMMON, mage.cards.s.SejiriSteppe.class));
        cards.add(new SetCardInfo("Selesnya Sanctuary", 37, Rarity.COMMON, mage.cards.s.SelesnyaSanctuary.class));
        cards.add(new SetCardInfo("Shivan Hellkite", 60, Rarity.RARE, mage.cards.s.ShivanHellkite.class));
        cards.add(new SetCardInfo("Shiv's Embrace", 74, Rarity.UNCOMMON, mage.cards.s.ShivsEmbrace.class));
        cards.add(new SetCardInfo("Sigil Blessing", 30, Rarity.COMMON, mage.cards.s.SigilBlessing.class));
        cards.add(new SetCardInfo("Silver Knight", 8, Rarity.UNCOMMON, mage.cards.s.SilverKnight.class));
        cards.add(new SetCardInfo("Skirk Prospector", 49, Rarity.COMMON, mage.cards.s.SkirkProspector.class));
        cards.add(new SetCardInfo("Skyhunter Patrol", 19, Rarity.COMMON, mage.cards.s.SkyhunterPatrol.class));
        cards.add(new SetCardInfo("Spidersilk Armor", 32, Rarity.COMMON, mage.cards.s.SpidersilkArmor.class));
        cards.add(new SetCardInfo("Spitting Earth", 67, Rarity.COMMON, mage.cards.s.SpittingEarth.class));
        cards.add(new SetCardInfo("Steward of Valeron", 11, Rarity.COMMON, mage.cards.s.StewardOfValeron.class));
        cards.add(new SetCardInfo("Temporary Insanity", 73, Rarity.UNCOMMON, mage.cards.t.TemporaryInsanity.class));
        cards.add(new SetCardInfo("Test of Faith", 28, Rarity.UNCOMMON, mage.cards.t.TestOfFaith.class));
        cards.add(new SetCardInfo("Thunder Dragon", 61, Rarity.RARE, mage.cards.t.ThunderDragon.class));
        cards.add(new SetCardInfo("Treetop Village", 38, Rarity.UNCOMMON, mage.cards.t.TreetopVillage.class));
        cards.add(new SetCardInfo("Voracious Dragon", 56, Rarity.RARE, mage.cards.v.VoraciousDragon.class));
        cards.add(new SetCardInfo("White Knight", 9, Rarity.UNCOMMON, mage.cards.w.WhiteKnight.class));
        cards.add(new SetCardInfo("Wilt-Leaf Cavaliers", 15, Rarity.UNCOMMON, mage.cards.w.WiltLeafCavaliers.class));
        cards.add(new SetCardInfo("Zhalfirin Commander", 13, Rarity.UNCOMMON, mage.cards.z.ZhalfirinCommander.class));
    }
}