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
package mage.sets.shardsofalara;

import java.util.List;
import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public class RealmRazer extends CardImpl<RealmRazer> {

    public RealmRazer(UUID ownerId) {
        super(ownerId, 187, "Realm Razer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}{G}{W}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Beast");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Realm Razer enters the battlefield, exile all lands.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ExileAllEffect()));
        // When Realm Razer leaves the battlefield, return the exiled cards to the battlefield tapped under their owners' control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new RealmRazerEffect(), false));
    }

    public RealmRazer(final RealmRazer card) {
        super(card);
    }

    @Override
    public RealmRazer copy() {
        return new RealmRazer(this);
    }
}

class ExileAllEffect extends OneShotEffect<ExileAllEffect> {

    public ExileAllEffect() {
        super(Outcome.Exile);
        staticText = "exile all lands";
    }

    public ExileAllEffect(final ExileAllEffect effect) {
        super(effect);
    }

    @Override
    public ExileAllEffect copy() {
        return new ExileAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(new FilterLandPermanent(), source.getControllerId(), source.getId(), game);
        for (Permanent permanent: permanents) {
            permanent.moveToExile(source.getSourceId(), "Realm Razer", source.getSourceId(), game);
        }
        return true;
    }

}


class RealmRazerEffect extends OneShotEffect<RealmRazerEffect> {

    public RealmRazerEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return the exiled cards to the battlefield tapped under their owners' control";
    }

    public RealmRazerEffect(final RealmRazerEffect effect) {
        super(effect);
    }

    @Override
    public RealmRazerEffect copy() {
        return new RealmRazerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone exZone = game.getExile().getExileZone(source.getSourceId());
        if (exZone != null) {
            for (Card card : exZone.getCards(game)) {
                if (card != null) {
                    if(card.putOntoBattlefield(game, Zone.EXILED, source.getSourceId(), card.getOwnerId())){
                        game.getPermanent(card.getId()).setTapped(true);
                    }
                }
            }
            return true;
        }
        return false;
    }
}