
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class SphinxsDecree extends CardImpl {

    public SphinxsDecree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Each opponent can't cast instant or sorcery spells during that player's next turn.
        this.getSpellAbility().addEffect(new SphinxsDecreeEffect());
    }

    private SphinxsDecree(final SphinxsDecree card) {
        super(card);
    }

    @Override
    public SphinxsDecree copy() {
        return new SphinxsDecree(this);
    }
}

class SphinxsDecreeEffect extends OneShotEffect {

    public SphinxsDecreeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each opponent can't cast instant or sorcery spells during that player's next turn";
    }

    public SphinxsDecreeEffect(final SphinxsDecreeEffect effect) {
        super(effect);
    }

    @Override
    public SphinxsDecreeEffect copy() {
        return new SphinxsDecreeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            ContinuousEffect effect = new SphinxsDecreeCantCastEffect();
            effect.setTargetPointer(new FixedTarget(opponentId));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class SphinxsDecreeCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    int playersNextTurn;

    public SphinxsDecreeCantCastEffect() {
        super(Duration.Custom, Outcome.Detriment);
        staticText = "You can't cast instant or sorcery spells during this turn";
        playersNextTurn = 0;
    }

    public SphinxsDecreeCantCastEffect(final SphinxsDecreeCantCastEffect effect) {
        super(effect);
        this.playersNextTurn = effect.playersNextTurn;
    }

    @Override
    public SphinxsDecreeCantCastEffect copy() {
        return new SphinxsDecreeCantCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast instant or sorcery spells this turn (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        UUID opponentId = getTargetPointer().getFirst(game, source);
        if (game.isActivePlayer(opponentId)) {
            if (playersNextTurn == 0) {
                playersNextTurn = game.getTurnNum();
            }
            if (playersNextTurn == game.getTurnNum()) {
                if (opponentId.equals(event.getPlayerId())) {
                    MageObject object = game.getObject(event.getSourceId());
                    if (event.getType() == GameEvent.EventType.CAST_SPELL) {
                        if (object != null && object.isInstantOrSorcery(game)) {
                            return true;
                        }
                    }
                }
            } else {
                discard();
            }
        } else if (playersNextTurn > 0) {
            discard();
        }
        return false;
    }
}
