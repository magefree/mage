package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BillowingShriekmass extends CardImpl {

    public BillowingShriekmass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, mill three cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(3)));

        // Threshold -- This creature gets +2/+1 as long as there are seven or more cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 1, Duration.WhileOnBattlefield), ThresholdCondition.instance,
                "this creature gets +2/+1 as long as there are seven or more cards in your graveyard"
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private BillowingShriekmass(final BillowingShriekmass card) {
        super(card);
    }

    @Override
    public BillowingShriekmass copy() {
        return new BillowingShriekmass(this);
    }
}
