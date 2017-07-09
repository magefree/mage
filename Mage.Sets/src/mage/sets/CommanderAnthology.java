/*
 * Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
 * @author fireshoes
 */
public class CommanderAnthology extends ExpansionSet {
    private static final CommanderAnthology instance = new CommanderAnthology();

    public static CommanderAnthology getInstance() {
        return instance;
    }

    private CommanderAnthology() {
        super("Commander Anthology", "CMA", ExpansionSet.buildDate(2017, 6, 9), SetType.SUPPLEMENTAL);
        this.blockName = "Commander Anthology";
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Acidic Slime", 90, Rarity.UNCOMMON, mage.cards.a.AcidicSlime.class));
        cards.add(new SetCardInfo("Aerie Mystics", 1, Rarity.UNCOMMON, mage.cards.a.AerieMystics.class));
        cards.add(new SetCardInfo("Aethermage's Touch", 172, Rarity.RARE, mage.cards.a.AethermagesTouch.class));
        cards.add(new SetCardInfo("Akoum Refuge", 238, Rarity.UNCOMMON, mage.cards.a.AkoumRefuge.class));
        cards.add(new SetCardInfo("Akroma's Vengeance", 2, Rarity.RARE, mage.cards.a.AkromasVengeance.class));
        cards.add(new SetCardInfo("Akroma, Angel of Fury", 75, Rarity.RARE, mage.cards.a.AkromaAngelOfFury.class));
        cards.add(new SetCardInfo("Altar's Reap", 45, Rarity.COMMON, mage.cards.a.AltarsReap.class));
        cards.add(new SetCardInfo("Ambition's Cost", 46, Rarity.UNCOMMON, mage.cards.a.AmbitionsCost.class));
        cards.add(new SetCardInfo("Angel of Despair", 173, Rarity.RARE, mage.cards.a.AngelOfDespair.class));
        cards.add(new SetCardInfo("Angel of Finality", 3, Rarity.RARE, mage.cards.a.AngelOfFinality.class));
        cards.add(new SetCardInfo("Angelic Arbiter", 4, Rarity.RARE, mage.cards.a.AngelicArbiter.class));
        cards.add(new SetCardInfo("Anger", 76, Rarity.UNCOMMON, mage.cards.a.Anger.class));
        cards.add(new SetCardInfo("Arcane Denial", 30, Rarity.COMMON, mage.cards.a.ArcaneDenial.class));
        cards.add(new SetCardInfo("Archangel of Strife", 5, Rarity.RARE, mage.cards.a.ArchangelOfStrife.class));
        cards.add(new SetCardInfo("Armillary Sphere", 207, Rarity.COMMON, mage.cards.a.ArmillarySphere.class));
        cards.add(new SetCardInfo("Assault Suit", 208, Rarity.UNCOMMON, mage.cards.a.AssaultSuit.class));
        cards.add(new SetCardInfo("Avatar of Slaughter", 77, Rarity.RARE, mage.cards.a.AvatarOfSlaughter.class));
        cards.add(new SetCardInfo("Azami, Lady of Scrolls", 31, Rarity.RARE, mage.cards.a.AzamiLadyOfScrolls.class));
        cards.add(new SetCardInfo("Azorius Chancery", 239, Rarity.COMMON, mage.cards.a.AzoriusChancery.class));
        cards.add(new SetCardInfo("Azorius Guildgate", 240, Rarity.COMMON, mage.cards.a.AzoriusGuildgate.class));
        cards.add(new SetCardInfo("Azorius Keyrune", 209, Rarity.UNCOMMON, mage.cards.a.AzoriusKeyrune.class));
        cards.add(new SetCardInfo("Bane of Progress", 91, Rarity.RARE, mage.cards.b.BaneOfProgress.class));
        cards.add(new SetCardInfo("Banshee of the Dread Choir", 47, Rarity.UNCOMMON, mage.cards.b.BansheeOfTheDreadChoir.class));
        cards.add(new SetCardInfo("Bant Panorama", 241, Rarity.COMMON, mage.cards.b.BantPanorama.class));
        cards.add(new SetCardInfo("Barren Moor", 242, Rarity.COMMON, mage.cards.b.BarrenMoor.class));
        cards.add(new SetCardInfo("Barter in Blood", 48, Rarity.UNCOMMON, mage.cards.b.BarterInBlood.class));
        cards.add(new SetCardInfo("Basalt Monolith", 210, Rarity.UNCOMMON, mage.cards.b.BasaltMonolith.class));
        cards.add(new SetCardInfo("Basandra, Battle Seraph", 174, Rarity.RARE, mage.cards.b.BasandraBattleSeraph.class));
        cards.add(new SetCardInfo("Bathe in Light", 6, Rarity.UNCOMMON, mage.cards.b.BatheInLight.class));
        cards.add(new SetCardInfo("Beastmaster Ascension", 92, Rarity.RARE, mage.cards.b.BeastmasterAscension.class));
        cards.add(new SetCardInfo("Bladewing the Risen", 175, Rarity.RARE, mage.cards.b.BladewingTheRisen.class));
        cards.add(new SetCardInfo("Blood Bairn", 49, Rarity.COMMON, mage.cards.b.BloodBairn.class));
        cards.add(new SetCardInfo("Bloodspore Thrinax", 93, Rarity.RARE, mage.cards.b.BloodsporeThrinax.class));
        cards.add(new SetCardInfo("Blue Sun's Zenith", 32, Rarity.RARE, mage.cards.b.BlueSunsZenith.class));
        cards.add(new SetCardInfo("Bojuka Bog", 243, Rarity.COMMON, mage.cards.b.BojukaBog.class));
        cards.add(new SetCardInfo("Bonehoard", 211, Rarity.RARE, mage.cards.b.Bonehoard.class));
        cards.add(new SetCardInfo("Boros Garrison", 244, Rarity.COMMON, mage.cards.b.BorosGarrison.class));
        cards.add(new SetCardInfo("Boros Guildmage", 199, Rarity.UNCOMMON, mage.cards.b.BorosGuildmage.class));
        cards.add(new SetCardInfo("Boros Signet", 212, Rarity.COMMON, mage.cards.b.BorosSignet.class));
        cards.add(new SetCardInfo("Borrowing 100,000 Arrows", 33, Rarity.UNCOMMON, mage.cards.b.Borrowing100000Arrows.class));
        cards.add(new SetCardInfo("Butcher of Malakir", 50, Rarity.RARE, mage.cards.b.ButcherOfMalakir.class));
        cards.add(new SetCardInfo("Caller of the Pack", 94, Rarity.UNCOMMON, mage.cards.c.CallerOfThePack.class));
        cards.add(new SetCardInfo("Centaur Vinecrasher", 95, Rarity.RARE, mage.cards.c.CentaurVinecrasher.class));
        cards.add(new SetCardInfo("Champion of Stray Souls", 51, Rarity.MYTHIC, mage.cards.c.ChampionOfStraySouls.class));
        cards.add(new SetCardInfo("Cleansing Beam", 78, Rarity.UNCOMMON, mage.cards.c.CleansingBeam.class));
        cards.add(new SetCardInfo("Cloudthresher", 96, Rarity.RARE, mage.cards.c.Cloudthresher.class));
        cards.add(new SetCardInfo("Collective Unconscious", 97, Rarity.RARE, mage.cards.c.CollectiveUnconscious.class));
        cards.add(new SetCardInfo("Comet Storm", 79, Rarity.MYTHIC, mage.cards.c.CometStorm.class));
        cards.add(new SetCardInfo("Command Tower", 245, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Commander's Sphere", 213, Rarity.COMMON, mage.cards.c.CommandersSphere.class));
        cards.add(new SetCardInfo("Congregate", 7, Rarity.COMMON, mage.cards.c.Congregate.class));
        cards.add(new SetCardInfo("Conjurer's Closet", 214, Rarity.RARE, mage.cards.c.ConjurersCloset.class));
        cards.add(new SetCardInfo("Control Magic", 34, Rarity.UNCOMMON, mage.cards.c.ControlMagic.class));
        cards.add(new SetCardInfo("Corpse Augur", 52, Rarity.UNCOMMON, mage.cards.c.CorpseAugur.class));
        cards.add(new SetCardInfo("Creeperhulk", 98, Rarity.RARE, mage.cards.c.Creeperhulk.class));
        cards.add(new SetCardInfo("Crystal Vein", 246, Rarity.UNCOMMON, mage.cards.c.CrystalVein.class));
        cards.add(new SetCardInfo("Curse of Inertia", 35, Rarity.UNCOMMON, mage.cards.c.CurseOfInertia.class));
        cards.add(new SetCardInfo("Curse of Predation", 99, Rarity.UNCOMMON, mage.cards.c.CurseOfPredation.class));
        cards.add(new SetCardInfo("Curse of the Forsaken", 8, Rarity.UNCOMMON, mage.cards.c.CurseOfTheForsaken.class));
        cards.add(new SetCardInfo("Darksteel Ingot", 215, Rarity.UNCOMMON, mage.cards.d.DarksteelIngot.class));
        cards.add(new SetCardInfo("Darksteel Mutation", 9, Rarity.UNCOMMON, mage.cards.d.DarksteelMutation.class));
        cards.add(new SetCardInfo("Death by Dragons", 80, Rarity.UNCOMMON, mage.cards.d.DeathByDragons.class));
        cards.add(new SetCardInfo("Deceiver Exarch", 36, Rarity.UNCOMMON, mage.cards.d.DeceiverExarch.class));
        cards.add(new SetCardInfo("Derevi, Empyrial Tactician", 176, Rarity.MYTHIC, mage.cards.d.DereviEmpyrialTactician.class));
        cards.add(new SetCardInfo("Desert Twister", 100, Rarity.UNCOMMON, mage.cards.d.DesertTwister.class));
        cards.add(new SetCardInfo("Diabolic Servitude", 53, Rarity.UNCOMMON, mage.cards.d.DiabolicServitude.class));
        cards.add(new SetCardInfo("Diabolic Tutor", 54, Rarity.UNCOMMON, mage.cards.d.DiabolicTutor.class));
        cards.add(new SetCardInfo("Diviner Spirit", 37, Rarity.UNCOMMON, mage.cards.d.DivinerSpirit.class));
        cards.add(new SetCardInfo("Djinn of Infinite Deceits", 38, Rarity.RARE, mage.cards.d.DjinnOfInfiniteDeceits.class));
        cards.add(new SetCardInfo("Dragon Whelp", 81, Rarity.UNCOMMON, mage.cards.d.DragonWhelp.class));
        cards.add(new SetCardInfo("Dread Cacodemon", 55, Rarity.RARE, mage.cards.d.DreadCacodemon.class));
        cards.add(new SetCardInfo("Dread Summons", 56, Rarity.RARE, mage.cards.d.DreadSummons.class));
        cards.add(new SetCardInfo("Drove of Elves", 101, Rarity.UNCOMMON, mage.cards.d.DroveOfElves.class));
        cards.add(new SetCardInfo("Duergar Hedge-Mage", 200, Rarity.UNCOMMON, mage.cards.d.DuergarHedgeMage.class));
        cards.add(new SetCardInfo("Dungeon Geists", 39, Rarity.RARE, mage.cards.d.DungeonGeists.class));
        cards.add(new SetCardInfo("Earthquake", 82, Rarity.RARE, mage.cards.e.Earthquake.class));
        cards.add(new SetCardInfo("Eater of Hope", 57, Rarity.RARE, mage.cards.e.EaterOfHope.class));
        cards.add(new SetCardInfo("Eldrazi Monument", 216, Rarity.MYTHIC, mage.cards.e.EldraziMonument.class));
        cards.add(new SetCardInfo("Elvish Archdruid", 102, Rarity.RARE, mage.cards.e.ElvishArchdruid.class));
        cards.add(new SetCardInfo("Elvish Mystic", 103, Rarity.COMMON, mage.cards.e.ElvishMystic.class));
        cards.add(new SetCardInfo("Elvish Skysweeper", 104, Rarity.COMMON, mage.cards.e.ElvishSkysweeper.class));
        cards.add(new SetCardInfo("Elvish Visionary", 105, Rarity.COMMON, mage.cards.e.ElvishVisionary.class));
        cards.add(new SetCardInfo("Emerald Medallion", 217, Rarity.RARE, mage.cards.e.EmeraldMedallion.class));
        cards.add(new SetCardInfo("Essence Warden", 106, Rarity.COMMON, mage.cards.e.EssenceWarden.class));
        cards.add(new SetCardInfo("Eternal Witness", 107, Rarity.UNCOMMON, mage.cards.e.EternalWitness.class));
        cards.add(new SetCardInfo("Evincar's Justice", 58, Rarity.COMMON, mage.cards.e.EvincarsJustice.class));
        cards.add(new SetCardInfo("Evolving Wilds", 247, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Extractor Demon", 59, Rarity.RARE, mage.cards.e.ExtractorDemon.class));
        cards.add(new SetCardInfo("Ezuri, Renegade Leader", 108, Rarity.RARE, mage.cards.e.EzuriRenegadeLeader.class));
        cards.add(new SetCardInfo("Faerie Conclave", 248, Rarity.UNCOMMON, mage.cards.f.FaerieConclave.class));
        cards.add(new SetCardInfo("Fallen Angel", 60, Rarity.RARE, mage.cards.f.FallenAngel.class));
        cards.add(new SetCardInfo("Farhaven Elf", 109, Rarity.COMMON, mage.cards.f.FarhavenElf.class));
        cards.add(new SetCardInfo("Fiend Hunter", 10, Rarity.UNCOMMON, mage.cards.f.FiendHunter.class));
        cards.add(new SetCardInfo("Flickerform", 11, Rarity.RARE, mage.cards.f.Flickerform.class));
        cards.add(new SetCardInfo("Flickerwisp", 12, Rarity.UNCOMMON, mage.cards.f.Flickerwisp.class));
        cards.add(new SetCardInfo("Forest", 309, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 310, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 311, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 312, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 313, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 314, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 315, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 316, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 317, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 318, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 319, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forest", 320, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Forgotten Cave", 249, Rarity.COMMON, mage.cards.f.ForgottenCave.class));
        cards.add(new SetCardInfo("Fresh Meat", 110, Rarity.RARE, mage.cards.f.FreshMeat.class));
        cards.add(new SetCardInfo("Freyalise, Llanowar's Fury", 111, Rarity.MYTHIC, mage.cards.f.FreyaliseLlanowarsFury.class));
        cards.add(new SetCardInfo("Furnace Whelp", 83, Rarity.UNCOMMON, mage.cards.f.FurnaceWhelp.class));
        cards.add(new SetCardInfo("Gargoyle Castle", 250, Rarity.RARE, mage.cards.g.GargoyleCastle.class));
        cards.add(new SetCardInfo("Ghost Quarter", 251, Rarity.UNCOMMON, mage.cards.g.GhostQuarter.class));
        cards.add(new SetCardInfo("Golgari Charm", 177, Rarity.UNCOMMON, mage.cards.g.GolgariCharm.class));
        cards.add(new SetCardInfo("Golgari Guildgate", 252, Rarity.COMMON, mage.cards.g.GolgariGuildgate.class));
        cards.add(new SetCardInfo("Golgari Rot Farm", 253, Rarity.COMMON, mage.cards.g.GolgariRotFarm.class));
        cards.add(new SetCardInfo("Golgari Signet", 218, Rarity.COMMON, mage.cards.g.GolgariSignet.class));
        cards.add(new SetCardInfo("Grave Sifter", 112, Rarity.RARE, mage.cards.g.GraveSifter.class));
        cards.add(new SetCardInfo("Great Oak Guardian", 113, Rarity.UNCOMMON, mage.cards.g.GreatOakGuardian.class));
        cards.add(new SetCardInfo("Grim Backwoods", 254, Rarity.RARE, mage.cards.g.GrimBackwoods.class));
        cards.add(new SetCardInfo("Grim Flowering", 114, Rarity.UNCOMMON, mage.cards.g.GrimFlowering.class));
        cards.add(new SetCardInfo("Grisly Salvage", 178, Rarity.COMMON, mage.cards.g.GrislySalvage.class));
        cards.add(new SetCardInfo("Gwyllion Hedge-Mage", 201, Rarity.UNCOMMON, mage.cards.g.GwyllionHedgeMage.class));
        cards.add(new SetCardInfo("Hada Spy Patrol", 40, Rarity.UNCOMMON, mage.cards.h.HadaSpyPatrol.class));
        cards.add(new SetCardInfo("Harrow", 115, Rarity.COMMON, mage.cards.h.Harrow.class));
        cards.add(new SetCardInfo("Haunted Fengraf", 255, Rarity.COMMON, mage.cards.h.HauntedFengraf.class));
        cards.add(new SetCardInfo("Havenwood Battleground", 256, Rarity.UNCOMMON, mage.cards.h.HavenwoodBattleground.class));
        cards.add(new SetCardInfo("High Market", 257, Rarity.RARE, mage.cards.h.HighMarket.class));
        cards.add(new SetCardInfo("Hunting Triad", 116, Rarity.UNCOMMON, mage.cards.h.HuntingTriad.class));
        cards.add(new SetCardInfo("Immaculate Magistrate", 117, Rarity.RARE, mage.cards.i.ImmaculateMagistrate.class));
        cards.add(new SetCardInfo("Imperious Perfect", 118, Rarity.RARE, mage.cards.i.ImperiousPerfect.class));
        cards.add(new SetCardInfo("Indrik Stomphowler", 119, Rarity.UNCOMMON, mage.cards.i.IndrikStomphowler.class));
        cards.add(new SetCardInfo("Island", 293, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Island", 294, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Island", 295, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Island", 296, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Jarad, Golgari Lich Lord", 179, Rarity.MYTHIC, mage.cards.j.JaradGolgariLichLord.class));
        cards.add(new SetCardInfo("Joraga Warcaller", 120, Rarity.RARE, mage.cards.j.JoragaWarcaller.class));
        cards.add(new SetCardInfo("Jungle Basin", 258, Rarity.UNCOMMON, mage.cards.j.JungleBasin.class));
        cards.add(new SetCardInfo("Jungle Hollow", 259, Rarity.COMMON, mage.cards.j.JungleHollow.class));
        cards.add(new SetCardInfo("Kaalia of the Vast", 180, Rarity.MYTHIC, mage.cards.k.KaaliaOfTheVast.class));
        cards.add(new SetCardInfo("Karmic Guide", 13, Rarity.RARE, mage.cards.k.KarmicGuide.class));
        cards.add(new SetCardInfo("Kazandu Tuskcaller", 121, Rarity.RARE, mage.cards.k.KazanduTuskcaller.class));
        cards.add(new SetCardInfo("Kessig Cagebreakers", 122, Rarity.RARE, mage.cards.k.KessigCagebreakers.class));
        cards.add(new SetCardInfo("Kirtar's Wrath", 14, Rarity.RARE, mage.cards.k.KirtarsWrath.class));
        cards.add(new SetCardInfo("Korozda Guildmage", 181, Rarity.UNCOMMON, mage.cards.k.KorozdaGuildmage.class));
        cards.add(new SetCardInfo("Krosan Grip", 123, Rarity.UNCOMMON, mage.cards.k.KrosanGrip.class));
        cards.add(new SetCardInfo("Leafdrake Roost", 182, Rarity.UNCOMMON, mage.cards.l.LeafdrakeRoost.class));
        cards.add(new SetCardInfo("Leonin Bladetrap", 219, Rarity.UNCOMMON, mage.cards.l.LeoninBladetrap.class));
        cards.add(new SetCardInfo("Lifeblood Hydra", 124, Rarity.RARE, mage.cards.l.LifebloodHydra.class));
        cards.add(new SetCardInfo("Lightkeeper of Emeria", 15, Rarity.UNCOMMON, mage.cards.l.LightkeeperOfEmeria.class));
        cards.add(new SetCardInfo("Lightning Greaves", 220, Rarity.UNCOMMON, mage.cards.l.LightningGreaves.class));
        cards.add(new SetCardInfo("Llanowar Elves", 125, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Loreseeker's Stone", 221, Rarity.UNCOMMON, mage.cards.l.LoreseekersStone.class));
        cards.add(new SetCardInfo("Lotleth Troll", 183, Rarity.RARE, mage.cards.l.LotlethTroll.class));
        cards.add(new SetCardInfo("Lu Xun, Scholar General", 41, Rarity.RARE, mage.cards.l.LuXunScholarGeneral.class));
        cards.add(new SetCardInfo("Lys Alana Huntmaster", 126, Rarity.COMMON, mage.cards.l.LysAlanaHuntmaster.class));
        cards.add(new SetCardInfo("Malfegor", 184, Rarity.MYTHIC, mage.cards.m.Malfegor.class));
        cards.add(new SetCardInfo("Mana-Charged Dragon", 84, Rarity.RARE, mage.cards.m.ManaChargedDragon.class));
        cards.add(new SetCardInfo("Masked Admirers", 127, Rarity.UNCOMMON, mage.cards.m.MaskedAdmirers.class));
        cards.add(new SetCardInfo("Mazirek, Kraul Death Priest", 185, Rarity.MYTHIC, mage.cards.m.MazirekKraulDeathPriest.class));
        cards.add(new SetCardInfo("Meren of Clan Nel Toth", 186, Rarity.MYTHIC, mage.cards.m.MerenOfClanNelToth.class));
        cards.add(new SetCardInfo("Mirror Entity", 16, Rarity.RARE, mage.cards.m.MirrorEntity.class));
        cards.add(new SetCardInfo("Mistmeadow Witch", 203, Rarity.UNCOMMON, mage.cards.m.MistmeadowWitch.class));
        cards.add(new SetCardInfo("Molten Slagheap", 260, Rarity.UNCOMMON, mage.cards.m.MoltenSlagheap.class));
        cards.add(new SetCardInfo("Mortify", 187, Rarity.UNCOMMON, mage.cards.m.Mortify.class));
        cards.add(new SetCardInfo("Moss Diamond", 222, Rarity.UNCOMMON, mage.cards.m.MossDiamond.class));
        cards.add(new SetCardInfo("Mother of Runes", 17, Rarity.UNCOMMON, mage.cards.m.MotherOfRunes.class));
        cards.add(new SetCardInfo("Mountain", 305, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 306, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 307, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 308, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mulch", 128, Rarity.COMMON, mage.cards.m.Mulch.class));
        cards.add(new SetCardInfo("Murkfiend Liege", 204, Rarity.RARE, mage.cards.m.MurkfiendLiege.class));
        cards.add(new SetCardInfo("Mycoloth", 129, Rarity.RARE, mage.cards.m.Mycoloth.class));
        cards.add(new SetCardInfo("Myriad Landscape", 261, Rarity.UNCOMMON, mage.cards.m.MyriadLandscape.class));
        cards.add(new SetCardInfo("Oni of Wild Places", 85, Rarity.UNCOMMON, mage.cards.o.OniOfWildPlaces.class));
        cards.add(new SetCardInfo("Opal Palace", 262, Rarity.COMMON, mage.cards.o.OpalPalace.class));
        cards.add(new SetCardInfo("Oran-Rief, the Vastwood", 263, Rarity.RARE, mage.cards.o.OranRiefTheVastwood.class));
        cards.add(new SetCardInfo("Orim's Thunder", 18, Rarity.COMMON, mage.cards.o.OrimsThunder.class));
        cards.add(new SetCardInfo("Oros, the Avenger", 188, Rarity.RARE, mage.cards.o.OrosTheAvenger.class));
        cards.add(new SetCardInfo("Orzhov Basilica", 264, Rarity.COMMON, mage.cards.o.OrzhovBasilica.class));
        cards.add(new SetCardInfo("Orzhov Guildmage", 205, Rarity.UNCOMMON, mage.cards.o.OrzhovGuildmage.class));
        cards.add(new SetCardInfo("Orzhov Signet", 223, Rarity.COMMON, mage.cards.o.OrzhovSignet.class));
        cards.add(new SetCardInfo("Overrun", 130, Rarity.UNCOMMON, mage.cards.o.Overrun.class));
        cards.add(new SetCardInfo("Overwhelming Stampede", 131, Rarity.RARE, mage.cards.o.OverwhelmingStampede.class));
        cards.add(new SetCardInfo("Path to Exile", 19, Rarity.UNCOMMON, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Pathbreaker Ibex", 132, Rarity.RARE, mage.cards.p.PathbreakerIbex.class));
        cards.add(new SetCardInfo("Phantom Nantuko", 133, Rarity.RARE, mage.cards.p.PhantomNantuko.class));
        cards.add(new SetCardInfo("Phyrexian Plaguelord", 61, Rarity.RARE, mage.cards.p.PhyrexianPlaguelord.class));
        cards.add(new SetCardInfo("Phyrexian Rager", 62, Rarity.COMMON, mage.cards.p.PhyrexianRager.class));
        cards.add(new SetCardInfo("Pilgrim's Eye", 224, Rarity.COMMON, mage.cards.p.PilgrimsEye.class));
        cards.add(new SetCardInfo("Plains", 285, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 286, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 287, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 288, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 289, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 290, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 291, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Plains", 292, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(null, true)));
         cards.add(new SetCardInfo("Polluted Mire", 265, Rarity.COMMON, mage.cards.p.PollutedMire.class));
        cards.add(new SetCardInfo("Praetor's Counsel", 134, Rarity.MYTHIC, mage.cards.p.PraetorsCounsel.class));
        cards.add(new SetCardInfo("Predator, Flagship", 225, Rarity.RARE, mage.cards.p.PredatorFlagship.class));
        cards.add(new SetCardInfo("Presence of Gond", 135, Rarity.COMMON, mage.cards.p.PresenceOfGond.class));
        cards.add(new SetCardInfo("Priest of Titania", 136, Rarity.COMMON, mage.cards.p.PriestOfTitania.class));
        cards.add(new SetCardInfo("Primal Growth", 137, Rarity.COMMON, mage.cards.p.PrimalGrowth.class));
        cards.add(new SetCardInfo("Primordial Sage", 138, Rarity.RARE, mage.cards.p.PrimordialSage.class));
        cards.add(new SetCardInfo("Putrefy", 189, Rarity.UNCOMMON, mage.cards.p.Putrefy.class));
        cards.add(new SetCardInfo("Pyrohemia", 86, Rarity.UNCOMMON, mage.cards.p.Pyrohemia.class));
        cards.add(new SetCardInfo("Rakdos Carnarium", 266, Rarity.COMMON, mage.cards.r.RakdosCarnarium.class));
        cards.add(new SetCardInfo("Rakdos Signet", 226, Rarity.COMMON, mage.cards.r.RakdosSignet.class));
        cards.add(new SetCardInfo("Rampaging Baloths", 139, Rarity.MYTHIC, mage.cards.r.RampagingBaloths.class));
        cards.add(new SetCardInfo("Razorjaw Oni", 63, Rarity.UNCOMMON, mage.cards.r.RazorjawOni.class));
        cards.add(new SetCardInfo("Reclamation Sage", 140, Rarity.UNCOMMON, mage.cards.r.ReclamationSage.class));
        cards.add(new SetCardInfo("Reiver Demon", 64, Rarity.RARE, mage.cards.r.ReiverDemon.class));
        cards.add(new SetCardInfo("Restore", 141, Rarity.UNCOMMON, mage.cards.r.Restore.class));
        cards.add(new SetCardInfo("Return to Dust", 20, Rarity.UNCOMMON, mage.cards.r.ReturnToDust.class));
        cards.add(new SetCardInfo("Righteous Cause", 21, Rarity.UNCOMMON, mage.cards.r.RighteousCause.class));
        cards.add(new SetCardInfo("Rise from the Grave", 65, Rarity.UNCOMMON, mage.cards.r.RiseFromTheGrave.class));
        cards.add(new SetCardInfo("Roon of the Hidden Realm", 190, Rarity.MYTHIC, mage.cards.r.RoonOfTheHiddenRealm.class));
        cards.add(new SetCardInfo("Rubinia Soulsinger", 191, Rarity.RARE, mage.cards.r.RubiniaSoulsinger.class));
        cards.add(new SetCardInfo("Rupture Spire", 267, Rarity.COMMON, mage.cards.r.RuptureSpire.class));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", 142, Rarity.COMMON, mage.cards.s.SakuraTribeElder.class));
        cards.add(new SetCardInfo("Saltcrusted Steppe", 268, Rarity.UNCOMMON, mage.cards.s.SaltcrustedSteppe.class));
        cards.add(new SetCardInfo("Satyr Wayfinder", 143, Rarity.COMMON, mage.cards.s.SatyrWayfinder.class));
        cards.add(new SetCardInfo("Scourge of Nel Toth", 66, Rarity.RARE, mage.cards.s.ScourgeOfNelToth.class));
        cards.add(new SetCardInfo("Seaside Citadel", 269, Rarity.UNCOMMON, mage.cards.s.SeasideCitadel.class));
        cards.add(new SetCardInfo("Secluded Steppe", 270, Rarity.COMMON, mage.cards.s.SecludedSteppe.class));
        cards.add(new SetCardInfo("Seer's Sundial", 227, Rarity.RARE, mage.cards.s.SeersSundial.class));
        cards.add(new SetCardInfo("Sejiri Refuge", 271, Rarity.UNCOMMON, mage.cards.s.SejiriRefuge.class));
        cards.add(new SetCardInfo("Selesnya Charm", 192, Rarity.UNCOMMON, mage.cards.s.SelesnyaCharm.class));
        cards.add(new SetCardInfo("Selesnya Guildgate", 272, Rarity.COMMON, mage.cards.s.SelesnyaGuildgate.class));
        cards.add(new SetCardInfo("Selesnya Guildmage", 206, Rarity.UNCOMMON, mage.cards.s.SelesnyaGuildmage.class));
        cards.add(new SetCardInfo("Selesnya Sanctuary", 273, Rarity.COMMON, mage.cards.s.SelesnyaSanctuary.class));
        cards.add(new SetCardInfo("Selesnya Signet", 228, Rarity.COMMON, mage.cards.s.SelesnyaSignet.class));
        cards.add(new SetCardInfo("Serra Angel", 22, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Sever the Bloodline", 67, Rarity.RARE, mage.cards.s.SeverTheBloodline.class));
        cards.add(new SetCardInfo("Shattered Angel", 23, Rarity.UNCOMMON, mage.cards.s.ShatteredAngel.class));
        cards.add(new SetCardInfo("Shriekmaw", 68, Rarity.UNCOMMON, mage.cards.s.Shriekmaw.class));
        cards.add(new SetCardInfo("Siege Behemoth", 144, Rarity.RARE, mage.cards.s.SiegeBehemoth.class));
        cards.add(new SetCardInfo("Silklash Spider", 145, Rarity.RARE, mage.cards.s.SilklashSpider.class));
        cards.add(new SetCardInfo("Simic Guildgate", 274, Rarity.COMMON, mage.cards.s.SimicGuildgate.class));
        cards.add(new SetCardInfo("Simic Signet", 229, Rarity.COMMON, mage.cards.s.SimicSignet.class));
        cards.add(new SetCardInfo("Skullclamp", 230, Rarity.UNCOMMON, mage.cards.s.Skullclamp.class));
        cards.add(new SetCardInfo("Skullwinder", 146, Rarity.UNCOMMON, mage.cards.s.Skullwinder.class));
        cards.add(new SetCardInfo("Skyward Eye Prophets", 193, Rarity.UNCOMMON, mage.cards.s.SkywardEyeProphets.class));
        cards.add(new SetCardInfo("Slippery Karst", 275, Rarity.COMMON, mage.cards.s.SlipperyKarst.class));
        cards.add(new SetCardInfo("Sol Ring", 231, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Song of the Dryads", 147, Rarity.RARE, mage.cards.s.SongOfTheDryads.class));
        cards.add(new SetCardInfo("Soul Snare", 24, Rarity.UNCOMMON, mage.cards.s.SoulSnare.class));
        cards.add(new SetCardInfo("Soul of the Harvest", 148, Rarity.RARE, mage.cards.s.SoulOfTheHarvest.class));
        cards.add(new SetCardInfo("Spider Spawning", 149, Rarity.UNCOMMON, mage.cards.s.SpiderSpawning.class));
        cards.add(new SetCardInfo("Stonecloaker", 25, Rarity.UNCOMMON, mage.cards.s.Stonecloaker.class));
        cards.add(new SetCardInfo("Stranglehold", 87, Rarity.RARE, mage.cards.s.Stranglehold.class));
        cards.add(new SetCardInfo("Sulfurous Blast", 88, Rarity.UNCOMMON, mage.cards.s.SulfurousBlast.class));
        cards.add(new SetCardInfo("Surveyor's Scope", 232, Rarity.RARE, mage.cards.s.SurveyorsScope.class));
        cards.add(new SetCardInfo("Swamp", 297, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 298, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 299, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 300, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 301, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 302, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 303, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swamp", 304, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Swiftfoot Boots", 233, Rarity.UNCOMMON, mage.cards.s.SwiftfootBoots.class));
        cards.add(new SetCardInfo("Sword of the Paruns", 234, Rarity.RARE, mage.cards.s.SwordOfTheParuns.class));
        cards.add(new SetCardInfo("Sylvan Offering", 150, Rarity.RARE, mage.cards.s.SylvanOffering.class));
        cards.add(new SetCardInfo("Sylvan Ranger", 151, Rarity.COMMON, mage.cards.s.SylvanRanger.class));
        cards.add(new SetCardInfo("Sylvan Safekeeper", 152, Rarity.RARE, mage.cards.s.SylvanSafekeeper.class));
        cards.add(new SetCardInfo("Syphon Flesh", 69, Rarity.UNCOMMON, mage.cards.s.SyphonFlesh.class));
        cards.add(new SetCardInfo("Syphon Mind", 70, Rarity.COMMON, mage.cards.s.SyphonMind.class));
        cards.add(new SetCardInfo("Tainted Wood", 276, Rarity.UNCOMMON, mage.cards.t.TaintedWood.class));
        cards.add(new SetCardInfo("Tariel, Reckoner of Souls", 194, Rarity.MYTHIC, mage.cards.t.TarielReckonerOfSouls.class));
        cards.add(new SetCardInfo("Temple of the False God", 277, Rarity.UNCOMMON, mage.cards.t.TempleOfTheFalseGod.class));
        cards.add(new SetCardInfo("Tempt with Glory", 26, Rarity.RARE, mage.cards.t.TemptWithGlory.class));
        cards.add(new SetCardInfo("Terastodon", 153, Rarity.RARE, mage.cards.t.Terastodon.class));
        cards.add(new SetCardInfo("Terminate", 195, Rarity.COMMON, mage.cards.t.Terminate.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 278, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("Thief of Blood", 71, Rarity.UNCOMMON, mage.cards.t.ThiefOfBlood.class));
        cards.add(new SetCardInfo("Thornweald Archer", 154, Rarity.COMMON, mage.cards.t.ThornwealdArcher.class));
        cards.add(new SetCardInfo("Thornwind Faeries", 42, Rarity.COMMON, mage.cards.t.ThornwindFaeries.class));
        cards.add(new SetCardInfo("Thought Vessel", 235, Rarity.COMMON, mage.cards.t.ThoughtVessel.class));
        cards.add(new SetCardInfo("Thousand-Year Elixir", 236, Rarity.RARE, mage.cards.t.ThousandYearElixir.class));
        cards.add(new SetCardInfo("Thunderfoot Baloth", 155, Rarity.RARE, mage.cards.t.ThunderfootBaloth.class));
        cards.add(new SetCardInfo("Thunderstaff", 237, Rarity.UNCOMMON, mage.cards.t.Thunderstaff.class));
        cards.add(new SetCardInfo("Timberwatch Elf", 156, Rarity.COMMON, mage.cards.t.TimberwatchElf.class));
        cards.add(new SetCardInfo("Titania's Chosen", 158, Rarity.UNCOMMON, mage.cards.t.TitaniasChosen.class));
        cards.add(new SetCardInfo("Titania, Protector of Argoth", 157, Rarity.MYTHIC, mage.cards.t.TitaniaProtectorOfArgoth.class));
        cards.add(new SetCardInfo("Tornado Elemental", 159, Rarity.RARE, mage.cards.t.TornadoElemental.class));
        cards.add(new SetCardInfo("Tranquil Thicket", 279, Rarity.COMMON, mage.cards.t.TranquilThicket.class));
        cards.add(new SetCardInfo("Transguild Promenade", 280, Rarity.COMMON, mage.cards.t.TransguildPromenade.class));
        cards.add(new SetCardInfo("Tribute to the Wild", 160, Rarity.UNCOMMON, mage.cards.t.TributeToTheWild.class));
        cards.add(new SetCardInfo("Unexpectedly Absent", 27, Rarity.RARE, mage.cards.u.UnexpectedlyAbsent.class));
        cards.add(new SetCardInfo("Verdant Force", 161, Rarity.RARE, mage.cards.v.VerdantForce.class));
        cards.add(new SetCardInfo("Victimize", 72, Rarity.UNCOMMON, mage.cards.v.Victimize.class));
        cards.add(new SetCardInfo("Viridian Emissary", 162, Rarity.COMMON, mage.cards.v.ViridianEmissary.class));
        cards.add(new SetCardInfo("Viridian Zealot", 163, Rarity.RARE, mage.cards.v.ViridianZealot.class));
        cards.add(new SetCardInfo("Vivid Grove", 281, Rarity.UNCOMMON, mage.cards.v.VividGrove.class));
        cards.add(new SetCardInfo("Vivid Marsh", 282, Rarity.UNCOMMON, mage.cards.v.VividMarsh.class));
        cards.add(new SetCardInfo("Vivid Meadow", 283, Rarity.UNCOMMON, mage.cards.v.VividMeadow.class));
        cards.add(new SetCardInfo("Voice of All", 28, Rarity.RARE, mage.cards.v.VoiceOfAll.class));
        cards.add(new SetCardInfo("Vow of Duty", 29, Rarity.UNCOMMON, mage.cards.v.VowOfDuty.class));
        cards.add(new SetCardInfo("Vow of Lightning", 89, Rarity.UNCOMMON, mage.cards.v.VowOfLightning.class));
        cards.add(new SetCardInfo("Vow of Malice", 73, Rarity.UNCOMMON, mage.cards.v.VowOfMalice.class));
        cards.add(new SetCardInfo("Vulturous Zombie", 196, Rarity.RARE, mage.cards.v.VulturousZombie.class));
        cards.add(new SetCardInfo("Wall of Blossoms", 164, Rarity.UNCOMMON, mage.cards.w.WallOfBlossoms.class));
        cards.add(new SetCardInfo("Wash Out", 43, Rarity.UNCOMMON, mage.cards.w.WashOut.class));
        cards.add(new SetCardInfo("Wave of Vitriol", 165, Rarity.RARE, mage.cards.w.WaveOfVitriol.class));
        cards.add(new SetCardInfo("Wellwisher", 166, Rarity.COMMON, mage.cards.w.Wellwisher.class));
        cards.add(new SetCardInfo("Whirlwind", 167, Rarity.RARE, mage.cards.w.Whirlwind.class));
        cards.add(new SetCardInfo("Winged Coatl", 197, Rarity.COMMON, mage.cards.w.WingedCoatl.class));
        cards.add(new SetCardInfo("Wolfbriar Elemental", 168, Rarity.RARE, mage.cards.w.WolfbriarElemental.class));
        cards.add(new SetCardInfo("Wolfcaller's Howl", 169, Rarity.RARE, mage.cards.w.WolfcallersHowl.class));
        cards.add(new SetCardInfo("Wonder", 44, Rarity.UNCOMMON, mage.cards.w.Wonder.class));
        cards.add(new SetCardInfo("Wood Elves", 170, Rarity.COMMON, mage.cards.w.WoodElves.class));
        cards.add(new SetCardInfo("Wrecking Ball", 198, Rarity.COMMON, mage.cards.w.WreckingBall.class));
        cards.add(new SetCardInfo("Wren's Run Packmaster", 171, Rarity.RARE, mage.cards.w.WrensRunPackmaster.class));
        cards.add(new SetCardInfo("Wretched Confluence", 74, Rarity.RARE, mage.cards.w.WretchedConfluence.class));
        cards.add(new SetCardInfo("Zoetic Cavern", 284, Rarity.UNCOMMON, mage.cards.z.ZoeticCavern.class));
    }
}
