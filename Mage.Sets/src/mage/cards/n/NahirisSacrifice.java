package mage.cards.n;

import mage.abilities.costs.Cost;
import mage.abilities.costs.SacrificeCost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.UUID;

/**
 * @author miesma
 */
public final class NahirisSacrifice extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an artifact or creature");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate()));
    }

    /**
     * Need to annouce X before sacrificing in step 601.2B as costs aren't paid until 601.2H while target are chosen
     * in step  601.2C and damange split in 601.2D at which time nothing was sacrificed as of targeting.
     */
    public NahirisSacrifice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // As an additional cost to cast this spell, sacrifice an artifact or creature with mana value X.
        this.getSpellAbility().addCost(new SacrificeXManaValueCost(filter,true));

        // Nahiri’s Sacrifice deals X damage divided as you choose among any number of target creatures.
        Effect effect = new DamageMultiEffect(GetXValue.instance);
        effect.setText("{this} deals X damage divided as you choose among any number of target creatures.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(GetXValue.instance));
    }

    private NahirisSacrifice(final NahirisSacrifice card) {
        super(card);
    }

    @Override
    public NahirisSacrifice copy() {
        return new NahirisSacrifice(this);
    }
}

/**
 * Potentially a 'common' cost if costs like these are used again
 */
class SacrificeXManaValueCost extends VariableCostImpl implements SacrificeCost {

    protected final FilterControlledPermanent filter;

    public SacrificeXManaValueCost(FilterControlledPermanent filter, boolean useAsAdditionalCost) {
        super(useAsAdditionalCost ? VariableCostType.ADDITIONAL : VariableCostType.NORMAL,
                filter.getMessage() + " with manavalue X to sacrifice");
        this.text = (useAsAdditionalCost ? "sacrifice " : "Sacrifice ") + filter.getMessage() + " with mana value " +xText;
        this.filter = filter;
    }

    private SacrificeXManaValueCost(final SacrificeXManaValueCost cost) {
        super(cost);
        this.filter = cost.filter;
    }

    @Override
    public SacrificeXManaValueCost copy() {
        return new SacrificeXManaValueCost(this);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        //Add the announced cost to the filter
        FilterControlledPermanent manavaluefilter = new FilterControlledPermanent(filter.getMessage() + " with mana value "+xValue);
        manavaluefilter.add(filter.getPredicates().get(0));
        manavaluefilter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        return new SacrificeTargetCost(manavaluefilter);
    }

}
