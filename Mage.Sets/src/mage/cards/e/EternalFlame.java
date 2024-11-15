
package mage.cards.e;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.HalfValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetOpponentOrPlaneswalker;

import java.util.UUID;

/**
 * @author L_J
 */
public final class EternalFlame extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.MOUNTAIN));
    private static final Hint hint = new ValueHint("Mountains you control", xValue);

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Mountains you control");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public EternalFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Eternal Flame deals X damage to target opponent, where X is the number of Mountains you control. It deals half X damage, rounded up, to you.);
        this.getSpellAbility().addEffect(new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter)).setText("{this} deals X damage to target opponent, where X is the number of Mountains you control"));
        this.getSpellAbility().addEffect(new DamageControllerEffect(new HalfValue(new PermanentsOnBattlefieldCount(filter), true)).setText("It deals half X damage, rounded up, to you"));
        this.getSpellAbility().addTarget(new TargetOpponentOrPlaneswalker());
        this.getSpellAbility().addHint(hint);
    }

    private EternalFlame(final EternalFlame card) {
        super(card);
    }

    @Override
    public EternalFlame copy() {
        return new EternalFlame(this);
    }
}
