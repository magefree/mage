package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class MazirekKraulDeathPriest extends CardImpl {

    public MazirekKraulDeathPriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a player sacrifices another permanent, put a +1/+1 counter on each creature you control.
        this.addAbility(new SacrificePermanentTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        ), StaticFilters.FILTER_ANOTHER_PERMANENT, TargetController.ANY));
    }

    private MazirekKraulDeathPriest(final MazirekKraulDeathPriest card) {
        super(card);
    }

    @Override
    public MazirekKraulDeathPriest copy() {
        return new MazirekKraulDeathPriest(this);
    }
}
