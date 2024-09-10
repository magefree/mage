package mage.cards.a;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileSpellWithTimeCountersEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author emerald000
 */
public final class ArcBlade extends CardImpl {

    public ArcBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}{R}");

        // Arc Blade deals 2 damage to any target. Exile Arc Blade with three time counters on it.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new ExileSpellWithTimeCountersEffect(3));

        // Suspend 3—{2}{R}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{2}{R}"), this));
    }

    private ArcBlade(final ArcBlade card) {
        super(card);
    }

    @Override
    public ArcBlade copy() {
        return new ArcBlade(this);
    }
}
