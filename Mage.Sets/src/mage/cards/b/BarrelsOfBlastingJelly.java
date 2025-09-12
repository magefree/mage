package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.LimitedTimesPerTurnActivatedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarrelsOfBlastingJelly extends CardImpl {

    public BarrelsOfBlastingJelly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {1}: Add one mana of any color. Activate only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedManaAbility(
                Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(), new GenericManaCost(1)
        ));

        // {5}, {T}, Sacrifice this artifact: It deals 5 damage to target creature.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(5, "it"), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BarrelsOfBlastingJelly(final BarrelsOfBlastingJelly card) {
        super(card);
    }

    @Override
    public BarrelsOfBlastingJelly copy() {
        return new BarrelsOfBlastingJelly(this);
    }
}
