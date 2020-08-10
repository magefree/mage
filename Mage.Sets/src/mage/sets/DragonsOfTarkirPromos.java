package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pdtk
 */
public class DragonsOfTarkirPromos extends ExpansionSet {

    private static final DragonsOfTarkirPromos instance = new DragonsOfTarkirPromos();

    public static DragonsOfTarkirPromos getInstance() {
        return instance;
    }

    private DragonsOfTarkirPromos() {
        super("Dragons of Tarkir Promos", "PDTK", ExpansionSet.buildDate(2015, 3, 28), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Anafenza, Kin-Tree Spirit", "2s", Rarity.RARE, mage.cards.a.AnafenzaKinTreeSpirit.class));
        cards.add(new SetCardInfo("Arashin Foremost", "3s", Rarity.RARE, mage.cards.a.ArashinForemost.class));
        cards.add(new SetCardInfo("Arashin Sovereign", 212, Rarity.RARE, mage.cards.a.ArashinSovereign.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arashin Sovereign", "212s", Rarity.RARE, mage.cards.a.ArashinSovereign.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Atarka's Command", "213s", Rarity.RARE, mage.cards.a.AtarkasCommand.class));
        cards.add(new SetCardInfo("Avatar of the Resolute", "175s", Rarity.RARE, mage.cards.a.AvatarOfTheResolute.class));
        cards.add(new SetCardInfo("Blessed Reincarnation", "47s", Rarity.RARE, mage.cards.b.BlessedReincarnation.class));
        cards.add(new SetCardInfo("Blood-Chin Fanatic", "88s", Rarity.RARE, mage.cards.b.BloodChinFanatic.class));
        cards.add(new SetCardInfo("Boltwing Marauder", 214, Rarity.RARE, mage.cards.b.BoltwingMarauder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Boltwing Marauder", "214s", Rarity.RARE, mage.cards.b.BoltwingMarauder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crater Elemental", "132s", Rarity.RARE, mage.cards.c.CraterElemental.class));
        cards.add(new SetCardInfo("Damnable Pact", "93s", Rarity.RARE, mage.cards.d.DamnablePact.class));
        cards.add(new SetCardInfo("Deathbringer Regent", 96, Rarity.RARE, mage.cards.d.DeathbringerRegent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deathbringer Regent", "96s", Rarity.RARE, mage.cards.d.DeathbringerRegent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Den Protector", "181s", Rarity.RARE, mage.cards.d.DenProtector.class));
        cards.add(new SetCardInfo("Dragonlord Atarka", "216s", Rarity.MYTHIC, mage.cards.d.DragonlordAtarka.class));
        cards.add(new SetCardInfo("Dragonlord Dromoka", "217s", Rarity.MYTHIC, mage.cards.d.DragonlordDromoka.class));
        cards.add(new SetCardInfo("Dragonlord Kolaghan", "218s", Rarity.MYTHIC, mage.cards.d.DragonlordKolaghan.class));
        cards.add(new SetCardInfo("Dragonlord Ojutai", "219s", Rarity.MYTHIC, mage.cards.d.DragonlordOjutai.class));
        cards.add(new SetCardInfo("Dragonlord Silumgar", "220s", Rarity.MYTHIC, mage.cards.d.DragonlordSilumgar.class));
        cards.add(new SetCardInfo("Dromoka's Command", "221s", Rarity.RARE, mage.cards.d.DromokasCommand.class));
        cards.add(new SetCardInfo("Foe-Razer Regent", "187s", Rarity.RARE, mage.cards.f.FoeRazerRegent.class));
        cards.add(new SetCardInfo("Harbinger of the Hunt", 223, Rarity.RARE, mage.cards.h.HarbingerOfTheHunt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Harbinger of the Hunt", "223s", Rarity.RARE, mage.cards.h.HarbingerOfTheHunt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hidden Dragonslayer", "23s", Rarity.RARE, mage.cards.h.HiddenDragonslayer.class));
        cards.add(new SetCardInfo("Icefall Regent", "58s", Rarity.RARE, mage.cards.i.IcefallRegent.class));
        cards.add(new SetCardInfo("Ire Shaman", "141s", Rarity.RARE, mage.cards.i.IreShaman.class));
        cards.add(new SetCardInfo("Kolaghan's Command", "224s", Rarity.RARE, mage.cards.k.KolaghansCommand.class));
        cards.add(new SetCardInfo("Living Lore", "61s", Rarity.RARE, mage.cards.l.LivingLore.class));
        cards.add(new SetCardInfo("Myth Realized", "26s", Rarity.RARE, mage.cards.m.MythRealized.class));
        cards.add(new SetCardInfo("Necromaster Dragon", 226, Rarity.RARE, mage.cards.n.NecromasterDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Necromaster Dragon", "226s", Rarity.RARE, mage.cards.n.NecromasterDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ojutai's Command", 227, Rarity.RARE, mage.cards.o.OjutaisCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ojutai's Command", "227s", Rarity.RARE, mage.cards.o.OjutaisCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pitiless Horde", "112s", Rarity.RARE, mage.cards.p.PitilessHorde.class));
        cards.add(new SetCardInfo("Pristine Skywise", 228, Rarity.RARE, mage.cards.p.PristineSkywise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pristine Skywise", "228s", Rarity.RARE, mage.cards.p.PristineSkywise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Profaner of the Dead", "70s", Rarity.RARE, mage.cards.p.ProfanerOfTheDead.class));
        cards.add(new SetCardInfo("Scaleguard Sentinels", 201, Rarity.UNCOMMON, mage.cards.s.ScaleguardSentinels.class));
        cards.add(new SetCardInfo("Sidisi, Undead Vizier", "120s", Rarity.RARE, mage.cards.s.SidisiUndeadVizier.class));
        cards.add(new SetCardInfo("Silumgar Assassin", "121s", Rarity.RARE, mage.cards.s.SilumgarAssassin.class));
        cards.add(new SetCardInfo("Silumgar's Command", "232s", Rarity.RARE, mage.cards.s.SilumgarsCommand.class));
        cards.add(new SetCardInfo("Stratus Dancer", "80s", Rarity.RARE, mage.cards.s.StratusDancer.class));
        cards.add(new SetCardInfo("Sunscorch Regent", "41s", Rarity.RARE, mage.cards.s.SunscorchRegent.class));
        cards.add(new SetCardInfo("Surrak, the Hunt Caller", "210s", Rarity.RARE, mage.cards.s.SurrakTheHuntCaller.class));
        cards.add(new SetCardInfo("Thunderbreak Regent", 162, Rarity.RARE, mage.cards.t.ThunderbreakRegent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thunderbreak Regent", "162s", Rarity.RARE, mage.cards.t.ThunderbreakRegent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Volcanic Vision", "167s", Rarity.RARE, mage.cards.v.VolcanicVision.class));
        cards.add(new SetCardInfo("Zurgo Bellstriker", "169s", Rarity.RARE, mage.cards.z.ZurgoBellstriker.class));
     }
}
