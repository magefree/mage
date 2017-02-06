/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.keyword;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.OptionalAdditionalCostImpl;
import mage.abilities.costs.OptionalAdditionalSourceCosts;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SharesColorWithSourcePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/*
 * 702.77. Conspire
 * 702.77a Conspire is a keyword that represents two abilities.
 * The first is a static ability that functions while the spell with conspire is on the stack.
 * The second is a triggered ability that functions while the spell with conspire is on the stack.
 * "Conspire" means "As an additional cost to cast this spell,
 * you may tap two untapped creatures you control that each share a color with it"
 * and "When you cast this spell, if its conspire cost was paid, copy it.
 * If the spell has any targets, you may choose new targets for the copy."
 * Paying a spell’s conspire cost follows the rules for paying additional costs in rules 601.2b and 601.2e–g.
 * 702.77b If a spell has multiple instances of conspire, each is paid separately and triggers
 * based on its own payment, not any other instance of conspire. *
 *
 * @author LevelX
 */
public class ConspireAbility extends StaticAbility implements OptionalAdditionalSourceCosts {

    private static final String keywordText = "Conspire";
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures you control that share a color with it");
    protected static final String CONSPIRE_ACTIVATION_KEY = "ConspireActivation";

    static {
        filter.add(Predicates.not(new TappedPredicate()));
        filter.add(new SharesColorWithSourcePredicate());
    }

    public enum ConspireTargets {

        NONE,
        ONE,
        MORE
    }

    private UUID conspireId;
    private String reminderText;
    private OptionalAdditionalCostImpl conspireCost;

    /**
     * Unique Id for a ConspireAbility but may not change while a continuous
     * effect gives Conspire
     *
     * @param conspireId
     * @param conspireTargets controls the content of the reminder text
     */
    public ConspireAbility(UUID conspireId, ConspireTargets conspireTargets) {
        super(Zone.STACK, null);
        this.conspireId = conspireId;
        switch (conspireTargets) {
            case NONE:
                reminderText = "As you cast this spell, you may tap two untapped creatures you control that share a color with it. When you do, copy it.";
                break;
            case ONE:
                reminderText = "As you cast this spell, you may tap two untapped creatures you control that share a color with it. When you do, copy it and you may choose a new target for the copy.";
                break;
            case MORE:
                reminderText = "As you cast this spell, you may tap two untapped creatures you control that share a color with it. When you do, copy it and you may choose new targets for the copy.";
                break;
        }
        Cost cost = new TapTargetCost(new TargetControlledPermanent(2, 2, filter, true));
        cost.setText("");
        conspireCost = new OptionalAdditionalCostImpl(keywordText, " ", reminderText, cost);
        addSubAbility(new ConspireTriggeredAbility(conspireId));
    }

    public ConspireAbility(final ConspireAbility ability) {
        super(ability);
        this.conspireId = ability.conspireId;
        this.conspireCost = ability.conspireCost.copy();
        this.reminderText = ability.reminderText;
    }

    @Override
    public ConspireAbility copy() {
        return new ConspireAbility(this);
    }

    @Override
    public void addCost(Cost cost) {
        if (conspireCost != null) {
            ((Costs) conspireCost).add(cost);
        }
    }

    public UUID getConspireId() {
        return conspireId;
    }

    @Override
    public boolean isActivated() {
        throw new UnsupportedOperationException("Use ConspireAbility.isActivated(Ability ability, Game game) method instead!");
    }

    public boolean isActivated(Ability ability, Game game) {
        Set<UUID> activations = (Set<UUID>) game.getState().getValue(CONSPIRE_ACTIVATION_KEY + ability.getId());
        if (activations != null) {
            return activations.contains(getConspireId());
        }
        return false;
    }

    @Override
    public void addOptionalAdditionalCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            Player player = game.getPlayer(getControllerId());
            if (player != null) {
                resetConspire(ability, game);
                if (conspireCost.canPay(ability, getSourceId(), getControllerId(), game)
                        && player.chooseUse(Outcome.Benefit, "Pay " + conspireCost.getText(false) + " ?", ability, game)) {
                    activateConspire(ability, game);
                    for (Iterator it = ((Costs) conspireCost).iterator(); it.hasNext();) {
                        Cost cost = (Cost) it.next();
                        ability.getCosts().add(cost.copy());
                    }
                }
            }
        }
    }

    private void activateConspire(Ability ability, Game game) {
        Set<UUID> activations = (Set<UUID>) game.getState().getValue(CONSPIRE_ACTIVATION_KEY + ability.getId());
        if (activations == null) {
            activations = new HashSet<>();
            game.getState().setValue(CONSPIRE_ACTIVATION_KEY + ability.getId(), activations);
        }
        activations.add(getConspireId());
    }

    private void resetConspire(Ability ability, Game game) {
        Set<UUID> activations = (Set<UUID>) game.getState().getValue(CONSPIRE_ACTIVATION_KEY + ability.getId());
        if (activations != null) {
            activations.remove(getConspireId());
        }
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        if (conspireCost != null) {
            sb.append(conspireCost.getText(false));
            sb.append(' ').append(conspireCost.getReminderText());
        }
        return sb.toString();
    }

    @Override
    public String getCastMessageSuffix() {
        if (conspireCost != null) {
            return conspireCost.getCastSuffixMessage(0);
        } else {
            return "";
        }
    }

    public String getReminderText() {
        if (conspireCost != null) {
            return conspireCost.getReminderText();
        } else {
            return "";
        }
    }
}

class ConspireTriggeredAbility extends TriggeredAbilityImpl {

    private UUID conspireId;

    public ConspireTriggeredAbility(UUID conspireId) {
        super(Zone.STACK, new ConspireEffect());
        this.conspireId = conspireId;
        this.setRuleVisible(false);
    }

    private ConspireTriggeredAbility(final ConspireTriggeredAbility ability) {
        super(ability);
        this.conspireId = ability.conspireId;
    }

    @Override
    public ConspireTriggeredAbility copy() {
        return new ConspireTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(getSourceId())) {
            Spell spell = game.getStack().getSpell(event.getSourceId());
            for (Ability ability : spell.getAbilities(game)) {
                if (ability instanceof ConspireAbility
                        && ((ConspireAbility) ability).getConspireId().equals(getConspireId())) {
                    if (((ConspireAbility) ability).isActivated(spell.getSpellAbility(), game)) {
                        for (Effect effect : this.getEffects()) {
                            if (effect instanceof ConspireEffect) {
                                ((ConspireEffect) effect).setConspiredSpell(spell);
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public UUID getConspireId() {
        return conspireId;
    }

    @Override
    public String getRule() {
        return "When you pay the conspire costs, copy it and you may choose a new target for the copy.";
    }
}

class ConspireEffect extends OneShotEffect {

    private Spell conspiredSpell;

    public ConspireEffect() {
        super(Outcome.Copy);
    }

    public ConspireEffect(final ConspireEffect effect) {
        super(effect);
        this.conspiredSpell = effect.conspiredSpell;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && conspiredSpell != null) {
            Card card = game.getCard(conspiredSpell.getSourceId());
            if (card != null) {
                StackObject newStackObject = conspiredSpell.createCopyOnStack(game, source, source.getControllerId(), true);
                if (newStackObject != null && newStackObject instanceof Spell && !game.isSimulation()) {
                    game.informPlayers(controller.getLogName() + ((Spell) newStackObject).getActivatedMessage(game));
                }
                return true;
            }
        }
        return false;
    }

    public void setConspiredSpell(Spell conspiredSpell) {
        this.conspiredSpell = conspiredSpell;
    }

    @Override
    public ConspireEffect copy() {
        return new ConspireEffect(this);
    }
}
