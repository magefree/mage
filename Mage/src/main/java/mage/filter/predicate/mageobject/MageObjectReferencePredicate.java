package mage.filter.predicate.mageobject;

import mage.MageItem;
import mage.MageObject;
import mage.MageObjectReference;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class MageObjectReferencePredicate implements Predicate<MageItem> {

    private final MageObjectReference mor;

    public MageObjectReferencePredicate(UUID sourceId, Game game) {
        this(new MageObjectReference(sourceId, game));
    }

    public MageObjectReferencePredicate(MageObject mageObject, Game game) {
        this(new MageObjectReference(mageObject, game));
    }

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

    public String getName(Game game) {
        Permanent permanent = mor.getPermanent(game);
        if (permanent != null) {
            return permanent.getIdName();
        }
        Player player = game.getPlayer(mor.getSourceId());
        if (player != null) {
            return player.getName();
        }
        return null;
    }

    @Override
    public String toString() {
        return "MageObjectReference(" + mor.toString() + ')';
    }
}
