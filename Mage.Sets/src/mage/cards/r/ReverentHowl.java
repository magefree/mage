package mage.cards.r;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author muz
 */
public final class ReverentHowl extends CardImpl {

    public ReverentHowl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Choose one --
        // * Target player draws two cards and loses 2 life.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(2).withTargetDescription("and"));

        // * Target creature gets +2/+2 and gains lifelink until end of turn.
        Mode mode = new Mode(new BoostTargetEffect(2, 2, Duration.EndOfTurn)
            .setText("target creature gets +2/+2"));
        mode.addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn)
            .setText("and gains lifelink until end of turn"));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private ReverentHowl(final ReverentHowl card) {
        super(card);
    }

    @Override
    public ReverentHowl copy() {
        return new ReverentHowl(this);
    }
}
