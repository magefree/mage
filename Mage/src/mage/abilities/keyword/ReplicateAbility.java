/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
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

/**
 *
 * @author LevelX2
 */

public class ReplicateAbility extends StaticAbility<ReplicateAbility> implements OptionalAdditionalSourceCosts {

    private static final String keywordText = "Replicate";
    private static final String reminderTextMana = "<i>(When you cast this spell, copy it for each time you paid its replicate cost. You may choose new targets for the copies.)</i>";
    protected OptionalAdditionalCost additionalCost;

    public ReplicateAbility(Card card, String manaString) {
       super(Zone.STACK, null);
       this.additionalCost = new OptionalAdditionalCostImpl(keywordText, reminderTextMana, new ManaCostsImpl(manaString));
       this.additionalCost.setRepeatable(true);
       setRuleAtTheTop(true);
       card.addAbility(new ReplicateTriggeredAbility());
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
            Player player = game.getPlayer(controllerId);
            if (player != null) {
                this.resetReplicate();

                boolean again = true;
                while (again) {
                    String times = "";
                    if (additionalCost.isRepeatable()) {
                        int activated = additionalCost.getActivateCount();
                        times = Integer.toString(activated + 1) + (activated == 0 ? " time ":" times ");
                    }
                    if (additionalCost.canPay(sourceId, controllerId, game) &&
                            player.chooseUse(Outcome.Benefit, new StringBuilder("Pay ").append(times).append(additionalCost.getText(false)).append(" ?").toString(), game)) {
                        additionalCost.activate();
                        for (Iterator it = ((Costs) additionalCost).iterator(); it.hasNext();) {
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
            sb.append(" ").append(additionalCost.getReminderText());
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

class ReplicateTriggeredAbility extends TriggeredAbilityImpl<ReplicateTriggeredAbility> {

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
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getSourceId().equals(this.sourceId)) {
            StackObject spell = game.getStack().getStackObject(this.sourceId);
            if (spell instanceof Spell) {
                Card card = game.getCard(spell.getSourceId());
                if (card != null) {
                    for (Ability ability: card.getAbilities()) {
                        if (ability instanceof ReplicateAbility) {
                            if (((ReplicateAbility) ability).isActivated()) {
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
        return "Replicate <i>(When you cast this spell, copy it for each time you paid its replicate cost. You may choose new targets for the copies.)</i>" ;
    }
}

class ReplicateCopyEffect extends OneShotEffect<ReplicateCopyEffect> {

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
            if (spell != null && replicateCount > 0) {
                // reset replicate now so the copies don't report x times Replicate
                Card card = game.getCard(spell.getSourceId());
                if (card != null) {
                    for (Ability ability: card.getAbilities()) {
                        if (ability instanceof ReplicateAbility) {
                            if (((ReplicateAbility) ability).isActivated()) {
                                ((ReplicateAbility) ability).resetReplicate();
                            }
                        }
                    }
                }
                // create the copies
                for (int i = 0; i < replicateCount; i++) {
                    Spell copy = spell.copySpell();
                    copy.setControllerId(source.getControllerId());
                    copy.setCopiedSpell(true);
                    game.getStack().push(copy);
                    copy.chooseNewTargets(game, source.getControllerId());
                    game.informPlayers(new StringBuilder(controller.getName()).append(copy.getActivatedMessage(game)).toString());
                }
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
