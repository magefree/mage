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
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author bunchOfDevs
 */
public class DefiantVanguard extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("Rebel permanent card with converted mana cost 4 or less");

    static {
        filter.add(new SubtypePredicate(SubType.REBEL));
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 5));
    }

    public DefiantVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Defiant Vanguard blocks, at end of combat, destroy it and all creatures it blocked this turn.
        DelayedTriggeredAbility ability = new AtTheEndOfCombatDelayedTriggeredAbility(new DefiantVanguardEffect());
        Effect effect = new CreateDelayedTriggeredAbilityEffect(ability);
        this.addAbility(new DefiantVanguardTriggeredAbility(effect));

        // {5}, {tap}: Search your library for a Rebel permanent card with converted mana cost 4 or less and put it onto the battlefield. Then shuffle your library.
        SimpleActivatedAbility ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), false),
                new ManaCostsImpl("{5}"));
        ability2.addCost(new TapSourceCost());
        this.addAbility(ability2);
    }

    public DefiantVanguard(final DefiantVanguard card) {
        super(card);
    }

    @Override
    public DefiantVanguard copy() {
        return new DefiantVanguard(this);
    }
}

class DefiantVanguardTriggeredAbility extends TriggeredAbilityImpl {

    DefiantVanguardTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    DefiantVanguardTriggeredAbility(final DefiantVanguardTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DefiantVanguardTriggeredAbility copy() {
        return new DefiantVanguardTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.BLOCKER_DECLARED
                && event.getSourceId().equals(getSourceId()); // Defiant Vanguard is the blocker
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent blocker = game.getPermanent(event.getSourceId());
        Permanent blocked = game.getPermanent(event.getTargetId());
        if (blocker != null
                && blocked != null) {
            game.getState().setValue(blocked.toString(), blocked.getZoneChangeCounter(game)); // in case the attacker changes zone
            game.getState().setValue(blocker.toString(), blocker.getZoneChangeCounter(game)); // in case the blocker changes zone
            getAllEffects().setTargetPointer(new FixedTarget(blocked.getId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} blocks, at end of combat, destroy it and all creatures it blocked this turn";
    }
}

class DefiantVanguardEffect extends OneShotEffect {

    public DefiantVanguardEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy it and all creatures it blocked this turn";
    }

    public DefiantVanguardEffect(final DefiantVanguardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        Permanent blockedCreature = game.getPermanent(targetPointer.getFirst(game, source));
        Permanent defiantVanguard = game.getPermanent(source.getSourceId());
        if (blockedCreature != null) {
            if (game.getState().getValue(blockedCreature.toString()).equals(blockedCreature.getZoneChangeCounter(game))) { // true if it did not change zones
                blockedCreature.destroy(source.getSourceId(), game, false);
                result = true;
            }
        }
        if (defiantVanguard != null) {
            if (game.getState().getValue(defiantVanguard.toString()).equals(defiantVanguard.getZoneChangeCounter(game))) { // true if it did not change zones
                defiantVanguard.destroy(source.getSourceId(), game, false);
                result = true;
            }
        }
        return result;
    }

    @Override
    public DefiantVanguardEffect copy() {
        return new DefiantVanguardEffect(this);
    }
}
