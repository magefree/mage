package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author nantuko
 */
public class DontUntapInControllersUntapStepEnchantedEffect extends ContinuousRuleModifyingEffectImpl {

    public DontUntapInControllersUntapStepEnchantedEffect() {
        this("creature");
    }

    public DontUntapInControllersUntapStepEnchantedEffect(String description) {
        super(Duration.WhileOnBattlefield, Outcome.Removal, false, true);
        staticText = "Enchanted " + description + " doesn't untap during its controller's untap step";
    }

    public DontUntapInControllersUntapStepEnchantedEffect(final DontUntapInControllersUntapStepEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DontUntapInControllersUntapStepEnchantedEffect copy() {
        return new DontUntapInControllersUntapStepEnchantedEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
            if (enchanted != null) {
                return enchanted.getLogName() + " doesn't untap during its controller's untap step (" + enchantment.getLogName() + ')';
            }
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurnStepType() == PhaseStep.UNTAP) {
            Permanent enchantment = game.getPermanent(source.getSourceId());
            if (enchantment != null && enchantment.getAttachedTo() != null && event.getTargetId().equals(enchantment.getAttachedTo())) {
                Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
                if (permanent != null &&  permanent.isControlledBy(game.getActivePlayerId())) {
                    return true;
                }
            }
        }
        return false;
    }

}
