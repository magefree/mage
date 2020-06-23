package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.cost.DynamicValueCostModidicationEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class SanctumOfTranquilLight extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Shrine you control");
    private static final PermanentsOnBattlefieldCount count = new PermanentsOnBattlefieldCount(filter);

    static {
        filter.add(SubType.SHRINE.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public SanctumOfTranquilLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // {5}{W}: Tap target creature. This ability costs {1} less to activate for each Shrine you control.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new ManaCostsImpl<>("{5}{W}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new DynamicValueCostModidicationEffect(Duration.WhileOnBattlefield, Outcome.Benefit, ability.getOriginalId(), count, true));
        ability.addHint(new ValueHint("Shrines you control", count));
        this.addAbility(ability);
    }

    private SanctumOfTranquilLight(final SanctumOfTranquilLight card) {
        super(card);
    }

    @Override
    public SanctumOfTranquilLight copy() {
        return new SanctumOfTranquilLight(this);
    }
}
