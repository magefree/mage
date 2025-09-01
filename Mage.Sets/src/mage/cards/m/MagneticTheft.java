package mage.cards.m;

import mage.abilities.effects.common.AttachTargetToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class MagneticTheft extends CardImpl {

    public MagneticTheft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Attach target Equipment to target creature.
        this.getSpellAbility().addEffect(new AttachTargetToTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_EQUIPMENT));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private MagneticTheft(final MagneticTheft card) {
        super(card);
    }

    @Override
    public MagneticTheft copy() {
        return new MagneticTheft(this);
    }
}
