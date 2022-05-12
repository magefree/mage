package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class AngelOfSuffering extends CardImpl {

    public AngelOfSuffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If damage would be dealt to you, prevent that damage and mill twice that many cards.
        this.addAbility(new SimpleStaticAbility(new AngelOfSufferingEffect()));
    }

    private AngelOfSuffering(final AngelOfSuffering card) {
        super(card);
    }

    @Override
    public AngelOfSuffering copy() {
        return new AngelOfSuffering(this);
    }
}

class AngelOfSufferingEffect extends ReplacementEffectImpl {

    public AngelOfSufferingEffect() {
        super(Duration.WhileOnBattlefield, Outcome.PreventDamage);
        this.staticText = "If damage would be dealt to you, prevent that damage and mill twice that many cards";
    }

    private AngelOfSufferingEffect(final AngelOfSufferingEffect effect) {
        super(effect);
    }

    @Override
    public AngelOfSufferingEffect copy() {
        return new AngelOfSufferingEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int cardsToMill = event.getAmount() * 2;
        game.preventDamage(event, source, game, Integer.MAX_VALUE);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.millCards(cardsToMill, source, game);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getControllerId());
    }
}
