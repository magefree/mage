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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class LureboundScarecrow extends CardImpl {

    public LureboundScarecrow(UUID ownerId) {
        super(ownerId, 256, "Lurebound Scarecrow", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Scarecrow");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // As Lurebound Scarecrow enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Detriment)));

        // When you control no permanents of the chosen color, sacrifice Lurebound Scarecrow.
        this.addAbility(new LureboundScarecrowTriggeredAbility());
    }

    public LureboundScarecrow(final LureboundScarecrow card) {
        super(card);
    }

    @Override
    public LureboundScarecrow copy() {
        return new LureboundScarecrow(this);
    }
}

class LureboundScarecrowTriggeredAbility extends StateTriggeredAbility {

    private static final String staticText = "When you control no permanents of the chosen color, sacrifice {this}.";

    public LureboundScarecrowTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    public LureboundScarecrowTriggeredAbility(LureboundScarecrowTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        if (permanent != null) {
            ObjectColor color = (ObjectColor) game.getState().getValue(getSourceId() + "_color");
            if (color != null) {
                for (Permanent perm : game.getBattlefield().getAllActivePermanents(controllerId)) {
                    if (perm.getColor(game).contains(color)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public LureboundScarecrowTriggeredAbility copy() {
        return new LureboundScarecrowTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return staticText;
    }
}
