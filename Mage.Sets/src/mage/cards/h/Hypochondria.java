
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox
 */
public final class Hypochondria extends CardImpl {

    public Hypochondria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");

        // {W}, Discard a card: Prevent the next 3 damage that would be dealt to any target this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, 3), new ManaCostsImpl<>("{W}"));
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
        // {W}, Sacrifice Hypochondria: Prevent the next 3 damage that would be dealt to any target this turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, 3), new ManaCostsImpl<>("{W}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private Hypochondria(final Hypochondria card) {
        super(card);
    }

    @Override
    public Hypochondria copy() {
        return new Hypochondria(this);
    }
}
