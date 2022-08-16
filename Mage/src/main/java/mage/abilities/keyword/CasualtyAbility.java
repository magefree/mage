package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.*;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CopySourceSpellEffect;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 * @author TheElk801, Alex-Vasile
 */
public class CasualtyAbility extends StaticAbility implements OptionalAdditionalSourceCosts {

    private static final String keywordText = "Casualty";
    private final String promptString;

    protected OptionalAdditionalCost additionalCost;

    private static TargetControlledPermanent makeFilter(int number) {
        FilterControlledPermanent filter = new FilterControlledCreaturePermanent(
                "creature with power " + number + " or greater"
        );
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, number - 1));
        return new TargetControlledPermanent(1, 1, filter, true);
    }

    public CasualtyAbility(int number) {
        super(Zone.STACK, null);
        String reminderText = "As you cast this spell, you may sacrifice a creature with power " +
                number + " or greater. When you do, copy this spell.";
        this.promptString = "Sacrifice a creature with power " + number + " or greater?";
        this.additionalCost = new OptionalAdditionalCostImpl(keywordText, reminderText, new SacrificeTargetCost(makeFilter(number)));
        this.additionalCost.setRepeatable(false);
        this.setRuleAtTheTop(true);
    }

    private CasualtyAbility(final CasualtyAbility ability) {
        super(ability);
        this.additionalCost = ability.additionalCost;
        this.promptString = ability.promptString;
    }

    public void resetCasualty() {
        if (additionalCost != null) {
            additionalCost.reset();
        }
    }

    @Override
    public CasualtyAbility copy() {
        return new CasualtyAbility(this);
    }

    @Override
    public void addOptionalAdditionalCosts(Ability ability, Game game) {
        if (!(ability instanceof SpellAbility)) {
            return;
        }

        Player player = game.getPlayer(ability.getControllerId());
        if (player == null) {
            return;
        }

        this.resetCasualty();
        boolean canPay = additionalCost.canPay(ability, this, ability.getControllerId(), game);
        if (!canPay || !player.chooseUse(Outcome.Sacrifice, promptString, ability, game)) {
            return;
        }

        additionalCost.activate();
        for (Cost cost : ((Costs<Cost>) additionalCost)) {
            ability.getCosts().add(cost.copy());
        }
        game.fireReflexiveTriggeredAbility(new ReflexiveTriggeredAbility(
                new CopySourceSpellEffect(), false, "when you do, copy this spell"
        ), ability);
    }

    @Override
    public String getCastMessageSuffix() {
        return additionalCost == null ? "" : additionalCost.getCastSuffixMessage(0);
    }
}
