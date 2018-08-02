

package mage.cards.d;

import java.util.UUID;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class Dispatch extends CardImpl {

    public Dispatch (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new ExileTargetEffect(), MetalcraftCondition.instance, "<i>Metalcraft</i> &mdash; If you control three or more artifacts, exile that creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public Dispatch (final Dispatch card) {
        super(card);
    }

    @Override
    public Dispatch copy() {
        return new Dispatch(this);
    }

}
