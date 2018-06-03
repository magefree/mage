
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author anonymous
 */
public final class KeldonMegaliths extends CardImpl {

    public KeldonMegaliths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Keldon Megaliths enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {R}.
        this.addAbility(new RedManaAbility());
        // Hellbent - {1}{R}, {tap}: Keldon Megaliths deals 1 damage to any target. Activate this ability only if you have no cards in hand.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{1}{R}"), HellbentCondition.instance,
                "<i>Hellbent</i> &mdash; {1}{R}, {T}: {this} deals 1 damage to any target. Activate this ability only if you have no cards in hand.");
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public KeldonMegaliths(final KeldonMegaliths card) {
        super(card);
    }

    @Override
    public KeldonMegaliths copy() {
        return new KeldonMegaliths(this);
    }
}
