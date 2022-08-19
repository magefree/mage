
package mage.cards.v;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CantBeTargetedTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterObject;
import mage.filter.FilterStackObject;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author nantuko
 */
public final class VinesOfVastwood extends CardImpl {

    private static final FilterObject filter = new FilterStackObject("spells or abilities your opponents control");

    private static final String staticText = "if this spell was kicked, that creature gets +4/+4 until end of turn";

    public VinesOfVastwood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Kicker {G} (You may pay an additional {G} as you cast this spell.)
        this.addAbility(new KickerAbility("{G}"));

        // Target creature can't be the target of spells or abilities your opponents control this turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new CantBeTargetedTargetEffect(filter, Duration.EndOfTurn, TargetController.OPPONENT));

        // If Vines of Vastwood was kicked, that creature gets +4/+4 until end of turn.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(new BoostTargetEffect(4, 4, Duration.EndOfTurn),
                new LockedInCondition(KickedCondition.ONCE), staticText));
    }

    private VinesOfVastwood(final VinesOfVastwood card) {
        super(card);
    }

    @Override
    public VinesOfVastwood copy() {
        return new VinesOfVastwood(this);
    }
}
