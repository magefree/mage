
package mage.cards.i;

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
import mage.game.events.DamageCreatureEvent;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author nantuko
 */
public final class InquisitorsFlail extends CardImpl {

    public InquisitorsFlail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // If equipped creature would deal combat damage, it deals double that damage instead.
        // If another creature would deal combat damage to equipped creature, it deals double that damage to equipped creature instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new InquisitorsFlailEffect()));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    public InquisitorsFlail(final InquisitorsFlail card) {
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
        return event.getType() == GameEvent.EventType.DAMAGE_CREATURE ||
                event.getType() == GameEvent.EventType.DAMAGE_PLANESWALKER ||
                event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        boolean isCombat = false;
        if (event instanceof DamageCreatureEvent) {
            isCombat = ((DamageCreatureEvent) event).isCombatDamage();
        } else if (event instanceof DamageEvent) {
            isCombat = ((DamageEvent) event).isCombatDamage();
        }
        if (isCombat) {
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
        event.setAmount(CardUtil.addWithOverflowCheck(event.getAmount(), event.getAmount()));
        return false;
    }

}
