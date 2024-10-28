package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.YouGainedOrLostLifeCondition;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StarCharter extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("a creature card with power 3 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public StarCharter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.BAT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your end step, if you gained or lost life this turn, look at the top four cards of your library. You may reveal a creature card with power 3 or less from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new LookLibraryAndPickControllerEffect(
                        4, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
                ), false, YouGainedOrLostLifeCondition.instance
        ).addHint(YouGainedOrLostLifeCondition.getHint()), new PlayerGainedLifeWatcher());
    }

    private StarCharter(final StarCharter card) {
        super(card);
    }

    @Override
    public StarCharter copy() {
        return new StarCharter(this);
    }
}
