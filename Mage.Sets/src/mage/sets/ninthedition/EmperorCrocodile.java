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
package mage.sets.ninthedition;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author anonymous
 */
public class EmperorCrocodile extends CardImpl {

    public EmperorCrocodile(UUID ownerId) {
        super(ownerId, 241, "Emperor Crocodile", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "9ED";
        this.subtype.add("Crocodile");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When you control no other creatures, sacrifice Emperor Crocodile.
        // 704.1
        this.addAbility(new EmperorCrocodileStateTriggeredAbility());
    }

    public EmperorCrocodile(final EmperorCrocodile card) {
        super(card);
    }

    @Override
    public EmperorCrocodile copy() {
        return new EmperorCrocodile(this);
    }
}

class EmperorCrocodileStateTriggeredAbility extends StateTriggeredAbility {

    public EmperorCrocodileStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    public EmperorCrocodileStateTriggeredAbility(final EmperorCrocodileStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EmperorCrocodileStateTriggeredAbility copy() {
        return new EmperorCrocodileStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for (Permanent perm : game.getBattlefield().getAllActivePermanents(controllerId)) {
            if (!perm.getId().equals(this.getSourceId()) && perm.getCardType().contains(CardType.CREATURE)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getRule() {
        return "When you control no other creatures, sacrifice {this}.";
    }
}
