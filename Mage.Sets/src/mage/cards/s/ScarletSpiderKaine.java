package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MayhemAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScarletSpiderKaine extends CardImpl {

    public ScarletSpiderKaine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Scarlet Spider enters, you may discard a card. If you do, put a +1/+1 counter on him.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on him"),
                new DiscardCardCost()
        )));

        // Mayhem {B/R}
        this.addAbility(new MayhemAbility(this, "{B/R}"));
    }

    private ScarletSpiderKaine(final ScarletSpiderKaine card) {
        super(card);
    }

    @Override
    public ScarletSpiderKaine copy() {
        return new ScarletSpiderKaine(this);
    }
}
