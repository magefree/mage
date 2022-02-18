package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.target.TargetPermanent;
import mage.target.common.TargetAnyTargetAmount;

public final class FireIce extends SplitCard {

    public FireIce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}", "{1}{U}", SpellAbilityType.SPLIT);

        // Fire
        // Fire deals 2 damage divided as you choose among one or two targets.
        getLeftHalfCard().getSpellAbility().addEffect(new DamageMultiEffect(2, "Fire"));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetAnyTargetAmount(2));

        // Ice
        // Tap target permanent.
        // Draw a card.
        getRightHalfCard().getSpellAbility().addEffect(new TapTargetEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetPermanent());
        getRightHalfCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));

    }

    private FireIce(final FireIce card) {
        super(card);
    }

    @Override
    public FireIce copy() {
        return new FireIce(this);
    }
}
