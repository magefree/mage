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
package mage.cards.g;

import java.util.UUID;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.cost.SpellsCostIncreasementAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class Gloom extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("White spells");
    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public Gloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");

        // White spells cost {3} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostIncreasementAllEffect(filter, 3)));
        
        // Activated abilities of white enchantments cost {3} more to activate.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GloomCostIncreaseEffect()));
    }

    public Gloom(final Gloom card) {
        super(card);
    }

    @Override
    public Gloom copy() {
        return new Gloom(this);
    }
}

class GloomCostIncreaseEffect extends CostModificationEffectImpl {

    GloomCostIncreaseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Activated abilities of white enchantments cost {3} more to activate.";
    }

    GloomCostIncreaseEffect(GloomCostIncreaseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 3);
        return true;
    }
    
    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {        
        boolean isWhiteEnchantment = false;
        boolean isActivated = abilityToModify.getAbilityType() == AbilityType.ACTIVATED;
        if (isActivated) {            
            MageObject permanent = game.getPermanent(abilityToModify.getSourceId());
            if (permanent != null) {
                isWhiteEnchantment = permanent.isEnchantment() && permanent.getColor(game).isWhite();
            }
        }
        return isActivated && isWhiteEnchantment;
    }

    @Override
    public GloomCostIncreaseEffect copy() {
        return new GloomCostIncreaseEffect(this);
    }
}