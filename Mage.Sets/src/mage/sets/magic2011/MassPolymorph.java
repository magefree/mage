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
package mage.sets.magic2011;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MassPolymorph extends CardImpl {

    public MassPolymorph(UUID ownerId) {
        super(ownerId, 64, "Mass Polymorph", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{U}");
        this.expansionSetCode = "M11";

        // Exile all creatures you control, then reveal cards from the top of your library until you reveal that many creature cards.
        // Put all creature cards revealed this way onto the battlefield, then shuffle the rest of the revealed cards into your library.
        this.getSpellAbility().addEffect(new MassPolymorphEffect());
    }

    public MassPolymorph(final MassPolymorph card) {
        super(card);
    }

    @Override
    public MassPolymorph copy() {
        return new MassPolymorph(this);
    }
}

class MassPolymorphEffect extends OneShotEffect {

    public MassPolymorphEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Exile all creatures you control, then reveal cards from the top of your library until you reveal that many creature cards. Put all creature cards revealed this way onto the battlefield, then shuffle the rest of the revealed cards into your library";
    }

    public MassPolymorphEffect(final MassPolymorphEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            int count;
            // Cards creatures = new CardsImpl();
            List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game);
            Set<Card> creaturesToExile = new HashSet<>();
            creaturesToExile.addAll(creatures);
            count = creatures.size();
            controller.moveCards(creaturesToExile, Zone.HAND, source, game);

            Cards revealed = new CardsImpl();
            Set<Card> creatureCards = new LinkedHashSet<>();
            Cards nonCreatureCards = new CardsImpl();
            while (creatureCards.size() < count && controller.getLibrary().size() > 0) {
                Card card = controller.getLibrary().removeFromTop(game);
                revealed.add(card);
                if (card.getCardType().contains(CardType.CREATURE)) {
                    creatureCards.add(card);
                } else {
                    nonCreatureCards.add(card);
                }
            }
            controller.revealCards(sourceObject.getIdName(), revealed, game);
            controller.moveCards(creatureCards, Zone.BATTLEFIELD, source, game, false, false, true, null);
            controller.putCardsOnTopOfLibrary(nonCreatureCards, game, source, false);
            controller.shuffleLibrary(game);
            return true;
        }
        return false;
    }

    @Override
    public MassPolymorphEffect copy() {
        return new MassPolymorphEffect(this);
    }

}
