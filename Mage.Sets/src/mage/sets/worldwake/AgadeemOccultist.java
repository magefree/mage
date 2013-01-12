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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public class AgadeemOccultist extends CardImpl<AgadeemOccultist> {

    public AgadeemOccultist(UUID ownerId) {
        super(ownerId, 48, "Agadeem Occultist", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Human");
        this.subtype.add("Shaman");
        this.subtype.add("Ally");

        this.color.setBlack(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {tap}: Put target creature card from an opponent's graveyard onto the battlefield under your control if its converted mana cost is less than or equal to the number of Allies you control.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new AgadeemOccultistEffect(), new TapSourceCost()));

    }

    public AgadeemOccultist(final AgadeemOccultist card) {
        super(card);
    }

    @Override
    public AgadeemOccultist copy() {
        return new AgadeemOccultist(this);
    }
}

class AgadeemOccultistEffect extends OneShotEffect<AgadeemOccultistEffect> {

    public AgadeemOccultistEffect() {
        super(Constants.Outcome.GainControl);
        this.staticText = "Put target creature card from an opponent's graveyard onto the battlefield under your control if its converted mana cost is less than or equal to the number of Allies you control";
    }

    public AgadeemOccultistEffect(final AgadeemOccultistEffect effect) {
        super(effect);
    }

    @Override
    public AgadeemOccultistEffect copy() {
        return new AgadeemOccultistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player you = game.getPlayer(source.getControllerId());
        int allycount = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent.hasSubtype("Ally")) {
                allycount++;
            }
        }

        FilterCard filter = new FilterCard("creature card in an opponent's graveyard");
        filter.add(new CardTypePredicate(CardType.CREATURE));
        TargetCardInOpponentsGraveyard target = new TargetCardInOpponentsGraveyard(1, 1, filter);

        if (you != null) {
            if (target.canChoose(source.getControllerId(), game)
                    && you.choose(Constants.Outcome.GainControl, target, source.getSourceId(), game)) {
                if (!target.getTargets().isEmpty()) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        if (card.getManaCost().convertedManaCost() <= allycount) {
                            card.putOntoBattlefield(game, Constants.Zone.GRAVEYARD, source.getId(), source.getControllerId());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
