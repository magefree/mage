package mage.abilities.effects.common.continuous;

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

public class LookAtCardsExiledWithThisEffect extends AsThoughEffectImpl {

    public LookAtCardsExiledWithThisEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may look at cards exiled with {this}";
    }

    private LookAtCardsExiledWithThisEffect(final LookAtCardsExiledWithThisEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public LookAtCardsExiledWithThisEffect copy() {
        return new LookAtCardsExiledWithThisEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card card = game.getCard(objectId);
        if (!source.isControlledBy(affectedControllerId) || card == null) {
            return false;
        }
        int zcc = CardUtil.getActualSourceObjectZoneChangeCounter(game, source);
        ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source, zcc));
        if (exile == null || exile.isEmpty() || !exile.contains(card.getId())) {
            return false;
        }
        boolean canLookAtCard = exile.isPlayerAllowedToSeeCard(affectedControllerId, card);
        if (canLookAtCard) {
            return true;
        }
        exile.letPlayerSeeCards(affectedControllerId, card);
        return true;
    }

}
