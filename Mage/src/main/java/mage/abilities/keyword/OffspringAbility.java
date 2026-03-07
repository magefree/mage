package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.*;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    static String getActivationValueKey(UUID abilityOriginalId) {
        return OFFSPRING_ACTIVATION_VALUE_KEY + abilityOriginalId;
    }

    String getActivationValueKey() {
        return getActivationValueKey(offspringTriggeredAbility.getOriginalId());
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
        String activationValueKey = getActivationValueKey();
        ability.setCostsTag(activationValueKey, null);
        if (!isIntrinsicOffspringAbility(game, ability)) {
            game.addDelayedTriggeredAbility(
                    new OffspringDelayedTriggeredAbility(
                            sourceCardHasIntrinsicOffspring(game, ability),
                            activationValueKey
                    ),
                    ability
            );
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

    private boolean isIntrinsicOffspringAbility(Game game, Ability spellAbility) {
        Card sourceCard = game.getCard(spellAbility.getSourceId());
        return sourceCard != null && sourceCard
                .getAbilities()
                .stream()
                .filter(OffspringAbility.class::isInstance)
                .map(Ability::getId)
                .anyMatch(this.getId()::equals);
    }

    private boolean sourceCardHasIntrinsicOffspring(Game game, Ability spellAbility) {
        Card sourceCard = game.getCard(spellAbility.getSourceId());
        return sourceCard != null
                && sourceCard.getAbilities().stream().anyMatch(OffspringAbility.class::isInstance);
    }

    public static void syncActivationTagsWithCurrentSpellAbilities(Spell spell, Game game) {
        SpellAbility spellAbility = spell.getSpellAbility();
        Map<String, Object> costsTagMap = spellAbility.getCostsTagMap();
        if (costsTagMap == null || costsTagMap.isEmpty()) {
            return;
        }

        Set<String> activeOffspringActivationKeys = spell
                .getAbilities(game)
                .stream()
                .filter(OffspringAbility.class::isInstance)
                .map(OffspringAbility.class::cast)
                .map(OffspringAbility::getActivationValueKey)
                .collect(Collectors.toSet());

        costsTagMap.keySet().removeIf(tag ->
                tag.startsWith(OFFSPRING_ACTIVATION_VALUE_KEY)
                        && !activeOffspringActivationKeys.contains(tag)
        );
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
                game,
                this,
                OffspringAbility.getActivationValueKey(this.getOriginalId())
        );
    }
}

class OffspringDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final boolean skipIfNoAbilitiesOnEtb;
    private final String activationValueKey;

    OffspringDelayedTriggeredAbility(boolean skipIfNoAbilitiesOnEtb, String activationValueKey) {
        super(new OffspringEffect(), Duration.EndOfTurn, true);
        this.skipIfNoAbilitiesOnEtb = skipIfNoAbilitiesOnEtb;
        this.activationValueKey = activationValueKey;
        this.setRuleVisible(false);
    }

    private OffspringDelayedTriggeredAbility(final OffspringDelayedTriggeredAbility ability) {
        super(ability);
        this.skipIfNoAbilitiesOnEtb = ability.skipIfNoAbilitiesOnEtb;
        this.activationValueKey = ability.activationValueKey;
    }

    @Override
    public OffspringDelayedTriggeredAbility copy() {
        return new OffspringDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(getSourceId())) {
            return false;
        }
        if (!(event instanceof EntersTheBattlefieldEvent)) {
            return false;
        }
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent == null
                || permanent.getZoneChangeCounter(game) != getStackMomentSourceZCC() + 1) {
            return false;
        }
        if (!CardUtil.checkSourceCostsTagExists(game, this, activationValueKey)) {
            return false;
        }
        if (skipIfNoAbilitiesOnEtb && permanent.getAbilities().isEmpty()) {
            // Printed offspring was stripped from the entering creature (e.g. by Humility),
            // so the granted offspring fallback must also not trigger.
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(permanent, game));
        return true;
    }
}
