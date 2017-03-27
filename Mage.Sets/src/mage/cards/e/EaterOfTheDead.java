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
package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class EaterOfTheDead extends CardImpl {

    public EaterOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add("Horror");
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {0}: If Eater of the Dead is tapped, exile target creature card from a graveyard and untap Eater of the Dead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new EaterOfTheDeadEffect(), new GenericManaCost(0));
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard()));
        this.addAbility(ability);
    }

    public EaterOfTheDead(final EaterOfTheDead card) {
        super(card);
    }

    @Override
    public EaterOfTheDead copy() {
        return new EaterOfTheDead(this);
    }
}

class EaterOfTheDeadEffect extends OneShotEffect {
    EaterOfTheDeadEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "If {this} is tapped, exile target creature card from a graveyard and untap {this}";
    }

    EaterOfTheDeadEffect(final EaterOfTheDeadEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Card card = game.getCard(source.getFirstTarget());
        if (sourcePermanent != null && sourcePermanent.isTapped() && card != null) {
            card.moveToExile(null, "Eater of the Dead", source.getSourceId(), game);
            sourcePermanent.untap(game);
        }
        return false;
    }

    @Override
    public EaterOfTheDeadEffect copy() {
        return new EaterOfTheDeadEffect(this);
    }

}
