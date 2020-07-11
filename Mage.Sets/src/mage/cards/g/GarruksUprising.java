package mage.cards.g;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class GarruksUprising extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a creature with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public GarruksUprising(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // When Garruk's Uprising enters the battlefield, if you control a creature with power 4 or greater, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), FerociousCondition.instance))
            .addHint(FerociousHint.instance));

        // Creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES
        )));

        // Whenever a creature with power 4 or greater enters the battlefield under your control, draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), filter, false
        ));
    }

    private GarruksUprising(final GarruksUprising card) {
        super(card);
    }

    @Override
    public GarruksUprising copy() {
        return new GarruksUprising(this);
    }
}
