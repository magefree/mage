package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class PrereleaseEvents extends ExpansionSet {

    private static final PrereleaseEvents instance = new PrereleaseEvents();

    public static PrereleaseEvents getInstance() {
        return instance;
    }

    private PrereleaseEvents() {
        super("Prerelease Events", "PPRE", ExpansionSet.buildDate(1990, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        // Commented cards are non-English printings
        cards.add(new SetCardInfo("Ajani Vengeant", 38, Rarity.MYTHIC, mage.cards.a.AjaniVengeant.class));
        cards.add(new SetCardInfo("Allosaurus Rider", 30, Rarity.RARE, mage.cards.a.AllosaurusRider.class));
        cards.add(new SetCardInfo("Avatar of Discord", 29, Rarity.RARE, mage.cards.a.AvatarOfDiscord.class));
        cards.add(new SetCardInfo("Avatar of Hope", 11, Rarity.RARE, mage.cards.a.AvatarOfHope.class));
        cards.add(new SetCardInfo("Beast of Burden", 5, Rarity.RARE, mage.cards.b.BeastOfBurden.class));
        cards.add(new SetCardInfo("Demigod of Revenge", 36, Rarity.RARE, mage.cards.d.DemigodOfRevenge.class));
        cards.add(new SetCardInfo("Dirtcowl Wurm", 1, Rarity.RARE, mage.cards.d.DirtcowlWurm.class));
        cards.add(new SetCardInfo("Djinn Illuminatus", 28, Rarity.RARE, mage.cards.d.DjinnIlluminatus.class));
        cards.add(new SetCardInfo("Door of Destinies", 35, Rarity.RARE, mage.cards.d.DoorOfDestinies.class));
        cards.add(new SetCardInfo("Dragon Broodmother", 40, Rarity.MYTHIC, mage.cards.d.DragonBroodmother.class));
        cards.add(new SetCardInfo("False Prophet", 7, Rarity.RARE, mage.cards.f.FalseProphet.class));
        cards.add(new SetCardInfo("Feral Throwback", 19, Rarity.RARE, mage.cards.f.FeralThrowback.class));
        //cards.add(new SetCardInfo("Fungal Shambler", 14, Rarity.RARE, mage.cards.f.FungalShambler.class));
        cards.add(new SetCardInfo("Gleancrawler", 27, Rarity.RARE, mage.cards.g.Gleancrawler.class));
        //cards.add(new SetCardInfo("Glory", 17, Rarity.RARE, mage.cards.g.Glory.class));
        cards.add(new SetCardInfo("Helm of Kaldra", 23, Rarity.RARE, mage.cards.h.HelmOfKaldra.class));
        cards.add(new SetCardInfo("Ink-Eyes, Servant of Oni", 25, Rarity.RARE, mage.cards.i.InkEyesServantOfOni.class));
        cards.add(new SetCardInfo("Kiyomaro, First to Stand", 26, Rarity.RARE, mage.cards.k.KiyomaroFirstToStand.class));
        cards.add(new SetCardInfo("Korlash, Heir to Blackblade", 33, Rarity.RARE, mage.cards.k.KorlashHeirToBlackblade.class));
        //cards.add(new SetCardInfo("Laquatus's Champion", 16, Rarity.RARE, mage.cards.l.LaquatussChampion.class));
        cards.add(new SetCardInfo("Lightning Dragon", 4, Rarity.RARE, mage.cards.l.LightningDragon.class));
        cards.add(new SetCardInfo("Lotus Bloom", 31, Rarity.RARE, mage.cards.l.LotusBloom.class));
        cards.add(new SetCardInfo("Lu Bu, Master-at-Arms", 6, Rarity.RARE, mage.cards.l.LuBuMasterAtArms.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lu Bu, Master-at-Arms", 8, Rarity.RARE, mage.cards.l.LuBuMasterAtArms.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Malfegor", 39, Rarity.RARE, mage.cards.m.Malfegor.class));
        cards.add(new SetCardInfo("Monstrous Hound", 3, Rarity.RARE, mage.cards.m.MonstrousHound.class));
        cards.add(new SetCardInfo("Oros, the Avenger", 32, Rarity.RARE, mage.cards.o.OrosTheAvenger.class));
        cards.add(new SetCardInfo("Overbeing of Myth", 37, Rarity.RARE, mage.cards.o.OverbeingOfMyth.class));
        cards.add(new SetCardInfo("Overtaker", 9, Rarity.RARE, mage.cards.o.Overtaker.class));
        //cards.add(new SetCardInfo("Questing Phelddagrif", 13, Rarity.RARE, mage.cards.q.QuestingPhelddagrif.class));
        //cards.add(new SetCardInfo("Raging Kavu", 12, Rarity.RARE, mage.cards.r.RagingKavu.class));
        cards.add(new SetCardInfo("Rathi Assassin", 10, Rarity.RARE, mage.cards.r.RathiAssassin.class));
        cards.add(new SetCardInfo("Revenant", 2, Rarity.RARE, mage.cards.r.Revenant.class));
        cards.add(new SetCardInfo("Ryusei, the Falling Star", 24, Rarity.RARE, mage.cards.r.RyuseiTheFallingStar.class));
        cards.add(new SetCardInfo("Shield of Kaldra", 22, Rarity.RARE, mage.cards.s.ShieldOfKaldra.class));
        cards.add(new SetCardInfo("Silent Specter", 18, Rarity.RARE, mage.cards.s.SilentSpecter.class));
        cards.add(new SetCardInfo("Soul Collector", 20, Rarity.RARE, mage.cards.s.SoulCollector.class));
        //cards.add(new SetCardInfo("Stone-Tongue Basilisk", 15, Rarity.RARE, mage.cards.s.StoneTongueBasilisk.class));
        cards.add(new SetCardInfo("Sword of Kaldra", 21, Rarity.RARE, mage.cards.s.SwordOfKaldra.class));
        cards.add(new SetCardInfo("Wren's Run Packmaster", 34, Rarity.RARE, mage.cards.w.WrensRunPackmaster.class));
    }
}