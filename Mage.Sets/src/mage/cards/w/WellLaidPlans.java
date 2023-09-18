
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class WellLaidPlans extends CardImpl {

    public WellLaidPlans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Prevent all damage that would be dealt to a creature by another creature if they share a color.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WellLaidPlansPreventionEffect()));
    }

    private WellLaidPlans(final WellLaidPlans card) {
        super(card);
    }

    @Override
    public WellLaidPlans copy() {
        return new WellLaidPlans(this);
    }
}

class WellLaidPlansPreventionEffect extends PreventionEffectImpl {

    public WellLaidPlansPreventionEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false, false);
        this.staticText = "Prevent all damage that would be dealt to a creature by another creature if they share a color";
    }

    private WellLaidPlansPreventionEffect(final WellLaidPlansPreventionEffect effect) {
        super(effect);
    }

    @Override
    public WellLaidPlansPreventionEffect copy() {
        return new WellLaidPlansPreventionEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGE_PERMANENT) {
            return false;
        }
        Permanent attacker = game.getPermanentOrLKIBattlefield(event.getSourceId());
        Permanent damaged = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (attacker == null || damaged == null
                || !attacker.isCreature(game) || !damaged.isCreature(game)) {
            return false;
        }
        return !attacker.getColor(game).intersection(damaged.getColor(game)).isColorless();
    }
}
