package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.token.KnightToken;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author jack-the-BOSS
 */
public final class AryelKnightOfWindgrace extends CardImpl {

    public AryelKnightOfWindgrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {2}{W}, {T}: Create a 2/2 white Knight creature token with vigilance.
        Ability tokenAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new KnightToken()), new ManaCostsImpl("{2}{W}"));
        tokenAbility.addCost(new TapSourceCost());
        this.addAbility(tokenAbility);

        // {B}, {T}, Tap X untapped Knights you control: Destroy target creature with power X or less.
        //Simple costs
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect()
                .setText("Destroy target creature with power X or less"), new ManaCostsImpl("{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new AryelTapXTargetCost());
        ability.setTargetAdjuster(AryelKnightOfWindgraceAdjuster.instance);
        this.addAbility(ability);
    }

    private AryelKnightOfWindgrace(final AryelKnightOfWindgrace card) {
        super(card);
    }

    @Override
    public AryelKnightOfWindgrace copy() {
        return new AryelKnightOfWindgrace(this);
    }
}

class AryelTapXTargetCost extends VariableCostImpl {

    static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Knights you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.KNIGHT.getPredicate());
    }

    public AryelTapXTargetCost() {
        super(VariableCostType.NORMAL, "controlled untapped Knights you would like to tap");
        this.text = "Tap X untapped Knights you control";
    }

    public AryelTapXTargetCost(final AryelTapXTargetCost cost) {
        super(cost);
    }

    @Override
    public AryelTapXTargetCost copy() {
        return new AryelTapXTargetCost(this);
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        return game.getBattlefield().count(filter, source.getControllerId(), source, game);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        TargetControlledPermanent target = new TargetControlledPermanent(xValue, xValue, filter, true);
        return new TapTargetCost(target);
    }
}

enum AryelKnightOfWindgraceAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int value = 0;
        for (VariableCost cost : ability.getCosts().getVariableCosts()) {
            value = cost.getAmount();
        }
        FilterCreaturePermanent filterCreaturePermanent = new FilterCreaturePermanent("creature with power " + value + " or less");
        filterCreaturePermanent.add(new PowerPredicate(ComparisonType.FEWER_THAN, value + 1));
        ability.getTargets().clear();
        ability.addTarget(new TargetCreaturePermanent(filterCreaturePermanent));
    }
}
