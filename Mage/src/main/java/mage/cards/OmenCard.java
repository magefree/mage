package mage.cards;

import mage.abilities.SpellAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jmlundeen
 */
public abstract class OmenCard extends CardWithSpellOption<OmenCardHalf, OmenCard> {

    public OmenCard(
            UUID ownerId, CardSetInfo setInfo,
            SuperType[] superTypesLeft, CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
            String omenName,
            CardType[] typesRight, String costsRight
    ) {
        this(
                ownerId, setInfo,
                superTypesLeft, typesLeft, subTypesLeft, costsLeft,
                omenName,
                new SuperType[]{}, typesRight, new SubType[]{SubType.OMEN}, costsRight
        );
    }

    public OmenCard (
            UUID ownerId, CardSetInfo setInfo,
            CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
            String omenName,
            CardType[] typesRight, String costsRight
    ) {
        this(
                ownerId, setInfo,
                new SuperType[]{}, typesLeft, subTypesLeft, costsLeft,
                omenName,
                new SuperType[]{}, typesRight, new SubType[]{SubType.OMEN}, costsRight
        );
    }

    public OmenCard(
            UUID ownerId, CardSetInfo setInfo,
            CardType[] typesLeft, String costsLeft,
            String adventureName,
            CardType[] typesRight, String costsRight) {
        this(
                ownerId, setInfo,
                new SuperType[]{}, typesLeft, new SubType[]{}, costsLeft,
                adventureName,
                new SuperType[]{}, typesRight, new SubType[]{SubType.OMEN}, costsRight
        );
    }

    public OmenCard(
            UUID ownerId, CardSetInfo setInfo,
            SuperType[] superTypesLeft, CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
            String omenName,
            SuperType[] superTypesRight,  CardType[] typesRight, SubType[] subTypesRight, String costsRight
    ) {
        super(ownerId, setInfo, typesLeft, costsLeft, SpellAbilityType.ADVENTURE_OMEN);
        // main card name must be same as left side
        leftHalfCard = new OmenCardHalf(
                this.getOwnerId(), setInfo.copy(),
                superTypesLeft, typesLeft, subTypesLeft, costsLeft,
                this, SpellAbilityType.ADVENTURE_OMEN_LEFT
        );
        rightHalfCard = new OmenCardHalf(
                this.getOwnerId(), new CardSetInfo(omenName, setInfo),
                superTypesRight, typesRight, subTypesRight, costsRight,
                this, SpellAbilityType.ADVENTURE_OMEN_RIGHT
        );
    }

    public OmenCard(final OmenCard card) {
        super(card);
    }

    @Override
    protected void finalizeCard() {
        OmenCardSpellAbility newSpellAbility = new OmenCardSpellAbility(
                rightHalfCard.getSpellAbility(),
                rightHalfCard.getName(),
                rightHalfCard.getCardType(),
                rightHalfCard.getManaCost().toString()
        );
        this.getRightHalfCard().replaceSpellAbility(newSpellAbility);
        finalized = true;
    }
}

class OmenCardSpellAbility extends SpellAbility {

    public OmenCardSpellAbility(final SpellAbility baseSpellAbility, String omenName, List<CardType> cardTypes, String costs) {
        super(baseSpellAbility);
        this.setName(cardTypes, omenName, costs);
        this.setCardName(omenName);
        Effect effect = new ShuffleIntoLibrarySourceEffect();
        effect.setText("");
        this.addEffect(effect);
    }

    protected OmenCardSpellAbility(final OmenCardSpellAbility ability) {
        super(ability);
    }

    public void setName(List<CardType> cardTypes, String omenName, String costs) {
        this.name = "Omen "
                + cardTypes.stream().map(CardType::toString).collect(Collectors.joining(" "))
                + " &mdash; "
                + omenName
                + " " + costs;
    }

    @Override
    public String getRule(boolean all) {
        return CardUtil.getTextWithFirstCharUpperCase(super.getRule(false)) // without cost
                + " <i>(Then shuffle this card into its owner's library.)</i>";
    }

    @Override
    public OmenCardSpellAbility copy() {
        return new OmenCardSpellAbility(this);
    }
}
