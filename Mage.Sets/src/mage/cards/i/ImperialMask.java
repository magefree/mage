
package mage.cards.i;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author TheElk801
 */
public final class ImperialMask extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public ImperialMask(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");

        // When Imperial Mask enters the battlefield, if it's not a token, each of your teammates puts a token that's a copy of Imperial Mask onto the battlefield.
        // No implementation of teammates currently, so no effect needed
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new InfoEffect("each of your teammates puts a token that's a copy of {this} onto the battlefield"), false),
                new SourceMatchesFilterCondition(filter),
                "When {this} enters the battlefield, if it's not a token, "
                + "each of your teammates puts a token that's a copy of {this} onto the battlefield"
        ));

        // You have hexproof.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControllerEffect(HexproofAbility.getInstance())));
    }

    public ImperialMask(final ImperialMask card) {
        super(card);
    }

    @Override
    public ImperialMask copy() {
        return new ImperialMask(this);
    }
}
