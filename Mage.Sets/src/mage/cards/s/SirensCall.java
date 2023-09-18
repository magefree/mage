
package mage.cards.s;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.common.BeforeAttackersAreDeclaredCondition;
import mage.abilities.condition.common.OnOpponentsTurnCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author emerald000
 */
public final class SirensCall extends CardImpl {

    public SirensCall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Cast Siren's Call only during an opponent's turn, before attackers are declared.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(null, null,
                new CompoundCondition(OnOpponentsTurnCondition.instance, BeforeAttackersAreDeclaredCondition.instance),
                "Cast this spell only during an opponent's turn, before attackers are declared"));

        // Creatures the active player controls attack this turn if able.
        this.getSpellAbility().addEffect(new SirensCallMustAttackEffect());

        // At the beginning of the next end step, destroy all non-Wall creatures that player controls that didn't attack this turn. Ignore this effect for each creature the player didn't control continuously since the beginning of the turn.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new SirensCallDestroyEffect())));
        this.getSpellAbility().addWatcher(new AttackedThisTurnWatcher());
    }

    private SirensCall(final SirensCall card) {
        super(card);
    }

    @Override
    public SirensCall copy() {
        return new SirensCall(this);
    }
}

class SirensCallMustAttackEffect extends RequirementEffect {

    SirensCallMustAttackEffect() {
        super(Duration.EndOfTurn);
        staticText = "Creatures the active player controls attack this turn if able";
    }

    private SirensCallMustAttackEffect(final SirensCallMustAttackEffect effect) {
        super(effect);
    }

    @Override
    public SirensCallMustAttackEffect copy() {
        return new SirensCallMustAttackEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return game.isActivePlayer(permanent.getControllerId());
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

}

class SirensCallDestroyEffect extends OneShotEffect {

    SirensCallDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy all non-Wall creatures that player controls that didn't attack this turn. Ignore this effect for each creature the player didn't control continuously since the beginning of the turn";
    }

    private SirensCallDestroyEffect(final SirensCallDestroyEffect effect) {
        super(effect);
    }

    @Override
    public SirensCallDestroyEffect copy() {
        return new SirensCallDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(player.getId())) {

                // Non Creature Cards are safe.
                if (!permanent.isCreature(game)) {
                    continue;
                }

                // Walls are safe.
                if (permanent.hasSubtype(SubType.WALL, game)) {
                    continue;
                }
                // Creatures that attacked are safe.
                AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
                if (watcher != null && watcher.getAttackedThisTurnCreatures().contains(new MageObjectReference(permanent, game))) {
                    continue;
                }
                // Creatures that weren't controlled since the beginning of turn are safe.
                if (!permanent.wasControlledFromStartOfControllerTurn()) {
                    continue;
                }
                // Destroy the rest.
                permanent.destroy(source, game, false);
            }
            return true;
        }
        return false;
    }
}
