package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.ManaValueTargetAdjuster;

/**
 *
 * @author Grath
 */
public final class SidisiRegentOfTheMire extends CardImpl {

    public SidisiRegentOfTheMire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}, Sacrifice a creature you control with mana value X other than Sidisi: Return target creature card with mana value X plus 1 from your graveyard to the battlefield. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), new TapSourceCost()
        );
        ability.addCost(new SidisiRegentOfTheMireCost());
        ability.addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card with mana value X plus 1 from your graveyard")));
        ability.setTargetAdjuster(new SidisiRegentOfTheMireAdjuster());
        this.addAbility(ability);
    }

    private SidisiRegentOfTheMire(final SidisiRegentOfTheMire card) {
        super(card);
    }

    @Override
    public SidisiRegentOfTheMire copy() {
        return new SidisiRegentOfTheMire(this);
    }
}

class SidisiRegentOfTheMireAdjuster extends ManaValueTargetAdjuster {

    public SidisiRegentOfTheMireAdjuster() {
        super(new AdditiveDynamicValue(GetXValue.instance, StaticValue.get(1)), ComparisonType.EQUAL_TO);
    }

}

class SidisiRegentOfTheMireCost extends VariableCostImpl {

    public SidisiRegentOfTheMireCost() {
        super(VariableCostType.NORMAL, "mana value X");
        this.text = "Sacrifice a creature you control with mana value X other than {this}";
    }

    protected SidisiRegentOfTheMireCost(final SidisiRegentOfTheMireCost cost) {
        super(cost);
    }

    @Override
    public SidisiRegentOfTheMireCost copy() {
        return new SidisiRegentOfTheMireCost(this);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        FilterPermanent filter = new FilterControlledCreaturePermanent("another creature with mana value X");
        filter.add(AnotherPredicate.instance);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        return new SacrificeTargetCost(filter);
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        return game.getBattlefield().getActivePermanents(
            StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE,
            source.getControllerId(), source, game
            ).stream().mapToInt(MageObject::getManaValue).max().orElse(0);
    }

}
