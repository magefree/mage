package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

public class CopyTokenEffect extends ContinuousEffectImpl {
    protected Token token;

    public CopyTokenEffect(Token token) {
        super(Duration.WhileOnBattlefield, Layer.CopyEffects_1, SubLayer.NA, Outcome.BecomeCreature);
        this.token = token.copy();
        staticText = "You may have {this} enter the battlefield as a copy of " + token.getDescription() + " on the battlefield";
    }

    public CopyTokenEffect(final CopyTokenEffect effect) {
        super(effect);
        this.token = effect.token.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        permanent.setName(token.getName());
        permanent.getColor(game).setColor(token.getColor(game));
        permanent.getCardType().clear();
        for (CardType type: token.getCardType()) {
            permanent.getCardType().add(type);
        }
        permanent.getSubtype(game).clear();
        for (String type: token.getSubtype(game)) {
            permanent.getSubtype(game).add(type);
        }
        permanent.getSupertype().clear();
        for (String type: token.getSupertype()) {
            permanent.getSupertype().add(type);
        }
        permanent.getAbilities().clear();
        for (Ability ability: token.getAbilities()) {
            permanent.addAbility(ability, game);
        }
        permanent.getPower().setValue(token.getPower().getValue());
        permanent.getToughness().setValue(token.getToughness().getValue());
        //permanent.getLoyalty().setValue(card.getLoyalty().getValue());

        return true;

    }

    @Override
    public CopyTokenEffect copy() {
        return new CopyTokenEffect(this);
    }

}
