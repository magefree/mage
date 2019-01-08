package mage.sets;

import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

public final class RavnicaAllegiance extends ExpansionSet {

    private static final RavnicaAllegiance instance = new RavnicaAllegiance();

    public static RavnicaAllegiance getInstance() {
        return instance;
    }

    private RavnicaAllegiance() {
        super("Ravnica Allegiance", "RNA", ExpansionSet.buildDate(2019, 1, 25), SetType.EXPANSION);
        this.blockName = "Guilds of Ravnica";
        this.hasBoosters = true;
        this.hasBasicLands = false; // this is temporary until the actual basics have been spoiled to prevent a test fail
        this.numBoosterSpecial = 1;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 259;

        cards.add(new SetCardInfo("Absorb", 151, Rarity.RARE, mage.cards.a.Absorb.class));
        cards.add(new SetCardInfo("Aeromunculus", 152, Rarity.COMMON, mage.cards.a.Aeromunculus.class));
        cards.add(new SetCardInfo("Amplifire", 92, Rarity.RARE, mage.cards.a.Amplifire.class));
        cards.add(new SetCardInfo("Angelic Exaltation", 2, Rarity.UNCOMMON, mage.cards.a.AngelicExaltation.class));
        cards.add(new SetCardInfo("Azorius Guildgate", 243, Rarity.COMMON, mage.cards.a.AzoriusGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Azorius Guildgate", 244, Rarity.COMMON, mage.cards.a.AzoriusGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Azorius Locket", 231, Rarity.COMMON, mage.cards.a.AzoriusLocket.class));
        cards.add(new SetCardInfo("Bankrupt in Blood", 62, Rarity.UNCOMMON, mage.cards.b.BankruptInBlood.class));
        cards.add(new SetCardInfo("Basilica Bell-Haunt", 156, Rarity.UNCOMMON, mage.cards.b.BasilicaBellHaunt.class));
        cards.add(new SetCardInfo("Bedevil", 157, Rarity.RARE, mage.cards.b.Bedevil.class));
        cards.add(new SetCardInfo("Biogenic Upgrade", 123, Rarity.UNCOMMON, mage.cards.b.BiogenicUpgrade.class));
        cards.add(new SetCardInfo("Biomancer's Familiar", 158, Rarity.RARE, mage.cards.b.BiomancersFamiliar.class));
        cards.add(new SetCardInfo("Blade Juggler", 63, Rarity.COMMON, mage.cards.b.BladeJuggler.class));
        cards.add(new SetCardInfo("Blood Crypt", 245, Rarity.RARE, mage.cards.b.BloodCrypt.class));
        cards.add(new SetCardInfo("Bolrac-Clan Crusher", 159, Rarity.UNCOMMON, mage.cards.b.BolracClanCrusher.class));
        cards.add(new SetCardInfo("Breeding Pool", 246, Rarity.RARE, mage.cards.b.BreedingPool.class));
        cards.add(new SetCardInfo("Burn Bright", 93, Rarity.COMMON, mage.cards.b.BurnBright.class));
        cards.add(new SetCardInfo("Burning-Tree Vandal", 94, Rarity.COMMON, mage.cards.b.BurningTreeVandal.class));
        cards.add(new SetCardInfo("Carnival // Carnage", 222, Rarity.UNCOMMON, mage.cards.c.CarnivalCarnage.class));
        cards.add(new SetCardInfo("Combine Guildmage", 163, Rarity.UNCOMMON, mage.cards.c.CombineGuildmage.class));
        cards.add(new SetCardInfo("Consecrate // Consume", 224, Rarity.UNCOMMON, mage.cards.c.ConsecrateConsume.class));
        cards.add(new SetCardInfo("Cry of the Carnarium", 70, Rarity.UNCOMMON, mage.cards.c.CryOfTheCarnarium.class));
        cards.add(new SetCardInfo("Depose // Deploy", 225, Rarity.UNCOMMON, mage.cards.d.DeposeDeploy.class));
        cards.add(new SetCardInfo("Deputy of Detention", 165, Rarity.RARE, mage.cards.d.DeputyOfDetention.class));
        cards.add(new SetCardInfo("Domri, Chaos Bringer", 166, Rarity.MYTHIC, mage.cards.d.DomriChaosBringer.class));
        cards.add(new SetCardInfo("Dovin, Grand Arbiter", 167, Rarity.MYTHIC, mage.cards.d.DovinGrandArbiter.class));
        cards.add(new SetCardInfo("Drill Bit", 73, Rarity.UNCOMMON, mage.cards.d.DrillBit.class));
        cards.add(new SetCardInfo("Electrodominance", 99, Rarity.RARE, mage.cards.e.Electrodominance.class));
        cards.add(new SetCardInfo("Emergency Powers", 169, Rarity.MYTHIC, mage.cards.e.EmergencyPowers.class));
        cards.add(new SetCardInfo("End-Raze Forerunners", 124, Rarity.RARE, mage.cards.e.EndRazeForerunners.class));
        cards.add(new SetCardInfo("Frenzied Arynx", 173, Rarity.COMMON, mage.cards.f.FrenziedArynx.class));
        cards.add(new SetCardInfo("Frilled Mystic", 174, Rarity.UNCOMMON, mage.cards.f.FrilledMystic.class));
        cards.add(new SetCardInfo("Gate Colossus", 232, Rarity.UNCOMMON, mage.cards.g.GateColossus.class));
        cards.add(new SetCardInfo("Godless Shrine", 248, Rarity.RARE, mage.cards.g.GodlessShrine.class));
        cards.add(new SetCardInfo("Grasping Thrull", 177, Rarity.COMMON, mage.cards.g.GraspingThrull.class));
        cards.add(new SetCardInfo("Growth Spiral", 178, Rarity.COMMON, mage.cards.g.GrowthSpiral.class));
        cards.add(new SetCardInfo("Growth-Chamber Guardian", 128, Rarity.RARE, mage.cards.g.GrowthChamberGuardian.class));
        cards.add(new SetCardInfo("Gruul Beastmaster", 129, Rarity.UNCOMMON, mage.cards.g.GruulBeastmaster.class));
        cards.add(new SetCardInfo("Gruul Guildgate", 249, Rarity.COMMON, mage.cards.g.GruulGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gruul Guildgate", 250, Rarity.COMMON, mage.cards.g.GruulGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gruul Locket", 234, Rarity.COMMON, mage.cards.g.GruulLocket.class));
        cards.add(new SetCardInfo("Gruul Spellbreaker", 179, Rarity.RARE, mage.cards.g.GruulSpellbreaker.class));
        cards.add(new SetCardInfo("Guardian Project", 130, Rarity.RARE, mage.cards.g.GuardianProject.class));
        cards.add(new SetCardInfo("Gutterbones", 76, Rarity.RARE, mage.cards.g.Gutterbones.class));
        cards.add(new SetCardInfo("Hackrobat", 181, Rarity.UNCOMMON, mage.cards.h.Hackrobat.class));
        cards.add(new SetCardInfo("Hallowed Fountain", 251, Rarity.RARE, mage.cards.h.HallowedFountain.class));
        cards.add(new SetCardInfo("High Alert", 182, Rarity.UNCOMMON, mage.cards.h.HighAlert.class));
        cards.add(new SetCardInfo("Humongulus", 41, Rarity.COMMON, mage.cards.h.Humongulus.class));
        cards.add(new SetCardInfo("Hydroid Krasis", 183, Rarity.MYTHIC, mage.cards.h.HydroidKrasis.class));
        cards.add(new SetCardInfo("Imperious Oligarch", 184, Rarity.COMMON, mage.cards.i.ImperiousOligarch.class));
        cards.add(new SetCardInfo("Incubation // Incongruity", 226, Rarity.UNCOMMON, mage.cards.i.IncubationIncongruity.class));
        cards.add(new SetCardInfo("Judith, the Scourge Diva", 185, Rarity.RARE, mage.cards.j.JudithTheScourgeDiva.class));
        cards.add(new SetCardInfo("Kaya's Wrath", 187, Rarity.RARE, mage.cards.k.KayasWrath.class));
        cards.add(new SetCardInfo("Kaya, Orzhov Usurper", 186, Rarity.MYTHIC, mage.cards.k.KayaOrzhovUsurper.class));
        cards.add(new SetCardInfo("Lavinia, Azorius Renegade", 189, Rarity.RARE, mage.cards.l.LaviniaAzoriusRenegade.class));
        cards.add(new SetCardInfo("Light Up the Stage", 107, Rarity.UNCOMMON, mage.cards.l.LightUpTheStage.class));
        cards.add(new SetCardInfo("Mass Manipulation", 42, Rarity.RARE, mage.cards.m.MassManipulation.class));
        cards.add(new SetCardInfo("Mesmerizing Benthid", 43, Rarity.MYTHIC, mage.cards.m.MesmerizingBenthid.class));
        cards.add(new SetCardInfo("Ministrant of Obligation", 16, Rarity.UNCOMMON, mage.cards.m.MinistrantOfObligation.class));
        cards.add(new SetCardInfo("Mortify", 192, Rarity.UNCOMMON, mage.cards.m.Mortify.class));
        cards.add(new SetCardInfo("Nikya of the Old Ways", 193, Rarity.RARE, mage.cards.n.NikyaOfTheOldWays.class));
        cards.add(new SetCardInfo("Orzhov Guildgate", 252, Rarity.COMMON, mage.cards.o.OrzhovGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orzhov Guildgate", 253, Rarity.COMMON, mage.cards.o.OrzhovGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orzhov Locket", 236, Rarity.COMMON, mage.cards.o.OrzhovLocket.class));
        cards.add(new SetCardInfo("Orzhov Racketeers", 80, Rarity.UNCOMMON, mage.cards.o.OrzhovRacketeers.class));
        cards.add(new SetCardInfo("Pestilent Spirit", 81, Rarity.RARE, mage.cards.p.PestilentSpirit.class));
        cards.add(new SetCardInfo("Pitiless Pontiff", 194, Rarity.UNCOMMON, mage.cards.p.PitilessPontiff.class));
        cards.add(new SetCardInfo("Precognitive Perception", 45, Rarity.RARE, mage.cards.p.PrecognitivePerception.class));
        cards.add(new SetCardInfo("Prime Speaker Vannifar", 195, Rarity.MYTHIC, mage.cards.p.PrimeSpeakerVannifar.class));
        cards.add(new SetCardInfo("Quench", 48, Rarity.COMMON, mage.cards.q.Quench.class));
        cards.add(new SetCardInfo("Rafter Demon", 196, Rarity.COMMON, mage.cards.r.RafterDemon.class));
        cards.add(new SetCardInfo("Rakdos Firewheeler", 197, Rarity.UNCOMMON, mage.cards.r.RakdosFirewheeler.class));
        cards.add(new SetCardInfo("Rakdos Guildgate", 255, Rarity.COMMON, mage.cards.r.RakdosGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakdos Guildgate", 256, Rarity.COMMON, mage.cards.r.RakdosGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakdos Locket", 237, Rarity.COMMON, mage.cards.r.RakdosLocket.class));
        cards.add(new SetCardInfo("Rakdos Roustabout", 198, Rarity.COMMON, mage.cards.r.RakdosRoustabout.class));
        cards.add(new SetCardInfo("Rakdos, the Showstopper", 199, Rarity.MYTHIC, mage.cards.r.RakdosTheShowstopper.class));
        cards.add(new SetCardInfo("Rampage of the Clans", 134, Rarity.RARE, mage.cards.r.RampageOfTheClans.class));
        cards.add(new SetCardInfo("Ravager Wurm", 200, Rarity.MYTHIC, mage.cards.r.RavagerWurm.class));
        cards.add(new SetCardInfo("Rhythm of the Wild", 201, Rarity.UNCOMMON, mage.cards.r.RhythmOfTheWild.class));
        cards.add(new SetCardInfo("Rix Maadi Reveler", 109, Rarity.RARE, mage.cards.r.RixMaadiReveler.class));
        cards.add(new SetCardInfo("Sauroform Hybrid", 140, Rarity.COMMON, mage.cards.s.SauroformHybrid.class));
        cards.add(new SetCardInfo("Savage Smash", 203, Rarity.COMMON, mage.cards.s.SavageSmash.class));
        cards.add(new SetCardInfo("Seraph of the Scales", 205, Rarity.MYTHIC, mage.cards.s.SeraphOfTheScales.class));
        cards.add(new SetCardInfo("Silhana Wayfinder", 141, Rarity.UNCOMMON, mage.cards.s.SilhanaWayfinder.class));
        cards.add(new SetCardInfo("Simic Ascendancy", 207, Rarity.RARE, mage.cards.s.SimicAscendancy.class));
        cards.add(new SetCardInfo("Simic Guildgate", 257, Rarity.COMMON, mage.cards.s.SimicGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Simic Guildgate", 258, Rarity.COMMON, mage.cards.s.SimicGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Simic Locket", 240, Rarity.COMMON, mage.cards.s.SimicLocket.class));
        cards.add(new SetCardInfo("Skarrgan Hellkite", 114, Rarity.MYTHIC, mage.cards.s.SkarrganHellkite.class));
        cards.add(new SetCardInfo("Skatewing Spy", 52, Rarity.UNCOMMON, mage.cards.s.SkatewingSpy.class));
        cards.add(new SetCardInfo("Smelt-Ward Ignus", 116, Rarity.UNCOMMON, mage.cards.s.SmeltWardIgnus.class));
        cards.add(new SetCardInfo("Smothering Tithe", 22, Rarity.RARE, mage.cards.s.SmotheringTithe.class));
        cards.add(new SetCardInfo("Spawn of Mayhem", 85, Rarity.MYTHIC, mage.cards.s.SpawnOfMayhem.class));
        cards.add(new SetCardInfo("Sphinx of Foresight", 55, Rarity.RARE, mage.cards.s.SphinxOfForesight.class));
        cards.add(new SetCardInfo("Sphinx of New Prahv", 208, Rarity.UNCOMMON, mage.cards.s.SphinxOfNewPrahv.class));
        cards.add(new SetCardInfo("Sphinx's Insight", 209, Rarity.COMMON, mage.cards.s.SphinxsInsight.class));
        cards.add(new SetCardInfo("Stomping Ground", 259, Rarity.RARE, mage.cards.s.StompingGround.class));
        cards.add(new SetCardInfo("Syndicate Guildmage", 211, Rarity.UNCOMMON, mage.cards.s.SyndicateGuildmage.class));
        cards.add(new SetCardInfo("Teysa Karlov", 212, Rarity.RARE, mage.cards.t.TeysaKarlov.class));
        cards.add(new SetCardInfo("The Haunt of Hightower", 273, Rarity.MYTHIC, mage.cards.t.TheHauntOfHightower.class));
        cards.add(new SetCardInfo("Titanic Brawl", 146, Rarity.COMMON, mage.cards.t.TitanicBrawl.class));
        cards.add(new SetCardInfo("Tithe Taker", 27, Rarity.RARE, mage.cards.t.TitheTaker.class));
        cards.add(new SetCardInfo("Trollbred Guardian", 148, Rarity.UNCOMMON, mage.cards.t.TrollbredGuardian.class));
        cards.add(new SetCardInfo("Verity Circle", 58, Rarity.RARE, mage.cards.v.VerityCircle.class));
        cards.add(new SetCardInfo("Wilderness Reclamation", 149, Rarity.UNCOMMON, mage.cards.w.WildernessReclamation.class));
        cards.add(new SetCardInfo("Zegana, Utopian Speaker", 214, Rarity.RARE, mage.cards.z.ZeganaUtopianSpeaker.class));
        cards.add(new SetCardInfo("Zhur-Taa Goblin", 215, Rarity.UNCOMMON, mage.cards.z.ZhurTaaGoblin.class));
    }

    @Override
    public List<CardInfo> getCardsByRarity(Rarity rarity) {
        if (rarity == Rarity.COMMON) {
            List<CardInfo> savedCardsInfos = savedCards.get(rarity);
            if (savedCardsInfos == null) {
                CardCriteria criteria = new CardCriteria();
                criteria.setCodes(this.code).notTypes(CardType.LAND);
                criteria.rarities(rarity).doubleFaced(false);
                savedCardsInfos = CardRepository.instance.findCards(criteria);
                if (maxCardNumberInBooster != Integer.MAX_VALUE) {
                    savedCardsInfos.removeIf(next -> next.getCardNumberAsInt() > maxCardNumberInBooster);
                }
                savedCards.put(rarity, savedCardsInfos);
            }
            // Return a copy of the saved cards information, as not to modify the original.
            return new ArrayList<>(savedCardsInfos);
        } else {
            return super.getCardsByRarity(rarity);
        }
    }

    @Override
    public List<CardInfo> getSpecialCommon() {
        List<CardInfo> specialCards = getCardsByRarity(Rarity.SPECIAL);
        if (specialCards.isEmpty()) {
            CardCriteria criteria = new CardCriteria();
            criteria.rarities(Rarity.COMMON).setCodes(this.code).name("Guildgate");
            List<CardInfo> specialCardsSave = CardRepository.instance.findCards(criteria);
            savedCards.put(Rarity.SPECIAL, specialCardsSave);
            specialCards.addAll(specialCardsSave);
        }
        return specialCards;
    }
}
