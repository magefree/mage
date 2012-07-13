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
package mage.sets.conflux;

import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.PostResolveEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author North
 */
public class ViewFromAbove extends CardImpl<ViewFromAbove> {

    public ViewFromAbove(UUID ownerId) {
        super(ownerId, 38, "View from Above", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "CON";

        this.color.setBlue(true);

        // Target creature gains flying until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // If you control a white permanent, return View from Above to its owner's hand.
        this.getSpellAbility().addEffect(new ViewFromAboveEffect());
    }

    public ViewFromAbove(final ViewFromAbove card) {
        super(card);
    }

    @Override
    public ViewFromAbove copy() {
        return new ViewFromAbove(this);
    }
}

class ViewFromAboveEffect extends PostResolveEffect<ViewFromAboveEffect> {

    public ViewFromAboveEffect() {
        this.staticText = "If you control a white permanent, return {this} to its owner's hand";
    }

    public ViewFromAboveEffect(final ViewFromAboveEffect effect) {
        super(effect);
    }

    @Override
    public ViewFromAboveEffect copy() {
        return new ViewFromAboveEffect(this);
    }

    @Override
    public void postResolve(Card card, Ability source, UUID controllerId, Game game) {
        FilterPermanent filter = new FilterPermanent("white permanent");
        filter.add(new ColorPredicate(ObjectColor.WHITE));

        if (game.getBattlefield().countAll(filter, source.getControllerId(), game) > 0) {
            card.moveToZone(Zone.HAND, source.getId(), game, false);
        } else {
            card.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
        }
    }
}
