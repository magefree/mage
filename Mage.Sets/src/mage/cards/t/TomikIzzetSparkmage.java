package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author notshauna
 */

public final class TomikIzzetSparkmage extends CardImpl {

    public TomikIzzetSparkmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Prowess
        this.addAbility(new ProwessAbility());

        //If a source you control would deal noncombat damage to an opponent or a permanent an opponent controls,
        // it deals that much damage plus 1 instead.
        this.addAbility(new SimpleStaticAbility(new TomikIzzetSparkmageEffect()));
    }

    private TomikIzzetSparkmage(final TomikIzzetSparkmage card) {
        super(card);
    }

    @Override
    public TomikIzzetSparkmage copy() {
        return new TomikIzzetSparkmage(this);
    }
}

class TomikIzzetSparkmageEffect extends ReplacementEffectImpl {

    TomikIzzetSparkmageEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText= "If a source you control would deal noncombat damage to an opponent or a permanent an opponent controls," +
                " it deals that much damage plus 1 instead.";
    }

    private TomikIzzetSparkmageEffect(final TomikIzzetSparkmageEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return source.isControlledBy(game.getControllerId(event.getSourceId()))
                && !((DamageEvent) event).isCombatDamage()
                && (player.hasOpponent(event.getTargetId(), game)
                || player.hasOpponent(game.getControllerId(event.getTargetId()), game));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }

    @Override
    public TomikIzzetSparkmageEffect copy() {
        return new TomikIzzetSparkmageEffect(this);
    }
}