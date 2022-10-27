package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/slc
 */
public class SecretLair30thAnniversaryCountdownKit extends ExpansionSet {

    private static final SecretLair30thAnniversaryCountdownKit instance = new SecretLair30thAnniversaryCountdownKit();

    public static SecretLair30thAnniversaryCountdownKit getInstance() {
        return instance;
    }

    private SecretLair30thAnniversaryCountdownKit() {
        super("Secret Lair 30th Anniversary Countdown Kit", "SLC", ExpansionSet.buildDate(2022, 11, 1), SetType.PROMOTIONAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Arclight Phoenix", 2018, Rarity.RARE, mage.cards.a.ArclightPhoenix.class));
        cards.add(new SetCardInfo("Birthing Pod", 2011, Rarity.RARE, mage.cards.b.BirthingPod.class));
        cards.add(new SetCardInfo("Bloodbraid Elf", 2009, Rarity.RARE, mage.cards.b.BloodbraidElf.class));
        cards.add(new SetCardInfo("Bogardan Hellkite", 2006, Rarity.RARE, mage.cards.b.BogardanHellkite.class));
        cards.add(new SetCardInfo("Chrome Mox", 2003, Rarity.MYTHIC, mage.cards.c.ChromeMox.class));
        cards.add(new SetCardInfo("Deathrite Shaman", 2012, Rarity.RARE, mage.cards.d.DeathriteShaman.class));
        cards.add(new SetCardInfo("Dragonlord Ojutai", 2015, Rarity.RARE, mage.cards.d.DragonlordOjutai.class));
        cards.add(new SetCardInfo("Elite Spellbinder", 2021, Rarity.RARE, mage.cards.e.EliteSpellbinder.class));
        cards.add(new SetCardInfo("Elspeth, Sun's Champion", 2013, Rarity.MYTHIC, mage.cards.e.ElspethSunsChampion.class));
        cards.add(new SetCardInfo("Emry, Lurker of the Loch", 2019, Rarity.RARE, mage.cards.e.EmryLurkerOfTheLoch.class));
        cards.add(new SetCardInfo("Genesis", 2002, Rarity.RARE, mage.cards.g.Genesis.class));
        cards.add(new SetCardInfo("Glimpse of Nature", 2004, Rarity.RARE, mage.cards.g.GlimpseOfNature.class));
        cards.add(new SetCardInfo("Heritage Druid", 2008, Rarity.RARE, mage.cards.h.HeritageDruid.class));
        cards.add(new SetCardInfo("Lightning Helix", 2005, Rarity.RARE, mage.cards.l.LightningHelix.class));
        cards.add(new SetCardInfo("Lin Sivvi, Defiant Hero", 2000, Rarity.RARE, mage.cards.l.LinSivviDefiantHero.class));
        cards.add(new SetCardInfo("Mishra's Factory", 1994, Rarity.RARE, mage.cards.m.MishrasFactory.class));
        cards.add(new SetCardInfo("Nashi, Moon Sage's Scion", 2022, Rarity.MYTHIC, mage.cards.n.NashiMoonSagesScion.class));
        cards.add(new SetCardInfo("Necropotence", 1995, Rarity.MYTHIC, mage.cards.n.Necropotence.class));
        cards.add(new SetCardInfo("Nicol Bolas, God-Pharaoh", 2017, Rarity.MYTHIC, mage.cards.n.NicolBolasGodPharaoh.class));
        cards.add(new SetCardInfo("Ponder", 2007, Rarity.RARE, mage.cards.p.Ponder.class));
        cards.add(new SetCardInfo("Shark Typhoon", 2020, Rarity.RARE, mage.cards.s.SharkTyphoon.class));
        cards.add(new SetCardInfo("Shivan Dragon", 1993, Rarity.RARE, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Siege Rhino", 2014, Rarity.RARE, mage.cards.s.SiegeRhino.class));
        cards.add(new SetCardInfo("Smokestack", 1998, Rarity.RARE, mage.cards.s.Smokestack.class));
        cards.add(new SetCardInfo("Squee, Goblin Nabob", 1999, Rarity.RARE, mage.cards.s.SqueeGoblinNabob.class));
        cards.add(new SetCardInfo("Sun Titan", 2010, Rarity.MYTHIC, mage.cards.s.SunTitan.class));
        cards.add(new SetCardInfo("Thalia, Heretic Cathar", 2016, Rarity.RARE, mage.cards.t.ThaliaHereticCathar.class));
        cards.add(new SetCardInfo("Tradewind Rider", 1997, Rarity.RARE, mage.cards.t.TradewindRider.class));
        cards.add(new SetCardInfo("Wild Mongrel", 2001, Rarity.RARE, mage.cards.w.WildMongrel.class));
    }
}
