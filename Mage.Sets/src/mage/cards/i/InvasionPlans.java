
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.combat.BlocksIfAbleAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class InvasionPlans extends CardImpl {

    public InvasionPlans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // All creatures block each turn if able. 
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BlocksIfAbleAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES)));
        // The attacking player chooses how each creature blocks each turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new InvasionPlansEffect()));
    }

    private InvasionPlans(final InvasionPlans card) {
        super(card);
    }

    @Override
    public InvasionPlans copy() {
        return new InvasionPlans(this);
    }
}

class InvasionPlansEffect extends ContinuousRuleModifyingEffectImpl {

    public InvasionPlansEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, false, false);
        staticText = "The attacking player chooses how each creature blocks each turn";
    }

    public InvasionPlansEffect(final InvasionPlansEffect effect) {
        super(effect);
    }

    @Override
    public InvasionPlansEffect copy() {
        return new InvasionPlansEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARING_BLOCKERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player blockController = game.getPlayer(game.getCombat().getAttackingPlayerId());
        if (blockController != null) {
            // temporary workaround for AI bugging out while choosing blockers
            if (!blockController.isComputer()) {
                game.getCombat().selectBlockers(blockController, source, game);
                return event.getPlayerId().equals(game.getCombat().getAttackingPlayerId());
            }
        }
        return false;
    }
}
