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
package mage.sets.newphyrexia;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public class BrutalizerExarch extends CardImpl<BrutalizerExarch> {

    private static final FilterPermanent filter = new FilterPermanent("noncreature permanent");

    static {
        filter.getNotCardType().add(CardType.CREATURE);
    }

    public BrutalizerExarch(UUID ownerId) {
        super(ownerId, 105, "Brutalizer Exarch", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{5}{G}");
        this.expansionSetCode = "NPH";
        this.subtype.add("Cleric");

        this.color.setGreen(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        Ability ability = new EntersBattlefieldTriggeredAbility(new BrutalizerExarchEffect1());
        Mode mode = new Mode();
        mode.getEffects().add(new BrutalizerExarchEffect2());
        mode.getTargets().add(new TargetPermanent(filter));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    public BrutalizerExarch(final BrutalizerExarch card) {
        super(card);
    }

    @Override
    public BrutalizerExarch copy() {
        return new BrutalizerExarch(this);
    }
}

class BrutalizerExarchEffect1 extends OneShotEffect<BrutalizerExarchEffect1> {

    public BrutalizerExarchEffect1() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for a creature card, reveal it, then shuffle your library and put that card on top of it";
    }

    public BrutalizerExarchEffect1(final BrutalizerExarchEffect1 effect) {
        super(effect);
    }

    @Override
    public BrutalizerExarchEffect1 copy() {
        return new BrutalizerExarchEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(new FilterCreatureCard("creature card in your library"));
            target.setRequired(true);
            if (player.searchLibrary(target, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    Cards cards = new CardsImpl();
                    cards.add(card);
                    player.revealCards("Brutalizer Exarch", cards, game);
                    player.shuffleLibrary(game);
                    card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
                    return true;
                }
            }
        }
        return false;
    }
}

class BrutalizerExarchEffect2 extends OneShotEffect<BrutalizerExarchEffect2> {

    public BrutalizerExarchEffect2() {
        super(Outcome.Removal);
        this.staticText = "put target noncreature permanent on the bottom of its owner's library";
    }

    public BrutalizerExarchEffect2(final BrutalizerExarchEffect2 effect) {
        super(effect);
    }

    @Override
    public BrutalizerExarchEffect2 copy() {
        return new BrutalizerExarchEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getOwnerId());
            if (player == null) {
                return false;
            }

            return permanent.moveToZone(Zone.LIBRARY, source.getId(), game, false);
        }
        return false;
    }
}
