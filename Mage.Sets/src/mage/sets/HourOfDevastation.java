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
import mage.cards.FrameStyle;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fireshoes
 */
public class HourOfDevastation extends ExpansionSet {

    private static final HourOfDevastation instance = new HourOfDevastation();

    public static HourOfDevastation getInstance() {
        return instance;
    }

    protected final List<CardInfo> savedSpecialLand = new ArrayList<>();

    private HourOfDevastation() {
        super("Hour of Devastation", "HOU", ExpansionSet.buildDate(2017, 7, 14), SetType.EXPANSION);
        this.blockName = "Amonkhet";
        this.parentSet = Amonkhet.getInstance();
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.ratioBoosterSpecialLand = 144;
        this.maxCardNumberInBooster = 199;
      
        cards.add(new SetCardInfo("Abandoned Sarcophagus", 158, Rarity.RARE, mage.cards.a.AbandonedSarcophagus.class));
        cards.add(new SetCardInfo("Abrade", 83, Rarity.UNCOMMON, mage.cards.a.Abrade.class));
        cards.add(new SetCardInfo("Accursed Horde", 56, Rarity.UNCOMMON, mage.cards.a.AccursedHorde.class));
        cards.add(new SetCardInfo("Act of Heroism", 1, Rarity.COMMON, mage.cards.a.ActOfHeroism.class));
        cards.add(new SetCardInfo("Adorned Pouncer", 2, Rarity.RARE, mage.cards.a.AdornedPouncer.class));
        cards.add(new SetCardInfo("Aerial Guide", 29, Rarity.COMMON, mage.cards.a.AerialGuide.class));
        cards.add(new SetCardInfo("Ambuscade", 110, Rarity.COMMON, mage.cards.a.Ambuscade.class));
        cards.add(new SetCardInfo("Ammit Eternal", 57, Rarity.RARE, mage.cards.a.AmmitEternal.class));
        cards.add(new SetCardInfo("Angel of Condemnation", 3, Rarity.RARE, mage.cards.a.AngelOfCondemnation.class));
        cards.add(new SetCardInfo("Angel of the God-Pharaoh", 4, Rarity.UNCOMMON, mage.cards.a.AngelOfTheGodPharaoh.class));
        cards.add(new SetCardInfo("Apocalypse Demon", 58, Rarity.RARE, mage.cards.a.ApocalypseDemon.class));
        cards.add(new SetCardInfo("Appeal // Authority", 152, Rarity.UNCOMMON, mage.cards.a.AppealAuthority.class));
        cards.add(new SetCardInfo("Aven Reedstalker", 30, Rarity.COMMON, mage.cards.a.AvenReedstalker.class));
        cards.add(new SetCardInfo("Aven of Enduring Hope", 5, Rarity.COMMON, mage.cards.a.AvenOfEnduringHope.class));
        cards.add(new SetCardInfo("Avid Reclaimer", 201, Rarity.UNCOMMON, mage.cards.a.AvidReclaimer.class));
        cards.add(new SetCardInfo("Banewhip Punisher", 59, Rarity.UNCOMMON, mage.cards.b.BanewhipPunisher.class));
        cards.add(new SetCardInfo("Beneath The Sands", 111, Rarity.COMMON, mage.cards.b.BeneathTheSands.class));
        cards.add(new SetCardInfo("Bitterbow Sharpshooters", 112, Rarity.COMMON, mage.cards.b.BitterbowSharpshooters.class));
        cards.add(new SetCardInfo("Bloodwater Entity", 138, Rarity.UNCOMMON, mage.cards.b.BloodwaterEntity.class));
        cards.add(new SetCardInfo("Blur of Blades", 84, Rarity.COMMON, mage.cards.b.BlurOfBlades.class));
        cards.add(new SetCardInfo("Bontu's Last Reckoning", 60, Rarity.RARE, mage.cards.b.BontusLastReckoning.class));
        cards.add(new SetCardInfo("Brambleweft Behemoth", 202, Rarity.COMMON, mage.cards.b.BrambleweftBehemoth.class));
        cards.add(new SetCardInfo("Burning-Fist Minotaur", 85, Rarity.UNCOMMON, mage.cards.b.BurningFistMinotaur.class));
        cards.add(new SetCardInfo("Carrion Screecher", 61, Rarity.COMMON, mage.cards.c.CarrionScreecher.class));
        cards.add(new SetCardInfo("Champion of Wits", 31, Rarity.RARE, mage.cards.c.ChampionOfWits.class));
        cards.add(new SetCardInfo("Chandra's Defeat", 86, Rarity.UNCOMMON, mage.cards.c.ChandrasDefeat.class));
        cards.add(new SetCardInfo("Chaos Maw", 87, Rarity.RARE, mage.cards.c.ChaosMaw.class));
        cards.add(new SetCardInfo("Cinder Barrens", 209, Rarity.COMMON, mage.cards.c.CinderBarrens.class));
        cards.add(new SetCardInfo("Claim // Fame", 150, Rarity.UNCOMMON, mage.cards.c.ClaimFame.class));
        cards.add(new SetCardInfo("Consign // Oblivion", 149, Rarity.UNCOMMON, mage.cards.c.ConsignOblivion.class));
        cards.add(new SetCardInfo("Countervailing Winds", 32, Rarity.COMMON, mage.cards.c.CountervailingWinds.class));
        cards.add(new SetCardInfo("Crash Through", 88, Rarity.COMMON, mage.cards.c.CrashThrough.class));
        cards.add(new SetCardInfo("Crested Sunmare", 6, Rarity.MYTHIC, mage.cards.c.CrestedSunmare.class));
        cards.add(new SetCardInfo("Crook of Condemnation", 159, Rarity.UNCOMMON, mage.cards.c.CrookOfCondemnation.class));
        cards.add(new SetCardInfo("Crypt of the Eternals", 169, Rarity.UNCOMMON, mage.cards.c.CryptOfTheEternals.class));
        cards.add(new SetCardInfo("Cunning Survivor", 33, Rarity.COMMON, mage.cards.c.CunningSurvivor.class));
        cards.add(new SetCardInfo("Dagger of the Worthy", 160, Rarity.UNCOMMON, mage.cards.d.DaggerOfTheWorthy.class));
        cards.add(new SetCardInfo("Dauntless Aven", 7, Rarity.COMMON, mage.cards.d.DauntlessAven.class));
        cards.add(new SetCardInfo("Defiant Khenra", 89, Rarity.COMMON, mage.cards.d.DefiantKhenra.class));
        cards.add(new SetCardInfo("Desert of the Fervent", 170, Rarity.COMMON, mage.cards.d.DesertOfTheFervent.class));
        cards.add(new SetCardInfo("Desert of the Glorified", 171, Rarity.COMMON, mage.cards.d.DesertOfTheGlorified.class));
        cards.add(new SetCardInfo("Desert of the Indomitable", 172, Rarity.COMMON, mage.cards.d.DesertOfTheIndomitable.class));
        cards.add(new SetCardInfo("Desert of the Mindful", 173, Rarity.COMMON, mage.cards.d.DesertOfTheMindful.class));
        cards.add(new SetCardInfo("Desert of the True", 174, Rarity.COMMON, mage.cards.d.DesertOfTheTrue.class));
        cards.add(new SetCardInfo("Desert's Hold", 8, Rarity.UNCOMMON, mage.cards.d.DesertsHold.class));
        cards.add(new SetCardInfo("Devotee of Strength", 113, Rarity.UNCOMMON, mage.cards.d.DevoteeOfStrength.class));
        cards.add(new SetCardInfo("Disposal Mummy", 9, Rarity.COMMON, mage.cards.d.DisposalMummy.class));
        cards.add(new SetCardInfo("Djeru's Renunciation", 11, Rarity.COMMON, mage.cards.d.DjerusRenunciation.class));
        cards.add(new SetCardInfo("Djeru, With Eyes Open", 10, Rarity.RARE, mage.cards.d.DjeruWithEyesOpen.class));
        cards.add(new SetCardInfo("Doomfall", 62, Rarity.UNCOMMON, mage.cards.d.Doomfall.class));
        cards.add(new SetCardInfo("Dreamstealer", 63, Rarity.RARE, mage.cards.d.Dreamstealer.class));
        cards.add(new SetCardInfo("Driven // Despair", 157, Rarity.RARE, mage.cards.d.DrivenDespair.class));
        cards.add(new SetCardInfo("Dune Diviner", 114, Rarity.UNCOMMON, mage.cards.d.DuneDiviner.class));
        cards.add(new SetCardInfo("Dunes of the Dead", 175, Rarity.UNCOMMON, mage.cards.d.DunesOfTheDead.class));
        cards.add(new SetCardInfo("Dutiful Servants", 12, Rarity.COMMON, mage.cards.d.DutifulServants.class));
        cards.add(new SetCardInfo("Earthshaker Khenra", 90, Rarity.RARE, mage.cards.e.EarthshakerKhenra.class));
        cards.add(new SetCardInfo("Endless Sands", 176, Rarity.RARE, mage.cards.e.EndlessSands.class));
        cards.add(new SetCardInfo("Eternal of Harsh Truths", 34, Rarity.UNCOMMON, mage.cards.e.EternalOfHarshTruths.class));
        cards.add(new SetCardInfo("Farm // Market", 148, Rarity.UNCOMMON, mage.cards.f.FarmMarket.class));
        cards.add(new SetCardInfo("Feral Prowler", 115, Rarity.COMMON, mage.cards.f.FeralProwler.class));
        cards.add(new SetCardInfo("Fervent Paincaster", 91, Rarity.UNCOMMON, mage.cards.f.FerventPaincaster.class));
        cards.add(new SetCardInfo("Firebrand Archer", 92, Rarity.COMMON, mage.cards.f.FirebrandArcher.class));
        cards.add(new SetCardInfo("Forest", 189, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(FrameStyle.BFZ_FULL_ART_BASIC, true)));
        cards.add(new SetCardInfo("Forest", 198, Rarity.LAND, mage.cards.basiclands.Forest.class));
        cards.add(new SetCardInfo("Forest", 199, Rarity.LAND, mage.cards.basiclands.Forest.class));
        cards.add(new SetCardInfo("Fraying Sanity", 35, Rarity.UNCOMMON, mage.cards.f.FrayingSanity.class));
        cards.add(new SetCardInfo("Frilled Sandwalla", 116, Rarity.COMMON, mage.cards.f.FrilledSandwalla.class));
        cards.add(new SetCardInfo("Frontline Devastator", 93, Rarity.COMMON, mage.cards.f.FrontlineDevastator.class));
        cards.add(new SetCardInfo("Gideon's Defeat", 13, Rarity.UNCOMMON, mage.cards.g.GideonsDefeat.class));
        cards.add(new SetCardInfo("Gift of Strength", 117, Rarity.COMMON, mage.cards.g.GiftOfStrength.class));
        cards.add(new SetCardInfo("Gilded Cerodon", 94, Rarity.COMMON, mage.cards.g.GildedCerodon.class));
        cards.add(new SetCardInfo("God-Pharaoh's Faithful", 14, Rarity.COMMON, mage.cards.g.GodPharaohsFaithful.class));
        cards.add(new SetCardInfo("God-Pharaoh's Gift", 161, Rarity.RARE, mage.cards.g.GodPharaohsGift.class));
        cards.add(new SetCardInfo("Granitic Titan", 95, Rarity.COMMON, mage.cards.g.GraniticTitan.class));
        cards.add(new SetCardInfo("Graven Abomination", 162, Rarity.COMMON, mage.cards.g.GravenAbomination.class));
        cards.add(new SetCardInfo("Grind // Dust", 155, Rarity.RARE, mage.cards.g.GrindDust.class));
        cards.add(new SetCardInfo("Grisly Survivor", 64, Rarity.COMMON, mage.cards.g.GrislySurvivor.class));
        cards.add(new SetCardInfo("Harrier Naga", 118, Rarity.COMMON, mage.cards.h.HarrierNaga.class));
        cards.add(new SetCardInfo("Hashep Oasis", 177, Rarity.UNCOMMON, mage.cards.h.HashepOasis.class));
        cards.add(new SetCardInfo("Hazoret's Undying Fury", 96, Rarity.RARE, mage.cards.h.HazoretsUndyingFury.class));
        cards.add(new SetCardInfo("Hollow One", 163, Rarity.RARE, mage.cards.h.HollowOne.class));
        cards.add(new SetCardInfo("Hope Tender", 119, Rarity.UNCOMMON, mage.cards.h.HopeTender.class));
        cards.add(new SetCardInfo("Hostile Desert", 178, Rarity.RARE, mage.cards.h.HostileDesert.class));
        cards.add(new SetCardInfo("Hour of Devastation", 97, Rarity.RARE, mage.cards.h.HourOfDevastation.class));
        cards.add(new SetCardInfo("Hour of Eternity", 36, Rarity.RARE, mage.cards.h.HourOfEternity.class));
        cards.add(new SetCardInfo("Hour of Glory", 65, Rarity.RARE, mage.cards.h.HourOfGlory.class));
        cards.add(new SetCardInfo("Hour of Promise", 120, Rarity.RARE, mage.cards.h.HourOfPromise.class));
        cards.add(new SetCardInfo("Hour of Revelation", 15, Rarity.RARE, mage.cards.h.HourOfRevelation.class));
        cards.add(new SetCardInfo("Ifnir Deadlands", 179, Rarity.UNCOMMON, mage.cards.i.IfnirDeadlands.class));
        cards.add(new SetCardInfo("Imaginary Threats", 37, Rarity.UNCOMMON, mage.cards.i.ImaginaryThreats.class));
        cards.add(new SetCardInfo("Imminent Doom", 98, Rarity.RARE, mage.cards.i.ImminentDoom.class));
        cards.add(new SetCardInfo("Inferno Jet", 99, Rarity.UNCOMMON, mage.cards.i.InfernoJet.class));
        cards.add(new SetCardInfo("Ipnu Rivulet", 180, Rarity.UNCOMMON, mage.cards.i.IpnuRivulet.class));
        cards.add(new SetCardInfo("Island", 186, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(FrameStyle.BFZ_FULL_ART_BASIC, true)));
        cards.add(new SetCardInfo("Island", 192, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Island", 193, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Jace's Defeat", 38, Rarity.UNCOMMON, mage.cards.j.JacesDefeat.class));
        cards.add(new SetCardInfo("Kefnet's Last Word", 39, Rarity.RARE, mage.cards.k.KefnetsLastWord.class));
        cards.add(new SetCardInfo("Khenra Eternal", 66, Rarity.COMMON, mage.cards.k.KhenraEternal.class));
        cards.add(new SetCardInfo("Khenra Scrapper", 100, Rarity.COMMON, mage.cards.k.KhenraScrapper.class));
        cards.add(new SetCardInfo("Kindled Fury", 101, Rarity.COMMON, mage.cards.k.KindledFury.class));
        cards.add(new SetCardInfo("Leave // Chance", 153, Rarity.RARE, mage.cards.l.LeaveChance.class));
        cards.add(new SetCardInfo("Lethal Sting", 67, Rarity.COMMON, mage.cards.l.LethalSting.class));
        cards.add(new SetCardInfo("Life Goes On", 121, Rarity.COMMON, mage.cards.l.LifeGoesOn.class));
        cards.add(new SetCardInfo("Liliana's Defeat", 68, Rarity.UNCOMMON, mage.cards.l.LilianasDefeat.class));
        cards.add(new SetCardInfo("Lurching Rotbeast", 69, Rarity.COMMON, mage.cards.l.LurchingRotbeast.class));
        cards.add(new SetCardInfo("Magmaroth", 102, Rarity.UNCOMMON, mage.cards.m.Magmaroth.class));
        cards.add(new SetCardInfo("Majestic Myriarch", 122, Rarity.MYTHIC, mage.cards.m.MajesticMyriarch.class));
        cards.add(new SetCardInfo("Manalith", 164, Rarity.COMMON, mage.cards.m.Manalith.class));
        cards.add(new SetCardInfo("Manticore Eternal", 103, Rarity.UNCOMMON, mage.cards.m.ManticoreEternal.class));
        cards.add(new SetCardInfo("Marauding Boneslasher", 70, Rarity.COMMON, mage.cards.m.MaraudingBoneslasher.class));
        cards.add(new SetCardInfo("Merciless Eternal", 71, Rarity.UNCOMMON, mage.cards.m.MercilessEternal.class));
        cards.add(new SetCardInfo("Mirage Mirror", 165, Rarity.RARE, mage.cards.m.MirageMirror.class));
        cards.add(new SetCardInfo("Moaning Wall", 72, Rarity.COMMON, mage.cards.m.MoaningWall.class));
        cards.add(new SetCardInfo("Mountain", 188, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(FrameStyle.BFZ_FULL_ART_BASIC, true)));
        cards.add(new SetCardInfo("Mountain", 196, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Mountain", 197, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Mummy Paramount", 16, Rarity.COMMON, mage.cards.m.MummyParamount.class));
        cards.add(new SetCardInfo("Neheb, the Eternal", 104, Rarity.MYTHIC, mage.cards.n.NehebTheEternal.class));
        cards.add(new SetCardInfo("Nicol Bolas, God-Pharaoh", 140, Rarity.MYTHIC, mage.cards.n.NicolBolasGodPharaoh.class));
        cards.add(new SetCardInfo("Nicol Bolas, the Deceiver", 205, Rarity.MYTHIC, mage.cards.n.NicolBolasTheDeceiver.class));
        cards.add(new SetCardInfo("Nimble Obstructionist", 40, Rarity.RARE, mage.cards.n.NimbleObstructionist.class));
        cards.add(new SetCardInfo("Nissa's Defeat", 123, Rarity.UNCOMMON, mage.cards.n.NissasDefeat.class));
        cards.add(new SetCardInfo("Nissa's Encouragement", 203, Rarity.RARE, mage.cards.n.NissasEncouragement.class));
        cards.add(new SetCardInfo("Nissa, Genesis Mage", 200, Rarity.MYTHIC, mage.cards.n.NissaGenesisMage.class));
        cards.add(new SetCardInfo("Oasis Ritualist", 124, Rarity.COMMON, mage.cards.o.OasisRitualist.class));
        cards.add(new SetCardInfo("Obelisk Spider", 141, Rarity.UNCOMMON, mage.cards.o.ObeliskSpider.class));
        cards.add(new SetCardInfo("Oketra's Avenger", 17, Rarity.COMMON, mage.cards.o.OketrasAvenger.class));
        cards.add(new SetCardInfo("Oketra's Last Mercy", 18, Rarity.RARE, mage.cards.o.OketrasLastMercy.class));
        cards.add(new SetCardInfo("Ominous Sphinx", 41, Rarity.UNCOMMON, mage.cards.o.OminousSphinx.class));
        cards.add(new SetCardInfo("Open Fire", 105, Rarity.COMMON, mage.cards.o.OpenFire.class));
        cards.add(new SetCardInfo("Overcome", 125, Rarity.UNCOMMON, mage.cards.o.Overcome.class));
        cards.add(new SetCardInfo("Overwhelming Splendor", 19, Rarity.MYTHIC, mage.cards.o.OverwhelmingSplendor.class));
        cards.add(new SetCardInfo("Plains", 185, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(FrameStyle.BFZ_FULL_ART_BASIC, true)));
        cards.add(new SetCardInfo("Plains", 190, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Plains", 191, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Pride Sovereign", 126, Rarity.RARE, mage.cards.p.PrideSovereign.class));
        cards.add(new SetCardInfo("Proven Combatant", 42, Rarity.COMMON, mage.cards.p.ProvenCombatant.class));
        cards.add(new SetCardInfo("Puncturing Blow", 106, Rarity.COMMON, mage.cards.p.PuncturingBlow.class));
        cards.add(new SetCardInfo("Quarry Beetle", 127, Rarity.UNCOMMON, mage.cards.q.QuarryBeetle.class));
        cards.add(new SetCardInfo("Rampaging Hippo", 128, Rarity.COMMON, mage.cards.r.RampagingHippo.class));
        cards.add(new SetCardInfo("Ramunap Excavator", 129, Rarity.RARE, mage.cards.r.RamunapExcavator.class));
        cards.add(new SetCardInfo("Ramunap Hydra", 130, Rarity.RARE, mage.cards.r.RamunapHydra.class));
        cards.add(new SetCardInfo("Ramunap Ruins", 181, Rarity.UNCOMMON, mage.cards.r.RamunapRuins.class));
        cards.add(new SetCardInfo("Razaketh's Rite", 74, Rarity.UNCOMMON, mage.cards.r.RazakethsRite.class));
        cards.add(new SetCardInfo("Razaketh, the Foulblooded", 73, Rarity.MYTHIC, mage.cards.r.RazakethTheFoulblooded.class));
        cards.add(new SetCardInfo("Reason // Believe", 154, Rarity.RARE, mage.cards.r.ReasonBelieve.class));
        cards.add(new SetCardInfo("Refuse // Cooperate", 156, Rarity.RARE, mage.cards.r.RefuseCooperate.class));
        cards.add(new SetCardInfo("Resilient Khenra", 131, Rarity.RARE, mage.cards.r.ResilientKhenra.class));
        cards.add(new SetCardInfo("Resolute Survivors", 142, Rarity.UNCOMMON, mage.cards.r.ResoluteSurvivors.class));
        cards.add(new SetCardInfo("Rhonas's Last Stand", 132, Rarity.RARE, mage.cards.r.RhonassLastStand.class));
        cards.add(new SetCardInfo("Rhonas's Stalwart", 133, Rarity.COMMON, mage.cards.r.RhonassStalwart.class));
        cards.add(new SetCardInfo("Riddleform", 43, Rarity.UNCOMMON, mage.cards.r.Riddleform.class));
        cards.add(new SetCardInfo("River Hoopoe", 143, Rarity.UNCOMMON, mage.cards.r.RiverHoopoe.class));
        cards.add(new SetCardInfo("Ruin Rat", 75, Rarity.COMMON, mage.cards.r.RuinRat.class));
        cards.add(new SetCardInfo("Samut, the Tested", 144, Rarity.MYTHIC, mage.cards.s.SamutTheTested.class));
        cards.add(new SetCardInfo("Sand Strangler", 107, Rarity.UNCOMMON, mage.cards.s.SandStrangler.class));
        cards.add(new SetCardInfo("Sandblast", 20, Rarity.COMMON, mage.cards.s.Sandblast.class));
        cards.add(new SetCardInfo("Saving Grace", 21, Rarity.UNCOMMON, mage.cards.s.SavingGrace.class));
        cards.add(new SetCardInfo("Scavenger Grounds", 182, Rarity.RARE, mage.cards.s.ScavengerGrounds.class));
        cards.add(new SetCardInfo("Scrounger of Souls", 76, Rarity.COMMON, mage.cards.s.ScroungerOfSouls.class));
        cards.add(new SetCardInfo("Seer of the Last Tomorrow", 44, Rarity.COMMON, mage.cards.s.SeerOfTheLastTomorrow.class));
        cards.add(new SetCardInfo("Shefet Dunes", 183, Rarity.UNCOMMON, mage.cards.s.ShefetDunes.class));
        cards.add(new SetCardInfo("Sidewinder Naga", 134, Rarity.COMMON, mage.cards.s.SidewinderNaga.class));
        cards.add(new SetCardInfo("Sifter Wurm", 135, Rarity.UNCOMMON, mage.cards.s.SifterWurm.class));
        cards.add(new SetCardInfo("Sinuous Striker", 45, Rarity.UNCOMMON, mage.cards.s.SinuousStriker.class));
        cards.add(new SetCardInfo("Solemnity", 22, Rarity.RARE, mage.cards.s.Solemnity.class));
        cards.add(new SetCardInfo("Solitary Camel", 23, Rarity.COMMON, mage.cards.s.SolitaryCamel.class));
        cards.add(new SetCardInfo("Spellweaver Eternal", 46, Rarity.COMMON, mage.cards.s.SpellweaverEternal.class));
        cards.add(new SetCardInfo("Steadfast Sentinel", 24, Rarity.COMMON, mage.cards.s.SteadfastSentinel.class));
        cards.add(new SetCardInfo("Steward of Solidarity", 25, Rarity.UNCOMMON, mage.cards.s.StewardOfSolidarity.class));
        cards.add(new SetCardInfo("Strategic Planning", 47, Rarity.COMMON, mage.cards.s.StrategicPlanning.class));
        cards.add(new SetCardInfo("Striped Riverwinder", 48, Rarity.COMMON, mage.cards.s.StripedRiverwinder.class));
        cards.add(new SetCardInfo("Struggle // Survive", 151, Rarity.UNCOMMON, mage.cards.s.StruggleSurvive.class));
        cards.add(new SetCardInfo("Sunscourge Champion", 26, Rarity.UNCOMMON, mage.cards.s.SunscourgeChampion.class));
        cards.add(new SetCardInfo("Sunset Pyramid", 166, Rarity.UNCOMMON, mage.cards.s.SunsetPyramid.class));
        cards.add(new SetCardInfo("Supreme Will", 49, Rarity.UNCOMMON, mage.cards.s.SupremeWill.class));
        cards.add(new SetCardInfo("Survivors' Encampment", 184, Rarity.COMMON, mage.cards.s.SurvivorsEncampment.class));
        cards.add(new SetCardInfo("Swamp", 187, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(FrameStyle.BFZ_FULL_ART_BASIC, true)));
        cards.add(new SetCardInfo("Swamp", 194, Rarity.LAND, mage.cards.basiclands.Swamp.class));
        cards.add(new SetCardInfo("Swamp", 195, Rarity.LAND, mage.cards.basiclands.Swamp.class));
        cards.add(new SetCardInfo("Swarm Intelligence", 50, Rarity.RARE, mage.cards.s.SwarmIntelligence.class));
        cards.add(new SetCardInfo("Tenacious Hunter", 136, Rarity.UNCOMMON, mage.cards.t.TenaciousHunter.class));
        cards.add(new SetCardInfo("The Locust God", 139, Rarity.MYTHIC, mage.cards.t.TheLocustGod.class));
        cards.add(new SetCardInfo("The Scarab God", 145, Rarity.MYTHIC, mage.cards.t.TheScarabGod.class));
        cards.add(new SetCardInfo("The Scorpion God", 146, Rarity.MYTHIC, mage.cards.t.TheScorpionGod.class));
        cards.add(new SetCardInfo("Thorned Moloch", 108, Rarity.COMMON, mage.cards.t.ThornedMoloch.class));
        cards.add(new SetCardInfo("Torment of Hailfire", 77, Rarity.RARE, mage.cards.t.TormentOfHailfire.class));
        cards.add(new SetCardInfo("Torment of Scarabs", 78, Rarity.UNCOMMON, mage.cards.t.TormentOfScarabs.class));
        cards.add(new SetCardInfo("Torment of Venom", 79, Rarity.COMMON, mage.cards.t.TormentOfVenom.class));
        cards.add(new SetCardInfo("Tragic Lesson", 51, Rarity.COMMON, mage.cards.t.TragicLesson.class));
        cards.add(new SetCardInfo("Traveler's Amulet", 167, Rarity.COMMON, mage.cards.t.TravelersAmulet.class));
        cards.add(new SetCardInfo("Uncage the Menagerie", 137, Rarity.MYTHIC, mage.cards.u.UncageTheMenagerie.class));
        cards.add(new SetCardInfo("Unconventional Tactics", 27, Rarity.UNCOMMON, mage.cards.u.UnconventionalTactics.class));
        cards.add(new SetCardInfo("Unesh, Criosphinx Sovereign", 52, Rarity.MYTHIC, mage.cards.u.UneshCriosphinxSovereign.class));
        cards.add(new SetCardInfo("Unquenchable Thirst", 53, Rarity.COMMON, mage.cards.u.UnquenchableThirst.class));
        cards.add(new SetCardInfo("Unraveling Mummy", 147, Rarity.UNCOMMON, mage.cards.u.UnravelingMummy.class));
        cards.add(new SetCardInfo("Unsummon", 54, Rarity.COMMON, mage.cards.u.Unsummon.class));
        cards.add(new SetCardInfo("Vile Manifestation", 80, Rarity.UNCOMMON, mage.cards.v.VileManifestation.class));
        cards.add(new SetCardInfo("Visage of Bolas", 208, Rarity.RARE, mage.cards.v.VisageOfBolas.class));
        cards.add(new SetCardInfo("Vizier of the Anointed", 55, Rarity.UNCOMMON, mage.cards.v.VizierOfTheAnointed.class));
        cards.add(new SetCardInfo("Vizier of the True", 28, Rarity.UNCOMMON, mage.cards.v.VizierOfTheTrue.class));
        cards.add(new SetCardInfo("Wall of Forgotten Pharaohs", 168, Rarity.COMMON, mage.cards.w.WallOfForgottenPharaohs.class));
        cards.add(new SetCardInfo("Wasp of the Bitter End", 206, Rarity.UNCOMMON, mage.cards.w.WaspOfTheBitterEnd.class));
        cards.add(new SetCardInfo("Wildfire Eternal", 109, Rarity.RARE, mage.cards.w.WildfireEternal.class));
        cards.add(new SetCardInfo("Without Weakness", 81, Rarity.COMMON, mage.cards.w.WithoutWeakness.class));
        cards.add(new SetCardInfo("Woodland Stream", 204, Rarity.COMMON, mage.cards.w.WoodlandStream.class));
        cards.add(new SetCardInfo("Wretched Camel", 82, Rarity.COMMON, mage.cards.w.WretchedCamel.class));
        cards.add(new SetCardInfo("Zealot of the God-Pharaoh", 207, Rarity.COMMON, mage.cards.z.ZealotOfTheGodPharaoh.class));
    }

    @Override
    public List<CardInfo> getSpecialLand() {
        if (savedSpecialLand.isEmpty()) {
            CardCriteria criteria = new CardCriteria();
            criteria.setCodes("MPS-AKH");
            criteria.minCardNumber(31);
            criteria.maxCardNumber(54);
            savedSpecialLand.addAll(CardRepository.instance.findCards(criteria));
        }

        return new ArrayList<>(savedSpecialLand);
    }
}
