
package mage.view;

import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author NinthWorld
 */
public class MutateView extends CardsView {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final UUID id;

    public MutateView(Permanent permanent, Game game) {
        this.name = permanent.getName();
        this.id = permanent.getId();
        for (Permanent perm : permanent.getMutatedOverList()) {
            this.put(perm.getId(), new CardView(perm, game, false));
        }
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

}
