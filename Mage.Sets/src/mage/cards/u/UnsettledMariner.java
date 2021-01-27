package mage.cards.u;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnsettledMariner extends CardImpl {

    public UnsettledMariner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls, counter that spell or ability unless its controller pays {1}.
        this.addAbility(new UnsettledMarinerTriggeredAbility());
    }

    private UnsettledMariner(final UnsettledMariner card) {
        super(card);
    }

    @Override
    public UnsettledMariner copy() {
        return new UnsettledMariner(this);
    }
}

class UnsettledMarinerTriggeredAbility extends TriggeredAbilityImpl {

    UnsettledMarinerTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private UnsettledMarinerTriggeredAbility(final UnsettledMarinerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public UnsettledMarinerTriggeredAbility copy() {
        return new UnsettledMarinerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getOpponents(getControllerId()).contains(event.getPlayerId())) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if ((permanent == null || !permanent.getControllerId().equals(getControllerId()))
                && !event.getTargetId().equals(getControllerId())) {
            return false;
        }
        Effect effect = new CounterUnlessPaysEffect(new GenericManaCost(1));
        effect.setTargetPointer(new FixedTarget(event.getSourceId(), game));
        this.getEffects().clear();
        this.addEffect(effect);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls, " +
                "counter that spell or ability unless its controller pays {1}.";
    }
}