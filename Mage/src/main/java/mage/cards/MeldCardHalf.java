package mage.cards;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

public class MeldCardHalf extends DoubleFacedCardHalf<MeldCard> {

    public MeldCardHalf(
            java.util.UUID ownerId, CardSetInfo setInfo,
            SuperType[] superTypes, CardType[] cardTypes, SubType[] subTypes, String costs,
            MeldCard parentCard,
            SpellAbilityType spellAbilityType
    ) {
        super(ownerId, setInfo, superTypes, cardTypes, subTypes, costs, parentCard, spellAbilityType);
    }

    protected MeldCardHalf(final MeldCardHalf card) {
        super(card);
    }

    public MeldCardHalf(
            UUID ownerId, CardSetInfo setInfo,
            SuperType[] superTypesRight, CardType[] typesRight, SubType[] subTypesRight, String colorRight, MeldCard parentCard) {
        super(ownerId, setInfo, superTypesRight, typesRight, subTypesRight, "", parentCard, SpellAbilityType.MELD_RIGHT);
        this.getColor().setColor(new ObjectColor(colorRight));
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        if (isBackSide()) {
            throw new IllegalStateException("Cannot cast the back side of a meld card half: " + this.getName());
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
    public boolean removeFromZone(Game game, Zone fromZone, Ability source) {
        if (isCopy()) {
            return super.removeFromZone(game, fromZone, source);
        }
        if (getMeldedWith(game) != null) {
            getMeldedWith(game).removeFromZone(game, fromZone, source);
        }
        return super.removeFromZone(game, fromZone, source);
    }

    @Override
    public MeldCardHalf copy() {
        return new MeldCardHalf(this);
    }
}
