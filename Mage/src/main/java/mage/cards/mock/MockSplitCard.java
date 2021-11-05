package mage.cards.mock;

import mage.MageInt;
import mage.abilities.Ability;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.cards.SplitCardHalf;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;

import java.util.List;

/**
 * @author North
 */
public class MockSplitCard extends SplitCard {

    public MockSplitCard(CardInfo card) {
        super(null, new CardSetInfo(card.getName(), card.getSetCode(), card.getCardNumber(), card.getRarity()),
                card.getTypes().toArray(new CardType[0]),
                join(card.getManaCosts(CardInfo.ManaCostSide.LEFT)),
                join(card.getManaCosts(CardInfo.ManaCostSide.RIGHT)),
                getSpellAbilityType(card));
        this.expansionSetCode = card.getSetCode();
        this.power = mageIntFromString(card.getPower());
        this.toughness = mageIntFromString(card.getToughness());
        this.cardType = card.getTypes();
        this.subtype = card.getSubTypes();
        this.supertype = card.getSupertypes();

        this.frameColor = card.getFrameColor();
        this.frameStyle = card.getFrameStyle();
        this.usesVariousArt = card.usesVariousArt();

        this.color = card.getColor();
        this.flipCard = card.isFlipCard();

        this.nightCard = card.isNightCard();
        if (card.getSecondSideName() != null && !card.getSecondSideName().isEmpty()) {
            this.secondSideCard = new MockCard(CardRepository.instance.findCardWPreferredSet(card.getSecondSideName(), card.getSetCode(), false));
        }

        this.flipCardName = card.getFlipCardName();

        for (String ruleText : card.getRules()) {
            this.addAbility(textAbilityFromString(ruleText));
        }

        CardInfo leftHalf = CardRepository.instance.findCardWPreferredSet(getLeftHalfName(card), card.getSetCode(), false);
        if (leftHalf != null) {
            this.leftHalfCard = new MockSplitCardHalf(leftHalf);
            ((SplitCardHalf) this.leftHalfCard).setParentCard(this);
        }

        CardInfo rightHalf = CardRepository.instance.findCardWPreferredSet(getRightHalfName(card), card.getSetCode(), false);
        if (rightHalf != null) {
            this.rightHalfCard = new MockSplitCardHalf(rightHalf);
            ((SplitCardHalf) this.rightHalfCard).setParentCard(this);
        }
    }

    public MockSplitCard(final MockSplitCard card) {
        super(card);
    }

    @Override
    public MockSplitCard copy() {
        return new MockSplitCard(this);
    }

    private MageInt mageIntFromString(String value) {
        try {
            int intValue = Integer.parseInt(value);
            return new MageInt(intValue);
        } catch (NumberFormatException e) {
            return new MageInt(0, value);
        }
    }

    private static SpellAbilityType getSpellAbilityType(CardInfo cardInfo) {
        if (cardInfo.isSplitFuseCard()) {
            return SpellAbilityType.SPLIT_FUSED;
        }
        if (cardInfo.isSplitAftermathCard()) {
            return SpellAbilityType.SPLIT_AFTERMATH;
        }
        return SpellAbilityType.SPLIT;
    }

    private static String join(List<String> strings) {
        StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            sb.append(string);
        }
        return sb.toString();
    }

    private Ability textAbilityFromString(final String text) {
        return new MockAbility(text);
    }

    private static String getLeftHalfName(CardInfo card) {
        return card.getName().split(" // ")[0];
    }

    private static String getRightHalfName(CardInfo card) {
        return card.getName().split(" // ")[1];
    }
}
