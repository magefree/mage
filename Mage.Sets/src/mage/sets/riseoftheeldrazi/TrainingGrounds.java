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

package mage.sets.riseoftheeldrazi;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class TrainingGrounds extends CardImpl<TrainingGrounds> {

    public TrainingGrounds (UUID ownerId) {
        super(ownerId, 91, "Training Grounds", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{U}");
        this.expansionSetCode = "ROE";

        this.color.setBlue(true);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TrainingGroundsEffect()));
    }

    public TrainingGrounds (final TrainingGrounds card) {
        super(card);
    }

    @Override
    public TrainingGrounds copy() {
        return new TrainingGrounds(this);
    }
}

class TrainingGroundsEffect extends CostModificationEffectImpl<TrainingGroundsEffect> {
    
    private static final String effectText = "Activated abilities of creatures you control cost up to {2} less to activate. This effect can't reduce the amount of mana an ability costs to activate to less than one mana";
    private static final FilterControlledCreaturePermanent filter;
    static {
        filter = new FilterControlledCreaturePermanent();
    }

    public TrainingGroundsEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = effectText;
    }

    public TrainingGroundsEffect(final TrainingGroundsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(abilityToModify.getControllerId());
        if (controller != null){
            Mana mana = abilityToModify.getManaCostsToPay().getMana();
            int reduceMax = mana.getColorless();
            if (reduceMax > 0 && mana.count() == mana.getColorless()){
                reduceMax--;
            }
            if (reduceMax > 2){
                reduceMax = 2;
            }
            if (reduceMax > 0) {
                ChoiceImpl choice = new ChoiceImpl(true);
                Set<String> set = new LinkedHashSet<>();

                for(int i = 0; i <= reduceMax; i++){
                    set.add(String.valueOf(i));
                }
                choice.setChoices(set);
                choice.setMessage("Reduce ability cost");
                if(controller.choose(Outcome.Benefit, choice, game)){
                    int reduce = Integer.parseInt(choice.getChoice());
                    mana.setColorless(mana.getColorless() - reduce);
                    abilityToModify.getManaCostsToPay().load(mana.toString());
                }
            }
            return true;
        }
         return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof ActivatedAbility) {
            //Activated abilities of creatures you control
            Permanent permanent = game.getPermanent(abilityToModify.getSourceId());
            if (permanent != null && filter.match(permanent, source.getSourceId(), source.getControllerId(), game)) {
                return true;             
            }
        }
        return false;
    }

    @Override
    public TrainingGroundsEffect copy() {
        return new TrainingGroundsEffect(this);
    }
}
