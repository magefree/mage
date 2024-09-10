package mage.cards.b;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BurningSunsFury extends CardImpl {

    public BurningSunsFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Up to two target creatures each get +2/+0 and gain haste until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 0)
                .setText("up to two target creatures each get +2/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setText("and gain haste until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private BurningSunsFury(final BurningSunsFury card) {
        super(card);
    }

    @Override
    public BurningSunsFury copy() {
        return new BurningSunsFury(this);
    }
}
