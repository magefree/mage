package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class TibaltCosmicImpostorEmblem extends Emblem {
    // You may play cards exiled with Tibalt, Cosmic Impostor, and you may spend mana as though it were mana of any color to cast those spells."

    public TibaltCosmicImpostorEmblem() {
        setName("Emblem Tibalt");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new TibaltCosmicImpostorPlayFromExileEffect()));

        this.setExpansionSetCodeForImage("KHM");
    }
}

class TibaltCosmicImpostorPlayFromExileEffect extends AsThoughEffectImpl {

    TibaltCosmicImpostorPlayFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may play cards exiled with Tibalt, Cosmic Impostor, and you may spend mana as though it were mana of any color to cast those spells";
    }

    TibaltCosmicImpostorPlayFromExileEffect(final TibaltCosmicImpostorPlayFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TibaltCosmicImpostorPlayFromExileEffect copy() {
        return new TibaltCosmicImpostorPlayFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Emblem tibaltEmblem = (Emblem) game.getEmblem(source.getSourceId());
        if (tibaltEmblem == null) {
            return false;
        }
        // the exile zone of the Tibalt, Cosmic Impostor that spawned the emblem only
        UUID exileId = CardUtil.getExileZoneId(tibaltEmblem.getSourceObject().getId().toString(), game);
        if (exileId == null) {
            return false;
        }
        ExileZone exile = game.getState().getExile().getExileZone(exileId);
        if (exile == null) {
            return false;
        }
        if (exile.isEmpty()) {
            return false;
        }
        Card cardInExile = game.getCard(objectId);
        if (cardInExile == null) {
            return false;
        }
        UUID mainCardId = cardInExile.getMainCard().getId();
        if (exile.contains(mainCardId)
                && affectedControllerId.equals(source.getControllerId())
                && game.getState().getZone(mainCardId).equals(Zone.EXILED)) {
            CardUtil.makeCardPlayable(game, source, cardInExile, Duration.Custom, true);
            return true;
        }
        return false;
    }
}
