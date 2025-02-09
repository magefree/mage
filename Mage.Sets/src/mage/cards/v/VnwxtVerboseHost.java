package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VnwxtVerboseHost extends CardImpl {

    public VnwxtVerboseHost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // You have no maximum hand size.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.WhileOnBattlefield,
                MaximumHandSizeControllerEffect.HandSizeModification.SET
        )));

        // Max speed -- If you would draw a card, draw two cards instead.
        this.addAbility(new MaxSpeedAbility(new VnwxtVerboseHostEffect()));
    }

    private VnwxtVerboseHost(final VnwxtVerboseHost card) {
        super(card);
    }

    @Override
    public VnwxtVerboseHost copy() {
        return new VnwxtVerboseHost(this);
    }
}

class VnwxtVerboseHostEffect extends ReplacementEffectImpl {

    VnwxtVerboseHostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "if you would draw a card, draw two cards instead";
    }

    private VnwxtVerboseHostEffect(final VnwxtVerboseHostEffect effect) {
        super(effect);
    }

    @Override
    public VnwxtVerboseHostEffect copy() {
        return new VnwxtVerboseHostEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player you = game.getPlayer(event.getPlayerId());
        if (you != null) {
            you.drawCards(2, source, game, event);
        }
        return true;
    }
}
