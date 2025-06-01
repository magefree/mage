package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.replacement.GainDoubleLifeReplacementEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PhialOfGaladriel extends CardImpl {

    public PhialOfGaladriel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);

        // If you would draw a card while you have no cards in hand, draw two cards instead.
        this.addAbility(new SimpleStaticAbility(new PhialOfGaladrielDrawEffect()));

        // If you would gain life while you have 5 or less life, you gain twice that much life instead.
        this.addAbility(new SimpleStaticAbility(new ConditionalReplacementEffect(
                new GainDoubleLifeReplacementEffect(), FatefulHourCondition.instance
        ).setText("if you would gain life while you have 5 or less life, you gain twice that much life instead")));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private PhialOfGaladriel(final PhialOfGaladriel card) {
        super(card);
    }

    @Override
    public PhialOfGaladriel copy() {
        return new PhialOfGaladriel(this);
    }
}

class PhialOfGaladrielDrawEffect extends ReplacementEffectImpl {

    PhialOfGaladrielDrawEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If you would draw a card while you have no cards in hand, draw two cards instead";
    }

    private PhialOfGaladrielDrawEffect(final PhialOfGaladrielDrawEffect effect) {
        super(effect);
    }

    @Override
    public PhialOfGaladrielDrawEffect copy() {
        return new PhialOfGaladrielDrawEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(2, source, game, event);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        UUID playerId = source.getControllerId();
        if (playerId == null || !event.getPlayerId().equals(playerId)) {
            return false;
        }

        Player player = game.getPlayer(playerId);
        if (player == null) {
            return false;
        }

        return player.getHand().size() == 0;
    }
}
