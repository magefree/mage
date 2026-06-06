package mage.cards;

import mage.abilities.SpellAbility;
import mage.abilities.effects.common.ExileAdventureSpellEffect;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author phulin
 */
public abstract class AdventureCard extends CardWithSpellOption<AdventureCardHalf, AdventureCard> {

    public AdventureCard(
            UUID ownerId, CardSetInfo setInfo,
            SuperType[] superTypesLeft, CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
            String adventureName,
            CardType[] typesRight, String costsRight
    ) {
        this(
                ownerId, setInfo,
                superTypesLeft, typesLeft, subTypesLeft, costsLeft,
                adventureName,
                new SuperType[]{}, typesRight, new SubType[]{SubType.ADVENTURE}, costsRight
        );
    }

    public AdventureCard (
            UUID ownerId, CardSetInfo setInfo,
            CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
            String adventureName,
            CardType[] typesRight, String costsRight
    ) {
        this(
                ownerId, setInfo,
                new SuperType[]{}, typesLeft, subTypesLeft, costsLeft,
                adventureName,
                new SuperType[]{}, typesRight, new SubType[]{SubType.ADVENTURE}, costsRight
        );
    }

    public AdventureCard(
            UUID ownerId, CardSetInfo setInfo,
            CardType[] typesLeft, String costsLeft,
            String adventureName,
            CardType[] typesRight, String costsRight) {
        this(
                ownerId, setInfo,
                new SuperType[]{}, typesLeft, new SubType[]{}, costsLeft,
                adventureName,
                new SuperType[]{}, typesRight, new SubType[]{SubType.ADVENTURE}, costsRight
        );
    }

    public AdventureCard(
            UUID ownerId, CardSetInfo setInfo,
            SuperType[] superTypesLeft, CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
            String adventureName,
            SuperType[] superTypesRight,  CardType[] typesRight, SubType[] subTypesRight, String costsRight
    ) {
        super(ownerId, setInfo, typesLeft, costsLeft, SpellAbilityType.ADVENTURE_OMEN);
        // main card name must be same as left side
        leftHalfCard = new AdventureCardHalf(
                this.getOwnerId(), setInfo.copy(),
                superTypesLeft, typesLeft, subTypesLeft, costsLeft,
                this, SpellAbilityType.ADVENTURE_OMEN_LEFT
        );
        rightHalfCard = new AdventureCardHalf(
                this.getOwnerId(), new CardSetInfo(adventureName, setInfo),
                superTypesRight, typesRight, subTypesRight, costsRight,
                this, SpellAbilityType.ADVENTURE_OMEN_RIGHT
        );

    }

    public AdventureCard(final AdventureCard card) {
        super(card);
    }

    @Override
    protected void finalizeCard() {
        AdventureCardSpellAbility adventureSpellAbility = new AdventureCardSpellAbility(
                rightHalfCard.getSpellAbility(),
                rightHalfCard.getName(),
                rightHalfCard.getCardType(),
                rightHalfCard.getManaCost().toString()
        );
        this.getRightHalfCard().replaceSpellAbility(adventureSpellAbility);
        finalized = true;
    }
}

class AdventureCardSpellAbility extends SpellAbility {

    public AdventureCardSpellAbility(final SpellAbility baseSpellAbility, String omenName, List<CardType> cardTypes, String costs) {
        super(baseSpellAbility);
        this.setName(cardTypes, omenName, costs);
        this.setCardName(omenName);
        this.addEffect(ExileAdventureSpellEffect.getInstance());
    }

    protected AdventureCardSpellAbility(final AdventureCardSpellAbility ability) {
        super(ability);
    }

    public void setName(List<CardType> cardTypes, String omenName, String costs) {
        this.name = "Adventure "
                + cardTypes.stream().map(CardType::toString).collect(Collectors.joining(" "))
                + " &mdash; "
                + omenName
                + " " + costs;
    }

    @Override
    public String getRule(boolean all) {
        return CardUtil.getTextWithFirstCharUpperCase(super.getRule(false)) // without cost
                + " <i>(Then exile this card. You may cast the creature later from exile.)</i>";
    }

    @Override
    public AdventureCardSpellAbility copy() {
        return new AdventureCardSpellAbility(this);
    }
}
