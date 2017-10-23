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
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.watchers.common.CastFromHandWatcher;

/**
 *
 * @author L_J
 */
public class Hypnox extends CardImpl {

    public Hypnox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{8}{B}{B}{B}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Hypnox enters the battlefield, if you cast it from your hand, exile all cards from target opponent's hand.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new HypnoxExileEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(new ConditionalTriggeredAbility(ability, CastFromHandSourceCondition.instance,
                "When {this} enters the battlefield, if you cast it from your hand, exile all cards from target opponent's hand."), new CastFromHandWatcher());

        // When Hypnox leaves the battlefield, return the exiled cards to their owner's hand.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new HypnoxReturnEffect(), false));
    }

    public Hypnox(final Hypnox card) {
        super(card);
    }

    @Override
    public Hypnox copy() {
        return new Hypnox(this);
    }
}

class HypnoxExileEffect extends OneShotEffect {
    HypnoxExileEffect() {
        super(Outcome.Exile);
        staticText = "Exile all cards from target opponent's hand";
    }

    HypnoxExileEffect(final HypnoxExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            for (UUID cid : player.getHand().copy()) {
                Card c = game.getCard(cid);
                if (c != null) {
                    c.moveToExile(source.getSourceId(), "Hypnox", source.getSourceId(), game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public HypnoxExileEffect copy() {
        return new HypnoxExileEffect(this);
    }

 }
 
class HypnoxReturnEffect extends OneShotEffect {

    public HypnoxReturnEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return the exiled cards to their owner's hand";
    }

    public HypnoxReturnEffect(final HypnoxReturnEffect effect) {
        super(effect);
    }

    @Override
    public HypnoxReturnEffect copy() {
        return new HypnoxReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ExileZone exZone = game.getExile().getExileZone(source.getSourceId());
            if (exZone != null) {
                return controller.moveCards(exZone.getCards(game), Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }
}
