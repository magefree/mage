package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class DisruptorPistol extends CardImpl {

    public DisruptorPistol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {4}, {T}, Sacrifice this artifact: It deals 5 damage to target creature.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(5, "This artifact"), new ManaCostsImpl<>("{4}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DisruptorPistol(final DisruptorPistol card) {
        super(card);
    }

    @Override
    public DisruptorPistol copy() {
        return new DisruptorPistol(this);
    }
}
