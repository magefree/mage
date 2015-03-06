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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ShamanOfForgottenWays extends CardImpl {

    public ShamanOfForgottenWays(UUID ownerId) {
        super(ownerId, 204, "Shaman of Forgotten Ways", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Human");
        this.subtype.add("Shaman");
        this.power = new MageInt(	2);
        this.toughness = new MageInt(3);

        // {T}:Add two mana in any combination of colors to your mana pool. Spend this mana only to cast creature spells.
        this.addAbility(new ConditionalAnyColorManaAbility(2, new ShamanOfForgottenWaysManaBuilder()));
        
        // <i>Formidable</i> - {9}{G}{G},{T}:Each player's life total becomes the number of creatures he or she controls. Activate the ability only if creatures you control have total power 8 or greater.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, 
                new ShamanOfForgottenWaysEffect(), 
                new ManaCostsImpl("{9}{G}{G}"), 
                FormidableCondition.getInstance());
        ability.addCost(new TapSourceCost());
        ability.setAbilityWord(AbilityWord.FORMIDABLE);        
        this.addAbility(ability);
    }

    public ShamanOfForgottenWays(final ShamanOfForgottenWays card) {
        super(card);
    }

    @Override
    public ShamanOfForgottenWays copy() {
        return new ShamanOfForgottenWays(this);
    }
}

class ShamanOfForgottenWaysManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new ShamanOfForgottenWaysConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast creature spells";
    }
}

class ShamanOfForgottenWaysConditionalMana extends ConditionalMana {

    public ShamanOfForgottenWaysConditionalMana(Mana mana) {
        super(mana);
        this.staticText = "Spend this mana only to cast creature spells";
        addCondition(new ShamanOfForgottenWaysManaCondition());
    }
}

class ShamanOfForgottenWaysManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = source.getSourceObject(game);
        return object != null && (object instanceof Spell) && object.getCardType().contains(CardType.CREATURE);
    }
}

class ShamanOfForgottenWaysEffect extends OneShotEffect {
    
    public ShamanOfForgottenWaysEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player's life total becomes the number of creatures he or she controls";
    }
    
    public ShamanOfForgottenWaysEffect(final ShamanOfForgottenWaysEffect effect) {
        super(effect);
    }
    
    @Override
    public ShamanOfForgottenWaysEffect copy() {
        return new ShamanOfForgottenWaysEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterPermanent filter = new FilterCreaturePermanent();
            for(UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null){
                    int numberCreatures = game.getBattlefield().getAllActivePermanents(filter, playerId, game).size();
                    player.setLife(numberCreatures, game);
                }
            }
            return true;
        }
        return false;
    }
}
