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
package mage.sets.onslaught;

import java.lang.annotation.Target;
import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class SupremeInquisitor extends CardImpl<SupremeInquisitor> {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Wizards you control");

    static {
        filter.add(new SubtypePredicate("Wizard"));
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public SupremeInquisitor(UUID ownerId) {
        super(ownerId, 117, "Supreme Inquisitor", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.expansionSetCode = "ONS";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Tap five untapped Wizards you control: Search target player's library for up to five cards and exile them. Then that player shuffles his or her library.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new SupremeInquisitorEffect(), new TapTargetCost(new TargetControlledPermanent(5, 5, filter, true)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public SupremeInquisitor(final SupremeInquisitor card) {
        super(card);
    }

    @Override
    public SupremeInquisitor copy() {
        return new SupremeInquisitor(this);
    }
}

class SupremeInquisitorEffect extends OneShotEffect<SupremeInquisitorEffect> {

    private final static FilterCard filter = new FilterCard();

    public SupremeInquisitorEffect() {
        super(Constants.Outcome.Exile);
        staticText = "Search target player's library for up to five cards and exile them. Then that player shuffles his or her library";
    }

    public SupremeInquisitorEffect(final SupremeInquisitorEffect effect) {
        super(effect);
    }

    @Override
    public SupremeInquisitorEffect copy() {
        return new SupremeInquisitorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && targetPlayer != null) {
            Cards targetLibrary = new CardsImpl();
            targetLibrary.addAll(targetPlayer.getLibrary().getCardList());
            TargetCardInLibrary target = new TargetCardInLibrary(0, 5, filter);
            if (player.choose(Constants.Outcome.Benefit, targetLibrary, target, game)) {
                List<UUID> targetId = target.getTargets();
                for (UUID targetCard : targetId) {
                    Card card = targetPlayer.getLibrary().remove(targetCard, game);
                    if (card != null) {
                        card.moveToExile(getId(), "Supreme Inquisitor", source.getSourceId(), game);
                    }
                }
            }
        }

        targetPlayer.shuffleLibrary(game);
        return true;
    }
}