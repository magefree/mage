
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetAtBeginningOfNextEndStepEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class GlyphOfDestruction extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("blocking Wall you control");

    static {
        filter.add(SubType.WALL.getPredicate());
        filter.add(BlockingPredicate.instance);
    }

    public GlyphOfDestruction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Target blocking Wall you control gets +10/+0 until end of combat. Prevent all damage that would be dealt to it this turn. Destroy it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new BoostTargetEffect(10, 0, Duration.EndOfCombat));
        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, Integer.MAX_VALUE).setText("Prevent all damage that would be dealt to it this turn"));
        this.getSpellAbility().addEffect(new DestroyTargetAtBeginningOfNextEndStepEffect().setText("Destroy it at the beginning of the next end step"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(filter));
    }

    private GlyphOfDestruction(final GlyphOfDestruction card) {
        super(card);
    }

    @Override
    public GlyphOfDestruction copy() {
        return new GlyphOfDestruction(this);
    }
}
