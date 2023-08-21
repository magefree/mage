package mage.abilities.effects;

import mage.abilities.Ability;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

public class CastCardFromGraveyardThenExileItEffect extends OneShotEffect {

    public CastCardFromGraveyardThenExileItEffect() {
        super(Outcome.Benefit);
    }

    protected CastCardFromGraveyardThenExileItEffect(final CastCardFromGraveyardThenExileItEffect effect) {
        super(effect);
    }

    @Override
    public CastCardFromGraveyardThenExileItEffect copy() {
        return new CastCardFromGraveyardThenExileItEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (card == null) {
            return false;
        }
        ContinuousEffect effect = new CastCardFromGraveyardEffect();
        effect.setTargetPointer(new FixedTarget(card, game));
        game.addEffect(effect, source);
        ContinuousEffect effect2 = new ThatSpellGraveyardExileReplacementEffect();
        effect2.setTargetPointer(new FixedTarget(card, game));
        game.addEffect(effect2, source);
        return true;
    }
}

class CastCardFromGraveyardEffect extends AsThoughEffectImpl {

    CastCardFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        this.staticText = "You may cast target card from your graveyard";
    }

    private CastCardFromGraveyardEffect(final CastCardFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CastCardFromGraveyardEffect copy() {
        return new CastCardFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return objectId.equals(this.getTargetPointer().getFirst(game, source))
                && affectedControllerId.equals(source.getControllerId());
    }
}
