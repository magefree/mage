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
package mage.sets.prereleaseevents;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

/**
 *
 * @author LevelX2
 */
public class Gleancrawler extends CardImpl {

    public Gleancrawler(UUID ownerId) {
        super(ownerId, 27, "Gleancrawler", Rarity.SPECIAL, new CardType[]{CardType.CREATURE}, "{3}{B/G}{B/G}{B/G}");
        this.expansionSetCode = "PTC";
        this.subtype.add("Insect");
        this.subtype.add("Horror");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // <i>({B/G} can be paid with either {B} or {G}.)</i>
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("<i>({B/G} can be paid with either {B} or {G}.)</i>")));
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of your end step, return to your hand all creature cards in your graveyard that were put there from the battlefield this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new GleancrawlerEffect(), TargetController.YOU, false), new CardsPutIntoGraveyardWatcher());

    }

    public Gleancrawler(final Gleancrawler card) {
        super(card);
    }

    @Override
    public Gleancrawler copy() {
        return new Gleancrawler(this);
    }
}

class GleancrawlerEffect extends OneShotEffect {

    boolean applied = false;

    public GleancrawlerEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return to your hand all creature cards in your graveyard that were put there from the battlefield this turn";
    }

    public GleancrawlerEffect(final GleancrawlerEffect effect) {
        super(effect);
    }

    @Override
    public GleancrawlerEffect copy() {
        return new GleancrawlerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CardsPutIntoGraveyardWatcher watcher = (CardsPutIntoGraveyardWatcher) game.getState().getWatchers().get("CardsPutIntoGraveyardWatcher");
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && watcher != null) {
            Set<MageObjectReference> cardsToGraveyardThisTurn = watcher.getCardsPutToGraveyardFromBattlefield();
            Cards cardsToHand = new CardsImpl();
            for (MageObjectReference mor : cardsToGraveyardThisTurn) {
                if (game.getState().getZoneChangeCounter(mor.getSourceId()) == mor.getZoneChangeCounter()) {
                    Card card = game.getCard(mor.getSourceId());
                    if (card != null && card.getCardType().contains(CardType.CREATURE)
                            && card.getOwnerId().equals(source.getControllerId())) {
                        cardsToHand.add(card);
                    }
                }
            }
            controller.moveCards(cardsToHand, Zone.GRAVEYARD, Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}
