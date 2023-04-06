package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InfectedDefector extends CardImpl {

    public InfectedDefector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Infected Defector dies, incubate 3.
        this.addAbility(new DiesSourceTriggeredAbility(new IncubateEffect(3)));
    }

    private InfectedDefector(final InfectedDefector card) {
        super(card);
    }

    @Override
    public InfectedDefector copy() {
        return new InfectedDefector(this);
    }
}
