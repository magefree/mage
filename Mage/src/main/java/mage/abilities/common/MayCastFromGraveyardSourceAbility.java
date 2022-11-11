package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class MayCastFromGraveyardSourceAbility extends StaticAbility {

    public MayCastFromGraveyardSourceAbility() {
        super(Zone.GRAVEYARD, new MayCastFromGraveyardEffect());
    }

    private MayCastFromGraveyardSourceAbility(final MayCastFromGraveyardSourceAbility ability) {
        super(ability);
    }

    @Override
    public MayCastFromGraveyardSourceAbility copy() {
        return new MayCastFromGraveyardSourceAbility(this);
    }
}

class MayCastFromGraveyardEffect extends AsThoughEffectImpl {

    MayCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.PutCreatureInPlay);
        staticText = "you may cast {this} from your graveyard";
    }

    private MayCastFromGraveyardEffect(final MayCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MayCastFromGraveyardEffect copy() {
        return new MayCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!objectId.equals(source.getSourceId()) || !source.isControlledBy(affectedControllerId)) {
            return false;
        }
        Card card = game.getCard(source.getSourceId());
        return card != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD;
    }
}
