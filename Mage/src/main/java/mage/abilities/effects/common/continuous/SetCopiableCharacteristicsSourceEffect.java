package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 * @author xenohedron
 */
public class SetCopiableCharacteristicsSourceEffect extends ContinuousEffectImpl {

    /*
     * CR 2023-07-24
     * 613.2a Layer 1a: Copiable effects are applied.
     * "As ... enters the battlefield" and "as ... is turned face up" abilities generate copiable effects
     * if they set power and toughness, even if they also define other characteristics.
     */

    protected final Token token;

    /**
     * Apply this continuous effect from a one-shot effect that selects characteristics
     * as the creature enters the battlefield or is turned face up
     *
     * @param characteristics       Token as blueprint for creature to become.
     */
    public SetCopiableCharacteristicsSourceEffect(Token characteristics) {
        super(Duration.Custom, Layer.CopyEffects_1, SubLayer.CopyEffects_1a, Outcome.AddAbility);
        this.token = characteristics;
        if (token == null || token.getPower() == null || token.getToughness() == null) {
            throw new IllegalArgumentException("SetCopiableCharacteristics must set power and toughness");
        }
        staticText = "{this} becomes a " + token.getDescription()
                + (token.getSubtype().isEmpty() ? "" : " in addition to its other types");
    }

    protected SetCopiableCharacteristicsSourceEffect(final SetCopiableCharacteristicsSourceEffect effect) {
        super(effect);
        this.token = effect.token.copy();
    }

    @Override
    public SetCopiableCharacteristicsSourceEffect copy() {
        return new SetCopiableCharacteristicsSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(source.getSourceId());
        }
        if (permanent == null) {
            discard();
            return false;
        }
        for (CardType cardType : token.getCardType(game)) {
            permanent.addCardType(game, cardType);
        }
        permanent.copySubTypesFrom(game, token);
        if (token.getColor(game).hasColor()) {
            permanent.getColor(game).setColor(token.getColor(game));
        }
        for (Ability ability : token.getAbilities()) {
            permanent.addAbility(ability, source.getSourceId(), game);
        }
        permanent.getPower().setModifiedBaseValue(token.getPower().getValue());
        permanent.getToughness().setModifiedBaseValue(token.getToughness().getValue());
        return true;
    }

}
