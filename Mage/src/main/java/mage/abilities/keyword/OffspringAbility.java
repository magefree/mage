package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.TriggeredAbility;
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
    private String activationKey;

    public OffspringAbility(String manaString) {
        this(new ManaCostsImpl<>(manaString));
    }

    public OffspringAbility(Cost cost) {
        super(Zone.STACK, null);
        this.activationKey = OFFSPRING_ACTIVATION_VALUE_KEY + "_" + this.getId();
        this.additionalCost = new OptionalAdditionalCostImpl(
                keywordText + ' ' + cost.getText(),
                String.format(reminderText, cost.getText()), cost
        );
        this.additionalCost.setRepeatable(false);
        this.rule = additionalCost.getName() + ' ' + additionalCost.getReminderText();
        this.setRuleAtTheTop(true);
        this.addSubAbility(new EntersBattlefieldTriggeredAbility(new OffspringEffect())
                .withInterveningIf(new OffspringCondition(this.activationKey)).setRuleVisible(false));
    }

    protected OffspringAbility(final OffspringAbility ability) {
        super(ability);
        this.activationKey = ability.activationKey;
        this.rule = ability.rule;
        this.additionalCost = ability.additionalCost.copy();
    }

    @Override
    public void newId() {
        super.newId();
        this.activationKey = OFFSPRING_ACTIVATION_VALUE_KEY + "_" + this.getId();
        for (Ability sub : getSubAbilities()) {
            if (sub instanceof TriggeredAbility) {
                ((TriggeredAbility) sub).withInterveningIf(new OffspringCondition(this.activationKey));
            }
        }
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
        ability.addCost(additionalCost.copy());
        ability.setCostsTag(this.activationKey, null);
        mage.cards.Card card = game.getCard(ability.getSourceId());
        if (card != null && !card.getAbilities().contains(this)) {
            ability.setCostsTag("offspring_trigger_" + this.activationKey,
                    new EntersBattlefieldTriggeredAbility(new OffspringEffect())
                            .withInterveningIf(new OffspringCondition(this.activationKey)).setRuleVisible(false));
        }
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

class OffspringCondition implements Condition {

    private final String activationKey;

    OffspringCondition(String activationKey) {
        this.activationKey = activationKey;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.checkSourceCostsTagExists(game, source, activationKey);
    }

    @Override
    public String toString() {
        return "its offspring cost was paid";
    }
}
