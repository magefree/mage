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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.ExileCardsFromTopOfLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class OblivionSower extends CardImpl {

    public OblivionSower(UUID ownerId) {
        super(ownerId, 11, "Oblivion Sower", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{6}");
        this.expansionSetCode = "BFZ";
        this.subtype.add("Eldrazi");
        this.power = new MageInt(5);
        this.toughness = new MageInt(8);

        // When you cast Oblivion Sower, target opponent exiles the top four cards of his or her library, then you may put any number of land cards that player owns from exile onto the battlefield under your control.
        Ability ability = new CastSourceTriggeredAbility(new ExileCardsFromTopOfLibraryTargetEffect(4, "target opponent"), false);
        ability.addEffect(new OblivionSowerEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public OblivionSower(final OblivionSower card) {
        super(card);
    }

    @Override
    public OblivionSower copy() {
        return new OblivionSower(this);
    }
}

class OblivionSowerEffect extends OneShotEffect {

    public OblivionSowerEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = ", then you may put any number of land cards that player owns from exile onto the battlefield under your control";
    }

    public OblivionSowerEffect(final OblivionSowerEffect effect) {
        super(effect);
    }

    @Override
    public OblivionSowerEffect copy() {
        return new OblivionSowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && targetPlayer != null) {
            FilterLandCard filter = new FilterLandCard();
            filter.add(new OwnerIdPredicate(targetPlayer.getId()));
            Cards exiledCards = new CardsImpl();
            exiledCards.addAll(game.getExile().getAllCards(game));
            Cards exiledLands = new CardsImpl();
            exiledLands.addAll(exiledCards.getCards(filter, source.getSourceId(), controller.getId(), game));
            if (!exiledLands.isEmpty() && controller.chooseUse(outcome, "Put lands into play?", source, game)) {
                FilterCard filterToPlay = new FilterCard("Lands owned by " + targetPlayer.getName() + " to put into play under your control");
                TargetCard targetCards = new TargetCard(0, exiledLands.size(), Zone.EXILED, filterToPlay);
                if (controller.chooseTarget(outcome, exiledLands, targetCards, source, game)) {
                    controller.moveCards(new CardsImpl(targetCards.getTargets()), null, Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
