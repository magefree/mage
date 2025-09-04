package mage.cards.h;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class HideOnTheCeiling extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts and/or creatures");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public HideOnTheCeiling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");
        

        // Exile X target artifacts and/or creatures. Return the exiled cards to the battlefield under their owners' control at the beginning of the next end step.
        Effect effect = new ExileReturnBattlefieldNextEndStepTargetEffect()
                .returnExiledOnly(true);
        effect.setText("Exile X target artifacts and/or creatures. Return the exiled cards to the battlefield under their owners' control at the beginning of the next end step");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
    }

    private HideOnTheCeiling(final HideOnTheCeiling card) {
        super(card);
    }

    @Override
    public HideOnTheCeiling copy() {
        return new HideOnTheCeiling(this);
    }
}
