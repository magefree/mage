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
 * @author BetaSteward_at_googlemail.com, xenohedron
 */
public class BecomesCreatureSourceEffect extends ContinuousEffectImpl {

    /*
     * CR 2023-04-14
     * 205.1a. Some effects set an object's card type. In most such cases, the new card type(s) replaces any existing
     * card types. However, an object with either the instant or sorcery card type retains that type. Counters,
     * stickers, effects, and damage marked on the object remain with it, even if they are meaningless to the new card
     * type. Similarly, when an effect sets one or more of an object's subtypes, the new subtype(s) replaces any
     * existing subtypes from the appropriate set (creature types, land types, artifact types, enchantment types,
     * planeswalker types, or spell types). If an object's card type is removed, the subtypes correlated with that card
     * type will remain if they are also the subtypes of a card type the object currently has; otherwise, they are also
     * removed for the entire time the object's card type is removed. Removing an object's subtype doesn't affect its
     * card types at all.
     * 205.1b. Some effects change an object's card type, supertype, or subtype but specify that the object retains a
     * prior card type, supertype, or subtype. In such cases, all the object's prior card types, supertypes, and
     * subtypes are retained. This rule applies to effects that use the phrase "in addition to its types" or that state
     * that something is "still a [type, supertype, or subtype]." Some effects state that an object becomes an
     * "artifact creature"; these effects also allow the object to retain all of its prior card types and subtypes.
     * Some effects state that an object becomes a "[creature type or types] artifact creature"; these effects also
     * allow the object to retain all of its prior card types and subtypes other than creature types, but replace any
     * existing creature types.
     */

    protected Token token;
    protected CardType retainType; // if null, loses previous types
    protected boolean loseAbilities = false;
    protected boolean loseEquipmentType = false;
    protected DynamicValue power = null;
    protected DynamicValue toughness = null;
    protected boolean durationRuleAtStart; // put duration rule at the start of the rules text rather than the end

    /**
     * @param token       Token as blueprint for creature to become
     * @param retainType  If null, permanent loses its previous types, otherwise retains types with appropriate text
     * @param duration    Duration for the effect
     */
    public BecomesCreatureSourceEffect(Token token, CardType retainType, Duration duration) {
        super(duration, Outcome.BecomeCreature);
        this.token = token;
        this.retainType = retainType;
        this.durationRuleAtStart = (retainType == CardType.PLANESWALKER || retainType == CardType.CREATURE);
        setText();
        this.addDependencyType(DependencyType.BecomeCreature);
    }

    public BecomesCreatureSourceEffect(final BecomesCreatureSourceEffect effect) {
        super(effect);
        this.token = effect.token.copy();
        this.retainType = effect.retainType;
        this.loseAbilities = effect.loseAbilities;
        this.loseEquipmentType = effect.loseEquipmentType;
        if (effect.power != null) {
            this.power = effect.power.copy();
        }
        if (effect.toughness != null) {
            this.toughness = effect.toughness.copy();
        }
        this.durationRuleAtStart = effect.durationRuleAtStart;
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
        if (permanent == null) {
            if (duration == Duration.Custom) {
                this.discard();
            }
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                if (retainType == null) {
                    permanent.removeAllCardTypes(game);
                    permanent.removeAllSubTypes(game);
                }
                for (SuperType superType : token.getSuperType(game)) {
                    permanent.addSuperType(game, superType);
                }
                for (CardType cardType : token.getCardType(game)) {
                    permanent.addCardType(game, cardType);
                }
                if (loseEquipmentType) {
                    permanent.removeSubType(game, SubType.EQUIPMENT);
                }
                if (retainType == CardType.CREATURE || retainType == CardType.ARTIFACT) {
                    permanent.removeAllCreatureTypes(game);
                }
                permanent.copySubTypesFrom(game, token);
                break;

            case ColorChangingEffects_5:
                if (token.getColor(game).hasColor()) {
                    permanent.getColor(game).setColor(token.getColor(game));
                }
                break;

            case AbilityAddingRemovingEffects_6:
                if (loseAbilities) {
                    permanent.removeAllAbilities(source.getSourceId(), game);
                }
                for (Ability ability : token.getAbilities()) {
                    permanent.addAbility(ability, source.getSourceId(), game);
                }
                break;

            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    if (power != null) {
                        permanent.getPower().setModifiedBaseValue(power.calculate(game, source, this)); // check all other becomes to use calculate?
                    } else if (token.getPower() != null) {
                        permanent.getPower().setModifiedBaseValue(token.getPower().getValue());
                    }
                    if (toughness != null) {
                        permanent.getToughness().setModifiedBaseValue(toughness.calculate(game, source, this));
                    } else if (token.getToughness() != null) {
                        permanent.getToughness().setModifiedBaseValue(token.getToughness().getValue());
                    }
                }
                break;
        }

        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    public BecomesCreatureSourceEffect withDynamicPT(DynamicValue power, DynamicValue toughness) {
        this.power = power;
        this.toughness = toughness;
        return this;
    }

    /**
     * Source loses all other abilities as part of the effect. Need to set text elsewhere.
     */
    public BecomesCreatureSourceEffect andLoseAbilities(boolean loseAbilities) {
        this.loseAbilities = loseAbilities;
        return this;
    }

    /**
     * Source loses Equipment subtype as part of the effect. Need to set text manually.
     */
    public BecomesCreatureSourceEffect andNotEquipment(boolean notEquipment) {
        this.loseEquipmentType = notEquipment;
        return this;
    }

    public BecomesCreatureSourceEffect withDurationRuleAtStart(boolean durationRuleAtStart) {
        this.durationRuleAtStart = durationRuleAtStart;
        setText();
        return this;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        if (!duration.toString().isEmpty() && durationRuleAtStart) {
            sb.append(duration.toString());
            sb.append(", ");
        }
        sb.append("{this} becomes a ");
        sb.append(token.getDescription());
        if (retainType == CardType.ENCHANTMENT) {
            sb.append(" in addition to its other types");
        }
        if (!duration.toString().isEmpty() && !durationRuleAtStart) {
            sb.append(" ");
            sb.append(duration.toString());
        }
        if (retainType == CardType.PLANESWALKER) {
            sb.append(" that's still a planeswalker");
        }
        if (retainType == CardType.LAND) {
            if (token.getDescription().endsWith(".\"")) {
                sb.append(" It's");
            } else {
                sb.append(". It's");
            }
            sb.append(" still a land");
        }
        staticText = sb.toString();
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7
                || layer == Layer.AbilityAddingRemovingEffects_6
                || layer == Layer.ColorChangingEffects_5
                || layer == Layer.TypeChangingEffects_4;
    }

}
