package mage.game.command.emblems;

import java.util.UUID;
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

/**
 *
 * @author jeffwadsworth
 */
public final class TibaltCosmicImposterEmblem extends Emblem {
    // You may play cards exiled with Tibalt, Cosmic Impostor, and you may spend mana as though it were mana of any color to cast those spells."

    public TibaltCosmicImposterEmblem() {
        setName("Emblem Tibalt Cosmic Imposter");
        this.setExpansionSetCodeForImage("KHM");
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new TibaltCosmicImposterPlayFromExileEffect());
        this.getAbilities().add(ability);
    }
}

class TibaltCosmicImposterPlayFromExileEffect extends AsThoughEffectImpl {

    TibaltCosmicImposterPlayFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may play cards exiled with Tibalt, Cosmic Impostor, and you may spend mana as though it were mana of any color to cast those spells";
    }

    TibaltCosmicImposterPlayFromExileEffect(final TibaltCosmicImposterPlayFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TibaltCosmicImposterPlayFromExileEffect copy() {
        return new TibaltCosmicImposterPlayFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Emblem tibaltEmblem = (Emblem) game.getEmblem(source.getSourceId());
        if (tibaltEmblem == null) {
            return false;
        }
        // the exile zone of the Tibalt, Cosmic Imposter that spawned the emblem only
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
            CardUtil.makeCardPlayableAndSpendManaAsAnyColor(game, source, cardInExile, Duration.Custom);
            return true;
        }
        return false;
    }
}
