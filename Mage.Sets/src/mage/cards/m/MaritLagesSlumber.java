package mage.cards.m;

import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SuperType;
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
    private static final FilterPermanent filter2
            = new FilterControlledPermanent("you control ten or more snow permanents");

    static {
        filter.add(SuperType.SNOW.getPredicate());
        filter2.add(SuperType.SNOW.getPredicate());
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter2, ComparisonType.MORE_THAN, 9);
    private static final ConditionHint hint = new ConditionHint(condition);

    public MaritLagesSlumber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.supertype.add(SuperType.SNOW);

        // Whenever Marit Lage's Slumber or another snow permanent you control enters, scry 1.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new ScryEffect(1, false), filter, false, true
        ));

        // At the beginning of your upkeep, if you control ten or more snow permanents, sacrifice Marit Lage's Slumber. If you do, create Marit Lage, a legendary 20/20 black Avatar creature token with flying and indestructible.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(
                new CreateTokenEffect(new MaritLageToken()),
                new SacrificeSourceCost(), "", false
        )).withInterveningIf(condition).addHint(hint));
    }

    private MaritLagesSlumber(final MaritLagesSlumber card) {
        super(card);
    }

    @Override
    public MaritLagesSlumber copy() {
        return new MaritLagesSlumber(this);
    }
}
