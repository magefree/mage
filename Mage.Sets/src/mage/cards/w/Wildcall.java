
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class Wildcall extends CardImpl {

    public Wildcall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{G}{G}");

        // Manifest the top card of your library, then put X +1/+1 counters on it.
        this.getSpellAbility().addEffect(new WildcallEffect());

    }

    private Wildcall(final Wildcall card) {
        super(card);
    }

    @Override
    public Wildcall copy() {
        return new Wildcall(this);
    }
}

class WildcallEffect extends OneShotEffect {

    public WildcallEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Manifest the top card of your library, then put X +1/+1 counters on it";
    }

    private WildcallEffect(final WildcallEffect effect) {
        super(effect);
    }

    @Override
    public WildcallEffect copy() {
        return new WildcallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                new ManifestEffect(1).apply(game, source);
                int xValue = source.getManaCostsToPay().getX();
                if (xValue > 0) {
                    Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(xValue));
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    return effect.apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
