package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TatyovaStewardOfTides extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("land creatures");

    static {
        filter.add(CardType.LAND.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_LAND, ComparisonType.MORE_THAN, 6, true
    );

    public TatyovaStewardOfTides(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Land creatures you control have flying.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // Whenever a land enters the battlefield under your control, if you control seven or more lands, up to one target land you control becomes a 3/3 Elemental creature with haste. It's still a land.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldControlledTriggeredAbility(new BecomesCreatureTargetEffect(
                        new CreatureToken(
                                3, 3, "3/3 Elemental creature with haste", SubType.ELEMENTAL
                        ).withAbility(HasteAbility.getInstance()), false, true, Duration.Custom
                ), StaticFilters.FILTER_LAND_A), condition, "Whenever a land enters the battlefield " +
                "under your control, if you control seven or more lands, up to one target land you control " +
                "becomes a 3/3 Elemental creature with haste. It's still a land."
        );
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
        this.addAbility(ability);
    }

    private TatyovaStewardOfTides(final TatyovaStewardOfTides card) {
        super(card);
    }

    @Override
    public TatyovaStewardOfTides copy() {
        return new TatyovaStewardOfTides(this);
    }
}
