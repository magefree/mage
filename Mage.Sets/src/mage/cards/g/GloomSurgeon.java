package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.*;
import mage.players.Player;

import java.util.UUID;

/**
 * @author noxx
 */
public final class GloomSurgeon extends CardImpl {

    public GloomSurgeon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // If combat damage would be dealt to Gloom Surgeon, prevent that damage and exile that many cards from the top of your library.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GloomSurgeonEffect()));
    }

    private GloomSurgeon(final GloomSurgeon card) {
        super(card);
    }

    @Override
    public GloomSurgeon copy() {
        return new GloomSurgeon(this);
    }
}

class GloomSurgeonEffect extends ReplacementEffectImpl {

    GloomSurgeonEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If combat damage would be dealt to {this}, prevent that damage and exile that many cards from the top of your library";
    }

    GloomSurgeonEffect(final GloomSurgeonEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int damage = event.getAmount();
        game.preventDamage(event, source, game, Integer.MAX_VALUE);
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.moveCards(player.getLibrary().getTopCards(game, damage), Zone.EXILED, source, game);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            DamageEvent damageEvent = (DamageEvent) event;
            return damageEvent.isCombatDamage();
        }
        return false;
    }

    @Override
    public GloomSurgeonEffect copy() {
        return new GloomSurgeonEffect(this);
    }
}
