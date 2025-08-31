package mage.abilities.effects.common.asthought;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Susucr
 */
public class MayCastFromGraveyardAsAdventureEffect extends AsThoughEffectImpl {

    public MayCastFromGraveyardAsAdventureEffect() {
        super(AsThoughEffectType.CAST_ADVENTURE_FROM_NOT_OWN_HAND_ZONE, Duration.UntilEndOfYourNextTurn, Outcome.Benefit);
        staticText = "you may cast it from your graveyard as an Adventure until the end of your next turn";
    }

    private MayCastFromGraveyardAsAdventureEffect(final MayCastFromGraveyardAsAdventureEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MayCastFromGraveyardAsAdventureEffect copy() {
        return new MayCastFromGraveyardAsAdventureEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)) {
            return false;
        }
        Card card = game.getCard(sourceId);
        if (card == null || card.getMainCard() == null || !card.getMainCard().getId().equals(source.getSourceId())) {
            return false;
        }

        Card sourceCard = game.getCard(source.getSourceId());

        return sourceCard != null
                && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD
                && source.getStackMomentSourceZCC() == sourceCard.getZoneChangeCounter(game);
    }
}
