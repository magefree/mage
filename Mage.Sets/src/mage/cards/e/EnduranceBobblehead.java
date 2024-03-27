package mage.cards.e;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.ColorlessManaCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

/**
 *
 * @author justinjohnson14
 */
public final class EnduranceBobblehead extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(SubType.BOBBLEHEAD.getPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);

    public EnduranceBobblehead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        
        this.subtype.add(SubType.BOBBLEHEAD);


        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
        // {3}, {T}: Up to X target creatures you control get +1/+0 and gain indestructible until end of turn, where X is the number of Bobbleheads you control as you activate this ability. Activate only as a sorcery.
        Ability ability = (new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P0.createInstance())
                        .setText("Up to X target creatures you control get +1/+0 and gain indestructible until end of turn, where X is the number of Bobbleheads you control as you activate this ability. Activate only as a sorcery"),
                new TapSourceCost()));
        ability.addCost(new GenericManaCost(3));
        ability.setTargetAdjuster(EnduranceBobbleheadAdjuster.instance);
        this.addAbility(ability);
    }

    private EnduranceBobblehead(final EnduranceBobblehead card) {
        super(card);
    }

    @Override
    public EnduranceBobblehead copy() {
        return new EnduranceBobblehead(this);
    }
}
enum EnduranceBobbleheadAdjuster implements TargetAdjuster {
    instance;

    private static FilterPermanent filter = new FilterPermanent();

    static{
        filter.add(SubType.BOBBLEHEAD.getPredicate());
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {

        int amount = game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_CREATURE, ability.getControllerId(), ability, game);

        ability.getTargets().clear();
        ability.addTarget(new TargetCreaturePermanent(0, amount));
    }
}