package mage.cards.v;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public final class ViolentEruption extends CardImpl {

    public ViolentEruption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}{R}{R}");

        // Violent Eruption deals 4 damage divided as you choose among any number of targets.
        this.getSpellAbility().addEffect(new DamageMultiEffect(4));
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(4));

        // Madness {1}{R}{R}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{1}{R}{R}")));
    }

    private ViolentEruption(final ViolentEruption card) {
        super(card);
    }

    @Override
    public ViolentEruption copy() {
        return new ViolentEruption(this);
    }
}
