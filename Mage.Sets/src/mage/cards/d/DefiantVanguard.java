
package mage.cards.d;

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
import mage.constants.*;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author bunchOfDevs
 */
public final class DefiantVanguard extends CardImpl {

    protected static final String EFFECT_KEY = "DefiantVanguardEffect_";

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
        SimpleActivatedAbility ability2 = new SimpleActivatedAbility(
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

    private DefiantVanguardTriggeredAbility(final DefiantVanguardTriggeredAbility ability) {
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
            game.getState().setValue(DefiantVanguard.EFFECT_KEY + blocked.getId(), blocked.getZoneChangeCounter(game)); // in case the attacker changes zone
            game.getState().setValue(DefiantVanguard.EFFECT_KEY + blocker.getId(), blocker.getZoneChangeCounter(game)); // in case the blocker changes zone
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

    DefiantVanguardEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy it and all creatures it blocked this turn";
    }

    private DefiantVanguardEffect(final DefiantVanguardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        Permanent blockedCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent defiantVanguard = game.getPermanent(source.getSourceId());
        if (blockedCreature != null) {
            if (game.getState().getValue(DefiantVanguard.EFFECT_KEY + blockedCreature.getId()).equals(blockedCreature.getZoneChangeCounter(game))) { // true if it did not change zones
                blockedCreature.destroy(source, game, false);
                result = true;
            }
        }
        if (defiantVanguard != null) {
            if (game.getState().getValue(DefiantVanguard.EFFECT_KEY + defiantVanguard.getId()).equals(defiantVanguard.getZoneChangeCounter(game))) { // true if it did not change zones
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
