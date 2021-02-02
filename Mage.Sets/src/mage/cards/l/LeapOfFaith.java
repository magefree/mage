
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author noxx
 */
public final class LeapOfFaith extends CardImpl {

    public LeapOfFaith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");


        // Target creature gains flying until end of turn. Prevent all damage that would be dealt to that creature this turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, Integer.MAX_VALUE));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private LeapOfFaith(final LeapOfFaith card) {
        super(card);
    }

    @Override
    public LeapOfFaith copy() {
        return new LeapOfFaith(this);
    }
}
