package mage.cards.t;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 * @author TheElk801
 */
public final class TaintedObserver extends CardImpl {

    public TaintedObserver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Toxic 1
        this.addAbility(new ToxicAbility(1));

        // Whenever another creature enters the battlefield under your control, you may pay {2}. If you do, proliferate.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DoIfCostPaid(new ProliferateEffect(), new GenericManaCost(2)),
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ));
    }

    private TaintedObserver(final TaintedObserver card) {
        super(card);
    }

    @Override
    public TaintedObserver copy() {
        return new TaintedObserver(this);
    }
}
