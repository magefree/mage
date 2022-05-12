
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author emerald000
 */
public final class Statecraft extends CardImpl {

    public Statecraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}");


        // Prevent all combat damage that would be dealt to and dealt by creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new StatecraftPreventionEffect()));
    }

    private Statecraft(final Statecraft card) {
        super(card);
    }

    @Override
    public Statecraft copy() {
        return new Statecraft(this);
    }
}

class StatecraftPreventionEffect extends PreventionEffectImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
    
    StatecraftPreventionEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, true);
        this.staticText = "Prevent all combat damage that would be dealt to and dealt by creatures you control";
    }

    StatecraftPreventionEffect(final StatecraftPreventionEffect effect) {
        super(effect);
    }

    @Override
    public StatecraftPreventionEffect copy() {
        return new StatecraftPreventionEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            Permanent sourcePermanent = game.getPermanent(event.getSourceId());
            if (sourcePermanent != null && filter.match(sourcePermanent, source.getControllerId(), source, game)) {
                return true;
            }
            Permanent targetPermanent = game.getPermanent(event.getTargetId());
            if (targetPermanent != null && filter.match(targetPermanent, source.getControllerId(), source, game)) {
                return true;
            }
        }
        return false;
    }
}
