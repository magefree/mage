
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/sch
 * @author ReSech
 */
public final class StoreChampionships extends ExpansionSet {

    private static final StoreChampionships instance = new StoreChampionships();

    public static StoreChampionships getInstance() {
        return instance;
    }

    private StoreChampionships() {
        super("Store Championships", "SCH", ExpansionSet.buildDate(2022, 7, 9), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Aether Channeler", 11, Rarity.RARE, mage.cards.a.AetherChanneler.class));
        cards.add(new SetCardInfo("Angel of Despair", 22, Rarity.RARE, mage.cards.a.AngelOfDespair.class));
        cards.add(new SetCardInfo("Annex Sentry", 7, Rarity.RARE, mage.cards.a.AnnexSentry.class));
        cards.add(new SetCardInfo("Archmage's Charm", 2, Rarity.RARE, mage.cards.a.ArchmagesCharm.class));
        cards.add(new SetCardInfo("Bitter Triumph", 42, Rarity.RARE, mage.cards.b.BitterTriumph.class));
        cards.add(new SetCardInfo("Blazing Rootwalla", 24, Rarity.RARE, mage.cards.b.BlazingRootwalla.class));
        cards.add(new SetCardInfo("Cauldron Familiar", 18, Rarity.RARE, mage.cards.c.CauldronFamiliar.class));
        cards.add(new SetCardInfo("Charming Scoundrel", 37, Rarity.RARE, mage.cards.c.CharmingScoundrel.class));
        cards.add(new SetCardInfo("Chromatic Sphere", 30, Rarity.RARE, mage.cards.c.ChromaticSphere.class));
        cards.add(new SetCardInfo("City of Brass", 41, Rarity.RARE, mage.cards.c.CityOfBrass.class));
        cards.add(new SetCardInfo("Dark Confidant", 3, Rarity.RARE, mage.cards.d.DarkConfidant.class, FULL_ART));
        cards.add(new SetCardInfo("Dark Petition", 19, Rarity.RARE, mage.cards.d.DarkPetition.class));
        cards.add(new SetCardInfo("Dauthi Voidwalker", "23e", Rarity.RARE, mage.cards.d.DauthiVoidwalker.class, FULL_ART));
        cards.add(new SetCardInfo("Death's Shadow", 40, Rarity.RARE, mage.cards.d.DeathsShadow.class));
        cards.add(new SetCardInfo("Deep-Cavern Bat", 33, Rarity.RARE, mage.cards.d.DeepCavernBat.class));
        cards.add(new SetCardInfo("Eidolon of the Great Revel", 14, Rarity.RARE, mage.cards.e.EidolonOfTheGreatRevel.class));
        cards.add(new SetCardInfo("Fable of the Mirror-Breaker", 44, Rarity.RARE, mage.cards.f.FableOfTheMirrorBreaker.class));
        cards.add(new SetCardInfo("Flame Slash", 1, Rarity.RARE, mage.cards.f.FlameSlash.class));
        cards.add(new SetCardInfo("Gifted Aetherborn", 13, Rarity.RARE, mage.cards.g.GiftedAetherborn.class));
        cards.add(new SetCardInfo("Gilded Goose", 5, Rarity.RARE, mage.cards.g.GildedGoose.class));
        cards.add(new SetCardInfo("Gleeful Demolition", 36, Rarity.RARE, mage.cards.g.GleefulDemolition.class));
        cards.add(new SetCardInfo("Goddric, Cloaked Reveler", 38, Rarity.RARE, mage.cards.g.GoddricCloakedReveler.class, FULL_ART));
        cards.add(new SetCardInfo("Hollow One", 25, Rarity.RARE, mage.cards.h.HollowOne.class));
        cards.add(new SetCardInfo("Koth, Fire of Resistance", 9, Rarity.RARE, mage.cards.k.KothFireOfResistance.class));
        cards.add(new SetCardInfo("Lier, Disciple of the Drowned", 20, Rarity.RARE, mage.cards.l.LierDiscipleOfTheDrowned.class, FULL_ART));
        cards.add(new SetCardInfo("Memory Deluge", 8, Rarity.RARE, mage.cards.m.MemoryDeluge.class));
        cards.add(new SetCardInfo("Monastery Swiftspear", 27, Rarity.RARE, mage.cards.m.MonasterySwiftspear.class));
        cards.add(new SetCardInfo("Moonshaker Cavalry", 17, Rarity.MYTHIC, mage.cards.m.MoonshakerCavalry.class, FULL_ART));
        cards.add(new SetCardInfo("Mortify", 21, Rarity.RARE, mage.cards.m.Mortify.class));
        cards.add(new SetCardInfo("Omnath, Locus of Creation", 6, Rarity.MYTHIC, mage.cards.o.OmnathLocusOfCreation.class, FULL_ART));
        cards.add(new SetCardInfo("Preacher of the Schism", 34, Rarity.RARE, mage.cards.p.PreacherOfTheSchism.class));
        cards.add(new SetCardInfo("Preordain", 39, Rarity.RARE, mage.cards.p.Preordain.class));
        cards.add(new SetCardInfo("Reality Smasher", 31, Rarity.RARE, mage.cards.r.RealitySmasher.class));
        cards.add(new SetCardInfo("Reflection of Kiki-Jiki", 44, Rarity.RARE, mage.cards.r.ReflectionOfKikiJiki.class));
        cards.add(new SetCardInfo("Shark Typhoon", 28, Rarity.RARE, mage.cards.s.SharkTyphoon.class));
        cards.add(new SetCardInfo("Slickshot Show-Off", 43, Rarity.RARE, mage.cards.s.SlickshotShowOff.class));
        cards.add(new SetCardInfo("Spell Pierce", 4, Rarity.RARE, mage.cards.s.SpellPierce.class));
        cards.add(new SetCardInfo("Strangle", 10, Rarity.RARE, mage.cards.s.Strangle.class));
        cards.add(new SetCardInfo("Tail Swipe", 15, Rarity.RARE, mage.cards.t.TailSwipe.class));
        cards.add(new SetCardInfo("Thalia and The Gitrog Monster", 12, Rarity.MYTHIC, mage.cards.t.ThaliaAndTheGitrogMonster.class, FULL_ART));
        cards.add(new SetCardInfo("Transcendent Message", 16, Rarity.RARE, mage.cards.t.TranscendentMessage.class));
        cards.add(new SetCardInfo("Urza's Saga", 29, Rarity.RARE, mage.cards.u.UrzasSaga.class, FULL_ART));
        cards.add(new SetCardInfo("Vengevine", 26, Rarity.RARE, mage.cards.v.Vengevine.class, FULL_ART));
        cards.add(new SetCardInfo("Virtue of Persistence", 35, Rarity.MYTHIC, mage.cards.v.VirtueOfPersistence.class));
        cards.add(new SetCardInfo("Void Winnower", 32, Rarity.RARE, mage.cards.v.VoidWinnower.class, FULL_ART));
    }

}
