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
package mage.sets.mirage;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author Quercitron
 */
public class WaitingInTheWeeds extends CardImpl<WaitingInTheWeeds> {

    public WaitingInTheWeeds(UUID ownerId) {
        super(ownerId, 150, "Waiting in the Weeds", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{1}{G}{G}");
        this.expansionSetCode = "MIR";

        this.color.setGreen(true);

        // Each player puts a 1/1 green Cat creature token onto the battlefield for each untapped Forest he or she controls.
        this.getSpellAbility().addEffect(new WaitingInTheWeedsEffect());
    }

    public WaitingInTheWeeds(final WaitingInTheWeeds card) {
        super(card);
    }

    @Override
    public WaitingInTheWeeds copy() {
        return new WaitingInTheWeeds(this);
    }
}

class WaitingInTheWeedsEffect extends OneShotEffect<WaitingInTheWeedsEffect> {

    private static final FilterPermanent filter = new FilterPermanent("untapped Forest he or she controls");
    
    static {
        filter.add(new SubtypePredicate("Forest"));
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public WaitingInTheWeedsEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Each player puts a 1/1 green Cat creature token onto the battlefield for each untapped Forest he or she controls";
    }

    public WaitingInTheWeedsEffect(final WaitingInTheWeedsEffect effect) {
        super(effect);
    }

    @Override
    public WaitingInTheWeedsEffect copy() {
        return new WaitingInTheWeedsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : controller.getInRange()) {
                Token token = new CatToken();
                int amount = game.getBattlefield().getAllActivePermanents(filter, playerId, game).size();
                token.putOntoBattlefield(amount, game, source.getSourceId(), playerId);
            }
            return true;
        }
        return false;
    }
}

class CatToken extends Token {

    public CatToken() {
        super("Cat", "1/1 green Cat creature token");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.GREEN;
        subtype.add("Cat");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}