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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.CantBeCounteredAbility;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.game.turn.TurnMod;
import mage.players.Player;


/**
 * @author Loki
 */
public class EmrakulTheAeonsTorn extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("colored spells");

    static {
        filter.add(Predicates.not(new ColorlessPredicate()));
    }

    public EmrakulTheAeonsTorn(UUID ownerId) {
        super(ownerId, 4, "Emrakul, the Aeons Torn", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{15}");
        this.expansionSetCode = "ROE";
        this.supertype.add("Legendary");
        this.subtype.add("Eldrazi");
        this.power = new MageInt(15);
        this.toughness = new MageInt(15);
        
        // Emrakul, the Aeons Torn can't be countered.
        this.addAbility(new CantBeCounteredAbility());
        // When you cast Emrakul, take an extra turn after this one.   
        this.addAbility(new EmrakulTheAeonsTornOnCastAbility());
        // Flying, protection from colored spells, annihilator 6
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new ProtectionAbility(filter));
        this.addAbility(new AnnihilatorAbility(6));
        // When Emrakul is put into a graveyard from anywhere, its owner shuffles his or her graveyard into his or her library.
        this.addAbility(new PutIntoGraveFromAnywhereSourceTriggeredAbility(new EmrakulTheAeonsTornEffect(), false));
    }

    public EmrakulTheAeonsTorn(final EmrakulTheAeonsTorn card) {
        super(card);
    }

    @Override
    public EmrakulTheAeonsTorn copy() {
        return new EmrakulTheAeonsTorn(this);
    }
}
class EmrakulTheAeonsTornOnCastAbility extends TriggeredAbilityImpl {

    private static final String abilityText = "When you cast {this}, take an extra turn after this one";

    EmrakulTheAeonsTornOnCastAbility() {
        super(Zone.STACK, new EmrakulExtraTurnEffect());
    }

    EmrakulTheAeonsTornOnCastAbility(EmrakulTheAeonsTornOnCastAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = (Spell) game.getObject(event.getTargetId());
        return this.getSourceId().equals(spell.getSourceId());
    }

    @Override
    public EmrakulTheAeonsTornOnCastAbility copy() {
        return new EmrakulTheAeonsTornOnCastAbility(this);
    }

    @Override
    public String getRule() {
        return abilityText;
    }
}

class EmrakulTheAeonsTornEffect extends OneShotEffect {

    EmrakulTheAeonsTornEffect() {
        super(Outcome.Benefit);
        staticText = "its owner shuffles his or her graveyard into his or her library";
    }

    EmrakulTheAeonsTornEffect(final EmrakulTheAeonsTornEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Card card: player.getGraveyard().getCards(game)) {
                card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
            }                           
            player.shuffleLibrary(game);
            return true;
        }
        return false;
    }

    @Override
    public EmrakulTheAeonsTornEffect copy() {
        return new EmrakulTheAeonsTornEffect(this);
    }
}

class EmrakulExtraTurnEffect extends OneShotEffect {

    EmrakulExtraTurnEffect() {
        super(Outcome.ExtraTurn);
        staticText = "take an extra turn after this one";
    }

    EmrakulExtraTurnEffect(final EmrakulExtraTurnEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getState().getTurnMods().add(new TurnMod(source.getControllerId(), false));
        return true;
    }

    @Override
    public EmrakulExtraTurnEffect copy() {
        return new EmrakulExtraTurnEffect(this);
    }
}
