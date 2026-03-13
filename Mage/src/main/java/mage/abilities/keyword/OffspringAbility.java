package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.*;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class OffspringAbility extends StaticAbility implements OptionalAdditionalSourceCosts {

    private static final String keywordText = "Offspring";
    private static final String reminderText = "You may pay an additional %s as you cast this spell. If you do, when this creature enters, create a 1/1 token copy of it.";
    private final String rule;
    private final OffspringTriggeredAbility offspringTriggeredAbility;

    public static final String OFFSPRING_ACTIVATION_VALUE_KEY = "offspringActivation";

    protected OptionalAdditionalCost additionalCost;

    static String getActivationValueKey(Ability ability) {
        return CardUtil.getLinkedCostTag(ability, OFFSPRING_ACTIVATION_VALUE_KEY);
    }

    String getActivationValueKey() {
        return getActivationValueKey(offspringTriggeredAbility);
    }

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
        this.offspringTriggeredAbility = new OffspringTriggeredAbility();
        this.addSubAbility(offspringTriggeredAbility);
        this.setRuleAtTheTop(true);
    }

    private OffspringAbility(final OffspringAbility ability) {
        super(ability);
        this.rule = ability.rule;
        this.additionalCost = ability.additionalCost.copy();
        this.offspringTriggeredAbility = this.getSubAbilities().stream()
                .filter(OffspringTriggeredAbility.class::isInstance)
                .map(OffspringTriggeredAbility.class::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Offspring triggered ability wasn't found"));
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
        ability.setCostsTag(getActivationValueKey(), null);
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
        Permanent permanent = null;
        List<UUID> pointerTargets = getTargetPointer().getTargets(game, source);
        if (pointerTargets != null && !pointerTargets.isEmpty()) {
            permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        }
        if (permanent == null) {
            permanent = source.getSourcePermanentOrLKI(game);
        }
        return permanent != null && new CreateTokenCopyTargetEffect(
                null, null, false, 1, false,
                false, null, 1, 1, false
        ).setSavedPermanent(permanent).apply(game, source);
    }
}

class OffspringTriggeredAbility extends EntersBattlefieldTriggeredAbility {

    OffspringTriggeredAbility() {
        super(new OffspringEffect(), false);
        setTriggerPhrase("When this permanent enters, ");
        this.setRuleVisible(false);
    }

    private OffspringTriggeredAbility(final OffspringTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OffspringTriggeredAbility copy() {
        return new OffspringTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        return CardUtil.checkSourceCostsTagExists(
                game, this, OffspringAbility.getActivationValueKey(this)
        );
    }
}
