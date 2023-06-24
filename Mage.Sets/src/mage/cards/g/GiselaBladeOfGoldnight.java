
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GiselaBladeOfGoldnight extends CardImpl {

    public GiselaBladeOfGoldnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(FirstStrikeAbility.getInstance());

        // If a source would deal damage to an opponent or a permanent an opponent controls, that source deals double that damage to that player or permanent instead.
        this.addAbility(new SimpleStaticAbility(new GiselaBladeOfGoldnightDoubleDamageEffect()));

        // If a source would deal damage to you or a permanent you control, prevent half that damage, rounded up.
        this.addAbility(new SimpleStaticAbility(new GiselaBladeOfGoldnightPreventionEffect()));
    }

    private GiselaBladeOfGoldnight(final GiselaBladeOfGoldnight card) {
        super(card);
    }

    @Override
    public GiselaBladeOfGoldnight copy() {
        return new GiselaBladeOfGoldnight(this);
    }
}

class GiselaBladeOfGoldnightDoubleDamageEffect extends ReplacementEffectImpl {

    GiselaBladeOfGoldnightDoubleDamageEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a source would deal damage to an opponent or a permanent an opponent controls, " +
                "that source deals double that damage to that player or permanent instead.";
    }

    private GiselaBladeOfGoldnightDoubleDamageEffect(final GiselaBladeOfGoldnightDoubleDamageEffect effect) {
        super(effect);
    }

    @Override
    public GiselaBladeOfGoldnightDoubleDamageEffect copy() {
        return new GiselaBladeOfGoldnightDoubleDamageEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
            case DAMAGE_PERMANENT:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return player.hasOpponent(event.getTargetId(), game)
                || player.hasOpponent(game.getControllerId(event.getTargetId()), game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}

class GiselaBladeOfGoldnightPreventionEffect extends PreventionEffectImpl {

    GiselaBladeOfGoldnightPreventionEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "If a source would deal damage to you or a permanent you control, " +
                "prevent half that damage, rounded up";
    }

    private GiselaBladeOfGoldnightPreventionEffect(final GiselaBladeOfGoldnightPreventionEffect effect) {
        super(effect);
    }

    @Override
    public GiselaBladeOfGoldnightPreventionEffect copy() {
        return new GiselaBladeOfGoldnightPreventionEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getTargetId())
                || source.isControlledBy(game.getControllerId(event.getTargetId()));
    }

    @Override
    protected PreventionEffectData preventDamageAction(GameEvent event, Ability source, Game game) {
        return game.preventDamage(event, source, game, Math.floorDiv(event.getAmount(), 2) + (event.getAmount() % 2));
    }
}
