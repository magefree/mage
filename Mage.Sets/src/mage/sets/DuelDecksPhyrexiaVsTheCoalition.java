
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author LevelX2
 */
public final class DuelDecksPhyrexiaVsTheCoalition extends ExpansionSet {

    private static final DuelDecksPhyrexiaVsTheCoalition instance = new DuelDecksPhyrexiaVsTheCoalition();

    public static DuelDecksPhyrexiaVsTheCoalition getInstance() {
        return instance;
    }

    private DuelDecksPhyrexiaVsTheCoalition() {
        super("Duel Decks: Phyrexia vs. the Coalition", "DDE", ExpansionSet.buildDate(2010, 3, 19),
                SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Allied Strategies", 63, Rarity.UNCOMMON, mage.cards.a.AlliedStrategies.class));
        cards.add(new SetCardInfo("Armadillo Cloak", 58, Rarity.COMMON, mage.cards.a.ArmadilloCloak.class));
        cards.add(new SetCardInfo("Bone Shredder", 5, Rarity.UNCOMMON, mage.cards.b.BoneShredder.class));
        cards.add(new SetCardInfo("Carrion Feeder", 2, Rarity.COMMON, mage.cards.c.CarrionFeeder.class));
        cards.add(new SetCardInfo("Charging Troll", 45, Rarity.UNCOMMON, mage.cards.c.ChargingTroll.class));
        cards.add(new SetCardInfo("Coalition Relic", 54, Rarity.RARE, mage.cards.c.CoalitionRelic.class));
        cards.add(new SetCardInfo("Darigaaz's Charm", 59, Rarity.UNCOMMON, mage.cards.d.DarigaazsCharm.class));
        cards.add(new SetCardInfo("Darigaaz, the Igniter", 47, Rarity.RARE, mage.cards.d.DarigaazTheIgniter.class));
        cards.add(new SetCardInfo("Dark Ritual", 18, Rarity.COMMON, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Elfhame Palace", 64, Rarity.UNCOMMON, mage.cards.e.ElfhamePalace.class));
        cards.add(new SetCardInfo("Evasive Action", 50, Rarity.UNCOMMON, mage.cards.e.EvasiveAction.class));
        cards.add(new SetCardInfo("Exotic Curse", 56, Rarity.COMMON, mage.cards.e.ExoticCurse.class));
        cards.add(new SetCardInfo("Fertile Ground", 52, Rarity.COMMON, mage.cards.f.FertileGround.class));
        cards.add(new SetCardInfo("Forest", 70, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 71, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gerrard Capashen", 46, Rarity.RARE, mage.cards.g.GerrardCapashen.class));
        cards.add(new SetCardInfo("Gerrard's Command", 53, Rarity.COMMON, mage.cards.g.GerrardsCommand.class));
        cards.add(new SetCardInfo("Harrow", 57, Rarity.COMMON, mage.cards.h.Harrow.class));
        cards.add(new SetCardInfo("Hideous End", 26, Rarity.COMMON, mage.cards.h.HideousEnd.class));
        cards.add(new SetCardInfo("Hornet Cannon", 28, Rarity.UNCOMMON, mage.cards.h.HornetCannon.class));
        cards.add(new SetCardInfo("Island", 68, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Lightning Greaves", 19, Rarity.UNCOMMON, mage.cards.l.LightningGreaves.class));
        cards.add(new SetCardInfo("Living Death", 31, Rarity.RARE, mage.cards.l.LivingDeath.class));
        cards.add(new SetCardInfo("Mountain", 69, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Narrow Escape", 55, Rarity.COMMON, mage.cards.n.NarrowEscape.class));
        cards.add(new SetCardInfo("Nomadic Elf", 38, Rarity.COMMON, mage.cards.n.NomadicElf.class));
        cards.add(new SetCardInfo("Order of Yawgmoth", 11, Rarity.UNCOMMON, mage.cards.o.OrderOfYawgmoth.class));
        cards.add(new SetCardInfo("Phyrexian Arena", 27, Rarity.RARE, mage.cards.p.PhyrexianArena.class));
        cards.add(new SetCardInfo("Phyrexian Battleflies", 3, Rarity.COMMON, mage.cards.p.PhyrexianBattleflies.class));
        cards.add(new SetCardInfo("Phyrexian Broodlings", 8, Rarity.COMMON, mage.cards.p.PhyrexianBroodlings.class));
        cards.add(new SetCardInfo("Phyrexian Colossus", 16, Rarity.RARE, mage.cards.p.PhyrexianColossus.class));
        cards.add(new SetCardInfo("Phyrexian Debaser", 10, Rarity.COMMON, mage.cards.p.PhyrexianDebaser.class));
        cards.add(new SetCardInfo("Phyrexian Defiler", 12, Rarity.UNCOMMON, mage.cards.p.PhyrexianDefiler.class));
        cards.add(new SetCardInfo("Phyrexian Denouncer", 4, Rarity.COMMON, mage.cards.p.PhyrexianDenouncer.class));
        cards.add(new SetCardInfo("Phyrexian Gargantua", 15, Rarity.UNCOMMON, mage.cards.p.PhyrexianGargantua.class));
        cards.add(new SetCardInfo("Phyrexian Ghoul", 6, Rarity.COMMON, mage.cards.p.PhyrexianGhoul.class));
        cards.add(new SetCardInfo("Phyrexian Hulk", 14, Rarity.UNCOMMON, mage.cards.p.PhyrexianHulk.class));
        cards.add(new SetCardInfo("Phyrexian Negator", 1, Rarity.MYTHIC, mage.cards.p.PhyrexianNegator.class));
        cards.add(new SetCardInfo("Phyrexian Plaguelord", 13, Rarity.RARE, mage.cards.p.PhyrexianPlaguelord.class));
        cards.add(new SetCardInfo("Phyrexian Processor", 29, Rarity.RARE, mage.cards.p.PhyrexianProcessor.class));
        cards.add(new SetCardInfo("Phyrexian Totem", 20, Rarity.UNCOMMON, mage.cards.p.PhyrexianTotem.class));
        cards.add(new SetCardInfo("Phyrexian Vault", 21, Rarity.UNCOMMON, mage.cards.p.PhyrexianVault.class));
        cards.add(new SetCardInfo("Plains", 67, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Power Armor", 62, Rarity.UNCOMMON, mage.cards.p.PowerArmor.class));
        cards.add(new SetCardInfo("Priest of Gix", 7, Rarity.UNCOMMON, mage.cards.p.PriestOfGix.class));
        cards.add(new SetCardInfo("Puppet Strings", 22, Rarity.UNCOMMON, mage.cards.p.PuppetStrings.class));
        cards.add(new SetCardInfo("Quirion Elves", 39, Rarity.COMMON, mage.cards.q.QuirionElves.class));
        cards.add(new SetCardInfo("Rith's Charm", 60, Rarity.UNCOMMON, mage.cards.r.RithsCharm.class));
        cards.add(new SetCardInfo("Rith, the Awakener", 48, Rarity.RARE, mage.cards.r.RithTheAwakener.class));
        cards.add(new SetCardInfo("Sanguine Guard", 9, Rarity.UNCOMMON, mage.cards.s.SanguineGuard.class));
        cards.add(new SetCardInfo("Shivan Oasis", 65, Rarity.UNCOMMON, mage.cards.s.ShivanOasis.class));
        cards.add(new SetCardInfo("Slay", 25, Rarity.UNCOMMON, mage.cards.s.Slay.class));
        cards.add(new SetCardInfo("Sunscape Battlemage", 40, Rarity.UNCOMMON, mage.cards.s.SunscapeBattlemage.class));
        cards.add(new SetCardInfo("Swamp", 32, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 33, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 34, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 35, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tendrils of Corruption", 30, Rarity.COMMON, mage.cards.t.TendrilsOfCorruption.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 66, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("Thornscape Apprentice", 37, Rarity.COMMON, mage.cards.t.ThornscapeApprentice.class));
        cards.add(new SetCardInfo("Thornscape Battlemage", 42, Rarity.UNCOMMON, mage.cards.t.ThornscapeBattlemage.class));
        cards.add(new SetCardInfo("Thunderscape Battlemage", 41, Rarity.UNCOMMON, mage.cards.t.ThunderscapeBattlemage.class));
        cards.add(new SetCardInfo("Treva's Charm", 61, Rarity.UNCOMMON, mage.cards.t.TrevasCharm.class));
        cards.add(new SetCardInfo("Treva, the Renewer", 49, Rarity.RARE, mage.cards.t.TrevaTheRenewer.class));
        cards.add(new SetCardInfo("Tribal Flames", 51, Rarity.COMMON, mage.cards.t.TribalFlames.class));
        cards.add(new SetCardInfo("Urza's Rage", 36, Rarity.MYTHIC, mage.cards.u.UrzasRage.class));
        cards.add(new SetCardInfo("Verduran Emissary", 43, Rarity.UNCOMMON, mage.cards.v.VerduranEmissary.class));
        cards.add(new SetCardInfo("Voltaic Key", 17, Rarity.UNCOMMON, mage.cards.v.VoltaicKey.class));
        cards.add(new SetCardInfo("Whispersilk Cloak", 23, Rarity.UNCOMMON, mage.cards.w.WhispersilkCloak.class));
        cards.add(new SetCardInfo("Worn Powerstone", 24, Rarity.UNCOMMON, mage.cards.w.WornPowerstone.class));
        cards.add(new SetCardInfo("Yavimaya Elder", 44, Rarity.COMMON, mage.cards.y.YavimayaElder.class));
    }
}
