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
package mage.cards.b;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class BoseijuWhoSheltersAll extends CardImpl {

    public BoseijuWhoSheltersAll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.supertype.add("Legendary");

        // Boseiju, Who Shelters All enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {tap}, Pay 2 life: Add {C} to your mana pool. If that mana is spent on an instant or sorcery spell, that spell can't be countered by spells or abilities.
        Mana mana = Mana.ColorlessMana(1);
        mana.setFlag(true); // used to indicate this mana ability
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, mana, new TapSourceCost());
        ability.addCost(new PayLifeCost(2));
        ability.getEffects().get(0).setText("Add {C} to your mana pool. If that mana is spent on an instant or sorcery spell, that spell can't be countered by spells or abilities");
        this.addAbility(ability);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoseijuWhoSheltersAllCantCounterEffect()), new BoseijuWhoSheltersAllWatcher());
    }

    public BoseijuWhoSheltersAll(final BoseijuWhoSheltersAll card) {
        super(card);
    }

    @Override
    public BoseijuWhoSheltersAll copy() {
        return new BoseijuWhoSheltersAll(this);
    }
}

class BoseijuWhoSheltersAllWatcher extends Watcher {

    public List<UUID> spells = new ArrayList<>();

    public BoseijuWhoSheltersAllWatcher() {
        super("ManaPaidFromBoseijuWhoSheltersAllWatcher", WatcherScope.GAME);
    }

    public BoseijuWhoSheltersAllWatcher(final BoseijuWhoSheltersAllWatcher watcher) {
        super(watcher);
    }

    @Override
    public BoseijuWhoSheltersAllWatcher copy() {
        return new BoseijuWhoSheltersAllWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.MANA_PAYED) {
            MageObject object = game.getObject(event.getSourceId());
            // TODO: Replace identification by name by better method that also works if ability is copied from other land with other name
            if (object != null && object.getName().equals("Boseiju, Who Shelters All") && event.getFlag()) {
                spells.add(event.getTargetId());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        spells.clear();
    }
}

class BoseijuWhoSheltersAllCantCounterEffect extends ContinuousRuleModifyingEffectImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard();

    public BoseijuWhoSheltersAllCantCounterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = null;
    }

    public BoseijuWhoSheltersAllCantCounterEffect(final BoseijuWhoSheltersAllCantCounterEffect effect) {
        super(effect);
    }

    @Override
    public BoseijuWhoSheltersAllCantCounterEffect copy() {
        return new BoseijuWhoSheltersAllCantCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null) {
            return "This spell can't be countered by spells or abilities (" + sourceObject.getName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        BoseijuWhoSheltersAllWatcher watcher = (BoseijuWhoSheltersAllWatcher) game.getState().getWatchers().get("ManaPaidFromBoseijuWhoSheltersAllWatcher");
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && watcher.spells.contains(spell.getId())) {
            if (filter.match(spell.getCard(), game)) {
                return true;
            }
        }
        return false;
    }
}
