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
package mage.cards.w;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.IntComparePredicate;
import mage.filter.predicate.other.OwnerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 *
 * @author fenhl
 */
public class WildPair extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature");

    static {
        filter.add(new OwnerPredicate(TargetController.YOU));
    }

    public WildPair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}{G}");

        // Whenever a creature enters the battlefield, if you cast it from your hand, you may search your library for a creature card with the same total power and toughness and put it onto the battlefield. If you do, shuffle your library.
        this.addAbility(new ConditionalTriggeredAbility(
                new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new WildPairEffect(), filter, true, SetTargetPointer.PERMANENT, ""),
                new CastFromHandTargetCondition(),
                "Whenever a creature enters the battlefield, if you cast it from your hand, you may search your library for a creature card with the same total power and toughness and put it onto the battlefield. If you do, shuffle your library."
        ), new CastFromHandWatcher());
    }

    public WildPair(final WildPair card) {
        super(card);
    }

    @Override
    public WildPair copy() {
        return new WildPair(this);
    }
}

class WildPairEffect extends OneShotEffect {

    public WildPairEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "search your library for a creature card with the same total power and toughness and put it onto the battlefield";
    }

    public WildPairEffect(final WildPairEffect effect) {
        super(effect);
    }

    @Override
    public WildPairEffect copy() {
        return new WildPairEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                int totalPT = permanent.getPower().getValue() + permanent.getToughness().getValue();
                FilterCreatureCard filter = new FilterCreatureCard("creature card with total power and toughness " + totalPT);
                filter.add(new TotalPowerAndToughnessPredicate(ComparisonType.EQUAL_TO, totalPT));
                TargetCardInLibrary target = new TargetCardInLibrary(1, filter);
                if (controller.searchLibrary(target, game)) {
                    if (!target.getTargets().isEmpty()) {
                        controller.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
                    }
                }
                controller.shuffleLibrary(source, game);
                return true;
            }
        }
        return false;
    }
}

/**
 *
 * @author fenhl
 */
class TotalPowerAndToughnessPredicate extends IntComparePredicate<MageObject> {

    public TotalPowerAndToughnessPredicate(ComparisonType type, int value) {
        super(type, value);
    }

    @Override
    protected int getInputValue(MageObject input) {
        return input.getPower().getValue() + input.getToughness().getValue();
    }

    @Override
    public String toString() {
        return "TotalPowerAndToughness" + super.toString();
    }
}

class CastFromHandTargetCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = source.getEffects().get(0).getTargetPointer().getFirst(game, source);
        Permanent permanent = game.getPermanentEntering(targetId);
        int zccDiff = 0;
        if (permanent == null) {
            permanent = game.getPermanentOrLKIBattlefield(targetId); // can be alredy again removed from battlefield so also check LKI
            zccDiff = -1;
        }
        if (permanent != null) {
            // check that the spell is still in the LKI
            Spell spell = game.getStack().getSpell(targetId);
            if (spell == null || spell.getZoneChangeCounter(game) != permanent.getZoneChangeCounter(game) + zccDiff) {
                if (game.getLastKnownInformation(targetId, Zone.STACK, permanent.getZoneChangeCounter(game) + zccDiff) == null) {
                    return false;
                }
            }
            CastFromHandWatcher watcher = (CastFromHandWatcher) game.getState().getWatchers().get(CastFromHandWatcher.class.getName());
            if (watcher != null && watcher.spellWasCastFromHand(targetId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "you cast it from your hand";
    }

}
