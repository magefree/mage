package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExplosionOfRiches extends CardImpl {

    public ExplosionOfRiches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}");

        // Draw a card, then each other player may draw a card. Whenever a card is drawn this way, Explosion of Riches deals 5 damage to target opponent chosen at random from among your opponents.
        this.getSpellAbility().addEffect(new ExplosionOfRichesEffect());
    }

    private ExplosionOfRiches(final ExplosionOfRiches card) {
        super(card);
    }

    @Override
    public ExplosionOfRiches copy() {
        return new ExplosionOfRiches(this);
    }
}

class ExplosionOfRichesEffect extends OneShotEffect {

    ExplosionOfRichesEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card, then each other player may draw a card. Whenever a card is drawn this way, " +
                "{this} deals 5 damage to target opponent chosen at random from among your opponents";
    }

    private ExplosionOfRichesEffect(final ExplosionOfRichesEffect effect) {
        super(effect);
    }

    @Override
    public ExplosionOfRichesEffect copy() {
        return new ExplosionOfRichesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int cardsDrawn = 0;
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            if (!playerId.equals(source.getControllerId())
                    && !player.chooseUse(outcome, "Draw a card?", source, game)) {
                continue;
            }
            cardsDrawn += player.drawCards(1, source, game);
        }
        for (int i = 0; i < cardsDrawn; i++) {
            ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                    new DamageTargetEffect(5), false,
                    "{this} deals damage to target opponent chosen at random"
            );
            Target target = new TargetOpponent();
            target.setRandom(true);
            ability.addTarget(target);
            game.fireReflexiveTriggeredAbility(ability, source);
        }
        return true;
    }
}
