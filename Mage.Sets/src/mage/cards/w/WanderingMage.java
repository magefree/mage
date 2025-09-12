
package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.PutCountersTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class WanderingMage extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Cleric or Wizard");

    static {
        filter.add(Predicates.or(
                SubType.CLERIC.getPredicate(),
                SubType.WIZARD.getPredicate()));
    }

    public WanderingMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // {W}, Pay 1 life: Prevent the next 2 damage that would be dealt to target creature this turn.
        Ability ability = new SimpleActivatedAbility(
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 2), new ManaCostsImpl<>("{W}"));
        ability.addCost(new PayLifeCost(1));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {U}: Prevent the next 1 damage that would be dealt to target Cleric or Wizard creature this turn.
        ability = new SimpleActivatedAbility(
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 1), new ManaCostsImpl<>("{U}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // {B}, Put a -1/-1 counter on a creature you control: Prevent the next 2 damage that would be dealt to target player this turn.
        ability = new SimpleActivatedAbility(
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 2), new ManaCostsImpl<>("{B}"));
        ability.addCost(new PutCountersTargetCost(CounterType.M1M1.createInstance()));
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private WanderingMage(final WanderingMage card) {
        super(card);
    }

    @Override
    public WanderingMage copy() {
        return new WanderingMage(this);
    }
}
