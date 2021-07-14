package mage.sets;

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.cards.FrameStyle;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author magenoxx
 */
public final class Unhinged extends ExpansionSet {

    private static final Unhinged instance = new Unhinged();

    public static Unhinged getInstance() {
        return instance;
    }

    private Unhinged() {
        super("Unhinged", "UNH", ExpansionSet.buildDate(2004, 11, 20), SetType.JOKESET);

        cards.add(new SetCardInfo("\"Ach! Hans, Run!\"", 116, Rarity.RARE, mage.cards.a.AchHansRun.class));
        cards.add(new SetCardInfo("B-I-N-G-O", 92, Rarity.RARE, mage.cards.b.BINGO.class));
        cards.add(new SetCardInfo("Blast from the Past", "72*", Rarity.RARE, mage.cards.b.BlastFromThePast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blast from the Past", 72, Rarity.RARE, mage.cards.b.BlastFromThePast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodletter", 50, Rarity.COMMON, mage.cards.b.Bloodletter.class));
        cards.add(new SetCardInfo("Booster Tutor", 51, Rarity.UNCOMMON, mage.cards.b.BoosterTutor.class));
        cards.add(new SetCardInfo("Double Header", 31, Rarity.COMMON, mage.cards.d.DoubleHeader.class));
        cards.add(new SetCardInfo("Elvish House Party", 94, Rarity.UNCOMMON, mage.cards.e.ElvishHouseParty.class));
        cards.add(new SetCardInfo("First Come, First Served", 12, Rarity.UNCOMMON, mage.cards.f.FirstComeFirstServed.class));
        cards.add(new SetCardInfo("Forest", 140, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(FrameStyle.UNH_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Form of the Squirrel", 96, Rarity.RARE, mage.cards.f.FormOfTheSquirrel.class));
        cards.add(new SetCardInfo("Goblin Secret Agent", 79, Rarity.COMMON, mage.cards.g.GoblinSecretAgent.class));
        cards.add(new SetCardInfo("Island", 137, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(FrameStyle.UNH_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Johnny, Combo Player", 35, Rarity.RARE, mage.cards.j.JohnnyComboPlayer.class));
        cards.add(new SetCardInfo("Mana Screw", "123*", Rarity.UNCOMMON, mage.cards.m.ManaScrew.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Screw", 123, Rarity.UNCOMMON, mage.cards.m.ManaScrew.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mise", 38, Rarity.UNCOMMON, mage.cards.m.Mise.class));
        cards.add(new SetCardInfo("Monkey Monkey Monkey", "104*", Rarity.COMMON, mage.cards.m.MonkeyMonkeyMonkey.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Monkey Monkey Monkey", 104, Rarity.COMMON, mage.cards.m.MonkeyMonkeyMonkey.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 139, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(FrameStyle.UNH_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Mox Lotus", "124*", Rarity.RARE, mage.cards.m.MoxLotus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Lotus", 124, Rarity.RARE, mage.cards.m.MoxLotus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Now I Know My ABC's", 41, Rarity.RARE, mage.cards.n.NowIKnowMyABCs.class));
        cards.add(new SetCardInfo("Old Fogey", "106*", Rarity.RARE, mage.cards.o.OldFogey.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Old Fogey", 106, Rarity.RARE, mage.cards.o.OldFogey.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 136, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(FrameStyle.UNH_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Rare-B-Gone", 119, Rarity.RARE, mage.cards.r.RareBGone.class));
        cards.add(new SetCardInfo("Rod of Spanking", 127, Rarity.UNCOMMON, mage.cards.r.RodOfSpanking.class));
        cards.add(new SetCardInfo("Six-y Beast", 89, Rarity.UNCOMMON, mage.cards.s.SixyBeast.class));
        cards.add(new SetCardInfo("Swamp", 138, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(FrameStyle.UNH_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Symbol Status", 114, Rarity.UNCOMMON, mage.cards.s.SymbolStatus.class));
        cards.add(new SetCardInfo("The Fallen Apart", 55, Rarity.COMMON, mage.cards.t.TheFallenApart.class));
        cards.add(new SetCardInfo("Togglodyte", 129, Rarity.UNCOMMON, mage.cards.t.Togglodyte.class));
        cards.add(new SetCardInfo("Uktabi Kong", 115, Rarity.RARE, mage.cards.u.UktabiKong.class));
        cards.add(new SetCardInfo("Urza's Hot Tub", 131, Rarity.UNCOMMON, mage.cards.u.UrzasHotTub.class));
        cards.add(new SetCardInfo("When Fluffy Bunnies Attack", "67*", Rarity.COMMON, mage.cards.w.WhenFluffyBunniesAttack.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("When Fluffy Bunnies Attack", 67, Rarity.COMMON, mage.cards.w.WhenFluffyBunniesAttack.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wordmail", 22, Rarity.COMMON, mage.cards.w.Wordmail.class));
        cards.add(new SetCardInfo("World-Bottling Kit", 133, Rarity.RARE, mage.cards.w.WorldBottlingKit.class));
        cards.add(new SetCardInfo("Zzzyxas's Abyss", 70, Rarity.RARE, mage.cards.z.ZzzyxassAbyss.class));
    }
}
