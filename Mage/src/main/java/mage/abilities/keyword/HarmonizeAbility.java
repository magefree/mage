package mage.abilities.keyword;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class HarmonizeAbility extends CastFromGraveyardAbility {

    private String abilityName;
    private SpellAbility spellAbilityToResolve;

    public HarmonizeAbility(Card card, String manaString) {
        super(card, new ManaCostsImpl<>(manaString), SpellAbilityCastMode.HARMONIZE);
        this.addCost(new HarmonizeCost());
        this.addSubAbility(new SimpleStaticAbility(Zone.ALL, new HarmonizeCostReductionEffect()).setRuleVisible(false));
    }

    private HarmonizeAbility(final HarmonizeAbility ability) {
        super(ability);
    }

    @Override
    public HarmonizeAbility copy() {
        return new HarmonizeAbility(this);
    }

    @Override
    public String getRule() {
        return name + " <i>(You may cast this card from your graveyard for its harmonize cost. " +
                "You may tap a creature you control to reduce that cost by {X}, " +
                "where X is its power. Then exile this spell.)</i>";
    }
}

class HarmonizeCostReductionEffect extends CostModificationEffectImpl {

    HarmonizeCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
    }

    private HarmonizeCostReductionEffect(final HarmonizeCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        int power;
        if (game.inCheckPlayableState()) {
            power = game
                    .getBattlefield()
                    .getActivePermanents(
                            StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE,
                            source.getControllerId(), source, game
                    ).stream()
                    .map(MageObject::getPower)
                    .mapToInt(MageInt::getValue)
                    .max()
                    .orElse(0);
        } else {
            power = CardUtil
                    .castStream(spellAbility.getCosts().stream(), HarmonizeCost.class)
                    .map(HarmonizeCost::getChosenCreature)
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .map(MageObject::getPower)
                    .mapToInt(MageInt::getValue)
                    .map(x -> Math.max(x, 0))
                    .sum();
        }
        if (power > 0) {
            CardUtil.adjustCost(spellAbility, power);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public HarmonizeCostReductionEffect copy() {
        return new HarmonizeCostReductionEffect(this);
    }
}

class HarmonizeCost extends VariableCostImpl {

    private UUID chosenCreature = null;

    HarmonizeCost() {
        super(VariableCostType.ADDITIONAL, "", "");
    }

    private HarmonizeCost(final HarmonizeCost cost) {
        super(cost);
        this.chosenCreature = cost.chosenCreature;
    }

    @Override
    public HarmonizeCost copy() {
        return new HarmonizeCost(this);
    }

    @Override
    public void clearPaid() {
        super.clearPaid();
        chosenCreature = null;
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        return game.getBattlefield().contains(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE, source, game, 1) ? 1 : 0;
    }

    @Override
    public int announceXValue(Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE, source, game, 1
        ) || !player.chooseUse(
                Outcome.Benefit, "Tap an untapped creature you control for harmonize?", source, game
        )) {
            return 0;
        }
        TargetPermanent target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE);
        target.withNotTarget(true);
        target.withChooseHint("for harmonize");
        player.choose(Outcome.PlayForFree, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return 0;
        }
        chosenCreature = permanent.getId();
        return 1;
    }

    private FilterControlledPermanent makeFilter() {
        FilterControlledPermanent filter = new FilterControlledPermanent("tap the chosen creature");
        filter.add(new PermanentIdPredicate(chosenCreature));
        return filter;
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new TapTargetCost(new TargetControlledPermanent(xValue, xValue, makeFilter(), true));
    }

    public UUID getChosenCreature() {
        return chosenCreature;
    }
}
