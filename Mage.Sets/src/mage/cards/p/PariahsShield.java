
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class PariahsShield extends CardImpl {

    public PariahsShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");
        this.subtype.add(SubType.EQUIPMENT);

        // All damage that would be dealt to you is dealt to equipped creature instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PariahEffect()));
        
        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private PariahsShield(final PariahsShield card) {
        super(card);
    }

    @Override
    public PariahsShield copy() {
        return new PariahsShield(this);
    }

    class PariahEffect extends ReplacementEffectImpl {
        PariahEffect() {
            super(Duration.WhileOnBattlefield, Outcome.RedirectDamage);
            staticText = "All damage that would be dealt to you is dealt to equipped creature instead";
        }

        PariahEffect(final PariahEffect effect) {
            super(effect);
        }

        @Override
        public boolean replaceEvent(GameEvent event, Ability source, Game game) {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null) {
                Permanent permanent = game.getPermanent(equipment.getAttachedTo());
                if (permanent != null) {
                    DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
                    permanent.damage(damageEvent.getAmount(), event.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable());
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean checksEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
        }

        @Override
        public boolean applies(GameEvent event, Ability source, Game game) {
            return event.getPlayerId().equals(source.getControllerId());
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return true;
        }

        @Override
        public PariahEffect copy() {
            return new PariahEffect(this);
        }
    }
}
