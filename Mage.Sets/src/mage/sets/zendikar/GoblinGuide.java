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
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GoblinGuide extends CardImpl {

    public GoblinGuide(UUID ownerId) {
        super(ownerId, 126, "Goblin Guide", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "ZEN";

        this.subtype.add("Goblin");
        this.subtype.add("Scout");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Whenever Goblin Guide attacks, defending player reveals the top card of his or her library.
        // If it's a land card, that player puts it into his or her hand.
        this.addAbility(new GoblinGuideTriggeredAbility(new GoblinGuideEffect(), false));
    }

    public GoblinGuide(final GoblinGuide card) {
        super(card);
    }

    @Override
    public GoblinGuide copy() {
        return new GoblinGuide(this);
    }

}

class GoblinGuideTriggeredAbility extends TriggeredAbilityImpl {

    protected String text;

    public GoblinGuideTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public GoblinGuideTriggeredAbility(Effect effect, boolean optional, String text) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.text = text;
    }

    public GoblinGuideTriggeredAbility(final GoblinGuideTriggeredAbility ability) {
        super(ability);
        this.text = ability.text;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId()) ) {
            UUID defenderId = game.getCombat().getDefendingPlayerId(getSourceId(), game);
            if (defenderId != null) {
                for (Effect effect :this.getEffects()) {
                    // set here because attacking creature can be removed until effect resolves
                    effect.setTargetPointer(new FixedTarget(defenderId));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        if (text == null || text.isEmpty()) {
            return "When {this} attacks, " + super.getRule();
        }
        return text;
    }

    @Override
    public GoblinGuideTriggeredAbility copy() {
        return new GoblinGuideTriggeredAbility(this);
    }


}
class GoblinGuideEffect extends OneShotEffect {

    public GoblinGuideEffect() {
        super(Outcome.DrawCard);
        staticText = "defending player reveals the top card of his or her library. If it's a land card, that player puts it into his or her hand";
    }

    public GoblinGuideEffect(final GoblinGuideEffect effect) {
        super(effect);
    }

    @Override
    public GoblinGuideEffect copy() {
        return new GoblinGuideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {        
        Player defender = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && defender != null) {
            Cards cards = new CardsImpl();
            Card card = defender.getLibrary().getFromTop(game);
            if (card != null) {
                cards.add(card);
                defender.revealCards(sourceObject.getName(), cards, game);
                if (card.getCardType().contains(CardType.LAND)) {
                    defender.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
                }
            }
            return true;
        }
        return false;
    }

}