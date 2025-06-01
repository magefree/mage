package mage.cards.h;

import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HasteMagic extends CardImpl {

    public HasteMagic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target creature gets +3/+1 and gains haste until end of turn. Exile the top card of your library. You may play it until your next end step.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 1).setText("target creature gets +3/+1"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance()).setText("and gains haste until end of turn"));
        this.getSpellAbility().addEffect(new ExileTopXMayPlayUntilEffect(1, Duration.UntilYourNextEndStep));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private HasteMagic(final HasteMagic card) {
        super(card);
    }

    @Override
    public HasteMagic copy() {
        return new HasteMagic(this);
    }
}
