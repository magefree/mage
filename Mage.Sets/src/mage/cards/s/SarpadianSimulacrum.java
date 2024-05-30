package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SarpadianSimulacrum extends CardImpl {

    public SarpadianSimulacrum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {3}{R}, Sacrifice Sarpadian Simulacrum: It deals 4 damage to target creature.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(4, "it"),
                new ManaCostsImpl<>("{3}{R}")
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SarpadianSimulacrum(final SarpadianSimulacrum card) {
        super(card);
    }

    @Override
    public SarpadianSimulacrum copy() {
        return new SarpadianSimulacrum(this);
    }
}
