package mage.cards.g;

import java.util.Optional;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author muz
 */
public final class GameOver extends CardImpl {

    public GameOver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // This spell costs {2} less to cast if a player's life total is less than or equal to half their starting life total.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, GameOverCondition.instance)
        ).setRuleAtTheTop(true));

        // Destroy all creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
    }

    private GameOver(final GameOver card) {
        super(card);
    }

    @Override
    public GameOver copy() {
        return new GameOver(this);
    }
}

enum GameOverCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional.ofNullable(game.getPlayer(source.getControllerId()))
            .map(Player::getLife)
            .map(x -> 2 * x <= game.getStartingLife())
            .orElse(false);
    }
}
