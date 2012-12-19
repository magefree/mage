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

import java.util.UUID;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.AdjustingSourceCosts;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

/*
 * 502.46. Convoke
 *
 * 502.46a Convoke is a static ability that functions while the spell is on the stack. "Convoke"
 * means "As an additional cost to play this spell, you may tap any number of untapped creatures
 * you control. Each creature tapped this way reduces the cost to play this spell by {1} or by
 * one mana of any of that creature's colors." Using the convoke ability follows the rules for
 * paying additional costs in rules 409.1b and 4091f-h.
 *
 * Example: You play Guardian of Vitu-Ghazi, a spell with convoke that costs {3}{G}{W}. You announce
 * that you're going to tap an artifact creature, a red creature, and a green-and-white creature to
 * help pay for it. The artifact creature and the red creature each reduce the spell's cost by {1}.
 * You choose whether the green-white creature reduces the spell's cost by {1}, {G}, or {W}. Then
 * the creatures become tapped as you pay Guardian of Vitu-Ghazi's cost.
 *
 * 502.46b Convoke can't reduce the cost to play a spell to less than 0.
 *
 * 502.46c Multiple instances of convoke on the same spell are redundant.
 *
 * You can tap only untapped creatures you control to reduce the cost of a spell with convoke
 * that you play.
 *
 * While playing a spell with convoke, if you control a creature that taps to produce mana, you
 * can either tap it for mana or tap it to reduce the cost of the spell, but not both.
 *
 * If you tap a multicolored creature to reduce the cost of a spell with convoke, you reduce
 * the cost by {1} or by one mana of your choice of any of that creature's colors.
 *
 * Convoke doesn't change a spell's mana cost or converted mana cost.
 *
 *
 * @author LevelX2
 */


public class ConvokeAbility extends SimpleStaticAbility implements AdjustingSourceCosts {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
    static {
      filter.add(Predicates.not(new TappedPredicate()));
    }

    public ConvokeAbility() {
        super(Zone.STACK, null);
        this.setRuleAtTheTop(true);
    }

    public ConvokeAbility(final ConvokeAbility ability) {
      super(ability);
    }

    @Override
    public ConvokeAbility copy() {
      return new ConvokeAbility(this);
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Player player = game.getPlayer(controllerId);
        if (player == null || !(ability instanceof SpellAbility)) {
            return;
        }
        Target target = new TargetControlledCreaturePermanent(1, Integer.MAX_VALUE, filter,true);
        target.setTargetName("creatures to convoke");
        if (!target.canChoose(sourceId, controllerId, game)) {
            return;
        }
        if (player.chooseUse(Outcome.Detriment, "Convoke creatures?", game)) {
            player.chooseTarget(Outcome.Tap, target, ability, game);
            if (target.getTargets().size() > 0) {
                int adjCost = 0;
                for (UUID creatureId: target.getTargets()) {
                    Permanent perm = game.getPermanent(creatureId);
                    if (perm!= null) {
                        Card card = game.getCard(perm.getId());
                        ManaCosts manaCostsCreature = card.getSpellAbility().getManaCosts();
                        if (card != null && manaCostsCreature != null && manaCostsCreature.convertedManaCost() > 0) {
                            if (perm.tap(game)) {
                                Choice chooseManaType = buildChoice(manaCostsCreature, ability.getManaCostsToPay());
                                if (chooseManaType.getChoices().size() > 0) {
                                    if (chooseManaType.getChoices().size() > 1) {
                                        chooseManaType.getChoices().add("Colorless");
                                        chooseManaType.setMessage("Choose mana color to reduce from " + perm.getName());
                                        while (!chooseManaType.isChosen()) {
                                            player.choose(Outcome.Benefit, chooseManaType, game);
                                        }
                                    } else {
                                        chooseManaType.setChoice(chooseManaType.getChoices().iterator().next());
                                    }

                                    ManaCosts manaCostsToReduce = new ManaCostsImpl();
                                    if (chooseManaType.getChoice().equals("Black")) {
                                        manaCostsToReduce.load("{B}");
                                    }
                                    if (chooseManaType.getChoice().equals("Blue")) {
                                        manaCostsToReduce.load("{U}");
                                    }
                                    if (chooseManaType.getChoice().equals("Green")) {
                                        manaCostsToReduce.load("{G}");
                                    }
                                    if (chooseManaType.getChoice().equals("White")) {
                                        manaCostsToReduce.load("{W}");
                                    }
                                    if (chooseManaType.getChoice().equals("Red")) {
                                        manaCostsToReduce.load("{R}");
                                    }
                                    if (chooseManaType.getChoice().equals("Colorless")) {
                                        ++adjCost;
                                    }
                                    CardUtil.adjustCost((SpellAbility)ability, manaCostsToReduce);
                                } else {
                                    ++adjCost;
                                }
                            }
                        }
                    }
                }
                this.getTargets().add(target);
                CardUtil.adjustCost((SpellAbility)ability, adjCost);
            }
        }
    }

    private Choice buildChoice(ManaCosts manaCosts, ManaCosts manaCostsSpell) {
       Choice choice = new ChoiceImpl();
       String creatureCosts = manaCosts.getText();
       String spellCosts = manaCostsSpell.getText();
       if (creatureCosts.contains("B") && spellCosts.contains("B")) {
           choice.getChoices().add("Black");
       }
       if (creatureCosts.contains("U") && spellCosts.contains("U")) {
           choice.getChoices().add("Blue");
       }
       if (creatureCosts.contains("G") && spellCosts.contains("G")) {
           choice.getChoices().add("Green");
       }
       if (creatureCosts.contains("R") && spellCosts.contains("R")) {
           choice.getChoices().add("Red");
       }
       if (creatureCosts.contains("W") && spellCosts.contains("W")) {
           choice.getChoices().add("White");
       }
       return choice;
    }

    @Override
    public String getRule() {
      return "Convoke <i>(Each creature you tap while casting this spell reduces its cost by {1} or by one mana of that creature's color.)</i>";
    }
}

