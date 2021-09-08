package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SacredFire extends CardImpl {

    public SacredFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{W}");

        // Sacred Fire deals 2 damage to any target and you gain 2 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addEffect(new GainLifeEffect(2).concatBy("and"));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Flashback {4}{R}{W}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl<>("{4}{R}{W}"), TimingRule.INSTANT));
    }

    private SacredFire(final SacredFire card) {
        super(card);
    }

    @Override
    public SacredFire copy() {
        return new SacredFire(this);
    }
}
