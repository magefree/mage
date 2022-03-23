
package mage.cards.s;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.discard.DiscardHandAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class StrategySchmategy extends CardImpl {

    public StrategySchmategy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Roll a six-sided die. Strategy, Schmategy has the indicated effect. 1 - Do nothing. 2 - Destroy all artifacts. 3 - Destroy all lands. 4 - Strategy, Schmategy deals 3 damage to each creature and each player. 5 - Each player discards their hand and draws seven cards. 6 - Repeat this process two more times.
        this.getSpellAbility().addEffect(new StrategySchmategyffect());
    }

    private StrategySchmategy(final StrategySchmategy card) {
        super(card);
    }

    @Override
    public StrategySchmategy copy() {
        return new StrategySchmategy(this);
    }
}

class StrategySchmategyffect extends OneShotEffect {

    public StrategySchmategyffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Roll a six-sided die. {this} has the indicated effect." +
                "<br>1 - Do nothing." +
                "<br>2 - Destroy all artifacts." +
                "<br>3 - Destroy all lands." +
                "<br>4 - {this} deals 3 damage to each creature and each player." +
                "<br>5 - Each player discards their hand and draws seven cards." +
                "<br>6 - Repeat this process two more times";
    }

    public StrategySchmategyffect(final StrategySchmategyffect effect) {
        super(effect);
    }

    @Override
    public StrategySchmategyffect copy() {
        return new StrategySchmategyffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        int numTimesToDo = 1;
        if (controller != null) {
            // 1 - Do nothing.
            // 2 - Destroy all artifacts.
            // 3 - Destroy all lands.
            // 4 - {this} deals 3 damage to each creature and each player.
            // 5 - Each player discards their hand and draws seven cards.
            // 6 - Repeat this process two more times
            while (numTimesToDo > 0) {
                int amount = controller.rollDice(Outcome.Detriment, source, game, 6); // ai must try to choose min
                numTimesToDo--;
                if (amount == 2) {
                    List<Permanent> artifactPermanents = game.getBattlefield().getActivePermanents(new FilterArtifactPermanent(), controller.getId(), game);
                    for (Permanent permanent : artifactPermanents) {
                        permanent.destroy(source, game, false);
                    }
                } else if (amount == 3) {
                    List<Permanent> landPermanents = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_LANDS, controller.getId(), game);
                    for (Permanent permanent : landPermanents) {
                        permanent.destroy(source, game, false);
                    }
                } else if (amount == 4) {
                    new DamageEverythingEffect(3, new FilterCreaturePermanent()).apply(game, source);
                } else if (amount == 5) {
                    new DiscardHandAllEffect().apply(game, source);
                    new DrawCardAllEffect(7).apply(game, source);
                } else if (amount == 6) {
                    numTimesToDo += 2;
                }
            }
        }
        return false;
    }
}
