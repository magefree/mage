package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
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
        this.addAbility(new SimpleStaticAbility(new GloomSurgeonEffect()));
    }

    private GloomSurgeon(final GloomSurgeon card) {
        super(card);
    }

    @Override
    public GloomSurgeon copy() {
        return new GloomSurgeon(this);
    }
}

class GloomSurgeonEffect extends PreventionEffectImpl {

    GloomSurgeonEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, true, false);
        staticText = "If combat damage would be dealt to {this}, prevent that damage and exile that many cards from the top of your library";
    }

    private GloomSurgeonEffect(final GloomSurgeonEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int damage = event.getAmount();
        preventDamageAction(event, source, game);
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.moveCards(player.getLibrary().getTopCards(game, damage), Zone.EXILED, source, game);
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (source.getSourcePermanentIfItStillExists(game) == null) {
            return false;
        }
        return super.applies(event, source, game) && event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public GloomSurgeonEffect copy() {
        return new GloomSurgeonEffect(this);
    }
}
