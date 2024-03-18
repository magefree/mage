package mage.cards.i;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetDiscard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IllTimedExplosion extends CardImpl {

    public IllTimedExplosion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{R}");

        // Draw two cards. Then you may discard two cards. When you do, Ill-Timed Explosion deals X damage to each creature, where X is the highest mana value among the discarded cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new IllTimedExplosionEffect());
    }

    private IllTimedExplosion(final IllTimedExplosion card) {
        super(card);
    }

    @Override
    public IllTimedExplosion copy() {
        return new IllTimedExplosion(this);
    }
}

class IllTimedExplosionEffect extends OneShotEffect {

    IllTimedExplosionEffect() {
        super(Outcome.Benefit);
        staticText = "Then you may discard two cards. When you do, {this} deals X damage to each creature, " +
                "where X is the greatest mana value among cards discarded this way";
    }

    private IllTimedExplosionEffect(final IllTimedExplosionEffect effect) {
        super(effect);
    }

    @Override
    public IllTimedExplosionEffect copy() {
        return new IllTimedExplosionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getHand().size() < 2
                || !player.chooseUse(outcome, "Discard two cards?", source, game)) {
            return false;
        }
        TargetDiscard target = new TargetDiscard(2, StaticFilters.FILTER_CARD_CARDS, source.getControllerId());
        player.choose(outcome, target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        player.discard(cards, false, source, game);
        int xValue = cards
                .getCards(game)
                .stream()
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0);
        game.fireReflexiveTriggeredAbility(new ReflexiveTriggeredAbility(
                new DamageAllEffect(xValue, StaticFilters.FILTER_PERMANENT_CREATURE), false
        ), source);
        return true;
    }
}
