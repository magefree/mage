package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class MrSpockScienceOfficer extends CardImpl {

    public MrSpockScienceOfficer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VULCAN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // If you would draw a card, draw two cards instead.
        this.addAbility(new SimpleStaticAbility(new MrSpockScienceOfficerEffect()));
    }

    private MrSpockScienceOfficer(final MrSpockScienceOfficer card) {
        super(card);
    }

    @Override
    public MrSpockScienceOfficer copy() {
        return new MrSpockScienceOfficer(this);
    }
}

class MrSpockScienceOfficerEffect extends ReplacementEffectImpl {

    MrSpockScienceOfficerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If you would draw a card, draw two cards instead";
    }

    private MrSpockScienceOfficerEffect(final MrSpockScienceOfficerEffect effect) {
        super(effect);
    }

    @Override
    public MrSpockScienceOfficerEffect copy() {
        return new MrSpockScienceOfficerEffect(this);
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
