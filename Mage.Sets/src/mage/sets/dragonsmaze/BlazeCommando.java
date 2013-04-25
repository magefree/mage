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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.MerfolkToken;
import mage.game.permanent.token.Token;
import mage.game.stack.StackObject;

/**
 *
 * @author LevelX2
 */


public class BlazeCommando extends CardImpl<BlazeCommando> {

    public BlazeCommando (UUID ownerId) {
        super(ownerId, 56, "Blaze Commando", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Minotaur");
        this.subtype.add("Soldier");
        this.color.setRed(true);
        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Whenever an instant or sorcery spell you control deals damage, put two 1/1 red and white Soldier creature tokens with haste onto the battlefield.
        this.addAbility(new BlazeCommandoTriggeredAbility());

    }

    public BlazeCommando (final BlazeCommando card) {
        super(card);
    }

    @Override
    public BlazeCommando copy() {
        return new BlazeCommando(this);
    }

}
class BlazeCommandoTriggeredAbility extends TriggeredAbilityImpl<BlazeCommandoTriggeredAbility> {

    private List<UUID> handledStackObjects = new ArrayList<UUID>();

    public BlazeCommandoTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new BlazeCommandoSoldierToken(), 2), false);
    }

    public BlazeCommandoTriggeredAbility(final BlazeCommandoTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BlazeCommandoTriggeredAbility copy() {
        return new BlazeCommandoTriggeredAbility(this);
    }

    @Override
    public void reset(Game game) {
        handledStackObjects.clear();
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_CREATURE || event.getType() == EventType.DAMAGED_PLANESWALKER || event.getType() == EventType.DAMAGED_PLAYER) {
            StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
            if (stackObject != null && stackObject.getControllerId().equals(getControllerId())) {
                MageObject object = game.getObject(stackObject.getSourceId());
                if (object.getCardType().contains(CardType.INSTANT) || object.getCardType().contains(CardType.SORCERY)) {
                    if (!handledStackObjects.contains(stackObject.getId())) {
                        handledStackObjects.add(stackObject.getId());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return new StringBuilder("Whenever an instant or sorcery spell you control deals damage, ").append(super.getRule()).toString();
    }
}

class BlazeCommandoSoldierToken extends Token {

    public BlazeCommandoSoldierToken() {
        super("Soldier", "1/1 red and white Soldier creature tokens with haste");
        cardType.add(Constants.CardType.CREATURE);
        color.setRed(true);
        color.setWhite(true);
        subtype.add("Soldier");
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(HasteAbility.getInstance());
    }
}