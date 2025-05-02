package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.Mode;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsPhaseCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GreatTrainHeist extends CardImpl {

    private static final Condition condition = new IsPhaseCondition(TurnPhase.COMBAT, true);

    public GreatTrainHeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Spree
        this.addAbility(new SpreeAbility(this));

        // + {2}{R} -- Untap all creatures you control. If it's your combat phase, there is an additional combat phase after this phase.
        this.getSpellAbility().addEffect(new UntapAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURES));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AdditionalCombatPhaseEffect(), condition, "if it's your combat phase, " +
                "there is an additional combat phase after this phase"
        ));
        this.getSpellAbility().withFirstModeCost(new ManaCostsImpl<>("{2}{R}"));

        // + {2} -- Creatures you control get +1/+0 and gain first strike until end of turn.
        this.getSpellAbility().addMode(new Mode(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn
        ).setText("creatures you control get +1/+0"))
                .addEffect(new GainAbilityControlledEffect(
                        FirstStrikeAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_PERMANENT_CREATURE
                ).setText("and gain first strike until end of turn"))
                .withCost(new GenericManaCost(2)));

        // + {R} -- Choose target opponent. Whenever a creature you control deals combat damage to that player this turn, create a tapped Treasure token.
        this.getSpellAbility().addMode(new Mode(new GreatTrainHeistEffect())
                .addTarget(new TargetOpponent())
                .withCost(new ManaCostsImpl<>("{R}")));
    }

    private GreatTrainHeist(final GreatTrainHeist card) {
        super(card);
    }

    @Override
    public GreatTrainHeist copy() {
        return new GreatTrainHeist(this);
    }
}

class GreatTrainHeistEffect extends OneShotEffect {

    GreatTrainHeistEffect() {
        super(Outcome.Benefit);
        staticText = "choose target opponent. Whenever a creature you control deals combat damage " +
                "to that player this turn, create a tapped Treasure token";
    }

    private GreatTrainHeistEffect(final GreatTrainHeistEffect effect) {
        super(effect);
    }

    @Override
    public GreatTrainHeistEffect copy() {
        return new GreatTrainHeistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addDelayedTriggeredAbility(new GreatTrainHeistTriggeredAbility(
                getTargetPointer().getFirst(game, source)
        ), source);
        return true;
    }
}

class GreatTrainHeistTriggeredAbility extends DelayedTriggeredAbility {

    private final UUID playerId;

    GreatTrainHeistTriggeredAbility(UUID playerId) {
        super(new CreateTokenEffect(new TreasureToken(), 1, true), Duration.EndOfTurn, false, false);
        setTriggerPhrase("Whenever a creature you control deals combat damage to that player this turn, ");
        this.playerId = playerId;
    }

    private GreatTrainHeistTriggeredAbility(final GreatTrainHeistTriggeredAbility ability) {
        super(ability);
        this.playerId = ability.playerId;
    }

    @Override
    public GreatTrainHeistTriggeredAbility copy() {
        return new GreatTrainHeistTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!((DamagedPlayerEvent) event).isCombatDamage() || !event.getTargetId().equals(playerId)) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        return permanent != null && permanent.isCreature(game) && permanent.isControlledBy(getControllerId());
    }
}
