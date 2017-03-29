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
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class BedlamReveler extends CardImpl {

    public BedlamReveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{R}{R}");
        this.subtype.add("Devil");
        this.subtype.add("Horror");
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Bedlam Reveler costs {1} less to cast for each instant or sorcery card in your graveyard.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new BedlamRevelerCostReductionEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Prowess
        this.addAbility(new ProwessAbility());

        // When Bedlam Reveler enters the battlefield, discard your hand, then draw three cards.
        ability = new EntersBattlefieldTriggeredAbility(new DiscardHandControllerEffect());
        Effect effect = new DrawCardSourceControllerEffect(3);
        effect.setText("then draw three cards");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public BedlamReveler(final BedlamReveler card) {
        super(card);
    }

    @Override
    public BedlamReveler copy() {
        return new BedlamReveler(this);
    }
}

class BedlamRevelerCostReductionEffect extends CostModificationEffectImpl {

    BedlamRevelerCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "{this} costs {1} less to cast for each instant or sorcery card in your graveyard";
    }

    BedlamRevelerCostReductionEffect(BedlamRevelerCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int reductionAmount = player.getGraveyard().count(new FilterInstantOrSorceryCard(), game);
            CardUtil.reduceCost(abilityToModify, reductionAmount);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility) && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return game.getCard(abilityToModify.getSourceId()) != null;
        }
        return false;
    }

    @Override
    public BedlamRevelerCostReductionEffect copy() {
        return new BedlamRevelerCostReductionEffect(this);
    }
}
