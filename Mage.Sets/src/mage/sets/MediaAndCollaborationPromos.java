package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pmei
 */
public class MediaAndCollaborationPromos extends ExpansionSet {

    private static final MediaAndCollaborationPromos instance = new MediaAndCollaborationPromos();

    public static MediaAndCollaborationPromos getInstance() {
        return instance;
    }

    private MediaAndCollaborationPromos() {
        super("Media and Collaboration Promos", "PMEI", ExpansionSet.buildDate(2020, 3, 26), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        // some cards are non-English (most are Japanese, Jamuraan Lion has a German printing), but it's ok - scryfall can download it

        cards.add(new SetCardInfo("Ajani, Mentor of Heroes", "2024-3", Rarity.MYTHIC, mage.cards.a.AjaniMentorOfHeroes.class));
        cards.add(new SetCardInfo("Ancestral Mask", "2025-2", Rarity.RARE, mage.cards.a.AncestralMask.class));
        cards.add(new SetCardInfo("Anti-Venom, Horrifying Healer", "2025-15", Rarity.MYTHIC, mage.cards.a.AntiVenomHorrifyingHealer.class));
        cards.add(new SetCardInfo("Archangel", "2000-4", Rarity.RARE, mage.cards.a.Archangel.class, RETRO_ART));
        cards.add(new SetCardInfo("Ascendant Evincar", "2000-7", Rarity.RARE, mage.cards.a.AscendantEvincar.class, RETRO_ART));
        cards.add(new SetCardInfo("Avalanche Riders", "2023-5", Rarity.RARE, mage.cards.a.AvalancheRiders.class));
        cards.add(new SetCardInfo("Blue Elemental Blast", "1995-2", Rarity.COMMON, mage.cards.b.BlueElementalBlast.class, RETRO_ART));
        cards.add(new SetCardInfo("Bone Shredder", "2021-2", Rarity.RARE, mage.cards.b.BoneShredder.class));
        cards.add(new SetCardInfo("Cast Down", "2019-1", Rarity.UNCOMMON, mage.cards.c.CastDown.class));
        cards.add(new SetCardInfo("Chandra's Outrage", "2010-3", Rarity.COMMON, mage.cards.c.ChandrasOutrage.class));
        cards.add(new SetCardInfo("Chandra's Spitfire", "2010-4", Rarity.UNCOMMON, mage.cards.c.ChandrasSpitfire.class));
        cards.add(new SetCardInfo("Chrome Host Seedshark", "2025-20", Rarity.RARE, mage.cards.c.ChromeHostSeedshark.class));
        cards.add(new SetCardInfo("Cloud, Midgar Mercenary", "2025-21", Rarity.MYTHIC, mage.cards.c.CloudMidgarMercenary.class));
        cards.add(new SetCardInfo("Cloud, Planet's Champion", "2025-13", Rarity.MYTHIC, mage.cards.c.CloudPlanetsChampion.class));
        cards.add(new SetCardInfo("Counterspell", "2021-1", Rarity.RARE, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Crop Rotation", "2020-7", Rarity.RARE, mage.cards.c.CropRotation.class));
        cards.add(new SetCardInfo("Culling the Weak", "2023-8", Rarity.RARE, mage.cards.c.CullingTheWeak.class));
        cards.add(new SetCardInfo("Cunning Sparkmage", "2010-2", Rarity.UNCOMMON, mage.cards.c.CunningSparkmage.class));
        cards.add(new SetCardInfo("Dark Ritual", "2020-4", Rarity.RARE, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Darksteel Juggernaut", "2010-1", Rarity.RARE, mage.cards.d.DarksteelJuggernaut.class));
        cards.add(new SetCardInfo("Daxos, Blessed by the Sun", "2020-2", Rarity.UNCOMMON, mage.cards.d.DaxosBlessedByTheSun.class));
        cards.add(new SetCardInfo("Diabolic Edict", "2019-2", Rarity.RARE, mage.cards.d.DiabolicEdict.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Diabolic Edict", "2024-5", Rarity.RARE, mage.cards.d.DiabolicEdict.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Disenchant", "2022-1", Rarity.RARE, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Duress", "2019-6", Rarity.RARE, mage.cards.d.Duress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Duress", "2025-7", Rarity.RARE, mage.cards.d.Duress.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fireball", "1995-1", Rarity.COMMON, mage.cards.f.Fireball.class, RETRO_ART));
        cards.add(new SetCardInfo("Frantic Search", "2022-4", Rarity.RARE, mage.cards.f.FranticSearch.class));
        cards.add(new SetCardInfo("Gingerbrute", "2023-3", Rarity.RARE, mage.cards.g.Gingerbrute.class, RETRO_ART));
        cards.add(new SetCardInfo("Gush", "2024-4", Rarity.RARE, mage.cards.g.Gush.class));
        cards.add(new SetCardInfo("Harald, King of Skemfar", "2021-3", Rarity.RARE, mage.cards.h.HaraldKingOfSkemfar.class));
        cards.add(new SetCardInfo("Heliod's Pilgrim", "2020-6", Rarity.RARE, mage.cards.h.HeliodsPilgrim.class));
        cards.add(new SetCardInfo("Huntmaster of the Fells", "2025-17", Rarity.RARE, mage.cards.h.HuntmasterOfTheFells.class));
        cards.add(new SetCardInfo("Hypnotic Sprite", "2019-5", Rarity.RARE, mage.cards.h.HypnoticSprite.class));
        cards.add(new SetCardInfo("Iron Spider, Stark Upgrade", "2025-18", Rarity.RARE, mage.cards.i.IronSpiderStarkUpgrade.class));
        cards.add(new SetCardInfo("Jace Beleren", "2009-1", Rarity.MYTHIC, mage.cards.j.JaceBeleren.class));
        cards.add(new SetCardInfo("Jace, Memory Adept", "2024-2", Rarity.MYTHIC, mage.cards.j.JaceMemoryAdept.class));
        cards.add(new SetCardInfo("Jamuraan Lion", "1996-3", Rarity.COMMON, mage.cards.j.JamuraanLion.class, RETRO_ART));
        cards.add(new SetCardInfo("Kaalia of the Vast", "2025-19", Rarity.MYTHIC, mage.cards.k.KaaliaOfTheVast.class));
        cards.add(new SetCardInfo("Kuldotha Phoenix", "2010-5", Rarity.RARE, mage.cards.k.KuldothaPhoenix.class));
        cards.add(new SetCardInfo("Lava Coil", "2019-4", Rarity.UNCOMMON, mage.cards.l.LavaCoil.class));
        cards.add(new SetCardInfo("Lightning Hounds", "2000-1", Rarity.COMMON, mage.cards.l.LightningHounds.class, RETRO_ART));
        cards.add(new SetCardInfo("Lightning, Security Sergeant", "2025-12", Rarity.RARE, mage.cards.l.LightningSecuritySergeant.class));
        cards.add(new SetCardInfo("Liliana of the Dark Realms", "2024-8", Rarity.MYTHIC, mage.cards.l.LilianaOfTheDarkRealms.class));
        cards.add(new SetCardInfo("Mental Misstep", "2023-1", Rarity.RARE, mage.cards.m.MentalMisstep.class));
        cards.add(new SetCardInfo("Nicol Bolas, Planeswalker", "2025-10", Rarity.MYTHIC, mage.cards.n.NicolBolasPlaneswalker.class));
        cards.add(new SetCardInfo("Parallax Dementia", "2000-6", Rarity.COMMON, mage.cards.p.ParallaxDementia.class, RETRO_ART));
        cards.add(new SetCardInfo("Patchwork Banner", "2024-7", Rarity.RARE, mage.cards.p.PatchworkBanner.class));
        cards.add(new SetCardInfo("Phantasmal Dragon", "2011-1", Rarity.UNCOMMON, mage.cards.p.PhantasmalDragon.class));
        cards.add(new SetCardInfo("Phyrexian Rager", "2000-5", Rarity.COMMON, mage.cards.p.PhyrexianRager.class, RETRO_ART));
        cards.add(new SetCardInfo("Pyromancer's Gauntlet", "2023-6", Rarity.RARE, mage.cards.p.PyromancersGauntlet.class, RETRO_ART));
        cards.add(new SetCardInfo("Ravager of the Fells", "2025-17", Rarity.RARE, mage.cards.r.RavagerOfTheFells.class));
        cards.add(new SetCardInfo("Ruin Crab", "2023-4", Rarity.RARE, mage.cards.r.RuinCrab.class, RETRO_ART));
        cards.add(new SetCardInfo("Sandbar Crocodile", "1996-1", Rarity.COMMON, mage.cards.s.SandbarCrocodile.class, RETRO_ART));
        cards.add(new SetCardInfo("Scent of Cinder", "1999-1", Rarity.COMMON, mage.cards.s.ScentOfCinder.class, RETRO_ART));
        cards.add(new SetCardInfo("Sephiroth, Planet's Heir", "2025-14", Rarity.MYTHIC, mage.cards.s.SephirothPlanetsHeir.class));
        cards.add(new SetCardInfo("Shield Wall", "1997-3", Rarity.COMMON, mage.cards.s.ShieldWall.class, RETRO_ART));
        cards.add(new SetCardInfo("Shivan Dragon", "2001-2", Rarity.RARE, mage.cards.s.ShivanDragon.class, RETRO_ART));
        cards.add(new SetCardInfo("Shock", "2019-3", Rarity.RARE, mage.cards.s.Shock.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shock", "2025-1", Rarity.RARE, mage.cards.s.Shock.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shrieking Drake", "1997-2", Rarity.COMMON, mage.cards.s.ShriekingDrake.class, RETRO_ART));
        cards.add(new SetCardInfo("Silver Drake", "2000-2", Rarity.COMMON, mage.cards.s.SilverDrake.class, RETRO_ART));
        cards.add(new SetCardInfo("Snuff Out", "2024-1", Rarity.RARE, mage.cards.s.SnuffOut.class));
        cards.add(new SetCardInfo("Spectacular Spider-Man", "2025-16", Rarity.RARE, mage.cards.s.SpectacularSpiderMan.class));
        cards.add(new SetCardInfo("Spined Wurm", "2001-1", Rarity.COMMON, mage.cards.s.SpinedWurm.class, RETRO_ART));
        cards.add(new SetCardInfo("Sprite Dragon", "2020-5", Rarity.RARE, mage.cards.s.SpriteDragon.class));
        cards.add(new SetCardInfo("Staggering Insight", "2020-3", Rarity.RARE, mage.cards.s.StaggeringInsight.class));
        cards.add(new SetCardInfo("Stream of Life", "1997-4", Rarity.COMMON, mage.cards.s.StreamOfLife.class, RETRO_ART));
        cards.add(new SetCardInfo("Talruum Champion", "1997-1", Rarity.COMMON, mage.cards.t.TalruumChampion.class, RETRO_ART));
        cards.add(new SetCardInfo("Tangled Florahedron", "2020-8", Rarity.UNCOMMON, mage.cards.t.TangledFlorahedron.class));
        cards.add(new SetCardInfo("Thorn Elemental", "2000-3", Rarity.RARE, mage.cards.t.ThornElemental.class, RETRO_ART));
        cards.add(new SetCardInfo("Ultimecia, Temporal Threat", "2025-11", Rarity.RARE, mage.cards.u.UltimeciaTemporalThreat.class));
        cards.add(new SetCardInfo("Usher of the Fallen", "2022-3", Rarity.RARE, mage.cards.u.UsherOfTheFallen.class));
        cards.add(new SetCardInfo("Voltaic Key", "2020-1", Rarity.RARE, mage.cards.v.VoltaicKey.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Voltaic Key", "2024-6", Rarity.RARE, mage.cards.v.VoltaicKey.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Warmonger", "1999-2", Rarity.UNCOMMON, mage.cards.w.Warmonger.class, RETRO_ART));
        cards.add(new SetCardInfo("Wild Growth", "2022-2", Rarity.RARE, mage.cards.w.WildGrowth.class));
        cards.add(new SetCardInfo("Winged Boots", "2023-7", Rarity.RARE, mage.cards.w.WingedBoots.class));
        cards.add(new SetCardInfo("Worn Powerstone", "2023-2", Rarity.RARE, mage.cards.w.WornPowerstone.class));
        cards.add(new SetCardInfo("Wrath of God", "2025-3", Rarity.RARE, mage.cards.w.WrathOfGod.class));
        cards.add(new SetCardInfo("Zhalfirin Knight", "1996-2", Rarity.COMMON, mage.cards.z.ZhalfirinKnight.class, RETRO_ART));
    }
}
