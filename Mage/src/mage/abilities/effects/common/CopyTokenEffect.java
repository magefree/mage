package mage.abilities.effects.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

public class CopyTokenEffect extends ContinuousEffectImpl<CopyTokenEffect> {
    protected Token token;

	public CopyTokenEffect(Token token) {
		super(Constants.Duration.WhileOnBattlefield, Constants.Layer.CopyEffects_1, Constants.SubLayer.NA, Constants.Outcome.BecomeCreature);
        this.token = token.copy();
	}

	public CopyTokenEffect(final CopyTokenEffect effect) {
		super(effect);
        this.token = effect.token.copy();
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent permanent = game.getPermanent(source.getSourceId());
		permanent.setName(token.getName());
		permanent.getColor().setColor(token.getColor());
		permanent.getCardType().clear();
		for (Constants.CardType type: token.getCardType()) {
			permanent.getCardType().add(type);
		}
		permanent.getSubtype().clear();
		for (String type: token.getSubtype()) {
			permanent.getSubtype().add(type);
		}
		permanent.getSupertype().clear();
		for (String type: token.getSupertype()) {
			permanent.getSupertype().add(type);
		}
		permanent.getAbilities().clear();
		for (Ability ability: token.getAbilities()) {
			permanent.addAbility(ability);
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

	@Override
	public String getText(Ability source) {
		return "You may have {this} enter the battlefield as a copy of " + token.getDescription() + " on the battlefield";
	}

}
