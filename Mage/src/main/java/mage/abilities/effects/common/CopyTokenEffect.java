package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

public class CopyTokenEffect extends ContinuousEffectImpl {

    protected Token token;

    public CopyTokenEffect(Token token) {
        super(Duration.WhileOnBattlefield, Layer.CopyEffects_1, SubLayer.CopyEffects_1a, Outcome.BecomeCreature);
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
        permanent.removeAllCardTypes(game);
        for (CardType type : token.getCardType(game)) {
            permanent.addCardType(game, type);
        }
        permanent.removeAllSubTypes(game);
        permanent.copySubTypesFrom(game, token);
        permanent.getSuperType().clear();
        for (SuperType type : token.getSuperType()) {
            permanent.addSuperType(type);
        }
        permanent.getAbilities().clear();
        for (Ability ability : token.getAbilities()) {
            permanent.addAbility(ability, source.getSourceId(), game);
        }
        permanent.getPower().setModifiedBaseValue(token.getPower().getModifiedBaseValue());
        permanent.getToughness().setModifiedBaseValue(token.getToughness().getModifiedBaseValue());
        //permanent.getLoyalty().setBoostedValue(card.getLoyalty().getValue());

        return true;

    }

    @Override
    public CopyTokenEffect copy() {
        return new CopyTokenEffect(this);
    }

}
