
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class SettleTheScore extends CardImpl {

    public SettleTheScore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Exile target creature. Put two loyalty counters on a planeswalker you control.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new SettleTheScoreEffect());
    }

    private SettleTheScore(final SettleTheScore card) {
        super(card);
    }

    @Override
    public SettleTheScore copy() {
        return new SettleTheScore(this);
    }
}

class SettleTheScoreEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("planeswalker you control");

    static {
        filter.add(CardType.PLANESWALKER.getPredicate());
    }

    public SettleTheScoreEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put two loyalty counters on a planeswalker you control";
    }

    public SettleTheScoreEffect(final SettleTheScoreEffect effect) {
        super(effect);
    }

    @Override
    public SettleTheScoreEffect copy() {
        return new SettleTheScoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(filter);
        if (target.choose(Outcome.Benefit, player.getId(), source.getSourceId(), source, game)) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.addCounters(CounterType.LOYALTY.createInstance(2), source.getControllerId(), source, game);
            }
        }
        return true;
    }
}
