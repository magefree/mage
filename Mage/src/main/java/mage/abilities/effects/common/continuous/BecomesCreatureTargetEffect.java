
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.target.Target;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class BecomesCreatureTargetEffect extends ContinuousEffectImpl {

    protected Token token;
    protected boolean loseAllAbilities;
    protected boolean addStillALandText;

    /**
     *
     * @param token
     * @param loseAllAbilities loses all subtypes and colors
     * @param stillALand add rule text, "it's still a land"
     * @param duration
     */
    public BecomesCreatureTargetEffect(Token token, boolean loseAllAbilities, boolean stillALand, Duration duration) {
        super(duration, Outcome.BecomeCreature);
        this.token = token;
        this.loseAllAbilities = loseAllAbilities;
        this.addStillALandText = stillALand;
    }

    public BecomesCreatureTargetEffect(final BecomesCreatureTargetEffect effect) {
        super(effect);
        token = effect.token.copy();
        this.loseAllAbilities = effect.loseAllAbilities;
        this.addStillALandText = effect.addStillALandText;
    }

    @Override
    public BecomesCreatureTargetEffect copy() {
        return new BecomesCreatureTargetEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        boolean result = false;
        for (UUID permanentId : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (sublayer == SubLayer.NA) {
                            if (loseAllAbilities) {
                                permanent.getSubtype(game).retainAll(SubType.getLandTypes(false));
                                permanent.getSubtype(game).addAll(token.getSubtype(game));
                            } else {
                                if (!token.getSubtype(game).isEmpty()) {
                                    for (SubType subtype : token.getSubtype(game)) {
                                        if (!permanent.hasSubtype(subtype, game)) {
                                            permanent.getSubtype(game).add(subtype);
                                        }
                                    }

                                }
                            }
                            if (!token.getCardType().isEmpty()) {
                                for (CardType t : token.getCardType()) {
                                    if (!permanent.getCardType().contains(t)) {
                                        permanent.addCardType(t);
                                    }
                                }
                            }
                        }
                        break;
                    case ColorChangingEffects_5:
                        if (sublayer == SubLayer.NA) {
                            if (loseAllAbilities) {
                                permanent.getColor(game).setBlack(false);
                                permanent.getColor(game).setGreen(false);
                                permanent.getColor(game).setBlue(false);
                                permanent.getColor(game).setWhite(false);
                                permanent.getColor(game).setBlack(false);
                            }
                            if (token.getColor(game).hasColor()) {
                                permanent.getColor(game).setColor(token.getColor(game));
                            }
                        }
                        break;
                    case AbilityAddingRemovingEffects_6:
                        if (loseAllAbilities) {
                            permanent.removeAllAbilities(source.getSourceId(), game);
                        }
                        if (sublayer == SubLayer.NA) {
                            if (!token.getAbilities().isEmpty()) {
                                for (Ability ability : token.getAbilities()) {
                                    permanent.addAbility(ability, source.getSourceId(), game);
                                }
                            }
                        }
                        break;
                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) { //  CDA can only define a characteristic of either the card or token it comes from.
                            permanent.getToughness().setValue(token.getToughness().getValue());
                            permanent.getPower().setValue(token.getPower().getValue());
                        }
                }
                result = true;
            }
        }
        if (!result && this.duration == Duration.Custom) {
            this.discard();
        }
        return result;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        Target target = mode.getTargets().get(0);
        if (target.getMaxNumberOfTargets() > 1) {
            if (target.getNumberOfTargets() < target.getMaxNumberOfTargets()) {
                sb.append("up to ");
            }
            sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(" target ").append(target.getTargetName());
            if (loseAllAbilities) {
                sb.append(" lose all their abilities and ");
            }
            sb.append("  each become ");
        } else {
            sb.append("target ").append(target.getTargetName());
            if (loseAllAbilities) {
                sb.append(" loses all abilities and ");
            }
            sb.append(" becomes a ");
        }
        sb.append(token.getDescription());
        sb.append(' ').append(duration.toString());
        if (addStillALandText) {
            if (target.getMaxNumberOfTargets() > 1) {
                sb.append(". They're still lands");
            } else {
                sb.append(". It's still a land");
            }
        }
        return sb.toString();
    }

}
