
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author LevelX2
 */
public final class CrossroadsConsecrator extends CardImpl {

    private final static FilterAttackingCreature filter = new FilterAttackingCreature("attacking Human");

    static {
        filter.add(new SubtypePredicate(SubType.HUMAN));
    }

    public CrossroadsConsecrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {G}, {T}: Target attacking Human gets +1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{G}"));
        ability.addTarget(new TargetAttackingCreature(1, 1, filter, false));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public CrossroadsConsecrator(final CrossroadsConsecrator card) {
        super(card);
    }

    @Override
    public CrossroadsConsecrator copy() {
        return new CrossroadsConsecrator(this);
    }
}
