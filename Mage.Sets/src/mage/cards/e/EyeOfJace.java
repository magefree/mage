package mage.cards.e;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author muz
 */
public final class EyeOfJace extends CardImpl {

    public EyeOfJace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        
        // At the beginning of your upkeep, surveil 1. Then if there are seven or more cards in your graveyard, sacrifice this artifact, it deals 2 damage to each opponent, and you gain 2 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new EyeOfJaceEffect()));
    }

    private EyeOfJace(final EyeOfJace card) {
        super(card);
    }

    @Override
    public EyeOfJace copy() {
        return new EyeOfJace(this);
    }
}

class EyeOfJaceEffect extends OneShotEffect {
    
    public EyeOfJaceEffect() {
        super(Outcome.Benefit);
        this.staticText = "surveil 1. Then if there are seven or more cards in your graveyard, sacrifice this artifact, it deals 2 damage to each opponent, and you gain 2 life";
    }

    private EyeOfJaceEffect(final EyeOfJaceEffect effect) {
        super(effect);
    }

    @Override
    public EyeOfJaceEffect copy() {
        return new EyeOfJaceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        controller.surveil(1, source, game);
        if (controller.getGraveyard().size() >= 7) {
            Permanent permanent = source.getSourcePermanentIfItStillExists(game);
            if (permanent != null) {
                permanent.sacrifice(source, game);
            }
            for (UUID opponentId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    opponent.damage(2, source.getSourceId(), source, game);
                }
            }
            controller.gainLife(2, game, source);
        }
        return true;
    }
}
