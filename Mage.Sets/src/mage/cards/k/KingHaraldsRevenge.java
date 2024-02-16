package mage.cards.k;

import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KingHaraldsRevenge extends CardImpl {

    public KingHaraldsRevenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Until end of turn, target creature gets +1/+1 for each creature you control and gains trample. It must be blocked this turn if able.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                CreaturesYouControlCount.instance, CreaturesYouControlCount.instance, Duration.EndOfTurn
        ).setText("until end of turn, target creature gets +1/+1 for each creature you control"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample."));
        this.getSpellAbility().addEffect(new MustBeBlockedByAtLeastOneTargetEffect(Duration.EndOfTurn)
                .setText("It must be blocked this turn if able"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private KingHaraldsRevenge(final KingHaraldsRevenge card) {
        super(card);
    }

    @Override
    public KingHaraldsRevenge copy() {
        return new KingHaraldsRevenge(this);
    }
}
