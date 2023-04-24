package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.OnOpponentsTurnCondition;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.turn.Phase;
import mage.game.turn.TurnMod;

import java.util.Set;
import java.util.Objects;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class IllusionistsGambit extends CardImpl {

    public IllusionistsGambit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Cast Illusionist's Gambit only during the declare blockers step on an opponent's turn.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(PhaseStep.DECLARE_BLOCKERS, OnOpponentsTurnCondition.instance));

        // Remove all attacking creatures from combat and untap them. After this phase, there is an additional combat phase. Each of those creatures attacks that combat if able. They can't attack you or a planeswalker you control that combat.
        this.getSpellAbility().addEffect(new IllusionistsGambitRemoveFromCombatEffect());
    }

    private IllusionistsGambit(final IllusionistsGambit card) {
        super(card);
    }

    @Override
    public IllusionistsGambit copy() {
        return new IllusionistsGambit(this);
    }
}

class IllusionistsGambitRemoveFromCombatEffect extends OneShotEffect {

    public IllusionistsGambitRemoveFromCombatEffect() {
        super(Outcome.Benefit);
        this.staticText = "Remove all attacking creatures from combat and untap them. After this phase, there is an additional combat phase. Each of those creatures attacks that combat if able. They can't attack you or planeswalkers you control that combat";
    }

    public IllusionistsGambitRemoveFromCombatEffect(final IllusionistsGambitRemoveFromCombatEffect effect) {
        super(effect);
    }

    @Override
    public IllusionistsGambitRemoveFromCombatEffect copy() {
        return new IllusionistsGambitRemoveFromCombatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> attackers = game.getCombat().getAttackers();
        for (UUID attackerId : attackers) {
            Permanent creature = game.getPermanent(attackerId);
            if (creature != null) {
                creature.removeFromCombat(game);
                creature.untap(game);
            }
        }
        if (!attackers.isEmpty()) {
            Phase phase = game.getTurn().getPhase();
            game.getState().getTurnMods().add(new TurnMod(game.getActivePlayerId(), TurnPhase.COMBAT, null, false));
            ContinuousEffect effect = new IllusionistsGambitRequirementEffect(attackers, phase);
            game.addEffect(effect, source);
            effect = new IllusionistsGambitRestrictionEffect(attackers, phase);
            game.addEffect(effect, source);

        }
        return true;
    }
}

class IllusionistsGambitRequirementEffect extends RequirementEffect {

    private Set<UUID> attackers;
    private Phase phase;

    public IllusionistsGambitRequirementEffect(Set<UUID> attackers, Phase phase) {
        super(Duration.Custom);
        this.attackers = attackers;
        this.phase = phase;
        this.staticText = "Each of those creatures attacks that combat if able";
    }

    public IllusionistsGambitRequirementEffect(final IllusionistsGambitRequirementEffect effect) {
        super(effect);
        this.attackers = effect.attackers;
        this.phase = effect.phase;
    }

    @Override
    public IllusionistsGambitRequirementEffect copy() {
        return new IllusionistsGambitRequirementEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (attackers.contains(permanent.getId())) {
            return game.getOpponents(permanent.getControllerId()).size() > 1;
        }
        return false;
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getTurnStepType() == PhaseStep.END_COMBAT) {
            return !Objects.equals(game.getTurn().getPhase(), phase);
        }
        return false;
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

class IllusionistsGambitRestrictionEffect extends RestrictionEffect {

    private final Set<UUID> attackers;
    private final Phase phase;

    public IllusionistsGambitRestrictionEffect(Set<UUID> attackers, Phase phase) {
        super(Duration.Custom, Outcome.Benefit);
        this.attackers = attackers;
        this.phase = phase;
        staticText = "They can't attack you or planeswalkers you control that combat";
    }

    public IllusionistsGambitRestrictionEffect(final IllusionistsGambitRestrictionEffect effect) {
        super(effect);
        this.attackers = effect.attackers;
        this.phase = effect.phase;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return attackers.contains(permanent.getId());
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getTurnStepType() == PhaseStep.END_COMBAT) {
            return !Objects.equals(game.getTurn().getPhase(), phase);
        }
        return false;
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }
        if (defenderId.equals(source.getControllerId())) {
            return false;
        }
        // planeswalker
        Permanent permanent = game.getPermanent(defenderId);
        return permanent == null || !permanent.isControlledBy(source.getControllerId())
                || !permanent.isPlaneswalker(game);
    }

    @Override
    public IllusionistsGambitRestrictionEffect copy() {
        return new IllusionistsGambitRestrictionEffect(this);
    }

}
