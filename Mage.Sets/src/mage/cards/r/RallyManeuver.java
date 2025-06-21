package mage.cards.r;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class RallyManeuver extends CardImpl {

    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("other creature");

    static {
        filter2.add(new AnotherTargetPredicate(2));
    }

    public RallyManeuver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Target creature gets +2/+0 and gains first strike until end of turn. Up to one other target creature gets +0/+2 and gains lifelink until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().setTargetTag(1).withChooseHint("+2/+0 and first strike"));
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 0).setText("Target creature gets +2/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn,
                "and gains first strike until end of turn"
        ));
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, filter2)
                .setTargetTag(2).withChooseHint("+0/+2 and lifelink"));
        this.getSpellAbility().addEffect(new BoostTargetEffect(0, 2)
                .setText("Up to one other target creature gets +0/+2")
                .setTargetPointer(new SecondTargetPointer())
        );
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn,
                "and gains lifelink until end of turn"
        ).setTargetPointer(new SecondTargetPointer()));
    }

    private RallyManeuver(final RallyManeuver card) {
        super(card);
    }

    @Override
    public RallyManeuver copy() {
        return new RallyManeuver(this);
    }
}
