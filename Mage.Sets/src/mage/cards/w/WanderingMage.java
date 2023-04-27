
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

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
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 2), new ManaCostsImpl<>("{W}"));
        ability.addCost(new PayLifeCost(1));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {U}: Prevent the next 1 damage that would be dealt to target Cleric or Wizard creature this turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 1), new ManaCostsImpl<>("{U}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // {B}, Put a -1/-1 counter on a creature you control: Prevent the next 2 damage that would be dealt to target player this turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 2), new ManaCostsImpl<>("{B}"));
        ability.addCost(new WanderingMageCost());
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        Target target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        ability.addTarget(target);
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

class WanderingMageCost extends CostImpl {

    public WanderingMageCost() {
        this.text = "Put a -1/-1 counter on a creature you control";
    }

    public WanderingMageCost(WanderingMageCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(ability.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            permanent.addCounters(CounterType.M1M1.createInstance(), controllerId, ability, game);
            this.paid = true;
        }
        return paid;
    }

    @Override
    public WanderingMageCost copy() {
        return new WanderingMageCost(this);
    }
}
