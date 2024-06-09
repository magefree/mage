package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.VoteEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheValeyard extends CardImpl {

    public TheValeyard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // If an opponent would face a villainous choice, they face that choice an additional time.
        this.addAbility(new SimpleStaticAbility(new TheValeyardChoiceEffect()));

        // While voting, you may vote an additional time.
        this.addAbility(new SimpleStaticAbility(new TheValeyardVoteEffect()));
    }

    private TheValeyard(final TheValeyard card) {
        super(card);
    }

    @Override
    public TheValeyard copy() {
        return new TheValeyard(this);
    }
}

class TheValeyardChoiceEffect extends ReplacementEffectImpl {

    TheValeyardChoiceEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if an opponent would face a villainous choice, they face that choice an additional time";
    }

    private TheValeyardChoiceEffect(final TheValeyardChoiceEffect effect) {
        super(effect);
    }

    @Override
    public TheValeyardChoiceEffect copy() {
        return new TheValeyardChoiceEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.FACE_VILLAINOUS_CHOICE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getOpponents(event.getTargetId()).contains(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}

class TheValeyardVoteEffect extends ReplacementEffectImpl {

    TheValeyardVoteEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "while voting, you may vote an additional time";
    }

    private TheValeyardVoteEffect(final TheValeyardVoteEffect effect) {
        super(effect);
    }

    @Override
    public TheValeyardVoteEffect copy() {
        return new TheValeyardVoteEffect(this);
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
