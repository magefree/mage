package mage.filter.predicate.other;

import mage.abilities.Ability;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author notgreat
 */
public enum AbilitySourceAttachedPredicate implements ObjectSourcePlayerPredicate<Ability> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Ability> input, Game game) {
        Ability ability = input.getObject();
        Ability source = input.getSource();
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);

        // a creature sacrificed as a cost is not equipped any more when its ability becomes activated (602.2b, 601.2i)
        return sourcePermanent != null
                && ability.getSourceId().equals(sourcePermanent.getAttachedTo())
                && game.getPermanent(ability.getSourceId()) != null;
    }

    @Override
    public String toString() {
        return "Attached activated/triggered";
    }
}
