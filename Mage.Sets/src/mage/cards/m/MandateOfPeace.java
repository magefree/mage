package mage.cards.m;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author goesta
 */
public final class MandateOfPeace extends CardImpl {

    public MandateOfPeace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Cast this spell only during combat.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(TurnPhase.COMBAT));

        // Your opponents can't cast spells this turn.
        this.getSpellAbility().addEffect(new MandateOfPeaceOpponentsCantCastSpellsEffect());

        // End the combat phase.
        this.getSpellAbility().addEffect(new MandateOfPeaceEndCombatEffect());
    }

    private MandateOfPeace(final MandateOfPeace card) {
        super(card);
    }

    @Override
    public MandateOfPeace copy() {
        return new MandateOfPeace(this);
    }
}

class MandateOfPeaceOpponentsCantCastSpellsEffect extends ContinuousRuleModifyingEffectImpl {

    public MandateOfPeaceOpponentsCantCastSpellsEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Your opponents can't cast spells this turn";
    }

    public MandateOfPeaceOpponentsCantCastSpellsEffect(final MandateOfPeaceOpponentsCantCastSpellsEffect effect) {
        super(effect);
    }

    @Override
    public MandateOfPeaceOpponentsCantCastSpellsEffect copy() {
        return new MandateOfPeaceOpponentsCantCastSpellsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast spells this turn (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getOpponents(source.getControllerId()).contains(event.getPlayerId());
    }

}

class MandateOfPeaceEndCombatEffect extends OneShotEffect {

    public MandateOfPeaceEndCombatEffect() {
        super(Outcome.Benefit);
        this.staticText = "End the combat phase. <i>(Remove all attackers "
                + "and blockers from combat. Exile all spells and abilities "
                + "from the stack, including this spell.)</i>";
    }

    public MandateOfPeaceEndCombatEffect(OneShotEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Combat combat = game.getCombat();
        List<UUID> attackerIds = combat.getAttackers();
        List<UUID> blockerIds = combat.getBlockers();
        Stream.concat(blockerIds.stream(), attackerIds.stream())
                .map(id -> game.getPermanent(id))
                .filter(e -> e != null)
                .forEach(permanent -> permanent.removeFromCombat(game));
        combat.endCombat(game);
        if (!game.getStack().isEmpty()) {
            game.getStack().stream()
                    .filter(stackObject -> stackObject instanceof Spell)
                    .forEach(stackObject -> ((Spell) stackObject).moveToExile(null, "", null, game));

            game.getStack().stream()
                    .filter(stackObject -> stackObject instanceof Ability)
                    .forEach(stackObject -> game.getStack().remove(stackObject, game));
        }
        return true;
    }

    @Override
    public Effect copy() {
        return new MandateOfPeaceEndCombatEffect(this);
    }
}
