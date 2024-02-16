
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.KickerAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class KrosanDruid extends CardImpl {

    public KrosanDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Kicker {4}{G} (You may pay an additional {4}{G} as you cast this spell.)
        this.addAbility(new KickerAbility("{4}{G}"));

        // When Krosan Druid enters the battlefield, if it was kicked, you gain 10 life.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new GainLifeEffect(10)),
                KickedCondition.ONCE,
                "When {this} enters the battlefield, if it was kicked, you gain 10 life"
        ));
    }

    private KrosanDruid(final KrosanDruid card) {
        super(card);
    }

    @Override
    public KrosanDruid copy() {
        return new KrosanDruid(this);
    }
}
