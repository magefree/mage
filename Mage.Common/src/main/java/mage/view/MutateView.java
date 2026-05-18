package mage.view;

import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author NinthWorld, xenohedron
 */
public class MutateView extends CardsView {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final UUID id;

    public MutateView(Permanent permanent, Game game) {
        this.name = permanent.getName();
        this.id = permanent.getId();
        permanent.getMutateObjects().stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .forEach(c -> this.put(c.getId(), new CardView(c, game, false)));
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

}
