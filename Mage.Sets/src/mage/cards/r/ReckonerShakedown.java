package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReckonerShakedown extends CardImpl {

    public ReckonerShakedown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent reveals their hand. You may choose a nonland card from it. If you do, that player discards that card. If you don't, put two +1/+1 counters on a creature or Vehicle you control.
        this.getSpellAbility().addEffect(new ReckonerShakedownEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private ReckonerShakedown(final ReckonerShakedown card) {
        super(card);
    }

    @Override
    public ReckonerShakedown copy() {
        return new ReckonerShakedown(this);
    }
}

class ReckonerShakedownEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent("creature or Vehicle you control");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    ReckonerShakedownEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent reveals their hand. You may choose a nonland card from it. " +
                "If you do, that player discards that card. If you don't, " +
                "put two +1/+1 counters on a creature or Vehicle you control";
    }

    private ReckonerShakedownEffect(final ReckonerShakedownEffect effect) {
        super(effect);
    }

    @Override
    public ReckonerShakedownEffect copy() {
        return new ReckonerShakedownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller == null || player == null) {
            return false;
        }
        player.revealCards(source, player.getHand(), game);
        TargetCard target = new TargetCardInHand(0, 1, StaticFilters.FILTER_CARD_A_NON_LAND);
        controller.choose(Outcome.Discard, player.getHand(), target, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.discard(card, false, source, game);
            return true;
        }
        if (!game.getBattlefield().contains(filter, source, game, 1)) {
            return true;
        }
        TargetPermanent targetPermanent = new TargetPermanent(filter);
        targetPermanent.setNotTarget(true);
        player.choose(Outcome.BoostCreature, targetPermanent, source, game);
        Permanent permanent = game.getPermanent(targetPermanent.getFirstTarget());
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(2), source, game);
        }
        return true;
    }
}
