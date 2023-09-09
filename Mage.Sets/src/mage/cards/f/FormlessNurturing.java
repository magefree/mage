
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
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class FormlessNurturing extends CardImpl {

    public FormlessNurturing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");

        // Manifest the top card of your library, then put a +1/+1 counter on it.
        this.getSpellAbility().addEffect(new FormlessNurturingEffect());
    }

    private FormlessNurturing(final FormlessNurturing card) {
        super(card);
    }

    @Override
    public FormlessNurturing copy() {
        return new FormlessNurturing(this);
    }
}

class FormlessNurturingEffect extends OneShotEffect {

    public FormlessNurturingEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Manifest the top card of your library, then put a +1/+1 counter on it";
    }

    private FormlessNurturingEffect(final FormlessNurturingEffect effect) {
        super(effect);
    }

    @Override
    public FormlessNurturingEffect copy() {
        return new FormlessNurturingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                new ManifestEffect(1).apply(game, source);
                Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
                effect.setTargetPointer(new FixedTarget(card.getId()));
                return effect.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
