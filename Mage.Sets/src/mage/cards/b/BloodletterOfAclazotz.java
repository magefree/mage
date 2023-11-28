package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BloodletterOfAclazotz extends CardImpl {

    public BloodletterOfAclazotz(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If an opponent would lose life during your turn, they lose twice that much life instead.
        this.addAbility(new SimpleStaticAbility(new BloodletterOfAclazotzEffect()));
    }

    private BloodletterOfAclazotz(final BloodletterOfAclazotz card) {
        super(card);
    }

    @Override
    public BloodletterOfAclazotz copy() {
        return new BloodletterOfAclazotz(this);
    }
}

class BloodletterOfAclazotzEffect extends ReplacementEffectImpl {

    public BloodletterOfAclazotzEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If an opponent would lose life during your turn, they lose twice that much life instead";
    }

    private BloodletterOfAclazotzEffect(final BloodletterOfAclazotzEffect effect) {
        super(effect);
    }

    @Override
    public BloodletterOfAclazotzEffect copy() {
        return new BloodletterOfAclazotzEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOSE_LIFE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.isActivePlayer(source.getControllerId())
                && game.getOpponents(source.getControllerId()).contains(event.getPlayerId());
    }
}
