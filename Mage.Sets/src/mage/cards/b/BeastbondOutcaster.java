package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeastbondOutcaster extends CardImpl {

    public BeastbondOutcaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Beastbond Outcaster enters the battlefield, if you control a creature with power 4 or greater, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)),
                FerociousCondition.instance, "When {this} enters the battlefield, " +
                "if you control a creature with power 4 or greater, draw a card."
        ).addHint(FerociousHint.instance));

        // Plot {1}{G}
        this.addAbility(new PlotAbility("{1}{G}"));
    }

    private BeastbondOutcaster(final BeastbondOutcaster card) {
        super(card);
    }

    @Override
    public BeastbondOutcaster copy() {
        return new BeastbondOutcaster(this);
    }
}
