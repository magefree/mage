
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LevelX2
 */
public final class SanctifiedCharge extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("White creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public SanctifiedCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{W}");


        // Creatures you control get +2/+1 until end of turn.  White creatures you control also gain first strike until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 1, Duration.EndOfTurn));
        Effect effect = new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, filter);
        effect.setText("White creatures you control also gain first strike until end of turn");
        this.getSpellAbility().addEffect(effect);

    }

    private SanctifiedCharge(final SanctifiedCharge card) {
        super(card);
    }

    @Override
    public SanctifiedCharge copy() {
        return new SanctifiedCharge(this);
    }
}
