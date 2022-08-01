package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public final class Tamanoa extends CardImpl {

    public Tamanoa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever a noncreature source you control deals damage, you gain that much life.
        Ability ability = new TamanoaDealsDamageTriggeredAbility(Zone.BATTLEFIELD, new GainLifeEffect(SavedDamageValue.MUCH), false);

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

    public TamanoaDealsDamageTriggeredAbility(Zone zone, Effect effect, boolean optional) {
        super(zone, effect, optional);
    }

    public TamanoaDealsDamageTriggeredAbility(final TamanoaDealsDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TamanoaDealsDamageTriggeredAbility copy() {
        return new TamanoaDealsDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MageObject eventSourceObject = game.getObject(event.getSourceId());
        if (eventSourceObject != null && !eventSourceObject.isCreature(game)) {
            if (isControlledBy(game.getControllerId(event.getSourceId()))) {
                this.getEffects().forEach((effect) -> {
                    effect.setValue("damage", event.getAmount());
                });
                return true;
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a noncreature source you control deals damage, " ;
    }
}
