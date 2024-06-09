package mage.cards.d;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class Damn extends CardImpl {

    public Damn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{B}");

        // Destroy target creature. A creature destroyed this way can’t be regenerated.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true)
                .setText("destroy target creature. A creature destroyed this way can't be regenerated"));

        // Overload {2}{W}{W} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of “target” with “each.”)
        this.addAbility(new OverloadAbility(
                this,
                new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES, true),
                new ManaCostsImpl<>("{2}{W}{W}")
        ));
    }

    private Damn(final Damn card) {
        super(card);
    }

    @Override
    public Damn copy() {
        return new Damn(this);
    }
}
