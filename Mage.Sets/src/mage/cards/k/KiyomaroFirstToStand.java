
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public final class KiyomaroFirstToStand extends CardImpl {

    public KiyomaroFirstToStand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Kiyomaro, First to Stand's power and toughness are each equal to the number of cards in your hand.
        DynamicValue xValue= CardsInControllerHandCount.instance;
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(xValue)));
        
        // As long as you have four or more cards in hand, Kiyomaro has vigilance.
        Condition condition = new CardsInHandCondition(ComparisonType.MORE_THAN,3);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield), condition,
                "As long as you have four or more cards in hand, {this} has vigilance"));
        this.addAbility(ability);
        
        // Whenever Kiyomaro deals damage, if you have seven or more cards in hand, you gain 7 life.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new KiyomaroFirstToStandDealsDamageTriggeredAbility(),
                new CardsInHandCondition(ComparisonType.MORE_THAN, 6),
                "Whenever {this} deals damage, if you have seven or more cards in hand, you gain 7 life"
        ));
    }

    private KiyomaroFirstToStand(final KiyomaroFirstToStand card) {
        super(card);
    }

    @Override
    public KiyomaroFirstToStand copy() {
        return new KiyomaroFirstToStand(this);
    }
}

class KiyomaroFirstToStandDealsDamageTriggeredAbility extends TriggeredAbilityImpl {

    public KiyomaroFirstToStandDealsDamageTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(7), false);
    }

    public KiyomaroFirstToStandDealsDamageTriggeredAbility(final KiyomaroFirstToStandDealsDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KiyomaroFirstToStandDealsDamageTriggeredAbility copy() {
        return new KiyomaroFirstToStandDealsDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("damage", event.getAmount());
            }
            return true;
        }
        return false;
    }

}
