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
package mage.sets.legions;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.BearToken;
import mage.watchers.Watcher;

/**
 *
 * @author Plopman
 */
public class CallerOfTheClaw extends CardImpl {

    public CallerOfTheClaw(UUID ownerId) {
        super(ownerId, 121, "Caller of the Claw", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "LGN";
        this.subtype.add("Elf");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // When Caller of the Claw enters the battlefield, put a 2/2 green Bear creature token onto the battlefield for each nontoken creature put into your graveyard from the battlefield this turn.
        this.getSpellAbility().addWatcher(new CallerOfTheClawWatcher());
        Effect effect = new CreateTokenEffect(new BearToken(), new CallerOfTheClawDynamicValue());
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect));
    }

    public CallerOfTheClaw(final CallerOfTheClaw card) {
        super(card);
    }

    @Override
    public CallerOfTheClaw copy() {
        return new CallerOfTheClaw(this);
    }
}

class CallerOfTheClawWatcher extends Watcher {

    private int creaturesCount = 0;

    public CallerOfTheClawWatcher() {
        super("YourCreaturesDied", WatcherScope.PLAYER);
        condition = true;
    }

    public CallerOfTheClawWatcher(final CallerOfTheClawWatcher watcher) {
        super(watcher);
        this.creaturesCount = watcher.creaturesCount;
    }

    @Override
    public CallerOfTheClawWatcher copy() {
        return new CallerOfTheClawWatcher(this);
    }

    public int getCreaturesCount() {
        return creaturesCount;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            Permanent card = (Permanent)game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (card != null && card.getOwnerId().equals(this.controllerId) && card.getCardType().contains(CardType.CREATURE) && !(card instanceof PermanentToken)) {
                creaturesCount++;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        creaturesCount = 0;
    }
}

class CallerOfTheClawDynamicValue implements DynamicValue {


    @Override
    public CallerOfTheClawDynamicValue copy() {
        return new CallerOfTheClawDynamicValue();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "nontoken creature put into your graveyard from the battlefield this turn";
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CallerOfTheClawWatcher watcher = (CallerOfTheClawWatcher) game.getState().getWatchers().get("YourCreaturesDied", sourceAbility.getControllerId());
        if (watcher != null) {
            return watcher.getCreaturesCount();
        }
        return 0;
    }
}
