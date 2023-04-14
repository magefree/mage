package mage.cards.mock;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.cards.CardImpl;
import mage.cards.ModalDoubleFacesCard;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock card for GUI (deck editor and panels, contains only texts)
 *
 * @author North
 */
public class MockCard extends CardImpl {

    static public String ADVENTURE_NAME_SEPARATOR = " // ";
    static public String MODAL_DOUBLE_FACES_NAME_SEPARATOR = " // ";

    // Needs to be here, as it is normally calculated from the
    // PlaneswalkerEntersWithLoyaltyAbility of the card... but the MockCard
    // only has MockAbilities.
    private final int startingLoyalty;

    // mana cost extra info for multiple mana drawing
    // warning, don't use ManaCost objects here due too much memory consumptions
    protected List<String> manaCostLeftStr;
    protected List<String> manaCostRightStr;
    protected List<String> manaCostStr;
    protected String adventureSpellName;
    protected boolean isModalDoubleFacesCard;
    protected int manaValue;

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

        //this.manaCost = new ManaCostsImpl<>(join(card.getManaCosts(CardInfo.ManaCostSide.ALL)));
        this.manaCostLeftStr = card.getManaCosts(CardInfo.ManaCostSide.LEFT);
        this.manaCostRightStr = card.getManaCosts(CardInfo.ManaCostSide.RIGHT);
        this.manaCostStr = card.getManaCosts(CardInfo.ManaCostSide.ALL);
        this.manaValue = card.getManaValue();

        this.color = card.getColor();

        this.frameColor = card.getFrameColor();
        this.frameStyle = card.getFrameStyle();

        this.flipCard = card.isFlipCard();

        this.nightCard = card.isNightCard();

        if (card.getSecondSideName() != null && !card.getSecondSideName().isEmpty()) {
            this.secondSideCard = new MockCard(CardRepository.instance.findCardWithPreferredSetAndNumber(card.getSecondSideName(), card.getSetCode(), card.getCardNumber()));
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

        switch (card.getStartingLoyalty()) {
            case "X":
                this.startingLoyalty = -2;
                break;
            case "":
                this.startingLoyalty = -1;
                break;
            default:
                this.startingLoyalty = Integer.parseInt(card.getStartingLoyalty());
        }
        this.flipCardName = card.getFlipCardName();
        for (String ruleText : card.getRules()) {
            this.addAbility(textAbilityFromString(ruleText));
        }
    }

    public MockCard(final MockCard card) {
        super(card);

        this.startingLoyalty = card.startingLoyalty;
        this.manaCostLeftStr = new ArrayList<>(card.manaCostLeftStr);
        this.manaCostRightStr = new ArrayList<>(card.manaCostRightStr);
        this.manaCostStr = new ArrayList<>(card.manaCostStr);
        this.adventureSpellName = card.adventureSpellName;
        this.isModalDoubleFacesCard = card.isModalDoubleFacesCard;
        this.manaValue = card.manaValue;
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
        // only split half cards can store mana cost in objects list instead strings (memory optimization)
        // see https://github.com/magefree/mage/issues/7515
        throw new IllegalArgumentException("Unsupport method call: getManaCost in " + this.getClass().getCanonicalName());
    }

    @Override
    public List<String> getManaCostSymbols() {
        return getManaCostStr(CardInfo.ManaCostSide.ALL);
    }

    @Override
    public int getManaValue() {
        return this.manaValue;
    }

    public List<String> getManaCostStr(CardInfo.ManaCostSide manaCostSide) {
        switch (manaCostSide) {
            case LEFT:
                return manaCostLeftStr;
            case RIGHT:
                return manaCostRightStr;
            default:
            case ALL:
                return manaCostStr;
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

    private Ability textAbilityFromString(final String text) {
        return new MockAbility(text);
    }

    @Override
    public boolean isTransformable() {
        // must enable toggle mode in deck editor (switch between card sides);
        return super.isTransformable() || this.isModalDoubleFacesCard || this.secondSideCard != null;
    }
}
