
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Galatolol
 */
public final class VenomspoutBrackus extends CardImpl {

    private static final FilterAttackingOrBlockingCreature filter = new FilterAttackingOrBlockingCreature(
            "attacking or blocking creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public VenomspoutBrackus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}");
        
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {1}{G}, {tap}: Venomspout Brackus deals 5 damage to target attacking or blocking creature with flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(5), new ManaCostsImpl("{1}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
        // Morph {3}{G}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{3}{G}{G}")));

    }

    private VenomspoutBrackus(final VenomspoutBrackus card) {
        super(card);
    }

    @Override
    public VenomspoutBrackus copy() {
        return new VenomspoutBrackus(this);
    }
}
