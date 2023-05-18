package mage.cards;

import mage.abilities.SpellAbility;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * @author JayDi85, TheElk801
 */
public abstract class TransformingDoubleFacedCard extends DoubleFacedCard {

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
                ownerId, setInfo, typesLeft, costsLeft, SpellAbilityType.BASE,
                new DoubleFacedCardHalfImpl(
                        ownerId, setInfo.copy(),
                        superTypesLeft, typesLeft, subTypesLeft, costsLeft,
                        SpellAbilityType.BASE
                ),
                new DoubleFacedCardHalfImpl(
                        ownerId, new CardSetInfo(secondSideName, setInfo),
                        superTypesRight, typesRight, subTypesRight, "",
                        SpellAbilityType.MODAL_RIGHT, colorRight
                )
        );
    }

    protected TransformingDoubleFacedCard(final TransformingDoubleFacedCard card) {
        super(card);
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        return super.cast(game, fromZone, ability, controllerId);
    }
}
