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

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author LevelX2
 */

public class SpeedVsCunning extends ExpansionSet {
    private static final SpeedVsCunning instance = new SpeedVsCunning();

    public static SpeedVsCunning getInstance() {
        return instance;
    }

    private SpeedVsCunning() {
        super("Duel Decks: Speed vs. Cunning", "DDN", ExpansionSet.buildDate(2014, 9, 5), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Act of Treason", 26, Rarity.COMMON, mage.cards.a.ActOfTreason.class));
        cards.add(new SetCardInfo("Aquamorph Entity", 54, Rarity.COMMON, mage.cards.a.AquamorphEntity.class));
        cards.add(new SetCardInfo("Arcanis the Omnipotent", 42, Rarity.MYTHIC, mage.cards.a.ArcanisTheOmnipotent.class));
        cards.add(new SetCardInfo("Arc Trail", 23, Rarity.UNCOMMON, mage.cards.a.ArcTrail.class));
        cards.add(new SetCardInfo("Arrow Volley Trap", 71, Rarity.UNCOMMON, mage.cards.a.ArrowVolleyTrap.class));
        cards.add(new SetCardInfo("Banefire", 31, Rarity.RARE, mage.cards.b.Banefire.class));
        cards.add(new SetCardInfo("Beetleback Chief", 14, Rarity.UNCOMMON, mage.cards.b.BeetlebackChief.class));
        cards.add(new SetCardInfo("Bone Splinters", 22, Rarity.COMMON, mage.cards.b.BoneSplinters.class));
        cards.add(new SetCardInfo("Coral Trickster", 44, Rarity.COMMON, mage.cards.c.CoralTrickster.class));
        cards.add(new SetCardInfo("Dauntless Onslaught", 27, Rarity.UNCOMMON, mage.cards.d.DauntlessOnslaught.class));
        cards.add(new SetCardInfo("Dregscape Zombie", 5, Rarity.COMMON, mage.cards.d.DregscapeZombie.class));
        cards.add(new SetCardInfo("Echo Tracer", 51, Rarity.COMMON, mage.cards.e.EchoTracer.class));
        cards.add(new SetCardInfo("Evolving Wilds", 32, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Faerie Impostor", 43, Rarity.UNCOMMON, mage.cards.f.FaerieImpostor.class));
        cards.add(new SetCardInfo("Faerie Invaders", 57, Rarity.COMMON, mage.cards.f.FaerieInvaders.class));
        cards.add(new SetCardInfo("Fathom Seer", 45, Rarity.COMMON, mage.cards.f.FathomSeer.class));
        cards.add(new SetCardInfo("Fiery Fall", 29, Rarity.COMMON, mage.cards.f.FieryFall.class));
        cards.add(new SetCardInfo("Flame-Kin Zealot", 17, Rarity.UNCOMMON, mage.cards.f.FlameKinZealot.class));
        cards.add(new SetCardInfo("Fleeting Distraction", 60, Rarity.COMMON, mage.cards.f.FleetingDistraction.class));
        cards.add(new SetCardInfo("Fleshbag Marauder", 8, Rarity.UNCOMMON, mage.cards.f.FleshbagMarauder.class));
        cards.add(new SetCardInfo("Frenzied Goblin", 2, Rarity.UNCOMMON, mage.cards.f.FrenziedGoblin.class));
        cards.add(new SetCardInfo("Fury of the Horde", 30, Rarity.RARE, mage.cards.f.FuryOfTheHorde.class));
        cards.add(new SetCardInfo("Ghitu Encampment", 33, Rarity.UNCOMMON, mage.cards.g.GhituEncampment.class));
        cards.add(new SetCardInfo("Goblin Bombardment", 24, Rarity.UNCOMMON, mage.cards.g.GoblinBombardment.class));
        cards.add(new SetCardInfo("Goblin Deathraiders", 6, Rarity.COMMON, mage.cards.g.GoblinDeathraiders.class));
        cards.add(new SetCardInfo("Goblin Warchief", 9, Rarity.UNCOMMON, mage.cards.g.GoblinWarchief.class));
        cards.add(new SetCardInfo("Hellraiser Goblin", 7, Rarity.UNCOMMON, mage.cards.h.HellraiserGoblin.class));
        cards.add(new SetCardInfo("Hell's Thunder", 10, Rarity.RARE, mage.cards.h.HellsThunder.class));
        cards.add(new SetCardInfo("Hold the Line", 66, Rarity.RARE, mage.cards.h.HoldTheLine.class));
        cards.add(new SetCardInfo("Hussar Patrol", 55, Rarity.COMMON, mage.cards.h.HussarPatrol.class));
        cards.add(new SetCardInfo("Impulse", 63, Rarity.COMMON, mage.cards.i.Impulse.class));
        cards.add(new SetCardInfo("Infantry Veteran", 3, Rarity.COMMON, mage.cards.i.InfantryVeteran.class));
        cards.add(new SetCardInfo("Inferno Trap", 67, Rarity.UNCOMMON, mage.cards.i.InfernoTrap.class));
        cards.add(new SetCardInfo("Island", 75, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Island", 76, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Island", 77, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Jeskai Elder", 46, Rarity.UNCOMMON, mage.cards.j.JeskaiElder.class));
        cards.add(new SetCardInfo("Kathari Bomber", 11, Rarity.COMMON, mage.cards.k.KathariBomber.class));
        cards.add(new SetCardInfo("Kor Hookmaster", 52, Rarity.COMMON, mage.cards.k.KorHookmaster.class));
        cards.add(new SetCardInfo("Krenko, Mob Boss", 15, Rarity.RARE, mage.cards.k.KrenkoMobBoss.class));
        cards.add(new SetCardInfo("Krenko's Command", 25, Rarity.COMMON, mage.cards.k.KrenkosCommand.class));
        cards.add(new SetCardInfo("Leonin Snarecaster", 4, Rarity.COMMON, mage.cards.l.LeoninSnarecaster.class));
        cards.add(new SetCardInfo("Lightning Angel", 56, Rarity.RARE, mage.cards.l.LightningAngel.class));
        cards.add(new SetCardInfo("Lightning Helix", 65, Rarity.UNCOMMON, mage.cards.l.LightningHelix.class));
        cards.add(new SetCardInfo("Lone Missionary", 49, Rarity.COMMON, mage.cards.l.LoneMissionary.class));
        cards.add(new SetCardInfo("Mana Leak", 64, Rarity.COMMON, mage.cards.m.ManaLeak.class));
        cards.add(new SetCardInfo("Mardu Heart-Piercer", 13, Rarity.UNCOMMON, mage.cards.m.MarduHeartPiercer.class));
        cards.add(new SetCardInfo("Master Decoy", 50, Rarity.COMMON, mage.cards.m.MasterDecoy.class));
        cards.add(new SetCardInfo("Mountain", 35, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 36, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 37, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 78, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mystic Monastery", 73, Rarity.UNCOMMON, mage.cards.m.MysticMonastery.class));
        cards.add(new SetCardInfo("Nomad Outpost", 34, Rarity.UNCOMMON, mage.cards.n.NomadOutpost.class));
        cards.add(new SetCardInfo("Ogre Battledriver", 16, Rarity.RARE, mage.cards.o.OgreBattledriver.class));
        cards.add(new SetCardInfo("Oni of Wild Places", 19, Rarity.UNCOMMON, mage.cards.o.OniOfWildPlaces.class));
        cards.add(new SetCardInfo("Orcish Cannonade", 28, Rarity.COMMON, mage.cards.o.OrcishCannonade.class));
        cards.add(new SetCardInfo("Plains", 38, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 79, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 80, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 81, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Reckless Abandon", 20, Rarity.COMMON, mage.cards.r.RecklessAbandon.class));
        cards.add(new SetCardInfo("Repeal", 72, Rarity.COMMON, mage.cards.r.Repeal.class));
        cards.add(new SetCardInfo("Scourge Devil", 18, Rarity.UNCOMMON, mage.cards.s.ScourgeDevil.class));
        cards.add(new SetCardInfo("Shambling Remains", 12, Rarity.UNCOMMON, mage.cards.s.ShamblingRemains.class));
        cards.add(new SetCardInfo("Shock", 21, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Sparkmage Apprentice", 48, Rarity.COMMON, mage.cards.s.SparkmageApprentice.class));
        cards.add(new SetCardInfo("Sphinx of Uthuun", 59, Rarity.RARE, mage.cards.s.SphinxOfUthuun.class));
        cards.add(new SetCardInfo("Stave Off", 61, Rarity.COMMON, mage.cards.s.StaveOff.class));
        cards.add(new SetCardInfo("Steam Augury", 68, Rarity.RARE, mage.cards.s.SteamAugury.class));
        cards.add(new SetCardInfo("Stonecloaker", 53, Rarity.UNCOMMON, mage.cards.s.Stonecloaker.class));
        cards.add(new SetCardInfo("Swamp", 39, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 40, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 41, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swift Justice", 62, Rarity.COMMON, mage.cards.s.SwiftJustice.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 74, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("Thousand Winds", 58, Rarity.RARE, mage.cards.t.ThousandWinds.class));
        cards.add(new SetCardInfo("Traumatic Visions", 69, Rarity.COMMON, mage.cards.t.TraumaticVisions.class));
        cards.add(new SetCardInfo("Whiplash Trap", 70, Rarity.COMMON, mage.cards.w.WhiplashTrap.class));
        cards.add(new SetCardInfo("Willbender", 47, Rarity.UNCOMMON, mage.cards.w.Willbender.class));
        cards.add(new SetCardInfo("Zurgo Helmsmasher", 1, Rarity.MYTHIC, mage.cards.z.ZurgoHelmsmasher.class));
    }
}