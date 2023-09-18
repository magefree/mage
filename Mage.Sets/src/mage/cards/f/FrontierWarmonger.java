package mage.cards.f;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class FrontierWarmonger extends CardImpl {

    public FrontierWarmonger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever one or more creatures attack an opponent or a planeswalker an opponent controls, those creatures gain menace until end of turn.
        this.addAbility(new FrontierWarmongerTriggeredAbility());
    }

    private FrontierWarmonger(final FrontierWarmonger card) {
        super(card);
    }

    @Override
    public FrontierWarmonger copy() {
        return new FrontierWarmonger(this);
    }
}

class FrontierWarmongerTriggeredAbility extends TriggeredAbilityImpl {

    FrontierWarmongerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainAbilityTargetEffect(new MenaceAbility(), Duration.EndOfTurn), false);
    }

    private FrontierWarmongerTriggeredAbility(final FrontierWarmongerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FrontierWarmongerTriggeredAbility copy() {
        return new FrontierWarmongerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Set<UUID> opponents = game.getOpponents(this.getControllerId());
        Predicate<UUID> predicate = uuid -> opponents.contains(game.getCombat().getDefendingPlayerId(uuid, game));
        if (game.getCombat().getAttackers().stream().noneMatch(predicate)) {
            return false;
        }
        List<Permanent> permanents = game
                .getCombat()
                .getAttackers()
                .stream()
                .filter(predicate)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        this.getEffects().setTargetPointer(new FixedTargets(permanents, game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures attack one of your opponents or a planeswalker they control, " +
                "those creatures gain menace until end of turn.";
    }
}
