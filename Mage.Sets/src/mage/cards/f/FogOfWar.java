package mage.cards.f;

import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class FogOfWar extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 3 or less");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature on the battlefield");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public FogOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // You gain 1 life for each creature on the battlefield.
        // Prevent all combat damage that would be dealt this turn by creatures with power 3 or less.
        this.getSpellAbility().addEffect(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter2))
                .setText("You gain 1 life for each creature on the battlefield."));
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(filter, Duration.EndOfTurn, true));
    }

    private FogOfWar(final FogOfWar card) {
        super(card);
    }

    @Override
    public FogOfWar copy() {
        return new FogOfWar(this);
    }
}
