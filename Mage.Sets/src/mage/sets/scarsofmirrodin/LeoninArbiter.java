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
package mage.sets.scarsofmirrodin;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author maurer.it_at_gmail.com, dustinconrad
 */
public class LeoninArbiter extends CardImpl {

    private static final String keyString = "_ignoreEffectForTurn";

    public LeoninArbiter(UUID ownerId) {
        super(ownerId, 14, "Leonin Arbiter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Cat");
        this.subtype.add("Cleric");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Players can't search libraries.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LeoninArbiterCantSearchEffect(keyString)));

        //  Any player may pay {2} for that player to ignore this effect until end of turn.
        this.addAbility(new LeoninArbiterSpecialAction(keyString));
    }

    public LeoninArbiter(final LeoninArbiter card) {
        super(card);
    }

    @Override
    public LeoninArbiter copy() {
        return new LeoninArbiter(this);
    }
}

class LeoninArbiterSpecialAction extends SpecialAction {

    public LeoninArbiterSpecialAction(final String keyString) {
        super(Zone.BATTLEFIELD);
        this.addCost(new ManaCostsImpl("{2}"));
        this.addEffect(new LeoninArbiterIgnoreEffect(keyString));
        this.setMayActivate(TargetController.ANY);
    }

    public LeoninArbiterSpecialAction(final LeoninArbiterSpecialAction ability) {
        super(ability);
    }

    @Override
    public LeoninArbiterSpecialAction copy() {
        return new LeoninArbiterSpecialAction(this);
    }
}

class LeoninArbiterIgnoreEffect extends OneShotEffect {

    private final String keyString;

    public LeoninArbiterIgnoreEffect(final String keyString) {
        super(Outcome.Benefit);
        this.keyString = keyString;
        this.staticText = "Any player may pay {2} for that player to ignore this effect until end of turn";
    }

    public LeoninArbiterIgnoreEffect(final LeoninArbiterIgnoreEffect effect) {
        super(effect);
        this.keyString = effect.keyString;
    }

    @Override
    public LeoninArbiterIgnoreEffect copy() {
        return new LeoninArbiterIgnoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        String key = permanent.getId() + keyString;

        // Using a Map.Entry since there is no pair class
        long zoneChangeCount = permanent.getZoneChangeCounter();
        long turnNum = game.getTurnNum();
        Long activationState =  zoneChangeCount << 32 | turnNum & 0xFFFFFFFFL;

        Map.Entry<Long, Set<UUID>> turnIgnoringPlayersPair = (Map.Entry<Long, Set<UUID>>) game.getState().getValue(key);
        if (turnIgnoringPlayersPair == null || !activationState.equals(turnIgnoringPlayersPair.getKey())) {
            turnIgnoringPlayersPair = new AbstractMap.SimpleImmutableEntry<Long, Set<UUID>>(activationState, new HashSet<UUID>());
            game.getState().setValue(key, turnIgnoringPlayersPair);
        }

        turnIgnoringPlayersPair.getValue().add(game.getPriorityPlayerId());
        return true;
    }
}

class LeoninArbiterCantSearchEffect extends ContinuousRuleModifyingEffectImpl {

    private final String keyString;

    public LeoninArbiterCantSearchEffect(final String keyString) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "Players can't search libraries.";
        this.keyString = keyString;
    }

    public LeoninArbiterCantSearchEffect(LeoninArbiterCantSearchEffect effect) {
        super(effect);
        this.keyString = effect.keyString;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        boolean applies = false;
        if (EventType.SEARCH_LIBRARY.equals(event.getType())) {
            applies = true;
            Permanent permanent = game.getPermanent(source.getSourceId());
            String key = permanent.getId() + keyString;
            Map.Entry<Long, Set<UUID>> turnIgnoringPlayersPair = (Map.Entry<Long, Set<UUID>>) game.getState().getValue(key);
            if (turnIgnoringPlayersPair != null) {
                long zoneChangeCount = permanent.getZoneChangeCounter();
                long turnNum = game.getTurnNum();
                Long activationState =  zoneChangeCount << 32 | turnNum & 0xFFFFFFFFL;
                if (activationState.equals(turnIgnoringPlayersPair.getKey())) {
                    applies = !turnIgnoringPlayersPair.getValue().contains(event.getPlayerId());
                }
            }
        }
        return applies;
    }

    @Override
    public LeoninArbiterCantSearchEffect copy() {
        return new LeoninArbiterCantSearchEffect(this);
    }
}
