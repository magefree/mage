package mage.cards.g;

import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class GetALegUp extends CardImpl {

    public GetALegUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Until end of turn, target creature gets +1/+1 for each creature you control and gains reach.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(CreaturesYouControlCount.instance, CreaturesYouControlCount.instance, Duration.EndOfTurn)
                .setText("until end of turn, target creature gets +1/+1 for each creature you control"));

        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(ReachAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains reach"));
        this.getSpellAbility().addHint(CreaturesYouControlHint.instance);
    }

    private GetALegUp(final GetALegUp card) {
        super(card);
    }

    @Override
    public GetALegUp copy() {
        return new GetALegUp(this);
    }
}
