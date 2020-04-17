package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpringjawTrap extends CardImpl {

    public SpringjawTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // {4}, {T}, Sacrifice Springjaw Trap: It deals 3 damage to any target.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(3, "it"), new GenericManaCost(4)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SpringjawTrap(final SpringjawTrap card) {
        super(card);
    }

    @Override
    public SpringjawTrap copy() {
        return new SpringjawTrap(this);
    }
}
