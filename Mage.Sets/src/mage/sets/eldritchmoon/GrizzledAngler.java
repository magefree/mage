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
package mage.sets.eldritchmoon;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class GrizzledAngler extends CardImpl {

    public GrizzledAngler(UUID ownerId) {
        super(ownerId, 63, "Grizzled Angler", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "EMN";
        this.subtype.add("Human");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.canTransform = true;
        this.secondSideCard = new GrislyAnglerfish(ownerId);

        // {T}: Put the top two cards of your library into your graveyard. Then if there is a colorless creature card in your graveyard, transform Grizzled Angler.
        this.addAbility(new TransformAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GrizzledAnglerEffect(), new TapSourceCost()));
    }

    public GrizzledAngler(final GrizzledAngler card) {
        super(card);
    }

    @Override
    public GrizzledAngler copy() {
        return new GrizzledAngler(this);
    }
}

class GrizzledAnglerEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a colorless creature card in your graveyard");

    static {
        filter.add(new ColorlessPredicate());
    }

    public GrizzledAnglerEffect() {
        super(Outcome.Benefit);
        staticText = "Put the top two cards of your library into your graveyard. Then if there is a colorless creature card in your graveyard, transform {this}";
    }

    public GrizzledAnglerEffect(final GrizzledAnglerEffect effect) {
        super(effect);
    }

    @Override
    public GrizzledAnglerEffect copy() {
        return new GrizzledAnglerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.moveCards(controller.getLibrary().getTopCards(game, 2), Zone.GRAVEYARD, source, game);
            if (controller.getGraveyard().count(filter, source.getSourceId(), source.getControllerId(), game) >= 1) {
                return new TransformSourceEffect(true).apply(game, source);
            }
        }
        return false;
    }
}
