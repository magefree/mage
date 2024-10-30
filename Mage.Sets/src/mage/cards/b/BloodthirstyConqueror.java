package mage.cards.b;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodthirstyConqueror extends CardImpl {

    public BloodthirstyConqueror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever an opponent loses life, you gain that much life.
        this.addAbility(new BloodthirstyConquerorTriggeredAbility());
    }

    private BloodthirstyConqueror(final BloodthirstyConqueror card) {
        super(card);
    }

    @Override
    public BloodthirstyConqueror copy() {
        return new BloodthirstyConqueror(this);
    }
}

class BloodthirstyConquerorTriggeredAbility extends TriggeredAbilityImpl {

    BloodthirstyConquerorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(SavedGainedLifeValue.MUCH));
        this.setTriggerPhrase("Whenever an opponent loses life, ");
    }

    private BloodthirstyConquerorTriggeredAbility(final BloodthirstyConquerorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BloodthirstyConquerorTriggeredAbility copy() {
        return new BloodthirstyConquerorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getOpponents(getControllerId()).contains(event.getTargetId())) {
            return false;
        }
        this.getEffects().setValue(SavedGainedLifeValue.VALUE_KEY, event.getAmount());
        return true;
    }
}
