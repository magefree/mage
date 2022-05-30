
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterBlockingCreature;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class ElvenFortress extends CardImpl {

    public ElvenFortress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");

        // {1}{G}: Target blocking creature gets +0/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(0, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{G}"));
        ability.addTarget(new TargetCreaturePermanent(new FilterBlockingCreature()));
        this.addAbility(ability);
    }

    private ElvenFortress(final ElvenFortress card) {
        super(card);
    }

    @Override
    public ElvenFortress copy() {
        return new ElvenFortress(this);
    }
}
