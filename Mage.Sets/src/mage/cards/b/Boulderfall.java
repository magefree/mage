package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author Plopman
 */
public final class Boulderfall extends CardImpl {

    public Boulderfall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{6}{R}{R}");

        // Boulderfall deals 5 damage divided as you choose among any number of targets.
        this.getSpellAbility().addEffect(new DamageMultiEffect(5));
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(5));
    }

    private Boulderfall(final Boulderfall card) {
        super(card);
    }

    @Override
    public Boulderfall copy() {
        return new Boulderfall(this);
    }
}
