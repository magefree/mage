package mage.cards;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.TransformAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.Target;

import java.util.UUID;

/**
 * @author TheElk801
 */
public abstract class TransformingDoubleFacedCard extends CardImpl {

    protected TransformingDoubleFacedCardHalfImpl leftHalfCard; // main card in all zone
    protected TransformingDoubleFacedCardHalfImpl rightHalfCard; // second side card, can be only in stack and battlefield zones
    private boolean finalized = false;

    public TransformingDoubleFacedCard(
            UUID ownerId, CardSetInfo setInfo,
            CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
            String secondSideName,
            CardType[] typesRight, SubType[] subTypesRight, String colorRight
    ) {
        this(
                ownerId, setInfo,
                new SuperType[]{}, typesLeft, subTypesLeft, costsLeft,
                secondSideName,
                new SuperType[]{}, typesRight, subTypesRight, colorRight
        );
    }

    public TransformingDoubleFacedCard(
            UUID ownerId, CardSetInfo setInfo,
            SuperType[] superTypesLeft, CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
            String secondSideName,
            SuperType[] superTypesRight, CardType[] typesRight, SubType[] subTypesRight, String colorRight
    ) {
        super(ownerId, setInfo, typesLeft, costsLeft);
        this.leftHalfCard = new TransformingDoubleFacedCardHalfImpl(ownerId, setInfo, costsLeft);
        this.rightHalfCard = new TransformingDoubleFacedCardHalfImpl(ownerId, setInfo, "");
        for (SuperType superType : superTypesLeft) {
            this.getLeftHalfCard().getSuperType().add(superType);
        }
        this.getLeftHalfCard().getSubtype().add(subTypesLeft);
        for (SuperType superType : superTypesRight) {
            this.getRightHalfCard().getSuperType().add(superType);
        }
        this.getRightHalfCard().addCardType(typesRight);
        this.getRightHalfCard().setName(secondSideName);
        this.getRightHalfCard().addSubType(subTypesRight);
        this.getRightHalfCard().getColor().addColor(new ObjectColor(colorRight));
    }

    protected TransformingDoubleFacedCard(final TransformingDoubleFacedCard card) {
        super(card);
        this.leftHalfCard = card.leftHalfCard.copy();
        this.rightHalfCard = card.rightHalfCard.copy();
        if (!card.finalized) {
            throw new IllegalStateException("Error, finalizeDFC needs to be called");
        }
        this.finalized = true;
    }

    public Card getLeftHalfCard() {
        return leftHalfCard;
    }

    public Card getRightHalfCard() {
        return rightHalfCard;
    }

    protected void finalizeDFC() {
        this.getSuperType().addAll(this.getLeftHalfCard().getSuperType());
        this.getCardType().addAll(this.getLeftHalfCard().getCardType());
        this.getSubtype().addAll(this.getLeftHalfCard().getSubtype());
        for (Ability ability : this.getLeftHalfCard().getAbilities()) {
            if (!ability.equals(this.getLeftHalfCard().getSpellAbility())) {
                this.addAbility(ability);
            }
        }
        if (!this.getAbilities().containsClass(TransformAbility.class)) {
            this.addAbility(new TransformAbility());
        }
        for (Effect effect : this.getLeftHalfCard().getSpellAbility().getEffects()) {
            this.getSpellAbility().addEffect(effect);
        }
        for (Target target : this.getLeftHalfCard().getSpellAbility().getTargets()) {
            this.getSpellAbility().addTarget(target);
        }
        this.power = this.getLeftHalfCard().getPower().copy();
        this.toughness = this.getLeftHalfCard().getToughness().copy();
        this.startingLoyalty = this.getLeftHalfCard().getStartingLoyalty();
        this.startingDefense = this.getLeftHalfCard().getStartingDefense();
        this.finalized = true;
    }

    public static void copyToBackFace(TransformingDoubleFacedCard tdfc, Card card) {
        card.getColor().setColor(tdfc.getRightHalfCard().getColor());
        card.getSuperType().addAll(tdfc.getRightHalfCard().getSuperType());
        card.getCardType().addAll(tdfc.getRightHalfCard().getCardType());
        card.getSubtype().addAll(tdfc.getRightHalfCard().getSubtype());
        for (Ability ability : tdfc.getRightHalfCard().getAbilities()) {
            if (!ability.equals(tdfc.getRightHalfCard().getSpellAbility())) {
                card.addAbility(ability);
            }
        }
        for (Effect effect : tdfc.getRightHalfCard().getSpellAbility().getEffects()) {
            card.getSpellAbility().addEffect(effect);
        }
        for (Target target : tdfc.getRightHalfCard().getSpellAbility().getTargets()) {
            card.getSpellAbility().addTarget(target);
        }
        card.setPT(tdfc.getRightHalfCard().getPower().copy(), tdfc.getRightHalfCard().getToughness().copy());
        card.setStartingLoyalty(tdfc.getRightHalfCard().getStartingLoyalty());
        card.setStartingDefense(tdfc.getRightHalfCard().getStartingDefense());
    }
}
