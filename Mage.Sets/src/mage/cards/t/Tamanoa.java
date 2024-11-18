package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Tamanoa extends CardImpl {

    public Tamanoa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever a noncreature source you control deals damage, you gain that much life.
        Ability ability = new TamanoaDealsDamageTriggeredAbility();

        this.addAbility(ability);
    }

    private Tamanoa(final Tamanoa card) {
        super(card);
    }

    @Override
    public Tamanoa copy() {
        return new Tamanoa(this);
    }
}

class TamanoaDealsDamageTriggeredAbility extends TriggeredAbilityImpl {

    TamanoaDealsDamageTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(SavedDamageValue.MUCH), false);
        setTriggerPhrase("Whenever a noncreature source you control deals damage, ");
    }

    private TamanoaDealsDamageTriggeredAbility(final TamanoaDealsDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TamanoaDealsDamageTriggeredAbility copy() {
        return new TamanoaDealsDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_BY_SOURCE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MageObject eventSourceObject = game.getObject(event.getSourceId());
        if (eventSourceObject == null || eventSourceObject.isCreature(game)
                || !isControlledBy(game.getControllerId(event.getSourceId()))) {
            return false;
        }
        getEffects().setValue("damage", event.getAmount());
        return true;
    }
}
