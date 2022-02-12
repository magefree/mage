package mage.cards.m;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.MaritLageToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaritLagesSlumber extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("snow permanent");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 9);
    private static final ConditionHint hint = new ConditionHint(condition, "You control ten or more snow permanents");

    public MaritLagesSlumber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.addSuperType(SuperType.SNOW);

        // Whenever Marit Lage's Slumber or another snow permanent enters the battlefield under your control, scry 1.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new ScryEffect(1, false), filter, false, true
        ));

        // At the beginning of your upkeep, if you control ten or more snow permanents, sacrifice Marit Lage's Slumber. If you do, create Marit Lage, a legendary 20/20 black Avatar creature token with flying and indestructible.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(
                        new CreateTokenEffect(new MaritLageToken()),
                        new SacrificeSourceCost(), "", false
                ), TargetController.YOU, false), condition, "At the beginning of your upkeep, " +
                "if you control ten or more snow permanents, sacrifice {this}. If you do, create Marit Lage, " +
                "a legendary 20/20 black Avatar creature token with flying and indestructible."
        ).addHint(hint));
    }

    private MaritLagesSlumber(final MaritLagesSlumber card) {
        super(card);
    }

    @Override
    public MaritLagesSlumber copy() {
        return new MaritLagesSlumber(this);
    }
}
