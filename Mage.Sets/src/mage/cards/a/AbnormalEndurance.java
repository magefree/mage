package mage.cards.a;

import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class AbnormalEndurance extends CardImpl {

    public AbnormalEndurance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Until end of turn, target creature gets +2/+0 and gains "When this creature dies, return it to the battlefield tapped under its owner's control."
        getSpellAbility().addTarget(new TargetCreaturePermanent());
        getSpellAbility().addEffect(new BoostTargetEffect(2, 0, Duration.EndOfTurn)
                .setText("Until end of turn, target creature gets +2/+0")
        );
        getSpellAbility().addEffect(new GainAbilityTargetEffect(
                new DiesSourceTriggeredAbility(
                        new ReturnToBattlefieldUnderOwnerControlTargetEffect(true, false)
                                .setText("return it to the battlefield tapped under its owner's control"),
                        false, SetTargetPointer.CARD),
                Duration.EndOfTurn,
                "and gains \"When this creature dies, return it to the battlefield tapped under its owner's control.\""
        ));
    }

    private AbnormalEndurance(final AbnormalEndurance card) {
        super(card);
    }

    @Override
    public AbnormalEndurance copy() {
        return new AbnormalEndurance(this);
    }
}
