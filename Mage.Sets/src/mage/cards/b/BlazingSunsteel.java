package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPermanentEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlazingSunsteel extends CardImpl {

    public BlazingSunsteel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 for each opponent you have.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(OpponentsCount.instance, StaticValue.get(0))
                .setText("equipped creature gets +1/+0 for each opponent you have")));

        // Whenever equipped creature is dealt damage, it deals that much damage to any target.
        this.addAbility(new BlazingSunsteelTriggeredAbility());

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(4), new TargetControlledCreaturePermanent(), false));
    }

    private BlazingSunsteel(final BlazingSunsteel card) {
        super(card);
    }

    @Override
    public BlazingSunsteel copy() {
        return new BlazingSunsteel(this);
    }
}

class BlazingSunsteelTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPermanentEvent> {

    BlazingSunsteelTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BlazingSunsteelEffect(), false);
        this.addTarget(new TargetAnyTarget());
    }

    private BlazingSunsteelTriggeredAbility(final BlazingSunsteelTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BlazingSunsteelTriggeredAbility copy() {
        return new BlazingSunsteelTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = getSourcePermanentIfItStillExists(game);
        if (equipment == null || !event.getTargetId().equals(equipment.getAttachedTo())) {
            return false;
        }
        this.getEffects().setValue("equipped", equipment.getAttachedTo());
        this.getEffects().setValue("damage", event.getAmount());
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature is dealt damage, it deals that much damage to any target.";
    }
}

class BlazingSunsteelEffect extends OneShotEffect {

    BlazingSunsteelEffect() {
        super(Outcome.Benefit);
    }

    private BlazingSunsteelEffect(final BlazingSunsteelEffect effect) {
        super(effect);
    }

    @Override
    public BlazingSunsteelEffect copy() {
        return new BlazingSunsteelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanentOrLKIBattlefield((UUID) getValue("equipped"));
        Integer damage = (Integer) getValue("damage");

        if (creature == null || damage == null || damage < 1) {
            return false;
        }

        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.damage(damage, creature.getId(), source, game);
            return true;
        }
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.damage(damage, creature.getId(), source, game);
            return true;
        }
        return false;
    }
}
