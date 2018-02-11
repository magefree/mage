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
package mage.cards.s;

import java.util.ArrayList;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.StanggTwinToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth & L_J
 */
public class Stangg extends CardImpl {

    public Stangg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Stangg enters the battlefield, create a legendary 3/4 red and green Human Warrior creature token named Stangg Twin.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new StanggCreateTokenEffect(), false));

        // When Stangg leaves the battlefield, exile that token. 
        // When that token leaves the battlefield, sacrifice Stangg.
        Ability ability = new LeavesBattlefieldTriggeredAbility(new StanggExileTokenEffect(), false);
        ability.addEffect(new InfoEffect("When that token leaves the battlefield, sacrifice {this}"));
        this.addAbility(ability);

    }

    public Stangg(final Stangg card) {
        super(card);
    }

    @Override
    public Stangg copy() {
        return new Stangg(this);
    }
}

class StanggCreateTokenEffect extends OneShotEffect {

    public StanggCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create a legendary 3/4 red and green Human Warrior creature token named Stangg Twin";
    }

    public StanggCreateTokenEffect(final StanggCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourceObject != null) {
            CreateTokenEffect effect = new CreateTokenEffect(new StanggTwinToken());
            effect.apply(game, source);
            game.getState().setValue(source.getSourceId() + "_token", effect.getLastAddedTokenIds());
            for (UUID addedTokenId : effect.getLastAddedTokenIds()) {
                Effect sacrificeEffect = new SacrificeTargetEffect("sacrifice " + sourceObject.getName());
                sacrificeEffect.setTargetPointer(new FixedTarget(sourceObject, game));
                LeavesBattlefieldTriggeredAbility triggerAbility = new LeavesBattlefieldTriggeredAbility(sacrificeEffect, false);
                ContinuousEffect continuousEffect = new GainAbilityTargetEffect(triggerAbility, Duration.WhileOnBattlefield);
                continuousEffect.setTargetPointer(new FixedTarget(addedTokenId, game));
                game.addEffect(continuousEffect, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public StanggCreateTokenEffect copy() {
        return new StanggCreateTokenEffect(this);
    }
}

class StanggExileTokenEffect extends OneShotEffect {

    public StanggExileTokenEffect() {
        super(Outcome.Removal);
        staticText = "exile that token";
    }

    public StanggExileTokenEffect(final StanggExileTokenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ArrayList<UUID> tokenIds = (ArrayList<UUID>) game.getState().getValue(source.getSourceId() + "_token");
            if (tokenIds != null) {
                Cards cards = new CardsImpl();
                for (UUID tokenId : tokenIds) {
                    Permanent tokenPermanent = game.getPermanent(tokenId);
                    if (tokenPermanent != null) {
                        cards.add(tokenPermanent);
                    }
                }
                controller.moveCards(cards, Zone.EXILED, source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public StanggExileTokenEffect copy() {
        return new StanggExileTokenEffect(this);
    }
}
