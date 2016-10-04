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
package mage.sets.alliances;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.common.BecomesBlockedTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LoneFox
 */
public class LimDulsPaladin extends CardImpl {

    public LimDulsPaladin(UUID ownerId) {
        super(ownerId, 191, "Lim-Dul's Paladin", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");
        this.expansionSetCode = "ALL";
        this.subtype.add("Human");
        this.subtype.add("Knight");
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of your upkeep, you may discard a card. If you don't, sacrifice Lim-Dul's Paladin and draw a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new LimDulsPaladinEffect(), TargetController.YOU, false));
        // Whenever Lim-Dul's Paladin becomes blocked, it gets +6/+3 until end of turn.
        this.addAbility(new BecomesBlockedTriggeredAbility(new BoostSourceEffect(6, 3, Duration.EndOfTurn), false));
        // Whenever Lim-Dul's Paladin attacks and isn't blocked, it assigns no combat damage to defending player this turn and that player loses 4 life.
        Effect effect = new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn);
        effect.setText("it assigns no combat damage this turn");
        Ability ability = new AttacksAndIsNotBlockedTriggeredAbility(effect, false, true);
        effect = new LoseLifeTargetEffect(4);
        effect.setText("and defending player loses 4 life");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public LimDulsPaladin(final LimDulsPaladin card) {
        super(card);
    }

    @Override
    public LimDulsPaladin copy() {
        return new LimDulsPaladin(this);
    }
}

class LimDulsPaladinEffect extends SacrificeSourceUnlessPaysEffect {

    public LimDulsPaladinEffect() {
        super(new DiscardTargetCost(new TargetCardInHand()));
        staticText = "you may discard a card. If you don't, sacrifice {this} and draw a card.";
    }

    public LimDulsPaladinEffect(final LimDulsPaladinEffect effect) {
        super(effect);
    }

    @Override
    public LimDulsPaladinEffect copy() {
        return new LimDulsPaladinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if(permanent != null) {
            super.apply(game, source);
            // Not in play anymore -> was sacrificed, draw a card
            if(game.getPermanent(source.getSourceId()) == null) {
                 return new DrawCardSourceControllerEffect(1).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
