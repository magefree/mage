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
package mage.sets.darksteel;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public class EmissaryOfHope extends CardImpl<EmissaryOfHope> {

    public EmissaryOfHope(UUID ownerId) {
        super(ownerId, 3, "Emissary of Hope", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.expansionSetCode = "DST";
        this.subtype.add("Spirit");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());

        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new EmissaryOfHopeEffect(), false, true));
    }

    public EmissaryOfHope(final EmissaryOfHope card) {
        super(card);
    }

    @Override
    public EmissaryOfHope copy() {
        return new EmissaryOfHope(this);
    }
}

class EmissaryOfHopeEffect extends OneShotEffect<EmissaryOfHopeEffect> {
    private final static FilterPermanent filter = new FilterPermanent("artifact");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new ControllerPredicate(Constants.TargetController.YOU));
    }

    EmissaryOfHopeEffect() {
        super(Constants.Outcome.GainLife);
        staticText = "you gain 1 life for each artifact that player controls";
    }

    EmissaryOfHopeEffect(final EmissaryOfHopeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && sourcePlayer != null) {
            int amount = game.getBattlefield().count(filter, targetPlayer.getId(), game);
            if (amount > 0) {
                sourcePlayer.gainLife(amount, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public EmissaryOfHopeEffect copy() {
        return new EmissaryOfHopeEffect(this);
    }
}