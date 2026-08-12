package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.WolfToken;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class HeadOfTheHunt extends CardImpl {

    public HeadOfTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // If a creature an opponent controls would die, exile it instead. When you do, create a 2/2 green Wolf creature token.
        this.addAbility(new SimpleStaticAbility(new HeadOfTheHuntEffect()));
    }

    private HeadOfTheHunt(final HeadOfTheHunt card) {
        super(card);
    }

    @Override
    public HeadOfTheHunt copy() {
        return new HeadOfTheHunt(this);
    }
}

class HeadOfTheHuntEffect extends ReplacementEffectImpl {

    HeadOfTheHuntEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If a creature an opponent controls would die, exile it instead. " +
                "When you do, create a 2/2 green Wolf creature token.";
    }

    private HeadOfTheHuntEffect(final HeadOfTheHuntEffect effect) {
        super(effect);
    }

    @Override
    public HeadOfTheHuntEffect copy() {
        return new HeadOfTheHuntEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        game.fireReflexiveTriggeredAbility(new ReflexiveTriggeredAbility(
                new CreateTokenEffect(new WolfToken()), false,
                "If a creature an opponent controls would die, exile it instead. " +
                        "When you do, create a 2/2 green Wolf creature token"
        ), source);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.isDiesEvent()
                && zEvent.isPermanentMoved()
                && zEvent.getTarget().isCreature(game)
                && game.getOpponents(zEvent.getTarget().getControllerId()).contains(source.getControllerId());
    }
}
