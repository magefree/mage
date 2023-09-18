package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.OpponentsLostLifeCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.hint.common.OpponentsLostLifeHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodtitheCollector extends CardImpl {

    public BloodtitheCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Bloodtithe Collector enters the battlefield, if an opponent lost life this turn, each opponent discards a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT)),
                OpponentsLostLifeCondition.instance, "When {this} enters the battlefield, " +
                "if an opponent lost life this turn, each opponent discards a card."
        ).addHint(OpponentsLostLifeHint.instance));
    }

    private BloodtitheCollector(final BloodtitheCollector card) {
        super(card);
    }

    @Override
    public BloodtitheCollector copy() {
        return new BloodtitheCollector(this);
    }
}
