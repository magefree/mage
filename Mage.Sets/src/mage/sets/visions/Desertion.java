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
package mage.sets.visions;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;

/**
 *
 * @author Quercitron
 */
public class Desertion extends CardImpl {

    public Desertion(UUID ownerId) {
        super(ownerId, 30, "Desertion", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");
        this.expansionSetCode = "VIS";


        // Counter target spell. If an artifact or creature spell is countered this way, put that card onto the battlefield under your control instead of into its owner's graveyard.
        this.getSpellAbility().addEffect(new DesertionEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public Desertion(final Desertion card) {
        super(card);
    }

    @Override
    public Desertion copy() {
        return new Desertion(this);
    }
}

class DesertionEffect extends OneShotEffect {

    private static final FilterSpell filter = new FilterSpell("artifact or creature spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)));
    }
    
    public DesertionEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell. If an artifact or creature spell is countered this way, put that card onto the battlefield under your control instead of into its owner's graveyard.";
    }

    public DesertionEffect(final DesertionEffect effect) {
        super(effect);
    }

    @Override
    public DesertionEffect copy() {
        return new DesertionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID objectId = source.getFirstTarget();
        UUID sourceId = source.getSourceId();

        StackObject stackObject = game.getStack().getStackObject(objectId);
        if (stackObject != null && !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.COUNTER, objectId, sourceId, stackObject.getControllerId()))) {
            if (stackObject instanceof Spell) {
                game.rememberLKI(objectId, Zone.STACK, (Spell) stackObject);
                game.getStack().remove(stackObject);
                if (!((Spell) stackObject).isCopiedSpell() && filter.match(stackObject, source.getControllerId(), game)) {
                    MageObject card = game.getObject(stackObject.getSourceId());
                    if (card instanceof Card) {
                        ((Card) card).putOntoBattlefield(game, Zone.STACK, source.getSourceId(), source.getControllerId());
                    }
                } else {
                    stackObject.counter(sourceId, game);
                }
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTERED, objectId, sourceId, stackObject.getControllerId()));
                return true;
            }
        }
        return false;
    }
    
}
