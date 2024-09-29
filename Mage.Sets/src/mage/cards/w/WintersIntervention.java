package mage.cards.w;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WintersIntervention extends CardImpl {

    public WintersIntervention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Winter's Intervention deals 2 damage to target creature. You gain 2 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
    }

    private WintersIntervention(final WintersIntervention card) {
        super(card);
    }

    @Override
    public WintersIntervention copy() {
        return new WintersIntervention(this);
    }
}
