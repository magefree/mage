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
package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.abilities.effects.common.continuous.PlayWithTheTopCardRevealedEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class MelekIzzetParagon extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public MelekIzzetParagon(UUID ownerId) {
        super(ownerId, 84, "Melek, Izzet Paragon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{U}{R}");
        this.expansionSetCode = "DGM";
        this.supertype.add("Legendary");
        this.subtype.add("Weird");
        this.subtype.add("Wizard");

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Play with the top card of your library revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithTheTopCardRevealedEffect()));

        // You may cast the top card of your library if it's an instant or sorcery card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayTheTopCardEffect(filter)));

        // Whenever you cast an instant or sorcery spell from your library, copy it. You may choose new targets for the copy.
        this.addAbility(new MelekIzzetParagonTriggeredAbility());
    }

    public MelekIzzetParagon(final MelekIzzetParagon card) {
        super(card);
    }

    @Override
    public MelekIzzetParagon copy() {
        return new MelekIzzetParagon(this);
    }
}

class MelekIzzetParagonTriggeredAbility extends TriggeredAbilityImpl {

    public MelekIzzetParagonTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CopyTargetSpellEffect(), false);
    }

    public MelekIzzetParagonTriggeredAbility(final MelekIzzetParagonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MelekIzzetParagonTriggeredAbility copy() {
        return new MelekIzzetParagonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getZone() == Zone.LIBRARY) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null
                    && spell.getOwnerId().equals(super.getControllerId())
                    && (spell.getCardType().contains(CardType.INSTANT)
                    || spell.getCardType().contains(CardType.SORCERY))) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast an instant or sorcery from your library, copy it. You may choose new targets for the copy.";
    }
}
