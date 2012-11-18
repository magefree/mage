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

package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.TargetSpell;

/**
 * 
 * @author nantuko
 */
public class PyromancerAscension extends CardImpl<PyromancerAscension> {

    public PyromancerAscension(UUID ownerId) {
        super(ownerId, 143, "Pyromancer Ascension", Rarity.RARE, new CardType[] { CardType.ENCHANTMENT }, "{1}{R}");
        this.expansionSetCode = "ZEN";
        this.color.setRed(true);

        // Whenever you cast an instant or sorcery spell that has the same name as a card in your graveyard, you may put a quest counter on Pyromancer Ascension.
        this.addAbility(new PyromancerAscensionQuestTriggeredAbility());
        // Whenever you cast an instant or sorcery spell while Pyromancer Ascension has two or more quest counters on it, you may copy that spell. You may choose new targets for the copy.
        this.addAbility(new PyromancerAscensionCopyTriggeredAbility());
    }

    public PyromancerAscension(final PyromancerAscension card) {
        super(card);
    }

    @Override
    public PyromancerAscension copy() {
        return new PyromancerAscension(this);
    }

}

class PyromancerAscensionQuestTriggeredAbility extends TriggeredAbilityImpl<PyromancerAscensionQuestTriggeredAbility> {

    PyromancerAscensionQuestTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.QUEST.createInstance(), true), true);
    }

    PyromancerAscensionQuestTriggeredAbility(final PyromancerAscensionQuestTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PyromancerAscensionQuestTriggeredAbility copy() {
        return new PyromancerAscensionQuestTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (isControlledInstantOrSorcery(spell)) {
                Card sourceCard = game.getCard(spell.getSourceId());
                if (sourceCard != null) {
                    for (UUID uuid : game.getPlayer(this.getControllerId()).getGraveyard()) {
                        if (!uuid.equals(sourceCard.getId())) {
                            Card card = game.getCard(uuid);
                            if (card != null && card.getName().equals(sourceCard.getName())) {
                                return true;
                            }
                        }
                    }
                }    
            }
        }
        return false;
    }

    private boolean isControlledInstantOrSorcery(Spell spell) {
        return spell != null && 
            (spell.getControllerId().equals(this.getControllerId())) && 
            (spell.getCardType().contains(CardType.INSTANT) || spell.getCardType().contains(CardType.SORCERY));
    }

    @Override
    public String getRule() {
        return "Whenever you cast an instant or sorcery spell that has the same name as a card in your graveyard, you may put a quest counter on {this}.";
    }
}

class PyromancerAscensionCopyTriggeredAbility extends TriggeredAbilityImpl<PyromancerAscensionCopyTriggeredAbility> {

    private static final FilterSpell filter = new FilterSpell();

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    PyromancerAscensionCopyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CopyTargetSpellEffect(), true);
        this.addTarget(new TargetSpell(filter));
    }

    PyromancerAscensionCopyTriggeredAbility(final PyromancerAscensionCopyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PyromancerAscensionCopyTriggeredAbility copy() {
        return new PyromancerAscensionCopyTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (isControlledInstantOrSorcery(spell)) {
                Permanent permanent = game.getBattlefield().getPermanent(this.getSourceId());
                if (permanent != null && permanent.getCounters().getCount(CounterType.QUEST) >= 2) {
                    this.getTargets().get(0).clearChosen();
                    this.getTargets().get(0).add(spell.getId(), game);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isControlledInstantOrSorcery(Spell spell) {
        return spell != null && 
            (spell.getControllerId().equals(this.getControllerId())) && 
            (spell.getCardType().contains(CardType.INSTANT) || spell.getCardType().contains(CardType.SORCERY));
    }

    @Override
    public String getRule() {
        return "Whenever you cast an instant or sorcery spell while {this} has two or more quest counters on it, you may copy that spell. You may choose new targets for the copy.";
    }
}
