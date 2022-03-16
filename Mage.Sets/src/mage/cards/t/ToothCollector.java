package mage.cards.t;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author fireshoes
 */
public final class ToothCollector extends CardImpl {

    public ToothCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Tooth Collector enters the battlefield, target creature an opponent controls gets -1/-1 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-1, -1, Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);

        // {<i>Delirium</i> &mdash; At the beginning of each opponent's upkeep, if there are four or more card types among cards in your graveyard,
        // target creature that player controls gets -1/-1 until end of turn.
        ability = new ConditionalInterveningIfTriggeredAbility(
                new ToothCollectorAbility(),
                DeliriumCondition.instance,
                "<i>Delirium</i> &mdash; At the beginning of each opponent's upkeep, if there are four or more card types among cards in your graveyard, "
                        + "target creature that player controls gets -1/-1 until end of turn.");
        ability.addHint(CardTypesInGraveyardHint.YOU);
        this.addAbility(ability);
    }

    private ToothCollector(final ToothCollector card) {
        super(card);
    }

    @Override
    public ToothCollector copy() {
        return new ToothCollector(this);
    }
}

class ToothCollectorAbility extends TriggeredAbilityImpl {

    public ToothCollectorAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(-1, -1, Duration.EndOfTurn));
    }

    public ToothCollectorAbility(final ToothCollectorAbility ability) {
        super(ability);
    }

    @Override
    public ToothCollectorAbility copy() {
        return new ToothCollectorAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Player opponent = game.getPlayer(event.getPlayerId());
            if (opponent != null) {
                FilterCreaturePermanent FILTER = new FilterCreaturePermanent("creature " + opponent.getLogName() + " controls");
                FILTER.add(new ControllerIdPredicate(opponent.getId()));
                this.getTargets().clear();
                this.addTarget(new TargetCreaturePermanent(FILTER));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "<i>Delirium</i> &mdash; At the beginning of each opponent's upkeep, if there are four or more card types among cards in your graveyard, "
                + "target creature that player controls gets -1/-1 until end of turn.";
    }
}
