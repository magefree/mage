package mage.cards.w;

import java.util.UUID;

import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

/**
 *
 * @author muz
 */
public final class WeatherMaker extends CardImpl {

    public WeatherMaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Landfall - Whenever a land you control enters, put a charge counter on this artifact.
        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance())));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {T}, Remove two charge counters from this artifact: Add {C}{C}.
        Ability manaAbility = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(2), new TapSourceCost());
        manaAbility.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(2)));
        this.addAbility(manaAbility);

        // {T}, Remove three charge counters from this artifact: It deals 3 damage to any target.
        Ability damageAbility = new SimpleActivatedAbility(new DamageTargetEffect(3), new TapSourceCost());
        damageAbility.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(3)));
        damageAbility.addTarget(new TargetAnyTarget());
        this.addAbility(damageAbility);
    }

    private WeatherMaker(final WeatherMaker card) {
        super(card);
    }

    @Override
    public WeatherMaker copy() {
        return new WeatherMaker(this);
    }
}
