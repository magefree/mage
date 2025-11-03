package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/slc
 */
public class SecretLairCountdown extends ExpansionSet {

    private static final SecretLairCountdown instance = new SecretLairCountdown();

    public static SecretLairCountdown getInstance() {
        return instance;
    }

    private SecretLairCountdown() {
        super("Secret Lair Countdown", "SLC", ExpansionSet.buildDate(2022, 11, 1), SetType.PROMOTIONAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Altar of the Brood", 1, Rarity.RARE, mage.cards.a.AltarOfTheBrood.class));
        cards.add(new SetCardInfo("Arclight Phoenix", 2018, Rarity.MYTHIC, mage.cards.a.ArclightPhoenix.class));
        cards.add(new SetCardInfo("Birthing Pod", 2011, Rarity.RARE, mage.cards.b.BirthingPod.class));
        cards.add(new SetCardInfo("Bloodbraid Elf", 2009, Rarity.RARE, mage.cards.b.BloodbraidElf.class));
        cards.add(new SetCardInfo("Bogardan Hellkite", 2006, Rarity.MYTHIC, mage.cards.b.BogardanHellkite.class));
        cards.add(new SetCardInfo("Brain Freeze", 2, Rarity.RARE, mage.cards.b.BrainFreeze.class));
        cards.add(new SetCardInfo("Chrome Mox", 2003, Rarity.MYTHIC, mage.cards.c.ChromeMox.class, RETRO_ART));
        cards.add(new SetCardInfo("Crop Rotation", 3, Rarity.RARE, mage.cards.c.CropRotation.class));
        cards.add(new SetCardInfo("Deathrite Shaman", 2012, Rarity.RARE, mage.cards.d.DeathriteShaman.class));
        cards.add(new SetCardInfo("Demonic Consultation", 4, Rarity.RARE, mage.cards.d.DemonicConsultation.class));
        cards.add(new SetCardInfo("Dragonlord Ojutai", 2015, Rarity.MYTHIC, mage.cards.d.DragonlordOjutai.class));
        cards.add(new SetCardInfo("Eerie Ultimatum", 5, Rarity.RARE, mage.cards.e.EerieUltimatum.class));
        cards.add(new SetCardInfo("Elite Spellbinder", 2021, Rarity.RARE, mage.cards.e.EliteSpellbinder.class));
        cards.add(new SetCardInfo("Elspeth, Sun's Champion", 2013, Rarity.MYTHIC, mage.cards.e.ElspethSunsChampion.class, RETRO_ART));
        cards.add(new SetCardInfo("Emry, Lurker of the Loch", 2019, Rarity.RARE, mage.cards.e.EmryLurkerOfTheLoch.class));
        cards.add(new SetCardInfo("Field of the Dead", 6, Rarity.RARE, mage.cards.f.FieldOfTheDead.class));
        cards.add(new SetCardInfo("Genesis", 2002, Rarity.RARE, mage.cards.g.Genesis.class));
        cards.add(new SetCardInfo("Glimpse of Nature", 2004, Rarity.RARE, mage.cards.g.GlimpseOfNature.class));
        cards.add(new SetCardInfo("Gray Merchant of Asphodel", 7, Rarity.RARE, mage.cards.g.GrayMerchantOfAsphodel.class));
        cards.add(new SetCardInfo("Heritage Druid", 2008, Rarity.RARE, mage.cards.h.HeritageDruid.class));
        cards.add(new SetCardInfo("Hymn to Tourach", 8, Rarity.RARE, mage.cards.h.HymnToTourach.class));
        cards.add(new SetCardInfo("Isochron Scepter", 9, Rarity.RARE, mage.cards.i.IsochronScepter.class));
        cards.add(new SetCardInfo("Junji, the Midnight Sky", 10, Rarity.MYTHIC, mage.cards.j.JunjiTheMidnightSky.class));
        cards.add(new SetCardInfo("Krark-Clan Ironworks", 11, Rarity.RARE, mage.cards.k.KrarkClanIronworks.class));
        cards.add(new SetCardInfo("Lightning Helix", 2005, Rarity.RARE, mage.cards.l.LightningHelix.class));
        cards.add(new SetCardInfo("Lim-Dul's Vault", 1996, Rarity.RARE, mage.cards.l.LimDulsVault.class));
        cards.add(new SetCardInfo("Lin Sivvi, Defiant Hero", 2000, Rarity.RARE, mage.cards.l.LinSivviDefiantHero.class));
        cards.add(new SetCardInfo("Llanowar Elves", 12, Rarity.RARE, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Lotus Field", 2023, Rarity.MYTHIC, mage.cards.l.LotusField.class));
        cards.add(new SetCardInfo("Mishra's Factory", 1994, Rarity.RARE, mage.cards.m.MishrasFactory.class));
        cards.add(new SetCardInfo("Myrel, Shield of Argive", 13, Rarity.RARE, mage.cards.m.MyrelShieldOfArgive.class));
        cards.add(new SetCardInfo("Narset's Reversal", 14, Rarity.RARE, mage.cards.n.NarsetsReversal.class));
        cards.add(new SetCardInfo("Nashi, Moon Sage's Scion", 2022, Rarity.MYTHIC, mage.cards.n.NashiMoonSagesScion.class));
        cards.add(new SetCardInfo("Necropotence", 1995, Rarity.MYTHIC, mage.cards.n.Necropotence.class));
        cards.add(new SetCardInfo("Nicol Bolas, God-Pharaoh", 2017, Rarity.MYTHIC, mage.cards.n.NicolBolasGodPharaoh.class));
        cards.add(new SetCardInfo("Ob Nixilis, the Fallen", 15, Rarity.MYTHIC, mage.cards.o.ObNixilisTheFallen.class));
        cards.add(new SetCardInfo("Phyrexian Altar", 16, Rarity.RARE, mage.cards.p.PhyrexianAltar.class));
        cards.add(new SetCardInfo("Ponder", 2007, Rarity.RARE, mage.cards.p.Ponder.class));
        cards.add(new SetCardInfo("Questing Beast", 17, Rarity.MYTHIC, mage.cards.q.QuestingBeast.class));
        cards.add(new SetCardInfo("Retrofitter Foundry", 18, Rarity.RARE, mage.cards.r.RetrofitterFoundry.class));
        cards.add(new SetCardInfo("Shark Typhoon", 2020, Rarity.RARE, mage.cards.s.SharkTyphoon.class));
        cards.add(new SetCardInfo("Shivan Dragon", 1993, Rarity.RARE, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Siege Rhino", 2014, Rarity.RARE, mage.cards.s.SiegeRhino.class));
        cards.add(new SetCardInfo("Smokestack", 1998, Rarity.RARE, mage.cards.s.Smokestack.class));
        cards.add(new SetCardInfo("Sol Ring", 19, Rarity.RARE, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Squee, Goblin Nabob", 1999, Rarity.RARE, mage.cards.s.SqueeGoblinNabob.class));
        cards.add(new SetCardInfo("Sun Titan", 2010, Rarity.MYTHIC, mage.cards.s.SunTitan.class));
        cards.add(new SetCardInfo("Temple of the False God", 20, Rarity.RARE, mage.cards.t.TempleOfTheFalseGod.class));
        cards.add(new SetCardInfo("Thalia, Heretic Cathar", 2016, Rarity.RARE, mage.cards.t.ThaliaHereticCathar.class));
        cards.add(new SetCardInfo("Tradewind Rider", 1997, Rarity.RARE, mage.cards.t.TradewindRider.class));
        cards.add(new SetCardInfo("Urza's Saga", 21, Rarity.RARE, mage.cards.u.UrzasSaga.class));
        cards.add(new SetCardInfo("Vesuva", 22, Rarity.RARE, mage.cards.v.Vesuva.class));
        cards.add(new SetCardInfo("Wasteland", 23, Rarity.RARE, mage.cards.w.Wasteland.class));
        cards.add(new SetCardInfo("Wild Mongrel", 2001, Rarity.RARE, mage.cards.w.WildMongrel.class));
        cards.add(new SetCardInfo("Xantcha, Sleeper Agent", 24, Rarity.RARE, mage.cards.x.XantchaSleeperAgent.class));
        cards.add(new SetCardInfo("Yarok, the Desecrated", 25, Rarity.MYTHIC, mage.cards.y.YarokTheDesecrated.class));
        cards.add(new SetCardInfo("Zo-Zu the Punisher", 26, Rarity.RARE, mage.cards.z.ZoZuThePunisher.class));
    }
}
