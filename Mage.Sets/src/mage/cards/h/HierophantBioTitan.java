package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class HierophantBioTitan extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public HierophantBioTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{10}{G}{G}");
        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(12);
        this.toughness = new MageInt(12);

        // Frenzied Metabolism — As an additional cost to cast this spell, you may remove any number of +1/+1 counters
        // from among creatures you control. This spell costs {2} less to cast for each counter removed this way.
        Cost cost = new RemoveVariableCountersTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE, CounterType.P1P1).setText("");
        this.getSpellAbility().addCost(cost);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new HierophantBioTitanCostReductionEffect()).withFlavorWord("Frenzied Metabolism"));

        // Vigilance, reach, ward {2}
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(ReachAbility.getInstance());
        this.addAbility(new WardAbility(new GenericManaCost(2), false));

        // Titanic — Hierophant Bio-Titan can't be blocked by creatures with power 2 or less.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)).withFlavorWord("Titanic"));
    }

    private HierophantBioTitan(final HierophantBioTitan card) {
        super(card);
    }

    @Override
    public HierophantBioTitan copy() {
        return new HierophantBioTitan(this);
    }
}

class HierophantBioTitanCostReductionEffect extends CostModificationEffectImpl {

    HierophantBioTitanCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "As an additional cost to cast this spell, you may remove any " +
                "number of +1/+1 counters from among creatures you control. This spell " +
                "costs {2} less to cast for each counter removed this way.";
    }

    private HierophantBioTitanCostReductionEffect(final HierophantBioTitanCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        int reduction = 0;
        for (Cost cost : spellAbility.getCosts()) {
            if (!(cost instanceof RemoveVariableCountersTargetCost)) {
                continue;
            }
            if (game.inCheckPlayableState()) {
                // allows to cast in getPlayable
                reduction = ((RemoveVariableCountersTargetCost) cost).getMaxValue(spellAbility, game);
            } else {
                // real cast
                reduction = ((RemoveVariableCountersTargetCost) cost).getAmount();
            }
            break;
        }
        CardUtil.adjustCost(spellAbility, reduction * 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility && abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public HierophantBioTitanCostReductionEffect copy() {
        return new HierophantBioTitanCostReductionEffect(this);
    }
}
