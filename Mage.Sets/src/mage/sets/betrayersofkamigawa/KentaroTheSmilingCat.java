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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public class KentaroTheSmilingCat extends CardImpl<KentaroTheSmilingCat> {

    public KentaroTheSmilingCat(UUID ownerId) {
        super(ownerId, 13, "Kentaro, the Smiling Cat", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "BOK";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Samurai");
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Bushido 1 (When this blocks or becomes blocked, it gets +1/+1 until end of turn.)
        this.addAbility(new BushidoAbility(1));
        
        // You may pay {X} rather than pay the mana cost for Samurai spells you cast, where X is that spell's converted mana cost.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new KentaroTheSmilingCatCostReductionEffect()));
        

    }

    public KentaroTheSmilingCat(final KentaroTheSmilingCat card) {
        super(card);
    }

    @Override
    public KentaroTheSmilingCat copy() {
        return new KentaroTheSmilingCat(this);
    }


    private class KentaroTheSmilingCatCostReductionEffect extends CostModificationEffectImpl<KentaroTheSmilingCatCostReductionEffect> {

        private static final String effectText = "You may pay {X} rather than pay the mana cost for Samurai spells you cast, where X is that spell's converted mana cost";

        KentaroTheSmilingCatCostReductionEffect() {
            super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
            staticText = effectText;
        }

        KentaroTheSmilingCatCostReductionEffect(KentaroTheSmilingCatCostReductionEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source, Ability abilityToModify) {
            return true;
        }

        @Override
        public boolean applies(Ability abilityToModify, Ability source, Game game) {
            if (abilityToModify instanceof SpellAbility) {
                SpellAbility spell = (SpellAbility) abilityToModify;
                if (spell.getControllerId().equals(source.getControllerId())) {
                    Card sourceCard = game.getCard(spell.getSourceId());
                    if (sourceCard != null && sourceCard.getSubtype().contains("Samurai")) {
                        String manaCostsString = "{" + sourceCard.getManaCost().convertedManaCost() + "}"; 
                        Player player = game.getPlayer(spell.getControllerId());
                        if (player != null && player.chooseUse(Constants.Outcome.Benefit, "Pay converted mana cost rather than pay the mana cost for Samurai creature?", game)) {
                            spell.getManaCostsToPay().clear();
                            spell.getManaCostsToPay().load(manaCostsString);
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        @Override
        public KentaroTheSmilingCatCostReductionEffect copy() {
            return new KentaroTheSmilingCatCostReductionEffect(this);
        }

    }
}