package mage.cards.d;

import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DemonicGifts extends CardImpl {

    public DemonicGifts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Until end of turn, target creature gets +2/+0 and gains "When this creature dies, return it to the battlefield under its owner's control."
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                2, 0, Duration.EndOfTurn
        ).setText("Until end of turn, target creature gets +2/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                new DiesSourceTriggeredAbility(
                        new ReturnSourceFromGraveyardToBattlefieldEffect(false, true), false
                ), Duration.EndOfTurn, "and gains \"When this creature dies, " +
                "return it to the battlefield under its owner's control.\""
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DemonicGifts(final DemonicGifts card) {
        super(card);
    }

    @Override
    public DemonicGifts copy() {
        return new DemonicGifts(this);
    }
}
