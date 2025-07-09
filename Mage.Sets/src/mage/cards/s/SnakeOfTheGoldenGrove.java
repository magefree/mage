package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TributeNotPaidCondition;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.TributeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SnakeOfTheGoldenGrove extends CardImpl {

    public SnakeOfTheGoldenGrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.SNAKE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Tribute 3
        this.addAbility(new TributeAbility(3));

        // When Snake of the Golden Grove enters the battlefield, if tribute wasn't paid, you gain 4 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(4)).withInterveningIf(TributeNotPaidCondition.instance));
    }

    private SnakeOfTheGoldenGrove(final SnakeOfTheGoldenGrove card) {
        super(card);
    }

    @Override
    public SnakeOfTheGoldenGrove copy() {
        return new SnakeOfTheGoldenGrove(this);
    }
}
