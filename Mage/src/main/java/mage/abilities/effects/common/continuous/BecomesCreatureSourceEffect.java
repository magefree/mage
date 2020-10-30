package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class BecomesCreatureSourceEffect extends ContinuousEffectImpl implements SourceEffect {

    protected Token token;
    protected String theyAreStillType;
    protected boolean losePreviousTypes;
    protected boolean loseAbilities;
    protected DynamicValue power = null;
    protected DynamicValue toughness = null;

    public BecomesCreatureSourceEffect(Token token, String theyAreStillType, Duration duration) {
        this(token, theyAreStillType, duration, false, false);
    }

    public BecomesCreatureSourceEffect(Token token, String theyAreStillType, Duration duration, boolean losePreviousTypes, boolean characterDefining) {
        this(token, theyAreStillType, duration, losePreviousTypes, characterDefining, null, null);
    }

    public BecomesCreatureSourceEffect(Token token, String theyAreStillType, Duration duration, boolean losePreviousTypes, boolean characterDefining, DynamicValue power, DynamicValue toughness) {
        this(token, theyAreStillType, duration, losePreviousTypes, characterDefining, power, toughness, false);
    }

    public BecomesCreatureSourceEffect(Token token, String theyAreStillType, Duration duration, boolean losePreviousTypes, boolean characterDefining, DynamicValue power, DynamicValue toughness, boolean loseAbilities) {
        super(duration, Outcome.BecomeCreature);
        this.characterDefining = characterDefining;
        this.token = token;
        this.theyAreStillType = theyAreStillType;
        this.losePreviousTypes = losePreviousTypes;
        this.power = power;
        this.toughness = toughness;
        this.loseAbilities = loseAbilities;
        setText();

        this.addDependencyType(DependencyType.BecomeCreature);
    }

    public BecomesCreatureSourceEffect(final BecomesCreatureSourceEffect effect) {
        super(effect);
        this.token = effect.token.copy();
        this.theyAreStillType = effect.theyAreStillType;
        this.losePreviousTypes = effect.losePreviousTypes;
        this.loseAbilities = effect.loseAbilities;
        if (effect.power != null) {
            this.power = effect.power.copy();
        }
        if (effect.toughness != null) {
            this.toughness = effect.toughness.copy();
        }
    }

    @Override
    public BecomesCreatureSourceEffect copy() {
        return new BecomesCreatureSourceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (affectedObjectsSet) {
            affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent;
        if (affectedObjectsSet) {
            permanent = affectedObjectList.get(0).getPermanent(game);
        } else {
            permanent = game.getPermanent(source.getSourceId());
        }
        if (permanent != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        if (losePreviousTypes) {
                            permanent.getCardType().clear();
                        }
                        for (CardType cardType : token.getCardType()) {
                            if (permanent.getCardType().contains(cardType)) {
                                continue;
                            }
                            permanent.addCardType(cardType);
                        }

                        if (theyAreStillType != null && theyAreStillType.isEmpty() || theyAreStillType == null && permanent.isLand()) {
                            permanent.getSubtype(game).retainAll(SubType.getLandTypes());
                        }
                        if (!token.getSubtype(game).isEmpty()) {
                            for (SubType subType : token.getSubtype(game)) {
                                if (permanent.hasSubtype(subType, game)) {
                                    continue;
                                }
                                permanent.getSubtype(game).add(subType);
                            }
                        }
                        permanent.setIsAllCreatureTypes(token.isAllCreatureTypes());
                    }
                    break;

                case ColorChangingEffects_5:
                    if (sublayer == SubLayer.NA) {
                        if (token.getColor(game).hasColor()) {
                            permanent.getColor(game).setColor(token.getColor(game));
                        }
                    }
                    break;

                case AbilityAddingRemovingEffects_6:
                    if (sublayer == SubLayer.NA) {
                        if (loseAbilities) {
                            permanent.removeAllAbilities(source.getSourceId(), game);
                        }
                        for (Ability ability : token.getAbilities()) {
                            permanent.addAbility(ability, source.getSourceId(), game);
                        }
                    }
                    break;

                case PTChangingEffects_7:
                    if ((sublayer == SubLayer.CharacteristicDefining_7a && isCharacterDefining())
                            || (sublayer == SubLayer.SetPT_7b && !isCharacterDefining())) {
                        if (power != null) {
                            permanent.getPower().setValue(power.calculate(game, source, this)); // check all other becomes to use calculate?
                        } else if (token.getPower() != null) {
                            permanent.getPower().setValue(token.getPower().getValue());
                        }
                        if (toughness != null) {
                            permanent.getToughness().setValue(toughness.calculate(game, source, this));
                        } else if (token.getToughness() != null) {
                            permanent.getToughness().setValue(token.getToughness().getValue());
                        }
                    }
                    break;
            }

            return true;

        } else if (duration == Duration.Custom) {
            this.discard();
        }

        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    private void setText() {
        if (theyAreStillType != null && !theyAreStillType.isEmpty()) {
            staticText = duration.toString() + " {this} becomes a " + token.getDescription() + " that's still a " + this.theyAreStillType;
        } else {
            staticText = duration.toString() + " {this} becomes a " + token.getDescription();
        }
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }

}
