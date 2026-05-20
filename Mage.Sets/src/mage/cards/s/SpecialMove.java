package mage.cards.s;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterAnyTarget;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetPermanentOrPlayer;

/**
 *
 * @author muz
 */
public final class SpecialMove extends CardImpl {

    private static final FilterAttackingOrBlockingCreature filter =
        new FilterAttackingOrBlockingCreature("attacking or blocking creature you control");
    private static final FilterPermanentOrPlayer filterSecondTarget =
        new FilterAnyTarget("any other target");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filterSecondTarget.getPermanentFilter().add(new AnotherTargetPredicate(2));
    }

    public SpecialMove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Choose two --
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Jump Kick -- Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().withFirstModeFlavorWord("Jump Kick");

        // * Dash Attack -- Put two +1/+1 counters on target attacking or blocking creature you control.
        this.getSpellAbility().addMode(new Mode(new AddCountersTargetEffect(
            CounterType.P1P1.createInstance(2)
        )).addTarget(new TargetPermanent(filter))
            .withFlavorWord("Dash Attack"));

        // * Foot Toss -- Target creature you control deals damage equal to its power to any other target. Then sacrifice it.
        this.getSpellAbility().addMode(new Mode(new DamageWithPowerFromOneToAnotherTargetEffect())
            .addTarget(new TargetControlledCreaturePermanent().setTargetTag(1))
            .addTarget(new TargetPermanentOrPlayer(filterSecondTarget).setTargetTag(2))
            .addEffect(new SacrificeTargetEffect("sacrifice it").concatBy("Then"))
            .withFlavorWord("Foot Toss")
        );
    }

    private SpecialMove(final SpecialMove card) {
        super(card);
    }

    @Override
    public SpecialMove copy() {
        return new SpecialMove(this);
    }
}
