package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.RollDiceEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PixieGuide extends CardImpl {

    public PixieGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Grant an Advantage — if you would roll one or more dice, instead roll that many dice plus one and ignore the lowest roll.
        this.addAbility(new SimpleStaticAbility(new PixieGuideEffect()).withFlavorWord("Grant an Advantage"));
    }

    private PixieGuide(final PixieGuide card) {
        super(card);
    }

    @Override
    public PixieGuide copy() {
        return new PixieGuide(this);
    }
}

class PixieGuideEffect extends ReplacementEffectImpl {

    PixieGuideEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if you would roll one or more dice, instead roll that many dice plus one and ignore the lowest roll";
    }

    private PixieGuideEffect(final PixieGuideEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        RollDiceEvent rdEvent = (RollDiceEvent) event;
        rdEvent.incAmount(1);
        rdEvent.incIgnoreLowestAmount(1);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ROLL_DICE
                && ((RollDiceEvent) event).getRollDieType() == RollDieType.NUMERICAL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public PixieGuideEffect copy() {
        return new PixieGuideEffect(this);
    }
}
