package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlastZone extends CardImpl {

    public BlastZone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");


        // Blast Zone enters the battlefield with a charge counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance(1)),
                "with a charge counter on it"
        ));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {X}{X}, {T}: Put X charge counters on Blast Zone.
        Ability ability = new SimpleActivatedAbility(new AddCountersSourceEffect(
                CounterType.CHARGE.createInstance(), ManacostVariableValue.REGULAR, true
        ), new ManaCostsImpl<>("{X}{X}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {3}, {T}, Sacrifice Blast Zone: Destroy each nonland permanent with converted mana cost equal to the number of charge counters on Blast Zone.
        ability = new SimpleActivatedAbility(new BlastZoneEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private BlastZone(final BlastZone card) {
        super(card);
    }

    @Override
    public BlastZone copy() {
        return new BlastZone(this);
    }
}

class BlastZoneEffect extends OneShotEffect {

    BlastZoneEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy each nonland permanent with mana value " +
                "equal to the number of charge counters on {this}";
    }

    private BlastZoneEffect(final BlastZoneEffect effect) {
        super(effect);
    }

    @Override
    public BlastZoneEffect copy() {
        return new BlastZoneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        int xValue = permanent.getCounters(game).getCount(CounterType.CHARGE);
        FilterPermanent filter = new FilterNonlandPermanent();
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        return new DestroyAllEffect(filter).apply(game, source);
    }
}