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
package mage.sets.tempest;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.CastAsThoughItHadFlashEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.Filter.ComparisonType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public class Aluren extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature cards with converted mana cost 3 or less");
    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.LessThan, 4));
    }
    
    public Aluren(UUID ownerId) {
        super(ownerId, 107, "Aluren", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");
        this.expansionSetCode = "TMP";

        this.color.setGreen(true);

        // Any player may play creature cards with converted mana cost 3 or less without paying their mana cost
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new AlurenEffect());
        // and as though they had flash.
        Effect effect = new CastAsThoughItHadFlashEffect(Duration.WhileOnBattlefield, filter, true);
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

class AlurenEffect extends CostModificationEffectImpl {
    
    AlurenEffect() {
        super(Duration.WhileOnBattlefield, Outcome.PlayForFree, CostModificationType.SET_COST);
        this.staticText = "Any player may play creature cards with converted mana cost 3 or less without paying their mana cost";
    }
    
    AlurenEffect(final AlurenEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        spellAbility.getManaCostsToPay().clear();
        return true;
    }
    
    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            Card sourceCard = game.getCard(abilityToModify.getSourceId());
            StackObject stackObject = game.getStack().getStackObject(abilityToModify.getSourceId());
            if (stackObject != null && stackObject instanceof Spell) {
                if (sourceCard != null && sourceCard.getCardType().contains(CardType.CREATURE) && sourceCard.getManaCost().convertedManaCost() <= 3) {
                    Player player = game.getPlayer(stackObject.getControllerId());
                    String message = "Cast " + sourceCard.getName() + " without paying its mana costs?";
                    if (player != null && 
                            (CardUtil.isCheckPlayableMode(abilityToModify) || player.chooseUse(outcome, message, game))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public AlurenEffect copy() {
        return new AlurenEffect(this);
    }
}