package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetPowerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.SatyrCantBlockToken;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnaxHardenedInTheForge extends CardImpl {

    public AnaxHardenedInTheForge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{R}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMIGOD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Anax's power is equal to your devotion to red.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetPowerSourceEffect(DevotionCount.R, Duration.EndOfGame)
                        .setText("{this}'s power is equal to your devotion to red")
        ).addHint(DevotionCount.R.getHint()));

        // Whenever Anax or another nontoken creature you control dies, create a 1/1 red Satyr creature token with "This creature can't block." If the creature had 4 power or greater, create two of those tokens instead.
        this.addAbility(new AnaxHardenedInTheForgeTriggeredAbility());
    }

    private AnaxHardenedInTheForge(final AnaxHardenedInTheForge card) {
        super(card);
    }

    @Override
    public AnaxHardenedInTheForge copy() {
        return new AnaxHardenedInTheForge(this);
    }
}

class AnaxHardenedInTheForgeTriggeredAbility extends TriggeredAbilityImpl {

    AnaxHardenedInTheForgeTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private AnaxHardenedInTheForgeTriggeredAbility(final AnaxHardenedInTheForgeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AnaxHardenedInTheForgeTriggeredAbility copy() {
        return new AnaxHardenedInTheForgeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent()) {
            return false;
        }
        if (!zEvent.getTarget().getId().equals(getSourceId())
                && (zEvent.getTarget() instanceof PermanentToken
                || !zEvent.getTarget().isCreature()
                || !Objects.equals(zEvent.getTarget().getControllerId(), getControllerId()))) {
            return false;
        }
        int tokenCount = zEvent.getTarget().getPower().getValue() > 3 ? 2 : 1;
        this.getEffects().clear();
        this.addEffect(new CreateTokenEffect(new SatyrCantBlockToken(), tokenCount));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another nontoken creature you control dies, " +
                "create a 1/1 red Satyr creature token with \"This creature can't block.\" " +
                "If the creature had 4 power or greater, create two of those tokens instead.";
    }
}
