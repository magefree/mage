package mage.util.functions;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.stack.StackObject;

public class RemoveTypeCopyApplier extends CopyApplier implements StackObjectCopyApplier {

    private final CardType type;
    private final SuperType superType;
    private final SubType subType;

    public RemoveTypeCopyApplier(CardType type) {
        this.type = type;
        this.superType = null;
        this.subType = null;
    }

    public RemoveTypeCopyApplier(SuperType superType) {
        this.superType = superType;
        this.subType = null;
        this.type = null;
    }

    public RemoveTypeCopyApplier(SubType subType) {
        this.subType = subType;
        this.superType = null;
        this.type = null;
    }

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
        if (type != null && blueprint.getCardType().contains(type)) {
            blueprint.getCardType().remove(type);
        } else if (superType != null && blueprint.getSuperType().contains(superType)) {
            blueprint.getSuperType().remove(superType);
        } else if (subType != null && blueprint.getSubtype().contains(subType)) {
            blueprint.getSubtype().remove(subType);
        }

        return true;
    }

    @Override
    public void modifySpell(StackObject stackObject, Game game) {
        if (type != null && stackObject.getCardType().contains(type)) {
            stackObject.getCardType().remove(type);
        } else if (superType != null && stackObject.getSuperType().contains(superType)) {
            stackObject.getSuperType().remove(superType);
        } else if (subType != null && stackObject.getSubtype().contains(subType)) {
            stackObject.getSubtype().remove(subType);
        }
    }

    @Override
    public MageObjectReferencePredicate getNextNewTargetType() {
        return null;
    }

}
