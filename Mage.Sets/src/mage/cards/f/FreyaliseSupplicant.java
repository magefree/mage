package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.HalfValue;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class FreyaliseSupplicant extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("red or white creature");

    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.RED),
                new ColorPredicate(ObjectColor.WHITE)));
    }

    public FreyaliseSupplicant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}, Sacrifice a red or white creature: Freyalise Supplicant deals damage to any target equal to half the sacrificed creature's power, rounded down.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(new HalfValue(SacrificeCostCreaturesPower.instance, false)), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(filter)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

    }

    private FreyaliseSupplicant(final FreyaliseSupplicant card) {
        super(card);
    }

    @Override
    public FreyaliseSupplicant copy() {
        return new FreyaliseSupplicant(this);
    }
}
