package mage.cards.r;

import mage.MageObject;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.WaterbendedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.WaterbendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RuinousWaterbending extends CardImpl {

    public RuinousWaterbending(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        this.subtype.add(SubType.LESSON);

        // As an additional cost to cast this spell, you may waterbend {4}.
        this.addAbility(new WaterbendAbility(4));

        // All creatures get -2/-2 until end of turn. If this spell's additional cost was paid, whenever a creature dies this turn, you gain 1 life.
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, -2, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateDelayedTriggeredAbilityEffect(new RuinousWaterbendingAbility()),
                WaterbendedCondition.instance, "If this spell's additional cost was paid, " +
                "whenever a creature dies this turn, you gain 1 life"
        ));
    }

    private RuinousWaterbending(final RuinousWaterbending card) {
        super(card);
    }

    @Override
    public RuinousWaterbending copy() {
        return new RuinousWaterbending(this);
    }
}

class RuinousWaterbendingAbility extends DelayedTriggeredAbility {

    RuinousWaterbendingAbility() {
        super(new GainLifeEffect(1), Duration.EndOfTurn, false);
        setLeavesTheBattlefieldTrigger(true);
        setTriggerPhrase("Whenever a creature dies this turn, ");
    }

    private RuinousWaterbendingAbility(final RuinousWaterbendingAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.isDiesEvent() && zEvent.getTarget().isCreature(game);
    }

    @Override
    public RuinousWaterbendingAbility copy() {
        return new RuinousWaterbendingAbility(this);
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject sourceObject, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, sourceObject, event, game);
    }
}
