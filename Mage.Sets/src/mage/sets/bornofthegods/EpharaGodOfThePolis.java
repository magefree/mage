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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.continious.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;
import mage.watchers.WatcherImpl;

/**
 *
 * @author LevelX2
 */
public class EpharaGodOfThePolis extends CardImpl<EpharaGodOfThePolis> {

    public EpharaGodOfThePolis(UUID ownerId) {
        super(ownerId, 145, "Ephara, God of the Polis", Rarity.MYTHIC, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{W}{U}");
        this.expansionSetCode = "BNG";
        this.supertype.add("Legendary");
        this.subtype.add("God");

        this.color.setBlue(true);
        this.color.setWhite(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
        // As long as your devotion to white and blue is less than seven, Ephara isn't a creature.
        Effect effect = new LoseCreatureTypeSourceEffect(new DevotionCount(ColoredManaSymbol.W, ColoredManaSymbol.U), 7);
        effect.setText("As long as your devotion to white and blue is less than seven, Ephara isn't a creature");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        // At the beginning of each upkeep, if you had another creature enter the battlefield under your control last turn, draw a card.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DrawCardControllerEffect(1), TargetController.ANY, false),
                HadAnotherCreatureEnterTheBattlefieldCondition.getInstance(),
                "At the beginning of each upkeep, if you had another creature enter the battlefield under your control last turn, draw a card."));
        this.addWatcher(new CreatureEnteredBattlefieldLastTurnWatcher());

    }

    public EpharaGodOfThePolis(final EpharaGodOfThePolis card) {
        super(card);
    }

    @Override
    public EpharaGodOfThePolis copy() {
        return new EpharaGodOfThePolis(this);
    }
}

class HadAnotherCreatureEnterTheBattlefieldCondition implements Condition {

    private static HadAnotherCreatureEnterTheBattlefieldCondition fInstance = new HadAnotherCreatureEnterTheBattlefieldCondition();

    public static HadAnotherCreatureEnterTheBattlefieldCondition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Watcher watcher = game.getState().getWatchers().get("CreatureEnteredBattlefieldLastTurnWatcher", source.getSourceId());
        return watcher != null && watcher.conditionMet();
    }
}

class CreatureEnteredBattlefieldLastTurnWatcher extends WatcherImpl<CreatureEnteredBattlefieldLastTurnWatcher> {

    private boolean anotherCreatureEntered = false;

    public CreatureEnteredBattlefieldLastTurnWatcher() {
        super("CreatureEnteredBattlefieldLastTurnWatcher", WatcherScope.CARD);
    }

    public CreatureEnteredBattlefieldLastTurnWatcher(final CreatureEnteredBattlefieldLastTurnWatcher watcher) {
        super(watcher);
        this.anotherCreatureEntered = watcher.anotherCreatureEntered;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (!anotherCreatureEntered && event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            if (!event.getTargetId().equals(this.getSourceId()) && event.getPlayerId().equals(this.getControllerId())) {
                Permanent permanent = game.getPermanent(event.getTargetId());
                if (permanent != null && permanent.getCardType().contains(CardType.CREATURE)) {
                    anotherCreatureEntered = true;
                }
            }
        }
    }

    @Override
    public void reset() {
        condition = anotherCreatureEntered;
        anotherCreatureEntered = false;
    }

    @Override
    public CreatureEnteredBattlefieldLastTurnWatcher copy() {
        return new CreatureEnteredBattlefieldLastTurnWatcher(this);
    }
}
