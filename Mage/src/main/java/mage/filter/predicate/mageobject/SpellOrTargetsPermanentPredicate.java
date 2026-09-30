package mage.filter.predicate.mageobject;

import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.stack.StackObject;

/**
 * @author muz
 */
public class SpellOrTargetsPermanentPredicate implements ObjectSourcePlayerPredicate<StackObject> {

    private final SubType spellSubtype;
    private final TargetsPermanentPredicate targetsPermanentPredicate;

    public SpellOrTargetsPermanentPredicate(SubType spellSubtype, FilterPermanent targetFilter) {
        this.spellSubtype = spellSubtype;
        this.targetsPermanentPredicate = new TargetsPermanentPredicate(targetFilter);
    }

    @Override
    public boolean apply(ObjectSourcePlayer<StackObject> input, Game game) {
        return input.getObject().hasSubtype(spellSubtype, game)
                || targetsPermanentPredicate.apply(input, game);
    }
}
