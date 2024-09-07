package mage.cards.t;

import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TurnInsideOut extends CardImpl {

    public TurnInsideOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target creature gets +3/+0 until end of turn. When it dies this turn, manifest dread.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 0));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new WhenTargetDiesDelayedTriggeredAbility(new ManifestDreadEffect())
                        .setTriggerPhrase("When it dies this turn, ")
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private TurnInsideOut(final TurnInsideOut card) {
        super(card);
    }

    @Override
    public TurnInsideOut copy() {
        return new TurnInsideOut(this);
    }
}
