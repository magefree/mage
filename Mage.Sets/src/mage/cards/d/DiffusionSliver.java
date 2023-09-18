package mage.cards.d;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DiffusionSliver extends CardImpl {

    public DiffusionSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a Sliver creature you control becomes the target of a spell or ability an opponent controls, counter that spell or ability unless its controller pays {2}.
        this.addAbility(new DiffusionSliverTriggeredAbility());
    }

    private DiffusionSliver(final DiffusionSliver card) {
        super(card);
    }

    @Override
    public DiffusionSliver copy() {
        return new DiffusionSliver(this);
    }
}

class DiffusionSliverTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.SLIVER);

    DiffusionSliverTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private DiffusionSliverTriggeredAbility(final DiffusionSliverTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DiffusionSliverTriggeredAbility copy() {
        return new DiffusionSliverTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            return false;
        }
        Permanent creature = game.getPermanent(event.getTargetId());
        if (creature == null || !filter.match(creature, getControllerId(), this, game)) {
            return false;
        }
        this.getEffects().clear();
        Effect effect = new CounterUnlessPaysEffect(new GenericManaCost(2));
        effect.setTargetPointer(new FixedTarget(event.getSourceId(), game));
        this.addEffect(effect);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a Sliver creature you control becomes the target of a spell or ability an opponent controls, " +
                "counter that spell or ability unless its controller pays {2}.";
    }
}
