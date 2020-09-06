package mage.cards.i;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InordinateRage extends CardImpl {

    public InordinateRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target creature gets +3/+2 until end of turn. Scry 1.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 2));
        this.getSpellAbility().addEffect(new ScryEffect(1).setText("Scry 1"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private InordinateRage(final InordinateRage card) {
        super(card);
    }

    @Override
    public InordinateRage copy() {
        return new InordinateRage(this);
    }
}
