package mage.cards.z;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZenithFlare extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new AbilityPredicate(CyclingAbility.class));
    }

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(filter);

    public ZenithFlare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}{W}");

        // Zenith Flare deals X damage to any target and you gain X life, where X is the number of cards with a cycling ability in your graveyard.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue)
                .setText("{this} deals X damage to any target"));
        this.getSpellAbility().addEffect(new GainLifeEffect(xValue)
                .setText("and you gain X life, where X is the number of cards with a cycling ability in your graveyard"));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private ZenithFlare(final ZenithFlare card) {
        super(card);
    }

    @Override
    public ZenithFlare copy() {
        return new ZenithFlare(this);
    }
}
