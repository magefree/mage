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
package mage.sets.commander2015;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class Skullwinder extends CardImpl {

    public Skullwinder(UUID ownerId) {
        super(ownerId, 39, "Skullwinder", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "C15";
        this.subtype.add("Snake");
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Skullwinder enters the battlefield, return target card from your graveyard to your hand, then choose an opponent. That player returns a card from his or her graveyard to his or her hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard());
        ability.addEffect(new SkullwinderEffect());
        this.addAbility(ability);
    }

    public Skullwinder(final Skullwinder card) {
        super(card);
    }

    @Override
    public Skullwinder copy() {
        return new Skullwinder(this);
    }
}

class SkullwinderEffect extends OneShotEffect {

    public SkullwinderEffect() {
        super(Outcome.Benefit);
        this.staticText = ", then choose an opponent. That player returns a card from his or her graveyard to his or her hand";
    }

    public SkullwinderEffect(final SkullwinderEffect effect) {
        super(effect);
    }

    @Override
    public SkullwinderEffect copy() {
        return new SkullwinderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            TargetOpponent targetOpponent = new TargetOpponent(true);
            if (controller.choose(Outcome.Detriment, targetOpponent, source.getSourceId(), game)) {
                Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
                if (opponent != null) {
                    game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " has chosen " + opponent.getLogName());
                    // That player returns a card from his or her graveyard to his or her hand
                    TargetCardInYourGraveyard targetCard = new TargetCardInYourGraveyard(new FilterCard("a card from your graveyard to return to your hand"));
                    targetCard.setNotTarget(true);
                    if (opponent.choose(outcome, targetCard, source.getSourceId(), game)) {
                        Card card = game.getCard(targetCard.getFirstTarget());
                        if (card != null) {
                            opponent.moveCards(card, Zone.HAND, source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;

    }
}
