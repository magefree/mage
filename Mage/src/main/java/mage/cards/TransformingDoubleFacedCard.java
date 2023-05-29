package mage.cards;

import mage.abilities.SpellAbility;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * @author JayDi85, TheElk801
 */
public abstract class TransformingDoubleFacedCard extends DoubleFacedCard {

    public static final String VALUE_KEY_ENTER_TRANSFORMED = "EnterTransformed";

    public static void setCardTransformed(Card card, Game game) {
        game.getState().setValue(VALUE_KEY_ENTER_TRANSFORMED + card.getMainCard().getId(), true);
    }

    public TransformingDoubleFacedCard(
            UUID ownerId, CardSetInfo setInfo,
            CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
            String secondSideName,
            CardType[] typesRight, SubType[] subTypesRight, String colorRight
    ) {
        this(
                ownerId, setInfo,
                new SuperType[]{}, typesLeft, subTypesLeft, costsLeft,
                secondSideName,
                new SuperType[]{}, typesRight, subTypesRight, colorRight
        );
    }

    public TransformingDoubleFacedCard(
            UUID ownerId, CardSetInfo setInfo,
            SuperType[] superTypesLeft, CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
            String secondSideName,
            SuperType[] superTypesRight, CardType[] typesRight, SubType[] subTypesRight, String colorRight
    ) {
        super(
                ownerId, setInfo, typesLeft, costsLeft, SpellAbilityType.TRANSFORMING,
                new DoubleFacedCardHalfImpl(
                        ownerId, setInfo.copy(),
                        superTypesLeft, typesLeft, subTypesLeft, costsLeft,
                        SpellAbilityType.TRANSFORMING_LEFT
                ),
                new DoubleFacedCardHalfImpl(
                        ownerId, new CardSetInfo(secondSideName, setInfo),
                        superTypesRight, typesRight, subTypesRight, "",
                        SpellAbilityType.TRANSFORMING_RIGHT, colorRight
                )
        );
    }

    protected TransformingDoubleFacedCard(final TransformingDoubleFacedCard card) {
        super(card);
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        switch (ability.getSpellAbilityType()) {
            case TRANSFORMING_LEFT:
                return this.leftHalfCard.cast(game, fromZone, ability, controllerId);
            case TRANSFORMING_RIGHT:
                return this.rightHalfCard.cast(game, fromZone, ability, controllerId);
            default:
                if (this.leftHalfCard.getSpellAbility() != null)
                    this.leftHalfCard.getSpellAbility().setControllerId(controllerId);
                if (this.rightHalfCard.getSpellAbility() != null)
                    this.rightHalfCard.getSpellAbility().setControllerId(controllerId);
                return super.cast(game, fromZone, ability, controllerId);
        }
    }

    @Override
    public boolean isTransformable() {
        return true;
    }
}
