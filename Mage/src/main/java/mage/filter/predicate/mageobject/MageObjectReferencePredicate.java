package mage.filter.predicate.mageobject;

import mage.MageItem;
import mage.MageObject;
import mage.MageObjectReference;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class MageObjectReferencePredicate implements Predicate<MageItem> {

    private final MageObjectReference mor;

    public MageObjectReferencePredicate(MageObjectReference mor) {
        this.mor = mor;
    }

    @Override
    public boolean apply(MageItem input, Game game) {
        if (input instanceof Player) {
            return mor.getSourceId().equals(input.getId());
        }
        return input instanceof MageObject && mor.refersTo((MageObject) input, game);
    }

    @Override
    public String toString() {
        return "MageObjectReference(" + mor.toString() + ')';
    }
}
