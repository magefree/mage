package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LoneFox
 */
public final class DesolationGiant extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public DesolationGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Kicker {W}{W}
        this.addAbility(new KickerAbility("{W}{W}"));
        // When Desolation Giant enters the battlefield, destroy all other creatures you control. If it was kicked, destroy all other creatures instead.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(new DestroyAllEffect(filter),
            new DestroyAllEffect(StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES), KickedCondition.ONCE, "destroy all other creatures you control. If it was kicked, destroy all other creatures instead.")));
}

    private DesolationGiant(final DesolationGiant card) {
        super(card);
    }

    @Override
    public DesolationGiant copy() {
        return new DesolationGiant(this);
    }
}
