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
package mage.sets.coldsnap;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author emerald000
 */
public class ArcumDagsson extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("artifact creature");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public ArcumDagsson(UUID ownerId) {
        super(ownerId, 27, "Arcum Dagsson", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "CSP";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Artificer");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Target artifact creature's controller sacrifices it. That player may search his or her library for a noncreature artifact card, put it onto the battlefield, then shuffle his or her library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ArcumDagssonEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    public ArcumDagsson(final ArcumDagsson card) {
        super(card);
    }

    @Override
    public ArcumDagsson copy() {
        return new ArcumDagsson(this);
    }
}

class ArcumDagssonEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterArtifactCard("noncreature artifact card");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    ArcumDagssonEffect() {
        super(Outcome.Removal);
        this.staticText = "Target artifact creature's controller sacrifices it. That player may search his or her library for a noncreature artifact card, put it onto the battlefield, then shuffle his or her library";
    }

    ArcumDagssonEffect(final ArcumDagssonEffect effect) {
        super(effect);
    }

    @Override
    public ArcumDagssonEffect copy() {
        return new ArcumDagssonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent artifactCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (artifactCreature != null) {
            Player player = game.getPlayer(artifactCreature.getControllerId());
            if (player != null) {
                artifactCreature.sacrifice(source.getSourceId(), game);
                if (player.chooseUse(Outcome.PutCardInPlay, "Search your library for a noncreature artifact card?", source, game)) {
                    TargetCardInLibrary target = new TargetCardInLibrary(filter);
                    if (player.searchLibrary(target, game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null) {
                            player.moveCards(card, Zone.BATTLEFIELD, source, game);
                        }
                    }
                    player.shuffleLibrary(game);
                }
                return true;
            }
        }
        return false;
    }
}
