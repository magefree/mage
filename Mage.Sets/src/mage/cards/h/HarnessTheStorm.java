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
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.CastFromHandWatcher;

/**
 *
 * @author LevelX2
 */
public class HarnessTheStorm extends CardImpl {

    public HarnessTheStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");

        // Whenever you cast an instant or sorcery spell from your hand, you may cast target card with the same name as that spell from your graveyard.
        this.addAbility(new HarnessTheStormTriggeredAbility(new HarnessTheStormEffect(),
                new FilterInstantOrSorcerySpell("an instant or sorcery spell from your hand"), false), new CastFromHandWatcher());
    }

    public HarnessTheStorm(final HarnessTheStorm card) {
        super(card);
    }

    @Override
    public HarnessTheStorm copy() {
        return new HarnessTheStorm(this);
    }

}

class HarnessTheStormTriggeredAbility extends SpellCastControllerTriggeredAbility {

    public HarnessTheStormTriggeredAbility(Effect effect, FilterSpell filter, boolean optional) {
        super(effect, filter, optional);
    }

    public HarnessTheStormTriggeredAbility(HarnessTheStormTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            CastFromHandWatcher watcher = (CastFromHandWatcher) game.getState().getWatchers().get(CastFromHandWatcher.class.getName());
            if (watcher != null && watcher.spellWasCastFromHand(event.getSourceId())) {
                Spell spell = game.getState().getStack().getSpell(event.getSourceId());
                if (spell != null) {
                    FilterCard filterCard = new FilterCard("a card named " + spell.getName() + " in your graveyard");
                    filterCard.add(new NamePredicate(spell.getName()));
                    this.getTargets().clear();
                    this.getTargets().add(new TargetCardInYourGraveyard(filterCard));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public HarnessTheStormTriggeredAbility copy() {
        return new HarnessTheStormTriggeredAbility(this);
    }

}

class HarnessTheStormEffect extends OneShotEffect {

    public HarnessTheStormEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may cast target card with the same name as that spell from your graveyard. <i>(you still pay its costs.)</i>";
    }

    public HarnessTheStormEffect(final HarnessTheStormEffect effect) {
        super(effect);
    }

    @Override
    public HarnessTheStormEffect copy() {
        return new HarnessTheStormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller != null) {
            Card card = controller.getGraveyard().get(getTargetPointer().getFirst(game, source), game);
            if (card != null) {
                if (controller.chooseUse(outcome, "Cast " + card.getIdName() + " from your graveyard?", source, game)) {
                    controller.cast(card.getSpellAbility(), game, false);
                }
            }
            return true;
        }

        return false;
    }
}
