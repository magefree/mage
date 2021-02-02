package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX
 */
public final class SamuraiOfThePaleCurtain extends CardImpl {

    public SamuraiOfThePaleCurtain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Bushido 1 (When this blocks or becomes blocked, it gets +1/+1 until end of turn.)
        this.addAbility(new BushidoAbility(1));

        // If a permanent would be put into a graveyard, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SamuraiOfThePaleCurtainEffect()));
    }

    private SamuraiOfThePaleCurtain(final SamuraiOfThePaleCurtain card) {
        super(card);
    }

    @Override
    public SamuraiOfThePaleCurtain copy() {
        return new SamuraiOfThePaleCurtain(this);
    }

}


class SamuraiOfThePaleCurtainEffect extends ReplacementEffectImpl {

    SamuraiOfThePaleCurtainEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If a permanent would be put into a graveyard, exile it instead";
    }

    private SamuraiOfThePaleCurtainEffect(final SamuraiOfThePaleCurtainEffect effect) {
        super(effect);
    }

    @Override
    public SamuraiOfThePaleCurtainEffect copy() {
        return new SamuraiOfThePaleCurtainEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                return player.moveCards(permanent, Zone.EXILED, source, game);
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getToZone() == Zone.GRAVEYARD;
    }

}
