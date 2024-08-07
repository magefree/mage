package mage.filter.predicate.other;

import mage.MageObject;
import mage.abilities.Ability;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;

/**
 * @author notgreat
 */
public enum AbilitySourceAttachedPredicate implements ObjectSourcePlayerPredicate<StackObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<StackObject> input, Game game) {
        MageObject obj = input.getObject();
        Ability source = input.getSource();

        return obj instanceof StackAbility
                && ((StackAbility) obj).getSourceId().equals(source.getSourcePermanentOrLKI(game).getAttachedTo());
    }

    @Override
    public String toString() {
        return "Attached activated/triggered";
    }
}
