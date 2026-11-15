package mage.cards.o;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.HistoricPredicate;

import java.util.UUID;

/**
 * @author muz
 */
public final class OnTheShouldersOfGiants extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("historic creatures");

    static {
        filter.add(HistoricPredicate.instance);
    }

    public OnTheShouldersOfGiants(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Historic creatures you control have flying.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));
    }

    private OnTheShouldersOfGiants(final OnTheShouldersOfGiants card) {
        super(card);
    }

    @Override
    public OnTheShouldersOfGiants copy() {
        return new OnTheShouldersOfGiants(this);
    }
}
