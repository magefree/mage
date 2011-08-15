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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.CantCounterAbility;
import mage.abilities.common.PutIntoGraveFromAnywhereTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.turn.TurnMod;
import mage.players.Player;

/**
* @author Loki
*/
public class EmrakultheAeonsTorn extends CardImpl<EmrakultheAeonsTorn> {
    private static FilterSpell filter = new FilterSpell("colored spells");

    static {
        filter.setColorless(false);
        filter.setUseColorless(true);
    }

    public EmrakultheAeonsTorn(UUID ownerId) {
        super(ownerId, 4, "Emrakul, the Aeons Torn", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{15}");
        this.expansionSetCode = "ROE";
        this.supertype.add("Legendary");
        this.subtype.add("Eldrazi");
        this.power = new MageInt(15);
        this.toughness = new MageInt(15);
        this.addAbility(new CantCounterAbility());
        this.addAbility(new EmrakultheAeonsTornOnCastAbility());
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new ProtectionAbility(filter));
        this.addAbility(new AnnihilatorAbility(6));
        this.addAbility(new PutIntoGraveFromAnywhereTriggeredAbility(new EmrakultheAeonsTornEffect(), false));
    }

    public EmrakultheAeonsTorn(final EmrakultheAeonsTorn card) {
        super(card);
    }

    @Override
    public EmrakultheAeonsTorn copy() {
        return new EmrakultheAeonsTorn(this);
    }

}

class EmrakultheAeonsTornOnCastAbility extends TriggeredAbilityImpl<EmrakultheAeonsTornOnCastAbility> {

    private static final String abilityText = "When you cast {this}, take an extra turn after this one";

    EmrakultheAeonsTornOnCastAbility() {
        super(Constants.Zone.STACK, new EmrakulExtraTurnEffect());
    }

    EmrakultheAeonsTornOnCastAbility(EmrakultheAeonsTornOnCastAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (this.getSourceId().equals(spell.getSourceId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public EmrakultheAeonsTornOnCastAbility copy() {
        return new EmrakultheAeonsTornOnCastAbility(this);
    }

    @Override
    public String getRule() {
        return abilityText;
    }
}

class EmrakultheAeonsTornEffect extends OneShotEffect<EmrakultheAeonsTornEffect> {
    EmrakultheAeonsTornEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "its owner shuffles his or her graveyard into his or her library";
    }

    EmrakultheAeonsTornEffect(final EmrakultheAeonsTornEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        /*Card permanent = (Card)game.getObject(source.getSourceId());*/
        if (player != null /* && permanent != null */) {
            /*permanent.moveToZone(Zone.LIBRARY, source.getId(), game, true);*/
            player.getLibrary().addAll(player.getGraveyard().getCards(game), game);
            player.getGraveyard().clear();
            player.getLibrary().shuffle();
            return true;
        }
        return false;
    }

    @Override
    public EmrakultheAeonsTornEffect copy() {
        return new EmrakultheAeonsTornEffect(this);
    }
}

class EmrakulExtraTurnEffect extends OneShotEffect<EmrakulExtraTurnEffect> {
    EmrakulExtraTurnEffect() {
        super(Constants.Outcome.ExtraTurn);
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
