
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AccursedHorde extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("attacking Zombie");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public AccursedHorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}{B}: Target attacking Zombie gains indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{1}{B}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private AccursedHorde(final AccursedHorde card) {
        super(card);
    }

    @Override
    public AccursedHorde copy() {
        return new AccursedHorde(this);
    }
}
