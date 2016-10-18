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

import mage.constants.SetType;
import mage.cards.ExpansionSet;
import mage.constants.Rarity;

/**
 *
 * @author fireshoes
 */
public class GameDay extends ExpansionSet {

    private static final GameDay fINSTANCE = new GameDay();

    public static GameDay getInstance() {
        return fINSTANCE;
    }

    private GameDay() {
        super("Game Day", "MGDC", ExpansionSet.buildDate(2011, 6, 17), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Anguished Unmaking", 52, Rarity.RARE, mage.cards.a.AnguishedUnmaking.class));
        cards.add(new SetCardInfo("Black Sun's Zenith", 7, Rarity.RARE, mage.cards.b.BlackSunsZenith.class));
        cards.add(new SetCardInfo("Chief Engineer", 40, Rarity.RARE, mage.cards.c.ChiefEngineer.class));
        cards.add(new SetCardInfo("Conclave Naturalists", 45, Rarity.UNCOMMON, mage.cards.c.ConclaveNaturalists.class));
        cards.add(new SetCardInfo("Cryptborn Horror", 22, Rarity.RARE, mage.cards.c.CryptbornHorror.class));
        cards.add(new SetCardInfo("Cultivator of Blades", 56, Rarity.RARE, mage.cards.c.CultivatorOfBlades.class));
        cards.add(new SetCardInfo("Dictate of Kruphix", 35, Rarity.RARE, mage.cards.d.DictateOfKruphix.class));
        cards.add(new SetCardInfo("Diregraf Ghoul", 12, Rarity.UNCOMMON, mage.cards.d.DiregrafGhoul.class));
        cards.add(new SetCardInfo("Dryad Militant", 23, Rarity.UNCOMMON, mage.cards.d.DryadMilitant.class));
        cards.add(new SetCardInfo("Dungrove Elder", 11, Rarity.RARE, mage.cards.d.DungroveElder.class));
        cards.add(new SetCardInfo("Elite Inquisitor", 13, Rarity.RARE, mage.cards.e.EliteInquisitor.class));
        cards.add(new SetCardInfo("Essence Extraction", 55, Rarity.UNCOMMON, mage.cards.e.EssenceExtraction.class));
        cards.add(new SetCardInfo("Firemane Avenger", 24, Rarity.RARE, mage.cards.f.FiremaneAvenger.class));
        cards.add(new SetCardInfo("Goblin Diplomats", 29, Rarity.RARE, mage.cards.g.GoblinDiplomats.class));
        cards.add(new SetCardInfo("Hall of Triumph", 36, Rarity.RARE, mage.cards.h.HallOfTriumph.class));
        cards.add(new SetCardInfo("Heir of the Wilds", 37, Rarity.UNCOMMON, mage.cards.h.HeirOfTheWilds.class));
        cards.add(new SetCardInfo("Heron's Grace Champion", 54, Rarity.RARE, mage.cards.h.HeronsGraceChampion.class));
        cards.add(new SetCardInfo("Hive Stirrings", 28, Rarity.COMMON, mage.cards.h.HiveStirrings.class));
        cards.add(new SetCardInfo("Immolating Glare", 49, Rarity.UNCOMMON, mage.cards.i.ImmolatingGlare.class));
        cards.add(new SetCardInfo("Incorrigible Youths", 51, Rarity.UNCOMMON, mage.cards.i.IncorrigibleYouths.class));
        cards.add(new SetCardInfo("Jori En, Ruin Diver", 50, Rarity.RARE, mage.cards.j.JoriEnRuinDiver.class));
        cards.add(new SetCardInfo("Killing Wave", 19, Rarity.RARE, mage.cards.k.KillingWave.class));
        cards.add(new SetCardInfo("Kiora's Follower", 33, Rarity.UNCOMMON, mage.cards.k.KiorasFollower.class));
        cards.add(new SetCardInfo("Languish", 46, Rarity.RARE, mage.cards.l.Languish.class));
        cards.add(new SetCardInfo("Latch Seeker", 18, Rarity.UNCOMMON, mage.cards.l.LatchSeeker.class));
        cards.add(new SetCardInfo("Liliana's Specter", 2, Rarity.COMMON, mage.cards.l.LilianasSpecter.class));
        cards.add(new SetCardInfo("Magmaquake", 20, Rarity.RARE, mage.cards.m.Magmaquake.class));
        cards.add(new SetCardInfo("Mardu Shadowspear", 41, Rarity.UNCOMMON, mage.cards.m.MarduShadowspear.class));
        cards.add(new SetCardInfo("Melek, Izzet Paragon", 26, Rarity.RARE, mage.cards.m.MelekIzzetParagon.class));
        cards.add(new SetCardInfo("Memnite", 4, Rarity.UNCOMMON, mage.cards.m.Memnite.class));
        cards.add(new SetCardInfo("Mitotic Slime", 3, Rarity.RARE, mage.cards.m.MitoticSlime.class));
        cards.add(new SetCardInfo("Mwonvuli Beast Tracker", 21, Rarity.UNCOMMON, mage.cards.m.MwonvuliBeastTracker.class));
        cards.add(new SetCardInfo("Myr Superion", 8, Rarity.RARE, mage.cards.m.MyrSuperion.class));
        cards.add(new SetCardInfo("Nighthowler", 31, Rarity.RARE, mage.cards.n.Nighthowler.class));
        cards.add(new SetCardInfo("Pain Seer", 32, Rarity.RARE, mage.cards.p.PainSeer.class));
        cards.add(new SetCardInfo("Phalanx Leader", 30, Rarity.UNCOMMON, mage.cards.p.PhalanxLeader.class));
        cards.add(new SetCardInfo("Priest of Urabrask", 9, Rarity.UNCOMMON, mage.cards.p.PriestOfUrabrask.class));
        cards.add(new SetCardInfo("Pristine Talisman", 17, Rarity.COMMON, mage.cards.p.PristineTalisman.class));
        cards.add(new SetCardInfo("Radiant Flames", 48, Rarity.RARE, mage.cards.r.RadiantFlames.class));
        cards.add(new SetCardInfo("Reclamation Sage", 39, Rarity.UNCOMMON, mage.cards.r.ReclamationSage.class));
        cards.add(new SetCardInfo("Reya Dawnbringer", 1, Rarity.RARE, mage.cards.r.ReyaDawnbringer.class));
        cards.add(new SetCardInfo("Scaleguard Sentinels", 44, Rarity.UNCOMMON, mage.cards.s.ScaleguardSentinels.class));
        cards.add(new SetCardInfo("Squelching Leeches", 34, Rarity.UNCOMMON, mage.cards.s.SquelchingLeeches.class));
        cards.add(new SetCardInfo("Stasis Snare", 47, Rarity.UNCOMMON, mage.cards.s.StasisSnare.class));
        cards.add(new SetCardInfo("Stormblood Berserker", 10, Rarity.UNCOMMON, mage.cards.s.StormbloodBerserker.class));
        cards.add(new SetCardInfo("Strangleroot Geist", 15, Rarity.UNCOMMON, mage.cards.s.StranglerootGeist.class));
        cards.add(new SetCardInfo("Supplant Form", 42, Rarity.RARE, mage.cards.s.SupplantForm.class));
        cards.add(new SetCardInfo("Suture Priest", 16, Rarity.COMMON, mage.cards.s.SuturePriest.class));
        cards.add(new SetCardInfo("Tempered Steel", 5, Rarity.RARE, mage.cards.t.TemperedSteel.class));
        cards.add(new SetCardInfo("Thunderbreak Regent", 43, Rarity.RARE, mage.cards.t.ThunderbreakRegent.class));
        cards.add(new SetCardInfo("Treasure Mage", 6, Rarity.UNCOMMON, mage.cards.t.TreasureMage.class));
        cards.add(new SetCardInfo("Trostani's Summoner", 27, Rarity.UNCOMMON, mage.cards.t.TrostanisSummoner.class));
        cards.add(new SetCardInfo("Unsubstantiate", 53, Rarity.UNCOMMON, mage.cards.u.Unsubstantiate.class));
        cards.add(new SetCardInfo("Utter End", 38, Rarity.RARE, mage.cards.u.UtterEnd.class));
        cards.add(new SetCardInfo("Zameck Guildmage", 25, Rarity.UNCOMMON, mage.cards.z.ZameckGuildmage.class));
        cards.add(new SetCardInfo("Zombie Apocalypse", 14, Rarity.RARE, mage.cards.z.ZombieApocalypse.class));
    }
}
