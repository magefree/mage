
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author maxlebedev
 */
public final class SylvanParadise extends CardImpl {

    public SylvanParadise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Any number of target creatures become green until end of turn.
        Effect effect = new BecomesColorTargetEffect(ObjectColor.GREEN, Duration.EndOfTurn);
        effect.setText("Any number of target creatures become green until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE, StaticFilters.FILTER_PERMANENT_CREATURE, false));

    }

    private SylvanParadise(final SylvanParadise card) {
        super(card);
    }

    @Override
    public SylvanParadise copy() {
        return new SylvanParadise(this);
    }

}
