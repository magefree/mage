package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author awjackson
 */
public class PreventAllDamageToSourceByPermanentsEffect extends PreventAllDamageByAllPermanentsEffect {

    public PreventAllDamageToSourceByPermanentsEffect(FilterPermanent filter) {
        this(filter, false);
    }

    public PreventAllDamageToSourceByPermanentsEffect(FilterPermanent filter, boolean onlyCombat) {
        this(filter, Duration.WhileOnBattlefield, onlyCombat);
    }

    public PreventAllDamageToSourceByPermanentsEffect(FilterPermanent filter, Duration duration, boolean onlyCombat) {
        super(filter, duration, onlyCombat);
        setText();
    }

    public PreventAllDamageToSourceByPermanentsEffect(final PreventAllDamageToSourceByPermanentsEffect effect) {
        super(effect);
    }

    public PreventAllDamageToSourceByPermanentsEffect copy() {
        return new PreventAllDamageToSourceByPermanentsEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game) && event.getTargetId().equals(source.getSourceId());
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("prevent all ");
        if (onlyCombat) {
            sb.append("combat ");
        }
        sb.append("damage that would be dealt to {this} ");
        if (duration == Duration.EndOfTurn) {
            sb.append("this turn ");
        }
        sb.append("by ");
        sb.append(filter.getMessage());
        staticText = sb.toString();
    }
}
