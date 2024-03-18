package mage.cards.o;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.Target;
import mage.target.TargetPlayer;

/**
 *
 * @author DominionSpy
 */
public final class OfficiousInterrogation extends CardImpl {

    public OfficiousInterrogation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{U}");

        // This spell costs {W}{U} more to cast for each target beyond the first.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new OfficiousInterrogationCostIncreasingEffect())
                .setRuleAtTheTop(true));

        // Choose any number of target players. Investigate X times, where X is the total number of creatures those players control.
        this.getSpellAbility().addEffect(new InvestigateEffect(OfficiousInterrogationCount.instance)
                .setText("Choose any number of target players. Investigate X times, where X is " +
                        "the total number of creatures those players control."));
        this.getSpellAbility().addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
    }

    private OfficiousInterrogation(final OfficiousInterrogation card) {
        super(card);
    }

    @Override
    public OfficiousInterrogation copy() {
        return new OfficiousInterrogation(this);
    }
}

class OfficiousInterrogationCostIncreasingEffect extends CostModificationEffectImpl {

    OfficiousInterrogationCostIncreasingEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "This spell costs {W}{U} more to cast for each target beyond the first.";
    }

    private OfficiousInterrogationCostIncreasingEffect(final OfficiousInterrogationCostIncreasingEffect effect) {
        super(effect);
    }

    @Override
    public OfficiousInterrogationCostIncreasingEffect copy() {
        return new OfficiousInterrogationCostIncreasingEffect(this);
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getSourceId().equals(source.getSourceId()) &&
                (abilityToModify instanceof SpellAbility);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Target target = abilityToModify.getTargets().get(0);
        int additionalTargets = target.getTargets().size() - 1;
        if (additionalTargets > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < additionalTargets; i++) {
                sb.append("{W}{U}");
            }
            abilityToModify.addManaCostsToPay(new ManaCostsImpl<>(sb.toString()));
            return true;
        }
        return false;
    }
}

enum OfficiousInterrogationCount implements DynamicValue {
    instance;

    @Override
    public OfficiousInterrogationCount copy() {
        return instance;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int totalCreatureCount = 0;

        Target target = sourceAbility.getTargets().get(0);
        for (UUID playerId : target.getTargets()) {
            totalCreatureCount += game.getBattlefield()
                    .count(StaticFilters.FILTER_CONTROLLED_CREATURE, playerId, sourceAbility, game);
        }

        return totalCreatureCount;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the total number of creatures those players control";
    }
}
