package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetAndSelfEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class PsionicSliver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SLIVER, "All Sliver creatures");

    public PsionicSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All Sliver creatures have "{T}: This creature deals 2 damage to any target and 3 damage to itself."
        Ability ability = new SimpleActivatedAbility(new DamageTargetAndSelfEffect(2, 3), new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(
                new SimpleStaticAbility(new GainAbilityAllEffect(ability, Duration.WhileOnBattlefield, filter,
                        "All Sliver creatures have \"{T}: This creature deals 2 damage to any target and 3 damage to itself.\"")
                )
        );
    }

    private PsionicSliver(final PsionicSliver card) {
        super(card);
    }

    @Override
    public PsionicSliver copy() {
        return new PsionicSliver(this);
    }
}
