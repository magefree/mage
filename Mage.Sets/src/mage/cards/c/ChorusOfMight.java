package mage.cards.c;

import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ChorusOfMight extends CardImpl {

    public ChorusOfMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Until end of turn, target creature gets +1/+1 for each creature you control and gains trample.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(CreaturesYouControlCount.instance, CreaturesYouControlCount.instance, Duration.EndOfTurn)
                .setText("until end of turn, target creature gets +1/+1 for each creature you control")
        );
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains trample")
        );
        this.getSpellAbility().addHint(CreaturesYouControlHint.instance);
    }

    private ChorusOfMight(final ChorusOfMight card) {
        super(card);
    }

    @Override
    public ChorusOfMight copy() {
        return new ChorusOfMight(this);
    }
}
