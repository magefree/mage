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
        cards.add(new SetCardInfo("Azorius Guildgate", 243, Rarity.COMMON, mage.cards.a.AzoriusGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Azorius Guildgate", 244, Rarity.COMMON, mage.cards.a.AzoriusGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Azorius Locket", 231, Rarity.COMMON, mage.cards.a.AzoriusLocket.class));
        cards.add(new SetCardInfo("Bedevil", 157, Rarity.RARE, mage.cards.b.Bedevil.class));
        cards.add(new SetCardInfo("Blood Crypt", 245, Rarity.RARE, mage.cards.b.BloodCrypt.class));
        cards.add(new SetCardInfo("Breeding Pool", 246, Rarity.RARE, mage.cards.b.BreedingPool.class));
        cards.add(new SetCardInfo("Deputy of Detention", 165, Rarity.RARE, mage.cards.d.DeputyOfDetention.class));
        cards.add(new SetCardInfo("Dovin, Grand Arbiter", 167, Rarity.MYTHIC, mage.cards.d.DovinGrandArbiter.class));
        cards.add(new SetCardInfo("Emergency Powers", 169, Rarity.MYTHIC, mage.cards.e.EmergencyPowers.class));
        cards.add(new SetCardInfo("Frenzied Arynx", 173, Rarity.COMMON, mage.cards.f.FrenziedArynx.class));
        cards.add(new SetCardInfo("Gate Colossus", 232, Rarity.UNCOMMON, mage.cards.g.GateColossus.class));
        cards.add(new SetCardInfo("Godless Shrine", 248, Rarity.RARE, mage.cards.g.GodlessShrine.class));
        cards.add(new SetCardInfo("Growth Spiral", 178, Rarity.COMMON, mage.cards.g.GrowthSpiral.class));
        cards.add(new SetCardInfo("Gruul Guildgate", 249, Rarity.COMMON, mage.cards.g.GruulGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gruul Guildgate", 250, Rarity.COMMON, mage.cards.g.GruulGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gruul Locket", 234, Rarity.COMMON, mage.cards.g.GruulLocket.class));
        cards.add(new SetCardInfo("Gruul Spellbreaker", 179, Rarity.RARE, mage.cards.g.GruulSpellbreaker.class));
        cards.add(new SetCardInfo("Hallowed Fountain", 251, Rarity.RARE, mage.cards.h.HallowedFountain.class));
        cards.add(new SetCardInfo("Hydroid Krasis", 183, Rarity.MYTHIC, mage.cards.h.HydroidKrasis.class));
        cards.add(new SetCardInfo("Imperious Oligarch", 184, Rarity.COMMON, mage.cards.i.ImperiousOligarch.class));
        cards.add(new SetCardInfo("Incubation // Incongruity", 226, Rarity.UNCOMMON, mage.cards.i.IncubationIncongruity.class));
        cards.add(new SetCardInfo("Judith, the Scourge Diva", 185, Rarity.RARE, mage.cards.j.JudithTheScourgeDiva.class));
        cards.add(new SetCardInfo("Light Up the Stage", 107, Rarity.UNCOMMON, mage.cards.l.LightUpTheStage.class));
        cards.add(new SetCardInfo("Mortify", 192, Rarity.UNCOMMON, mage.cards.m.Mortify.class));
        cards.add(new SetCardInfo("Orzhov Guildgate", 252, Rarity.COMMON, mage.cards.o.OrzhovGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orzhov Guildgate", 253, Rarity.COMMON, mage.cards.o.OrzhovGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orzhov Locket", 236, Rarity.COMMON, mage.cards.o.OrzhovLocket.class));
        cards.add(new SetCardInfo("Rafter Demon", 196, Rarity.COMMON, mage.cards.r.RafterDemon.class));
        cards.add(new SetCardInfo("Rakdos Firewheeler", 197, Rarity.UNCOMMON, mage.cards.r.RakdosFirewheeler.class));
        cards.add(new SetCardInfo("Rakdos Guildgate", 255, Rarity.COMMON, mage.cards.r.RakdosGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakdos Guildgate", 256, Rarity.COMMON, mage.cards.r.RakdosGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakdos Locket", 237, Rarity.COMMON, mage.cards.r.RakdosLocket.class));
        cards.add(new SetCardInfo("Rix Maadi Reveler", 109, Rarity.RARE, mage.cards.r.RixMaadiReveler.class));
        cards.add(new SetCardInfo("Simic Guildgate", 257, Rarity.COMMON, mage.cards.s.SimicGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Simic Guildgate", 258, Rarity.COMMON, mage.cards.s.SimicGuildgate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Simic Locket", 240, Rarity.COMMON, mage.cards.s.SimicLocket.class));
        cards.add(new SetCardInfo("Sphinx of Foresight", 55, Rarity.RARE, mage.cards.s.SphinxOfForesight.class));
        cards.add(new SetCardInfo("Sphinx's Insight", 209, Rarity.COMMON, mage.cards.s.SphinxsInsight.class));
        cards.add(new SetCardInfo("Stomping Ground", 259, Rarity.RARE, mage.cards.s.StompingGround.class));
        cards.add(new SetCardInfo("The Haunt of Hightower", 273, Rarity.MYTHIC, mage.cards.t.TheHauntOfHightower.class));
        cards.add(new SetCardInfo("Tithe Taker", 27, Rarity.RARE, mage.cards.t.TitheTaker.class));
        cards.add(new SetCardInfo("Zegana, Utopian Speaker", 214, Rarity.RARE, mage.cards.z.ZeganaUtopianSpeaker.class));
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
