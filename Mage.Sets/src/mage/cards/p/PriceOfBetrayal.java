package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.MultiAmountType;
import mage.constants.Outcome;
import mage.filter.FilterOpponent;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.ArrayList;
import java.util.List;
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
        staticText = "Remove up to five counters from target artifact, creature, planeswalker, or opponent.";
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
            List<String> toChoose = new ArrayList<>(permanent.getCounters(game).keySet());
            List<Integer> counterList = controller.getMultiAmount(Outcome.UnboostCreature, toChoose, 0, 0, 5, MultiAmountType.REMOVE_COUNTERS, game);
            for (int i = 0; i < toChoose.size(); i++) {
                int amountToRemove = counterList.get(i);
                if (amountToRemove > 0) {
                    permanent.removeCounters(toChoose.get(i), amountToRemove, source, game);
                }
            }
            return true;
        }

        // from player
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            List<String> toChoose = new ArrayList<>(player.getCountersAsCopy().keySet());
            List<Integer> counterList = controller.getMultiAmount(Outcome.Neutral, toChoose, 0, 0, 5, MultiAmountType.REMOVE_COUNTERS, game);
            for (int i = 0; i < toChoose.size(); i++) {
                int amountToRemove = counterList.get(i);
                if (amountToRemove > 0) {
                    player.loseCounters(toChoose.get(i), amountToRemove, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
