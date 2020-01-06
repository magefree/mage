package mage.cards.p;

import mage.MageInt;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EscapesWithAbility;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PharikasSpawn extends CardImpl {

    public PharikasSpawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.GORGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Escape—{5}{B}, Exile three other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{5}{B}", 3));

        // Pharika's Spawn escapes with two +1/+1 counters on it. When it enters the battlefield this way, each opponent sacrifices a non-Gorgon creature.
        this.addAbility(new EscapesWithAbility(2, new PharikasSpawnDelayedTriggeredAbility()));
    }

    private PharikasSpawn(final PharikasSpawn card) {
        super(card);
    }

    @Override
    public PharikasSpawn copy() {
        return new PharikasSpawn(this);
    }
}

class PharikasSpawnDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a non-Gorgon creature");

    static {
        filter.add(Predicates.not(SubType.GORGON.getPredicate()));
    }

    PharikasSpawnDelayedTriggeredAbility() {
        super(new SacrificeOpponentsEffect(filter), Duration.EndOfTurn, true);
    }

    private PharikasSpawnDelayedTriggeredAbility(final PharikasSpawnDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PharikasSpawnDelayedTriggeredAbility copy() {
        return new PharikasSpawnDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(getSourceId());
    }

    @Override
    public String getRule() {
        return "When it enters the battlefield this way, each opponent sacrifices a non-Gorgon creature.";
    }
}
