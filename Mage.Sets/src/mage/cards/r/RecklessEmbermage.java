
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageSelfEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Quercitron
 */
public final class RecklessEmbermage extends CardImpl {

    public RecklessEmbermage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{R}: Reckless Embermage deals 1 damage to any target and 1 damage to itself.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{1}{R}"));
        ability.addEffect(new DamageSelfEffect(1));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private RecklessEmbermage(final RecklessEmbermage card) {
        super(card);
    }

    @Override
    public RecklessEmbermage copy() {
        return new RecklessEmbermage(this);
    }
}
