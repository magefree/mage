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
public final class SmitingHelix extends CardImpl {

    public SmitingHelix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Smiting Helix deals 3 damage to any target and you gain 3 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addEffect(new GainLifeEffect(3).concatBy("and"));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Flashback {R}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{R}{W}")));
    }

    private SmitingHelix(final SmitingHelix card) {
        super(card);
    }

    @Override
    public SmitingHelix copy() {
        return new SmitingHelix(this);
    }
}
