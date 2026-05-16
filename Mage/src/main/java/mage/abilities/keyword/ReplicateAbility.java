package mage.abilities.keyword;

import mage.Mana;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.*;
import mage.abilities.mana.ManaOptions;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class ReplicateAbility extends StaticAbility implements OptionalAdditionalSourceCosts {

    public static final String REPLICATE_COST_PLAN_KEY_PREFIX = "optional-additional:replicate:";
    private static final String REPLICATE_COST_PLAN_KEY = "optional-additional:replicate";

    private static final String keywordText = "Replicate";
    private static final String reminderTextMana = "When you cast this spell, "
            + "copy it for each time you paid its replicate cost."
            + " You may choose new targets for the copies.";
    protected OptionalAdditionalCost additionalCost;

    public ReplicateAbility(String manaString) {
        this(new ManaCostsImpl<>(manaString));
    }

    public ReplicateAbility(Cost cost) {
        super(Zone.STACK, null);
        this.additionalCost = new OptionalAdditionalCostImpl(keywordText, reminderTextMana, cost);
        this.additionalCost.setRepeatable(true);
        setRuleAtTheTop(true);
        addSubAbility(new ReplicateTriggeredAbility(this.getId(), this.additionalCost.getText(true)));
    }

    protected ReplicateAbility(final ReplicateAbility ability) {
        super(ability);
        additionalCost = ability.additionalCost;
    }

    @Override
    public ReplicateAbility copy() {
        return new ReplicateAbility(this);
    }

    @Override
    public void addCost(Cost cost) {
        if (additionalCost != null) {
            ((Costs) additionalCost).add(cost);
        }
    }

    @Override
    public boolean isActivated() {
        return additionalCost != null && additionalCost.isActivated();
    }

    public int getActivateCount() {
        return additionalCost == null ? 0 : additionalCost.getActivateCount();
    }

    public void resetReplicate() {
        if (additionalCost != null) {
            additionalCost.reset();
        }
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

        this.resetReplicate();
        int plannedCount = CastCostPlan.getOptionalAdditionalCostCount(ability, getCostPlanKey());
        if (plannedCount == 0 && hasSingleReplicateAbility(ability.getSourceObject(game), game)) {
            plannedCount = CastCostPlan.getOptionalAdditionalCostCount(ability, REPLICATE_COST_PLAN_KEY);
        }
        if (plannedCount > 0) {
            for (int i = 0; i < plannedCount && player.canRespond(); i++) {
                additionalCost.activate();
                addReplicateCostToAbility(ability);
            }
            return;
        }

        boolean again = true;
        while (player.canRespond() && again) {
            String times = "";
            if (additionalCost.isRepeatable()) {
                int numActivations = additionalCost.getActivateCount();
                times = (numActivations + 1) + (numActivations == 0 ? " time " : " times ");
            }
            String payPrompt = "Pay " + times + additionalCost.getText(false) + " ?";

            // TODO: add AI support to find max number of possible activations (from available mana)
            //  canPay checks only single mana available, not total mana usage
            boolean canPay = additionalCost.canPay(ability, this, ability.getControllerId(), game);
            if (!canPay || !player.chooseUse(/*Outcome.Benefit*/Outcome.AIDontUseIt, payPrompt, ability, game)) {
                again = false;
            } else {
                additionalCost.activate();
                addReplicateCostToAbility(ability);
            }
        }
    }

    @Override
    public List<Ability> getOptionalAdditionalCostVariants(Ability ability, Game game) {
        if (!(ability instanceof SpellAbility) || additionalCost == null || !additionalCost.isRepeatable()) {
            return new ArrayList<>();
        }
        List<Ability> variants = new ArrayList<>();
        for (int count = 1; count <= CastCostPlan.DEFAULT_MAX_REPEAT_COUNT; count++) {
            if (!canPayReplicateCount(ability, game, count)) {
                break;
            }
            Ability variant = CastCostPlan.withOptionalAdditionalCostCount(
                    ability,
                    getCostPlanKey(),
                    count,
                    "with-replicate-" + count
            );
            CastCostPlan.setOptionalAdditionalCostCount(variant, REPLICATE_COST_PLAN_KEY, count);
            variants.add(variant);
        }
        return variants;
    }

    public static int getSelectedReplicateCount(Ability ability) {
        return Math.max(
                CastCostPlan.getOptionalAdditionalCostCount(ability, REPLICATE_COST_PLAN_KEY),
                CastCostPlan.getOptionalAdditionalCostCountByPrefix(ability, REPLICATE_COST_PLAN_KEY_PREFIX)
        );
    }

    private String getCostPlanKey() {
        return getCostPlanKey(getSourceId(), getOriginalId(), additionalCost.getText(true));
    }

    static String getCostPlanKey(UUID sourceId, UUID fallbackId, String costText) {
        UUID stableSourceId = sourceId == null ? fallbackId : sourceId;
        return REPLICATE_COST_PLAN_KEY_PREFIX + stableSourceId + ':' + costText;
    }

    static boolean hasSingleReplicateAbility(MageObject sourceObject, Game game) {
        if (sourceObject == null) {
            return true;
        }
        int replicateAbilities = 0;
        for (Ability sourceAbility : CardUtil.getAbilities(sourceObject, game)) {
            if (sourceAbility instanceof ReplicateAbility) {
                replicateAbilities++;
            }
        }
        return replicateAbilities <= 1;
    }

    private boolean canPayReplicateCount(Ability ability, Game game, int count) {
        Player player = game.getPlayer(ability.getControllerId());
        if (player == null || !(ability instanceof ActivatedAbility)) {
            return false;
        }

        Ability testAbility = ability.copy();
        for (int i = 0; i < count; i++) {
            if (!additionalCost.canPay(testAbility, this, testAbility.getControllerId(), game)) {
                return false;
            }
            addReplicateCostToAbility(testAbility);
        }
        if (!((ActivatedAbility) testAbility).canActivate(testAbility.getControllerId(), game).canActivate()) {
            return false;
        }

        testAbility.adjustX(game);
        game.getContinuousEffects().costModification(testAbility, game);
        ManaOptions requiredMana = testAbility.getManaCostsToPay().getOptions(player.canPayLifeCost(testAbility));
        if (requiredMana.isEmpty()) {
            return true;
        }
        ManaOptions availableMana = player.getManaAvailable(game);
        for (Mana mana : requiredMana) {
            if (availableMana.enough(mana)) {
                return true;
            }
        }
        return false;
    }

    private void addReplicateCostToAbility(Ability ability) {
        for (Iterator it = ((Costs) additionalCost).iterator(); it.hasNext(); ) {
            Cost cost = (Cost) it.next();
            if (cost instanceof ManaCostsImpl) {
                ability.addManaCostsToPay((ManaCostsImpl) cost.copy());
            } else {
                ability.addCost(cost.copy());
            }
        }
    }

    @Override
    public String getRule() {
        return additionalCost == null ? "" : additionalCost.getText(false) + ' ' + additionalCost.getReminderText();
    }

    @Override
    public String getCastMessageSuffix() {
        return additionalCost == null ? "" : additionalCost.getCastSuffixMessage(0);
    }

    public String getReminderText() {
        return additionalCost == null ? "" : additionalCost.getReminderText();
    }
}

class ReplicateTriggeredAbility extends TriggeredAbilityImpl {

    private UUID replicateId; // need to correspond only to own replicate ability, not any other instances of replicate ability
    private String replicateCostText;

    public ReplicateTriggeredAbility(UUID replicateId, String replicateCostText) {
        super(Zone.STACK, new ReplicateCopyEffect());
        this.replicateId = replicateId;
        this.replicateCostText = replicateCostText;
        this.setRuleVisible(false);
    }

    private ReplicateTriggeredAbility(final ReplicateTriggeredAbility ability) {
        super(ability);
        this.replicateId = ability.replicateId;
        this.replicateCostText = ability.replicateCostText;
    }

    @Override
    public ReplicateTriggeredAbility copy() {
        return new ReplicateTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(this.sourceId)) {
            return false;
        }
        StackObject spell = game.getStack().getStackObject(this.sourceId);
        if (!(spell instanceof Spell)) {
            return false;
        }
        Card card = ((Spell) spell).getCard();
        if (card == null) {
            return false;
        }
        int plannedCount = CastCostPlan.getOptionalAdditionalCostCount(
                ((Spell) spell).getSpellAbility(),
                ReplicateAbility.getCostPlanKey(spell.getSourceId(), replicateId, replicateCostText)
        );
        if (plannedCount == 0 && ReplicateAbility.hasSingleReplicateAbility(card, game)) {
            plannedCount = ReplicateAbility.getSelectedReplicateCount(((Spell) spell).getSpellAbility());
        }
        if (plannedCount > 0) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("ReplicateSpell", spell);
                effect.setValue("ReplicateCount", plannedCount);
            }
            return true;
        }
        for (Ability ability : card.getAbilities(game)) {
            if (!(ability instanceof ReplicateAbility) || !ability.isActivated() || ability.getId() != replicateId) {
                continue;
            }
            for (Effect effect : this.getEffects()) {
                effect.setValue("ReplicateSpell", spell);
                effect.setValue("ReplicateCount", ((ReplicateAbility) ability).getActivateCount());
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Replicate <i>(When you cast this spell, copy it for each time you paid "
                + "its replicate cost. You may choose new targets for the copies.)</i>";
    }
}

class ReplicateCopyEffect extends OneShotEffect {

    public ReplicateCopyEffect() {
        super(Outcome.Copy);
    }

    protected ReplicateCopyEffect(final ReplicateCopyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) this.getValue("ReplicateSpell");
        int replicateCount = (Integer) this.getValue("ReplicateCount");
        if (controller == null || spell == null || replicateCount == 0) {
            return false;
        }

        // reset replicate now so the copies don't report x times Replicate
        Card card = game.getCard(spell.getSourceId());
        if (card == null) {
            return false;
        }

        for (Ability ability : card.getAbilities(game)) {
            if ((ability instanceof ReplicateAbility) && ability.isActivated()) {
                ((ReplicateAbility) ability).resetReplicate();
            }
        }
        // create the copies
        spell.createCopyOnStack(game, source, source.getControllerId(), true, replicateCount);
        return true;
    }

    @Override
    public ReplicateCopyEffect copy() {
        return new ReplicateCopyEffect(this);
    }
}
