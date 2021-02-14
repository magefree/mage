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
public final class BragosRepresentative extends CardImpl {

    public BragosRepresentative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // While voting, you get an additional vote.
        this.addAbility(new SimpleStaticAbility(new BragosRepresentativeReplacementEffect()));
    }

    private BragosRepresentative(final BragosRepresentative card) {
        super(card);
    }

    @Override
    public BragosRepresentative copy() {
        return new BragosRepresentative(this);
    }
}

class BragosRepresentativeReplacementEffect extends ReplacementEffectImpl {

    BragosRepresentativeReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "while voting, you get an additional vote";
    }

    private BragosRepresentativeReplacementEffect(final BragosRepresentativeReplacementEffect effect) {
        super(effect);
    }

    @Override
    public BragosRepresentativeReplacementEffect copy() {
        return new BragosRepresentativeReplacementEffect(this);
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
        ((VoteEvent) event).incrementExtraVotes();
        return false;
    }
}
