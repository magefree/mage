package mage.abilities.effects.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author nantuko
 */
public class SkipEnchantedUntapEffect extends ReplacementEffectImpl<SkipEnchantedUntapEffect> {

    public SkipEnchantedUntapEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Detriment);
        staticText = "Enchanted permanent doesn't untap during its controller's untap step";
    }

    public SkipEnchantedUntapEffect(final SkipEnchantedUntapEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SkipEnchantedUntapEffect copy() {
        return new SkipEnchantedUntapEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurn().getStepType() == Constants.PhaseStep.UNTAP && event.getType() == GameEvent.EventType.UNTAP) {
            Permanent enchantment = game.getPermanent(source.getSourceId());
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
                if (permanent != null && event.getTargetId().equals(permanent.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

}
