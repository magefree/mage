package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 * @author jeffwadsworth
 */
public class BecomesCreatureAttachedEffect extends ContinuousEffectImpl {

    public enum LoseType {
        NONE, ALL, ALL_BUT_COLOR, ABILITIES, ABILITIES_SUBTYPE, COLOR
    }

    protected Token token;
    protected String type;
    protected LoseType loseType;  // what attributes are lost

    public BecomesCreatureAttachedEffect(Token token, String text, Duration duration) {
        this(token, text, duration, LoseType.NONE);
    }

    public BecomesCreatureAttachedEffect(Token token, String text, Duration duration, LoseType loseType) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.BecomeCreature);
        this.token = token;
        this.loseType = loseType;
        staticText = text;
    }

    public BecomesCreatureAttachedEffect(Token token, String text, Duration duration, LoseType loseType, Outcome outcome) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, outcome);
        this.token = token;
        this.loseType = loseType;
        staticText = text;
    }

    public BecomesCreatureAttachedEffect(final BecomesCreatureAttachedEffect effect) {
        super(effect);
        this.token = effect.token.copy();
        this.type = effect.type;
        this.loseType = effect.loseType;
    }

    @Override
    public BecomesCreatureAttachedEffect copy() {
        return new BecomesCreatureAttachedEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null) {
            Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
            if (permanent != null) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (sublayer == SubLayer.NA) {
                            for (SuperType t : token.getSuperType()) {
                                permanent.addSuperType(t);

                            }
                            // card type
                            switch (loseType) {
                                case ALL:
                                case ALL_BUT_COLOR:
                                    permanent.getCardType().clear();
                                    break;
                            }
                            for (CardType t : token.getCardType()) {
                                permanent.addCardType(t);
                            }

                            // sub type
                            switch (loseType) {
                                case ALL:
                                case ALL_BUT_COLOR:
                                case ABILITIES_SUBTYPE:
                                    if (permanent.isLand()) {
                                        permanent.getSubtype(game).retainAll(SubType.getLandTypes());
                                    } else {
                                        permanent.getSubtype(game).clear();
                                    }
                                    permanent.setIsAllCreatureTypes(false);
                                    break;
                            }
                            for (SubType t : token.getSubtype(game)) {
                                if (!permanent.hasSubtype(t, game)) {
                                    permanent.getSubtype(game).add(t);
                                }
                            }
                        }
                        break;

                    case ColorChangingEffects_5:
                        if (sublayer == SubLayer.NA) {
                            if (loseType == LoseType.ALL || loseType == LoseType.COLOR) {
                                permanent.getColor(game).setBlack(false);
                                permanent.getColor(game).setGreen(false);
                                permanent.getColor(game).setBlue(false);
                                permanent.getColor(game).setWhite(false);
                                permanent.getColor(game).setRed(false);
                            }
                            if (token.getColor(game).hasColor()) {
                                permanent.getColor(game).addColor(token.getColor(game));
                            }
                        }
                        break;

                    case AbilityAddingRemovingEffects_6:
                        if (sublayer == SubLayer.NA) {
                            switch (loseType) {
                                case ALL:
                                case ALL_BUT_COLOR:
                                case ABILITIES:
                                case ABILITIES_SUBTYPE:
                                    permanent.removeAllAbilities(source.getSourceId(), game);
                                    break;
                            }
                            for (Ability ability : token.getAbilities()) {
                                permanent.addAbility(ability, source.getSourceId(), game);
                            }
                        }
                        break;

                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) {
                            permanent.getPower().setValue(token.getPower().getValue());
                            permanent.getToughness().setValue(token.getToughness().getValue());
                        }
                        break;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7
                || layer == Layer.AbilityAddingRemovingEffects_6
                || layer == Layer.ColorChangingEffects_5
                || layer == Layer.TypeChangingEffects_4;
    }

}
