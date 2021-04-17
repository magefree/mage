package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FeedTheSwarm extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("creature or enchantment an opponent controls");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public FeedTheSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Destroy target creature or enchantment an opponent controls. You lose life equal to that permanent's converted mana cost.
        this.getSpellAbility().addEffect(new FeedTheSwarmEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private FeedTheSwarm(final FeedTheSwarm card) {
        super(card);
    }

    @Override
    public FeedTheSwarm copy() {
        return new FeedTheSwarm(this);
    }
}

class FeedTheSwarmEffect extends OneShotEffect {

    FeedTheSwarmEffect() {
        super(Outcome.Benefit);
        staticText = "destroy target creature or enchantment an opponent controls. " +
                "You lose life equal to that permanent's mana value";
    }

    private FeedTheSwarmEffect(final FeedTheSwarmEffect effect) {
        super(effect);
    }

    @Override
    public FeedTheSwarmEffect copy() {
        return new FeedTheSwarmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (permanent == null || player == null) {
            return false;
        }
        player.loseLife(permanent.getManaValue(), game, source, false);
        permanent.destroy(source, game, false);
        return true;
    }
}
