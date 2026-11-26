
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

    SphinxsDecreeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each opponent can't cast instant or sorcery spells during that player's next turn";
    }

    private SphinxsDecreeEffect(final SphinxsDecreeEffect effect) {
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

    SphinxsDecreeCantCastEffect() {
        super(Duration.UntilEndOfYourNextTurn, Outcome.Detriment);
        staticText = "You can't cast instant or sorcery spells during this turn";
    }

    private SphinxsDecreeCantCastEffect(final SphinxsDecreeCantCastEffect effect) {
        super(effect);
    }

    @Override
    public SphinxsDecreeCantCastEffect copy() {
        return new SphinxsDecreeCantCastEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        UUID opponentId = getTargetPointer().getFirst(game, source);
        if (opponentId != null) {
            setStartingControllerAndTurnNum(game, opponentId, game.getActivePlayerId()); // setup startingController to calc isYourTurn calls
        } else {
            discard();
        }
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
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!isYourNextTurn(game) || !event.getPlayerId().equals(getTargetPointer().getFirst(game, source))) {
            return false;
        }
        MageObject object = game.getObject(event.getSourceId());
        return object != null && object.isInstantOrSorcery(game);
    }
}
