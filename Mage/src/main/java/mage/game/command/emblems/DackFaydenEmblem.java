
package mage.game.command.emblems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author spjspj
 */
public final class DackFaydenEmblem extends Emblem {

    public DackFaydenEmblem() {
        this.setName("Emblem Dack");
        this.getAbilities().add(new DackFaydenEmblemTriggeredAbility());

        availableImageSetCodes = Arrays.asList("CNS", "EMA", "MED");
    }
}

class DackFaydenEmblemTriggeredAbility extends TriggeredAbilityImpl {

    DackFaydenEmblemTriggeredAbility() {
        super(Zone.COMMAND, new DackFaydenEmblemEffect(), false);
    }

    DackFaydenEmblemTriggeredAbility(final DackFaydenEmblemTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DackFaydenEmblemTriggeredAbility copy() {
        return new DackFaydenEmblemTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean returnValue = false;
        List<UUID> targetedPermanentIds = new ArrayList<>(0);
        Player player = game.getPlayer(this.getControllerId());
        if (player != null) {
            if (event.getPlayerId().equals(this.getControllerId())) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null) {
                    SpellAbility spellAbility = spell.getSpellAbility();
                    for (Mode mode : spellAbility.getModes().values()) {
                        for (Target target : mode.getTargets()) {
                            if (!target.isNotTarget()) {
                                for (UUID targetId : target.getTargets()) {
                                    if (game.getBattlefield().containsPermanent(targetId)) {
                                        returnValue = true;
                                        targetedPermanentIds.add(targetId);
                                    }
                                }
                            }
                        }
                    }
                    for (Effect effect : spellAbility.getEffects()) {
                        for (UUID targetId : effect.getTargetPointer().getTargets(game, spellAbility)) {
                            if (game.getBattlefield().containsPermanent(targetId)) {
                                returnValue = true;
                                targetedPermanentIds.add(targetId);
                            }
                        }
                    }
                }
            }
        }
        for (Effect effect : this.getEffects()) {
            if (effect instanceof DackFaydenEmblemEffect) {
                DackFaydenEmblemEffect dackEffect = (DackFaydenEmblemEffect) effect;
                List<Permanent> permanents = new ArrayList<>();
                for (UUID permanentId : targetedPermanentIds) {
                    Permanent permanent = game.getPermanent(permanentId);
                    if (permanent != null) {
                        permanents.add(permanent);
                    }
                }

                dackEffect.setTargets(permanents, game);
            }
        }
        return returnValue;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell that targets one or more permanents, gain control of those permanents.";
    }
}

class DackFaydenEmblemEffect extends ContinuousEffectImpl {

    protected FixedTargets fixedTargets;

    DackFaydenEmblemEffect() {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.staticText = "gain control of those permanents";
    }

    DackFaydenEmblemEffect(final DackFaydenEmblemEffect effect) {
        super(effect);
        this.fixedTargets = effect.fixedTargets;
    }

    @Override
    public DackFaydenEmblemEffect copy() {
        return new DackFaydenEmblemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID permanentId : fixedTargets.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null) {
                permanent.changeControllerId(source.getControllerId(), game, source);
            }
        }
        return true;
    }

    public void setTargets(List<Permanent> targetedPermanents, Game game) {
        this.fixedTargets = new FixedTargets(targetedPermanents, game);
    }
}
