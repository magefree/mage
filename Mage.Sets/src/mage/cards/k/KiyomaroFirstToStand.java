package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KiyomaroFirstToStand extends CardImpl {

    public KiyomaroFirstToStand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Kiyomaro, First to Stand's power and toughness are each equal to the number of cards in your hand.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(CardsInControllerHandCount.ANY)));

        // As long as you have four or more cards in hand, Kiyomaro has vigilance.
        Condition condition = new CardsInHandCondition(ComparisonType.MORE_THAN, 3);
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield), condition,
                "As long as you have four or more cards in hand, {this} has vigilance"));
        this.addAbility(ability);

        // Whenever Kiyomaro deals damage, if you have seven or more cards in hand, you gain 7 life.
        this.addAbility(new DealsDamageSourceTriggeredAbility(new GainLifeEffect(7))
                .withInterveningIf(new CardsInHandCondition(ComparisonType.OR_GREATER, 7)));
    }

    private KiyomaroFirstToStand(final KiyomaroFirstToStand card) {
        super(card);
    }

    @Override
    public KiyomaroFirstToStand copy() {
        return new KiyomaroFirstToStand(this);
    }
}
