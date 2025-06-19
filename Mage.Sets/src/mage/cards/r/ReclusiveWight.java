
package mage.cards.r;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReclusiveWight extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("you control another nonland permanent");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public ReclusiveWight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, if you control another nonland permanent, sacrifice Reclusive Wight.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceEffect()).withInterveningIf(condition));
    }

    private ReclusiveWight(final ReclusiveWight card) {
        super(card);
    }

    @Override
    public ReclusiveWight copy() {
        return new ReclusiveWight(this);
    }
}
