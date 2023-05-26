package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.Emblem;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WrennAndRealmbreakerEmblem extends Emblem {

    // -7: You get an emblem with "You may play lands and cast permanent spells from your graveyard."
    public WrennAndRealmbreakerEmblem() {
        super("Emblem Wrenn");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new WrennAndRealmbreakerEmblemEffect()));
    }

    private WrennAndRealmbreakerEmblem(final WrennAndRealmbreakerEmblem card) {
        super(card);
    }

    @Override
    public WrennAndRealmbreakerEmblem copy() {
        return new WrennAndRealmbreakerEmblem(this);
    }
}

class WrennAndRealmbreakerEmblemEffect extends AsThoughEffectImpl {

    public WrennAndRealmbreakerEmblemEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "you may play lands and cast permanent spells from your graveyard";
    }

    public WrennAndRealmbreakerEmblemEffect(final WrennAndRealmbreakerEmblemEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public WrennAndRealmbreakerEmblemEffect copy() {
        return new WrennAndRealmbreakerEmblemEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)) {
            return false;
        }
        Card card = game.getCard(objectId);
        return card != null
                && card.isPermanent(game)
                && card.isOwnedBy(source.getControllerId())
                && game.getState().getZone(objectId) == Zone.GRAVEYARD;
    }
}
