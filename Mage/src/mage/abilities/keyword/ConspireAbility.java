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

import java.util.Iterator;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.OptionalAdditionalCost;
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

/**
 * 702.77. Conspire 702.77a Conspire is a keyword that represents two abilities.
 * The first is a static ability that functions while the spell with conspire is
 * on the stack. The second is a triggered ability that functions while the
 * spell with conspire is on the stack. "Conspire" means "As an additional cost
 * to cast this spell, you may tap two untapped creatures you control that each
 * share a color with it" and "When you cast this spell, if its conspire cost
 * was paid, copy it. If the spell has any targets, you may choose new targets
 * for the copy." Paying a spell’s conspire cost follows the rules for paying
 * additional costs in rules 601.2b and 601.2e–g. 702.77b If a spell has
 * multiple instances of conspire, each is paid separately and triggers based on
 * its own payment, not any other instance of conspire. *
 *
 * @author jeffwadsworth heavily based off the replicate keyword by LevelX
 */
public class ConspireAbility extends StaticAbility implements OptionalAdditionalSourceCosts {

    private static final String keywordText = "Conspire";
    private static final String reminderTextCost = "<i>As you cast this spell, you may tap two untapped creatures you control that share a color with it. When you do, copy it and you may choose a new target for the copy.)</i>";
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("two untapped creatures you control that share a color with it");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
        filter.add(new SharesColorWithSourcePredicate());
    }

    Cost costConspire = new TapTargetCost(new TargetControlledPermanent(2, 2, filter, true));
    OptionalAdditionalCost conspireCost = new OptionalAdditionalCostImpl(keywordText, "-", reminderTextCost, costConspire);

    public ConspireAbility(Card card) {
        super(Zone.STACK, null);
        setRuleAtTheTop(false);
        addSubAbility(new ConspireTriggeredAbility());
    }

    public ConspireAbility(final ConspireAbility ability) {
        super(ability);
        conspireCost = ability.conspireCost;
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

    @Override
    public boolean isActivated() {
        if (conspireCost != null) {
            return conspireCost.isActivated();
        }
        return false;
    }

    public void resetConspire() {
        if (conspireCost != null) {
            conspireCost.reset();
        }
    }

    @Override
    public void addOptionalAdditionalCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            Player player = game.getPlayer(controllerId);
            if (player != null) {
                this.resetConspire();
                if (player.chooseUse(Outcome.Benefit, new StringBuilder("Pay ").append(conspireCost.getText(false)).append(" ?").toString(), game)) {
                    conspireCost.activate();
                    for (Iterator it = ((Costs) conspireCost).iterator(); it.hasNext();) {
                        Cost cost = (Cost) it.next();
                        ability.getCosts().add(cost.copy());
                    }
                }
            }
        }
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        if (conspireCost != null) {
            sb.append(conspireCost.getText(false));
            sb.append(" ").append(conspireCost.getReminderText());
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

    public ConspireTriggeredAbility() {
        super(Zone.STACK, new ConspireEffect());
        this.setRuleVisible(false);
    }

    private ConspireTriggeredAbility(final ConspireTriggeredAbility ability) {
        super(ability);
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
        if (event.getSourceId().equals(this.sourceId)) {
            StackObject spell = game.getStack().getStackObject(this.sourceId);
            if (spell instanceof Spell) {
                Card card = game.getCard(spell.getSourceId());
                if (card != null) {
                    for (Ability ability : card.getAbilities()) {
                        if (ability instanceof ConspireAbility) {
                            if (((ConspireAbility) ability).isActivated()) {
                                for (Effect effect : this.getEffects()) {
                                    effect.setValue("ConspireSpell", spell);
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
        return "Conspire: <i>As you cast this spell, you may tap two untapped creatures you control that share a color with it. When you do, copy it and you may choose a new target for the copy.)</i>";
    }
}

class ConspireEffect extends OneShotEffect {

    public ConspireEffect() {
        super(Outcome.Copy);
    }

    public ConspireEffect(final ConspireEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Spell spell = (Spell) this.getValue("ConspireSpell");
            if (spell != null) {
                Card card = game.getCard(spell.getSourceId());
                if (card != null) {
                    for (Ability ability : card.getAbilities()) {
                        if (ability instanceof ConspireAbility) {
                            if (((ConspireAbility) ability).isActivated()) {
                                ((ConspireAbility) ability).resetConspire();
                            }
                        }
                    }
                    Spell copy = spell.copySpell();
                    copy.setControllerId(source.getControllerId());
                    copy.setCopiedSpell(true);
                    game.getStack().push(copy);
                    copy.chooseNewTargets(game, source.getControllerId());
                    if (!game.isSimulation())
                        game.informPlayers(new StringBuilder(controller.getName()).append(copy.getActivatedMessage(game)).toString());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ConspireEffect copy() {
        return new ConspireEffect(this);
    }
}
