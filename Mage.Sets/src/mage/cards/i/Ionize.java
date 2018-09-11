package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class Ionize extends CardImpl {

    public Ionize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{R}");

        // Counter target spell. Ionize deals 2 damage to that spell's controller.
        this.getSpellAbility().addEffect(new IonizeEffect());
    }

    public Ionize(final Ionize card) {
        super(card);
    }

    @Override
    public Ionize copy() {
        return new Ionize(this);
    }
}

class IonizeEffect extends OneShotEffect {

    public IonizeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Counter target spell. "
                + "{this} deals 2 damage to that spell's controller.";
    }

    public IonizeEffect(final IonizeEffect effect) {
        super(effect);
    }

    @Override
    public IonizeEffect copy() {
        return new IonizeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getControllerId(source.getSourceId()));
        new CounterTargetEffect().apply(game, source);
        if (player == null) {
            return false;
        }
        return player.damage(2, source.getSourceId(), game, false, true) > 0;
    }
}
