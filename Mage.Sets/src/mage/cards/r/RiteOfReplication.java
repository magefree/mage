
package mage.cards.r;

import java.util.UUID;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class RiteOfReplication extends CardImpl {

    public RiteOfReplication(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}{U}");

        // Kicker {5}
        this.addAbility(new KickerAbility("{5}"));

        // Create a token that's a copy of target creature. If Rite of Replication was kicked, create five of those tokens instead.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new CreateTokenCopyTargetEffect(null, null, false, 5),
                new CreateTokenCopyTargetEffect(), KickedCondition.ONCE,
                "Create a token that's a copy of target creature. If this spell was kicked, create five of those tokens instead"));
    }

    private RiteOfReplication(final RiteOfReplication card) {
        super(card);
    }

    @Override
    public RiteOfReplication copy() {
        return new RiteOfReplication(this);
    }
}
