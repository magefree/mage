package mage.cards.d;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.MayPay2LifeForColorAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.PermanentPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DefilerOfVigor extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a green permanent spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter.add(PermanentPredicate.instance);
    }

    public DefilerOfVigor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // As an additional cost to cast green permanent spells, you may pay 2 life. Those spells cost {G} less to cast if you paid life this way. This effect reduces only the amount of green mana you pay.
        this.addAbility(new MayPay2LifeForColorAbility(ObjectColor.GREEN));

        // Whenever you cast a green permanent spell, put a +1/+1 counter on each creature you control.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersAllEffect(
                        CounterType.P1P1.createInstance(),
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ), filter, false
        ));
    }

    private DefilerOfVigor(final DefilerOfVigor card) {
        super(card);
    }

    @Override
    public DefilerOfVigor copy() {
        return new DefilerOfVigor(this);
    }
}
