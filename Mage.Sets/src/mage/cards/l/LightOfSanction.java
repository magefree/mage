
package mage.cards.l;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Dilnu
 */
public final class LightOfSanction extends CardImpl {

    public LightOfSanction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}{W}");

        // Prevent all damage that would be dealt to creatures you control by sources you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LightOfSanctionEffect()));
    }

    private LightOfSanction(final LightOfSanction card) {
        super(card);
    }

    @Override
    public LightOfSanction copy() {
        return new LightOfSanction(this);
    }
}

class LightOfSanctionEffect extends PreventionEffectImpl {

    public LightOfSanctionEffect() {
        super(Duration.EndOfGame);
        this.staticText = "Prevent all damage that would be dealt to creatures you control by sources you control.";
        consumable = false;
    }

    private LightOfSanctionEffect(final LightOfSanctionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_PERMANENT) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.isCreature(game) && permanent.isControlledBy(source.getControllerId())) {
                MageObject damageSource = game.getObject(event.getSourceId());
                if (damageSource instanceof Controllable) {
                    return ((Controllable) damageSource).isControlledBy(source.getControllerId());
                }
                else if (damageSource instanceof Card) {
                    return ((Card) damageSource).isOwnedBy(source.getControllerId());
                }
            }
        }
        return false;
    }

    @Override
    public LightOfSanctionEffect copy() {
        return new LightOfSanctionEffect(this);
    }
}