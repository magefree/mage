package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.*;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class OffspringAbility extends StaticAbility implements OptionalAdditionalSourceCosts {

    private static final String keywordText = "Offspring";
    private static final String reminderText = "You may pay an additional %s as you cast this spell. If you do, when this creature enters, create a 1/1 token copy of it.";
    private final String rule;

    public static final String OFFSPRING_ACTIVATION_VALUE_KEY = "offspringActivation";

    protected OptionalAdditionalCost additionalCost;

    public OffspringAbility(String manaString) {
        this(new ManaCostsImpl<>(manaString));
    }

    public OffspringAbility(Cost cost) {
        super(Zone.STACK, null);
        this.additionalCost = new OptionalAdditionalCostImpl(
                keywordText + ' ' + cost.getText(),
                String.format(reminderText, cost.getText()), cost
        );
        this.additionalCost.setRepeatable(false);
        this.rule = additionalCost.getName() + ' ' + additionalCost.getReminderText();
        this.setRuleAtTheTop(true);
        this.addSubAbility(new EntersBattlefieldTriggeredAbility(new OffspringEffect())
                .withInterveningIf(OffspringCondition.instance).setRuleVisible(false));
    }

    private OffspringAbility(final OffspringAbility ability) {
        super(ability);
        this.rule = ability.rule;
        this.additionalCost = ability.additionalCost.copy();
    }

    @Override
    public OffspringAbility copy() {
        return new OffspringAbility(this);
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
        additionalCost.reset();
        if (!additionalCost.canPay(ability, this, ability.getControllerId(), game)
                || !player.chooseUse(Outcome.PutCreatureInPlay, "Pay " + additionalCost.getText(true) + " for offspring?", ability, game)) {
            return;
        }
        additionalCost.activate();
        for (Cost cost : ((Costs<Cost>) additionalCost)) {
            ability.getCosts().add(cost.copy());
        }
        ability.setCostsTag(OFFSPRING_ACTIVATION_VALUE_KEY, null);
    }

    @Override
    public String getCastMessageSuffix() {
        return additionalCost.getCastSuffixMessage(0);
    }

    @Override
    public String getRule() {
        return rule;
    }
}

class OffspringEffect extends OneShotEffect {

    OffspringEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 token copy of it";
    }

    private OffspringEffect(final OffspringEffect effect) {
        super(effect);
    }

    @Override
    public OffspringEffect copy() {
        return new OffspringEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        return permanent != null && new CreateTokenCopyTargetEffect(
                null, null, false, 1, false,
                false, null, 1, 1, false
        ).setSavedPermanent(permanent).apply(game, source);
    }
}

enum OffspringCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.checkSourceCostsTagExists(game, source, OffspringAbility.OFFSPRING_ACTIVATION_VALUE_KEY);
    }

    @Override
    public String toString() {
        return "its offspring cost was paid";
    }
}
