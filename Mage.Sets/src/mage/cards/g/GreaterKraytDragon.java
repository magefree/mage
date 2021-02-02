
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class GreaterKraytDragon extends CardImpl {

    public GreaterKraytDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{G}{W}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // {X}{X}{R}{G}{W}: Monstrosity X.
        this.addAbility(new MonstrosityAbility("{X}{X}{R}{G}{W}", Integer.MAX_VALUE));

        //  When Greater Krayt Dragon becomes monstrous, draw a card for each +1/+1 counter on creatures you control.
        this.addAbility(new BecomesMonstrousSourceTriggeredAbility(new DrawCardSourceControllerEffect(new CountersCount(CounterType.P1P1, new FilterControlledCreaturePermanent()))));

    }

    private GreaterKraytDragon(final GreaterKraytDragon card) {
        super(card);
    }

    @Override
    public GreaterKraytDragon copy() {
        return new GreaterKraytDragon(this);
    }
}
