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
package mage.abilities.keyword;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public class PartnerWithAbility extends EntersBattlefieldTriggeredAbility {

    private final String partnerName;
    private final String shortName;

    public PartnerWithAbility(String partnerName) {
        this(partnerName, false);
    }

    public PartnerWithAbility(String partnerName, boolean isLegendary) {
        super(new PartnersWithSearchEffect(partnerName), false);
        this.addTarget(new TargetPlayer());
        this.partnerName = partnerName;
        if (isLegendary) {
            this.shortName = shortenName(partnerName);
        } else {
            this.shortName = partnerName;
        }
    }

    public PartnerWithAbility(final PartnerWithAbility ability) {
        super(ability);
        this.partnerName = ability.partnerName;
        this.shortName = ability.shortName;
    }

    @Override
    public PartnerWithAbility copy() {
        return new PartnerWithAbility(this);
    }

    @Override
    public String getRule() {
        return "Partner with " + partnerName
                + " <i>(When this creature enters the battlefield, target player may put " + shortName
                + " into their hand from their library, then shuffle.)</i>";
    }

    public String getPartnerName() {
        return partnerName;
    }

    public static String shortenName(String st) {
        StringBuilder sb = new StringBuilder();
        for (char s : st.toCharArray()) {
            if (s == ' ' || s == ',') {
                break;
            } else {
                sb.append(s);
            }
        }
        return sb.toString();
    }
}

class PartnersWithSearchEffect extends OneShotEffect {

    private final String partnerName;

    public PartnersWithSearchEffect(String partnerName) {
        super(Outcome.Detriment);
        this.partnerName = partnerName;
        this.staticText = "";
    }

    public PartnersWithSearchEffect(final PartnersWithSearchEffect effect) {
        super(effect);
        this.partnerName = effect.partnerName;
    }

    @Override
    public PartnersWithSearchEffect copy() {
        return new PartnersWithSearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player player = game.getPlayer(source.getFirstTarget());
            if (player != null) {
                FilterCard filter = new FilterCard("card named " + partnerName);
                filter.add(new NamePredicate(partnerName));
                TargetCardInLibrary target = new TargetCardInLibrary(filter);
                if (player.chooseUse(Outcome.Benefit, "Search your library for a card named " + partnerName + " and put it into your hand?", source, game)) {
                    player.searchLibrary(target, game);
                    for (UUID cardId : target.getTargets()) {
                        Card card = player.getLibrary().getCard(cardId, game);
                        if (card != null) {
                            player.revealCards(source, new CardsImpl(card), game);
                            player.moveCards(card, Zone.HAND, source, game);
                        }
                    }
                    player.shuffleLibrary(source, game);
                }
            }
            // prevent undo
            controller.resetStoredBookmark(game);
            return true;
        }
        return false;
    }
}
