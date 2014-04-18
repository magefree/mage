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
package mage.sets.journeyintonyx;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class QuarryColossus extends CardImpl<QuarryColossus> {

    public QuarryColossus(UUID ownerId) {
        super(ownerId, 22, "Quarry Colossus", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");
        this.expansionSetCode = "JOU";
        this.subtype.add("Giant");

        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // When Quarry Colossus enters the battlefield, put target creature into its owner's library just beneath the top X cards of that library, where X is the number of Plains you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new QuarryColossusReturnLibraryEffect(), false);
        ability.addTarget(new TargetCreaturePermanent(true));
        this.addAbility(ability);
    }

    public QuarryColossus(final QuarryColossus card) {
        super(card);
    }

    @Override
    public QuarryColossus copy() {
        return new QuarryColossus(this);
    }
}

class QuarryColossusReturnLibraryEffect extends OneShotEffect<QuarryColossusReturnLibraryEffect> {

    public QuarryColossusReturnLibraryEffect() {
        super(Outcome.Benefit);
        this.staticText = "put target creature into its owner's library just beneath the top X cards of that library, where X is the number of Plains you control";
    }

    public QuarryColossusReturnLibraryEffect(final QuarryColossusReturnLibraryEffect effect) {
        super(effect);
    }

    @Override
    public QuarryColossusReturnLibraryEffect copy() {
        return new QuarryColossusReturnLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (permanent != null && controller != null) {
            Player owner = game.getPlayer(permanent.getOwnerId());
            if (owner != null) {
                int plains = game.getBattlefield().countAll(new FilterPermanent("Plains", "Plains you control"), source.getControllerId(), game);
                int xValue = Math.min(plains, owner.getLibrary().size());
                Cards cards = new CardsImpl();
                List<UUID> cardIds = new ArrayList<>();
                for (int i = 0; i < xValue; i++) {
                    Card card = owner.getLibrary().getFromTop(game);
                    cards.add(card);
                    cardIds.add(card.getId());
                }
                // return cards back to library
                controller.moveCardToLibraryWithInfo(permanent, source.getSourceId(), game, Zone.BATTLEFIELD, true);
                ListIterator<UUID> libraryCards = cardIds.listIterator();
                while(libraryCards.hasPrevious()) {
                    UUID cardId = libraryCards.previous();
                    Card card = cards.get(cardId, game);
                    if (card != null) {
                        card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
