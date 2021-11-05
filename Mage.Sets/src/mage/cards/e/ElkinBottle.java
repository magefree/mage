package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author L_J
 */
public final class ElkinBottle extends CardImpl {

    public ElkinBottle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {3}, {tap}, Exile the top card of your library. Until the beginning of your next upkeep, you may play that card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ElkinBottleExileEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ElkinBottle(final ElkinBottle card) {
        super(card);
    }

    @Override
    public ElkinBottle copy() {
        return new ElkinBottle(this);
    }
}

class ElkinBottleExileEffect extends OneShotEffect {

    public ElkinBottleExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "Exile the top card of your library. Until the beginning of your next upkeep, you may play that card";
    }

    public ElkinBottleExileEffect(final ElkinBottleExileEffect effect) {
        super(effect);
    }

    @Override
    public ElkinBottleExileEffect copy() {
        return new ElkinBottleExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.moveCardsToExile(card, source, game, true, source.getSourceId(), CardUtil.createObjectRealtedWindowTitle(source, game, null));
                ContinuousEffect effect = new ElkinBottleCastFromExileEffect();
                effect.setTargetPointer(new FixedTarget(card.getId(), game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class ElkinBottleCastFromExileEffect extends AsThoughEffectImpl {

    private boolean sameStep = true;

    public ElkinBottleCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.staticText = "Until the beginning of your next upkeep, you may play that card.";
    }

    public ElkinBottleCastFromExileEffect(final ElkinBottleCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public ElkinBottleCastFromExileEffect copy() {
        return new ElkinBottleCastFromExileEffect(this);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPhase().getStep().getType() == PhaseStep.UPKEEP) {
            if (!sameStep && game.isActivePlayer(source.getControllerId()) || game.getPlayer(source.getControllerId()).hasReachedNextTurnAfterLeaving()) {
                return true;
            }
        } else {
            sameStep = false;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && sourceId.equals(getTargetPointer().getFirst(game, source));
    }

}
