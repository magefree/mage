package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.keyword.EndureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DescendantOfStorms extends CardImpl {

    public DescendantOfStorms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever this creature attacks, you may pay {1}{W}. If you do, it endures 1.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new EndureSourceEffect(1), new ManaCostsImpl<>("{1}{W}")
        )));
    }

    private DescendantOfStorms(final DescendantOfStorms card) {
        super(card);
    }

    @Override
    public DescendantOfStorms copy() {
        return new DescendantOfStorms(this);
    }
}
