package mage.cards.s;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SyphonFuel extends CardImpl {

    public SyphonFuel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Target creature gets -6/-6 until end of turn. You gain 2 life.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-6, -6));
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SyphonFuel(final SyphonFuel card) {
        super(card);
    }

    @Override
    public SyphonFuel copy() {
        return new SyphonFuel(this);
    }
}
