package mage.cards.m;

import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.Spirit32Token;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MakeYourMark extends CardImpl {

    public MakeYourMark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R/W}");

        // Target creature gets +1/+0 until end of turn.
        // When that creature dies this turn, create a 3/2 red and white Spirit creature token.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new WhenTargetDiesDelayedTriggeredAbility(new CreateTokenEffect(new Spirit32Token()))
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private MakeYourMark(final MakeYourMark card) {
        super(card);
    }

    @Override
    public MakeYourMark copy() {
        return new MakeYourMark(this);
    }
}
