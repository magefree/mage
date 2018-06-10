
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherTargetPredicate;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class UlvenwaldTracker extends CardImpl {

    public UlvenwaldTracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{G}, {tap}: Target creature you control fights another target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new FightTargetsEffect(), new ManaCostsImpl("{1}{G}"));
        ability.addCost(new TapSourceCost());
        Target controlledTarget = new TargetControlledCreaturePermanent();
        controlledTarget.setTargetTag(1);
        ability.addTarget(controlledTarget);
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new AnotherTargetPredicate(2));
        Target secondTarget = new TargetCreaturePermanent(filter);
        secondTarget.setTargetTag(2);
        ability.addTarget(secondTarget);
        this.addAbility(ability);
    }

    public UlvenwaldTracker(final UlvenwaldTracker card) {
        super(card);
    }

    @Override
    public UlvenwaldTracker copy() {
        return new UlvenwaldTracker(this);
    }
}
