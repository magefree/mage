
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterBlockingCreature;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class FightingChance extends CardImpl {

    private static final FilterBlockingCreature filter = new FilterBlockingCreature("Blocking creatures");

    public FightingChance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // For each blocking creature, flip a coin. If you win the flip, prevent all combat damage that would be dealt by that creature this turn.
        this.getSpellAbility().addEffect(new FightingChanceEffect());
    }

    private FightingChance(final FightingChance card) {
        super(card);
    }

    @Override
    public FightingChance copy() {
        return new FightingChance(this);
    }
}

class FightingChanceEffect extends OneShotEffect {

    FightingChanceEffect() {
        super(Outcome.Detriment);
        staticText = "For each blocking creature, flip a coin. If you win the flip, prevent all combat damage that would be dealt by that creature this turn.";
    }

    FightingChanceEffect(final FightingChanceEffect effect) {
        super(effect);
    }

    @Override
    public FightingChanceEffect copy() {
        return new FightingChanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (UUID blocker : game.getCombat().getBlockers()) {
                if (player.flipCoin(source, game, true)) {
                    PreventDamageByTargetEffect effect = new PreventDamageByTargetEffect(Duration.EndOfTurn, true);
                    effect.setTargetPointer(new FixedTarget(blocker, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
