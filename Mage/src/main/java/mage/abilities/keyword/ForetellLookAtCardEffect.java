package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.ExileZone;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

public class ForetellLookAtCardEffect extends AsThoughEffectImpl {

    public ForetellLookAtCardEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.AIDontUseIt);
    }

    protected ForetellLookAtCardEffect(final ForetellLookAtCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ForetellLookAtCardEffect copy() {
        return new ForetellLookAtCardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            if (card != null) {
                MageObject sourceObject = game.getObject(source);
                if (sourceObject == null) {
                    return false;
                }
                UUID mainCardId = card.getMainCard().getId();
                UUID exileId = CardUtil.getExileZoneId(mainCardId.toString() + "foretellAbility", game);
                ExileZone exile = game.getExile().getExileZone(exileId);
                return exile != null
                        && exile.contains(mainCardId);
            }
        }
        return false;
    }
}
