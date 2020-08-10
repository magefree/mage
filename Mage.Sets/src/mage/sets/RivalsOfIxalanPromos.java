package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/prix
 */
public class RivalsOfIxalanPromos extends ExpansionSet {

    private static final RivalsOfIxalanPromos instance = new RivalsOfIxalanPromos();

    public static RivalsOfIxalanPromos getInstance() {
        return instance;
    }

    private RivalsOfIxalanPromos() {
        super("Rivals of Ixalan Promos", "PRIX", ExpansionSet.buildDate(2018, 1, 19), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Admiral's Order", "31p", Rarity.RARE, mage.cards.a.AdmiralsOrder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Admiral's Order", "31s", Rarity.RARE, mage.cards.a.AdmiralsOrder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Angrath, the Flame-Chained", "152s", Rarity.MYTHIC, mage.cards.a.AngrathTheFlameChained.class));
        cards.add(new SetCardInfo("Arch of Orazca", "185p", Rarity.RARE, mage.cards.a.ArchOfOrazca.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arch of Orazca", "185s", Rarity.RARE, mage.cards.a.ArchOfOrazca.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Atzal, Cave of Eternity", "160s", Rarity.RARE, mage.cards.a.AtzalCaveOfEternity.class));
        cards.add(new SetCardInfo("Awakened Amalgam", "175s", Rarity.RARE, mage.cards.a.AwakenedAmalgam.class));
        cards.add(new SetCardInfo("Azor's Gateway", "176s", Rarity.MYTHIC, mage.cards.a.AzorsGateway.class));
        cards.add(new SetCardInfo("Azor, the Lawbringer", "154s", Rarity.MYTHIC, mage.cards.a.AzorTheLawbringer.class));
        cards.add(new SetCardInfo("Bishop of Binding", "2s", Rarity.RARE, mage.cards.b.BishopOfBinding.class));
        cards.add(new SetCardInfo("Blood Sun", "92p", Rarity.RARE, mage.cards.b.BloodSun.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Sun", "92s", Rarity.RARE, mage.cards.b.BloodSun.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brass's Bounty", 94, Rarity.RARE, mage.cards.b.BrasssBounty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brass's Bounty", "94s", Rarity.RARE, mage.cards.b.BrasssBounty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain's Hook", 177, Rarity.RARE, mage.cards.c.CaptainsHook.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain's Hook", "177s", Rarity.RARE, mage.cards.c.CaptainsHook.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Champion of Dusk", "64s", Rarity.RARE, mage.cards.c.ChampionOfDusk.class));
        cards.add(new SetCardInfo("Crafty Cutpurse", "33s", Rarity.RARE, mage.cards.c.CraftyCutpurse.class));
        cards.add(new SetCardInfo("Dead Man's Chest", "66s", Rarity.RARE, mage.cards.d.DeadMansChest.class));
        cards.add(new SetCardInfo("Deeproot Elite", "127p", Rarity.RARE, mage.cards.d.DeeprootElite.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deeproot Elite", "127s", Rarity.RARE, mage.cards.d.DeeprootElite.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dire Fleet Daredevil", "99p", Rarity.RARE, mage.cards.d.DireFleetDaredevil.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dire Fleet Daredevil", "99s", Rarity.RARE, mage.cards.d.DireFleetDaredevil.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dire Fleet Poisoner", "68p", Rarity.RARE, mage.cards.d.DireFleetPoisoner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dire Fleet Poisoner", "68s", Rarity.RARE, mage.cards.d.DireFleetPoisoner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elenda, the Dusk Rose", "157s", Rarity.MYTHIC, mage.cards.e.ElendaTheDuskRose.class));
        cards.add(new SetCardInfo("Etali, Primal Storm", "100p", Rarity.RARE, mage.cards.e.EtaliPrimalStorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Etali, Primal Storm", "100s", Rarity.RARE, mage.cards.e.EtaliPrimalStorm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Evolving Wilds", 186, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Form of the Dinosaur", "103s", Rarity.RARE, mage.cards.f.FormOfTheDinosaur.class));
        cards.add(new SetCardInfo("Ghalta, Primal Hunger", 130, Rarity.RARE, mage.cards.g.GhaltaPrimalHunger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ghalta, Primal Hunger", "130p", Rarity.RARE, mage.cards.g.GhaltaPrimalHunger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ghalta, Primal Hunger", "130s", Rarity.RARE, mage.cards.g.GhaltaPrimalHunger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gold-Forge Garrison", "179s", Rarity.RARE, mage.cards.g.GoldForgeGarrison.class));
        cards.add(new SetCardInfo("Golden Guardian", "179s", Rarity.RARE, mage.cards.g.GoldenGuardian.class));
        cards.add(new SetCardInfo("Hadana's Climb", "158s", Rarity.RARE, mage.cards.h.HadanasClimb.class));
        cards.add(new SetCardInfo("Huatli, Radiant Champion", "159s", Rarity.MYTHIC, mage.cards.h.HuatliRadiantChampion.class));
        cards.add(new SetCardInfo("Induced Amnesia", "40s", Rarity.RARE, mage.cards.i.InducedAmnesia.class));
        cards.add(new SetCardInfo("Jadelight Ranger", "136p", Rarity.RARE, mage.cards.j.JadelightRanger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jadelight Ranger", "136s", Rarity.RARE, mage.cards.j.JadelightRanger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Journey to Eternity", "160s", Rarity.RARE, mage.cards.j.JourneyToEternity.class));
        cards.add(new SetCardInfo("Kumena's Awakening", "42s", Rarity.RARE, mage.cards.k.KumenasAwakening.class));
        cards.add(new SetCardInfo("Kumena, Tyrant of Orazca", "162s", Rarity.MYTHIC, mage.cards.k.KumenaTyrantOfOrazca.class));
        cards.add(new SetCardInfo("Mastermind's Acquisition", "77p", Rarity.RARE, mage.cards.m.MastermindsAcquisition.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mastermind's Acquisition", "77s", Rarity.RARE, mage.cards.m.MastermindsAcquisition.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Metzali, Tower of Triumph", "165s", Rarity.RARE, mage.cards.m.MetzaliTowerOfTriumph.class));
        cards.add(new SetCardInfo("Nezahal, Primal Tide", "45p", Rarity.RARE, mage.cards.n.NezahalPrimalTide.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nezahal, Primal Tide", "45s", Rarity.RARE, mage.cards.n.NezahalPrimalTide.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Paladin of Atonement", "16s", Rarity.RARE, mage.cards.p.PaladinOfAtonement.class));
        cards.add(new SetCardInfo("Path of Discovery", "142p", Rarity.RARE, mage.cards.p.PathOfDiscovery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Path of Discovery", "142s", Rarity.RARE, mage.cards.p.PathOfDiscovery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Path of Mettle", "165s", Rarity.RARE, mage.cards.p.PathOfMettle.class));
        cards.add(new SetCardInfo("Polyraptor", "144s", Rarity.MYTHIC, mage.cards.p.Polyraptor.class));
        cards.add(new SetCardInfo("Profane Procession", "166s", Rarity.RARE, mage.cards.p.ProfaneProcession.class));
        cards.add(new SetCardInfo("Protean Raider", "167s", Rarity.RARE, mage.cards.p.ProteanRaider.class));
        cards.add(new SetCardInfo("Radiant Destiny", "18p", Rarity.RARE, mage.cards.r.RadiantDestiny.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Radiant Destiny", "18s", Rarity.RARE, mage.cards.r.RadiantDestiny.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rekindling Phoenix", "111p", Rarity.MYTHIC, mage.cards.r.RekindlingPhoenix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rekindling Phoenix", "111s", Rarity.MYTHIC, mage.cards.r.RekindlingPhoenix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Release to the Wind", "46s", Rarity.RARE, mage.cards.r.ReleaseToTheWind.class));
        cards.add(new SetCardInfo("Sanctum of the Sun", "176s", Rarity.MYTHIC, mage.cards.s.SanctumOfTheSun.class));
        cards.add(new SetCardInfo("Seafloor Oracle", "51p", Rarity.RARE, mage.cards.s.SeafloorOracle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Seafloor Oracle", "51s", Rarity.RARE, mage.cards.s.SeafloorOracle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Siegehorn Ceratops", "171p", Rarity.RARE, mage.cards.s.SiegehornCeratops.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Siegehorn Ceratops", "171s", Rarity.RARE, mage.cards.s.SiegehornCeratops.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Silent Gravestone", "182p", Rarity.RARE, mage.cards.s.SilentGravestone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Silent Gravestone", "182s", Rarity.RARE, mage.cards.s.SilentGravestone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Silverclad Ferocidons", "115p", Rarity.RARE, mage.cards.s.SilvercladFerocidons.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Silverclad Ferocidons", "115s", Rarity.RARE, mage.cards.s.SilvercladFerocidons.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Silvergill Adept", 53, Rarity.UNCOMMON, mage.cards.s.SilvergillAdept.class));
        cards.add(new SetCardInfo("Slaughter the Strong", "22p", Rarity.RARE, mage.cards.s.SlaughterTheStrong.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Slaughter the Strong", "22s", Rarity.RARE, mage.cards.s.SlaughterTheStrong.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sphinx's Decree", "24s", Rarity.RARE, mage.cards.s.SphinxsDecree.class));
        cards.add(new SetCardInfo("Storm the Vault", "173s", Rarity.RARE, mage.cards.s.StormTheVault.class));
        cards.add(new SetCardInfo("Temple Altisaur", "28s", Rarity.RARE, mage.cards.t.TempleAltisaur.class));
        cards.add(new SetCardInfo("Tendershoot Dryad", "147p", Rarity.RARE, mage.cards.t.TendershootDryad.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tendershoot Dryad", "147s", Rarity.RARE, mage.cards.t.TendershootDryad.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tetzimoc, Primal Death", "86p", Rarity.RARE, mage.cards.t.TetzimocPrimalDeath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tetzimoc, Primal Death", "86s", Rarity.RARE, mage.cards.t.TetzimocPrimalDeath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Immortal Sun", "180p", Rarity.MYTHIC, mage.cards.t.TheImmortalSun.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Immortal Sun", "180s", Rarity.MYTHIC, mage.cards.t.TheImmortalSun.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tilonalli's Summoner", "121p", Rarity.RARE, mage.cards.t.TilonallisSummoner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tilonalli's Summoner", "121s", Rarity.RARE, mage.cards.t.TilonallisSummoner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Timestream Navigator", "59s", Rarity.MYTHIC, mage.cards.t.TimestreamNavigator.class));
        cards.add(new SetCardInfo("Tomb of the Dusk Rose", "166s", Rarity.RARE, mage.cards.t.TombOfTheDuskRose.class));
        cards.add(new SetCardInfo("Tomb Robber", "87s", Rarity.RARE, mage.cards.t.TombRobber.class));
        cards.add(new SetCardInfo("Trapjaw Tyrant", "29s", Rarity.MYTHIC, mage.cards.t.TrapjawTyrant.class));
        cards.add(new SetCardInfo("Twilight Prophet", "88s", Rarity.MYTHIC, mage.cards.t.TwilightProphet.class));
        cards.add(new SetCardInfo("Vault of Catlacan", "173s", Rarity.RARE, mage.cards.v.VaultOfCatlacan.class));
        cards.add(new SetCardInfo("Vona's Hunger", "90p", Rarity.RARE, mage.cards.v.VonasHunger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vona's Hunger", "90s", Rarity.RARE, mage.cards.v.VonasHunger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Warkite Marauder", "60p", Rarity.RARE, mage.cards.w.WarkiteMarauder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Warkite Marauder", "60s", Rarity.RARE, mage.cards.w.WarkiteMarauder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wayward Swordtooth", "150p", Rarity.RARE, mage.cards.w.WaywardSwordtooth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wayward Swordtooth", "150s", Rarity.RARE, mage.cards.w.WaywardSwordtooth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Winged Temple of Orazca", "158s", Rarity.RARE, mage.cards.w.WingedTempleOfOrazca.class));
        cards.add(new SetCardInfo("World Shaper", "151p", Rarity.RARE, mage.cards.w.WorldShaper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("World Shaper", "151s", Rarity.RARE, mage.cards.w.WorldShaper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zacama, Primal Calamity", "174p", Rarity.MYTHIC, mage.cards.z.ZacamaPrimalCalamity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zacama, Primal Calamity", "174s", Rarity.MYTHIC, mage.cards.z.ZacamaPrimalCalamity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zetalpa, Primal Dawn", "30p", Rarity.RARE, mage.cards.z.ZetalpaPrimalDawn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zetalpa, Primal Dawn", "30s", Rarity.RARE, mage.cards.z.ZetalpaPrimalDawn.class, NON_FULL_USE_VARIOUS));

    }
}
