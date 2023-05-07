package mage.game.draft;

import mage.ObjectColor;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Rarity;
import mage.util.RandomUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RemixedSet implements Serializable {

    protected final int numBoosterCommons;
    protected final int numBoosterUncommons;
    protected final int numBoosterRares;
    protected final int numBoosterSpecials;
    protected final List<CardInfo> commons;
    protected final List<CardInfo> uncommons;
    protected final List<CardInfo> rares;
    protected final List<CardInfo> mythics;
    protected final List<CardInfo> specials;
    protected final double chanceMythic;

    public RemixedSet (List<ExpansionSet> sets, int c, int u, int r) {
        this(sets, c, u, r, 0);
    }

    public RemixedSet(List<ExpansionSet> sets, int c, int u, int r, int special) {
        this.numBoosterCommons = c;
        this.numBoosterUncommons = u;
        this.numBoosterRares = r;
        this.numBoosterSpecials = special; // TODO: add support for uploading a custom list of special cards
        this.commons = new ArrayList<>();
        this.uncommons = new ArrayList<>();
        this.rares = new ArrayList<>();
        this.mythics = new ArrayList<>();
        this.specials = new ArrayList<>();
        for (ExpansionSet set : sets) {
            commons.addAll(findCardsBySetAndRarity(set, Rarity.COMMON));
            uncommons.addAll(findCardsBySetAndRarity(set, Rarity.UNCOMMON));
            rares.addAll(findCardsBySetAndRarity(set, Rarity.RARE));
            mythics.addAll(findCardsBySetAndRarity(set, Rarity.MYTHIC));
        }
        float nMythics = mythics.size();
        float nRares = rares.size();
        this.chanceMythic = (nMythics / (nMythics + nRares + nRares));
    }

    protected List<CardInfo> findCardsBySetAndRarity(ExpansionSet set, Rarity rarity) {
        List<CardInfo> cardInfos = CardRepository.instance.findCards(new CardCriteria()
                .setCodes(set.getCode())
                .rarities(rarity)
                .maxCardNumber(set.getMaxCardNumberInBooster())); // TODO: Make sure this parameter is set appropriately where needed

        cardInfos.removeIf(next -> (
                next.getCardNumber().contains("*")
                        || next.getCardNumber().contains("+")));

        return cardInfos;
    }

    public List<Card> createBooster() {
        List<Card> booster = new ArrayList<>();
        booster.addAll(generateCommons());
        booster.addAll(generateUncommons());
        booster.addAll(generateRares());
        // TODO: Generate special cards
        return booster;
    }

    protected void addToBooster(List<Card> booster, List<CardInfo> cards) {
        if (cards.isEmpty()) {
            return;
        }
        CardInfo cardInfo = cards.remove(RandomUtil.nextInt(cards.size())); // so no duplicates in a booster
        Card card = cardInfo.getCard();
        if (card == null) {
            return;
        }
        booster.add(card);
    }

    protected List<Card> generateCommons() {
        List<Card> boosterCommons = new ArrayList<>(); // will be returned once valid or max attempts reached
        for (int i = 0; i < 100; i++) { // don't want to somehow loop forever
            boosterCommons.clear();
            List<CardInfo> commonsForGenerate = new ArrayList<>(commons); // to not modify base list
            for (int j = 0; j < numBoosterCommons; j++) {
                addToBooster(boosterCommons, commonsForGenerate);
            }
            if (validateCommonColors(boosterCommons)) {
                return boosterCommons;
            }
        }
        return boosterCommons;
    }

    protected List<Card> generateUncommons() {
        List<Card> boosterUncommons = new ArrayList<>(); // will be returned once valid or max attempts reached
        for (int i = 0; i < 100; i++) { // don't want to somehow loop forever
            boosterUncommons.clear();
            List<CardInfo> uncommonsForGenerate = new ArrayList<>(uncommons); // to not modify base list
            for (int j = 0; j < numBoosterUncommons; j++) {
                addToBooster(boosterUncommons, uncommonsForGenerate);
            }
            if (validateUncommonColors(boosterUncommons)) {
                return boosterUncommons;
            }
        }
        return boosterUncommons;
    }

    protected List<Card> generateRares() {
        List<Card> boosterRares = new ArrayList<>();
        List<CardInfo> raresForGenerate = new ArrayList<>(rares);
        List<CardInfo> mythicsForGenerate = new ArrayList<>(mythics);
        for (int j = 0; j < numBoosterRares; j++) {
            if (RandomUtil.nextDouble() < chanceMythic) {
                addToBooster(boosterRares, mythicsForGenerate);
            } else {
                addToBooster(boosterRares, raresForGenerate);
            }
        }
        return boosterRares;
    }

    // See ExpansionSet for original validation logic by awjackson
    protected boolean validateCommonColors(List<Card> booster) {
        List<ObjectColor> commonColors = booster.stream()
                .filter(card -> card.getRarity() == Rarity.COMMON)
                .map(ExpansionSet::getColorForValidate)
                .collect(Collectors.toList());

        // for multicolor sets, count not just the colors present at common,
        // but also the number of color combinations (guilds/shards/wedges)
        // e.g. a booster with three UB commons, three RW commons and four G commons
        // has all five colors but isn't "balanced"
        ObjectColor colorsRepresented = new ObjectColor();
        Set<ObjectColor> colorCombinations = new HashSet<>();
        int colorlessCountPlusOne = 1;

        for (ObjectColor color : commonColors) {
            colorCombinations.add(color);
            int colorCount = color.getColorCount();
            if (colorCount == 0) {
                ++colorlessCountPlusOne;
            } else if (colorCount > 1) {
                // to prevent biasing toward multicolor over monocolor cards,
                // count them as one of their colors chosen at random
                List<ObjectColor> multiColor = color.getColors();
                colorsRepresented.addColor(multiColor.get(RandomUtil.nextInt(multiColor.size())));
            } else {
                colorsRepresented.addColor(color);
            }
        }

        int colors = Math.min(colorsRepresented.getColorCount(), colorCombinations.size());

        // if booster has all five colors in five unique combinations, or if it has
        // one card per color and all but one of the rest are colorless, accept it
        // ("all but one" adds some leeway for sets with small boosters)
        if (colors >= Math.min(5, commonColors.size() - colorlessCountPlusOne)) return true;
        // otherwise, if booster is missing more than one color, reject it
        if (colors < 4) return false;
        // otherwise, stochastically treat each colorless card as 1/5 of a card of the missing color
        return (RandomUtil.nextDouble() > Math.pow(0.8, colorlessCountPlusOne));
    }

    protected boolean validateUncommonColors(List<Card> booster) {
        List<ObjectColor> uncommonColors = booster.stream()
                .filter(card -> card.getRarity() == Rarity.UNCOMMON)
                .map(ExpansionSet::getColorForValidate)
                .collect(Collectors.toList());

        // if there are only two uncommons, they can be the same color
        if (uncommonColors.size() < 3) return true;
        // boosters of artifact sets can have all colorless uncommons
        if (uncommonColors.contains(ObjectColor.COLORLESS)) return true;
        // otherwise, reject if all uncommons are the same color combination
        return (new HashSet<>(uncommonColors).size() > 1);
    }

}
