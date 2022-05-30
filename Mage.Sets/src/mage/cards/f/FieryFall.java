

package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class FieryFall extends CardImpl {

    public FieryFall (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{R}");

        
        // Fiery Fall deals 5 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
        // Basic landcycling  {1}{R} ({1}{R}, Discard this card: Search your library for a basic land card, reveal it, and put it into your hand. Then shuffle your library.)
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{1}{R}")));
    }

    public FieryFall (final FieryFall card) {
        super(card);
    }

    @Override
    public FieryFall copy() {
        return new FieryFall(this);
    }
}
