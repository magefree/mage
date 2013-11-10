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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class Foster extends CardImpl<Foster> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature you control");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public Foster(UUID ownerId) {
        super(ownerId, 146, "Foster", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");
        this.expansionSetCode = "C13";

        this.color.setGreen(true);

        // Whenever a creature you control dies, you may pay {1}. If you do, reveal cards from the top of your library until you reveal a creature card. Put that card into your hand and the rest into your graveyard.
        Ability ability = new DiesCreatureTriggeredAbility(
                new DoIfCostPaid(new FosterEffect(), new GenericManaCost(1)),
                false, filter);
        this.addAbility(ability);
    }

    public Foster(final Foster card) {
        super(card);
    }

    @Override
    public Foster copy() {
        return new Foster(this);
    }
}

class FosterEffect extends OneShotEffect<FosterEffect> {

    private static final FilterCreatureCard filter = new FilterCreatureCard();

    public FosterEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "reveal cards from the top of your library until you reveal a creature card. Put that card into your hand and the rest into your graveyard";
    }

    public FosterEffect(final FosterEffect effect) {
        super(effect);
    }

    @Override
    public FosterEffect copy() {
        return new FosterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard == null) {
            return false;
        }
        Cards cards = new CardsImpl(Zone.PICK);
        while (controller.getLibrary().size() > 0) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                cards.add(card);
                if(filter.match(card, game)){
                    card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                    break;
                }
                else{
                    card.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, false);
                }
            }
        }
        controller.revealCards(sourceCard.getName(), cards, game);
        return true;
    }
}
