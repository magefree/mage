package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.*;
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

import java.util.Iterator;

/**
 * @author LevelX2
 */
public class ReplicateAbility extends StaticAbility implements OptionalAdditionalSourceCosts {

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
        addSubAbility(new ReplicateTriggeredAbility());
    }

    public ReplicateAbility(final ReplicateAbility ability) {
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
        if (additionalCost != null) {
            return additionalCost.isActivated();
        }
        return false;
    }

    public int getActivateCount() {
        if (additionalCost != null) {
            return additionalCost.getActivateCount();
        }
        return 0;
    }

    public void resetReplicate() {
        if (additionalCost != null) {
            additionalCost.reset();
        }
    }

    @Override
    public void addOptionalAdditionalCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            Player player = game.getPlayer(ability.getControllerId());
            if (player != null) {
                this.resetReplicate();
                boolean again = true;
                while (player.canRespond()
                        && again) {
                    String times = "";
                    if (additionalCost.isRepeatable()) {
                        int numActivations = additionalCost.getActivateCount();
                        times = (numActivations + 1) + (numActivations == 0 ? " time " : " times ");
                    }

                    // TODO: add AI support to find max number of possible activations (from available mana)
                    //  canPay checks only single mana available, not total mana usage
                    if (additionalCost.canPay(ability, this, ability.getControllerId(), game)
                            && player.chooseUse(/*Outcome.Benefit*/Outcome.AIDontUseIt,
                            new StringBuilder("Pay ").append(times).append(
                                    additionalCost.getText(false)).append(" ?").toString(), ability, game)) {
                        additionalCost.activate();
                        for (Iterator it = ((Costs) additionalCost).iterator(); it.hasNext(); ) {
                            Cost cost = (Cost) it.next();
                            if (cost instanceof ManaCostsImpl) {
                                ability.getManaCostsToPay().add((ManaCostsImpl) cost.copy());
                            } else {
                                ability.getCosts().add(cost.copy());
                            }
                        }
                    } else {
                        again = false;
                    }
                }
            }
        }
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        if (additionalCost != null) {
            sb.append(additionalCost.getText(false));
            sb.append(' ').append(additionalCost.getReminderText());
        }
        return sb.toString();
    }

    @Override
    public String getCastMessageSuffix() {
        if (additionalCost != null) {
            return additionalCost.getCastSuffixMessage(0);
        } else {
            return "";
        }
    }

    public String getReminderText() {
        if (additionalCost != null) {
            return additionalCost.getReminderText();
        } else {
            return "";
        }
    }
}

class ReplicateTriggeredAbility extends TriggeredAbilityImpl {

    public ReplicateTriggeredAbility() {
        super(Zone.STACK, new ReplicateCopyEffect());
        this.setRuleVisible(false);
    }

    private ReplicateTriggeredAbility(final ReplicateTriggeredAbility ability) {
        super(ability);
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
        if (event.getSourceId().equals(this.sourceId)) {
            StackObject spell = game.getStack().getStackObject(this.sourceId);
            if (spell instanceof Spell) {
                Card card = ((Spell) spell).getCard();
                if (card != null) {
                    for (Ability ability : card.getAbilities(game)) {
                        if (ability instanceof ReplicateAbility) {
                            if (ability.isActivated()) {
                                for (Effect effect : this.getEffects()) {
                                    effect.setValue("ReplicateSpell", spell);
                                    effect.setValue("ReplicateCount", ((ReplicateAbility) ability).getActivateCount());
                                }
                                return true;
                            }
                        }
                    }
                }
            }
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

    public ReplicateCopyEffect(final ReplicateCopyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Spell spell = (Spell) this.getValue("ReplicateSpell");
            int replicateCount = (Integer) this.getValue("ReplicateCount");
            if (spell != null
                    && replicateCount > 0) {
                // reset replicate now so the copies don't report x times Replicate
                Card card = game.getCard(spell.getSourceId());
                if (card != null) {
                    for (Ability ability : card.getAbilities(game)) {
                        if (ability instanceof ReplicateAbility) {
                            if (ability.isActivated()) {
                                ((ReplicateAbility) ability).resetReplicate();
                            }
                        }
                    }
                }
                // create the copies
                spell.createCopyOnStack(game, source, source.getControllerId(), true, replicateCount);
                return true;
            }

        }
        return false;
    }

    @Override
    public ReplicateCopyEffect copy() {
        return new ReplicateCopyEffect(this);
    }
}
