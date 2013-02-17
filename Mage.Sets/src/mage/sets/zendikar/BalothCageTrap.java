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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.watchers.WatcherImpl;

/**
 *
 * @author jeffwadsworth
 */
public class BalothCageTrap extends CardImpl<BalothCageTrap> {

    public BalothCageTrap(UUID ownerId) {
        super(ownerId, 156, "Baloth Cage Trap", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{G}{G}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Trap");

        this.color.setGreen(true);

        // If an opponent had an artifact enter the battlefield under his or her control this turn, you may pay {1}{G} rather than pay Baloth Cage Trap's mana cost.
        this.getSpellAbility().addAlternativeCost(new BalothCageTrapAlternativeCost());
        this.addWatcher(new BalothCageTrapWatcher());

        // Put a 4/4 green Beast creature token onto the battlefield.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BeastGreenToken()));
    }

    public BalothCageTrap(final BalothCageTrap card) {
        super(card);
    }

    @Override
    public BalothCageTrap copy() {
        return new BalothCageTrap(this);
    }
}

class BalothCageTrapWatcher extends WatcherImpl<BalothCageTrapWatcher> {

    public BalothCageTrapWatcher() {
        super("BalothCageTrapWatcher", Constants.WatcherScope.GAME);
    }

    public BalothCageTrapWatcher(final BalothCageTrapWatcher watcher) {
        super(watcher);
    }

    @Override
    public BalothCageTrapWatcher copy() {
        return new BalothCageTrapWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition == true) { // no need to check - condition has already occured
            return;
        }
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent perm = game.getPermanent(event.getTargetId());
            if (perm.getCardType().contains(CardType.ARTIFACT) && !perm.getControllerId().equals(controllerId)) {
                condition = true;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        condition = false;
    }
}

class BalothCageTrapAlternativeCost extends AlternativeCostImpl<BalothCageTrapAlternativeCost> {

    public BalothCageTrapAlternativeCost() {
        super("you may pay {1}{G} rather than pay Baloth Cage Trap's mana cost");
        this.add(new ManaCostsImpl("{1}{G}"));
    }

    public BalothCageTrapAlternativeCost(final BalothCageTrapAlternativeCost cost) {
        super(cost);
    }

    @Override
    public BalothCageTrapAlternativeCost copy() {
        return new BalothCageTrapAlternativeCost(this);
    }

    @Override
    public boolean isAvailable(Game game, Ability source) {
        BalothCageTrapWatcher watcher = (BalothCageTrapWatcher) game.getState().getWatchers().get("BalothCageTrapWatcher");
        if (watcher != null && watcher.conditionMet()) {
            return true;
        }
        return false;
    }

    @Override
    public String getText() {
        return "If an opponent had an artifact enter the battlefield under his or her control this turn, you may pay {1}{G} rather than pay Baloth Cage Trap's mana cost";
    }
}

class BeastGreenToken extends Token {

    public BeastGreenToken() {
        super("Beast", "4/4 green Beast creature token");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.GREEN;
        subtype.add("Beast");
        power = new MageInt(4);
        toughness = new MageInt(4);
    }
}