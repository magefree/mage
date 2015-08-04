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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class NessianGameWarden extends CardImpl {

    public NessianGameWarden(UUID ownerId) {
        super(ownerId, 132, "Nessian Game Warden", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.expansionSetCode = "JOU";
        this.subtype.add("Beast");

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Nessian Game Warden enters the battlefield, look at the top X cards of your library, where X is the number of forests you control. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new NessianGameWardenEffect(), false));
    }

    public NessianGameWarden(final NessianGameWarden card) {
        super(card);
    }

    @Override
    public NessianGameWarden copy() {
        return new NessianGameWarden(this);
    }
}

class NessianGameWardenEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("forests you control");

    static {
        filter.add(new SubtypePredicate("Forest"));
    }

    public NessianGameWardenEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top X cards of your library, where X is the number of forests you control. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in any order";
    }

    public NessianGameWardenEffect(final NessianGameWardenEffect effect) {
        super(effect);
    }

    @Override
    public NessianGameWardenEffect copy() {
        return new NessianGameWardenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller == null || sourcePermanent == null) {
            return false;
        }

        Cards cards = new CardsImpl();
        int count = new PermanentsOnBattlefieldCount(filter).calculate(game, source, this);
        cards.addAll(controller.getLibrary().getTopCards(game, count));
        controller.lookAtCards(sourcePermanent.getIdName(), cards, game);

        if (!cards.isEmpty()) {
            TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCreatureCard("creature card to put into your hand"));
            if (target.canChoose(source.getSourceId(), controller.getId(), game) && controller.choose(Outcome.DrawCard, cards, target, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.revealCards(sourcePermanent.getName(), new CardsImpl(card), game);
                    cards.remove(card);
                    controller.moveCards(card, null, Zone.HAND, source, game);
                }
            }
        }

        controller.putCardsOnBottomOfLibrary(cards, game, source, true);
        return true;
    }
}
