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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.OfferingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;


/**
 * @author LevelX2
 */
public class PatronOfTheKitsune extends CardImpl<PatronOfTheKitsune> {

    public PatronOfTheKitsune(UUID ownerId) {
        super(ownerId, 19, "Patron of the Kitsune", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.expansionSetCode = "BOK";
        this.supertype.add("Legendary");
        this.subtype.add("Spirit");
        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Fox offering (You may cast this card any time you could cast an instant by sacrificing a Fox and paying the difference in mana costs between this and the sacrificed Fox. Mana cost includes color.)
        this.addAbility(new OfferingAbility("Fox"));

        // Whenever a creature attacks, you may gain 1 life.
        this.addAbility(new PatronOfTheKitsuneTriggeredAbility());
    }

    public PatronOfTheKitsune(final PatronOfTheKitsune card) {
        super(card);
    }

    @Override
    public PatronOfTheKitsune copy() {
        return new PatronOfTheKitsune(this);
    }
}

class PatronOfTheKitsuneTriggeredAbility extends TriggeredAbilityImpl<PatronOfTheKitsuneTriggeredAbility> {

    public PatronOfTheKitsuneTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new GainLifeEffect(1), true);
    }

    public PatronOfTheKitsuneTriggeredAbility(PatronOfTheKitsuneTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            return true;
        }
        return false;
    }

    @Override
    public PatronOfTheKitsuneTriggeredAbility copy() {
        return new PatronOfTheKitsuneTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature attacks, " + super.getRule();
    }

}
