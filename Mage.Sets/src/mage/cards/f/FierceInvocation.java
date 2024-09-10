
package mage.cards.f;

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
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class FierceInvocation extends CardImpl {

    public FierceInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}");

        // Manifest the top card of your library, then put two +1/+1 counters on it.<i> (To manifest a card, put it onto the battlefield face down as a 2/2 creature. Turn it face up any time for its mana cost if it's a creature card.)</i>
        this.getSpellAbility().addEffect(new FierceInvocationEffect());
    }

    private FierceInvocation(final FierceInvocation card) {
        super(card);
    }

    @Override
    public FierceInvocation copy() {
        return new FierceInvocation(this);
    }
}

class FierceInvocationEffect extends OneShotEffect {
    
    public FierceInvocationEffect() {
        super(Outcome.Benefit);
        this.staticText = "Manifest the top card of your library, then put two +1/+1 counters on it.<i> (To manifest a card, put it onto the battlefield face down as a 2/2 creature. Turn it face up any time for its mana cost if it's a creature card.)</i>";
    }
    
    private FierceInvocationEffect(final FierceInvocationEffect effect) {
        super(effect);
    }
    
    @Override
    public FierceInvocationEffect copy() {
        return new FierceInvocationEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                new ManifestEffect(1).apply(game, source);
                Permanent permanent = game.getPermanent(card.getId());
                if (permanent != null) {
                    Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(2));
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    return effect.apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
