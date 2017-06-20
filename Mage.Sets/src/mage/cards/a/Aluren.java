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
package mage.cards.a;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * 10/4/2004 	The mana cost of the creatures being cast is still the stated cost on the card, 
 *              even though you did not pay the cost.
 * 10/4/2004 	Aluren checks the actual printed cost on the creature card, and is not affected
 *              by things which allow you to cast the spell for less.
 * 10/4/2004 	You can't choose to cast a creature as though it had flash via Aluren and still pay the mana cost. 
 *              You either cast the creature normally, or via Aluren without paying the mana cost.
 * 10/4/2004 	You can't use Aluren when casting a creature using another alternate means, 
 *              such as the Morph ability.
 *  8/1/2008 	If creature with X in its cost is cast this way, X can only be 0.
 * 
 * @author emerald000
 */
public class Aluren extends CardImpl {
    
    private static final FilterCreatureCard filter = new FilterCreatureCard("creature cards with converted mana cost 3 or less");
    
    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 4));
    }
    
    public Aluren(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}{G}");


        // Any player may play creature cards with converted mana cost 3 or less without paying their mana cost
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new AlurenRuleEffect());
        // and as though they had flash.
        // TODO: This as thought effect may only be used if the creature is cast by the aluren effect
        Effect effect = new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter, true);
        effect.setText("and as though they had flash");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public Aluren(final Aluren card) {
        super(card);
    }

    @Override
    public Aluren copy() {
        return new Aluren(this);
    }
}

class AlurenRuleEffect extends ContinuousEffectImpl {
    
    private static final FilterCreatureCard filter = new FilterCreatureCard("creature cards with converted mana cost 3 or less");
    
    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 4));
    }
    
    private static AlternativeCostSourceAbility alternativeCastingCostAbility = new AlternativeCostSourceAbility(null, SourceIsSpellCondition.instance, null, filter,  true);
    
    public AlurenRuleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Any player may play creature cards with converted mana cost 3 or less without paying their mana cost";
    }

    public AlurenRuleEffect(final AlurenRuleEffect effect) {
        super(effect);
    }

    @Override
    public AlurenRuleEffect copy() {
        return new AlurenRuleEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId: game.getState().getPlayersInRange(controller.getId(), game)){
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.getAlternativeSourceCosts().add(alternativeCastingCostAbility);
                }
            }            
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}

//class AlurenEffect extends CostModificationEffectImpl {
//    
//    AlurenEffect() {
//        super(Duration.WhileOnBattlefield, Outcome.PlayForFree, CostModificationType.SET_COST);
//        this.staticText = "Any player may play creature cards with converted mana cost 3 or less without paying their mana cost";
//    }
//    
//    AlurenEffect(final AlurenEffect effect) {
//        super(effect);
//    }
//    
//    @Override
//    public boolean apply(Game game, Ability source, Ability abilityToModify) {
//        SpellAbility spellAbility = (SpellAbility) abilityToModify;
//        spellAbility.getManaCostsToPay().clear();
//        return true;
//    }
//    
//    @Override
//    public boolean applies(Ability abilityToModify, Ability source, Game game) {
//        if (abilityToModify instanceof SpellAbility) {
//            Card sourceCard = game.getCard(abilityToModify.getSourceId());
//            StackObject stackObject = game.getStack().getStackObject(abilityToModify.getSourceId());
//            if (stackObject != null && stackObject instanceof Spell) {
//                if (sourceCard != null && sourceCard.isCreature() && sourceCard.getConvertedManaCost() <= 3) {
//                    Player player = game.getPlayer(stackObject.getControllerId());
//                    String message = "Cast " + sourceCard.getName() + " without paying its mana costs?";
//                    if (player != null && 
//                            (CardUtil.isCheckPlayableMode(abilityToModify) || player.chooseUse(outcome, message, game))) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//    
//    @Override
//    public AlurenEffect copy() {
//        return new AlurenEffect(this);
//    }
//}
