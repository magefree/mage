package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterOpponent;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PriceOfBetrayal extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    private static final FilterPermanentOrPlayer filter2 = new FilterPermanentOrPlayer("artifact, creature, planeswalker, or opponent", filter, new FilterOpponent());

    public PriceOfBetrayal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Remove up to five counters from target artifact, creature, planeswalker or opponent.
        this.getSpellAbility().addEffect(new PriceOfBetrayalEffect());
        this.getSpellAbility().addTarget(new TargetPermanentOrPlayer(1, 1, filter2, false));
    }

    private PriceOfBetrayal(final PriceOfBetrayal card) {
        super(card);
    }

    @Override
    public PriceOfBetrayal copy() {
        return new PriceOfBetrayal(this);
    }
}

class PriceOfBetrayalEffect extends OneShotEffect {

    PriceOfBetrayalEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "Remove up to five counters from target artifact, creature, planeswalker or opponent.";
    }

    private PriceOfBetrayalEffect(final PriceOfBetrayalEffect effect) {
        super(effect);
    }

    @Override
    public PriceOfBetrayalEffect copy() {
        return new PriceOfBetrayalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        // from permanent
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
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

        // from player
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            int toRemove = 5;
            int removed = 0;
            String[] counterNames = player.getCounters().keySet().toArray(new String[0]);
            for (String counterName : counterNames) {
                if (controller.chooseUse(Outcome.Neutral, "Remove " + counterName + " counters?", source, game)) {
                    if (player.getCounters().get(counterName).getCount() == 1 || (toRemove - removed == 1)) {
                        player.removeCounters(counterName, 1, source, game);
                        removed++;
                    } else {
                        int amount = controller.getAmount(1, Math.min(player.getCounters().get(counterName).getCount(), toRemove - removed), "How many?", game);
                        if (amount > 0) {
                            removed += amount;
                            player.removeCounters(counterName, amount, source, game);
                        }
                    }
                }
                if (removed >= toRemove) {
                    break;
                }
            }
            return true;
        }
        return false;
    }
}
