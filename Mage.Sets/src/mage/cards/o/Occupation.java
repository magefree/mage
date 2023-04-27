package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.turn.Step;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author NinthWorld
 */
public final class Occupation extends CardImpl {

    public Occupation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{B}");


        // Creatures your opponents control enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OccupationTapEffect()));

        // {W}{B}: Target creature can't attack or block this turn, and its activated abilities can't be activated until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new OccupationOneShotEffect("Target creature can't attack or block this turn, and its activated abilities can't be activated until end of turn"),
                new ManaCostsImpl<>("{W}{B}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Occupation(final Occupation card) {
        super(card);
    }

    @Override
    public Occupation copy() {
        return new Occupation(this);
    }
}

class OccupationTapEffect extends ReplacementEffectImpl {

    OccupationTapEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Tap);
        staticText = "Creatures your opponents control enter the battlefield tapped";
    }

    OccupationTapEffect(final OccupationTapEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.setTapped(true);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
            return permanent != null && permanent.isCreature(game);
        }
        return false;
    }

    @Override
    public OccupationTapEffect copy() {
        return new OccupationTapEffect(this);
    }
}

class OccupationOneShotEffect extends OneShotEffect {

    public OccupationOneShotEffect() {
        super(Outcome.LoseAbility);
    }

    public OccupationOneShotEffect(String ruleText) {
        super(Outcome.LoseAbility);
        staticText = ruleText;
    }

    public OccupationOneShotEffect(final OccupationOneShotEffect effect) {
        super(effect);
    }

    @Override
    public OccupationOneShotEffect copy() {
        return new OccupationOneShotEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        OccupationRestrictionEffect effect = new OccupationRestrictionEffect();
        game.addEffect(effect, source);
        return true;
    }
}

class OccupationRestrictionEffect extends RestrictionEffect {

    public OccupationRestrictionEffect() {
        super(Duration.Custom);
        staticText = "";
    }

    public OccupationRestrictionEffect(final OccupationRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.addInfo("Can't attack or block and its activated abilities can't be activated." + getId(), "", game);
            }
        }
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPhase().getStep().getType() == PhaseStep.UNTAP
                && game.getStep().getStepPart() == Step.StepPart.PRE) {
            if (game.isActivePlayer(source.getControllerId())
                    || game.getPlayer(source.getControllerId()).hasReachedNextTurnAfterLeaving()) {
                for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        permanent.addInfo("Can't attack or block and its activated abilities can't be activated." + getId(), "", game);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return this.targetPointer.getTargets(game, source).contains(permanent.getId());
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public OccupationRestrictionEffect copy() {
        return new OccupationRestrictionEffect(this);
    }

}