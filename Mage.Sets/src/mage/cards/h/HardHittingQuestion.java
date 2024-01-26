package mage.cards.h;

import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HardHittingQuestion extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public HardHittingQuestion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Target creature you control deals damage equal to its power to target creature or planeswalker you don't control.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private HardHittingQuestion(final HardHittingQuestion card) {
        super(card);
    }

    @Override
    public HardHittingQuestion copy() {
        return new HardHittingQuestion(this);
    }
}
