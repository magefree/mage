
package mage.cards.v;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class VampiresBite extends CardImpl {

    public VampiresBite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");


        // Kicker {2}{B} (You may pay an additional {2}{B} as you cast this spell.)
        this.addAbility(new KickerAbility("{2}{B}"));

        // Target creature gets +3/+0 until end of turn. If Vampire's Bite was kicked, that creature gains lifelink until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 0, Duration.EndOfTurn));
        ContinuousEffect effect = new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn);
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(effect, new LockedInCondition(KickedCondition.ONCE), "if this spell was kicked, that creature gains lifelink until end of turn"));
    }

    private VampiresBite(final VampiresBite card) {
        super(card);
    }

    @Override
    public VampiresBite copy() {
        return new VampiresBite(this);
    }
}
