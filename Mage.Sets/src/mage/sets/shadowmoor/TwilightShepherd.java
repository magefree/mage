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
package mage.sets.shadowmoor;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.PersistAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

/**
 *
 * @author jeffwadsworth
 *
 */
public class TwilightShepherd extends CardImpl<TwilightShepherd> {

    public TwilightShepherd(UUID ownerId) {
        super(ownerId, 25, "Twilight Shepherd", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}{W}{W}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Angel");

        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Twilight Shepherd enters the battlefield, return to your hand all cards in your graveyard that were put there from the battlefield this turn.
        this.addWatcher(new CardsPutIntoGraveyardWatcher());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TwilightShepherdEffect(), false));

        // Persist
        this.addAbility(new PersistAbility());
    }

    public TwilightShepherd(final TwilightShepherd card) {
        super(card);
    }

    @Override
    public TwilightShepherd copy() {
        return new TwilightShepherd(this);
    }
}

class TwilightShepherdEffect extends OneShotEffect<TwilightShepherdEffect> {

    boolean applied = false;

    public TwilightShepherdEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return to your hand all cards in your graveyard that were put there from the battlefield this turn";
    }

    public TwilightShepherdEffect(final TwilightShepherdEffect effect) {
        super(effect);
    }

    @Override
    public TwilightShepherdEffect copy() {
        return new TwilightShepherdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CardsPutIntoGraveyardWatcher watcher = (CardsPutIntoGraveyardWatcher) game.getState().getWatchers().get("CardsPutIntoGraveyardWatcher");
        if (watcher != null) {
            Set<UUID> cardsInGraveyardId = watcher.getCardsPutToGraveyardFromBattlefield();
            for (UUID cardId : cardsInGraveyardId) {
                Card card = game.getCard(cardId);
                if (card != null 
                        && card.getOwnerId().equals(source.getControllerId())
                        && game.getState().getZone(card.getId()).match(Zone.GRAVEYARD)) {
                    applied = card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                }
            }
        }
        return applied;
    }
}