package mage.cards.h;

import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HobbitsSting extends CardImpl {

    private static final DynamicValue xValue = new AdditiveDynamicValue(
            CreaturesYouControlCount.instance,
            new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.FOOD))
    );
    private static final Hint hint = new ValueHint("Creatures and Foods you control", xValue);

    public HobbitsSting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Hobbit's Sting deals X damage to target creature, where X is the number of creatures you control plus the number of Foods you control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue).setText("{this} deals X damage to target creature, where X is the number of creatures you control plus the number of Foods you control"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(hint);
    }

    private HobbitsSting(final HobbitsSting card) {
        super(card);
    }

    @Override
    public HobbitsSting copy() {
        return new HobbitsSting(this);
    }
}
