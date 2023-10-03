
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class CelestialAncient extends CardImpl {

    public CelestialAncient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast an enchantment spell, put a +1/+1 counter on each creature you control.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(),
                new FilterControlledCreaturePermanent()), StaticFilters.FILTER_SPELL_AN_ENCHANTMENT, false));
    }

    private CelestialAncient(final CelestialAncient card) {
        super(card);
    }

    @Override
    public CelestialAncient copy() {
        return new CelestialAncient(this);
    }
}
