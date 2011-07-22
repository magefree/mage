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

import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.PutIntoGraveFromAnywhereTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public class KozilekButcherOfTruth extends CardImpl<KozilekButcherOfTruth> {

    public KozilekButcherOfTruth (UUID ownerId) {
        super(ownerId, 6, "Kozilek, Butcher of Truth", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{10}");
        this.expansionSetCode = "ROE";
        this.supertype.add("Legendary");
        this.subtype.add("Eldrazi");
        this.power = new MageInt(12);
        this.toughness = new MageInt(12);
        this.addAbility(new KozilekButcherOfTruthOnCastAbility());
        this.addAbility(new AnnihilatorAbility(4));
        this.addAbility(new PutIntoGraveFromAnywhereTriggeredAbility(new KozilekButcherOfTruthEffect(), false));
    }

    public KozilekButcherOfTruth (final KozilekButcherOfTruth card) {
        super(card);
    }

    @Override
    public KozilekButcherOfTruth copy() {
        return new KozilekButcherOfTruth(this);
    }

}

class KozilekButcherOfTruthOnCastAbility extends TriggeredAbilityImpl<KozilekButcherOfTruthOnCastAbility> {

    private static final String abilityText = "When you cast Kozilek, Butcher of Truth, draw four cards";

    KozilekButcherOfTruthOnCastAbility() {
        super(Zone.STACK, new DrawCardControllerEffect(4));
    }

    KozilekButcherOfTruthOnCastAbility(final KozilekButcherOfTruthOnCastAbility ability) {
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
    public KozilekButcherOfTruthOnCastAbility copy() {
        return new KozilekButcherOfTruthOnCastAbility(this);
    }

    @Override
    public String getRule() {
        return abilityText;
    }
}

class KozilekButcherOfTruthEffect extends OneShotEffect<KozilekButcherOfTruthEffect> {
    KozilekButcherOfTruthEffect() {
        super(Outcome.Benefit);
        staticText = "its owner shuffles his or her graveyard into his or her library";
    }

    KozilekButcherOfTruthEffect(final KozilekButcherOfTruthEffect effect) {
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
    public KozilekButcherOfTruthEffect copy() {
        return new KozilekButcherOfTruthEffect(this);
    }

}
