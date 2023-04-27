
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
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author bunchOfDevs
 */
public final class DefiantVanguard extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("Rebel permanent card with mana value 4 or less");

    static {
        filter.add(SubType.REBEL.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 5));
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
                new ManaCostsImpl<>("{5}"));
        ability2.addCost(new TapSourceCost());
        this.addAbility(ability2);
    }

    private DefiantVanguard(final DefiantVanguard card) {
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
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED
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
        return "When {this} blocks, at end of combat, destroy it and all creatures it blocked this turn.";
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
                blockedCreature.destroy(source, game, false);
                result = true;
            }
        }
        if (defiantVanguard != null) {
            if (game.getState().getValue(defiantVanguard.toString()).equals(defiantVanguard.getZoneChangeCounter(game))) { // true if it did not change zones
                defiantVanguard.destroy(source, game, false);
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
