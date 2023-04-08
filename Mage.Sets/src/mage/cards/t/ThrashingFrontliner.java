package mage.cards.t;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThrashingFrontliner extends CardImpl {

    public ThrashingFrontliner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.VIASHINO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Thrashing Frontliner attacks a battle, it gets +1/+1 until end of turn.
        this.addAbility(new ThrashingFrontlinerTriggeredAbility());
    }

    private ThrashingFrontliner(final ThrashingFrontliner card) {
        super(card);
    }

    @Override
    public ThrashingFrontliner copy() {
        return new ThrashingFrontliner(this);
    }
}

class ThrashingFrontlinerTriggeredAbility extends TriggeredAbilityImpl {

    ThrashingFrontlinerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn));
    }

    private ThrashingFrontlinerTriggeredAbility(final ThrashingFrontlinerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ThrashingFrontlinerTriggeredAbility copy() {
        return new ThrashingFrontlinerTriggeredAbility(this);
    }


    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return Optional
                .ofNullable(this.getSourceId())
                .map(game.getCombat()::getDefenderId)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(permanent -> permanent.isBattle(game))
                .orElse(false);
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks a battle, it gets +1/+1 until end of turn.";
    }
}
