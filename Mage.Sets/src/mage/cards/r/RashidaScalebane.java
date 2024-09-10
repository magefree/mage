
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.TargetPermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class RashidaScalebane extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking or blocking Dragon");

    static {
        filter.add(Predicates.or(AttackingPredicate.instance, BlockingPredicate.instance));
        filter.add(SubType.DRAGON.getPredicate());
    }

    public RashidaScalebane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {tap}: Destroy target attacking or blocking Dragon. It can't be regenerated. You gain life equal to its power.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(true), new TapSourceCost());
        Effect effect = new GainLifeEffect(TargetPermanentPowerCount.instance);
        effect.setText("You gain life equal to its power");
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private RashidaScalebane(final RashidaScalebane card) {
        super(card);
    }

    @Override
    public RashidaScalebane copy() {
        return new RashidaScalebane(this);
    }
}
