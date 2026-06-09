package mage.cards;

import mage.abilities.SpellAbility;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * @author emerald000
 */
public abstract class MeldCard extends DoubleFacedCard<MeldCardHalf, MeldCard> {

    public MeldCard(
            UUID ownerId, CardSetInfo setInfo,
            SuperType[] superTypesLeft, CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
            String secondSideName,
            SuperType[] superTypesRight, CardType[] typesRight, SubType[] subTypesRight, String colorRight
    )  {
        super(ownerId, setInfo, typesLeft, costsLeft, SpellAbilityType.MELD);
        // main card name must be same as left side
        leftHalfCard = new MeldCardHalf(
                this.getOwnerId(), setInfo.copy(),
                superTypesLeft, typesLeft, subTypesLeft, costsLeft,
                this, SpellAbilityType.MELD_LEFT
        );
        rightHalfCard = new MeldCardHalf(
                this.getOwnerId(), new CardSetInfo(secondSideName, setInfo),
                superTypesRight, typesRight, subTypesRight, colorRight, this
        );
        if (setInfo.getMeldNumber().isEmpty()) {
            throw new IllegalArgumentException("Meld cards must have a meld number in set info");
        }
        this.secondSideCard = rightHalfCard;
    }

    protected MeldCard(final MeldCard card) {
        super(card);
    }

    public MeldCard(
            UUID ownerId, CardSetInfo setInfo,
            CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
            String secondSideName,
            CardType[] typesRight, SubType[] subTypesRight, String colorRight) {
        this(ownerId, setInfo,
                new SuperType[]{}, typesLeft, subTypesLeft, costsLeft,
                secondSideName,
                new SuperType[]{}, typesRight, subTypesRight, colorRight);

    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        if (ability.getSpellAbilityType() == SpellAbilityType.MELD) {
            return this.leftHalfCard.cast(game, fromZone, ability, controllerId);
        }
        return super.cast(game, fromZone, ability, controllerId);
    }

    @Override
    public boolean isTransformable() {
        // 712.9. Only permanents represented by double-faced tokens and double-faced cards that are not meld cards can transform or convert.
        // (See rule 701.27, "Transform," and rule 701.28, "Convert.") If a spell or ability instructs a player to transform or convert
        // any permanent that isn't represented by a double-faced token or a double-faced card, nothing happens.
        return false;
    }

    @Override
    public abstract MeldCard copy();
}
