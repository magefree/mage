package mage.cards.p;

import java.util.UUID;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.InvestigateTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author muz
 */
public final class PantherPounce extends CardImpl {

    public PantherPounce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target player investigates. Target creature gets +1/+0 and gains flying until end of turn. Untap it.
        this.getSpellAbility().addEffect(new InvestigateTargetEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0)
            .setText("Target creature gets +1/+0")
            .setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance())
            .setText("and gains flying until end of turn.")
            .setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addEffect(new UntapTargetEffect()
            .setText("Untap it.")
            .setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private PantherPounce(final PantherPounce card) {
        super(card);
    }

    @Override
    public PantherPounce copy() {
        return new PantherPounce(this);
    }
}
