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
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.EldraziSpawnToken;

/**
 *
 * @author North
 */
public class PawnOfUlamog extends CardImpl<PawnOfUlamog> {

    public PawnOfUlamog(UUID ownerId) {
        super(ownerId, 122, "Pawn of Ulamog", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Vampire");
        this.subtype.add("Shaman");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new PawnOfUlamogTriggeredAbility());
    }

    public PawnOfUlamog(final PawnOfUlamog card) {
        super(card);
    }

    @Override
    public PawnOfUlamog copy() {
        return new PawnOfUlamog(this);
    }
}

class PawnOfUlamogTriggeredAbility extends TriggeredAbilityImpl<PawnOfUlamogTriggeredAbility> {

    public PawnOfUlamogTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new EldraziSpawnToken()), true);
    }

    public PawnOfUlamogTriggeredAbility(PawnOfUlamogTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PawnOfUlamogTriggeredAbility copy() {
        return new PawnOfUlamogTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE) {
            UUID targetId = event.getTargetId();
            Card card = game.getLastKnownInformation(targetId, Zone.BATTLEFIELD);
            if (card != null && card instanceof Permanent && !(card instanceof PermanentToken)) {
                Permanent permanent = (Permanent) card;
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD
                        && permanent.getControllerId().equals(this.controllerId)
                        && (targetId.equals(this.getSourceId())
                            || (permanent.getCardType().contains(CardType.CREATURE)
                                && !targetId.equals(this.getSourceId())
                                && !(permanent instanceof PermanentToken)))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever Pawn of Ulamog or another nontoken creature you control dies, you may put a 0/1 colorless Eldrazi Spawn creature token onto the battlefield. It has \"Sacrifice this creature: Add {1} to your mana pool.\"";
    }
}
