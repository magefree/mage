package mage.cards.l;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LocustSpray extends CardImpl {

    public LocustSpray(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target creature gets -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-1, -1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Cycling {B}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{B}")));
    }

    private LocustSpray(final LocustSpray card) {
        super(card);
    }

    @Override
    public LocustSpray copy() {
        return new LocustSpray(this);
    }
}
