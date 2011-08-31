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
package mage.sets.newphyrexia;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.BeastToken;
import mage.watchers.WatcherImpl;

/**
 *
 * @author North
 */
public class FreshMeat extends CardImpl<FreshMeat> {

    public FreshMeat(UUID ownerId) {
        super(ownerId, 109, "Fresh Meat", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{3}{G}");
        this.expansionSetCode = "NPH";

        this.color.setGreen(true);

        this.addWatcher(new FreshMeatWatcher());
        this.getSpellAbility().addEffect(new FreshMeatEffect());
    }

    public FreshMeat(final FreshMeat card) {
        super(card);
    }

    @Override
    public FreshMeat copy() {
        return new FreshMeat(this);
    }
}

class FreshMeatWatcher extends WatcherImpl<FreshMeatWatcher> {

    private int creaturesCount = 0;

    public FreshMeatWatcher() {
        super("CreaturesDiedFreshMeat");
        condition = true;
    }

    public FreshMeatWatcher(final FreshMeatWatcher watcher) {
        super(watcher);
        this.creaturesCount = watcher.creaturesCount;
    }

    @Override
    public FreshMeatWatcher copy() {
        return new FreshMeatWatcher(this);
    }

    public int getCreaturesCount() {
        return creaturesCount;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD
                && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
            Card card = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (card != null && card.getOwnerId().equals(this.controllerId) && card.getCardType().contains(CardType.CREATURE)) {
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

class FreshMeatEffect extends OneShotEffect<FreshMeatEffect> {

    public FreshMeatEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put a 3/3 green Beast creature token onto the battlefield for each creature put into your graveyard from the battlefield this turn";
    }

    public FreshMeatEffect(final FreshMeatEffect effect) {
        super(effect);
    }

    @Override
    public FreshMeatEffect copy() {
        return new FreshMeatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FreshMeatWatcher watcher = (FreshMeatWatcher) game.getState().getWatchers().get(source.getControllerId(), "CreaturesDiedFreshMeat");
        int count = watcher.getCreaturesCount();
        BeastToken token = new BeastToken();
        for (int i = 0; i < count; i++) {
            token.putOntoBattlefield(game, source.getSourceId(), source.getControllerId());
        }
        return true;
    }
}
