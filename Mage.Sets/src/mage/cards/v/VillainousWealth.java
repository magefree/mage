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
package mage.cards.v;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class VillainousWealth extends CardImpl {

    public VillainousWealth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{G}{U}");

        // Target opponent exiles the top X cards of his or her library. You may cast any number of nonland cards with converted mana cost X or less from among them without paying their mana cost.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new VillainousWealthEffect());

    }

    public VillainousWealth(final VillainousWealth card) {
        super(card);
    }

    @Override
    public VillainousWealth copy() {
        return new VillainousWealth(this);
    }
}

class VillainousWealthEffect extends OneShotEffect {

    public VillainousWealthEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Target opponent exiles the top X cards of his or her library. You may cast any number of nonland cards with converted mana cost X or less from among them without paying their mana cost";
    }

    public VillainousWealthEffect(final VillainousWealthEffect effect) {
        super(effect);
    }

    @Override
    public VillainousWealthEffect copy() {
        return new VillainousWealthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        if (controller != null) {
            Player player = game.getPlayer(targetPointer.getFirst(game, source));
            FilterCard filter = new FilterNonlandCard();
            filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, source.getManaCostsToPay().getX() + 1));
            UUID exileId = CardUtil.getCardExileZoneId(game, source);
            if (player != null) {
                Cards cardsToExile = new CardsImpl();
                cardsToExile.addAll(player.getLibrary().getTopCards(game, source.getManaCostsToPay().getX()));
                controller.moveCards(cardsToExile, Zone.EXILED, source, game);
                if (controller.chooseUse(Outcome.PlayForFree, "Cast cards exiled with " + mageObject.getLogName() + "  without paying its mana cost?", source, game)) {
                    OuterLoop:
                    while (cardsToExile.count(filter, game) > 0) {
                        if (!controller.canRespond()) {
                            return false;
                        }
                        TargetCardInExile target = new TargetCardInExile(0, 1, filter, exileId, false);
                        target.setNotTarget(true);
                        while (cardsToExile.count(filter, game) > 0 && controller.choose(Outcome.PlayForFree, cardsToExile, target, game)) {
                            Card card = game.getCard(target.getFirstTarget());
                            if (card != null) {
                                controller.cast(card.getSpellAbility(), game, true);
                                cardsToExile.remove(card);
                            } else {
                                break OuterLoop;
                            }
                            target.clearChosen();
                        }
                    }
                }
            }
            return true;
        }

        return false;
    }
}
