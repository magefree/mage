
package mage.cards.e;

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
 * @author LevelX2
 */
public final class Enlarge extends CardImpl {

    public Enlarge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // Target creature gets +7/+7 and gains trample until end of turn. It must be blocked this turn if able.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                7, 7, Duration.EndOfTurn
        ).setText("target creature gets +7/+7"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample until end of turn"));
        this.getSpellAbility().addEffect(new MustBeBlockedByAtLeastOneTargetEffect(Duration.EndOfTurn)
                .setText("It must be blocked this turn if able"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Enlarge(final Enlarge card) {
        super(card);
    }

    @Override
    public Enlarge copy() {
        return new Enlarge(this);
    }
}
