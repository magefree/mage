package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RenderInert extends CardImpl {

    public RenderInert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Remove up to five counters from target permanent.
        this.getSpellAbility().addEffect(new RenderInertEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private RenderInert(final RenderInert card) {
        super(card);
    }

    @Override
    public RenderInert copy() {
        return new RenderInert(this);
    }
}

class RenderInertEffect extends OneShotEffect {

    RenderInertEffect() {
        super(Outcome.Benefit);
        staticText = "remove up to five counters from target permanent";
    }

    private RenderInertEffect(final RenderInertEffect effect) {
        super(effect);
    }

    @Override
    public RenderInertEffect copy() {
        return new RenderInertEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller == null || permanent == null) {
            return false;
        }
        int toRemove = 5;
        int removed = 0;
        String[] counterNames = permanent.getCounters(game).keySet().toArray(new String[0]);
        for (String counterName : counterNames) {
            if (controller.chooseUse(Outcome.Neutral, "Remove " + counterName + " counters?", source, game)) {
                if (permanent.getCounters(game).get(counterName).getCount() == 1 || (toRemove - removed == 1)) {
                    permanent.removeCounters(counterName, 1, source, game);
                    removed++;
                } else {
                    int amount = controller.getAmount(1, Math.min(permanent.getCounters(game).get(counterName).getCount(), toRemove - removed), "How many?", game);
                    if (amount > 0) {
                        removed += amount;
                        permanent.removeCounters(counterName, amount, source, game);
                    }
                }
            }
            if (removed >= toRemove) {
                break;
            }
        }
        return true;
    }
}
