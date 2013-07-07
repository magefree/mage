package mage.cards.mock;

import java.util.List;
import mage.MageInt;
import mage.abilities.Ability;
import mage.cards.SplitCard;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;

/**
 *
 * @author North
 */
public class MockSplitCard extends SplitCard<MockSplitCard> {

    public MockSplitCard(CardInfo card) {
        super(null,
                card.getCardNumber(),
                getLeftHalfName(card),
                getRightHalfName(card),
                card.getRarity(),
                card.getTypes().toArray(new CardType[0]),
                join(card.getManaCosts()),
                "",
                join(card.getRules()).contains("Fuse"));
        this.expansionSetCode = card.getSetCode();
        this.power = mageIntFromString(card.getPower());
        this.toughness = mageIntFromString(card.getToughness());
        this.cardType = card.getTypes();
        // dont copy list with empty element (would be better to eliminate earlier but I don't know how (LevelX2))
        if (card.getSubTypes().get(0).length() >0) {
            // empty element leads to added "-" after cardtype.
            this.subtype = card.getSubTypes();
        }
        if (card.getSupertypes().get(0).length() >0) {
            // empty element leads to blank before Card Type.
            this.supertype = card.getSupertypes();
        }

        this.usesVariousArt = card.usesVariousArt();

        this.color = card.getColor();
        this.splitCard = card.isSplitCard();
        this.flipCard = card.isFlipCard();

        this.canTransform = card.isDoubleFaced();
        this.nightCard = card.isNightCard();
        if (card.getSecondSideName() != null && !card.getSecondSideName().isEmpty()) {
            this.secondSideCard = new MockCard(CardRepository.instance.findCard(card.getSecondSideName()));
        }

        this.flipCardName = card.getFlipCardName();

        for (String ruleText : card.getRules()) {
            this.addAbility(textAbilityFromString(ruleText));
        }

        CardInfo leftHalf = CardRepository.instance.findCard(getLeftHalfName(card));
        if(leftHalf != null) {
            this.leftHalfCard = new MockCard(leftHalf);
        }

        CardInfo rightHalf = CardRepository.instance.findCard(getRightHalfName(card));
        if(rightHalf != null) {
            this.rightHalfCard = new MockCard(rightHalf);
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
