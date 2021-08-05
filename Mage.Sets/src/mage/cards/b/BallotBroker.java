package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.VoteEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BallotBroker extends CardImpl {

    public BallotBroker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // While voting, you may vote an additional time.
        this.addAbility(new SimpleStaticAbility(new BallotBrokerReplacementEffect()));
    }

    private BallotBroker(final BallotBroker card) {
        super(card);
    }

    @Override
    public BallotBroker copy() {
        return new BallotBroker(this);
    }
}

class BallotBrokerReplacementEffect extends ReplacementEffectImpl {

    BallotBrokerReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "while voting, you may vote an additional time";
    }

    private BallotBrokerReplacementEffect(final BallotBrokerReplacementEffect effect) {
        super(effect);
    }

    @Override
    public BallotBrokerReplacementEffect copy() {
        return new BallotBrokerReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.VOTE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getTargetId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((VoteEvent) event).incrementOptionalExtraVotes();
        return false;
    }
}
