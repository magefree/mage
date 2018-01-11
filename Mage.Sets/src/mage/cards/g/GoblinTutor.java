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
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterEnchantmentCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author spjspj
 */
public class GoblinTutor extends CardImpl {

    public GoblinTutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Roll a six-sided die. If you roll a 1, Goblin Tutor has no effect. Otherwise, search your library for the indicated card, reveal it, put it into your hand, then shuffle your library. 2 - A card named Goblin Tutor 3 - An enchantment card 4 - An artifact card 5 - A creature card 6 - An instant or sorcery card
        this.getSpellAbility().addEffect(new GoblinTutorEffect());
    }

    public GoblinTutor(final GoblinTutor card) {
        super(card);
    }

    @Override
    public GoblinTutor copy() {
        return new GoblinTutor(this);
    }
}

class GoblinTutorEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("card named Goblin Tutor");

    static {
        filter.add(new NamePredicate("Goblin Tutor"));
    }

    public GoblinTutorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Roll a six-sided die. If you roll a 1, {this} has no effect. Otherwise, search your library for the indicated card, reveal it, put it into your hand, then shuffle your library. 2 - A card named Goblin Tutor 3 - An enchantment card 4 - An artifact card 5 - A creature card 6 - An instant or sorcery card";
    }

    public GoblinTutorEffect(final GoblinTutorEffect effect) {
        super(effect);
    }

    @Override
    public GoblinTutorEffect copy() {
        return new GoblinTutorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = controller.rollDice(game, 6);

            Effect effect = null;
            // 2 - A card named Goblin Tutor 
            // 3 - An enchantment card 
            // 4 - An artifact card 
            // 5 - A creature card 
            // 6 - An instant or sorcery card
            if (amount == 2) {
                effect = new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 1, filter), true);
            } else if (amount == 3) {
                effect = new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 1, new FilterEnchantmentCard()), true);
            } else if (amount == 4) {
                effect = new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 1, new FilterArtifactCard()), true);
            } else if (amount == 5) {
                effect = new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 1, new FilterCreatureCard()), true);
            } else if (amount == 6) {
                effect = new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 1, new FilterInstantOrSorceryCard()), true);
            }

            if (effect != null) {
                effect.apply(game, source);
                return true;
            }
        }
        return false;
    }
}
