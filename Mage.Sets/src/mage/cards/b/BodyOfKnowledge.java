package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BodyOfKnowledge extends CardImpl {

    public BodyOfKnowledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Body of Knowledge's power and toughness are each equal to the number of cards in your hand.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerToughnessSourceEffect(
                        CardsInControllerHandCount.instance, Duration.EndOfGame
                )
        ));

        // You have no maximum hand size.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.WhileOnBattlefield,
                MaximumHandSizeControllerEffect.HandSizeModification.SET
        )));

        // Whenever Body of Knowledge is dealt damage, draw that many cards.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
                new DrawCardSourceControllerEffect(SavedDamageValue.MANY), false));
    }

    private BodyOfKnowledge(final BodyOfKnowledge card) {
        super(card);
    }

    @Override
    public BodyOfKnowledge copy() {
        return new BodyOfKnowledge(this);
    }
}
