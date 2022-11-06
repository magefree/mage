package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class MiserysShadow extends CardImpl {

    public MiserysShadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.SHADE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // If a creature an opponent controls would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(new MiserysShadowReplacementEffect()));

        // {1}: Misery's Shadow gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), new GenericManaCost(1)));
    }

    private MiserysShadow(final MiserysShadow card) {
        super(card);
    }

    @Override
    public MiserysShadow copy() {
        return new MiserysShadow(this);
    }
}

class MiserysShadowReplacementEffect extends ReplacementEffectImpl {

    public MiserysShadowReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If a creature an opponent controls would die, exile it instead";
    }

    private MiserysShadowReplacementEffect(final MiserysShadowReplacementEffect effect) {
        super(effect);
    }

    @Override
    public MiserysShadowReplacementEffect copy() {
        return new MiserysShadowReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            Permanent permanent = zEvent.getTarget();
            if (permanent != null && permanent.isCreature()) {
                Player player = game.getPlayer(source.getControllerId());
                return player != null && player.hasOpponent(permanent.getControllerId(), game);
            }
        }
        return false;
    }
}