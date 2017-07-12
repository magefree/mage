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
package mage.cards.t;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author jeffwadsworth
 */
public class TwinningGlass extends CardImpl {

    public TwinningGlass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {1}, {tap}: You may cast a nonland card from your hand without paying its mana cost if it has the same name as a spell that was cast this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TwinningGlassEffect(), new ManaCostsImpl("{1}"));
        ability.addWatcher(new SpellsCastWatcher());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    public TwinningGlass(final TwinningGlass card) {
        super(card);
    }

    @Override
    public TwinningGlass copy() {
        return new TwinningGlass(this);
    }
}

class TwinningGlassEffect extends OneShotEffect {

    public TwinningGlassEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "You may cast a nonland card from your hand without paying its mana cost if it has the same name as a spell that was cast this turn";
    }

    public TwinningGlassEffect(final TwinningGlassEffect effect) {
        super(effect);
    }

    @Override
    public TwinningGlassEffect copy() {
        return new TwinningGlassEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCard filterCard = new FilterCard();
        filterCard.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
        Permanent twinningGlass = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        SpellsCastWatcher watcher = (SpellsCastWatcher) game.getState().getWatchers().get(SpellsCastWatcher.class.getSimpleName());
        if (twinningGlass == null) {
            twinningGlass = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (twinningGlass != null
                && controller != null
                && watcher != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                List<Spell> spells = watcher.getSpellsCastThisTurn(playerId);
                if (spells != null
                        && !spells.isEmpty()) {
                    for (Spell spell : spells) {
                        filterCard.add(new NamePredicate(spell.getName()));
                    }
                }
            }
            TargetCardInHand target = new TargetCardInHand(0, 1, filterCard);
            if (controller.choose(Outcome.Benefit, controller.getHand(), target, game)) {
                Card chosenCard = game.getCard(target.getFirstTarget());
                if (chosenCard != null) {
                    if (controller.chooseUse(outcome, "Cast the card without paying mana cost?", source, game)) {
                        return controller.cast(chosenCard.getSpellAbility(), game, true);
                    }
                }
            }
        }
        return false;
    }
}
