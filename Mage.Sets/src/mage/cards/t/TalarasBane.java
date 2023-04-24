
package mage.cards.t;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public final class TalarasBane extends CardImpl {

    public TalarasBane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target opponent reveals their hand. You choose a green or white creature card from it. You gain life equal that creature card's toughness, then that player discards that card.
        this.getSpellAbility().addEffect(new TalarasBaneEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

    }

    private TalarasBane(final TalarasBane card) {
        super(card);
    }

    @Override
    public TalarasBane copy() {
        return new TalarasBane(this);
    }
}

class TalarasBaneEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("a green or white creature card");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.GREEN),
                new ColorPredicate(ObjectColor.WHITE)));
        filter.add(CardType.CREATURE.getPredicate());
    }

    public TalarasBaneEffect() {
        super(Outcome.Detriment);
        this.staticText = "Target opponent reveals their hand. You choose a green or white creature card from it. You gain life equal to that creature card's toughness, then that player discards that card";
    }

    public TalarasBaneEffect(final TalarasBaneEffect effect) {
        super(effect);
    }

    @Override
    public TalarasBaneEffect copy() {
        return new TalarasBaneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        Card card = null;
        if (targetPlayer != null && you != null) {
            targetPlayer.revealCards("Talaras Bane", targetPlayer.getHand(), game);
            TargetCard target = new TargetCard(Zone.HAND, filter);
            if (you.choose(Outcome.Benefit, targetPlayer.getHand(), target, source, game)) {
                card = targetPlayer.getHand().get(target.getFirstTarget(), game);
            }
            if (card != null) {
                int lifeGain = card.getToughness().getValue();
                you.gainLife(lifeGain, game, source);
                return targetPlayer.discard(card, false, source, game);
            }
        }
        return false;
    }
}
