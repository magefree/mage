
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class ArmoredTransport extends CardImpl {

    public ArmoredTransport(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Prevent all combat damage that would be dealt to Armored Transport by creatures blocking it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ArmoredTransportPreventCombatDamageSourceEffect(Duration.WhileOnBattlefield)));

    }

    private ArmoredTransport(final ArmoredTransport card) {
        super(card);
    }

    @Override
    public ArmoredTransport copy() {
        return new ArmoredTransport(this);
    }
}

class ArmoredTransportPreventCombatDamageSourceEffect extends PreventionEffectImpl {

    public ArmoredTransportPreventCombatDamageSourceEffect(Duration duration) {
        super(duration);
        staticText = "Prevent all combat damage that would be dealt to {this} by creatures blocking it" + duration.toString();
    }

    public ArmoredTransportPreventCombatDamageSourceEffect(final ArmoredTransportPreventCombatDamageSourceEffect effect) {
        super(effect);
    }

    @Override
    public ArmoredTransportPreventCombatDamageSourceEffect copy() {
        return new ArmoredTransportPreventCombatDamageSourceEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            DamageEvent damageEvent = (DamageEvent) event;
            if (event.getTargetId().equals(source.getSourceId()) && damageEvent.isCombatDamage()) {
                Permanent sourcePermanent = game.getPermanent(source.getSourceId());
                if (sourcePermanent != null && sourcePermanent.isAttacking()) {
                    return true;
                }
            }
        }
        return false;
    }
}
