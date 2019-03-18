package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author dustinconrad
 */
public final class DeadGone extends SplitCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public DeadGone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}", "{2}{R}", SpellAbilityType.SPLIT);

        // Dead
        // Dead deals 2 damage to target creature.
        getLeftHalfCard().getSpellAbility().addEffect(new DeadDamageEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Gone
        // Return target creature you don't control to its owner's hand.
        getRightHalfCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public DeadGone(final DeadGone card) {
        super(card);
    }

    @Override
    public Card copy() {
        return new DeadGone(this);
    }
}

class DeadDamageEffect extends DamageTargetEffect {

    public DeadDamageEffect() {
        super(2);
        // Full name of split card was displaying using DamageTargetEffect
        staticText = "Dead deals 2 damage to target creature.";
    }
}
