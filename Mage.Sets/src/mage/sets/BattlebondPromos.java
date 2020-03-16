package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pbbd
 */
public class BattlebondPromos extends ExpansionSet {

    private static final BattlebondPromos instance = new BattlebondPromos();

    public static BattlebondPromos getInstance() {
        return instance;
    }

    private BattlebondPromos() {
        super("Battlebond Promos", "PBBD", ExpansionSet.buildDate(2018, 6, 9), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Blaring Captain", "14s", Rarity.UNCOMMON, mage.cards.b.BlaringCaptain.class));
        cards.add(new SetCardInfo("Blaring Recruiter", "13s", Rarity.UNCOMMON, mage.cards.b.BlaringRecruiter.class));
        cards.add(new SetCardInfo("Chakram Retriever", "15s", Rarity.UNCOMMON, mage.cards.c.ChakramRetriever.class));
        cards.add(new SetCardInfo("Chakram Slinger", "16s", Rarity.UNCOMMON, mage.cards.c.ChakramSlinger.class));
        cards.add(new SetCardInfo("Gorm the Great", "8s", Rarity.RARE, mage.cards.g.GormTheGreat.class));
        cards.add(new SetCardInfo("Impetuous Protege", "19s", Rarity.UNCOMMON, mage.cards.i.ImpetuousProtege.class));
        cards.add(new SetCardInfo("Khorvath Brightflame", "9s", Rarity.RARE, mage.cards.k.KhorvathBrightflame.class));
        cards.add(new SetCardInfo("Krav, the Unredeemed", "4s", Rarity.RARE, mage.cards.k.KravTheUnredeemed.class));
        cards.add(new SetCardInfo("Ley Weaver", "21s", Rarity.UNCOMMON, mage.cards.l.LeyWeaver.class));
        cards.add(new SetCardInfo("Lore Weaver", "22s", Rarity.UNCOMMON, mage.cards.l.LoreWeaver.class));
        cards.add(new SetCardInfo("Okaun, Eye of Chaos", "6s", Rarity.RARE, mage.cards.o.OkaunEyeOfChaos.class));
        cards.add(new SetCardInfo("Pir, Imaginative Rascal", "11s", Rarity.RARE, mage.cards.p.PirImaginativeRascal.class));
        cards.add(new SetCardInfo("Proud Mentor", "20s", Rarity.UNCOMMON, mage.cards.p.ProudMentor.class));
        cards.add(new SetCardInfo("Regna, the Redeemer", "3s", Rarity.RARE, mage.cards.r.RegnaTheRedeemer.class));
        cards.add(new SetCardInfo("Rowan Kenrith", "256s", Rarity.MYTHIC, mage.cards.r.RowanKenrith.class));
        cards.add(new SetCardInfo("Soulblade Corrupter", "17s", Rarity.UNCOMMON, mage.cards.s.SoulbladeCorrupter.class));
        cards.add(new SetCardInfo("Soulblade Renewer", "18s", Rarity.UNCOMMON, mage.cards.s.SoulbladeRenewer.class));
        cards.add(new SetCardInfo("Sylvia Brightspear", "10s", Rarity.RARE, mage.cards.s.SylviaBrightspear.class));
        cards.add(new SetCardInfo("Toothy, Imaginary Friend", "12s", Rarity.RARE, mage.cards.t.ToothyImaginaryFriend.class));
        cards.add(new SetCardInfo("Virtus the Veiled", "7s", Rarity.RARE, mage.cards.v.VirtusTheVeiled.class));
        cards.add(new SetCardInfo("Will Kenrith", "255s", Rarity.MYTHIC, mage.cards.w.WillKenrith.class));
        cards.add(new SetCardInfo("Zndrsplt, Eye of Wisdom", "5s", Rarity.RARE, mage.cards.z.ZndrspltEyeOfWisdom.class));
     }
}
