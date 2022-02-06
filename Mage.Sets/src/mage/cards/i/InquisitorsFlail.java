
package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class InquisitorsFlail extends CardImpl {

    public InquisitorsFlail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // If equipped creature would deal combat damage, it deals double that damage instead.
        // If another creature would deal combat damage to equipped creature, it deals double that damage to equipped creature instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new InquisitorsFlailEffect()));

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private InquisitorsFlail(final InquisitorsFlail card) {
        super(card);
    }

    @Override
    public InquisitorsFlail copy() {
        return new InquisitorsFlail(this);
    }
}

class InquisitorsFlailEffect extends ReplacementEffectImpl {

    public InquisitorsFlailEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If equipped creature would deal combat damage, it deals double that damage instead. \n"
                + "If another creature would deal combat damage to equipped creature, it deals double that damage to equipped creature instead";
    }

    public InquisitorsFlailEffect(final InquisitorsFlailEffect effect) {
        super(effect);
    }

    @Override
    public InquisitorsFlailEffect copy() {
        return new InquisitorsFlailEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT ||
                event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (((DamageEvent) event).isCombatDamage()) {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                UUID attachedTo = equipment.getAttachedTo();
                if (event.getSourceId().equals(attachedTo)) {
                    return true;
                } else if (event.getTargetId().equals(attachedTo)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }

}
