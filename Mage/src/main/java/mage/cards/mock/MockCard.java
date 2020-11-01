package mage.cards.mock;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.CardImpl;
import mage.cards.ModalDoubleFacesCard;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Mock card for GUI (deck editor and panels)
 *
 * @author North
 */
public class MockCard extends CardImpl {

    static public String ADVENTURE_NAME_SEPARATOR = " // ";
    static public String MODAL_DOUBLE_FACES_NAME_SEPARATOR = " // ";

    // Needs to be here, as it is normally calculated from the
    // PlaneswalkerEntersWithLoyaltyAbility of the card... but the MockCard
    // only has MockAbilities.
    private int startingLoyalty;

    // mana cost extra info for multiple mana drawing
    protected ManaCosts<ManaCost> manaCostLeft;
    protected ManaCosts<ManaCost> manaCostRight;
    protected String adventureSpellName;
    protected boolean isModalDoubleFacesCard;

    public MockCard(CardInfo card) {
        super(null, card.getName());
        this.cardNumber = card.getCardNumber();
        this.expansionSetCode = card.getSetCode();
        this.power = mageIntFromString(card.getPower());
        this.toughness = mageIntFromString(card.getToughness());
        this.rarity = card.getRarity();
        this.cardType = card.getTypes();
        this.subtype = card.getSubTypes();
        this.supertype = card.getSupertypes();

        this.usesVariousArt = card.usesVariousArt();

        this.manaCostLeft = new ManaCostsImpl(join(card.getManaCosts(CardInfo.ManaCostSide.LEFT)));
        this.manaCostRight = new ManaCostsImpl(join(card.getManaCosts(CardInfo.ManaCostSide.RIGHT)));
        this.manaCost = new ManaCostsImpl(join(card.getManaCosts(CardInfo.ManaCostSide.ALL)));

        this.color = card.getColor();

        this.frameColor = card.getFrameColor();
        this.frameStyle = card.getFrameStyle();

        this.flipCard = card.isFlipCard();

        this.transformable = card.isDoubleFaced();
        this.nightCard = card.isNightCard();
        if (card.getSecondSideName() != null && !card.getSecondSideName().isEmpty()) {
            this.secondSideCard = new MockCard(CardRepository.instance.findCardWPreferredSet(card.getSecondSideName(), card.getSetCode(), false));
        }

        if (card.isAdventureCard()) {
            this.adventureSpellName = card.getAdventureSpellName();
        }

        if (card.isModalDoubleFacesCard()) {
            ModalDoubleFacesCard mdfCard = (ModalDoubleFacesCard) card.getCard();
            CardInfo mdfSecondSide = new CardInfo(mdfCard.getRightHalfCard());
            this.secondSideCard = new MockCard(mdfSecondSide);
            this.isModalDoubleFacesCard = true;
        }

        if (this.isPlaneswalker()) {
            String startingLoyaltyString = card.getStartingLoyalty();
            if (startingLoyaltyString.isEmpty()) {
            } else {
                try {
                    this.startingLoyalty = Integer.parseInt(startingLoyaltyString);
                } catch (NumberFormatException e) {
                    Logger.getLogger(MockCard.class).warn("Planeswalker `" + this.name + "` starting loyalty in bad format: `" + startingLoyaltyString + "`.");
                }
            }
        }

        this.flipCardName = card.getFlipCardName();
        for (String ruleText : card.getRules()) {
            this.addAbility(textAbilityFromString(ruleText));
        }
    }

    public MockCard(final MockCard card) {
        super(card);
    }

    @Override
    public int getStartingLoyalty() {
        return startingLoyalty;
    }

    @Override
    public MockCard copy() {
        return new MockCard(this);
    }

    @Override
    public ManaCosts<ManaCost> getManaCost() {
        return manaCost;
    }

    public ManaCosts<ManaCost> getManaCost(CardInfo.ManaCostSide manaCostSide) {
        switch (manaCostSide) {
            case LEFT:
                return manaCostLeft;
            case RIGHT:
                return manaCostRight;
            default:
            case ALL:
                return manaCost;
        }
    }

    public String getFullName(boolean showSecondName) {
        if (!showSecondName) {
            return getName();
        }

        if (adventureSpellName != null) {
            return getName() + ADVENTURE_NAME_SEPARATOR + adventureSpellName;
        } else if (isModalDoubleFacesCard) {
            return getName() + MODAL_DOUBLE_FACES_NAME_SEPARATOR + this.secondSideCard.getName();
        } else {
            return getName();
        }
    }


    private MageInt mageIntFromString(String value) {
        try {
            int intValue = Integer.parseInt(value);
            return new MageInt(intValue);
        } catch (NumberFormatException e) {
            return new MageInt(0, value);
        }
    }

    private String join(List<String> strings) {
        StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            sb.append(string);
        }
        return sb.toString();
    }

    private Ability textAbilityFromString(final String text) {
        return new MockAbility(text);
    }

    @Override
    public boolean isTransformable() {
        // must enable toggle mode in deck editor (switch between card sides);
        return super.isTransformable() || this.isModalDoubleFacesCard;
    }
}
