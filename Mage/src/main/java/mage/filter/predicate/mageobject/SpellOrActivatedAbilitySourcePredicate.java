package mage.filter.predicate.mageobject;

import mage.constants.AbilityType;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

public enum SpellOrActivatedAbilitySourcePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return input.getSource().isActivatedAbility() || input.getSource().getAbilityType() == AbilityType.SPELL;
    }

    @Override
    public String toString() {
        return "Source is a spell or activated ability";
    }
}