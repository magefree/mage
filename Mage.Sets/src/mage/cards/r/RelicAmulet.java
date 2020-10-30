package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveAllCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RelicAmulet extends CardImpl {

    public RelicAmulet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Whenever you cast an instant, sorcery, or Wizard spell, put a charge counter on Relic Amulet.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance()),
                StaticFilters.FILTER_SPELL_INSTANT_SORCERY_WIZARD, false
        ));

        // {2}, {T}, Remove all charge counters from Relic Amulet: It deals that much damage to target creature.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(RelicAmuletValue.instance)
                        .setText("it deals that much damage to target creature"),
                new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveAllCountersSourceCost(CounterType.CHARGE));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RelicAmulet(final RelicAmulet card) {
        super(card);
    }

    @Override
    public RelicAmulet copy() {
        return new RelicAmulet(this);
    }
}

enum RelicAmuletValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int countersRemoved = 0;
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof RemoveAllCountersSourceCost) {
                countersRemoved = ((RemoveAllCountersSourceCost) cost).getRemovedCounters();
            }
        }
        return countersRemoved;
    }

    @Override
    public RelicAmuletValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
