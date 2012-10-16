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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.dynamicvalue.common.OpponentsLostLifeCount;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class RakdosLordOfRiots extends CardImpl<RakdosLordOfRiots> {

    public RakdosLordOfRiots(UUID ownerId) {
        super(ownerId, 187, "Rakdos, Lord of Riots", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{B}{B}{R}{R}");
        this.expansionSetCode = "RTR";
        this.supertype.add("Legendary");
        this.subtype.add("Demon");
        this.color.setBlack(true);
        this.color.setRed(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // You can't cast Rakdos, Lord of Riots unless an opponent lost life this turn.
        this.getSpellAbility().addCost(new RakdosLordOfRiotsCost());

        // Flying, trample
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());

        // Creature spells you cast cost {1} less to cast for each 1 life your opponents have lost this turn.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new RakdosLordOfRiotsCostReductionEffect()));
    }

    public RakdosLordOfRiots(final RakdosLordOfRiots card) {
        super(card);
    }

    @Override
    public RakdosLordOfRiots copy() {
        return new RakdosLordOfRiots(this);
    }
}

class RakdosLordOfRiotsCost extends CostImpl<RakdosLordOfRiotsCost> {

    public RakdosLordOfRiotsCost() {
        text = "You can't cast Rakdos, Lord of Riots unless an opponent lost life this turn";
    }

    public RakdosLordOfRiotsCost(final RakdosLordOfRiotsCost cost) {
        super(cost);
    }

    @Override
    public RakdosLordOfRiotsCost copy() {
        return new RakdosLordOfRiotsCost(this);
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (game.getActivePlayerId().equals(controllerId)) {
            OpponentsLostLifeCount dynamicValue = new OpponentsLostLifeCount();
            if (dynamicValue != null && dynamicValue.calculate(game, controllerId) > 0) {
                return true;
            } else {
                game.informPlayer(controller, "You can't cast Rakdos, Lord of Riots because your opponents lost no life this turn yet");
                return false;
            }
        }
        // Always return true for not controller's turn:
        // http://www.wizards.com/magic/magazine/article.aspx?x=mtg/faq/rtr
        // Rakdos can be put onto the battlefield by another spell or ability even if no opponent has lost life that turn.

        return true;

    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        this.paid = true;
        return paid;
    }
}

class RakdosLordOfRiotsCostReductionEffect extends CostModificationEffectImpl<RakdosLordOfRiotsCostReductionEffect> {

    RakdosLordOfRiotsCostReductionEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
        staticText = "Creature spells you cast cost {1} less to cast for each 1 life your opponents have lost this turn";
    }

    RakdosLordOfRiotsCostReductionEffect(RakdosLordOfRiotsCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        if (spellAbility != null) {
            OpponentsLostLifeCount dynamicValue = new OpponentsLostLifeCount();
            int amount = dynamicValue.calculate(game, source);
            if (amount > 0) {
                CardUtil.adjustCost(spellAbility, amount);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            Card sourceCard = game.getCard(((SpellAbility) abilityToModify).getSourceId());
            if (sourceCard != null && abilityToModify.getControllerId().equals(source.getControllerId()) && (sourceCard.getCardType().contains(CardType.CREATURE))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public RakdosLordOfRiotsCostReductionEffect copy() {
        return new RakdosLordOfRiotsCostReductionEffect(this);
    }

}