package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pfrf
 */
public class FateReforgedPromos extends ExpansionSet {

    private static final FateReforgedPromos instance = new FateReforgedPromos();

    public static FateReforgedPromos getInstance() {
        return instance;
    }

    private FateReforgedPromos() {
        super("Fate Reforged Promos", "PFRF", ExpansionSet.buildDate(2015, 1, 24), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Alesha, Who Smiles at Death", "90s", Rarity.RARE, mage.cards.a.AleshaWhoSmilesAtDeath.class));
        cards.add(new SetCardInfo("Arcbond", "91s", Rarity.RARE, mage.cards.a.Arcbond.class));
        cards.add(new SetCardInfo("Archfiend of Depravity", 62, Rarity.RARE, mage.cards.a.ArchfiendOfDepravity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archfiend of Depravity", "62s", Rarity.RARE, mage.cards.a.ArchfiendOfDepravity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Atarka, World Render", "149s", Rarity.RARE, mage.cards.a.AtarkaWorldRender.class));
        cards.add(new SetCardInfo("Brutal Hordechief", "64s", Rarity.MYTHIC, mage.cards.b.BrutalHordechief.class));
        cards.add(new SetCardInfo("Daghatar the Adamant", "9s", Rarity.RARE, mage.cards.d.DaghatarTheAdamant.class));
        cards.add(new SetCardInfo("Dragonscale General", 11, Rarity.RARE, mage.cards.d.DragonscaleGeneral.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dragonscale General", "11s", Rarity.RARE, mage.cards.d.DragonscaleGeneral.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dromoka, the Eternal", "151s", Rarity.RARE, mage.cards.d.DromokaTheEternal.class));
        cards.add(new SetCardInfo("Flamerush Rider", 99, Rarity.RARE, mage.cards.f.FlamerushRider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flamerush Rider", "99s", Rarity.RARE, mage.cards.f.FlamerushRider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flamewake Phoenix", "100s", Rarity.RARE, mage.cards.f.FlamewakePhoenix.class));
        cards.add(new SetCardInfo("Jeskai Infiltrator", "36s", Rarity.RARE, mage.cards.j.JeskaiInfiltrator.class));
        cards.add(new SetCardInfo("Kolaghan, the Storm's Fury", "155s", Rarity.RARE, mage.cards.k.KolaghanTheStormsFury.class));
        cards.add(new SetCardInfo("Mardu Shadowspear", 74, Rarity.UNCOMMON, mage.cards.m.MarduShadowspear.class));
        cards.add(new SetCardInfo("Mardu Strike Leader", "75s", Rarity.RARE, mage.cards.m.MarduStrikeLeader.class));
        cards.add(new SetCardInfo("Mastery of the Unseen", "19s", Rarity.RARE, mage.cards.m.MasteryOfTheUnseen.class));
        cards.add(new SetCardInfo("Ojutai, Soul of Winter", "156s", Rarity.RARE, mage.cards.o.OjutaiSoulOfWinter.class));
        cards.add(new SetCardInfo("Rally the Ancestors", "22s", Rarity.RARE, mage.cards.r.RallyTheAncestors.class));
        cards.add(new SetCardInfo("Sage-Eye Avengers", 50, Rarity.RARE, mage.cards.s.SageEyeAvengers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sage-Eye Avengers", "50s", Rarity.RARE, mage.cards.s.SageEyeAvengers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sandsteppe Mastodon", 137, Rarity.RARE, mage.cards.s.SandsteppeMastodon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sandsteppe Mastodon", "137s", Rarity.RARE, mage.cards.s.SandsteppeMastodon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shaman of the Great Hunt", "113s", Rarity.MYTHIC, mage.cards.s.ShamanOfTheGreatHunt.class));
        cards.add(new SetCardInfo("Shamanic Revelation", 138, Rarity.RARE, mage.cards.s.ShamanicRevelation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shamanic Revelation", "138s", Rarity.RARE, mage.cards.s.ShamanicRevelation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shu Yun, the Silent Tempest", "52s", Rarity.RARE, mage.cards.s.ShuYunTheSilentTempest.class));
        cards.add(new SetCardInfo("Silumgar, the Drifting Death", "157s", Rarity.RARE, mage.cards.s.SilumgarTheDriftingDeath.class));
        cards.add(new SetCardInfo("Soulfire Grand Master", "27s", Rarity.MYTHIC, mage.cards.s.SoulfireGrandMaster.class));
        cards.add(new SetCardInfo("Soulflayer", "84s", Rarity.RARE, mage.cards.s.Soulflayer.class));
        cards.add(new SetCardInfo("Supplant Form", 54, Rarity.RARE, mage.cards.s.SupplantForm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Supplant Form", "54s", Rarity.RARE, mage.cards.s.SupplantForm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tasigur, the Golden Fang", "87s", Rarity.RARE, mage.cards.t.TasigurTheGoldenFang.class));
        cards.add(new SetCardInfo("Temur War Shaman", 142, Rarity.RARE, mage.cards.t.TemurWarShaman.class));
        cards.add(new SetCardInfo("Torrent Elemental", "56s", Rarity.MYTHIC, mage.cards.t.TorrentElemental.class));
        cards.add(new SetCardInfo("Warden of the First Tree", "143s", Rarity.MYTHIC, mage.cards.w.WardenOfTheFirstTree.class));
        cards.add(new SetCardInfo("Wildcall", "146s", Rarity.RARE, mage.cards.w.Wildcall.class));
        cards.add(new SetCardInfo("Yasova Dragonclaw", "148s", Rarity.RARE, mage.cards.y.YasovaDragonclaw.class));
     }
}
