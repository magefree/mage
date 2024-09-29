package mage.cards.i;

import mage.MageInt;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.SaprolingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InfestedThrinax extends CardImpl {

    public InfestedThrinax(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");

        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Infested Thrinax enters the battlefield, until end of turn, whenever a nontoken creature you control dies, create a number of 1/1 green Saproling creature tokens equal to that creature's power.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(new InfestedThrinaxTriggeredAbility())
                .setText("until end of turn, whenever a nontoken creature you control dies, " +
                        "create a number of 1/1 green Saproling creature tokens equal to that creature's power")));
    }

    private InfestedThrinax(final InfestedThrinax card) {
        super(card);
    }

    @Override
    public InfestedThrinax copy() {
        return new InfestedThrinax(this);
    }
}

class InfestedThrinaxTriggeredAbility extends DelayedTriggeredAbility {

    InfestedThrinaxTriggeredAbility() {
        super(new CreateTokenEffect(new SaprolingToken(), SavedDamageValue.MUCH), Duration.EndOfTurn, false, false);
    }

    private InfestedThrinaxTriggeredAbility(final InfestedThrinaxTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public InfestedThrinaxTriggeredAbility copy() {
        return new InfestedThrinaxTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent()
                || zEvent.getTarget() instanceof PermanentToken
                || !zEvent.getTarget().isCreature(game)
                || !zEvent.getTarget().isControlledBy(getControllerId())) {
            return false;
        }
        getEffects().setValue("damage", zEvent.getTarget().getPower().getValue());
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a nontoken creature you control dies, " +
                "create a number of 1/1 green Saproling creature tokens equal to that creature's power.";
    }
}
